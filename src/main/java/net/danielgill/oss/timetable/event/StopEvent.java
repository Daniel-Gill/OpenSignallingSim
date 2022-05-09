package net.danielgill.oss.timetable.event;

import net.danielgill.oss.time.Time;

public class StopEvent extends Event {

    public StopEvent(Time time, EventType type) {
        super(time, type);
    }
    
}
