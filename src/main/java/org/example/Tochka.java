package org.example;

public class Tochka {
    String number;
    double longitude;
    double latitude;

    public Tochka(String number, double longitude, double latitude) {
        this.number = number;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getNumber() {
        return number;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}