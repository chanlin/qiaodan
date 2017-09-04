package com.jordan.project.entity;

/**
 * Created by 昕 on 2017/4/21.
 */

public class JumpPointData {
    private float hangTime;//滞空时间
    private float jumpHegiht;//跳跃高度
    private float flipAngle;//翻转角度
    private double time;//相对时间单位
    private int flipAngleState;//翻转状态

    public float getHangTime() {
        return hangTime;
    }

    public void setHangTime(float hangTime) {
        this.hangTime = hangTime;
    }

    public float getJumpHegiht() {
        return jumpHegiht;
    }

    public void setJumpHegiht(float jumpHegiht) {
        this.jumpHegiht = jumpHegiht;
    }

    public float getFlipAngle() {
        return flipAngle;
    }

    public void setFlipAngle(float flipAngle) {
        this.flipAngle = flipAngle;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public int getFlipAngleState() {
        return flipAngleState;
    }

    public void setFlipAngleState(int flipAngleState) {
        this.flipAngleState = flipAngleState;
    }

    @Override
    public String toString() {
        return "JumpPointData{" +
                "hangTime='" + hangTime + '\'' +
                ", jumpHegiht='" + jumpHegiht + '\'' +
                ", flipAngle='" + flipAngle + '\'' +
                ", time='" + time + '\'' +
                ", flipAngleState=" + flipAngleState +
                '}';
    }
}
