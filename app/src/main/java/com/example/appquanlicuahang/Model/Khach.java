package com.example.appquanlicuahang.Model;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Khach {
    int id;
    String tenThoCat ;
    ArrayList<Bitmap> arrAnhCat ;

    public Khach(ArrayList<Bitmap> arrAnhCat) {
        this.arrAnhCat = arrAnhCat;
    }

    public Khach(int id, String tenThoCat, ArrayList<Bitmap> arrAnhCat) {
        this.id = id;
        this.tenThoCat = tenThoCat;
        this.arrAnhCat = arrAnhCat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenThoCat() {
        return tenThoCat;
    }

    public void setTenThoCat(String tenThoCat) {
        this.tenThoCat = tenThoCat;
    }

    public ArrayList<Bitmap> getArrAnhCat() {
        return arrAnhCat;
    }

    public void setArrAnhCat(ArrayList<Bitmap> arrAnhCat) {
        this.arrAnhCat = arrAnhCat;
    }
}

