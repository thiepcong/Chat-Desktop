/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import app.MessageType;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Admin
 */
public class ModelReceiveMessage {
    private int messageID;
    private int fromUserID;
    private String text;
    private String time;
    private MessageType messageType;
    private ModelReceiveImage dataImage;
    private ModelReceiveFile dataFile;

    public ModelReceiveMessage() {
    }

    public ModelReceiveMessage(int fromUserID, String text, String time, MessageType messageType) {
        this.fromUserID = fromUserID;
        this.text = text;
        this.time = time;
        this.messageType = messageType;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public ModelReceiveImage getDataImage() {
        return dataImage;
    }

    public void setDataImage(ModelReceiveImage dataImage) {
        this.dataImage = dataImage;
    }

    public ModelReceiveFile getDataFile() {
        return dataFile;
    }

    public void setDataFile(ModelReceiveFile dataFile) {
        this.dataFile = dataFile;
    }
    
    public ModelReceiveMessage(Object json) {
        JSONObject obj = (JSONObject) json;
        try {
            messageType = MessageType.toMessageType(obj.getInt("messageType"));
            fromUserID = obj.getInt("fromUserID");
            text = obj.getString("text");
            time = obj.getString("time");
            if (!obj.isNull("dataImage")) {
                dataImage = new ModelReceiveImage(obj.get("dataImage"));
            }
            if(!obj.isNull("dataFile")){
                dataFile=new ModelReceiveFile(obj.get("dataFile"));
            }
        } catch (JSONException e) {
            System.err.println(e);
        }
    }
    
    public JSONObject toJsonObject() {
        try {
            JSONObject json = new JSONObject();
            json.put("messageType", messageType.getValue());
            json.put("fromUserID", fromUserID);
            json.put("text", text);
            json.put("time", time);
            if (dataImage != null) {
                json.put("dataImage", dataImage.toJsonObject());
            }
            if (dataFile != null) {
                json.put("dataFile", dataFile.toJsonObject());
            }
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
}
