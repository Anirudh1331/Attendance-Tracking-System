package com.example.ats.commonClasses;

public class Student {

    String id,name,email,pass,pno,subject,latitude,longitude,ats;

    public Student(String id, String name, String email, String pass, String pno, String subject, String latitude, String longitude, String ats) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.pno = pno;
        this.subject = subject;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ats = ats;
    }

    public Student(){

    }

    public String getAts() {
        return ats;
    }

    public void setAts(String ats) {
        this.ats = ats;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }
}