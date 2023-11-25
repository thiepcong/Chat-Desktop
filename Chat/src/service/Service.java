package service;

import com.github.sarxos.webcam.Webcam;
import event.EventFileReceiver;
import event.PublicEvent;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import local.JSONHandle;
import model.ModelAccount;
import model.ModelCall;
import model.ModelFileReceiver;
import model.ModelFileSender;
import model.ModelLogin;
import model.ModelReceiveMessage;
import model.ModelSendMessage;

public class Service {

    private static Service instance;
    private Socket client;
    private final int PORT_NUMBER = 9999;
    private final String IP = "localhost";
    private ModelAccount user;
    private ServiceMessage serviceMessage;
    private ServiceUser serviceUser;
    private ServiceVideo serviceVideo;
    private List<ModelFileSender> fileSender;
    private List<ModelFileReceiver> fileReceiver;

    public static Service getInstance() {
        if (instance == null) {
            instance = new Service();
        }
        return instance;
    }

    private Service() {
        fileSender = new ArrayList<>();
        fileReceiver = new ArrayList<>();
        serviceMessage = new ServiceMessage();
        serviceUser = new ServiceUser();
        serviceVideo = new ServiceVideo();
    }

    public void startServer() {
        try {
            client = IO.socket("http://" + IP + ":" + PORT_NUMBER);
            client.on(Socket.EVENT_RECONNECT, new Emitter.Listener(){
                @Override
                public void call(Object... os) {
//                    System.err.println("reconnect"+Arrays.deepToString(os));
//                    if(user!=null){
//                        Object dataLocal = JSONHandle.getInstance().getDataLocal();
//                        Map<Integer,Object> map = new HashMap<>();
//                        map.put(user.getUserID(), dataLocal);
//                        client.emit("sycn", map);
//                    }
                    
                }
            });
            client.on("list_user", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    //  list user
                    List<ModelAccount> users = new ArrayList<>();
                    for (Object o : os) {
                        ModelAccount u = new ModelAccount(o);
                        if (u.getUserID() != user.getUserID()) {
                            users.add(u);
                        }
                    }
                    saveListModelAccount(users);
                    PublicEvent.getInstance().getEventMenuLeft().newUser(users);
                }
            });
            client.on("user_status", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    int userID = (Integer) os[0];
                    boolean status = (Boolean) os[1];
                    if (status) {
                        //  connect
                        PublicEvent.getInstance().getEventMenuLeft().userConnect(userID);
                    } else {
                        //  disconnect
                        PublicEvent.getInstance().getEventMenuLeft().userDisconnect(userID);
                    }
                }
            });
            client.on("receive_ms", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    ModelReceiveMessage message = new ModelReceiveMessage(os[0]);
                    int toUserID = (int) os[1];
                    int messageID = saveModelSendMessage(new ModelSendMessage(message.getMessageType(),
                            message.getFromUserID(),toUserID,message.getText(),message.getTime()));
                    message.setMessageID(messageID);
                    PublicEvent.getInstance().getEventChat().receiveMessage(message);
                }
            });
            client.on("image", new Emitter.Listener(){
                @Override
                public void call(Object... os) {
                    ModelCall call = new ModelCall(os[0]);
                    serviceVideo.receiveCallFromUser(call);
                }
            });
            client.on("image_response", new Emitter.Listener(){
                @Override
                public void call(Object... os) {
                    ModelCall call = new ModelCall(os[0]);
                    serviceVideo.receiveCallResponseFromUser(call);
                }
            });
            client.open();
        } catch (URISyntaxException e) {
            error(e);
        }
    }
    
    public Map<String,Object> getMessageByUser(int userID){
        Map<String,Object> map = new HashMap<>();
        map.put("send", serviceMessage.getListSendMessage(userID));
        map.put("receive", serviceMessage.getListReceiveMessage(userID));
        return map;
    }
    
    public ModelFileSender addFile(File file, ModelSendMessage message) throws IOException {
        ModelFileSender data = new ModelFileSender(file, client, message);
        message.setFile(data);
        int id = serviceMessage.saveModelSendMessage(message);
        message.setMessageID(id);
        fileSender.add(data);
        //  For send file one by one
        if (fileSender.size() == 1) {
            data.initSend();
        }
        return data;
    }

    public void fileSendFinish(ModelSendMessage message,String filePath,ModelFileSender data) throws IOException {
        fileSender.remove(data);
        serviceMessage.updateMessage(message, filePath);
        if (!fileSender.isEmpty()) {
            //  Start send new file when old file sending finish
            fileSender.get(0).initSend();
        }
    }
    
    public void fileReceiveFinish(ModelReceiveMessage message,String filePath,ModelFileReceiver data) throws IOException {
        fileReceiver.remove(data);
        serviceMessage.updateMessage(message,filePath);
        if (!fileReceiver.isEmpty()) {
            fileReceiver.get(0).initReceive();
        }
    }

    public void addFileReceiver(ModelReceiveMessage message,int fileID, EventFileReceiver event) throws IOException {
        ModelFileReceiver data = new ModelFileReceiver(message,fileID, client, event);
        fileReceiver.add(data);
        if (fileReceiver.size() == 1) {
            data.initReceive();
        }
    }
    
    public int saveModelSendMessage(ModelSendMessage data){
        return serviceMessage.saveModelSendMessage(data);
    }
    
    public void saveListModelAccount(List<ModelAccount> ds){
        serviceUser.saveListModelAccount(ds);
    }
    
    public void saveClient(ModelAccount data, ModelLogin login){
        serviceUser.saveClient(data, login);
    }
    
    public Map<String, Object> getClientLocal(){
        return serviceUser.getClient();
    }

    public Socket getClient() {
        return client;
    }
    
    public ModelAccount getUser() {
        return user;
    }

    public void setUser(ModelAccount user) {
        this.user = user;
    }

    private void error(Exception e) {
        System.err.println(e);
    }
    
    public void callToUser(ModelAccount user){
        serviceVideo.callToUser(user);
    }
}
