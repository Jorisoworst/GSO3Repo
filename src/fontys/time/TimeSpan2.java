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
    private long length;

    public TimeSpan2(ITime beginTime, long length) {
        if (beginTime != null && length <= 0) {
            this.beginTime = beginTime;
            this.length = length;
        }
    }

    @Override
    public ITime getBeginTime() {
        return this.beginTime;
    }

    @Override
    public ITime getEndTime() {
        if (this.beginTime != null && this.length >= Integer.MIN_VALUE && this.length <= Integer.MAX_VALUE) {
            return this.beginTime.plus((int) this.length);
        }

        return this.beginTime;
    }

    @Override
    public int length() {
        if (this.length >= Integer.MIN_VALUE && this.length <= Integer.MAX_VALUE) {
            return (int) this.length;
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
            this.length = endTime.difference(this.beginTime);
        }
    }

    @Override
    public void move(int minutes) {
        this.beginTime.plus(minutes);
        this.length += minutes;
    }

    @Override
    public void changeLengthWith(int minutes) {
        if (minutes <= 0) {
            throw new IllegalArgumentException("length of period must be positive");
        }

        this.length = minutes;
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
