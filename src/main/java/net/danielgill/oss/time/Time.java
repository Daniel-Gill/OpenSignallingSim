package net.danielgill.oss.time;

public class Time {
    private int hour;
    private int minute;
    private int second;

    public Time(int hour, int minute, int second) {
        if(second >= 60) {
            minute += second / 60;
            second = second % 60;
        }
        if(minute >= 60) {
            hour += minute / 60;
            minute = minute % 60;
        }
        if(hour >= 24) {
            hour = hour % 24;
        }
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Time) {
            Time t = (Time) o;
            return t.hour == hour && t.minute == minute && t.second == second;
        }
        return false;
    }

    public Time copy() {
        return new Time(hour, minute, second);
    }

    public void addMinute(int minute) {
        this.minute += minute;
        if(this.minute >= 60) {
            this.hour += this.minute / 60;
            this.minute = this.minute % 60;
        }
    }

    public void addSecond(int second) {
        this.second += second;
        if(this.second >= 60) {
            this.minute += this.second / 60;
            this.second = this.second % 60;
        }
        if(this.minute >= 60) {
            this.hour += this.minute / 60;
            this.minute = this.minute % 60;
        }
    }

    public void removeMinutes(int minute) {
        this.minute -= minute;
        if(this.minute < 0) {
            this.hour -= 1;
            this.minute = 60 + this.minute;
        }
        if(this.second >= 60) {
            this.minute += this.second / 60;
            this.second = this.second % 60;
        }
        if(this.minute >= 60) {
            this.hour += this.minute / 60;
            this.minute = this.minute % 60;
        }
    }
    
    public void addTime(Time t) {
        this.second += t.second;
        this.minute += t.minute;
        this.hour += t.hour;
        if(this.second >= 60) {
            this.minute += this.second / 60;
            this.second = this.second % 60;
        }
        if(this.minute >= 60) {
            this.hour += this.minute / 60;
            this.minute = this.minute % 60;
        }
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public boolean isBefore(Time t) {
        if(hour < t.hour) {
            return true;
        } else if(hour == t.hour) {
            if(minute < t.minute) {
                return true;
            } else if(minute == t.minute) {
                if(second < t.second) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isAfter(Time t) {
        if(hour > t.hour) {
            return true;
        } else if(hour == t.hour) {
            if(minute > t.minute) {
                return true;
            } else if(minute == t.minute) {
                if(second > t.second) {
                    return true;
                }
            }
        }
        return false;
    }
}
