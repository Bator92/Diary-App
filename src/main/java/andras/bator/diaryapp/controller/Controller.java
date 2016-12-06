package andras.bator.diaryapp.controller;

import andras.bator.diaryapp.Main;
import andras.bator.diaryapp.dao.EventDAO;
import andras.bator.diaryapp.model.AppointmentEntity;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import jfxtras.scene.control.agenda.Agenda;
import tornadofx.control.DateTimePicker;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.logging.Logger;

public class Controller implements Initializable {
    private final static Logger LOGGER = Logger.getLogger(Controller.class.getName());

    @FXML
    ComboBox eventChangesCombo;

    @FXML
    ComboBox languageChooseCombo;

    @FXML
    Pane root;

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
    TableView eventsTable;

    @FXML
    Agenda eventCalendar;

    public static List<AppointmentEntity> appointmentsToDelete;

    private static final String HUNGARIAN = "Magyar";
    private static final String ENGLISH = "English";

    @FXML
    private void handleCreateButtonAction(ActionEvent e) {
        AppointmentEntity appointmentEntity = new AppointmentEntity(summary.getText(), description.getText(), dateTimeStart.getDateTimeValue(), dateTimeEnd.getDateTimeValue(), wholeDay.isSelected(), location.getText(), participants.getText());
        EventDAO eventDAO = new EventDAO();
        eventDAO.create(appointmentEntity);
    }

    private ResourceBundle resourceBundle;

    @Override
    public void initialize(URL locationURL, ResourceBundle resources) {
        resourceBundle = resources;

        appointmentsToDelete = new ArrayList<>();
        languageChooseCombo.setItems(FXCollections.observableArrayList(HUNGARIAN, ENGLISH));
        languageChooseCombo.setValue(Main.language);

        eventChangesCombo.setItems(FXCollections.observableArrayList(resources.getString("allEvents"), resources.getString("todayEvents")));
        eventChangesCombo.setValue(resources.getString("allEvents"));

        List<TableColumn> tableColumns = createTableColumns(resources);

        eventsTable.getColumns().addAll(tableColumns);
        eventsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        eventsTable.setEditable(true);
    }

    public void tabTodayChanged(Event event) {
        eventsChanged(new ActionEvent());
    }

    public void eventsChanged(ActionEvent actionEvent) {

        List<AppointmentEntity> entityList = getAppointmentEntities();
        ObservableList<AppointmentEntity> data = FXCollections.observableArrayList(entityList);
        eventsTable.setItems(data);
    }

    private List<AppointmentEntity> getAppointmentEntities() {
        EventDAO eventDAO = new EventDAO();
        List<AppointmentEntity> entityList = new ArrayList<>();
        if (eventChangesCombo.getValue().equals(resourceBundle.getString("allEvents"))) {
            entityList = eventDAO.findAll();
        } else if (eventChangesCombo.getValue().equals(resourceBundle.getString("todayEvents"))) {
            entityList = eventDAO.findTodayEvents();
        }
        return entityList;
    }

