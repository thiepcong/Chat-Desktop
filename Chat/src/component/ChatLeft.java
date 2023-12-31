/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import java.awt.Color;
import javax.swing.Icon;
import model.ModelReceiveFile;
import model.ModelReceiveImage;
import model.ModelReceiveMessage;

/**
 *
 * @author Admin
 */
public class ChatLeft extends javax.swing.JLayeredPane {

    /**
     * Creates new form ChatLeft
     */
    public ChatLeft() {
        initComponents();
        txt.setBackground(new Color(225,225,225));
    }
    
    public void setText(String text){
        if(text.equals("")){
            txt.hintText();
        }else{
            txt.setText(text);
        }
        
    }
    
    public void setImage(Icon... images) {
//        txt.setImage(false, images);
    }
    
    public void setImage(ModelReceiveMessage message,ModelReceiveImage dataImage) {
        txt.setImage(false,message ,dataImage);
    }
    
    public void setImage(String imagePath) {
        txt.setImage(false, imagePath);
    }
    
//    public void setFile(String fileName, String fileSize) {
//        txt.setFile(false,fileName, fileSize);
//    }
    
    public void setFile(ModelReceiveMessage message,ModelReceiveFile dataFile){
        txt.setFile(false,message,dataFile);
    }
    
    public void setFile(String filePath){
        txt.setFile(false,filePath);
    }
    
    public void setEmoji(Icon icon){
        txt.hintText();
        txt.setEmoji(false, icon);
    }
    
    public void setTime(String time) {
        txt.setTime(time);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt = new component.ChatItem();

        setLayer(txt, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(txt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private component.ChatItem txt;
    // End of variables declaration//GEN-END:variables
}
