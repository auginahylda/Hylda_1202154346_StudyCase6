package com.example.android.hylda_1202154346_studycase6;

import com.google.firebase.firestore.IgnoreExtraProperties;

public class db_post  {
    String gmbr, judul, deskripsi, usr, key;

    //method kosong yang diperlukan untuk melakukan pengecekan
    public DatabasePost(){

    }

    //konstruktor
    public DatabasePost(String gmbr, String judul, String deskripsi,String usr){
        this.gmbr=gmbr;
        this.judul=judul;
        this.deskripsi=deskripsi;
        this.usr=usr;
    }

    //method getter
    public String getKey(){
        return key;
    }

    public void setKey(String key){
        key = key;
    }

    public String getGmbr() {
        return gmbr;
    }

    public String getJudul() {
        return judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getUsr() {
        return usr;
    }

}
