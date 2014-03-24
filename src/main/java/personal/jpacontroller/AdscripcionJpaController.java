package personal.jpacontroller;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import personal.modelo.Adscripcion;

/**
 * JPA para Adscripcion
 * @author edu
 * @see personal.modelo.Adscripcion
 */
public class AdscripcionJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "IACT-ADM_UIPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Adscripcion> findAdscripcionEntities() {
       EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Adscripcion as o");
            return q.getResultList();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }


    public Adscripcion findAdscripcion(String nombre) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Adscripcion.class, nombre);
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

    public int getAdscripcionCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Adscripcion as o").getSingleResult()).intValue();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

}
