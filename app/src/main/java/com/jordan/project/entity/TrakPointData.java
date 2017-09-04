package com.jordan.project.entity;

/**
 * Created by 昕 on 2017/4/21.
 */

public class TrakPointData {
    private float xCoordinate;//x坐标
    private float yCoordinate;//y坐标
    private float step;//步长
    private double time;//相对时间

    public float getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(float xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public float getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(float yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public float getStep() {
        return step;
    }

    public void setStep(float step) {
        this.step = step;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "TrakPointData{" +
                "xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate +
                ", step=" + step +
                ", time=" + time +
                '}';
    }
}

