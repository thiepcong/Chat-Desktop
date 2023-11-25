/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import event.EventFileReceiver;
import event.EventFileSender;
import event.PublicEvent;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import model.ModelFileSender;
import model.ModelReceiveImage;
import model.ModelReceiveMessage;
import service.Service;
import swing.blurHash.BlurHash;

/**
 *
 * @author Admin
 */
public class ImageItem extends javax.swing.JLayeredPane {

    /**
     * Creates new form ImageItem
     */
    public ImageItem() {
        initComponents();
    }
    
    public void setImage(Icon image, ModelFileSender fileSender){
        progress.setVisible(false);
        fileSender.addEvent(new EventFileSender(){
            @Override
            public void onSending(double percentage) {
                progress.setVisible(true);
                progress.setValue((int) percentage);
            }

            @Override
            public void onStartSending() {
           
            }

            @Override
            public void onFinish() {
                progress.setVisible(false);
            }
        });
        pic.setImage(image);
    }
    
    public void setImage(ModelReceiveMessage message,ModelReceiveImage dataImage){
        int width = dataImage.getWidth();
        int height = dataImage.getHeight();
        int[] data = BlurHash.decode(dataImage.getImage(), width, height, 1);
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        img.setRGB(0, 0, width,height,data,0,width);
        Icon icon = new ImageIcon(img);
        pic.setImage(icon);
        progress.setVisible(false);
        try {
            Service.getInstance().addFileReceiver(message,dataImage.getFileID(), new EventFileReceiver(){
                @Override
                public void onReceiving(double percentage) {
                    progress.setVisible(true);
                    progress.setValue((int) percentage);
                }

                @Override
                public void onStartReceiving() {
               
                }

                @Override
                public void onFinish(File file) {
                    progress.setVisible(false);
                    Icon image = new ImageIcon(file.getAbsolutePath());
                    pic.setImage(image);
                    addEvent(pic,image,file.getAbsolutePath());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void setImage(String imagePath){
        try {
            progress.setVisible(false);
            Icon image = new ImageIcon(imagePath);
            pic.setImage(image);
            addEvent(pic,image,imagePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void addEvent(Component com, Icon image,String filePath){
        com.setCursor(new Cursor(Cursor.HAND_CURSOR));
        com.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e)){
                    PublicEvent.getInstance().getEventImageView().viewImage(image, filePath);
                }
            }
            
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pic = new swing.PictureBox();
        progress = new swing.Progress();

        progress.setForeground(new java.awt.Color(255, 255, 255));
        progress.setProgressType(swing.Progress.ProgressType.CANCEL);

        pic.setLayer(progress, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout picLayout = new javax.swing.GroupLayout(pic);
        pic.setLayout(picLayout);
        picLayout.setHorizontalGroup(
            picLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(picLayout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(progress, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        picLayout.setVerticalGroup(
            picLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(picLayout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addComponent(progress, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        setLayer(pic, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.PictureBox pic;
    private swing.Progress progress;
    // End of variables declaration//GEN-END:variables
}
