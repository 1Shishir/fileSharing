package com.example.filesharing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;


public class SentPageCont {

    public Button connect_button;

    static boolean normalB;
    static boolean multiB;
    static boolean compB;

    int statusCount=0;
    static int nTh;

    public CheckBox normal;
    public CheckBox multi;
    public CheckBox comp;
    public TextField n=new TextField();



//    public CheckBox th1;
//    public CheckBox th2;
//    public CheckBox th3;
//    public CheckBox th4;
//    public CheckBox th5;
//    public CheckBox th6;
//    public CheckBox th7;
//    public CheckBox th8;
//    public CheckBox th9;
//    public CheckBox th10;
//
//    public Button sentAgain;
  //  public Label st;

    private Stage stage;
    boolean isSplited =false;
    public Button back;

    @FXML
    private Label fileName;
    @FXML
    private Label status;

    @FXML
    private TextField receiver_ip=new TextField();


    private compress compress=new compress();
    private indexing1 indexing=new indexing1();
    public static String receiverIP;

    File file;
    File compressedFile;
    ArrayList<byte[]> splitedFiles;
    int splitSize;
    StringBuilder log;

    public void initialize ()  {
        //ip , button image

        receiverIP=receiver_ip.getText().trim();
        ImageView imageView = new ImageView(getClass().getResource("back.png").toExternalForm());
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        back.setGraphic(imageView);
        fileName.setAlignment(Pos.BASELINE_CENTER);
        receiver_ip.setPadding(new Insets(0,0,0,20));

        normalB=normal.isSelected();
        multiB=multi.isSelected();
        compB=comp.isSelected();




     //   visibleStatus();


    }

    @FXML
    void chooseFile(ActionEvent e) throws IOException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a File\n");
        log = new StringBuilder("");

        file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            //compress and split
            fileName.setText(file.getName());

            perform();


//            if (compB) {
//                compressedFile = compress.compressFile(file);
//
//                if (compressedFile.length() != 0) {
//                    String percent = String.valueOf(String.format("%.2f", (1 - (double) compressedFile.length() / (double) file.length()) * 100));
//                    log.append("Compression Successful file size " + percent + " % reduced\n");
//                    status.setText(log.toString());
//                } else {
//                    log.append("Compression Failed\n");
//                }
//            }
//            else {
//                compressedFile=file;
//            }
//            // split
//            if(normalB){
//                nTh=1;
//            }
//            else {
//                nTh=Integer.parseInt(n.getText());
//            }
//
//            splitedFiles = indexing.index(compressedFile,nTh);
//            splitSize = indexing.totalPart;
//
//            if(splitedFiles.size()!= 0){
//
//                double size=((double) splitedFiles.get(0).length/1024.0);
//                log.append("File Splited into "+String.format("%.2f",size)+" KB "+splitSize+" parts\n");
//                status.setText(log.toString());
//                isSplited =true;
//
//
//            }else{
//                log.append("Spliting Failed\n");
//                status.setText(log.toString());
//            }
//
           }
        else {
            fileName.setText("File");
        }
        }

        void perform() throws IOException {


            if (compB) {
                compressedFile = compress.compressFile(file);

                if (compressedFile.length() != 0) {
                    String percent = String.valueOf(String.format("%.2f", (1 - (double) compressedFile.length() / (double) file.length()) * 100));
                    log.append("Compression Successful file size " + percent + " % reduced\n");
                    status.setText(log.toString());
                } else {
                    log.append("Compression Failed\n");
                }
            }
            else {
                compressedFile=file;
            }
            // split
            if(normalB){
                nTh=1;
            }
            else {
                nTh=Integer.parseInt(n.getText());
            }

            splitedFiles = indexing.index(compressedFile,nTh);
            splitSize = indexing.totalPart;

            if(splitedFiles.size()!= 0){

                double size=((double) splitedFiles.get(0).length/1024.0);
                log.append("File Splited into "+String.format("%.2f",size)+" KB "+splitSize+" parts\n");
                status.setText(log.toString());
                isSplited =true;


            }else{
                log.append("Spliting Failed\n");
                status.setText(log.toString());
            }

            statusCount++;
            if(statusCount==3){
                log=new StringBuilder("");
                statusCount=0;
            }

        }

//@FXML
//void visibleStatus(){
//        th1.setVisible(true);
//        th2.setVisible(true);
//        th3.setVisible(true);
//        th4.setVisible(true);
//        th5.setVisible(true);
//        th6.setVisible(true);
//        th7.setVisible(true);
//        th8.setVisible(true);
//        th9.setVisible(true);
//        th10.setVisible(true);
//
//        sentAgain.setVisible(true);
//        st.setVisible(true);
//
//        }

    @FXML
    void checkN(ActionEvent e){
        if((normalB=normal.isSelected())){
           multi.setSelected(false);
           multiB=false;
        }
        else {
            multi.setSelected(true);
            multiB=true;
        }


    }
    @FXML
    void checkM(ActionEvent e){
        if((multiB=multi.isSelected())){
            normal.setSelected(false);
            normalB=false;
        }
        else {
            normal.setSelected(true);
            normalB=true;
        }

    }
    @FXML
    void checkC(ActionEvent e){
      compB=comp.isSelected();

    }

    @FXML
    void connect(ActionEvent e) throws IOException {
        if(isSplited ==true){
            receiverIP=receiver_ip.getText().trim();
           // System.out.println(" S:"+System.currentTimeMillis());
            sent s=new sent();
          //  System.out.println(" E:"+System.currentTimeMillis());
            s.Client(nTh,splitedFiles,file.getName());
            if(sent.connected){
                log.append("connected with "+sent.receiver+"\n");
                status.setText(log.toString());
            }
        }
    }
    public void backBtn(ActionEvent event) throws IOException {
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(new FXMLLoader(getClass().getResource("hello-view.fxml")).load()));
    }

    public void update(ActionEvent event) throws IOException {
        if (file != null) {
        perform();
        }
    }
}
