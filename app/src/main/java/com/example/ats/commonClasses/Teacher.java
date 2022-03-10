package com.example.ats.commonClasses;

public class Teacher {
    String id,name,email,pass,pno,subject;
    public Teacher(){

    }

    public Teacher(String id, String name, String email, String pass, String pno, String subject) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.pno = pno;
        this.subject = subject;
    }

    public String getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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