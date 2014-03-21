package inventario.jpacontroller;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;

import inventario.modelo.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.persistence.Query;
import jpa.exceptions.RollbackFailureException;

public class InventarioJpaController {

    // <editor-fold defaultstate="collapsed" desc="jpaController">
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "IACT-ADM_UIPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }// </editor-fold>

    
    public Asignacion findAsignacion(Asignacion asignacion) {
        EntityManager em = getEntityManager();
        String selectQuery=
                "SELECT object(a) " +
                "FROM Asignacion AS a " +
                "WHERE ";

        if(asignacion.getProyecto()!=null){
            selectQuery+="a.proyecto.id='"+asignacion.getProyecto().getId()+"' ";
        }else{
            selectQuery+="a.proyecto IS NULL ";
        }

        if(asignacion.getEmpleado()!=null){
            selectQuery+="AND a.empleado.id="+asignacion.getEmpleado().getId()+" ";
        }else{
            selectQuery+="AND a.empleado IS NULL ";
        }

        if(asignacion.getLineaInvestigacion()!=null){
            selectQuery+="AND a.lineaInvestigacion.nombre='"+asignacion.getLineaInvestigacion().getNombre()+"' ";
        }else{
            selectQuery+="AND a.lineaInvestigacion IS NULL ";
        }

        if(asignacion.getProducto()!=null){
            selectQuery+="AND a.producto.id="+asignacion.getProducto().getId()+" ";
        }else{
            selectQuery+="AND a.producto IS NULL ";
        }

        Query q = em.createQuery(selectQuery);

        System.out.println(selectQuery);
        
        List<Asignacion> listaMovimientos = q.getResultList();

        em.close();
        
        if (!listaMovimientos.isEmpty()) {
            return listaMovimientos.get(0);
        } else {
            return null;
        }

    }

    public void create(Asignacion asignacion) throws RollbackFailureException, Exception{
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(asignacion);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Asignacion asignacion) throws RollbackFailureException, Exception{
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.merge(asignacion);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }




    public void create(MovimientoEntrada movimientoEntrada) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(movimientoEntrada);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void create(MovimientoSalida movimientoSalida) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(movimientoSalida);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Collection<MovimientoEntrada> findEntradasProducto(ProductoAlmacenado producto) {
        if (producto == null) {
            return Collections.EMPTY_LIST;
        }

        EntityManager em = getEntityManager();


        Query q = em.createQuery(
                "SELECT object(m) " +
                "FROM MovimientoEntrada m " +
                    "JOIN m.productoAsignado pa " +
                    "JOIN pa.producto p " +
                "WHERE p.id = :idProducto " +
                "ORDER BY m.fechaEntrada ASC");

        q.setParameter("idProducto", producto.getId());

        List<MovimientoEntrada> movimientosEntrada=q.getResultList();

        em.close();

        return movimientosEntrada;

    }

    public Collection<MovimientoSalida> findSalidasProducto(ProductoAlmacenado producto) {
        if (producto == null) {
            return Collections.EMPTY_LIST;
        }

        EntityManager em = getEntityManager();

        Query q = em.createQuery(
                "SELECT object(m) " +
                "FROM MovimientoSalida m " +
                "JOIN m.productoAsignado pa " +
                "JOIN pa.producto p " +
                "WHERE p.id = :idProducto " +
                "ORDER BY m.fechaSalida ASC");

        q.setParameter("idProducto", producto.getId());

        List<MovimientoSalida> listaMovimientos = q.getResultList();

        em.close();

        return listaMovimientos;

    }

    public Collection<MovimientoSalida> findUltimasSalidas(int num){
        EntityManager em = getEntityManager();

        Query q = em.createQuery(
                "SELECT object(m) " +
                "FROM MovimientoSalida m " +
                "ORDER BY m.fechaSalida DESC");

        q.setMaxResults(num);
        List<MovimientoSalida> listaMovimientos = q.getResultList();

        em.close();

        return listaMovimientos;
    }

    public Collection<MovimientoSalida> findMovimientosSalida(){
        EntityManager em = getEntityManager();

        Query q = em.createQuery(
                "SELECT object(m) " +
                "FROM MovimientoSalida m");

        List<MovimientoSalida> listaMovimientos = q.getResultList();

        em.close();

        return listaMovimientos;
    }

    public Collection<MovimientoEntrada> findUltimasEntradas(int num){
        EntityManager em = getEntityManager();

        Query q = em.createQuery(
                "SELECT object(m) " +
                "FROM MovimientoEntrada m " +
                "ORDER BY m.fechaEntrada DESC");

        q.setMaxResults(num);
        List<MovimientoEntrada> listaMovimientos = q.getResultList();

        em.close();

        return listaMovimientos;
    }

    public Collection<MovimientoEntrada> findMovimientosEntrada(){
        EntityManager em = getEntityManager();

        Query q = em.createQuery(
                "SELECT object(m) " +
                "FROM MovimientoEntrada m");

        List<MovimientoEntrada> listaMovimientos = q.getResultList();

        em.close();

        return listaMovimientos;
    }



    public MovimientoEntrada findMoviminetoEntrada(Long id) {
        EntityManager em = getEntityManager();

        MovimientoEntrada movimientoEntrada=em.find(MovimientoEntrada.class, id);
        em.close();

        return movimientoEntrada;
    }

    public MovimientoSalida findMovimientoSalida(Long id) {
        EntityManager em = getEntityManager();

        MovimientoSalida movimientoSalida=em.find(MovimientoSalida.class, id);
        em.close();

        return movimientoSalida;
    }



    public void create(ProductoAlmacenado producto) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(producto);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProductoAlmacenado producto) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.merge(producto);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProductoAlmacenado> findProductos() {
        EntityManager em = getEntityManager();

        Query q = em.createQuery("SELECT object(p) FROM ProductoAlmacenado AS p ORDER BY p.nombre");
        List<ProductoAlmacenado> productosInventario = q.getResultList();

        em.close();
        
        return productosInventario;
    }

    public ProductoAlmacenado findProducto(Long id) {
        EntityManager em = getEntityManager();

        ProductoAlmacenado productoFound=em.find(ProductoAlmacenado.class, id);
        em.close();
        
        return productoFound;

    }

    public ProductoAlmacenado findProductoPorEAN(String ean) {
        if (ean == null || ean.isEmpty()) {
            return null;
        }

        EntityManager em = getEntityManager();

        Query q = em.createQuery(
                "SELECT object(o) " +
                "FROM ProductoAlmacenado AS o " +
                "WHERE o.ean = :ean");
        q.setParameter("ean", ean);

        List<ProductoAlmacenado> listaProducto = q.getResultList();

        em.close();

        if (listaProducto.isEmpty()) {
            return null;
        } else {
            return listaProducto.get(0);
        }

    }



    
    public List<TipoCantidad> findTiposCantidad() {

        EntityManager em = getEntityManager();

        Query q = em.createQuery("SELECT object(tc) FROM TipoCantidad AS tc");
        List<TipoCantidad> productosInventario = q.getResultList();

        em.close();
        
        return productosInventario;

    }

    public TipoCantidad findTipoCantidad(Long id) {
        EntityManager em = getEntityManager();

        TipoCantidad tipoCantidadFound=em.find(TipoCantidad.class, id);
        em.close();

        return tipoCantidadFound;
    }


}
