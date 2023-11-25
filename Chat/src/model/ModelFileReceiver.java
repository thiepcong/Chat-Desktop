/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import event.EventFileReceiver;
import io.socket.client.Ack;
import io.socket.client.Socket;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import org.json.JSONException;
import service.Service;

/**
 *
 * @author Admin
 */
public class ModelFileReceiver {
    private ModelReceiveMessage message;
    private int fileID;
    private File file;
    private long fileSize;
    private String fileExtention;
    private RandomAccessFile accFile;
    private Socket socket;
    private EventFileReceiver event;
    private final String PATH_FILE = "client_data/";

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public File getFile() {
        return file;
    }

    public ModelReceiveMessage getMessage() {
        return message;
    }

    public void setMessage(ModelReceiveMessage message) {
        this.message = message;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileExtention() {
        return fileExtention;
    }

    public void setFileExtention(String fileExtention) {
        this.fileExtention = fileExtention;
    }

    public RandomAccessFile getAccFile() {
        return accFile;
    }

    public void setAccFile(RandomAccessFile accFile) {
        this.accFile = accFile;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public EventFileReceiver getEvent() {
        return event;
    }

    public void setEvent(EventFileReceiver event) {
        this.event = event;
    }

    public ModelFileReceiver() {
    }

    public ModelFileReceiver(ModelReceiveMessage message,int fileID, Socket socket, EventFileReceiver event) {
        this.message = message;
        this.fileID = fileID;
        this.socket = socket;
        this.event = event;
    }
    
    public void initReceive() {
        socket.emit("get_file", fileID, new Ack() {
            @Override
            public void call(Object... os) {
                if (os.length > 0) {
                    try {
                        fileExtention = os[0].toString();
                        fileSize = (int) os[1];
                        file = new File(PATH_FILE + fileID + fileExtention);
                        accFile = new RandomAccessFile(file, "rw");
                        event.onStartReceiving();
                        //  start save file
                        startSaveFile();
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void startSaveFile() throws IOException, JSONException {
        ModelRequestFile data = new ModelRequestFile(fileID, accFile.length());
        socket.emit("request_file", data.toJsonObject(), new Ack() {
            @Override
            public void call(Object... os) {
                try {
                    if (os.length > 0) {
                        byte[] b = (byte[]) os[0];
                        writeFile(b);
                        event.onReceiving(getPercentage());
                        startSaveFile();
                    } else {
                        close();
                        event.onFinish(new File(PATH_FILE + fileID + fileExtention));
                        //  remove list
                        Service.getInstance().fileReceiveFinish(message,PATH_FILE + fileID + fileExtention,ModelFileReceiver.this);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private synchronized long writeFile(byte[] data) throws IOException {
        accFile.seek(accFile.length());
        accFile.write(data);
        return accFile.length();
    }

    public double getPercentage() throws IOException {
        double percentage;
        long filePointer = accFile.getFilePointer();
        percentage = filePointer * 100 / fileSize;
        return percentage;
    }

    public void close() throws IOException {
        accFile.close();
    }
}
