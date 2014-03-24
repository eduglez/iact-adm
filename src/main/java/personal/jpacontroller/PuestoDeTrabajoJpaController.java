package personal.jpacontroller;

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
import personal.modelo.PuestoDeTrabajo;

public class PuestoDeTrabajoJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "IACT-ADM_UIPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PuestoDeTrabajo puestoDeTrabajo) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(puestoDeTrabajo);
            utx.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PuestoDeTrabajo puestoDeTrabajo) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            puestoDeTrabajo = em.merge(puestoDeTrabajo);
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
            PuestoDeTrabajo puestoDeTrabajo;
            try {
                puestoDeTrabajo = em.getReference(PuestoDeTrabajo.class, id);
                puestoDeTrabajo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The puestoDeTrabajo with id " + id + " no longer exists.", enfe);
            }
            em.remove(puestoDeTrabajo);
            utx.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PuestoDeTrabajo> findPuestoDeTrabajoEntities() {
        return findPuestoDeTrabajoEntities(true, -1, -1);
    }

    public List<PuestoDeTrabajo> findPuestoDeTrabajoEntities(int maxResults, int firstResult) {
        return findPuestoDeTrabajoEntities(false, maxResults, firstResult);
    }

    private List<PuestoDeTrabajo> findPuestoDeTrabajoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from PuestoDeTrabajo as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

    public PuestoDeTrabajo findPuestoDeTrabajo(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PuestoDeTrabajo.class, id);
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

    public int getPuestoDeTrabajoCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from PuestoDeTrabajo as o").getSingleResult()).intValue();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

}
