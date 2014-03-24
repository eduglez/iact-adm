package compras.jpacontroller;

import java.util.logging.Level;
import java.util.logging.Logger;
import personal.jpacontroller.*;
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
import compras.modelo.EstadoPedido;


public class EstadoPedidoJpaController {
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
            EstadoPedido estadoPedido;
            estadoPedido=new EstadoPedido();
            estadoPedido.setNombre("En proceso");
            em.persist(estadoPedido);

            estadoPedido=new EstadoPedido();
            estadoPedido.setNombre("Recibido");
            em.persist(estadoPedido);

            estadoPedido=new EstadoPedido();
            estadoPedido.setNombre("Cancelado");
            em.persist(estadoPedido);

            estadoPedido=new EstadoPedido();
            estadoPedido.setNombre("Finalizado");
            em.persist(estadoPedido);

            estadoPedido=new EstadoPedido();
            estadoPedido.setNombre("Pendiente de aceptación");
            em.persist(estadoPedido);

            utx.commit();
         } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstadoPedido> findEstadoPedidoEntities() {
        if(getEstadoPedidoCount()==0){
            try {
                crearEstadosPedido();
            } catch (RollbackFailureException ex) {
                Logger.getLogger(EstadoPedidoJpaController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(EstadoPedidoJpaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return findEstadoPedidoEntities(true, -1, -1);
    }


    private List<EstadoPedido> findEstadoPedidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from EstadoPedido as o");
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

    public EstadoPedido findEstadoPedido(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoPedido.class, id);

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public int getEstadoPedidoCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from EstadoPedido as o").getSingleResult()).intValue();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
