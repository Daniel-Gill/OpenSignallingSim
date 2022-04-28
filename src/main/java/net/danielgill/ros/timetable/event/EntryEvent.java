package net.danielgill.ros.timetable.event;

import net.danielgill.ros.block.Block;
import net.danielgill.ros.time.Time;

public class EntryEvent extends Event {
    private Block block;

    public EntryEvent(Time time, Block b) {
        super(time, EventType.ENTRY);
        block = b;
    }

    public Block getBlock() {
        return block;
    }
    
}
