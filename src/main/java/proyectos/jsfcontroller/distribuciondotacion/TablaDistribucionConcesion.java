package proyectos.jsfcontroller.distribuciondotacion;

import java.util.logging.Level;
import java.util.logging.Logger;
import jpa.exceptions.NonexistentEntityException;
import jpa.exceptions.RollbackFailureException;
import proyectos.jsfcontroller.*;
import java.util.Calendar;
import java.util.Collections;
import javax.faces.model.SelectItem;
import jsf.util.JsfUtil;
import proyectos.modelo.Proyecto;
import proyectos.modelo.distribuciondotacion.Anualidad;
import proyectos.modelo.distribuciondotacion.AnualidadPartida;

public class TablaDistribucionConcesion {

    private Proyecto proyecto;
    private ProyectoController proyectoController;

    public TablaDistribucionConcesion(ProyectoController proyectoController) {
        this.proyectoController=proyectoController;
        this.proyecto = proyectoController.getProyecto();
    }

    private Anualidad anualidadSeleccionada;

    public Anualidad getAnualidadSeleccionada() {
        return anualidadSeleccionada;
    }

    public void setAnualidadSeleccionada(Anualidad anualidadSeleccionada) {
        this.anualidadSeleccionada = anualidadSeleccionada;
    }

    private AnualidadPartida anualidadPartidaSeleccionada;

    public AnualidadPartida getAnualidadPartidaSeleccionada() {
        return anualidadPartidaSeleccionada;
    }

    public void setAnualidadPartidaSeleccionada(AnualidadPartida anualidadPartidaSeleccionada) {
        this.anualidadPartidaSeleccionada = anualidadPartidaSeleccionada;
    }

    

