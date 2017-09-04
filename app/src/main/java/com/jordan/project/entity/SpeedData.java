package com.jordan.project.entity;

/**
 * Created by 昕 on 2017/2/21.
 */

public class SpeedData {
    private float step;
    private float time;

    public float getStep() {
        return step;
    }

    public void setStep(float step) {
        this.step = step;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "SpeedData{" +
                "step=" + step +
                ", time=" + time +
                '}';
    }
}
