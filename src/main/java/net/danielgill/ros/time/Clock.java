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

    public Clock(int hour, int minute, int second) {
        time = new Time(hour, minute, second);
        runAtSecond = new ArrayList<>();
        actions = new ArrayList<>();
        actionTimes = new HashMap<>();
        ta = getGameTimer().runAtInterval(() -> {
            runActions();
            for(Runnable r : runAtSecond) {
                r.run();
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

    public void runAtSecond(Runnable r) {
        runAtSecond.add(r);
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
}
