package net.danielgill.oss.train;

import net.danielgill.oss.path.Path;
import net.danielgill.oss.time.Duration;

public class Performance {
    private int maxSpeed;

    public Performance(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Duration getTimeForPath(Path p) {
        double distance = p.getTotalDistance(); //m
        distance /= 1000; //km

        double speed = maxSpeed; //km/h

        double time = distance / speed; //h
        time *= 60; //min
        time *= 60; //sec

        return new Duration((int) time);
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }
}
