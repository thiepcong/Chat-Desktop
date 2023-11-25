/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import local.JSONHandle;
import model.ModelAccount;
import model.ModelLogin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Admin
 */
public class ServiceUser {
    public ServiceUser() {
    }
    
    private final String USER = "user";
    private final String LIST_USER = "list_user";
    
    public void saveClient(ModelAccount data, ModelLogin login) {
        try{
            if(JSONHandle.getInstance().getDataLocal().has(USER)) return;
            JSONObject client = new JSONObject();
            client.put("account", data.toJSONObject());
            client.put("login", login.toJsonObject());
            JSONHandle.getInstance().insertData(USER, "userID", client);          
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
    
    public Map<String, Object> getClient() {
        try{
            if(!JSONHandle.getInstance().getDataLocal().has(USER)) return null;
            Map<String, Object> client = new HashMap<>();
            JSONArray listClient = JSONHandle.getInstance().getDataLocal().getJSONArray(USER);
            for(int i=0;i<listClient.length();i++){
                JSONObject ii = listClient.getJSONObject(i);
                client.put("account", new ModelAccount(ii.getJSONObject("account")));
                client.put("login", new ModelLogin(ii.getJSONObject("login")));
                client.put("list_user", getListModelAccount());
                break;
            }return client;        
        }catch(JSONException e){
            e.printStackTrace();
        }return null;
    }
    
    public void saveListModelAccount(List<ModelAccount> ds) {
        try{
            if(JSONHandle.getInstance().getDataLocal().has(LIST_USER)) return;
            for(ModelAccount data : ds){
                JSONHandle.getInstance().insertData(LIST_USER, "userID", data.toJSONObject());
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
    
    public List<ModelAccount> getListModelAccount(){
        List<ModelAccount> list  = new ArrayList<>();
        try {
            if(!JSONHandle.getInstance().readJsonFile().has(LIST_USER)) return null;
            JSONArray listAccount = JSONHandle.getInstance().getDataLocal().getJSONArray(LIST_USER);
            for(int i=0;i<listAccount.length();i++){
                JSONObject ii = listAccount.getJSONObject(i);
                list.add(new ModelAccount(ii));
            }return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
