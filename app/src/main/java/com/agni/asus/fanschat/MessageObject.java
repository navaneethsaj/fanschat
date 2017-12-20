package com.agni.asus.fanschat;

/**
 * Created by ASUS on 12/20/2017.
 */

public class MessageObject {

    private String text;
    private String name;
    private String photourl;

    public MessageObject(String text, String name, String photourl) {
        this.text = text;
        this.name = name;
        this.photourl = photourl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }
}
