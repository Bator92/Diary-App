package andras.bator.diaryapp.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by abator on 11/28/2016.
 */
public abstract class AbstractDAO<T> {
    private final static Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private Class<T> entityClass;
    public AbstractDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }


    protected abstract EntityManager em();

    public void create(T entity) {
        LOGGER.info(entity.toString()+" entity created");
        EntityManager em = em();
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        em.close();
    }

    public void edit(T entity) {
        LOGGER.info(entity.toString()+" entity edited");
        EntityManager em = em();
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
        em.close();
    }

    public void remove(T entity) {
        LOGGER.info(entity.toString()+" entity removed");
        EntityManager em = em();
        em.getTransaction().begin();
        em.remove(em.merge(entity));
        em.getTransaction().commit();
        em.close();
    }

    public T find(Object id) {
        LOGGER.info("AbstractDAO find(id) method called");
        EntityManager em = em();
        em.getTransaction().begin();
        return em.find(entityClass, id);
    }

    public List<T> findAll() {
        LOGGER.info("AbstractDAO findAll() method called");
        EntityManager em = em();
        em.getTransaction().begin();
        CriteriaQuery<T> cq = em.getCriteriaBuilder()
                .createQuery(entityClass);
        cq.select(cq.from(entityClass));

        return em.createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        EntityManager em = em();
        em.getTransaction().begin();
        CriteriaQuery<T> cq = em.getCriteriaBuilder()
                .createQuery(entityClass);
        cq.select(cq.from(entityClass));
        TypedQuery<T> q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        LOGGER.info("AbstractDAO count() method called");
        EntityManager em = em();
        em.getTransaction().begin();
        CriteriaQuery<Long> cq = em.getCriteriaBuilder()
                .createQuery(Long.class);
        Root<T> rt = cq.from(entityClass);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
