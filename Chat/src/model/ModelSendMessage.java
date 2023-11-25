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
public class ModelSendMessage {
    private int messageID;
    private MessageType messageType;
    private int fromUserID;
    private int toUserID;
    private String text;
    private String time;
    private ModelFileSender file;

    public int getFromUserID() {
        return fromUserID;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public void setFromUserID(int fromUserID) {
        this.fromUserID = fromUserID;
    }

    public int getToUserID() {
        return toUserID;
    }

    public void setToUserID(int toUserID) {
        this.toUserID = toUserID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ModelFileSender getFile() {
        return file;
    }

    public void setFile(ModelFileSender file) {
        this.file = file;
    }
    public JSONObject toJsonObject() {
        try {
            JSONObject json = new JSONObject();
            json.put("messageType", messageType.getValue());
            json.put("fromUserID", fromUserID);
            json.put("toUserID", toUserID);
            json.put("time", time);
            if (messageType == MessageType.FILE || messageType == MessageType.IMAGE) {
                if(file!=null)json.put("text", file.getFileExtensions());
                else json.put("text", "");
            } else {
                json.put("text", text);
            }
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
    
    public ModelSendMessage(JSONObject json) {
        try {
            messageType = MessageType.toMessageType(json.getInt("messageType"));
            fromUserID = json.getInt("fromUserID");
            toUserID = json.getInt("toUserID");
            text = json.getString("text");
            time = json.getString("time");
        } catch (JSONException e) {
            System.err.println(e);
        }
    }

    public ModelSendMessage() {
    }

    public ModelSendMessage(MessageType messageType, int fromUserID, int toUserID, String text, String time) {
        this.messageType = messageType;
        this.fromUserID = fromUserID;
        this.toUserID = toUserID;
        this.text = text;
        this.time = time;
    }

    
}
