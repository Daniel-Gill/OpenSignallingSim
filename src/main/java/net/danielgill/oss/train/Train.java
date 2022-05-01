package net.danielgill.oss.train;

import net.danielgill.oss.App;
import net.danielgill.oss.block.Block;
import net.danielgill.oss.time.Duration;
import net.danielgill.oss.time.Time;
import net.danielgill.oss.timetable.Schedule;
import net.danielgill.oss.timetable.event.EntryEvent;
import net.danielgill.oss.timetable.event.EventType;

public class Train {
    private String id;
    private Schedule schedule;
    private Performance performance;
    private Block block;
    private TrainStatus status;

    public Train(String id, Schedule schedule, Performance performance) {
        this.id = id;
        this.schedule = schedule;
        this.performance = performance;
    }

    public void start() {
        EntryEvent e;

        if(schedule.getNext().getType() == EventType.ENTRY) {
            e = (EntryEvent) schedule.getNext();
        } else {
            throw new RuntimeException("Train " + id + " has no entry event");
        }

        block = e.getBlock();
        Time t = e.getTime().copy();
        t.removeMinutes(1);

        App.clock.runAtTime(() -> {
            prepare();
        }, t);
    }

    private void prepare() {
        App.clock.runAtSecond(() -> {
            if(!block.isOccupied()) {
                this.status = TrainStatus.PREPARING;
                block.occupyEarly(this);
                App.clock.setFlag("train_" + id + "_prepare", true);
                waitEntry();
            }
        }, "train_" + id + "_prepare");
    }

    private void waitEntry() {
        EntryEvent e = (EntryEvent) schedule.getNext();
        Time t = e.getTime().copy();
        App.clock.runAtTime(() -> {
            System.out.println("[" + id + "] Entered block " + block.getId() + " at time " + t);
            status = TrainStatus.WAITING;
            block.setNotEarly();
            App.clock.runAtSecond(() -> {
                run();
            }, "train_" + id + "_run");
        }, t);
    }

    private void run() {
        if(status == TrainStatus.WAITING) {
            System.out.println("WAITING");
            if(block.canExit()) {
                status = TrainStatus.RUNNING;
                App.clock.setFlag("train_" + id + "_run", true);
                block.getNextBlock().occupy(this);
                App.clock.runAfter(() -> {
                    block.unoccupy();
                    Block old = block;
                    block = old.getNextBlock();
                    old.clearPath();
                }, 10);

                Duration d = performance.getTimeForPath(block.getPath());
                App.clock.runAfter(() -> {
                    this.status = TrainStatus.WAITING;
                    App.clock.setFlag("train_" + id + "_run", false);
                }, d.getSeconds());

            }
        }
    }

    public String getId() {
        return id;
    }
}
