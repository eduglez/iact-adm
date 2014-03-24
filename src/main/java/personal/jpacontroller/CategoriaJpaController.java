package personal.jpacontroller;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import personal.modelo.Categoria;

/**
 * JPA para Categoria
 * @author edu
 * @see personal.modelo.Categoria
 */
public class CategoriaJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "IACT-ADM_UIPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Categoria> findCategoriaEntities() {
       EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Categoria as o");
            return q.getResultList();
        } finally {
           if(em!=null){
                em.close();
            }
        }
    }


    public Categoria findCategoria(String nombre) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categoria.class, nombre);
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

    public int getCategoriaCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Categoria as o").getSingleResult()).intValue();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

}
