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
import personal.modelo.Grupo;


public class GrupoJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "IACT-ADM_UIPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Grupo grupo) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(grupo);
            utx.commit();
        }finally {
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
            Grupo grupo;
            try {
                grupo = em.getReference(Grupo.class, id);
                grupo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("El Grupo " + id + " ya no existe.", enfe);
            }
            em.remove(grupo);
            utx.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Grupo> findGrupoEntities() {
        return findGrupoEntities(true, -1, -1);
    }

    public List<Grupo> findGrupoEntities(int maxResults, int firstResult) {
        return findGrupoEntities(false, maxResults, firstResult);
    }

    private List<Grupo> findGrupoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Grupo as o");
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

    public Grupo findGrupo(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Grupo.class, id);
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

    public int getGrupoCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Grupo as o").getSingleResult()).intValue();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

}
