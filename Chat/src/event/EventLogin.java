/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package event;

import model.ModelLogin;
import model.ModelRegister;

/**
 *
 * @author Admin
 */
public interface EventLogin {
    public void login(ModelLogin data,boolean isLocal);
    public void register(ModelRegister user, EventMessage message);
    public void goRegisterView();
    public void goLoginView();
}
