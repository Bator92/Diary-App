package andras.bator.diaryapp.dao;

import andras.bator.diaryapp.model.AppointmentEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abator on 11/28/2016.
 */
public class EventDAO extends AbstractDAO<AppointmentEntity> {

    public EventDAO() {
        super(AppointmentEntity.class);
    }

    @Override
    public EntityManager em() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("diaryapp");
        EntityManager em = emf.createEntityManager();
        return em;
    }

    /**
     * Loads today events from database
     * @return List of {@link AppointmentEntity}
     */
    public List<AppointmentEntity> findTodayEvents() {
        List<AppointmentEntity> entityList = super.findAll();
        List<AppointmentEntity> entityListToday = new ArrayList<>();
        for (AppointmentEntity appointmentEntity : entityList) {
            if(appointmentEntity.getStartLocalDateTime().toLocalDate().equals(LocalDate.now())){
                entityListToday.add(appointmentEntity);
            }

        }
        return entityListToday;
    }
}
