package service;

import local.JSONHandle;
import java.util.List;
import model.ModelMessage;
import model.ModelRegister;
import java.util.ArrayList;
import model.ModelAccount;
import model.ModelClient;
import model.ModelLogin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServiceUser {

    public ServiceUser() {
    }
    
    private final String ACCOUNT = "account";
    private final String USER = "user";
//    private JSONObject dataLocal;
//
//    public JSONObject getDataLocal() {
//        return dataLocal;
//    }
//
//    public void setDataLocal(JSONObject dataLocal) {
//        this.dataLocal = dataLocal;
//    }
    
    

    public ModelMessage register(ModelRegister data) {
        System.out.println(data.getUserName()+" "+data.getPassword());
        ModelMessage message = new ModelMessage();
        try {
            //  Check user exit
            if(!JSONHandle.getInstance().readJsonFile().has(ACCOUNT)) {
                message.setAction(true);
            }else{
                JSONArray listAccount = JSONHandle.getInstance().getDataLocal().getJSONArray(ACCOUNT);
                boolean check = true;
                for(int i=0;i<listAccount.length();i++){
                    JSONObject ii = listAccount.getJSONObject(i);
                    if(ii.has("userName")&&ii.get("userName").equals(data.getUserName())){
                        check = false;
                    }
                }message.setAction(check);
                if(!message.isAction())message.setMessage("Tên người dùng đã tồn tại");
            }
            if (message.isAction()) {
                int userID = 1;
                if(JSONHandle.getInstance().getDataLocal().has(ACCOUNT)) {
                    userID = JSONHandle.getInstance().getDataLocal().getJSONArray(ACCOUNT).length();
                }
                // Insert new account
                JSONHandle.getInstance().insertData( ACCOUNT, "userID", data.toJSONObject());

                // Create user from account
                ModelAccount user = new ModelAccount(userID, data.getUserName(), "", "", true);

                JSONHandle.getInstance().insertData(USER, "userID", user.toJSONObject());
                message.setAction(true);
                message.setMessage("Ok");
                message.setData(new ModelAccount(userID, data.getUserName(), "", "", true));
            }
        } catch (JSONException e) {
            message.setAction(false);
            message.setMessage("Lỗi đăng ký");
            e.printStackTrace();
        }
        return message;
    }
    
    public ModelAccount login(ModelLogin login){
        System.err.println(login.getPassword()+" "+login.getUserName());
        ModelAccount data = null;
        try{
            if(JSONHandle.getInstance().getDataLocal().has(ACCOUNT)){
                JSONArray listAccount = JSONHandle.getInstance().getDataLocal().getJSONArray(ACCOUNT);
                int userID = -1;
                for(int i=0;i<listAccount.length();i++){
                    JSONObject ii = listAccount.getJSONObject(i);
                    if(ii.has("userName")&&ii.get("userName").equals(login.getUserName())
                            &&ii.has("password")&&ii.get("password").equals(login.getPassword())){
                        userID = ii.getInt("userID");
//                        System.err.println(userID);
                        break;
                    }
                }
                JSONArray listUser = JSONHandle.getInstance().getDataLocal().getJSONArray(USER);
                for(int i=0;i<listUser.length();i++){
                    JSONObject ii = listUser.getJSONObject(i);
                    if(ii.has("userID")&&ii.getInt("userID")==userID){
//                        System.err.println(ii);
                        data = new ModelAccount(ii);
                        break;
                    }
                }   
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return data;
    }

    public List<ModelAccount> getUser(int exitUser) {
        
        List<ModelAccount> list = new ArrayList<ModelAccount>();
        try{
            JSONArray listUser = JSONHandle.getInstance().getDataLocal().getJSONArray(USER);
            for(int i=0;i<listUser.length();i++){
                JSONObject ii = listUser.getJSONObject(i);
                ModelAccount ma = new ModelAccount(ii);
                list.add(new ModelAccount(ma.getUserID(), ma.getUserName(), ma.getGender(), 
                        ma.getImage(), checkUserStatus(ma.getUserID())));
            }
        }
        catch(Exception e){
             e.printStackTrace();
        }
        return list; 
        
    }
    
    private boolean checkUserStatus(int userID) {
        List<ModelClient> clients = Service.getInstance(null).getListClient();
        for (ModelClient c : clients) {
            if (c.getUser().getUserID() == userID) {
                return true;
            }
        }
        return false;
    }
}
