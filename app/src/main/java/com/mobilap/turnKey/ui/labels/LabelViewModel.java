package com.mobilap.turnKey.ui.labels;

public class LabelViewModel {
    public String text;
    public int image;
    public int delete;

    public LabelViewModel(String text, int image, int delete) {

        this.text = text;
        this.image = image;
        this.delete = delete;
    }

    public String getText() {
        return text;
    }

    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
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
}

