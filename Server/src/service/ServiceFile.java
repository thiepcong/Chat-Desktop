/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import app.MessageType;
import local.JSONHandle;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import model.ModelFile;
import model.ModelFileReceiver;
import model.ModelFileSender;
import model.ModelPackageSender;
import model.ModelReceiveFile;
import model.ModelReceiveImage;
import model.ModelSendMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import swing.blurHash.BlurHash;

/**
 *
 * @author Admin
 */
public class ServiceFile {
    //  SQL
    private final String PATH_FILE = "server_data/";
    private final String FILE = "file";
    private final String MESSAGE = "message";
//    private JSONObject dataLocal;
    //  Instance
    private final Map<Integer, ModelFileReceiver> fileReceivers;
    private final Map<Integer, ModelFileSender> fileSenders;

    public Map<Integer, ModelFileReceiver> getFileReceivers() {
        return fileReceivers;
    }

    public ServiceFile() {
        this.fileReceivers = new HashMap<>();
        this.fileSenders = new HashMap<>();
//        this.dataLocal = JSONHandle.readJsonFile();
    }
    
    public ModelFile addFileReceiver(String fileExtension) {
        try{
            ModelFile data;
            int len = 0;
            if(JSONHandle.getInstance().readJsonFile().has(FILE)){
                len = JSONHandle.getInstance().getDataLocal().getJSONArray(FILE).length();
            }
            JSONHandle.getInstance().insertData(FILE, "fileID", 
                    (new ModelFile(len+1,fileExtension)).toJSONObject());
            int fileID = len+1;
            data = new ModelFile(fileID, fileExtension);
            return data;
        }catch(JSONException e){
            e.printStackTrace();
        }
    return null;
    }
    
