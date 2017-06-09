package com.example.management;

/**
 * Created by Chaewon on 2017-06-08.
 */

public class VetlistItem {
    private String vetName;
    private String vetAddr;
    private String vetTel;

    public VetlistItem(String vetName, String vetAddr, String vetTel){
        this.vetName= vetName;
        this.vetAddr= vetAddr;
        this.vetTel= vetTel;
    }

    public String getName(){
        return this.vetName;
    }

    public String getAddr(){
        return this.vetAddr;
    }

    public String getTel(){return this.vetTel;}
}