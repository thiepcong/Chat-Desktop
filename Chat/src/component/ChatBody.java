 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import app.MessageType;
import emojiicon.Emoji;
import java.awt.Adjustable;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.ImageIcon;
import javax.swing.JScrollBar;
import model.ModelReceiveMessage;
import model.ModelSendMessage;
import net.miginfocom.swing.MigLayout;
import swing.ScrollBar;

/**
 *
 * @author Admin
 */
public class ChatBody extends javax.swing.JPanel {

    /**
     * Creates new form ChatBody
     */
    public ChatBody() {
        initComponents();
        init();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sp = new javax.swing.JScrollPane();
        body = new javax.swing.JPanel();

        sp.setBorder(null);
        sp.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        body.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout bodyLayout = new javax.swing.GroupLayout(body);
        body.setLayout(bodyLayout);
        bodyLayout.setHorizontalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 695, Short.MAX_VALUE)
        );
        bodyLayout.setVerticalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 452, Short.MAX_VALUE)
        );

        sp.setViewportView(body);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sp)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sp, javax.swing.GroupLayout.Alignment.TRAILING)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private javax.swing.JScrollPane sp;
    // End of variables declaration//GEN-END:variables

    private void init() {
        body.setLayout(new MigLayout("fillx","", "5[bottom]5"));
        sp.setVerticalScrollBar(new ScrollBar());
        sp.getVerticalScrollBar().setBackground(Color.WHITE);
    }
    
    public void addItemLeft(ModelReceiveMessage data){
        if(data.getMessageType()==MessageType.TEXT){
            ChatLeft item = new ChatLeft();
            item.setText(data.getText());
            item.setTime(data.getTime());
            body.add(item, "wrap, w 100::80%");
            // ::80%    set max width = 80%
        }else if(data.getMessageType()==MessageType.EMOJI){
            ChatLeft item = new ChatLeft();
            item.setEmoji(Emoji.getInstance().getImoji(Integer.parseInt(data.getText())).getIcon());
            item.setTime(data.getTime());
            body.add(item, "wrap, w 100::80%");
            // ::80%    set max width = 80%
        }else if(data.getMessageType()==MessageType.IMAGE){
            ChatLeft item = new ChatLeft();
            item.setText("");
            if(data.getText().isEmpty()) item.setImage(data,data.getDataImage());
            else item.setImage(data.getText());
            item.setTime(data.getTime());
            body.add(item, "wrap, w 100::80%");
        }else if(data.getMessageType()==MessageType.FILE){
            ChatLeft item = new ChatLeft();
            item.setText("");
            if(data.getText().isEmpty()) item.setFile(data,data.getDataFile());
            else item.setFile(data.getText());
            item.setTime(data.getTime());
            body.add(item, "wrap, w 100::80%");
        }
        
        repaint();
        revalidate();
    }
    
    public void addItemLeft(String text,String time, String user,String[] images){
        ChatLeftWithProfile item = new ChatLeftWithProfile();
        item.setText(text);
        item.setImage(images);
        item.setTime(time);
        item.setUserProfile(user);
        body.add(item, "wrap, w 100::80%");
        // ::80%    set max width = 80%
        body.repaint();
        body.revalidate();
    }
    
//    public void addItemFile(String text, String time,String user,String fileName, String size){
//        ChatLeftWithProfile item = new ChatLeftWithProfile();
//        item.setText(text);
//        item.setFile(fileName,size);
//        item.setTime(time);
//        item.setUserProfile(user);
//        body.add(item, "wrap, w 100::80%");
//        // ::80%    set max width = 80%
//        body.repaint();
//        body.revalidate();
//    }
    public void addItemRight(ModelSendMessage data) {
        ChatRight item = new ChatRight();
        item.setId(data.getMessageID());
        if(data.getMessageType()==MessageType.TEXT){
            item.setText(data.getText());
            item.setTime(data.getTime());
            body.add(item, "wrap, al right, w 100::80%");
            //  ::80% set max with 80%
        }else if(data.getMessageType()==MessageType.EMOJI){
            item.setEmoji(Emoji.getInstance().getImoji(Integer.parseInt(data.getText())).getIcon());
            item.setTime(data.getTime());
            body.add(item, "wrap, al right, w 100::80%");
            //  ::80% set max with 80%
        }else if(data.getMessageType()==MessageType.IMAGE){
            item.setText("");
            if(data.getText().isEmpty())item.setImage(data.getFile());
            else item.setImage(data.getText());
            item.setTime(data.getTime());
            body.add(item, "wrap, al right, w 100::80%");
        }
        else if(data.getMessageType()==MessageType.FILE){
            item.setText("");
            if(data.getText().isEmpty()) item.setFile(data.getFile());
            else item.setFile(data.getText());
            item.setTime(data.getTime());
            body.add(item, "wrap, al right, w 100::80%");
        }
        repaint();
        revalidate();
        scrollToBottom();
    }
    
    public void sendMessageDone(ModelSendMessage data){
        for(Component i : body.getComponents()){
            if(i instanceof ChatRight j && j.getId()==data.getMessageID()){
                j.sendSuccess();break;
            }
        }
    }
    
    public void addItemRight(String text,String[] images) {
        ChatRight item = new ChatRight();
        item.setText(text);
        item.setImage(images);
        body.add(item, "wrap, al right, w 100::80%");
        //  ::80% set max with 80%
        body.repaint();
        body.revalidate();
    }
    
//    public void addItemFileRight(String text,String fileName, String size) {
//        ChatRight item = new ChatRight();
//        item.setText(text);
//        item.setFile(fileName, size);
//        body.add(item, "wrap, al right, w 100::80%");
//        //  ::80% set max with 80%
//        body.repaint();
//        body.revalidate();
//    }
    
    public void addDate(String date){
        ChatDate item = new ChatDate();
        item.setDate(date);
        body.add(item,"wrap, al center");
//        body.
        body.repaint();
        body.revalidate();
    }
    
    public void clearChat() {
        body.removeAll();
        repaint();
        revalidate();
    }
    
    private void scrollToBottom() {
        JScrollBar verticalBar = sp.getVerticalScrollBar();
        AdjustmentListener downScroller = new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                Adjustable adjustable = e.getAdjustable();
                adjustable.setValue(adjustable.getMaximum());
                verticalBar.removeAdjustmentListener(this);
            }
        };
        verticalBar.addAdjustmentListener(downScroller);
    }
}
