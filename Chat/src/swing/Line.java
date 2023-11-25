/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

import java.awt.Graphics;
import javax.swing.JLabel;

/**
 *
 * @author Admin
 */
public class Line extends JLabel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(getForeground());
        g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
    }
}
