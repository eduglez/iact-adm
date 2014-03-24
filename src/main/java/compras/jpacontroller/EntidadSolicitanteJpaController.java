package compras.jpacontroller;


import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import compras.modelo.EntidadSolicitante;


public class EntidadSolicitanteJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "IACT-ADM_UIPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<EntidadSolicitante> findEntidadSolicitanteEntities() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from EntidadSolicitante as o");
            
            return q.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EntidadSolicitante> findEntidadSolicitanteProyectosEntities() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from EntidadSolicitante as o where UPPER(o.nombre) like '%PROYECTO%'");
            
            return q.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public EntidadSolicitante findEntidadSolicitante(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EntidadSolicitante.class, id);

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public int getEntidadSolicitanteCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from EntidadSolicitante as o").getSingleResult()).intValue();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
