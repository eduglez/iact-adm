package compras.jpacontroller;

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
import compras.modelo.Producto;

public class ProductoJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "IACT-ADM_UIPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();

            em = getEntityManager();
            em.persist(producto);

            utx.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();

            em = getEntityManager();
            producto = em.merge(producto);

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
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            em.remove(producto);
            utx.commit();
        }finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Producto as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Producto findProducto(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Producto as o").getSingleResult()).intValue();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
