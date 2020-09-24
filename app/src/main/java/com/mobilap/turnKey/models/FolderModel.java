package com.mobilap.turnKey.models;

public class FolderModel {
    int folder_id;
    String folder_name;


    public FolderModel(String nom) {
        this.folder_name = nom;
    }

    public int getId() {
        return folder_id;
    }

    public void setId(int id) {
        this.folder_id = id;
    }

    public String getNom() {
        return folder_name;
    }

    public void setNom(String nom) {
        this.folder_name = nom;
    }
}
