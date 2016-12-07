package andras.bator.diaryapp.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abator on 12/7/2016.
 */
@XmlRootElement
@XmlSeeAlso({AppointmentEntity.class})
public class Appointments extends ArrayList<AppointmentEntity> {
    public Appointments(){

    }

    public Appointments(List<AppointmentEntity> entites){
        this.addAll(entites);
    }

    @XmlElement(name="appointment")
    public List<AppointmentEntity> getAppointments() {
        return this;
    }
}
