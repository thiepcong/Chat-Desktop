/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

/**
 *
 * @author Admin
 */
public enum MessageType {
    TEXT(1), EMOJI(2), FILE(3), IMAGE(4);

    private final int value;

    public int getValue() {
        return value;
    }

    private MessageType(int value) {
        this.value = value;
    }

    public static MessageType toMessageType(int value) {
        return switch (value) {
            case 1 -> TEXT;
            case 2 -> EMOJI;
            case 3 -> FILE;
            default -> IMAGE;
        };
    }
}
