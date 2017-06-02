package com.example.management;

/**
 * Created by Chaewon on 2017-05-31.
 */

public class VetlistItem {
    private String VetName;
    private String VetAddress;
    private String VetTel;

    //constructer of the VetlistItem class.
    /*public VetlistItem(String name) {
        super();
        this.VetName = name;
        }
        */

    public String getName() {
        return VetName;
    } //return the name of the veterinary

    public void setName(String name) {
        this.VetName = name;
    }// store the name of the veterinary

    public String getAddr(){
        return VetAddress;
    }

    public void setAddr(String addr){
        this.VetAddress= addr;
    }

    public String getTel(){
        return VetTel;
    }

    public void setTel(String tel){
        this.VetTel= tel;
    }
}
