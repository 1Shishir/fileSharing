package com.example.filesharing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HelloController {
    @FXML
    public Button porfile;
    public Label ip;
    @FXML
    private Stage stage;


    public void initialize () throws UnknownHostException {
        //profile
        ImageView imageView = new ImageView(getClass().getResource("profile.png").toExternalForm());
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        porfile.setGraphic(imageView);

        //ip
        ip.setText(InetAddress.getLocalHost().getHostAddress());
        ip.setAlignment(Pos.BASELINE_RIGHT);


    }

    //sent button
    @FXML
    protected void sentPage(ActionEvent e) throws IOException {
        stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(new FXMLLoader(getClass().getResource("sentPage.fxml")).load()));
    }

    //receive button
    @FXML
    protected void receivePage(ActionEvent e) throws IOException {
        stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(new FXMLLoader(getClass().getResource("receivePage.fxml")).load()));
    }

}
