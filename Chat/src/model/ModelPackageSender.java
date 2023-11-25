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
public class ModelPackageSender {
    private int fileID;
    private byte[] data;
    private boolean finish;

    public ModelPackageSender() {
    }

    public ModelPackageSender(int fileID, byte[] data, boolean finish) {
        this.fileID = fileID;
        this.data = data;
        this.finish = finish;
    }

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }
    
    
    
    public JSONObject toJsonObject() {
        try {
            JSONObject json = new JSONObject();
            json.put("fileID", fileID);
            json.put("data", data);
            json.put("finish", finish);
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
}
