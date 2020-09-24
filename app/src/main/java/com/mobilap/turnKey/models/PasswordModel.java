package com.mobilap.turnKey.models;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PasswordModel {

    int id;
    String title;
    String user;
    String password;
    String url;
    String informations;
    String image;
    String folder;
    Date dateCreation;
    Date dateModification;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PasswordModel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInformations() {
        return informations;
    }

    public void setInformations(String informations) {
        this.informations = informations;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public String getToString(){
        String result = "";

        result += "id="+this.getId() + ", title="+this.getTitle()+", user="+this.getUser()+", password="+this.getPassword()+
                ", url="+this.getUrl()+", informations="+this.getInformations()+", image="+this.getImage()+", folder="+this.getFolder()+
                ", dateCreation="+new SimpleDateFormat("dd/MM/yyyy").format(this.getDateCreation()) + ", dateModification=" + new SimpleDateFormat("dd/MM/yyyy").format(this.getDateModification());


        return result;
    }

    public PasswordModel setPasswordToSring(String password) {
        String[]list = password.split(",");
        for(int i=0; i<list.length; i++){
            list[i] = list[i].substring(list[i].indexOf("=")+1, list[i].length());
        }
        PasswordModel psw = new PasswordModel(list[1]);
        psw.setId(Integer.parseInt(list[0]));
        psw.setUser(list[2]);
        psw.setPassword(list[3]);
        psw.setUrl(list[4]);
        psw.setInformations(list[5]);
        psw.setImage(list[6]);
        psw.setFolder(list[7]);
        try{
            psw.setDateCreation(new SimpleDateFormat("dd/MM/yyyy").parse(list[8]));
            psw.setDateModification(new SimpleDateFormat("dd/MM/yyyy").parse(list[9]));
        }catch(Exception e){

        }
        return psw;
    }

}
