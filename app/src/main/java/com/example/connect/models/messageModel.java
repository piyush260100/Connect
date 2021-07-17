package com.example.connect.models;

public class messageModel {

    String uId, msgText,messageId;
    Long timeStamp;

    public messageModel(String uId, String msgText, Long timeStamp) {
        this.uId = uId;
        this.msgText = msgText;
        this.timeStamp = timeStamp;
    }

    public messageModel(String uId, String msgText) {
        this.uId = uId;
        this.msgText = msgText;
    }

    public messageModel(){}

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
