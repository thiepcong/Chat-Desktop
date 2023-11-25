/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author Admin
 */
public class ModelFileReceiver {
    private ModelSendMessage message;
    private File file;
    private RandomAccessFile accFile;//RandomAccessFile

    public ModelFileReceiver() {
    }

    public ModelFileReceiver(ModelSendMessage message, File file) throws IOException {
        this.message = message;
        this.file = file;
        this.accFile = new RandomAccessFile(file, "rw");
    }

    public ModelSendMessage getMessage() {
        return message;
    }

    public void setMessage(ModelSendMessage message) {
        this.message = message;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public RandomAccessFile getAccFile() {
        return accFile;
    }

    public void setAccFile(RandomAccessFile accFile) {
        this.accFile = accFile;
    }
    
    public synchronized long writeFile(byte[] data) throws IOException {
        accFile.seek(accFile.length());
        accFile.write(data);
        return accFile.length();
    }

    public void close() throws IOException {
        accFile.close();
    }
}
