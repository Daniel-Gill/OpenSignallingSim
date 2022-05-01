package net.danielgill.oss.timetable.event;

import net.danielgill.oss.time.Time;

public abstract class Event {
    protected Time time;
    protected EventType type;

    public Event(Time time, EventType type) {
        this.time = time;
        this.type = type;
    }

    public Time getTime() {
        return time;
    }

    public EventType getType() {
        return type;
    }
}
