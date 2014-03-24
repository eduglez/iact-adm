package personal.jpacontroller;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import personal.modelo.TipoLaboralTemporal;

/**
 * TipoLaboralTemporal
 * @author edu
 * @see personal.modelo.TipoLaboralTemporal
 */
public class TipoLaboralTemporalJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "IACT-ADM_UIPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<TipoLaboralTemporal> findTipoLaboralTemporalEntities() {
       EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from TipoLaboralTemporal as o");
            return q.getResultList();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }


    public TipoLaboralTemporal findTipoLaboralTemporal(String nombre) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoLaboralTemporal.class, nombre);
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

    public int getTipoLaboralTemporalCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from TipoLaboralTemporal as o").getSingleResult()).intValue();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

}