    public void saveToFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(resourceBundle.getString("saveToFile"));
        fileChooser.setInitialFileName("appointments.txt");
        File file = fileChooser.showSaveDialog(Main.getStage());
        if (file != null) {
            try {
                PrintWriter writer = new PrintWriter(file, "UTF-8");
                List<AppointmentEntity> entityList = getAppointmentEntities();
                for (AppointmentEntity entity: entityList) {
                    writer.println(entity);
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    public void eventsSaved(ActionEvent actionEvent) {
        EventDAO eventDAO = new EventDAO();
        List<AppointmentEntity> appointmentEntities = eventsTable.getItems();
        for (AppointmentEntity appointment : appointmentEntities) {
            eventDAO.edit(appointment);
        }
        for (AppointmentEntity appointment : appointmentsToDelete) {
            eventDAO.remove(appointment);
        }
    }

    public void languageChanged(ActionEvent actionEvent) {
        Scene scene = root.getScene();
        try {
            if (languageChooseCombo.getValue().equals(HUNGARIAN)) {
                Locale locale = new Locale("hu", "HU");
                Main.language = HUNGARIAN;
                scene.setRoot(FXMLLoader.load(getClass().getClassLoader().getResource("view/mainView.fxml"), ResourceBundle.getBundle("language.HU", locale)));
            } else if (languageChooseCombo.getValue().equals(ENGLISH)) {
                Locale locale = new Locale("en", "EN");
                Main.language = ENGLISH;
                scene.setRoot(FXMLLoader.load(getClass().getClassLoader().getResource("view/mainView.fxml"), ResourceBundle.getBundle("language.EN", locale)));
            }
        } catch (IOException e) {
            LOGGER.severe("IOException occured :" + e);
        }

    }

    private List<TableColumn> createTableColumns(ResourceBundle resources) {
        List<TableColumn> tableColumns = new ArrayList<>();
        TableColumn dateTimeStart = new TableColumn<AppointmentEntity, LocalDateTime>(resources.getString("startDate"));
        tableColumns.add(dateTimeStart);
        TableColumn dateTimeEnd = new TableColumn<AppointmentEntity, LocalDateTime>(resources.getString("endDate"));
        tableColumns.add(dateTimeEnd);
        TableColumn summary = new TableColumn(resources.getString("summary"));
        tableColumns.add(summary);
        TableColumn description = new TableColumn(resources.getString("description"));
        tableColumns.add(description);
        TableColumn location = new TableColumn(resources.getString("location"));
        tableColumns.add(location);
        TableColumn wholeDay = new TableColumn(resources.getString("wholeDay"));
        tableColumns.add(wholeDay);
        TableColumn participants = new TableColumn(resources.getString("participants"));
        tableColumns.add(participants);
        TableColumn delete = new TableColumn();
        tableColumns.add(delete);

        Callback<TableColumn, TableCell> cellFactory = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new EditingCell();
            }
        };

        summary.setCellValueFactory(new PropertyValueFactory<AppointmentEntity, String>("summary"));
        summary.setCellFactory(cellFactory);
        summary.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<AppointmentEntity, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<AppointmentEntity, String> t) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setSummary(t.getNewValue());
            }
        });
        //summary.setEditable(true);
        dateTimeStart.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AppointmentEntity, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AppointmentEntity, String> param) {
                LocalDate date = param.getValue().getStartLocalDateTime().toLocalDate();
                LocalTime time = param.getValue().getStartLocalDateTime().toLocalTime();
                return new ReadOnlyObjectWrapper<String>(date.toString() + " " + time.toString());

            }
        });
        dateTimeEnd.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AppointmentEntity, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AppointmentEntity, String> param) {
                LocalDate date = param.getValue().getStartLocalDateTime().toLocalDate();
                LocalTime time = param.getValue().getStartLocalDateTime().toLocalTime();
                return new ReadOnlyObjectWrapper<String>(date.toString() + " " + time.toString());

            }
        });
        description.setCellValueFactory(new PropertyValueFactory<AppointmentEntity, String>("description"));
        description.setCellFactory(cellFactory);
        description.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<AppointmentEntity, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<AppointmentEntity, String> t) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setDescription(t.getNewValue());
            }
        });
        location.setCellValueFactory(new PropertyValueFactory<AppointmentEntity, String>("location"));
        location.setCellFactory(cellFactory);
        location.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<AppointmentEntity, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<AppointmentEntity, String> t) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setLocation(t.getNewValue());
            }
        });
        participants.setCellValueFactory(new PropertyValueFactory<AppointmentEntity, String>("participants"));
        participants.setCellFactory(cellFactory);
        participants.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<AppointmentEntity, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<AppointmentEntity, String> t) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setParticipants(t.getNewValue());
            }
        });
        wholeDay.setCellValueFactory(new PropertyValueFactory<AppointmentEntity, String>("wholeDay"));
        delete.setSortable(false);
        delete.setCellFactory(
                p -> new DeleteButtonCell(eventsTable, resourceBundle));

        return tableColumns;
    }


}

