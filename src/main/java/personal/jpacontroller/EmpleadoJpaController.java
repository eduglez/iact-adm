package personal.jpacontroller;

import java.util.ArrayList;
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
import personal.modelo.Contrato;
import java.util.Calendar;
import java.util.Collections;
import javax.persistence.FlushModeType;
import personal.modelo.Empleado;
import personal.modelo.Falta;


public class EmpleadoJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "IACT-ADM_UIPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) throws RollbackFailureException, Exception {
       
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(empleado);
            utx.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    public void createFalta(Falta falta) throws RollbackFailureException, Exception {

        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(falta);
            utx.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleado empleado) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();

            em = getEntityManager();
            em.merge(empleado);
            
            utx.commit();

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroyContrato(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
         EntityManager em = null;
        try {
            utx.begin();

            em = getEntityManager();
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("El empleado ya no existe.", enfe);
            }
            empleado.setContratoActual(null);
            empleado.getContratos().clear();
            edit(empleado);
            em.remove(empleado);
            
            utx.commit();
        }finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        
        
        EntityManager em = null;

        try {

            em = getEntityManager();
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("El empleado ya no existe.", enfe);
            }

            empleado.setContratoActual(null);
            empleado.getContratos().clear();
            edit(empleado);

            utx.begin();
            em = getEntityManager();
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("El empleado ya no existe.", enfe);
            }

            em.remove(empleado);
            utx.commit();
        }finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleado> findEmpleadoEntities() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Empleado as o where o.borrado = false order by o.apellidos");
            return q.getResultList();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

   


    public List<Empleado> findEmpleadoEntities(String columnaFiltro, boolean ascendente) {
        String orderBy = " order by o." + columnaFiltro + " " + (ascendente ? "asc" : "desc");
         EntityManager em = getEntityManager();
            try {
                Query q = em.createQuery("select object(o) from Empleado as o where o.borrado = false" + orderBy);
                return q.getResultList();
            } finally {
                if(em!=null){
                    em.close();
                }
            }
    }

    public List<Contrato> findContratosVigentes() {
         EntityManager em = getEntityManager();
            try {
                Query q = em.createQuery("select object(o.contratoActual) from Empleado as o where o.contratoActual IS NOT NULL AND o.borrado=false");
                return q.getResultList();
            } finally {
                if(em!=null){
                    em.close();
                }
            }
    }

    public Empleado findEmpleado(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

     public Empleado findEmpleado(String dni) {
       if(dni==null||dni.isEmpty())
           return null;

       EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Empleado as o where o.dni= :dni AND o.borrado=false");
            q.setParameter("dni", dni);

            List<Empleado> listaEmpleados=q.getResultList();
            if(listaEmpleados.isEmpty())
                return null;
            else
                return listaEmpleados.get(0);
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Empleado as o where o.borrado=false").getSingleResult()).intValue();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

    public List<Empleado> findEmpleadosContratoActualFinalizaEnMenosDeUnMes(){
        EntityManager em = getEntityManager();
        try {
            Calendar c1 = Calendar.getInstance();
            c1.add(Calendar.MONTH, 1);

            Query q = em.createQuery("select object(o) from Empleado as o where (o.contratoActual IS NOT NULL) AND o.contratoActual.fechaFinContrato <= :fechaLimite AND o.borrado=false");
            q.setParameter("fechaLimite", c1.getTime());
            return q.getResultList();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

    public List<Empleado> findEmpleadoEntities(String texto){
        if(texto==null||texto.isEmpty())
            return Collections.emptyList();

        EntityManager em = getEntityManager();
        try {
            if(texto==null)
                texto="";
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
                                     "from Empleado as o " +
                                     "where (UPPER(o.nombre) like :texto) " +
                                        "OR (UPPER(o.apellidos) like :texto) " +
                                        "OR (UPPER(o.dni) like :texto) " +
                                        "order by o.apellidos");
            q.setParameter("texto", texto);
            return q.getResultList();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

    public List<Empleado> getEmpleadosEntitiesPorCategorias(){
        EntityManager em = getEntityManager();
        List<Empleado> estancia;

        List<Empleado> becarios;

        List<Empleado> funcionarios;

        List<Empleado> laborales;

        ArrayList<Empleado> todos=new ArrayList<Empleado>();
        try {
            Query q = em.createQuery("SELECT object(e) " +
                                     "FROM Empleado e JOIN e.contratoActual c JOIN c.tipoEmpleado t " +
                                     "where t.tipoEmpleado = 'Estancia' AND e.borrado=false " +
                                     "order by e.apellidos");

            estancia = q.getResultList();

            q = em.createQuery("SELECT object(e) " +
                                     "FROM Empleado e JOIN e.contratoActual c JOIN c.tipoEmpleado t " +
                                     "where t.tipoEmpleado = 'Becario' AND e.borrado=false " +
                                     "order by e.apellidos");

            becarios= q.getResultList();

            q = em.createQuery("SELECT object(e) " +
                                     "FROM Empleado e JOIN e.contratoActual c JOIN c.tipoEmpleado t JOIN t.puestoTrabajo p " +
                                     "where e.borrado=false " +
                                     "order by p.nombre, e.apellidos");


            funcionarios=q.getResultList();

            q = em.createQuery("SELECT object(e) " +
                                     "FROM Empleado e JOIN e.contratoActual c JOIN c.tipoEmpleado t JOIN t.categoria ca " +
                                     "where e.borrado=false " +
                                     "order by ca.nombre, e.apellidos");


            laborales=q.getResultList();

            todos.addAll(estancia);
            todos.addAll(becarios);
            todos.addAll(funcionarios);
            todos.addAll(laborales);
            return todos;

        } finally {
            if(em!=null){
                em.close();
            }
        }
    }


    public List<Contrato> findContratos(){
        EntityManager em = getEntityManager();
            try {

                Query q = em.createQuery(
                        "SELECT object(c) FROM Contrato AS c WHERE c.empleado IS NOT NULL ORDER BY c.empleado.apellidos");

                return q.getResultList();
            } finally {
                if(em!=null){
                    em.close();
                }
            }
    }
}
