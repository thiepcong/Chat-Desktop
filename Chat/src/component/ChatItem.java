/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.border.EmptyBorder;
import model.ModelFileSender;
import model.ModelReceiveFile;
import model.ModelReceiveImage;
import model.ModelReceiveMessage;

/**
 *
 * @author Admin
 */
public class ChatItem extends javax.swing.JLayeredPane {

    /**
     * Creates new form ChatItem
     */
    private JLabel label;
    public ChatItem() {
        initComponents();
        txt.setEditable(false);
        txt.setBackground(new Color(0,0,0,0));
        txt.setOpaque(false);
    }
    
    public void setUserProfile(String user) {
        JLayeredPane layer = new JLayeredPane();
        layer.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        layer.setBorder(new EmptyBorder(10,10,0,10));
        JButton cmd = new JButton(user);
        cmd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmd.setBorder(null);
        cmd.setContentAreaFilled(false);
        cmd.setFocusable(false);
        cmd.setForeground(new Color(30,121,213));
        cmd.setFont(new Font("sansserif",1,13));
        txt.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        layer.add(cmd);
        add(layer,0);
    }
    
    public void setText( String text){
        txt.setText(text);
    }
    
    public void setTime(String time) {
        JLayeredPane layer = new JLayeredPane();
        layer.setLayout(new FlowLayout(FlowLayout.RIGHT,0,0));
        layer.setBorder(new EmptyBorder(0,5,10,5));
        label = new JLabel(time);
        label.setForeground(new Color(110,110,110));
        label.setHorizontalTextPosition(JLabel.LEFT);
        layer.add(label);
        add(layer);
    }
    
    public void setImage(boolean right, ModelFileSender fileSender) {
        JLayeredPane layer = new JLayeredPane();
        layer.setLayout(new FlowLayout(right? FlowLayout.RIGHT : FlowLayout.LEFT));
        layer.setBorder(new EmptyBorder(0,5,0,5));
        ChatImage img = new ChatImage(right);
        img.addImage(fileSender);
        layer.add(img);
        add(layer);
        
    }
    
    public void setImage(boolean right, ModelReceiveMessage message,ModelReceiveImage dataImage) {
        JLayeredPane layer = new JLayeredPane();
        layer.setLayout(new FlowLayout(right? FlowLayout.RIGHT : FlowLayout.LEFT));
        layer.setBorder(new EmptyBorder(0,5,0,5));
        ChatImage img = new ChatImage(right);
        img.addImage(message,dataImage);
        layer.add(img);
        add(layer);
    }
    
    public void setImage(boolean right, String imagePath) {
        JLayeredPane layer = new JLayeredPane();
        layer.setLayout(new FlowLayout(right? FlowLayout.RIGHT : FlowLayout.LEFT));
        layer.setBorder(new EmptyBorder(0,5,0,5));
        ChatImage img = new ChatImage(right);
        img.addImage(imagePath);
        layer.add(img);
        add(layer);
    }
    
//    public void setFile(boolean right,String fileName, String size) {
//        JLayeredPane layer = new JLayeredPane();
//        layer.setLayout(new FlowLayout( FlowLayout.LEFT));
//        layer.setBorder(new EmptyBorder(0,5,0,5));
//        ChatFile chatFile = new ChatFile();
//        chatFile.setFile(right,fileName, size);
//        layer.add(chatFile);
//        add(layer);
//    }
    
    public void setFile(boolean right, ModelReceiveMessage message,ModelReceiveFile dataFile) {
        JLayeredPane layer = new JLayeredPane();
        layer.setLayout(new FlowLayout(right? FlowLayout.RIGHT : FlowLayout.LEFT));
        layer.setBorder(new EmptyBorder(0,5,0,5));
        ChatFile chatFile = new ChatFile();
        chatFile.setFile(right,message, dataFile);
        layer.add(chatFile);
        add(layer);
    }
    
    public void setFile(boolean right,ModelFileSender fileSender) {
        JLayeredPane layer = new JLayeredPane();
        layer.setLayout(new FlowLayout( FlowLayout.LEFT));
        layer.setBorder(new EmptyBorder(0,5,0,5));
        ChatFile chatFile = new ChatFile();
        chatFile.setFile(right,fileSender);
        layer.add(chatFile);
        add(layer);
    }
    
    public void setFile(boolean right,String filePath) {
        JLayeredPane layer = new JLayeredPane();
        layer.setLayout(new FlowLayout( FlowLayout.LEFT));
        layer.setBorder(new EmptyBorder(0,5,0,5));
        ChatFile chatFile = new ChatFile();
        chatFile.setFile(right,filePath);
        layer.add(chatFile);
        add(layer);
    }
    
    public void setEmoji(boolean right, Icon icon) {
        JLayeredPane layer = new JLayeredPane();
        layer.setLayout(new FlowLayout(right? FlowLayout.RIGHT : FlowLayout.LEFT));
        layer.setBorder(new EmptyBorder(0,5,0,5));
        layer.add(new JLabel(icon));
        add(layer);
    }
    
    public void sendSuccess() {
        if(label!=null) {
            label.setIcon(new ImageIcon(getClass().getResource("/assets/tick.png")));
        }
    }
    
    public void seen() {
        if(label!=null) {
            label.setIcon(new ImageIcon(getClass().getResource("/assets/double_tick.png")));
        }
    }
    
    public void hintText() {
         txt.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt = new swing.JIMSendTextPane();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        txt.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 5, 10));
        txt.setSelectionColor(new java.awt.Color(119, 188, 253));
        add(txt);
    }// </editor-fold>//GEN-END:initComponents
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 =  (Graphics2D)g;
        if(getBackground()!=null){
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        }
        super.paintComponent(g); 
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.JIMSendTextPane txt;
    // End of variables declaration//GEN-END:variables
}
