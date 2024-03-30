package com.example.myapplication.EX5Package;

import com.example.myapplication.R;

public enum Thumbnail {
    THUMBNAIL_1("thumbnail 1", R.drawable.thumbnail1),
    THUMBNAIL_2("thumbnail 2", R.drawable.thumbnail2),
    THUMBNAIL_3("thumbnail 3", R.drawable.thumbnail3),
    THUMBNAIL_4("thumbnail 4", R.drawable.thumbnail4);
    private String name;
    private int img;

    Thumbnail(String name, int img) {
        this.name = name;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public int getImg() {
        return img;
    }
}
