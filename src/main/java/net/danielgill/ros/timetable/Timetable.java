package net.danielgill.ros.timetable;

import java.util.ArrayList;
import java.util.List;

import net.danielgill.ros.time.Time;
import net.danielgill.ros.train.Performance;
import net.danielgill.ros.train.Train;

public class Timetable {
    Time startTime;
    List<Schedule> schedules;

    public Timetable(Time startTime) {
        this.startTime = startTime;
        schedules = new ArrayList<>();
    }

    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    public void createTrains() {
        for(Schedule schedule : schedules) {
            Train t = new Train(schedule.getId(), schedule, new Performance(100));
            t.start();
        }
    }

    public Time getStartTime() {
        return startTime;
    }
}
