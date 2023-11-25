/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package emojiicon;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Admin
 */
public class ModelEmoji {
    private int id;
    private Icon icon;

    public ModelEmoji toSize(int x, int y) {
        return new ModelEmoji(id, new ImageIcon(((ImageIcon) icon).getImage().getScaledInstance(x, y, Image.SCALE_SMOOTH)));
    }

    public ModelEmoji() {
    }

    public ModelEmoji(int id, Icon icon) {
        this.id = id;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
    
    
}
