/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import app.MessageType;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import model.ModelAccount;
import model.ModelCall;
import model.ModelClient;
import model.ModelFile;
import model.ModelFileReceiver;
import model.ModelLogin;
import model.ModelMessage;
import model.ModelPackageSender;
import model.ModelReceiveFile;
import model.ModelReceiveImage;
import model.ModelReceiveMessage;
import model.ModelRegister;
import model.ModelRequestFile;
import model.ModelSendMessage;
/**
 *
 * @author Admin
 */
public class Service {
    private static Service instance;
    private SocketIOServer server;
    private ServiceUser serviceUser;
    private ServiceFile serviceFile;
    private ServiceMessage serviceMessage;
    private List<ModelClient> listClient;
    private JTextArea textArea;
    private final int PORT_NUMBER = 9999;
    private Map<Integer,Object> map;

    public static Service getInstance(JTextArea textArea) {
        if (instance == null) {
            instance = new Service(textArea);
        }
        return instance;
    }

    private Service(JTextArea textArea) {
        this.textArea = textArea;
        serviceUser = new ServiceUser();
        serviceFile = new ServiceFile();
        serviceMessage = new ServiceMessage();
        listClient = new ArrayList<>();
        map = new HashMap<>();
    }

    public void startServer() {
        Configuration config = new Configuration();
        config.setPort(PORT_NUMBER);
        server = new SocketIOServer(config);
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient sioc) {
                textArea.append("One client connected\n");
            }
        });
        server.addEventListener("register", ModelRegister.class, new DataListener<ModelRegister>() {
            @Override
            public void onData(SocketIOClient sioc, ModelRegister t, AckRequest ar) throws Exception {
                ModelMessage message = serviceUser.register(t);
                ar.sendAckData(message.isAction(), message.getMessage(), message.getData());
                if (message.isAction()) {
                    textArea.append("User has Register :" + t.getUserName() + " Pass :" + t.getPassword() + "\n");
                    server.getBroadcastOperations().sendEvent("list_user", (ModelAccount) message.getData());
                    addClient(sioc, (ModelAccount) message.getData());
                }
            }
        });
        server.addEventListener("login", ModelLogin.class, new DataListener<ModelLogin>() {
            @Override
            public void onData(SocketIOClient sioc, ModelLogin t, AckRequest ar) throws Exception {
                ModelAccount login = serviceUser.login(t);
                if (login != null) {
                    ar.sendAckData(true, login);
                    addClient(sioc, login);
                    userConnect(login.getUserID());
                    if(!map.isEmpty()){
                        for(int i:map.keySet()){
                            if(i==login.getUserID()){
                                List<Map<String,Object>> ds = (List<Map<String,Object>>) map.get(i);
                                for(Map<String,Object> dt:ds){
//                                    Map<String,Object> dt = (Map<String,Object>) map.get(i);
                                    ModelSendMessage data = (ModelSendMessage) dt.get("ms");
                                    ModelReceiveImage dataImage;
                                    ModelReceiveFile dataFile;
                                    if(dt.containsKey("image")) dataImage = (ModelReceiveImage) dt.get("image");
                                    else dataImage = null;
                                    if(dt.containsKey("file")) dataFile = (ModelReceiveFile) dt.get("file");
                                    else dataFile = null;
                                    sioc.sendEvent("receive_ms", 
                                        new ModelReceiveMessage(data.getMessageType(), data.getFromUserID(), data.getText(),data.getTime(), dataImage,dataFile),
                                        data.getToUserID());
                                }
                                map.remove(i);
                                break;
                            }
                        }
                    }
                } else {
                    ar.sendAckData(false);
                }
            }
        });
        server.addEventListener("list_user", Integer.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient sioc, Integer userID, AckRequest ar) throws Exception {
                try {
                    List<ModelAccount> list = serviceUser.getUser(userID);
                    sioc.sendEvent("list_user", list.toArray());
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        });
        server.addEventListener("send_to_user", ModelSendMessage.class, new DataListener<ModelSendMessage>() {
            @Override
            public void onData(SocketIOClient sioc, ModelSendMessage t, AckRequest ar) throws Exception {
                sendToClient(t,ar);
            }
        });
        server.addEventListener("send_file", ModelPackageSender.class, new DataListener<ModelPackageSender>() {
            @Override
            public void onData(SocketIOClient sioc, ModelPackageSender t, AckRequest ar) throws Exception {
                try {
                    serviceFile.receiveFile(t);
                    if (t.isFinish()) {
                        ar.sendAckData(true);
                        
                        ModelFileReceiver file = serviceFile.getFileReceivers().get(t.getFileID());
                        if(file.getMessage().getMessageType()==MessageType.IMAGE.getValue()){
                            ModelReceiveImage dataImage = new ModelReceiveImage();
                            dataImage.setFileID(t.getFileID());
                            ModelSendMessage message = serviceFile.closeFile(dataImage);
                            sendTempFileToClient(message, dataImage);
                        }else if(file.getMessage().getMessageType()==MessageType.FILE.getValue()){
                            ModelReceiveFile dataFile = new ModelReceiveFile();
                            dataFile.setFileID(t.getFileID());
                            ModelSendMessage message = serviceFile.closeFile(dataFile);
                            sendTempFileToClient(message, dataFile);
                        }

                    } else {
                        ar.sendAckData(true);
                    }
                } catch (IOException | SQLException e) {
                    ar.sendAckData(false);
                    e.printStackTrace();
                }
            }
        });
        server.addEventListener("get_file", Integer.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient sioc, Integer t, AckRequest ar) throws Exception {
                ModelFile file = serviceFile.initFile(t);
                long fileSize = serviceFile.getFileSize(t);
                ar.sendAckData(file.getFileExtension(), fileSize);
            }
        });
        server.addEventListener("request_file", ModelRequestFile.class, new DataListener<ModelRequestFile>() {
            @Override
            public void onData(SocketIOClient sioc, ModelRequestFile t, AckRequest ar) throws Exception {
                byte[] data = serviceFile.getFileData(t.getCurrentLength(), t.getFileID());
                if (data != null) {
                    ar.sendAckData(data);
                } else {
                    ar.sendAckData();
                }   
            }
        });
        server.addEventListener("call", Integer.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient sioc, Integer t, AckRequest ar) throws Exception {
                boolean isOnline = false;
                for (ModelClient c : listClient){
                    if(c.getUser().getUserID() == t){
                        isOnline = true;
                        break;
                    }
                }ar.sendAckData(isOnline);
            } 
        });
        
        server.addEventListener("image", ModelCall.class, new DataListener<ModelCall>() {
            @Override
            public void onData(SocketIOClient sioc, ModelCall t, AckRequest ar) {                
                for (ModelClient c : listClient) {
                    if (c.getUser().getUserID() == t.getToUserID()) {
                        c.getClient().sendEvent("image", t);
                        break;
                    }
                }
            }
        });
        
        server.addEventListener("image_response", ModelCall.class, new DataListener<ModelCall>() {
            @Override
            public void onData(SocketIOClient sioc, ModelCall t, AckRequest ar) {                
                for (ModelClient c : listClient) {
                    if (c.getUser().getUserID() == t.getToUserID()) {
                        c.getClient().sendEvent("image_response", t);
                        break;
                    }
                }
            }
        });
        
        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient sioc) {
                int userID = removeClient(sioc);
                if (userID != 0) {
                    //  removed
                    userDisconnect(userID);
                }
            }
        });
        server.start();
        textArea.append("Server has Start on port : " + PORT_NUMBER + "\n");
    }
    
    private void userConnect(int userID) {
        server.getBroadcastOperations().sendEvent("user_status", userID, true);
    }

    private void userDisconnect(int userID) {
        server.getBroadcastOperations().sendEvent("user_status", userID, false);
    }

    private void addClient(SocketIOClient client, ModelAccount user) {
        listClient.add(new ModelClient(client, user));
    }
    
    private void sendToClient(ModelSendMessage data, AckRequest ar) {
        int id = serviceMessage.saveModelSendMessage(data);
        data.setMessageID(id);
        if (data.getMessageType() == MessageType.IMAGE.getValue() || data.getMessageType() == MessageType.FILE.getValue()){
            try {
                ModelFile file = serviceFile.addFileReceiver(data.getText());
                serviceFile.initFile(file, data);
                ar.sendAckData(file.getFileID());
            } catch (IOException  e) {
                e.printStackTrace();
            } 
        }else{
            ar.sendAckData(true);
            boolean sendDone = false;
            for (ModelClient c : listClient) {
                if (c.getUser().getUserID() == data.getToUserID()) {
                    c.getClient().sendEvent("receive_ms",
                            new ModelReceiveMessage(data.getMessageType(),data.getFromUserID(), data.getText(),data.getTime(),null,null),
                            data.getToUserID());
                    sendDone=true;
                    break;
                }
            }if(!sendDone){
                List<Map<String,Object>> ds = new ArrayList<>();
                if(map.containsKey(data.getToUserID())) ds = (List<Map<String, Object>>) map.get(data.getToUserID());
                Map<String,Object> dt = new HashMap<String, Object>();
                dt.put("ms", data);
                ds.add(dt);
                map.put(data.getToUserID(), ds);
            }
        }     
    }
    
    private void sendTempFileToClient(ModelSendMessage data, ModelReceiveImage dataImage) {
        boolean sendDone = false;
        for (ModelClient c : listClient) {
            if (c.getUser().getUserID() == data.getToUserID()) {
                c.getClient().sendEvent("receive_ms", 
                        new ModelReceiveMessage(data.getMessageType(), data.getFromUserID(), data.getText(),data.getTime(), dataImage,null),
                        data.getToUserID());
                sendDone = true;
                break;
            }
        }if(!sendDone){
            List<Map<String,Object>> ds = new ArrayList<>();
            if(map.containsKey(data.getToUserID())) ds = (List<Map<String, Object>>) map.get(data.getToUserID());
            Map<String,Object> dt = new HashMap<>();
            dt.put("ms", data);
            dt.put("image", dataImage);
            ds.add(dt);
            map.put(data.getToUserID(), ds);
        }
    }
    
    private void sendTempFileToClient(ModelSendMessage data, ModelReceiveFile dataFile) {
        boolean sendDone = false;
        for (ModelClient c : listClient) {
            if (c.getUser().getUserID() == data.getToUserID()) {
                c.getClient().sendEvent("receive_ms", 
                        new ModelReceiveMessage(data.getMessageType(), data.getFromUserID(), data.getText(),data.getTime(),null, dataFile),
                        data.getToUserID());
                sendDone = true;
                break;
            }
        }if(!sendDone){
            List<Map<String,Object>> ds = new ArrayList<>();
            if(map.containsKey(data.getToUserID())) ds = (List<Map<String, Object>>) map.get(data.getToUserID());
            Map<String,Object> dt = new HashMap<>();
            dt.put("ms", data);
            dt.put("file", dataFile);
            ds.add(dt);
            map.put(data.getToUserID(), ds);
        }
    }

    public int removeClient(SocketIOClient client) {
        for (ModelClient d : listClient) {
            if (d.getClient() == client) {
                listClient.remove(d);
                return d.getUser().getUserID();
            }
        }
        return 0;
    }

    public List<ModelClient> getListClient() {
        return listClient;
    }
}
