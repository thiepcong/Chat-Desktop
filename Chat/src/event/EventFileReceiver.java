/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package event;

import java.io.File;

/**
 *
 * @author Admin
 */
public interface EventFileReceiver {
    public void onReceiving(double percentage);
    public void onStartReceiving();
    public void onFinish(File file);
}
