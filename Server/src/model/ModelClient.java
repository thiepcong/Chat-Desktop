/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import com.corundumstudio.socketio.SocketIOClient;

/**
 *
 * @author Admin
 */
public class ModelClient {
    private SocketIOClient client;
    private ModelAccount user;

    public ModelClient() {
    }

    public ModelClient(SocketIOClient client, ModelAccount user) {
        this.client = client;
        this.user = user;
    }

    public SocketIOClient getClient() {
        return client;
    }

    public void setClient(SocketIOClient client) {
        this.client = client;
    }

    public ModelAccount getUser() {
        return user;
    }

    public void setUser(ModelAccount user) {
        this.user = user;
    }
    
}
