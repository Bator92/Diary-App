package andras.bator.diaryapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button createButton;

    @FXML
    private void createEvent(ActionEvent e){
        System.out.println("Szevasz!");
    }

    @FXML
    private void handleCreateButtonAction(ActionEvent e){
        System.out.println("Hello bello!");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
