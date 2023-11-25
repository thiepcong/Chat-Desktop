/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package event;

import model.ModelAccount;

/**
 *
 * @author Admin
 */
public interface EventMain {
    public void showLoading(boolean show);
    public void initChat();
    public void selectUser(ModelAccount user);
    public void updateUser(ModelAccount user);
}
