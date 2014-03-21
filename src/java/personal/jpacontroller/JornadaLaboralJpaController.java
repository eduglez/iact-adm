package personal.jpacontroller;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import personal.modelo.JornadaLaboral;


public class JornadaLaboralJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "IACT-ADM_UIPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<JornadaLaboral> findJornadaLaboralEntities() {
       EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from JornadaLaboral as o");
            return q.getResultList();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }


    public JornadaLaboral findJornadaLaboral(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(JornadaLaboral.class, id);
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

    public int getJornadaLaboralCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from JornadaLaboral as o").getSingleResult()).intValue();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

}
