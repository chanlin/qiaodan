package com.jordan.project.entity;

/**
 * Created by 昕 on 2017/2/21.
 */

public class PathOfParticleData {
    private double avgSpeed;
    private int count;

    public double getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "PathOfParticleData{" +
                "avgSpeed=" + avgSpeed +
                ", count=" + count +
                '}';
    }
}
