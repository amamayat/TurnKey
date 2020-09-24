package com.mobilap.turnKey.form;

import android.net.Uri;

public class LogoModel {
    String text;
    int image;
    Uri uri;

    public LogoModel(String text, int image) {
        this.text = text;
        this.image = image;
    }
    public LogoModel(String text, Uri uri){
        this.text = text;
        this.uri = uri;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}

