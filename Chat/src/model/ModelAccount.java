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
public class ModelAccount {
    private int userID;
    private String userName;
    private String gender;
    private String image;
    private boolean status;

    public ModelAccount(Object json) {
        JSONObject obj = (JSONObject) json;
        try {
            userID = obj.getInt("userID");
            userName = obj.getString("userName");
            gender = obj.getString("gender");
            image = obj.getString("image");
            status = obj.getBoolean("status");
        } catch (JSONException e) {
            System.err.println(e);
        }
    }
    
    public JSONObject toJSONObject() {
        try{
            JSONObject obj = new JSONObject();
            obj.put("userID", userID);
            obj.put("userName", userName);
            obj.put("gender", gender);
            obj.put("image", image);
            obj.put("status", status);
            return obj;
        }
        catch(JSONException e){
            e.printStackTrace();
        }return null;
    }

    public ModelAccount(int userID, String userName, String gender, String image, boolean status) {
        this.userID = userID;
        this.userName = userName;
        this.gender = gender;
        this.image = image;
        this.status = status;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
