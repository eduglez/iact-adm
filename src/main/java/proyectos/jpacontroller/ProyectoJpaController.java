package proyectos.jpacontroller;

import compras.modelo.Pedido;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.transaction.UserTransaction;
import jpa.exceptions.NonexistentEntityException;
import jpa.exceptions.RollbackFailureException;
import java.util.List;
import proyectos.modelo.DocumentoProyecto;
import proyectos.modelo.EmpleadoProyecto;
import gasto.modelo.Gasto;
import java.util.Collections;
import proyectos.modelo.Justificacion;
import proyectos.modelo.Proyecto;
import proyectos.modelo.distribuciondotacion.AnualidadPartida;


public class ProyectoJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "IACT-ADM_UIPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {

        return emf.createEntityManager();
    }

    public void create(Proyecto proyecto) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();

            em = getEntityManager();
            em.persist(proyecto);
           
            utx.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proyecto proyecto) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;

        try {
            utx.begin();
            em = getEntityManager(); 
            proyecto = em.merge(proyecto);
            utx.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    public void createJustificacion(Justificacion justificacion) throws NonexistentEntityException, RollbackFailureException, Exception {
      EntityManager em = null;
        try {
            utx.begin();
            
            em = getEntityManager();
            em.persist(justificacion);

            utx.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void createGasto(Gasto gasto) throws NonexistentEntityException, RollbackFailureException, Exception {
      EntityManager em = null;
        try {
            utx.begin();
            
            em = getEntityManager();
            em.persist(gasto);

            utx.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void createAnualidadPartida(AnualidadPartida anualidadPartida) throws NonexistentEntityException, RollbackFailureException, Exception {
      EntityManager em = null;
        try {
            utx.begin();
            
            em = getEntityManager();
            em.persist(anualidadPartida);

            utx.commit();
        }finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void createDocumentoProyecto(DocumentoProyecto documento) throws NonexistentEntityException, RollbackFailureException, Exception {
      EntityManager em = null;
        try {
            utx.begin();
            
            em = getEntityManager();
            em.persist(documento);

            utx.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void createEmpleadoProyecto(EmpleadoProyecto empleadoProyecto) throws NonexistentEntityException, RollbackFailureException, Exception {
      EntityManager em = null;
        try {
            utx.begin();
            
            em = getEntityManager();
            em.persist(empleadoProyecto);

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
            Proyecto proyecto;
            try {
                proyecto = em.getReference(Proyecto.class, id);
                proyecto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proyecto with id " + id + " no longer exists.", enfe);
            }
           
            em.remove(proyecto);

            utx.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proyecto> findProyectoEntities() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT object(o) FROM Proyecto as o WHERE o.borrado=false");
            return q.getResultList();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

    public Proyecto findProyecto(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proyecto.class, id);
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

    public int getProyectoCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("SELECT count(o) FROM Proyecto as o WHERE o.borrado=false").getSingleResult()).intValue();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

    public List<Proyecto> findProyectoEntities(String texto){
         EntityManager em = getEntityManager();
        try {
            texto=texto.toUpperCase();
            texto="%"+texto
                    .replace('A', '_')
                    .replace('E', '_')
                    .replace('I', '_')
                    .replace('O', '_')
                    .replace('U', '_')
                    .replace('Á', '_')
                    .replace('É', '_')
                    .replace('Í', '_')
                    .replace('Ó', '_')
                    .replace('Ú', '_')+"%";

            Query q = em.createQuery("select object(o) " +
                                     "from Proyecto as o " +
                                     "where (UPPER(o.id) like :texto) " +
                                        "OR (UPPER(o.responsable.empleadoIACT.nombre) like :texto) " +
                                        "OR (UPPER(o.referencia) like :texto) " +
                                        "OR (UPPER(o.responsable.empleadoIACT.apellidos) like :texto)" +
                                        "order by o.id");
            q.setParameter("texto", texto);
            return q.getResultList();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

    public List<Proyecto> findProyectosVigentes(String texto){
        if(texto==null || texto.trim().isEmpty())
            return Collections.EMPTY_LIST;

         EntityManager em = getEntityManager();
        try {
            texto=texto.toUpperCase();
            texto="%"+texto
                    .replace('A', '_')
                    .replace('E', '_')
                    .replace('I', '_')
                    .replace('O', '_')
                    .replace('U', '_')
                    .replace('Á', '_')
                    .replace('É', '_')
                    .replace('Í', '_')
                    .replace('Ó', '_')
                    .replace('Ú', '_')+"%";

            Query q = em.createQuery("select object(o) " +
                                     "from Proyecto as o " +
                                     "where ((UPPER(o.id) like :texto) " +
                                        "OR (UPPER(o.responsable.empleadoIACT.nombre) like :texto) " +
                                        "OR (UPPER(o.referencia) like :texto) " +
                                        "OR (UPPER(o.responsable.empleadoIACT.apellidos) like :texto)) " +
                                        "AND (o.estadoProyecto.nombre='Vigente') " +
                                        "AND (o.borrado=false) " +
                                        "order by o.id");
            q.setParameter("texto", texto);
            return q.getResultList();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

    public List<Proyecto> findProyectoVigentesEntities(String dniResponsable){
         EntityManager em = getEntityManager();
        try {
           
            Query q = em.createQuery("select object(o) " +
                                     "from Proyecto as o " +
                                     "where (o.responsable.empleadoIACT.dni = :dni) "+
                                        "AND (o.estadoProyecto.nombre = 'Vigente') AND o.borrado=false "+
                                        "order by o.id");
            q.setParameter("dni", dniResponsable);
            return q.getResultList();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

    public List<Pedido> findPedidosProyecto(Proyecto proyecto) {
         EntityManager em = getEntityManager();
        try {

            Query q = em.createQuery("select object(o) " +
                                     "from Pedido as o " +
                                     "where (o.proyecto.id = :id) "+
                                        "order by o.fecha");
            q.setParameter("id", proyecto.getId());
            return q.getResultList();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

    public AnualidadPartida findAnualidadPartida(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AnualidadPartida.class, id);
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

    public void refreshProyecto(Proyecto proyecto) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            
            em = getEntityManager();
            em.refresh(proyecto);

            utx.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
