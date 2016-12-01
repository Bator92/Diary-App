package andras.bator.diaryapp.controller;

import andras.bator.diaryapp.dao.EventDAO;
import andras.bator.diaryapp.model.AppointmentEntity;
import andras.bator.diaryapp.model.EventTableEntity;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import jfxtras.scene.control.agenda.Agenda;
import tornadofx.control.DateTimePicker;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    ComboBox languageChoose;

    @FXML
    Pane root;

    @FXML
    ChoiceBox choiceBox;

    @FXML
    ResourceBundle resourcesASD;

    @FXML
    TextField summary;

    @FXML
    TextField description;

    @FXML
    TextField location;

    @FXML
    TextField participants;

    @FXML
    CheckBox wholeDay;

    @FXML
    DateTimePicker dateTimeStart;

    @FXML
    DateTimePicker dateTimeEnd;

    @FXML
    TableView todayEventsTable;

    @FXML
    Agenda eventCalendar;

    @FXML
    private void handleCreateButtonAction(ActionEvent e) {
        AppointmentEntity appointmentEntity = new AppointmentEntity(summary.getText(), description.getText(), dateTimeStart.getDateTimeValue(), dateTimeEnd.getDateTimeValue(), wholeDay.isSelected(), location.getText(), participants.getText());
        EventDAO eventDAO = new EventDAO();
        eventDAO.create(appointmentEntity);
    }

    @Override
    public void initialize(URL locationURL, ResourceBundle resources) {
        languageChoose.setItems(FXCollections.observableArrayList("Magyar", "English"));
        languageChoose.setValue("English");

        choiceBox.setItems(FXCollections.observableArrayList("Magyar", "English"));
        choiceBox.setValue("English");
        choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Scene scene = root.getScene();
                try {
                    if (newValue.equals("Magyar")) {
                        Locale locale = new Locale("hu", "HU");
                        scene.setRoot(FXMLLoader.load(getClass().getClassLoader().getResource("view/mainView.fxml"), ResourceBundle.getBundle("language.HU", locale)));
                        choiceBox.setValue(newValue);
                        System.out.println(choiceBox.getValue());
                    } else if(newValue.equals("English")){
                        Locale locale = new Locale("en", "EN");
                        scene.setRoot(FXMLLoader.load(getClass().getClassLoader().getResource("view/mainView.fxml"), ResourceBundle.getBundle("language.EN", locale)));
                        choiceBox.setValue(newValue);
                    }
                } catch (IOException e) {
                    //TODO
                    e.printStackTrace();
                }
            }
        });

        TableColumn dateTimeStart = new TableColumn("Start date");
        TableColumn dateTimeEnd = new TableColumn("End date");
        TableColumn summary = new TableColumn("Event summary");
        TableColumn description = new TableColumn("Description");
        TableColumn location = new TableColumn("Location");
        TableColumn wholeDay = new TableColumn("Whole day");
        TableColumn participants = new TableColumn("Participants");

        summary.setCellValueFactory(new PropertyValueFactory<AppointmentEntity, String>("summary"));
        dateTimeStart.setCellValueFactory(new PropertyValueFactory<AppointmentEntity, LocalDateTime>("startTime"));
        dateTimeEnd.setCellValueFactory(new PropertyValueFactory<AppointmentEntity, LocalDateTime>("endTime"));
        description.setCellValueFactory(new PropertyValueFactory<AppointmentEntity, String>("description"));
        location.setCellValueFactory(new PropertyValueFactory<AppointmentEntity, String>("location"));
        participants.setCellValueFactory(new PropertyValueFactory<AppointmentEntity, String>("participants"));
        wholeDay.setCellValueFactory(new PropertyValueFactory<AppointmentEntity, String>("wholeDay"));

        todayEventsTable.getColumns().addAll(dateTimeStart, dateTimeEnd, summary, description, location, wholeDay, participants);
    }

    public void tabTodayChanged(Event event) {
        EventDAO eventDAO = new EventDAO();
        List<AppointmentEntity> entityListToday = eventDAO.findTodayEvents();
        ObservableList<AppointmentEntity> data = FXCollections.observableArrayList(entityListToday);
        todayEventsTable.setItems(data);
    }

    public void tabWeeklyChanged(Event event) {
        EventDAO eventDAO = new EventDAO();
        List<AppointmentEntity> entityList = eventDAO.findAll();
        eventCalendar.appointments().clear();
        eventCalendar.appointments().addAll(entityList);
    }

    public void weeklyEventsSaved(ActionEvent actionEvent) {
        EventDAO eventDAO = new EventDAO();
        List<Agenda.Appointment> appointments = eventCalendar.appointments();
        Iterator<Agenda.Appointment> iterator = appointments.iterator();
        while (iterator.hasNext()) {
            eventDAO.edit((AppointmentEntity) iterator.next());
        }
    }

    public void languageChanged(ActionEvent actionEvent) {
        Scene scene = root.getScene();
        try{
            if (languageChoose.getValue().equals("Magyar")) {
                Locale locale = new Locale("hu", "HU");
                scene.setRoot(FXMLLoader.load(getClass().getClassLoader().getResource("view/mainView.fxml"), ResourceBundle.getBundle("language.HU", locale)));
                choiceBox.setValue("Magyar");
                System.out.println(choiceBox.getValue());
            } else if(languageChoose.getValue().equals("English")){
                Locale locale = new Locale("en", "EN");
                scene.setRoot(FXMLLoader.load(getClass().getClassLoader().getResource("view/mainView.fxml"), ResourceBundle.getBundle("language.EN", locale)));
                choiceBox.setValue("English");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
