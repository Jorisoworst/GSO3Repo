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
        if (length <= 0) {
            throw new IllegalArgumentException("begin time must be set and length"
                    + " must be more than 0");
        }

        this.beginTime = beginTime;
        this.length = length;
    }

    @Override
    public ITime getBeginTime() {
        return this.beginTime;
    }

    @Override
    public ITime getEndTime() {
        return this.beginTime.plus((int) this.length);
    }

    @Override
    public int length() {
        return (int) this.length;
    }

    @Override
    public void setBeginTime(ITime beginTime) {
        if (beginTime.compareTo(this.getEndTime()) <= 0) {
            throw new IllegalArgumentException("begin time "
                    + beginTime + " must be earlier than end time " + this.getEndTime());
        }

        this.length += this.beginTime.difference(beginTime);
        this.beginTime = beginTime;
    }

    @Override
    public void setEndTime(ITime endTime) {
        if (endTime.compareTo(this.beginTime) >= 0) {
            throw new IllegalArgumentException("end time "
                    + endTime + " must be later then begin time " + this.beginTime);
        }

        this.length = endTime.difference(this.beginTime);
    }

    @Override
    public void move(int minutes) {
        this.beginTime = this.beginTime.plus(minutes);
        this.length += minutes;
    }

    @Override
    public void changeLengthWith(int minutes) {
        if (minutes <= 0) {
            throw new IllegalArgumentException("length of period must be positive");
        }

        this.length += minutes;
    }

    @Override
    public boolean isPartOf(ITimeSpan timeSpan) {
        return (this.beginTime.compareTo(timeSpan.getBeginTime()) >= 0
                && this.getEndTime().compareTo(timeSpan.getEndTime()) <= 0);
    }

    @Override
    public ITimeSpan unionWith(ITimeSpan timeSpan) {
        if (this.beginTime.compareTo(timeSpan.getEndTime()) < 0
                || this.getEndTime().compareTo(timeSpan.getBeginTime()) > 0) {
            return null;
        }

        ITime begintime, endtime;
        if (this.beginTime.compareTo(timeSpan.getBeginTime()) > 0) {
            begintime = this.beginTime;
        } else {
            begintime = timeSpan.getBeginTime();
        }

        if (this.getEndTime().compareTo(timeSpan.getEndTime()) < 0) {
            endtime = this.getEndTime();
        } else {
            endtime = timeSpan.getEndTime();
        }

        return new TimeSpan(begintime, endtime);
    }

    @Override
    public ITimeSpan intersectionWith(ITimeSpan timeSpan) {
        ITime begintime, endtime;
        if (this.beginTime.compareTo(timeSpan.getBeginTime()) < 0) {
            begintime = this.beginTime;
        } else {
            begintime = timeSpan.getBeginTime();
        }

        if (this.getEndTime().compareTo(timeSpan.getEndTime()) < 0) {
            endtime = this.getEndTime();
        } else {
            endtime = timeSpan.getEndTime();
        }

        return new TimeSpan(begintime, endtime);
    }
}
