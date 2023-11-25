/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import model.ModelAccount;
import model.ModelCall;
import view.CallVideo;

/**
 *
 * @author Admin
 */
public class ServiceVideo {
    private CallVideo videoCall;

    public ServiceVideo() {
        if(videoCall==null) videoCall = new CallVideo();
    }
    
    public void callToUser(ModelAccount user){
        if(!videoCall.isVisible())videoCall.setVisible(true);
        videoCall.callToUser(user);
    }
    
    public void receiveCallFromUser(ModelCall call){
        if(call.getImage().endsWith("endCall")){
            if(videoCall.isVisible()){
                videoCall.setVisible(false);
                videoCall.dispose();
            }
            return;
        }
        if(!videoCall.isVisible())videoCall.setVisible(true);
        videoCall.setImage(call);
    }
    
    public void receiveCallResponseFromUser(ModelCall call){
        if(call.getImage().endsWith("endCall")){
            if(videoCall.isVisible()){
                videoCall.setVisible(false);
                videoCall.dispose();
            }
            return;
        }
        if(!videoCall.isVisible())videoCall.setVisible(true);
        videoCall.setImageResponse(call);
    }
}
