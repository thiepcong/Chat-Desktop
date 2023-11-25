/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.ArrayList;
import java.util.List;
import local.JSONHandle;
import model.ModelReceiveMessage;
import model.ModelSendMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Admin
 */
public class ServiceMessage {

    public ServiceMessage() {
    }
    
    private final String MESSAGE = "message";
    
    public int saveModelSendMessage(ModelSendMessage data) {
        int id = 0;
        try{
            if(JSONHandle.getInstance().getDataLocal().has(MESSAGE)){
                id = JSONHandle.getInstance().getDataLocal().getJSONArray(MESSAGE).length();
            }id++;
            JSONHandle.getInstance().insertData(MESSAGE, "messageID", data.toJsonObject());
            return id;
        }catch(JSONException e){
            e.printStackTrace();
        }return id;
    }
    
    public void updateMessage(ModelReceiveMessage message,String filePath){
        try{
            JSONArray listFile = JSONHandle.getInstance().getDataLocal().getJSONArray(MESSAGE);
            JSONObject updatedData = new JSONObject();
            for(int i=0;i<listFile.length();i++){
                JSONObject ii = listFile.getJSONObject(i);
                if(ii.has("messageID")&&ii.getInt("messageID")==message.getMessageID()){
                    updatedData = ii;
                    break;
                }
            }
            updatedData.put("text", filePath);
            JSONHandle.getInstance().updateData(MESSAGE, "messageID", message.getMessageID(), updatedData);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
    
    public void updateMessage(ModelSendMessage message,String filePath){
        try{
            JSONArray listFile = JSONHandle.getInstance().getDataLocal().getJSONArray(MESSAGE);
            JSONObject updatedData = new JSONObject();
            for(int i=0;i<listFile.length();i++){
                JSONObject ii = listFile.getJSONObject(i);
                if(ii.has("messageID")&&ii.getInt("messageID")==message.getMessageID()){
                    updatedData = ii;
                    break;
                }
            }
            updatedData.put("text", filePath);
            JSONHandle.getInstance().updateData(MESSAGE, "messageID", message.getMessageID(), updatedData);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
    
    public List<ModelSendMessage> getListSendMessage(int fromUserID) {
        List<ModelSendMessage> list = new ArrayList<>();
        try{
            if(!JSONHandle.getInstance().readJsonFile().has(MESSAGE)) return list;
            JSONArray listFile = JSONHandle.getInstance().getDataLocal().getJSONArray(MESSAGE);
            for(int i=0;i<listFile.length();i++){
                JSONObject ii = listFile.getJSONObject(i);
                if(ii.has("fromUserID")&&ii.getInt("fromUserID")==fromUserID){
                    list.add(new ModelSendMessage(ii));
                }
            }return list;
        }catch(JSONException e){
            e.printStackTrace();
        }return list;
    }
    
    public List<ModelReceiveMessage> getListReceiveMessage(int toUserID) {
        List<ModelReceiveMessage> list = new ArrayList<>();
        try{
            if(!JSONHandle.getInstance().readJsonFile().has(MESSAGE)) return list;
            JSONArray listFile = JSONHandle.getInstance().getDataLocal().getJSONArray(MESSAGE);
            for(int i=0;i<listFile.length();i++){
                JSONObject ii = listFile.getJSONObject(i);
                if(ii.has("toUserID")&&ii.getInt("toUserID")==toUserID){
                    list.add(new ModelReceiveMessage(ii));
                }
            }return list;
        }catch(JSONException e){
            e.printStackTrace();
        }return list;
    }
}
