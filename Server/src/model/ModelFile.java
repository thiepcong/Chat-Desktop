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
public class ModelFile {
    private int fileID;
    private String fileExtension;

    public ModelFile() {
    }

    public ModelFile(int fileID, String fileExtension) {
        this.fileID = fileID;
        this.fileExtension = fileExtension;
    }
    
    public ModelFile(Object json) {
        JSONObject obj = (JSONObject) json;
        try {
            fileID = obj.getInt("fileID");
            fileExtension = obj.getString("fileExtension");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJSONObject() {
        try{
            JSONObject obj = new JSONObject();
            obj.put("fileExtension", fileExtension);
            obj.put("blurHash", "");
            obj.put("status", false);
            return obj;
        }
        catch(JSONException e){
            e.printStackTrace();
        }return null;
    }

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }
    
}
