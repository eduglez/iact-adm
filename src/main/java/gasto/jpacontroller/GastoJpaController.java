package gasto.jpacontroller;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;

import gasto.modelo.*;
import java.util.Collection;
import java.util.List;
import javax.persistence.Query;
import jpa.exceptions.RollbackFailureException;

public class GastoJpaController {

    // <editor-fold defaultstate="collapsed" desc="jpaController">
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "IACT-ADM_UIPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }// </editor-fold>


    public AsignacionGasto findAsignacionGasto(AsignacionGasto asignacion) {
        EntityManager em = getEntityManager();
        String selectQuery=
                "SELECT object(a) " +
                "FROM AsignacionGasto AS a " +
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

        Query q = em.createQuery(selectQuery);

        System.out.println(selectQuery);

        List<AsignacionGasto> listaMovimientos = q.getResultList();

        em.close();

        if (!listaMovimientos.isEmpty()) {
            return listaMovimientos.get(0);
        } else {
            return null;
        }

    }

    public void create(AsignacionGasto asignacion) throws RollbackFailureException, Exception{
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

    public void edit(AsignacionGasto asignacion) throws RollbackFailureException, Exception{
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




    public GastoRealizado findGastoRealizado(Long id) {
        EntityManager em = getEntityManager();

        GastoRealizado gastoRealizadoFound=em.find(GastoRealizado.class, id);
        em.close();

        return gastoRealizadoFound;

    }

    public void create(GastoRealizado gastoRealizado) throws RollbackFailureException, Exception{
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(gastoRealizado);
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

    public void edit(GastoRealizado gastoRealizado) throws RollbackFailureException, Exception{
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.merge(gastoRealizado);
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

    public Collection<GastoRealizado> findGastosRealizados() {
        EntityManager em = getEntityManager();

        Query q = em.createQuery(
                "SELECT object(m) " +
                "FROM GastoRealizado m " +
                "ORDER BY m.fecha DESC");

        List<GastoRealizado> listaGastos = q.getResultList();

        em.close();

        return listaGastos;

    }


    public Gasto findGasto(Long id) {
        EntityManager em = getEntityManager();

        Gasto gastoFound=em.find(Gasto.class, id);
        em.close();

        return gastoFound;

    }

    public void create(Gasto gasto) throws RollbackFailureException, Exception{
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(gasto);
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

    public void edit(Gasto gasto) throws RollbackFailureException, Exception{
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.merge(gasto);
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

    public List<Gasto> findGastos() {
       EntityManager em = getEntityManager();
       
        Query q = em.createQuery(
                "SELECT object(m) " +
                "FROM Gasto AS m");

        List<Gasto> gastos = q.getResultList();

        em.close();

        return gastos;

    }

    public Collection<GastoRealizado> findUltimosGastosRealizados(int numGastos){
        EntityManager em = getEntityManager();

        Query q = em.createQuery(
                "SELECT object(m) " +
                "FROM GastoRealizado m " +
                "ORDER BY m.fecha DESC");

        q.setMaxResults(numGastos);
        List<GastoRealizado> listaGastos = q.getResultList();

        em.close();

        return listaGastos;
    }

    
}
