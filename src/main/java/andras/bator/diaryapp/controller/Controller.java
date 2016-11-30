package andras.bator.diaryapp.controller;

import andras.bator.diaryapp.dao.EventDAO;
import andras.bator.diaryapp.model.AppointmentEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import jfxtras.scene.control.agenda.Agenda;
import tornadofx.control.DateTimePicker;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
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
    DateTimePicker dateTimeStart;

    @FXML
    TableView todayEventsTable;

    @FXML
    Agenda eventCalendar;

    @FXML
    private void handleCreateButtonAction(ActionEvent e){
        AppointmentEntity appointmentEntity = new AppointmentEntity(name.getText(), description.getText(), dateTimeStart.getDateTimeValue(), dateTimeStart.getDateTimeValue(), location.getText(), participants.getText());
        EventDAO eventDAO = new EventDAO();
        eventDAO.create(appointmentEntity);
    }

    @Override
    public void initialize(URL locationURL, ResourceBundle resources) {
        TableColumn dateTime2 = new TableColumn("Date");
        TableColumn name = new TableColumn("Event name");
        TableColumn description = new TableColumn("Description");
        TableColumn location = new TableColumn("Location");
        TableColumn participants = new TableColumn("Participants");

        name.setCellValueFactory(new PropertyValueFactory<AppointmentEntity,String>("name"));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setEditable(true);
        name.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<AppointmentEntity, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<AppointmentEntity, String> t) {
                        AppointmentEntity appointmentEntity =(AppointmentEntity) t.getTableView().getItems().get(t.getTablePosition().getRow());
                        appointmentEntity.setSummary(t.getNewValue());
                    }
                }
        );
        dateTime2.setCellValueFactory(new PropertyValueFactory<AppointmentEntity,String>("localDateTime"));
        description.setCellValueFactory(new PropertyValueFactory<AppointmentEntity,String>("description"));
        location.setCellValueFactory(new PropertyValueFactory<AppointmentEntity,String>("location"));
        participants.setCellValueFactory(new PropertyValueFactory<AppointmentEntity,String>("participants"));

        todayEventsTable.getColumns().addAll(dateTime2, name, description, location, participants);

        eventCalendar.appointments().addAll(new AppointmentEntity("asd", "asd", dateTimeStart.getDateTimeValue(), dateTimeStart.getDateTimeValue(), "asd", "asd"));
    }

    public void tabChanged(Event event) {
        EventDAO eventDAO = new EventDAO();
        List<AppointmentEntity> entityListToday = eventDAO.findTodayEvents();
        ObservableList<AppointmentEntity> data = FXCollections.observableArrayList(entityListToday);
        todayEventsTable.setItems(data);
    }
}
