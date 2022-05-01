package net.danielgill.oss.timetable;

import java.util.ArrayList;
import java.util.List;

import net.danielgill.oss.timetable.event.Event;

public class Schedule {
    private List<Event> events;
    private String id;

    public Schedule(String id) {
        events = new ArrayList<>();
        this.id = id;
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public Event getNext() {
        return events.get(0);
    }

    public void removeNext() {
        events.remove(0);
    }

    public String getId() {
        return id;
    }
}
