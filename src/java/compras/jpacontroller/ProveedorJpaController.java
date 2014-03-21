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
import compras.modelo.Proveedor;


public class ProveedorJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "IACT-ADM_UIPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proveedor proveedor) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(proveedor);
            utx.commit();
        }finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proveedor proveedor) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            proveedor = em.merge(proveedor);
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
            Proveedor proveedor;
            try {
                proveedor = em.getReference(Proveedor.class, id);
                proveedor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.", enfe);
            }
            em.remove(proveedor);
            utx.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }



    public List<Proveedor> findProveedorEntities() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Proveedor as o order by o.razonSocial");
            return q.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Proveedor findProveedor(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proveedor.class, id);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public int getProveedorCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Proveedor as o").getSingleResult()).intValue();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
