package culturacientifica.jsfcontroller;

import culturacientifica.modelo.ActividadCientifica;
import java.util.ArrayList;
import java.util.List;

public class Financiacion {

    private int numPlanNacional;
    private long totalPlanNacional;
    private int numOtrosNacional;
    private long totalOtrosNacional;
    private int numUnionEuropea;
    private long totalUnionEuropea;
    private int numOtrosInternacional;
    private long totalOtrosInternacional;
    private int numComunidadesAutonomas;
    private long totalComunidadesAutonomas;
    private int numNoFinanciados;
    private List<ActividadCientifica> actividadesCientificas;

    public Financiacion() {
        numPlanNacional = 0;
        totalPlanNacional = 0;

        numOtrosNacional = 0;
        totalOtrosNacional = 0;

        numUnionEuropea = 0;
        totalUnionEuropea = 0;

        numOtrosInternacional = 0;
        totalOtrosInternacional = 0;

        numComunidadesAutonomas = 0;
        totalComunidadesAutonomas = 0;

        numNoFinanciados = 0;

        actividadesCientificas = new ArrayList<ActividadCientifica>();
    }

    public void computarActividadCientifica(ActividadCientifica ac) {
        if (ac.isFinanciadoPlanNacional()) {
            numPlanNacional++;
            totalPlanNacional += ac.getCantidad();
        } else if (ac.isFinanciadoOtrosNacional()) {
            numOtrosNacional++;
            totalOtrosNacional+=ac.getCantidad();
        } else if (ac.isFinanciadoUnionEuropea()) {
            numUnionEuropea++;
            totalUnionEuropea+=ac.getCantidad();
        } else if (ac.isFinanciadoOtrosInternacional()) {
            numOtrosInternacional++;
            totalOtrosInternacional+=ac.getCantidad();
        } else if (ac.isFinanciadoComunidadesAutonomas()) {
            numComunidadesAutonomas++;
            totalComunidadesAutonomas+=ac.getCantidad();
        }else if(ac.isNoFinanciado()){
            numNoFinanciados++;
        }

        actividadesCientificas.add(ac);
        
    }

    public List<ActividadCientifica> getActividadesCientificas() {
        return actividadesCientificas;
    }

    public int getNumComunidadesAutonomas() {
        return numComunidadesAutonomas;
    }

    public int getNumOtrosInternacional() {
        return numOtrosInternacional;
    }

    public int getNumOtrosNacional() {
        return numOtrosNacional;
    }

    public int getNumPlanNacional() {
        return numPlanNacional;
    }

    public int getNumUnionEuropea() {
        return numUnionEuropea;
    }

    public long getTotalComunidadesAutonomas() {
        return totalComunidadesAutonomas;
    }

    public long getTotalOtrosInternacional() {
        return totalOtrosInternacional;
    }

    public long getTotalOtrosNacional() {
        return totalOtrosNacional;
    }

    public long getTotalPlanNacional() {
        return totalPlanNacional;
    }

    public long getTotalUnionEuropea() {
        return totalUnionEuropea;
    }

    public int getNumNoFinanciados() {
        return numNoFinanciados;
    }


    public long getTotalFinanciacion(){
        return totalComunidadesAutonomas+totalOtrosInternacional+totalOtrosNacional+totalPlanNacional+totalUnionEuropea;
    }

    public int getTotalActividades(){
        return actividadesCientificas.size();
    }
}
