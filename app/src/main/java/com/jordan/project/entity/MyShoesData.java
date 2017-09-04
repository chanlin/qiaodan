package com.jordan.project.entity;

/**
 * Created by 昕 on 2017/5/10.
 */

public class MyShoesData {
    private String id;
    private String shoesId;
    private String code;//编号
    private String name;//名称
    private String price;
    private String color;
    private String size;
    private String style;
    private String picture;//图片----------thumb
    private String intro;//简介
    private int type;
    //-----------------//
    private String buyTime;
    //-----------------//
    private String styleNumber;
    private String marketTime;
    private String forPeople;
    private String forSpace;
    private String forPosition;
    private String function;
    private String stat;
    private String picDesc;
    private String textDesc;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShoesId() {
        return shoesId;
    }

    public void setShoesId(String shoesId) {
        this.shoesId = shoesId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public String getStyleNumber() {
        return styleNumber;
    }

    public void setStyleNumber(String styleNumber) {
        this.styleNumber = styleNumber;
    }

    public String getMarketTime() {
        return marketTime;
    }

    public void setMarketTime(String marketTime) {
        this.marketTime = marketTime;
    }

    public String getForPeople() {
        return forPeople;
    }

    public void setForPeople(String forPeople) {
        this.forPeople = forPeople;
    }

    public String getForSpace() {
        return forSpace;
    }

    public void setForSpace(String forSpace) {
        this.forSpace = forSpace;
    }

    public String getForPosition() {
        return forPosition;
    }

    public void setForPosition(String forPosition) {
        this.forPosition = forPosition;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getPicDesc() {
        return picDesc;
    }

    public void setPicDesc(String picDesc) {
        this.picDesc = picDesc;
    }

    public String getTextDesc() {
        return textDesc;
    }

    public void setTextDesc(String textDesc) {
        this.textDesc = textDesc;
    }

    @Override
    public String toString() {
        return "MyShoesData{" +
                "id='" + id + '\'' +
                ", shoesId='" + shoesId + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", style='" + style + '\'' +
                ", picture='" + picture + '\'' +
                ", intro='" + intro + '\'' +
                ", type=" + type +
                ", buyTime='" + buyTime + '\'' +
                ", styleNumber='" + styleNumber + '\'' +
                ", marketTime='" + marketTime + '\'' +
                ", forPeople='" + forPeople + '\'' +
                ", forSpace='" + forSpace + '\'' +
                ", forPosition='" + forPosition + '\'' +
                ", function='" + function + '\'' +
                ", stat='" + stat + '\'' +
                ", picDesc='" + picDesc + '\'' +
                ", textDesc='" + textDesc + '\'' +
                '}';
    }
}
