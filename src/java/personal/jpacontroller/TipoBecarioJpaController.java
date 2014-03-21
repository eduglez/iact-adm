package personal.jpacontroller;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import personal.modelo.TipoBecario;

/**
 * JPA para TipoBecario
 * @author edu
 * @see personal.modelo.TipoBecario
 */
public class TipoBecarioJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "IACT-ADM_UIPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<TipoBecario> findTipoBecarioEntities() {
       EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from TipoBecario as o");
            return q.getResultList();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }


    public TipoBecario findTipoBecario(String nombre) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoBecario.class, nombre);
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

    public int getTipoBecarioCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from TipoBecario as o").getSingleResult()).intValue();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

}
