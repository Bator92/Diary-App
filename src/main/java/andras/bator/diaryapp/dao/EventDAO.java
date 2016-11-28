package andras.bator.diaryapp.dao;

import andras.bator.diaryapp.model.EventEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 * Created by abator on 11/28/2016.
 */
public class EventDAO extends AbstractDAO<EventEntity> {

    @PersistenceContext
    private EntityManager em;

    public EventDAO() {
        super(EventEntity.class);
    }

    @Override
    public EntityManager em() {
        return em;
    }


}
