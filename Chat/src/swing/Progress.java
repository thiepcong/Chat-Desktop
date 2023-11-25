/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

import javax.swing.Icon;
import javax.swing.JProgressBar;

/**
 *
 * @author Admin
 */
public class Progress extends JProgressBar {
    private ProgressType progressType = ProgressType.NONE;

    public ProgressType getProgressType() {
        return progressType;
    }

    public void setProgressType(ProgressType progressType) {
        this.progressType = progressType;
        repaint();
    }
    
    public Progress() {
        setOpaque(false);
        setUI(new ProgressCircleUI(this));
    }
    
    public static  enum ProgressType{
        NONE, DOWN_FILE, CANCEL, FILE
    }
}
