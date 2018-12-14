package com.example.phonglinh.myapplication.classdefine;

public class  SmsClass {
    private String phoneNumber;
    private String time;
    private String content;
    private String idsms;

    public SmsClass(String sphoneNumber, String stime, String scontent, String idSms){

        this.phoneNumber = sphoneNumber;
        this.time = stime;
        this.content = scontent;
        this.idsms = idSms;

    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIdsms() {
        return idsms;
    }

    public void setIdsms(String idsms) {
        this.idsms = idsms;
    }

    @Override
    public String toString() {
        return this.phoneNumber+this.time+this.content+this.idsms;
    }
}
