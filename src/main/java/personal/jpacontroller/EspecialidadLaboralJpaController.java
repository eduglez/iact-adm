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
import personal.modelo.EspecialidadLaboral;

public class EspecialidadLaboralJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "IACT-ADM_UIPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EspecialidadLaboral especialidadLaboral) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(especialidadLaboral);
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
            EspecialidadLaboral especialidadLaboral;
            try {
                especialidadLaboral = em.getReference(EspecialidadLaboral.class, id);
                especialidadLaboral.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("La Especialidad Laboral \"" + id + "\" no existe.", enfe);
            }
            em.remove(especialidadLaboral);
            utx.commit();
        }finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EspecialidadLaboral> findEspecialidadLaboralEntities() {
        return findEspecialidadLaboralEntities(true, -1, -1);
    }

    public List<EspecialidadLaboral> findEspecialidadLaboralEntities(int maxResults, int firstResult) {
        return findEspecialidadLaboralEntities(false, maxResults, firstResult);
    }

    private List<EspecialidadLaboral> findEspecialidadLaboralEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from EspecialidadLaboral as o");
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

    public EspecialidadLaboral findEspecialidadLaboral(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EspecialidadLaboral.class, id);
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

    public int getEspecialidadLaboralCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from EspecialidadLaboral as o").getSingleResult()).intValue();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

}
