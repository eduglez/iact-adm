package compras.jpacontroller;

import compras.modelo.Albaran;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import jpa.exceptions.RollbackFailureException;
import compras.modelo.Factura;
import compras.modelo.Pedido;
import jpa.exceptions.NonexistentEntityException;

public class FacturaJpaController {

    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "IACT-ADM_UIPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Factura> findFacturaEntities() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Factura as o");

            return q.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    public List<Factura> findFacturaEntities(String pedidoId) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(f) from Factura as f join f.pedidos as p where p.numPedido = '" + pedidoId + "'");

            return q.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Factura findFactura(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Factura.class, id);

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public int getEstadoProyectoCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Factura as o").getSingleResult()).intValue();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Factura factura) throws RollbackFailureException, Exception{
        EntityManager em=getEntityManager();

        try{
            utx.begin();

            for(Albaran a:factura.getAlbaranes()){
                a.setFactura(null);
                em.merge(a);
            }

            factura.getAlbaranes().clear();

            factura=em.merge(factura);

            for(Pedido p: factura.getPedidos()){
                p.getFacturas().remove(factura);
                em.merge(p);
            }

            factura.getPedidos().clear();

            factura=em.merge(factura);

            if(factura.getDocumento()!=null){
                factura.getDocumento().setFactura(null);
                em.merge(factura.getDocumento());
                factura.setDocumento(null);
                factura=em.merge(factura);
            }

            em.remove(factura);

            utx.commit();

        } finally{
            if (em != null) {
                em.close();
            }
        }


    }
    public void create(Factura factura) throws RollbackFailureException, Exception {
        EntityManager em = null;

        try {
            utx.begin();

            em = getEntityManager();
            em.persist(factura);

            utx.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    public void edit(Factura factura) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            
            em = getEntityManager();
            factura = em.merge(factura);

            utx.commit();
        }finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Factura> findFacturaEntities(String columnaFiltro, boolean ascendente) {
        String orderBy = " order by o." + columnaFiltro + " " + (ascendente ? "asc" : "desc");
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Factura as o" + orderBy);
            return q.getResultList();
         } finally {
            if (em != null) {
                em.close();
            }
         }
    }
}