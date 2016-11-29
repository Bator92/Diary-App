package andras.bator.diaryapp.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by abator on 11/28/2016.
 */
public abstract class AbstractDAO<T> {
    private Class<T> entityClass;

    public AbstractDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }


    protected abstract EntityManager em();

    public void create(T entity) {
        EntityManager em = em();
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        em.close();
    }

    public void edit(T entity) {
        EntityManager em = em();
        em.getTransaction().begin();
        em.merge(entity);
        em.close();
    }

    public void remove(T entity) {
        EntityManager em = em();
        em.getTransaction().begin();
        em.remove(em.merge(entity));
        em.getTransaction().commit();
        em.close();
    }

    public T find(Object id) {
        EntityManager em = em();
        em.getTransaction().begin();
        return em.find(entityClass, id);
    }

    public List<T> findAll() {
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
