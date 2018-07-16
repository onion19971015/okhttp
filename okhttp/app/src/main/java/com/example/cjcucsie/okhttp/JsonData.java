package com.example.cjcucsie.okhttp;

import com.google.gson.annotations.SerializedName;

public class JsonData {
    @SerializedName("pressure")
    private String pressure;
    @SerializedName("temp")
    private  String temp;

    public String getName() {
        return pressure;
    }

    public void setName(String name) {
        this.pressure = name;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
