package com.jordan.project.data;

/**
 * Created by junyi on 2017/5/8.
 */

public class TrainListData {
    private String id;
    private String tdId;
    private String title;
    private String type;
    private String position;
    private String intro;
    private String thumb;
    private String link;
    private String content;
    private String count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTdId() {
        return tdId;
    }

    public void setTdId(String tdId) {
        this.tdId = tdId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "TrainListData{" +
                "id='" + id + '\'' +
                ", tdId='" + tdId + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", position='" + position + '\'' +
                ", intro='" + intro + '\'' +
                ", thumb='" + thumb + '\'' +
                ", link='" + link + '\'' +
                ", content='" + content + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
