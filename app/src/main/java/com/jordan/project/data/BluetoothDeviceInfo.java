package com.jordan.project.data;

/**
 * Created by icean on 2017/2/27.
 */

public final class BluetoothDeviceInfo {
    private String mDeviceName, mDeviceAddress, mDeviceClass;

    public BluetoothDeviceInfo(String device_name, String device_address, String device_class) {
        mDeviceName = device_name;
        mDeviceAddress = device_address;
        mDeviceClass = device_class;
    }

    public String getDeviceName() {
        return mDeviceName;
    }

    public String getDeviceAddress() {
        return mDeviceAddress;
    }

    public String getDeviceClass() {
        return mDeviceClass;
    }

    @Override
    public String toString() {
        return "BluetoothDeviceInfo{" +
                "mDeviceName='" + mDeviceName + '\'' +
                ", mDeviceAddress='" + mDeviceAddress + '\'' +
                ", mDeviceClass='" + mDeviceClass + '\'' +
                '}';
    }
}
