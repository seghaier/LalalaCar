package com.example.formation.lalalacar;

/**
 * Created by Formation on 22/06/2017.
 */

public class Earthquake {

    private String mag, distance, direction, date, time, url;

    public Earthquake(String mag, String distance, String direction, String date, String time, String url) {
        this.mag = mag;
        this.distance = distance;
        this.direction = direction;
        this.date = date;
        this.time = time;
        this.url = url;
    }

    public String getMag() {
        return mag;
    }

    public void setMag(String mag) {
        this.mag = mag;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