    public void insertarAnualidad() {
       
        if(getAnualidadSeleccionada()==null)
            return ;
        for(AnualidadPartida ap:proyecto.getDatosEconomicos()){
            if(ap.getAnualidad().equals(getAnualidadSeleccionada()))
                    return;
        }

        AnualidadPartida ap = new AnualidadPartida();
        ap.setProyecto(proyecto);
        ap.setAnualidad(getAnualidadSeleccionada());
        proyecto.getDatosEconomicos().add(ap);
        try {
            proyectoController.getJpaController().createAnualidadPartida(ap);
        }catch (Exception ex) {
            Logger.getLogger(TablaDistribucionConcesion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void toggleMarcaBorrarAnualidad() {
        getAnualidadPartidaSeleccionada().setMarcaBorrado(!getAnualidadPartidaSeleccionada().isMarcaBorrado());
    }

  
    public long getTotalComplementosSalariales(){
        long total=0;
        for (AnualidadPartida ap : proyecto.getDatosEconomicos()) {
            total = total + ap.getComplementosSalariales();
        }
        return total;
    }

    public long getTotalContratacionPersonal(){
        long total=0;
        for (AnualidadPartida ap : proyecto.getDatosEconomicos()) {
            total = total + ap.getContratacionPersonal();
        }
        return total;
    }

    public long getTotalSeguridadSocial(){
        long total=0;
        for (AnualidadPartida ap : proyecto.getDatosEconomicos()) {
            total = total + ap.getSeguridadSocial();
        }
        return total;
    }

    public long getTotalFungible(){
        long total=0;
        for (AnualidadPartida ap : proyecto.getDatosEconomicos()) {
            total = total + ap.getFungible();
        }
        return total;
    }

    public long getTotalViajesDietas(){
        long total=0;
        for (AnualidadPartida ap : proyecto.getDatosEconomicos()) {
            total = total + ap.getViajesDietas();
        }
        return total;
    }

    public long getTotalOtrosGastos(){
        long total=0;
        for (AnualidadPartida ap : proyecto.getDatosEconomicos()) {
            total = total + ap.getOtrosGastos();
        }
        return total;
    }

    public long getTotalPersonal(){
        long total = 0;
        for (AnualidadPartida ap : proyecto.getDatosEconomicos()) {
            total = total + ap.getComplementosSalariales()+ap.getContratacionPersonal()+ap.getSeguridadSocial();
        }
        return total;
    }

    public long getTotalEjecucion(){
        long total = 0;
        for (AnualidadPartida ap : proyecto.getDatosEconomicos()) {
            total = total + ap.getInventariable()+ap.getFungible()+ap.getViajesDietas()+ap.getOtrosGastos();
        }
        return total;
    }

    public long getTotalIndirecto(){
        long total = 0;
        for (AnualidadPartida ap : proyecto.getDatosEconomicos()) {
            total = total + ap.getCostesIndirectos();
        }
        return total;
    }

    public long getTotalInventariable(){
        long total = 0;
        for (AnualidadPartida ap : proyecto.getDatosEconomicos()) {
            total = total + ap.getInventariable();
        }
        return total;
    }

    public long getTotalFuncionamiento(){
        long total = 0;
        for (AnualidadPartida ap : proyecto.getDatosEconomicos()) {
            total = total + ap.getFungible() + ap.getOtrosGastos();
        }
        return total;
    }

    public long getTotal(){
        long total=0;

        for (AnualidadPartida ap : proyecto.getDatosEconomicos()) {
            total = total +
                    ap.getComplementosSalariales()+
                    ap.getContratacionPersonal()+
                    ap.getCostesIndirectos()+
                    ap.getFungible()+
                    ap.getInventariable()+
                    ap.getOtrosGastos()+
                    ap.getSeguridadSocial()+
                    ap.getViajesDietas();
        }
        return total;
    }


/*
    public long getTotalGastoComplementosSalariales(){
        long total=0;
        for (AnualidadPartida ap : proyecto.getDatosEconomicos()) {
            total = total + ap.getGastoComplementosSalariales();
        }
        return total;
    }

    public long getTotalGastoContratacionPersonal(){
        long total=0;
        for (AnualidadPartida ap : proyecto.getDatosEconomicos()) {
            total = total + ap.getGastoContratacionPersonal();
        }
        return total;
    }

    public long getTotalGastoSeguridadSocial(){
        long total=0;
        for (AnualidadPartida ap : proyecto.getDatosEconomicos()) {
            total = total + ap.getGastoSeguridadSocial();
        }
        return total;
    }

    public long getTotalGastoFungible(){
        long total=0;
        for (AnualidadPartida ap : proyecto.getDatosEconomicos()) {
            total = total + ap.getGastoFungible();
        }
        return total;
    }

    public long getTotalGastoViajesDietas(){
        long total=0;
        for (AnualidadPartida ap : proyecto.getDatosEconomicos()) {
            total = total + ap.getGastoViajesDietas();
        }
        return total;
    }

    public long getTotalGastoOtrosGastos(){
        long total=0;
        for (AnualidadPartida ap : proyecto.getDatosEconomicos()) {
            total = total + ap.getGastoOtrosGastos();
        }
        return total;
    }

    public long getTotalGastoPersonal(){
        long total = 0;
        for (AnualidadPartida ap : proyecto.getDatosEconomicos()) {
            total = total + ap.getGastoComplementosSalariales()+ap.getGastoContratacionPersonal()+ap.getGastoSeguridadSocial();
        }
        return total;
    }

    public long getTotalGastoEjecucion(){
        long total = 0;
        for (AnualidadPartida ap : proyecto.getDatosEconomicos()) {
            total = total + ap.getGastoInventariable()+ap.getGastoFungible()+ap.getGastoViajesDietas()+ap.getGastoOtrosGastos();
        }
        return total;
    }

    public long getTotalGastoIndirecto(){
        long total = 0;
        for (AnualidadPartida ap : proyecto.getDatosEconomicos()) {
            total = total + ap.getGastoCostesIndirectos();
        }
        return total;
    }

    public long getTotalGastoInventariable(){
        long total = 0;
        for (AnualidadPartida ap : proyecto.getDatosEconomicos()) {
            total = total + ap.getGastoInventariable();
        }
        return total;
    }

    public long getTotalGastoFuncionamiento(){
        long total = 0;
        for (AnualidadPartida ap : proyecto.getDatosEconomicos()) {
            total = total + ap.getGastoFungible() + ap.getGastoOtrosGastos();
        }
        return total;
    }

    public long getTotalGasto(){
        long total=0;

        for (AnualidadPartida ap : proyecto.getDatosEconomicos()) {
            total = total +
                    ap.getGastoComplementosSalariales()+
                    ap.getGastoContratacionPersonal()+
                    ap.getGastoCostesIndirectos()+
                    ap.getGastoFungible()+
                    ap.getGastoInventariable()+
                    ap.getGastoOtrosGastos()+
                    ap.getGastoSeguridadSocial()+
                    ap.getGastoViajesDietas();
        }
        return total;
    }
*/
}
