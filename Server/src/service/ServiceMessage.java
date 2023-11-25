/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import local.JSONHandle;
import model.ModelSendMessage;
import org.json.JSONException;

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
}
