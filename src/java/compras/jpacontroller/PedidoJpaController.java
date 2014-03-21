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
import compras.modelo.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import personal.modelo.Empleado;

public class PedidoJpaController {

    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "IACT-ADM_UIPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Crea un nuevo pedido en la base de datos, los datos deben existir.
     * @param pedido Pedido a persistir.
     * @throws jpa.exceptions.RollbackFailureException
     * @throws java.lang.Exception
     */
    public void create(Pedido pedido) throws RollbackFailureException, Exception {
        EntityManager em = null;

        try {
            utx.begin();
            em = getEntityManager();

            //Buscamos el solicitante y se lo asignamos al pedido
            Empleado solicitante = pedido.getSolicitante();
            if (solicitante != null) {
                solicitante = em.getReference(solicitante.getClass(), solicitante.getId());
                pedido.setSolicitante(solicitante);
            }

            //Buscamos los datos de entrega y se los asignamos al pedido
            DatosEntrega datosEntrega = pedido.getDatosEntrega();
            if (datosEntrega != null) {
                datosEntrega = em.getReference(datosEntrega.getClass(), datosEntrega.getId());
                pedido.setDatosEntrega(datosEntrega);
            }

            //Buscamos los datos de facturación y se los asignamos al pedido
            DatosFacturacion datosFacturacion = pedido.getDatosFacturacion();
            if (datosFacturacion != null) {
                datosFacturacion = em.getReference(datosFacturacion.getClass(), datosFacturacion.getId());
                pedido.setDatosFacturacion(datosFacturacion);
            }

            //Buscamos al Proveedor y se lo reasignamos al pedido
            Proveedor proveedor = pedido.getProveedor();
            if (proveedor != null) {
                proveedor = em.getReference(proveedor.getClass(), proveedor.getId());
                pedido.setProveedor(proveedor);
            }

            em.persist(pedido);

            utx.commit();

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void createFactura(Factura factura) throws RollbackFailureException, Exception {
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

    public void createAlbaran(Albaran albaran) throws RollbackFailureException, Exception {
        EntityManager em = null;

        try {
            utx.begin();
            
            em = getEntityManager();
            em.persist(albaran);

            utx.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void createLineaPedido(LineaPedido lineaPedido) throws RollbackFailureException, Exception {
        EntityManager em = null;

        try {
            utx.begin();

            em = getEntityManager();
            em.persist(lineaPedido);

            utx.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void createDocumentoFactura(DocumentoFactura factura) throws RollbackFailureException, Exception {
        EntityManager em = null;

        try {
            utx.begin();

            em = getEntityManager();
            em.persist(factura);

            utx.commit();
        }finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void createDocumentoAlbaran(DocumentoAlbaran albaran) throws RollbackFailureException, Exception {
        EntityManager em = null;

        try {
            utx.begin();

            em = getEntityManager();
            em.persist(albaran);

            utx.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pedido pedido) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();

            em = getEntityManager();

            //Actualizamos el contexto.
            //Se actualizan/agregan las lineas tambien

            pedido = em.merge(pedido);

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
            Pedido pedido;

            //Comprobamos que existe el pedido en el contexto
            try {
                pedido = em.getReference(Pedido.class, id);
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.", enfe);
            }

            em.remove(pedido);

            utx.commit();

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pedido> findPedidoEntities() {
        return findPedidoEntities(true, -1, -1);
    }

    public List<Pedido> findPedidoEntities(int maxResults, int firstResult) {
        return findPedidoEntities(false, maxResults, firstResult);
    }

    private List<Pedido> findPedidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Pedido as o where o.borrado=false order by o.fecha desc");
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

    public Pedido findPedido(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pedido.class, id);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public int getPedidoCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Pedido as o where o.borrado=false").getSingleResult()).intValue();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<String> getRangoFecha() {
        ArrayList<String> anios = new ArrayList<String>();

        List<Pedido> pedidos = findPedidoEntities();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");

        if(!pedidos.isEmpty()){
            Date masReciente = pedidos.get(0).getFecha();

            Date masViejo = pedidos.get(pedidos.size() - 1).getFecha();

            int anioMasReciente = Integer.parseInt(dateFormat.format(masReciente));

            int anioMasViejo = Integer.parseInt(dateFormat.format(masViejo));

            for (int i = anioMasReciente; i >= anioMasViejo; i--) {
                anios.add(Integer.toString(i));
            }

        }else {
            anios.add(dateFormat.format(new Date()));
        }

        return anios;

    }

    public List<Pedido> findPedidoEntities(String anioFiltro) {
        EntityManager em = getEntityManager();
        try {
            Calendar c1 = Calendar.getInstance();
            c1.set(Integer.parseInt(anioFiltro), Calendar.JANUARY, 1);
            Calendar c2 = Calendar.getInstance();
            c2.set(Integer.parseInt(anioFiltro), Calendar.DECEMBER, 31);
            Query q = em.createQuery("select object(o) from Pedido as o where o.fecha>= :inicioAnio and o.fecha<=:finAnio and o.borrado=false order by o.fecha desc");
            q.setParameter("inicioAnio", c1.getTime());
            q.setParameter("finAnio", c2.getTime());
            return q.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pedido> findPedidoEntities(String dni,String columnaFiltro, boolean ascendente, String anioFiltro) {
        String orderBy = " order by o." + columnaFiltro + " " + (ascendente ? "asc" : "desc");
        if (anioFiltro != null) {
            EntityManager em = getEntityManager();
            try {
                Calendar c1 = Calendar.getInstance();
                c1.set(Integer.parseInt(anioFiltro), Calendar.JANUARY, 1);
                Calendar c2 = Calendar.getInstance();
                c2.set(Integer.parseInt(anioFiltro), Calendar.DECEMBER, 31);
                Query q = em.createQuery("select object(o) from Pedido as o where (o.solicitante.dni = :dni) and (o.fecha>= :inicioAnio and o.fecha<=:finAnio) and o.borrado=false" + orderBy);
                q.setParameter("inicioAnio", c1.getTime());
                q.setParameter("finAnio", c2.getTime());
                q.setParameter("dni", dni);
                return q.getResultList();
            } finally {
                if (em != null) {
                    em.close();
                }
            }

        }else{
            EntityManager em = getEntityManager();
            try {

                Query q = em.createQuery("select object(o) from Pedido as o where (o.solicitante.dni = :dni) and o.borrado=false" + orderBy);
                q.setParameter("dni", dni);
                return q.getResultList();
            } finally {
                if (em != null) {
                    em.close();
                }
            }
        }
    }

    public String getSiguienteNumPedido() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        String anio = dateFormat.format(new Date());
        List<Pedido> pedidosAnio = findPedidoEntities(anio);
        if (pedidosAnio.isEmpty()) {
            return anio + "/" + "1";
        } else {
            int i = Integer.parseInt(pedidosAnio.get(0).getNumPedido().substring(5)) + 1;
            while (true) {
                if (findPedido(anio + "/" + i) == null) {
                    return anio + "/" + i;
                } else {
                    i++;
                }
            }
        }
    }

    public List<Pedido> findPedidoEntities(String columnaFiltro, boolean ascendente, String anioFiltro) {
        String orderBy = " order by o." + columnaFiltro + " " + (ascendente ? "asc" : "desc");
        if (anioFiltro != null) {
            EntityManager em = getEntityManager();
            try {
                Calendar c1 = Calendar.getInstance();
                c1.set(Integer.parseInt(anioFiltro), Calendar.JANUARY, 1);
                Calendar c2 = Calendar.getInstance();
                c2.set(Integer.parseInt(anioFiltro), Calendar.DECEMBER, 31);
                Query q = em.createQuery("select object(o) from Pedido as o where o.fecha>= :inicioAnio and o.fecha<=:finAnio and o.borrado=false" + orderBy);
                q.setParameter("inicioAnio", c1.getTime());
                q.setParameter("finAnio", c2.getTime());
                return q.getResultList();
            } finally {
                if (em != null) {
                    em.close();
                }
            }

        }else{
            EntityManager em = getEntityManager();
            try {

                Query q = em.createQuery("select object(o) from Pedido as o where o.borrado=false" + orderBy);
                
                return q.getResultList();
            } finally {
                if (em != null) {
                    em.close();
                }
            }
        }
    }

    public List<LineaPedido> findLineasPedido(){
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from LineaPedido as o order by o.descripcionAlternativa");
            return q.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pedido> findPedidosEmpleado(Empleado e){
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Pedido as o where o.solicitante=:empleado order by o.fecha");
            q.setParameter("empleado", e);
            return q.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


}
