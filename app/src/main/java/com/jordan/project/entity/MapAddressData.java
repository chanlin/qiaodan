package com.jordan.project.entity;

/**
 * Created by 昕 on 2017/4/7.
 */

public class MapAddressData {

    private String longitude;//                longitude	经度
    private String latitude ;//                latitude	纬度
    private String province ;//                province	省份名
    private String city ;//                city	城市名
    private String district ;//                district	区县名
    private String street ;//                street	街道
    private String address ;//                address	详细地址

    @Override
    public String toString() {
        return "MapAddressData{" +
                "longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", street='" + street + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
