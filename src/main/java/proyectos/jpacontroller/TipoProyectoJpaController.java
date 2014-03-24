//TODO Documentar!!!!
package proyectos.jpacontroller;

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
import proyectos.modelo.TipoProyecto;


public class TipoProyectoJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "IACT-ADM_UIPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

   
    public void create(TipoProyecto tipoProyecto) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(tipoProyecto);
            utx.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

   
    public void edit(TipoProyecto tipoProyecto) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            tipoProyecto = em.merge(tipoProyecto);
            utx.commit();
        }finally {
            if (em != null) {
                em.close();
            }
        }
    }

  
    public void destroy(String nombreTipoProyecto) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoProyecto tipoProyecto;
            try {
                tipoProyecto = em.getReference(TipoProyecto.class, nombreTipoProyecto);
                tipoProyecto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("El tipo de proyecto con nombre \"" + nombreTipoProyecto + "\" no existe.", enfe);
            }
            em.remove(tipoProyecto);
            utx.commit();
        }finally {
            if (em != null) {
                em.close();
            }
        }
    }

   
    public List<TipoProyecto> findTipoProyectoEntities() {
        return findTipoProyectoEntities(true, -1, -1);
    }


    public List<TipoProyecto> findTipoProyectoEntities(int maxResults, int firstResult) {
        return findTipoProyectoEntities(false, maxResults, firstResult);
    }

    private List<TipoProyecto> findTipoProyectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from TipoProyecto as o");
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

    public TipoProyecto findTipoProyecto(String nombre) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoProyecto.class, nombre);
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

   
    public int getTipoProyectoCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from TipoProyecto as o").getSingleResult()).intValue();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

}
