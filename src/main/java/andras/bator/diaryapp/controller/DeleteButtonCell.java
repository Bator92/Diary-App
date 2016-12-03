package andras.bator.diaryapp.controller;

import andras.bator.diaryapp.model.AppointmentEntity;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;

import java.util.ResourceBundle;

/**
 * Created by András on 2016. 12. 03..
 */
public class DeleteButtonCell extends TableCell<AppointmentEntity, Boolean>{
    ResourceBundle resourceBundles;
    final Button cellButton;

    DeleteButtonCell(final TableView tblView, ResourceBundle resourceBundle) {
        resourceBundles = resourceBundle;
        cellButton = new Button(resourceBundles.getString("delete"));

        cellButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                int selectdIndex = getTableRow().getIndex();
                AppointmentEntity appointmentEntity = (AppointmentEntity) tblView.getItems().get(selectdIndex);
                Controller.appointmentsToDelete.add(appointmentEntity);
                tblView.getItems().remove(selectdIndex);
            }
        });
    }

    //Display button if the row is not empty
    @Override
    protected void updateItem(Boolean t, boolean empty) {
        super.updateItem(t, empty);
        if (!empty) {
            setGraphic(cellButton);
        }
    }
}
