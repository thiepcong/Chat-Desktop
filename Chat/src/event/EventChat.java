/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package event;

import model.ModelReceiveMessage;
import model.ModelSendMessage;

/**
 *
 * @author Admin
 */
public interface EventChat {
    public void sendMessage(ModelSendMessage data);
    public void sendMessageDone(ModelSendMessage data);
    public void receiveMessage(ModelReceiveMessage data);
}
