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
import compras.modelo.DatosFacturacion;


public class DatosFacturacionJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "IACT-ADM_UIPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DatosFacturacion datosFacturacion) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(datosFacturacion);
            utx.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DatosFacturacion datosFacturacion) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            datosFacturacion = em.merge(datosFacturacion);
            utx.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            DatosFacturacion datosFacturacion;
            try {
                datosFacturacion = em.getReference(DatosFacturacion.class, id);
                datosFacturacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.", enfe);
            }
            em.remove(datosFacturacion);
            utx.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DatosFacturacion> findDatosFacturacionEntities() {
        return findDatosFacturacionEntities(true, -1, -1);
    }

    public List<DatosFacturacion> findDatosFacturacionEntities(int maxResults, int firstResult) {
        return findDatosFacturacionEntities(false, maxResults, firstResult);
    }

    private List<DatosFacturacion> findDatosFacturacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from DatosFacturacion as o");
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

    public DatosFacturacion findDatosFacturacion(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DatosFacturacion.class, id);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public int getDatosFacturacionCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from DatosFacturacion as o").getSingleResult()).intValue();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
