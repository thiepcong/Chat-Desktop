package emojiicon;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

public class Emoji{
    private static Emoji instance;
    public static Emoji getInstance() {
        if(instance==null) instance = new Emoji();
        return instance;
    }
    
    private Emoji() {
    }

    public List<ModelEmoji> getStyle1() {
        List<ModelEmoji> list = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            list.add(new ModelEmoji(i, new ImageIcon(getClass().getResource("/emojiicon/icon/" + i + ".png"))));
        }
        return list;
    }

    public List<ModelEmoji> getStyle2() {
        List<ModelEmoji> list = new ArrayList<>();
        for (int i = 21; i <= 40; i++) {
            list.add(new ModelEmoji(i, new ImageIcon(getClass().getResource("/emojiicon/icon/" + i + ".png"))));
        }
        return list;
    }

    public ModelEmoji getImoji(int id) {
        return new ModelEmoji(id, new ImageIcon(getClass().getResource("/emojiicon/icon/" + id + ".png")));
    }
}