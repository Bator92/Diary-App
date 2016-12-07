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

    /**
     * Returns an EntityManager
     * @return EntityManager
     */
    protected abstract EntityManager em();

    /**
     * Creates a new entity in database
     * @param entity
     */
    public void create(T entity) {
        LOGGER.info(entity.toString()+" entity created");
        EntityManager em = em();
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Edit an entity in the database
     * @param entity
     */
    public void edit(T entity) {
        LOGGER.info(entity.toString()+" entity edited");
        EntityManager em = em();
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Removes an etity from the database
     * @param entity
     */
    public void remove(T entity) {
        LOGGER.info(entity.toString()+" entity removed");
        EntityManager em = em();
        em.getTransaction().begin();
        em.remove(em.merge(entity));
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Finds an entity from the database
     * @param id
     * @return Generic object
     */
    public T find(Object id) {
        LOGGER.info("AbstractDAO find(id) method called");
        EntityManager em = em();
        em.getTransaction().begin();
        return em.find(entityClass, id);
    }

    /**
     * Returns all of the entities of a specified type from the database
     * @return List of generic objects
     */
    public List<T> findAll() {
        LOGGER.info("AbstractDAO findAll() method called");
        EntityManager em = em();
        em.getTransaction().begin();
        CriteriaQuery<T> cq = em.getCriteriaBuilder()
                .createQuery(entityClass);
        cq.select(cq.from(entityClass));

        return em.createQuery(cq).getResultList();
    }

    /**
     * Returns a range of entities from the database
     * @param range
     * @return List of generic objects
     */
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

    /**
     * Counts the entites of a specified type in the database
     * @return count
     */
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
