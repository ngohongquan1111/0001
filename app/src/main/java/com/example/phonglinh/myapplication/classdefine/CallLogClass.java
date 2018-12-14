package com.example.phonglinh.myapplication.classdefine;

public class CallLogClass  {
    private String phoneName;
    private  String phoneNumber;
    private  String callType;
    private String Duration;
    private String TimeCall;
    private String IDCall;

    public CallLogClass(String phoneName, String phoneNumber, String callType, String duration, String TimeCall,String IDCall){
        this.phoneName= phoneName;
        this.phoneNumber=phoneNumber;
        this.callType=callType;
        this.Duration=duration;
        this.TimeCall=TimeCall;
        this.IDCall = IDCall;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getTimeCall() {
        return TimeCall;
    }

    public void setTimeCall(String timeCall) {
        TimeCall = timeCall;
    }

    public String getIDCall() {
        return IDCall;
    }

    public void setIDCall(String IDCall) {
        this.IDCall = IDCall;
    }

    @Override
    public String toString() {
        return this.phoneName+this.phoneNumber+this.callType+this.Duration+this.TimeCall+this.IDCall;
    }
}
