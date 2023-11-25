/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Admin
 */
public class ModelCall {
    private int fromUserID;
    private int toUserID;
    private String image;

    public ModelCall() {
    }

    public ModelCall(int fromUserID, int toUserID, String image) {
        this.fromUserID = fromUserID;
        this.toUserID = toUserID;
        this.image = image;
    }

    public int getFromUserID() {
        return fromUserID;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    
    public JSONObject toJsonObject() {
        try {
            JSONObject obj = new JSONObject();
            obj.put("fromUserID", fromUserID);
            obj.put("toUserID", toUserID);
            obj.put("image", image);
            return obj;
        } catch (JSONException e) {
            return null;
        }
    }
    
    public ModelCall(Object json) {
        JSONObject obj = (JSONObject) json;
        try {
            fromUserID = obj.getInt("fromUserID");
            toUserID = obj.getInt("toUserID");
            image = obj.getString("image");
        } catch (JSONException e) {
            System.err.println(e);
        }
    }
}
