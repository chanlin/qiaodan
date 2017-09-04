package com.jordan.project.entity;

/**
 * Created by 昕 on 2017/4/18.
 */

public class MoveListData {
    private String timeYear;
    private String timeHour;
    private String id;
    private String totalDist;
    private String totalTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimeYear() {
        return timeYear;
    }

    public void setTimeYear(String timeYear) {
        this.timeYear = timeYear;
    }

    public String getTimeHour() {
        return timeHour;
    }

    public void setTimeHour(String timeHour) {
        this.timeHour = timeHour;
    }

    public String getTotalDist() {
        return totalDist;
    }

    public void setTotalDist(String totalDist) {
        this.totalDist = totalDist;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    @Override
    public String toString() {
        return "MoveListData{" +
                "timeYear='" + timeYear + '\'' +
                ", timeHour='" + timeHour + '\'' +
                ", id='" + id + '\'' +
                ", totalDist='" + totalDist + '\'' +
                ", totalTime='" + totalTime + '\'' +
                '}';
    }
}
