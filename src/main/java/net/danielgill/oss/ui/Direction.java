package net.danielgill.oss.ui;

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

    public static Direction getFromString(String s) {
        if(s.equalsIgnoreCase("NORTH")) {
            return NORTH;
        } else if(s.equalsIgnoreCase("SOUTH")) {
            return SOUTH;
        } else if(s.equalsIgnoreCase("EAST")) {
            return EAST;
        } else if(s.equalsIgnoreCase("WEST")) {
            return WEST;
        }
        return null;
    }
}
