package andras.bator.diaryapp.controller;

import andras.bator.diaryapp.dao.EventDAO;
import andras.bator.diaryapp.model.EventEntity;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import tornadofx.control.DateTimePicker;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    TextField name;

    @FXML
    TextField description;

    @FXML
    TextField location;

    @FXML
    TextField participants;

    @FXML
    DateTimePicker dateTime;

    @FXML
    private void handleCreateButtonAction(ActionEvent e){
        EventEntity eventEntity = new EventEntity(name.getText(), description.getText(), dateTime.getDateTimeValue(), location.getText(), participants.getText());
        EventDAO eventDAO = new EventDAO();
        eventDAO.create(eventEntity);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
