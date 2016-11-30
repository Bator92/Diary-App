package andras.bator.diaryapp.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by abator on 11/30/2016.
 */
public class EventTableEntity {
    private final SimpleStringProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty description;
    private final SimpleStringProperty localDateTime;
    private final SimpleStringProperty location;
    private final SimpleStringProperty participants;

    public EventTableEntity(String id, String name, String description, String localDateTime, String location, String participants) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.localDateTime = new SimpleStringProperty(localDateTime);
        this.location = new SimpleStringProperty(location);
        this.participants = new SimpleStringProperty(participants);
    }


    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getLocalDateTime() {
        return localDateTime.get();
    }

    public SimpleStringProperty localDateTimeProperty() {
        return localDateTime;
    }

    public void setLocalDateTime(String localDateTime) {
        this.localDateTime.set(localDateTime);
    }

    public String getLocation() {
        return location.get();
    }

    public SimpleStringProperty locationProperty() {
        return location;
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public String getParticipants() {
        return participants.get();
    }

    public SimpleStringProperty participantsProperty() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants.set(participants);
    }
}
