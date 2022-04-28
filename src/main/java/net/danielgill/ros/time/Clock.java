package net.danielgill.ros.time;

import com.almasb.fxgl.time.TimerAction;
import static com.almasb.fxgl.dsl.FXGL.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.util.Duration;

public class Clock {
    private Time time;
    private TimerAction ta;
    private List<Runnable> runAtSecond;
    private List<Runnable> actions;
    private Map<Runnable, Time> actionTimes;
    private Map<Runnable, String> flags;
    private Map<String, Boolean> flagsState;

    public Clock(Time t) {
        time = t;
        runAtSecond = new ArrayList<>();
        actions = new ArrayList<>();
        actionTimes = new HashMap<>();
        flags = new HashMap<>();
        flagsState = new HashMap<>();

        ta = getGameTimer().runAtInterval(() -> {
            runActions();
            for(Runnable r : runAtSecond) {
                if(flags.containsKey(r)) {
                    if(!flagsState.get(flags.get(r))) {
                        r.run();
                    }
                } else {
                    r.run();
                }
            }
            time.addSecond(1);
        }, Duration.seconds(1));

        ta.pause();
    }

    public void start() {
        ta.resume();
    }

    public void pause() {
        ta.pause();
    }

    public void resume() {
        ta.resume();
    }

    public void runAtTime(Runnable r, Time t) {
        actions.add(r);
        actionTimes.put(r, t);
    }

    public Runnable runAtSecond(Runnable r) {
        runAtSecond.add(r);
        return r;
    }

    public Runnable runAtSecond(Runnable r, String flag) {
        runAtSecond.add(r);
        flags.put(r, flag);
        flagsState.put(flag, false);
        return r;
    }

    public void runAfter(Runnable r, int second) {
        Time t = time.copy();
        t.addSecond(second);
        runAtTime(r, t);
    }

    public void runAfter(Runnable r, Time t) {
        Time time = this.time.copy();
        time.addTime(t);
        System.out.println("Running at " + time);
        runAtTime(r, time);
    }

    public void removeRun(Runnable r) {
        runAtSecond.remove(r);
    }

    public void setFlag(String flag, boolean state) {
        flagsState.put(flag, state);
    }

    private void runActions() {
        List<Runnable> toRemove = new ArrayList<>();

        for(Runnable r : actions) {
            if(actionTimes.get(r).equals(time)) {
                r.run();
                toRemove.add(r);
            }
        }
        for(Runnable r : toRemove) {
            actions.remove(r);
            actionTimes.remove(r);
        }
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time t) {
        time = t;
    }
}
