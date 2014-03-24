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
import proyectos.modelo.EntidadFinanciadora;



public class EntidadFinanciadoraJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "IACT-ADM_UIPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public void create(EntidadFinanciadora entidadFinanciadora) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(entidadFinanciadora);
            utx.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EntidadFinanciadora entidadFinanciadora) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            entidadFinanciadora = em.merge(entidadFinanciadora);
            utx.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }



    public void destroy(String nombreEntidadFinanciadora) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            EntidadFinanciadora entidadFinanciadora;
            try {
                entidadFinanciadora = em.getReference(EntidadFinanciadora.class, nombreEntidadFinanciadora);
                entidadFinanciadora.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("La entidad financiadora con nombre \"" + nombreEntidadFinanciadora + "\" no existe.", enfe);
            }
            em.remove(entidadFinanciadora);
            utx.commit();
        }finally {
            if (em != null) {
                em.close();
            }
        }
    }


    public List<EntidadFinanciadora> findEntidadFinanciadoraEntities() {
        return findEntidadFinanciadoraEntities(true, -1, -1);
    }


    public List<EntidadFinanciadora> findEntidadFinanciadoraEntities(int maxResults, int firstResult) {
        return findEntidadFinanciadoraEntities(false, maxResults, firstResult);
    }


    private List<EntidadFinanciadora> findEntidadFinanciadoraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from EntidadFinanciadora as o");
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


    public EntidadFinanciadora findEntidadFinanciadora(String nombre) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EntidadFinanciadora.class, nombre);
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }


    public int getEntidadFinanciadoraCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from EntidadFinanciadora as o").getSingleResult()).intValue();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

}
