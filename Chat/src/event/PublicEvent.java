/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package event;

/**
 *
 * @author Admin
 */
public class PublicEvent {
    private static PublicEvent instance;
    private EventImageView eventImageView;
    private EventChat eventChat;
    private EventLogin eventLogin;
    private EventMain eventMain;
    private EventMenuLeft eventMenuLeft;
    public static PublicEvent getInstance() {
        if(instance ==null) {
            instance = new PublicEvent();
        }
        return instance;
    }
    
    public PublicEvent() {
    }
    
    public void addEventImageView(EventImageView e){
        this.eventImageView = e;
    }
    
    public EventImageView getEventImageView(){
        return eventImageView;
    }
    
    public void addEventChat(EventChat e){
        this.eventChat = e;
    }
    
    public EventChat getEventChat(){
        return eventChat;
    }
    
    public void addEventLogin(EventLogin e) {
        this.eventLogin = e;
    }
    
    public EventLogin getEventLogin() {
        return eventLogin;
    }

    public EventMain getEventMain() {
        return eventMain;
    }

    public void addEventMain(EventMain eventMain) {
        this.eventMain = eventMain;
    }

    public EventMenuLeft getEventMenuLeft() {
        return eventMenuLeft;
    }

    public void addEventMenuLeft(EventMenuLeft eventMenuLeft) {
        this.eventMenuLeft = eventMenuLeft;
    }
}
