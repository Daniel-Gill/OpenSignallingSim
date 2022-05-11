package net.danielgill.oss.timetable.event;

import net.danielgill.oss.location.Location;
import net.danielgill.oss.time.Time;

public class StopEvent extends Event {
    Time depTime;
    Location loc;

    public StopEvent(Time arrTime, Time depTime, Location loc) {
        super(arrTime, EventType.STOP);
        this.depTime = depTime;
        this.loc = loc;
    }

    public Time getArrTime() {
        return this.time;
    }

    public Time getDepTime() {
        return this.depTime;
    }

    public Location getLocation() {
        return this.loc;
    }
    
}
