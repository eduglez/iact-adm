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
import proyectos.modelo.distribuciondotacion.Anualidad;



public class AnualidadJpaController {
    @Resource
    private UserTransaction utx = null;

    @PersistenceUnit(unitName = "IACT-ADM_UIPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public void create(Anualidad anualidad) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(anualidad);
            utx.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }



    public void edit(Anualidad anualidad) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            anualidad = em.merge(anualidad);
            utx.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }



    public void destroy(String anioAnualidad) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Anualidad anualidad;
            try {
                anualidad = em.getReference(Anualidad.class, anioAnualidad);
                anualidad.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("La anualidad \"" + anioAnualidad + "\" no existe.", enfe);
            }
            em.remove(anualidad);
            utx.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Anualidad> findAnualidadEntities() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Anualidad as o");
            return q.getResultList();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }


    public Anualidad findAnualidad(String anioAnualidad) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Anualidad.class, anioAnualidad);
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

    public Anualidad getAnualidad(String anio){
        Anualidad a=findAnualidad(String.valueOf(anio));

        if(a==null){
            a=new Anualidad();
            a.setAnio(String.valueOf(anio));
            try {
                create(a);
            }catch (Exception ex) {
                
            }
        }

            return a;

    }
}
