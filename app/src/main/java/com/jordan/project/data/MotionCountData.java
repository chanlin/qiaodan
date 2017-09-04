package com.jordan.project.data;

/**
 * Created by junyi on 2017/6/19.
 */

public class MotionCountData {
    private String count;
    private String id;
    private String time;//时间

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MotionCountData{" +
                "count='" + count + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
