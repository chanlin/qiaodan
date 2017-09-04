package com.jordan.project.entity;

/**
 * Created by 昕 on 2017/2/21.
 */

public class BluetoothData {
    private String id;
    private String createTime;
    private String name;
    private String sn;
    private String mac;//时间
    private boolean isBind;

    public boolean isBind() {
        return isBind;
    }

    public void setBind(boolean bind) {
        isBind = bind;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public String toString() {
        return "BluetoothData{" +
                "id='" + id + '\'' +
                ", createTime='" + createTime + '\'' +
                ", name='" + name + '\'' +
                ", sn='" + sn + '\'' +
                ", mac='" + mac + '\'' +
                ", isBind=" + isBind +
                '}';
    }
}
