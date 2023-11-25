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
public class ModelRegister {
    private String userName;
    private String password;

    public ModelRegister() {
    }
    
    public ModelRegister(Object json) {
        if (json instanceof JSONObject obj) {
            userName = obj.optString("userName", "");
            password = obj.optString("password", "");
        } else {
            throw new IllegalArgumentException("Input must be a valid JSONObject.");
        }
    }

    public JSONObject toJSONObject() {
        try{
        JSONObject obj = new JSONObject();
        obj.put("userName", userName);
        obj.put("password", password);
        return obj;
        }
        catch(JSONException e){
            e.printStackTrace();
        }return null;
    }

    public ModelRegister(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
