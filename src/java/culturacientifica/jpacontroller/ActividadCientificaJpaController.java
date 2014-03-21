package culturacientifica.jpacontroller;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import culturacientifica.modelo.*;
import javax.persistence.EntityNotFoundException;
import jpa.exceptions.NonexistentEntityException;
import jpa.exceptions.RollbackFailureException;

public class ActividadCientificaJpaController {

    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "IACT-ADM_UIPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ActividadCientifica actividadCientifica) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(actividadCientifica);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ActividadCientifica actividadCientifica) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            actividadCientifica = em.merge(actividadCientifica);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = actividadCientifica.getId();
                if (findActividadCientifica(id) == null) {
                    throw new NonexistentEntityException("The ActividadCientifica with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ActividadCientifica actividadCientifica;
            try {
                actividadCientifica = em.getReference(ActividadCientifica.class, id);
                actividadCientifica.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The actividadCientifica with id " + id + " no longer exists.", enfe);
            }
            em.remove(actividadCientifica);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ActividadCientifica> findActividadCientificaEntities() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from ActividadCientifica as o where o.borrado=false");

            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<ActividadCientifica> findActividadCientificaEntities(String dni) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(ac) from ActividadCientifica ac join ac.autoresIACT a where a.dni = :dni AND ac.borrado=false");
            q.setParameter("dni", dni);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ActividadCientifica findActividadCientifica(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ActividadCientifica.class, id);

        } finally {
            em.close();
        }
    }

    public int getActividadCientificaCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from ActividadCientifica as o where o.borrado=false").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
