package com.jordan.project.data;

/**
 * Created by junyi on 2017/3/3.
 */

public class MotionRedarData {
    private float addSpurt;
    private float lateralShearDirection;
    private float verJump;
    private float agile;
    private float explosiveForce;

    public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }

    private boolean hasData;
    private String id;
    private String title;
    private String intro;
    private String link;
    private String thumb;
    private String count;

    public float getAddSpurt() {
        return addSpurt;
    }

    public void setAddSpurt(float addSpurt) {
        this.addSpurt = addSpurt;
    }

    public float getLateralShearDirection() {
        return lateralShearDirection;
    }

    public void setLateralShearDirection(float lateralShearDirection) {
        this.lateralShearDirection = lateralShearDirection;
    }

    public float getVerJump() {
        return verJump;
    }

    public void setVerJump(float verJump) {
        this.verJump = verJump;
    }

    public float getAgile() {
        return agile;
    }

    public void setAgile(float agile) {
        this.agile = agile;
    }

    public float getExplosiveForce() {
        return explosiveForce;
    }

    public void setExplosiveForce(float explosiveForce) {
        this.explosiveForce = explosiveForce;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "MotionRedarData{" +
                "addSpurt=" + addSpurt +
                ", lateralShearDirection=" + lateralShearDirection +
                ", verJump=" + verJump +
                ", agile=" + agile +
                ", explosiveForce=" + explosiveForce +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", intro='" + intro + '\'' +
                ", link='" + link + '\'' +
                ", thumb='" + thumb + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
