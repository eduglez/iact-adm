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
import proyectos.modelo.LineaInvestigacion;


public class LineaInvestigacionJpaController {

    @PersistenceUnit(unitName = "IACT-ADM_UIPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<LineaInvestigacion> findLineaInvestigacionEntities() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from LineaInvestigacion as o");
            return q.getResultList();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

    public LineaInvestigacion findLineaInvestigacion(String nombre) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LineaInvestigacion.class, nombre);
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

   
    public int getLineaInvestigacionCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from LineaInvestigacion as o").getSingleResult()).intValue();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

}
