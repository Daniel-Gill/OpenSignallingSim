package net.danielgill.ros.track;

public enum Direction {
    SOUTH, NORTH, EAST, WEST;

    public Direction getOpposite() {
        if(this == NORTH) {
            return SOUTH;
        } else if(this == SOUTH) {
            return NORTH;
        } else if(this == EAST) {
            return WEST;
        } else if(this == WEST) {
            return EAST;
        }
        return null;
    }
}
