package com.example.appquanlicuahang.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class ThoCatToc implements Serializable {
    int idTho ;
    String TenTho ;

    public ThoCatToc(JSONObject json){
        try {
            this.idTho = json.getInt("id");
            this.TenTho = json.getString("name");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public ThoCatToc(int idTho, String tenTho) {
        this.idTho = idTho;
        TenTho = tenTho;
    }

    public int getIdTho() {
        return idTho;
    }

    public void setIdTho(int idTho) {
        this.idTho = idTho;
    }

    public String getTenTho() {
        return TenTho;
    }

    public void setTenTho(String tenTho) {
        TenTho = tenTho;
    }
    public String toString(){
        return TenTho ;
    }
}
