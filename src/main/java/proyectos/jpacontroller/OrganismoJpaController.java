package proyectos.jpacontroller;

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
import proyectos.modelo.Organismo;

/**
 * Controlador que se encarga de manejar el adapatador de persistencia (JPA)
 * para la clase personal.modelo.Cuerpo.
 * La interfaz está compuesta por las operaciones Crear, Editar, Eliminar y las
 * distintas consultas sobre la coleción de personal.modelo.Cuerpo.
 * @author edu
 * @see personal.modelo.Cuerpo
 */
public class OrganismoJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "IACT-ADM_UIPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Registra para su persistencia un nuevo objeto del tipo personal.modelo.Cuerpo,
     * siendo la operación realizada dentro de una transacción.
     * @param cuerpo el Cuerpo de trabajadores del estado a persistir.
     * @throws RollbackFailureException en caso de fallo y que no se puedan deshacer los cambios en la BBDD.
     * Puede quedar en un estado de inconsistencia.
     * @throws Exception
     */
    public void create(Organismo organismo) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(organismo);
            utx.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Edita un objeto del tipo personal.modelo.Cuerpo ya registrado, siendo la
     * operación realizada dentro de una transacción.
     * @param cuerpo el cuerpo de trabajadores del estado que ha cambiado, y se quieren registrar los cambios.
     * @throws NonexistentEntityException en caso de que no exista el Cuerpo de trabajadores del estado.
     * @throws RollbackFailureException en caso de fallo y que no se puedan deshacer los cambios en la BBDD.
     * Puede quedar en un estado de inconsistencia.
     * @throws Exception
     */
    public void edit(Organismo organismo) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            organismo = em.merge(organismo);
            utx.commit();
        }finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Da de baja al objeto de tipo personal.modelo.Cuerpo registrado para persistencia,
     * siendo la operación realizada dentro de una transacción.
     * @param nombreCuerpo el nombre del Cuerpo de trabajadores del estado que identifica al objeto.
     * @throws NonexistentEntityException en caso de que no exista el Cuerpo de trabajadores del estado.
     * @throws RollbackFailureException en caso de fallo y que no se puedan deshacer los cambios en la BBDD.
     * Puede quedar en un estado de inconsistencia.
     * @throws Exception
     */
    public void destroy(String nombreOrganismo) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Organismo organismo;
            try {
                organismo = em.getReference(Organismo.class, nombreOrganismo);
                organismo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("El organismo con nombre \"" + nombreOrganismo + "\" no existe.", enfe);
            }
            em.remove(organismo);
            utx.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Devuelve todos los cuerpos de trabajadores del Estado registrados.
     * @return Lista con los cuerpos de trabajadoers del Estado.
     */
    public List<Organismo> findOrganismoEntities() {
        return findOrganismoEntities(true, -1, -1);
    }


    public List<Organismo> findOrganismoEntities(int maxResults, int firstResult) {
        return findOrganismoEntities(false, maxResults, firstResult);
    }

    private List<Organismo> findOrganismoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Organismo as o");
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

    /**
     * Devuelve el objeto que representa el cuerpo de trabajadores del estado que
     * coincide con el nombre introducido. Como no puede haber duplicaciones, solo
     * devolverá uno.
     * @param nombre el nombre del cuerpo de trabajadores del Estado.
     * @return Objeto personal.modelo.Cuerpo que representa al cuerpo de trabajadores
     * del estado con el nombre introducido.
     */
    public Organismo findOrganismo(String nombre) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Organismo.class, nombre);
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

    /**
     * Devuelve el número de cuerpos de trabajadores del estado registrados en el
     * adaptador de persistencia.
     * @return Número de objetos personal.modelo.Cuerpo registrados.
     */
    public int getOrganismoCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Organismo as o").getSingleResult()).intValue();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

}
