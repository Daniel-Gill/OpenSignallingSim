package net.danielgill.oss.timetable.event;

import net.danielgill.oss.block.Block;
import net.danielgill.oss.time.Time;

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
