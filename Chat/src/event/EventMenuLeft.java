/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package event;

import java.util.List;
import model.ModelAccount;

/**
 *
 * @author Admin
 */
public interface EventMenuLeft {
    public void newUser(List<ModelAccount> users);
    public void userConnect(int userID);
    public void userDisconnect(int userID);
}
