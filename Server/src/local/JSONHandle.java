/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package local;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 *
 * @author Admin
 */
public class JSONHandle {
    private static final String JSON_FILE_PATH = "server_data/server_data.json";
    
    private static JSONHandle instance;
    
    public static JSONHandle getInstance(){
        if(instance==null){
            instance = new JSONHandle();
        }return instance;
    }

    public JSONHandle() {
        dataLocal = readJsonFile();
    }
    
    public JSONObject getDataLocal(){
        return dataLocal;
    }
    
    public void setDataLocal(JSONObject data){
        dataLocal = data;
    }
    
    private JSONObject dataLocal;

    public JSONObject readJsonFile()  {
        try {
            String content =new String(Files.readAllBytes(Paths.get(JSON_FILE_PATH)));
            if(content.isEmpty())return new JSONObject();
            return  new JSONObject(content);
        } catch (IOException|JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  void writeJsonFile(JSONObject jsonObject) throws JSONException {
        try {
            FileWriter fileWriter = new FileWriter(JSON_FILE_PATH) ;
            fileWriter.write(jsonObject.toString(4)); // 4 is the number of spaces for indentation
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insertData(String arrayName,String fieldId, JSONObject newData) throws JSONException {
        JSONArray jsonArray;
        if (dataLocal.has(arrayName)) {
            jsonArray = dataLocal.getJSONArray(arrayName);
        } else {
            jsonArray = new JSONArray();
            dataLocal.put(arrayName, jsonArray);
        }
        
        if(!newData.has(fieldId)){
            int newId = jsonArray.length() + 1; 
            newData.put(fieldId, newId);
        } 
        jsonArray.put(newData);
        writeJsonFile(dataLocal);
    }

    public void updateData(String arrayName, String key, Object value, JSONObject updatedData) throws JSONException {
        JSONArray jsonArray = dataLocal.getJSONArray(arrayName);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject data = jsonArray.getJSONObject(i);
            if (data.has(key) && data.get(key).equals(value)) {
                // Update the existing data
                Iterator<String> updatedKeys = updatedData.keys();
                while (updatedKeys.hasNext()) {
                    String updatedKey = updatedKeys.next();
                    data.put(updatedKey, updatedData.get(updatedKey));
                }
                writeJsonFile(dataLocal);
                break;
            }
        }
    }
    public static void main(String[] args) throws JSONException, FileNotFoundException, IOException {
            JSONArray listAccount = JSONHandle.getInstance().getDataLocal().getJSONArray("account");
                int userID = -1;
                for(int i=0;i<listAccount.length();i++){
                    JSONObject ii = listAccount.getJSONObject(i);
                    System.err.println(ii.get("userName")+" "+ii.get("password")+" "+ii.get("userID")
                    +" "+ii.has("userName"));
                    if(ii.has("useName")&&ii.get("userName").toString().equals("to")){
                        userID = ii.getInt("userID");
                        System.err.println(userID);
                        break;
                    }
                }
        }  
}
