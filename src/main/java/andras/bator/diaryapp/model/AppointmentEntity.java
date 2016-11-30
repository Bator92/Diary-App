package andras.bator.diaryapp.model;

import jfxtras.scene.control.agenda.Agenda;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.Calendar;

/**
 * Created by abator on 11/28/2016.
 */
@Entity
@Table(name = "Appointment", schema = "", catalog = "")
public class AppointmentEntity implements Agenda.Appointment {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;

    @Column(name = "name")
    private String summary;

    @Column(name = "description")
    private String description;

    @Column(name = "dateStart")
    private LocalDateTime startTime;

    @Column(name = "dateEnd")
    private LocalDateTime endTime;

    @Column(name = "location")
    private String location;

    @Column(name = "participants")
    private String participants;

    @Column(name = "wholeday")
    private boolean wholeDay;

    public AppointmentEntity() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getSummary() {
        return summary;
    }

    @Override
    public void setSummary(String name) {
        this.summary = name;
    }


    @Override
    public Boolean isWholeDay() {
        return wholeDay;
    }

    @Override
    public void setWholeDay(Boolean aBoolean) {
        wholeDay = aBoolean;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public Agenda.AppointmentGroup getAppointmentGroup() {
        return null;
    }

    @Override
    public void setAppointmentGroup(Agenda.AppointmentGroup appointmentGroup) {

    }

    @Override
    public Calendar getStartTime() {
        return null;
    }

    @Override
    public void setStartTime(Calendar calendar) {

    }

    @Override
    public Calendar getEndTime() {
        return null;
    }

    @Override
    public void setEndTime(Calendar calendar) {

    }

    @Override
    public Temporal getStartTemporal() {
        return null;
    }

    @Override
    public void setStartTemporal(Temporal temporal) {

    }

    @Override
    public Temporal getEndTemporal() {
        return null;
    }

    @Override
    public void setEndTemporal(Temporal temporal) {

    }

    @Override
    public LocalDateTime getStartLocalDateTime() {
        return startTime;
    }

    @Override
    public void setStartLocalDateTime(LocalDateTime localDateTime) {
        startTime = localDateTime;

    }

    @Override
    public LocalDateTime getEndLocalDateTime() {
        return endTime;
    }

    @Override
    public void setEndLocalDateTime(LocalDateTime localDateTime) {
        endTime = localDateTime;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public AppointmentEntity(String summary, String description, LocalDateTime startTime, LocalDateTime endTime, String location, String participants) {
        this.summary = summary;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.participants = participants;
    }
}
