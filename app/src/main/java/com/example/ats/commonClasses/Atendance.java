package com.example.ats.commonClasses;

public class Atendance {
    String date;
    String name;
    String Email;
    String id;

    public Atendance(){

    }

    public Atendance(String date, String name, String email,String id) {
        this.date = date;
        this.name = name;
        Email = email;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
