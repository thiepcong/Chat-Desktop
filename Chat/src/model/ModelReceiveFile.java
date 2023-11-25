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
public class ModelReceiveFile {
    private int fileID;
    private String fileName;
    private long fileSize;

    public ModelReceiveFile() {
    }

    public ModelReceiveFile(Object json) {
        JSONObject obj = (JSONObject) json;
        try {
            fileID = obj.getInt("fileID");
            fileName = obj.getString("fileName");
            fileSize = obj.getInt("fileSize");
        } catch (JSONException e) {
            System.err.println(e);
        }
    }
    
    public JSONObject toJsonObject() {
        try {
            JSONObject json = new JSONObject();
            json.put("fileID", fileID);
            json.put("fileName", fileName);
            json.put("fileSize", fileSize);
            return json;
        } catch (JSONException e) {
            return null;
        }
    }

    public ModelReceiveFile(int fileID, String fileName, long fileSize) {
        this.fileID = fileID;
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
    
}
