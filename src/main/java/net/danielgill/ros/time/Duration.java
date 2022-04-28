package net.danielgill.ros.time;

public class Duration {
    public int seconds;

    public Duration(Time start, Time end) {
        int deltaSec = Math.abs(end.getSecond() - start.getSecond());
        int deltaMin = Math.abs(end.getMinute() - start.getMinute());
        int deltaHour = Math.abs(end.getHour() - start.getHour());

        seconds = deltaSec + (deltaMin * 60) + (deltaHour * 3600);
    }

    public Duration(int seconds) {
        this.seconds = seconds;
    }

    public int getSeconds() {
        return seconds;
    }
}
