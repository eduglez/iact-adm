package personal.jpacontroller;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import personal.modelo.Contrato;



public class ContratoJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "IACT-ADM_UIPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

  
    public List<Contrato> findContratosVigentes() {
         EntityManager em = getEntityManager();
            try {

                Query q = em.createQuery(
                        "select object(c) from Empleado as o, Contrato as c where (o.contratoActual IS NOT NULL) and (o.contratoActual = c)");

                return q.getResultList();
            } finally {
                if(em!=null){
                em.close();
            }
            }
    }


    

    public Contrato findContrato(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contrato.class, id);
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }

    public int getContratoCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Contrato as o").getSingleResult()).intValue();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }


}
