/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

/**
 *
 * @author Joris
 */
public class TimeSpan2 implements ITimeSpan {

    private ITime beginTime;
    private long timeSpan;

    public TimeSpan2(ITime bt, long ts) {
        if (bt != null && ts >= 0) {
            this.beginTime = bt;
            this.timeSpan = ts;
        }
    }

    @Override
    public ITime getBeginTime() {
        return this.beginTime;
    }

    @Override
    public ITime getEndTime() {
        if (this.beginTime != null && this.timeSpan >= Integer.MIN_VALUE && this.timeSpan <= Integer.MAX_VALUE) {
            return this.beginTime.plus((int) this.timeSpan);
        }

        return this.beginTime;
    }

    @Override
    public int length() {
        if (this.timeSpan >= Integer.MIN_VALUE && this.timeSpan <= Integer.MAX_VALUE) {
            return (int) this.timeSpan;
        }

        return 0;
    }

    @Override
    public void setBeginTime(ITime beginTime) {
        if (beginTime != null) {
            this.beginTime = beginTime;
        }
    }

    @Override
    public void setEndTime(ITime endTime) {
        if (endTime != null) {
            this.timeSpan = endTime.difference(this.beginTime);
        }
    }

    @Override
    public void move(int minutes) {
        if (minutes >= 0 && this.beginTime != null) {
            this.beginTime.plus(minutes);
            this.timeSpan += minutes;
        }
    }

    @Override
    public void changeLengthWith(int minutes) {
        if (minutes >= 0) {
            this.timeSpan = minutes;
        }
    }

    @Override
    public boolean isPartOf(ITimeSpan timeSpan) {
        return (this.getBeginTime().compareTo(timeSpan.getBeginTime()) >= 0
                && this.getEndTime().compareTo(timeSpan.getEndTime()) <= 0);
    }

    @Override
    public ITimeSpan unionWith(ITimeSpan timeSpan) {
        if (this.getBeginTime().compareTo(timeSpan.getEndTime()) > 0
                || this.getEndTime().compareTo(timeSpan.getBeginTime()) < 0) {
            return null;
        }
        
        ITime begintime, endtime;
        if (this.getBeginTime().compareTo(timeSpan.getBeginTime()) < 0) {
            begintime = this.getBeginTime();
        } else {
            begintime = timeSpan.getBeginTime();
        }

        if (this.getEndTime().compareTo(timeSpan.getEndTime()) > 0) {
            endtime = this.getEndTime();
        } else {
            endtime = timeSpan.getEndTime();
        }

        return new TimeSpan(begintime, endtime);
    }

    @Override
    public ITimeSpan intersectionWith(ITimeSpan timeSpan) {
        ITime begintime, endtime;
        if (this.getBeginTime().compareTo(timeSpan.getBeginTime()) > 0) {
            begintime = this.getBeginTime();
        } else {
            begintime = timeSpan.getBeginTime();
        }

        if (this.getEndTime().compareTo(timeSpan.getEndTime()) < 0) {
            endtime = this.getEndTime();
        } else {
            endtime = timeSpan.getEndTime();
        }

        if (begintime.compareTo(endtime) >= 0) {
            return null;
        }

        return new TimeSpan(begintime, endtime);
    }
}
