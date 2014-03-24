package compras.jpacontroller;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.transaction.UserTransaction;
import jpa.exceptions.NonexistentEntityException;
import jpa.exceptions.RollbackFailureException;
import compras.modelo.DatosEntrega;


public class DatosEntregaJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "IACT-ADM_UIPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DatosEntrega datosEntrega) throws Exception {
        EntityManager em = null;
        try{
            utx.begin();
            em = getEntityManager();
            em.persist(datosEntrega);
            utx.commit();
        }finally{
            if (em != null) {
                em.close();
            }
        }
     }

    public void edit(DatosEntrega datosEntrega) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try{
            utx.begin();
            em = getEntityManager();
            datosEntrega = em.merge(datosEntrega);
            utx.commit();
        }finally{
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try{
            utx.begin();
            em = getEntityManager();
            DatosEntrega datosEntrega;
            try {
                datosEntrega = em.getReference(DatosEntrega.class, id);
                datosEntrega.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.", enfe);
            }

            em.remove(datosEntrega);
            utx.commit();

        }finally{
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DatosEntrega> findDatosEntregaEntities() {
        return findDatosEntregaEntities(true, -1, -1);
    }

    public List<DatosEntrega> findDatosEntregaEntities(int maxResults, int firstResult) {
        return findDatosEntregaEntities(false, maxResults, firstResult);
    }

    private List<DatosEntrega> findDatosEntregaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from DatosEntrega as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public DatosEntrega findDatosEntrega(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DatosEntrega.class, id);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public int getDatosEntregaCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from DatosEntrega as o").getSingleResult()).intValue();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
