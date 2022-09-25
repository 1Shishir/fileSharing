package com.example.filesharing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ReceivePageCont {

    public Button join;
    private Stage stage;
    public Label name;
    public Label ip;
    public Label gif;
    public Button qr;
    public Button bck;
    public Button act;
    boolean click=true;

    static File dir;
    static File tempF;

    public static ArrayList<Boolean> check=null;

    receiver r=new receiver();
   // joining joining =new joining();


    public void initialize () throws UnknownHostException {
        name.setText(InetAddress.getLocalHost().getHostName());
        ip.setText(InetAddress.getLocalHost().getHostAddress());

        ImageView imageView = new ImageView(getClass().getResource("back.png").toExternalForm());
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        bck.setGraphic(imageView);

        dir=new File(System.getProperty("user.home")+"\\File Sharing\\temp\\Final File");
        dir.getParentFile().getParentFile().mkdir();
        dir.getParentFile().mkdir();
        dir.mkdir();



    }

    @FXML
    void setQr(){

    }

  static void j() throws IOException{
       joining Joining =new joining();
       decompression decompression=new decompression();
       File finalOutputFile;
        //File tempF= oining.joinFiles();
       tempF= Joining.joinFiles();

        if(joining.compressed){
            decompression.decompressed(tempF);
        }
        else {

            finalOutputFile=new File(dir.getAbsolutePath()+"\\"+tempF.getName());

            FileInputStream fileInputStream=new FileInputStream(tempF);

            FileOutputStream fileOutputStream=new FileOutputStream(finalOutputFile);
            fileOutputStream.write(fileInputStream.readAllBytes());
            fileInputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();

        }


    }

    public void back(ActionEvent event) throws IOException {

        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(new FXMLLoader(getClass().getResource("hello-view.fxml")).load()));
    }


    @FXML
   // void st() throws IOException, InterruptedException {
    void st() throws IOException, InterruptedException {

        if(click){
            act.setText("Restart");
            gif.setText("Waiting for sender..");
            click=false;

new Thread(new Runnable() {
    @Override
    public void run() {
        try {
            r.receiverServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}).start();

        }
        else {
            act.setText("Start");
            gif.setText("Server Deactivate");
            click=true;



        }


//        receiver r=new receiver();
//        r.receiverServer();






//        connection receive = new connection();
//
//        String rFile= connection.orgFName+".lzw";
//
//        File receivedFile=new File("C:\\Users\\shish\\Downloads\\tx\\"+rFile);
//
//        if(receivedFile.exists()){
//        decompression decompression=new decompression();
//        decompression.decompressed(receivedFile);
//        }
//        else {
//            System.out.println("file can't found");
//        }
    }
}
