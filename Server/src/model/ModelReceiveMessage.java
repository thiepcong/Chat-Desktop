/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class ModelReceiveMessage {
    private int messageType;
    private int fromUserID;
    private String text;
    private String time;
    private ModelReceiveImage dataImage;
    private ModelReceiveFile dataFile;

    public ModelReceiveMessage() {
    }

    public ModelReceiveMessage(int messageType, int fromUserID, String text, String time, ModelReceiveImage dataImage, ModelReceiveFile dataFile) {
        this.messageType = messageType;
        this.fromUserID = fromUserID;
        this.text = text;
        this.time = time;
        this.dataImage = dataImage;
        this.dataFile = dataFile;
    }

//    public ModelReceiveMessage(int messageType, int fromUserID, String text, String time, ModelReceiveImage dataImage) {
//        this.messageType = messageType;
//        this.fromUserID = fromUserID;
//        this.text = text;
//        this.time = time;
//        this.dataImage = dataImage;
//    }
//    
//    public ModelReceiveMessage(int messageType, int fromUserID, String text, String time, ModelReceiveFile dataFile){
//        this.messageType = messageType;
//        this.fromUserID = fromUserID;
//        this.text = text;
//        this.time = time;
//        this.dataFile = dataFile;
//    }

    public ModelReceiveImage getDataImage() {
        return dataImage;
    }

    public void setDataImage(ModelReceiveImage dataImage) {
        this.dataImage = dataImage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    

    public int getFromUserID() {
        return fromUserID;
    }

    public void setFromUserID(int fromUserID) {
        this.fromUserID = fromUserID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ModelReceiveFile getDataFile() {
        return dataFile;
    }

    public void setDataFile(ModelReceiveFile dataFile) {
        this.dataFile = dataFile;
    }
    
}
