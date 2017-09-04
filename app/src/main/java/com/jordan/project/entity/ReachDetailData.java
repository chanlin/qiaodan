package com.jordan.project.entity;

import java.util.ArrayList;

/**
 * Created by 昕 on 2017/3/23.
 */

public class ReachDetailData {
     private String id;
     private String courtId;
     private String vipId;
     private String vipImg;
     private String time;
     private String duration;
     private String people;
     private String type;
     private String picture;
     private String longitude;
     private String latitude;
     private String province;
     private String city;
     private String district;
     private String street;
     private String address;
     private String mobile;
     private String contact;
     private String join;
     private String distance;
     private String remarks;
     private String createTime;
    private String slogan;

    private ArrayList<String> joinImgs=new ArrayList<String>();
    private ArrayList<String> joinMobiles=new ArrayList<String>();
    private ArrayList<String> joinNicks=new ArrayList<String>();

    public ArrayList<String> getJoinNicks() {
        return joinNicks;
    }

    public void setJoinNicks(ArrayList<String> joinNicks) {
        this.joinNicks = joinNicks;
    }

    public ArrayList<String> getJoinMobiles() {
        return joinMobiles;
    }

    public void setJoinMobiles(ArrayList<String> joinMobiles) {
        this.joinMobiles = joinMobiles;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public ArrayList<String> getJoinImgs() {
        return joinImgs;
    }

    public void setJoinImgs(ArrayList<String> joinImgs) {
        this.joinImgs = joinImgs;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourtId() {
        return courtId;
    }

    public void setCourtId(String courtId) {
        this.courtId = courtId;
    }

    public String getVipId() {
        return vipId;
    }

    public void setVipId(String vipId) {
        this.vipId = vipId;
    }

    public String getVipImg() {
        return vipImg;
    }

    public void setVipImg(String vipImg) {
        this.vipImg = vipImg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getJoin() {
        return join;
    }

    public void setJoin(String join) {
        this.join = join;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "ReachDetailData{" +
                "id='" + id + '\'' +
                ", courtId='" + courtId + '\'' +
                ", vipId='" + vipId + '\'' +
                ", vipImg='" + vipImg + '\'' +
                ", time='" + time + '\'' +
                ", duration='" + duration + '\'' +
                ", people='" + people + '\'' +
                ", type='" + type + '\'' +
                ", picture='" + picture + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", street='" + street + '\'' +
                ", address='" + address + '\'' +
                ", mobile='" + mobile + '\'' +
                ", contact='" + contact + '\'' +
                ", join='" + join + '\'' +
                ", distance='" + distance + '\'' +
                ", remarks='" + remarks + '\'' +
                ", createTime='" + createTime + '\'' +
                ", slogan='" + slogan + '\'' +
                ", joinImgs=" + joinImgs +
                ", joinMobiles=" + joinMobiles +
                '}';
    }
}
