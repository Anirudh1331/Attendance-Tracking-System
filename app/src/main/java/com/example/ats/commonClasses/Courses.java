package com.example.ats.commonClasses;

public class
Courses {
    private String course_name;
    private String teacher_name;
    private String image_uri;
    private String title;

    public Courses(String course_name, String teacher_name, String image_uri, String title) {
        this.course_name = course_name;
        this.teacher_name = teacher_name;
        this.image_uri = image_uri;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Courses(){

    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getImage_uri() {
        return image_uri;
    }

    public void setImage_uri(String image_uri) {
        this.image_uri = image_uri;
    }
}