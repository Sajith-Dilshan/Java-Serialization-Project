package me.sajithdilshan.dep8.util;

import java.io.Serializable;

public class CustomerTM implements Serializable {
    private String id;
    private String name;
    private String address;
    private byte[] picture;


    public CustomerTM() {
    }

    public CustomerTM(String id, String name, String address, byte[] picture) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

}
