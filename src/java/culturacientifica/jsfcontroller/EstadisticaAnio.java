package culturacientifica.jsfcontroller;

import culturacientifica.modelo.ActividadCientifica;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class EstadisticaAnio {

    private ConcurrentHashMap<String, Financiacion> actividadesCientificasFinanciacion;
    private int anio;

    public int getAnio() {
        return anio;
    }

    public int getNumFinanciacionComunidadesAutonomas() {
        int total=0;
        for(Financiacion f:actividadesCientificasFinanciacion.values()){
            total+=f.getNumComunidadesAutonomas();
        }
        return total;
    }

    public int getNumFinanciacionOtrosInternacional() {
        int total=0;
        for(Financiacion f:actividadesCientificasFinanciacion.values()){
            total+=f.getNumOtrosInternacional();
        }
        return total;
    }

    public int getNumFinanciacionOtrosNacional() {
        int total=0;
        for(Financiacion f:actividadesCientificasFinanciacion.values()){
            total+=f.getNumOtrosNacional();
        }
        return total;
    }

    public int getNumFinanciacionPlanNacional() {
        int total=0;
        for(Financiacion f:actividadesCientificasFinanciacion.values()){
            total+=f.getNumPlanNacional();
        }
        return total;
    }

    public int getNumFinanciacionUnionEuropea() {
        int total=0;
        for(Financiacion f:actividadesCientificasFinanciacion.values()){
            total+=f.getNumUnionEuropea();
        }
        return total;
    }

    public int getNumNoFinanciados() {
        int total=0;
        for(Financiacion f:actividadesCientificasFinanciacion.values()){
            total+=f.getNumNoFinanciados();
        }
        return total;
    }

    public int getNum(){
        return getNumFinanciacionComunidadesAutonomas()
                +getNumFinanciacionOtrosInternacional()
                +getNumFinanciacionOtrosNacional()
                +getNumFinanciacionPlanNacional()
                +getNumFinanciacionUnionEuropea();
    }



    public long getTotalFinanciacionComunidadesAutonomas() {
        long total=0;
        for(Financiacion f:actividadesCientificasFinanciacion.values()){
            total+=f.getTotalComunidadesAutonomas();
        }
        return total;
    }

    public long getTotalFinanciacionOtrosInternacional() {
        long total=0;
        for(Financiacion f:actividadesCientificasFinanciacion.values()){
            total+=f.getTotalOtrosInternacional();
        }
        return total;
    }

    public long getTotalFinanciacionOtrosNacional() {
        long total=0;
        for(Financiacion f:actividadesCientificasFinanciacion.values()){
            total+=f.getTotalOtrosNacional();
        }
        return total;
    }

    public long getTotalFinanciacionPlanNacional() {
        long total=0;
        for(Financiacion f:actividadesCientificasFinanciacion.values()){
            total+=f.getTotalPlanNacional();
        }
        return total;
    }

    public long getTotalFinanciacionUnionEuropea() {
        long total=0;
        for(Financiacion f:actividadesCientificasFinanciacion.values()){
            total+=f.getTotalUnionEuropea();
        }
        return total;
    }

    public long getTotal(){
        return getTotalFinanciacionComunidadesAutonomas()
                +getTotalFinanciacionOtrosInternacional()
                +getTotalFinanciacionOtrosNacional()
                +getTotalFinanciacionPlanNacional()
                +getTotalFinanciacionUnionEuropea();
    }

    public void computarActividadCientifica(ActividadCientifica ac) {

        Calendar calendario = Calendar.getInstance();
        if (ac.getFechaRepresentativa() != null) {
            calendario.setTime(ac.getFechaRepresentativa());
            if (calendario.get(Calendar.YEAR) == anio) {
                if (actividadesCientificasFinanciacion.containsKey(ac.getTipo())) {
                    actividadesCientificasFinanciacion.get(ac.getTipo()).computarActividadCientifica(ac);
                }
            }


        }
    }

    public EstadisticaAnio(int anio) {
        this.anio = anio;
        actividadesCientificasFinanciacion = new ConcurrentHashMap<String, Financiacion>();

        for (int i = 0; i < ActividadCientifica.ACTIVIDADES.length; i++) {
            actividadesCientificasFinanciacion.put(ActividadCientifica.ACTIVIDADES[i], new Financiacion());
        }
    }

    public Financiacion getFinanciacionExposiciones(){
        return actividadesCientificasFinanciacion.get(ActividadCientifica.EXPOSICION);
    }

    public Financiacion getFinanciacionCDDVD(){
        return actividadesCientificasFinanciacion.get(ActividadCientifica.CD_DVD);
    }

    public Financiacion getFinanciacionDiasPuertasAbiertas(){
        return actividadesCientificasFinanciacion.get(ActividadCientifica.DIA_PUERTAS_ABIERTAS);
    }

    public Financiacion getFinanciacionConferencias(){
        return actividadesCientificasFinanciacion.get(ActividadCientifica.CONFERENCIA);
    }

    public Financiacion getFinanciacionRutasCientificas(){
        return actividadesCientificasFinanciacion.get(ActividadCientifica.RUTA_CIENTIFICA);
    }

    public Financiacion getFinanciacionTalleres(){
        return actividadesCientificasFinanciacion.get(ActividadCientifica.TALLER);
    }

    public Financiacion getFinanciacionFerias(){
        return actividadesCientificasFinanciacion.get(ActividadCientifica.FERIA);
    }

    public Financiacion getFinanciacionSemanasCiencia(){
        return actividadesCientificasFinanciacion.get(ActividadCientifica.SEMANA_CIENCIA);
    }

    public Financiacion getFinanciacionOtrasActividades(){
        return actividadesCientificasFinanciacion.get(ActividadCientifica.OTRA_ACTIVIDAD);
    }

    public List<ActividadCientifica> getActividades(){
        ArrayList<ActividadCientifica> actividades=new ArrayList<ActividadCientifica>();
        actividades.addAll(getFinanciacionExposiciones().getActividadesCientificas());
        actividades.addAll(getFinanciacionCDDVD().getActividadesCientificas());
        actividades.addAll(getFinanciacionDiasPuertasAbiertas().getActividadesCientificas());
        actividades.addAll(getFinanciacionConferencias().getActividadesCientificas());
        actividades.addAll(getFinanciacionRutasCientificas().getActividadesCientificas());
        actividades.addAll(getFinanciacionTalleres().getActividadesCientificas());
        actividades.addAll(getFinanciacionFerias().getActividadesCientificas());
        actividades.addAll(getFinanciacionSemanasCiencia().getActividadesCientificas());
        actividades.addAll(getFinanciacionOtrasActividades().getActividadesCientificas());

        return actividades;
    }
}
