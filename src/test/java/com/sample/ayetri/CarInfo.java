package com.sample.ayetri;

public class CarInfo {
    private String make;
    private String model;
    private int year;

    public CarInfo(String make, String model, int year)
    {
        this.make = make;
        this.model = model;
        this.year = year;
    }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public int getYear() { return year; }
}
