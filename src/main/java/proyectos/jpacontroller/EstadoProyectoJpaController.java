package proyectos.jpacontroller;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import jpa.exceptions.RollbackFailureException;
import proyectos.modelo.EstadoProyecto;

/**
 *
 * @author edu
 */
public class EstadoProyectoJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "IACT-ADM_UIPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    } 

    private void crearEstadosPedido() throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            EstadoProyecto estadoProyecto;
            
            estadoProyecto=new EstadoProyecto();
            estadoProyecto.setNombre("Fase de solicitud");
            em.persist(estadoProyecto);

            estadoProyecto=new EstadoProyecto();
            estadoProyecto.setNombre("Fase de aceptación");
            em.persist(estadoProyecto);

            estadoProyecto=new EstadoProyecto();
            estadoProyecto.setNombre("Vigente");
            em.persist(estadoProyecto);

            estadoProyecto=new EstadoProyecto();
            estadoProyecto.setNombre("Finalizado ejecución");
            em.persist(estadoProyecto);

            estadoProyecto=new EstadoProyecto();
            estadoProyecto.setNombre("Desestimado");
            em.persist(estadoProyecto);

            estadoProyecto=new EstadoProyecto();
            estadoProyecto.setNombre("Finalizado y justificado");
            em.persist(estadoProyecto);

           utx.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstadoProyecto> findEstadoProyectoEntities() {
        if(getEstadoProyectoCount()==0){
            try {
                crearEstadosPedido();
            } catch (RollbackFailureException ex) {
                Logger.getLogger(EstadoProyectoJpaController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(EstadoProyectoJpaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return findEstadoProyectoEntities(true, -1, -1);
    }


    private List<EstadoProyecto> findEstadoProyectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from EstadoProyecto as o");
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

    public EstadoProyecto findEstadoProyecto(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoProyecto.class, id);

        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

      public int getEstadoProyectoCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from EstadoProyecto as o").getSingleResult()).intValue();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

}