    public void updateBlurHashDone(int fileID, String blurhash) {
        try{
            JSONArray listFile = JSONHandle.getInstance().getDataLocal().getJSONArray(FILE);
            JSONObject newObject = new JSONObject();
            for(int i=0;i<listFile.length();i++){
                JSONObject ii = listFile.getJSONObject(i);
                if(ii.has("fileID")&&ii.getInt("fileID")==fileID){
                    newObject = ii;
                    break;
                }
            }
            newObject.put("blurHash", blurhash);
            newObject.put("status", true);
            
            JSONHandle.getInstance().updateData(FILE, "fileID", fileID, newObject);
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void updateDone(int fileID) throws SQLException {
        try{
            JSONArray listFile = JSONHandle.getInstance().getDataLocal().getJSONArray(FILE);
            JSONObject newObject = new JSONObject();
            for(int i=0;i<listFile.length();i++){
                JSONObject ii = listFile.getJSONObject(i);
                if(ii.has("fileID")&&ii.getInt("fileID")==fileID){
                    newObject = ii;
                    break;
                }
            }
            newObject.put("status", true);
            
            JSONHandle.getInstance().updateData(FILE, "fileID", fileID, newObject);
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void initFile(ModelFile file, ModelSendMessage message) throws IOException {
        fileReceivers.put(file.getFileID(), new ModelFileReceiver(message, toFileObject(file)));
    }
    
    public ModelFile getFile(int fileID) throws SQLException {
        try{
            JSONArray listFile = JSONHandle.getInstance().getDataLocal().getJSONArray(FILE);
            for(int i=0;i<listFile.length();i++){
                JSONObject ii = listFile.getJSONObject(i);
                if(ii.has("fileID")&&ii.getInt("fileID")==fileID){
                    return new ModelFile(ii);
                }
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public synchronized ModelFile initFile(int fileID) throws IOException, SQLException {
        ModelFile file;
        if (!fileSenders.containsKey(fileID)) {
            file = getFile(fileID);
            fileSenders.put(fileID, new ModelFileSender(file, new File(PATH_FILE + fileID + file.getFileExtension())));
        } else {
            file = fileSenders.get(fileID).getData();
        }
        return file;
    }

    public byte[] getFileData(long currentLength, int fileID) throws IOException, SQLException {
        initFile(fileID);
        return fileSenders.get(fileID).read(currentLength);
    }

    public long getFileSize(int fileID) {
        return fileSenders.get(fileID).getFileSize();
    }

    public void receiveFile(ModelPackageSender dataPackage) throws IOException {
        if (!dataPackage.isFinish()) {
            fileReceivers.get(dataPackage.getFileID()).writeFile(dataPackage.getData());
        } else {
            ModelFileReceiver mrf = fileReceivers.get(dataPackage.getFileID());
            updateFileOfMessage(mrf.getMessage(), mrf.getFile().getAbsolutePath());
            fileReceivers.get(dataPackage.getFileID()).close();
        }
    }
    
    public void updateFileOfMessage(ModelSendMessage message,String filePath) throws IOException {
        try{
            JSONArray listFile = JSONHandle.getInstance().getDataLocal().getJSONArray(MESSAGE);
            JSONObject updatedData = new JSONObject();
            for(int i=0;i<listFile.length();i++){
                JSONObject ii = listFile.getJSONObject(i);
                if(ii.has("messageID")&&ii.getInt("messageID")==message.getMessageID()){
                    updatedData = ii;
                    break;
                }
            }
            updatedData.put("text", filePath);
            JSONHandle.getInstance().updateData(MESSAGE, "messageID", message.getMessageID(), updatedData);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public ModelSendMessage closeFile(ModelReceiveImage dataImage) throws IOException, SQLException {
        ModelFileReceiver file = fileReceivers.get(dataImage.getFileID());
        if (file.getMessage().getMessageType() == MessageType.IMAGE.getValue()) {
            //  Image file
            //  So create blurhash image string
            file.getMessage().setText("");
            String blurhash = convertFileToBlurHash(file.getFile(), dataImage);
            updateBlurHashDone(dataImage.getFileID(), blurhash);
        }else{
            updateDone(dataImage.getFileID());
        }
        fileReceivers.remove(dataImage.getFileID());
        //  Get message to send to target client when file receive finish
        return file.getMessage();
    }
    
    public ModelSendMessage closeFile(ModelReceiveFile dataFile) throws IOException, SQLException {
        ModelFileReceiver file = fileReceivers.get(dataFile.getFileID());
        if (file.getMessage().getMessageType() == MessageType.FILE.getValue()) {
            //  Image file
            //  So create blurhash image string
            file.getMessage().setText("");
            Path path = Paths.get(file.getFile().getAbsolutePath());
            long fileSize = Files.size(path);
            String fileName = path.getFileName().toString();
            dataFile.setFileName(fileName);
            dataFile.setFileSize(fileSize);
            
//            String blurhash = convertFileToBlurHash(file.getFile(), dataFile);
//            updateBlurHashDone(dataFile.getFileID(), blurhash);
        } else{
            updateDone(dataFile.getFileID());
        }
        fileReceivers.remove(dataFile.getFileID());
        //  Get message to send to target client when file receive finish
        return file.getMessage();
    }

    private String convertFileToBlurHash(File file, ModelReceiveImage dataImage) throws IOException {
        BufferedImage img = ImageIO.read(file);
        Dimension size = getAutoSize(new Dimension(img.getWidth(), img.getHeight()), new Dimension(200, 200));
        //  Convert image to small size
        BufferedImage newImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();
        g2.drawImage(img, 0, 0, size.width, size.height, null);
        String blurhash = BlurHash.encode(newImage);
        dataImage.setWidth(size.width);
        dataImage.setHeight(size.height);
        dataImage.setImage(blurhash);
        return blurhash;
    }

    private Dimension getAutoSize(Dimension fromSize, Dimension toSize) {
        int w = toSize.width;
        int h = toSize.height;
        int iw = fromSize.width;
        int ih = fromSize.height;
        double xScale = (double) w / iw;
        double yScale = (double) h / ih;
        double scale = Math.min(xScale, yScale);
        int width = (int) (scale * iw);
        int height = (int) (scale * ih);
        return new Dimension(width, height);
    }

    private File toFileObject(ModelFile file) {
        return new File(PATH_FILE + file.getFileID() + file.getFileExtension());
    }

    
}
