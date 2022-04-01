package com.sayalife.avianapp.model;

public class GridCountModel {
    private int img;
    private String title;
    private int count;

    public GridCountModel(int img, String title, int count) {
        this.img = img;
        this.title = title;
        this.count = count;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
