package net.danielgill.oss.location;

public class Location {
    protected String name;

    public Location(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }
        if(!(o instanceof Location) || o == null) {
            return false;
        }

        Location other = (Location) o;
        if(other.name.equalsIgnoreCase(this.name)) {
            return true;
        }
        return false;
    }

}
