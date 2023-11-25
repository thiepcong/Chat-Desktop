/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package component;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 *
 * @author Admin
 */
public class OptionButton extends JButton{

    private Icon disabledSelectedIcon;
    private Icon selectedIcon;
    public OptionButton() {
        setContentAreaFilled(false);
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    @Override
    public Icon getDisabledSelectedIcon() {
        return disabledSelectedIcon;
    }

    @Override
    public void setDisabledSelectedIcon(Icon disabledSelectedIcon) {
        this.disabledSelectedIcon = disabledSelectedIcon;
    }

    @Override
    public Icon getSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public void setSelectedIcon(Icon selectedIcon) {
        this.selectedIcon = selectedIcon;
    }
    

    @Override
    public void setSelected(boolean b) {
        super.setSelected(b); 
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        if(isSelected()){
            g.setColor(new Color(110,213,255));
            g.fillRect(0, getHeight()-2, getWidth(), getHeight());
        }
    }
    
    
}
