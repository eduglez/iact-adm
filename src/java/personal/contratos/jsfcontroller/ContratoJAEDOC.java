/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package personal.contratos.jsfcontroller;

import com.icesoft.faces.context.ByteArrayResource;
import com.icesoft.faces.context.Resource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import personal.jsfcontroller.EmpleadoController;

/**
 *
 * @author edu
 */
public class ContratoJAEDOC extends ContratoBasico{
    private String anio;
    private boolean tiempoCompleto;
    private String cifEmpresa;
    private String nombreContratador;
    private String apellidosContratador;
    private String nifContratador;
    private String conceptoContratador1;
    private String conceptoContratador2;
    private String paisCentroTrabajo;
    private String nombreCentroTrabajo;
    private String municipio;
    private String provincia;
    private String apellidosTrabajador;
    private String nombreTrabajador;
    private String nifTrabajador;
    private String fechaNacimientoTrabajador;
    private String numeroSSTrabajdor;
    private String nivelFormativoTrabajador;

    private String nacionalidadTrabajador;
    private String domicilioTrabajador;
    private String municipioTrabajador;
    private String provinciaTrabajador;
    private String nombreAsistenteLegal;
    private String apellidosAsistenteLegal;
    private String dniAsistenteLegal;
    private String calidadAsistenteLegal;
    private String expensorCertificadoMinusvalia;

    public Resource getInforme(){
        String path=FacesContext.getCurrentInstance().getExternalContext().getInitParameter("jsf.util.REPORTS_PATH");
        InputStream in=null;
        ByteArrayOutputStream outStream= new ByteArrayOutputStream();
        try{
            File reportfile=new File(path+"\\personal\\contratos\\contratoJAEDOC.jasper");
            in=new FileInputStream(reportfile);

            JasperRunManager.runReportToPdfStream(in,outStream,getParametrosInforme());
            return new ByteArrayResource(outStream.toByteArray());
        }catch(Exception e){
            e.printStackTrace();

        }finally{


        }

        return null;
    }
    @Override
    public ConcurrentHashMap getParametrosInforme(){
        ConcurrentHashMap h=super.getParametrosInforme();

        h.put("AÑO_DOCTORES", getAnio());
        h.put("ES_TIEMPO_COMLPETO", new Boolean(isTiempoCompleto()));
        h.put("CIF_EMPRESA", getCifEmpresa());
        h.put("NOMBRE_CONTRATADOR", getNombreContratador());
        h.put("APELLIDOS_CONTRATADOR", getApellidosContratador());
        h.put("NIF_CONTRATADOR", getNifContratador());
        h.put("CONCEPTO_CONTRATADOR_1", getConceptoContratador1());
        h.put("CONCEPTO_CONTRATADOR_2", getConceptoContratador2());
        h.put("PAIS_CENTRO_TRABAJO", getPaisCentroTrabajo());
        h.put("NOMBRE_CENTRO_TRABAJO", getNombreCentroTrabajo());
        h.put("MUNICIPIO", getMunicipio());
        h.put("PROVINCIA", getProvincia());
        h.put("APELLIDOS_TRABAJADOR", getApellidosTrabajador());
        h.put("NOMBRE_TRABAJADOR", getNombreTrabajador());
        h.put("NIF_TRABAJADOR", getNifTrabajador());
        h.put("FECHA_NACIMIENTO_TRABAJADOR", getFechaNacimientoTrabajador());
        h.put("NUMERO_SS_TRABAJADOR", getNumeroSSTrabajdor());
        h.put("NIVEL_FORMATIVO_TRABAJADOR", getNivelFormativoTrabajador());

        h.put("NACIONALIDAD_TRABAJADOR", getNacionalidadTrabajador());
        h.put("DOMICILIO_TRABAJADOR", getDomicilioTrabajador());
        h.put("MUNICIPIO_TRABAJADOR", getMunicipioTrabajador());
        h.put("PROVINCIA_TRABAJADOR", getProvinciaTrabajador());
        h.put("NOMBRE_ASISTENTE_LEGAL", getNombreAsistenteLegal());
        h.put("APELLIDOS_ASISTENTE_LEGAL", getApellidosAsistenteLegal());
        h.put("DNI_ASISTENTE_LEGAL", getDniAsistenteLegal());
        h.put("CALIDAD_ASISTENTE_LEGAL", getCalidadAsistenteLegal());
        h.put("EXPENSOR_CERTIFICADO_MINUSVALIA", getExpensorCertificadoMinusvalia());
        return h;
    }


    /**
     * @return the anio
     */
    public String getAnio() {
        return anio;
    }

    /**
     * @param anio the anio to set
     */
    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getApellidosAsistenteLegal() {
        return apellidosAsistenteLegal;
    }

    public void setApellidosAsistenteLegal(String apellidosAsistenteLegal) {
        this.apellidosAsistenteLegal = apellidosAsistenteLegal;
    }

    public String getApellidosContratador() {
        return apellidosContratador;
    }

    public void setApellidosContratador(String apellidosContratador) {
        this.apellidosContratador = apellidosContratador;
    }

    public String getApellidosTrabajador() {
        return apellidosTrabajador;
    }

    public void setApellidosTrabajador(String apellidosTrabajador) {
        this.apellidosTrabajador = apellidosTrabajador;
    }

    public String getCalidadAsistenteLegal() {
        return calidadAsistenteLegal;
    }

    public void setCalidadAsistenteLegal(String calidadAsistenteLegal) {
        this.calidadAsistenteLegal = calidadAsistenteLegal;
    }

    public String getCifEmpresa() {
        return cifEmpresa;
    }

    public void setCifEmpresa(String cifEmpresa) {
        this.cifEmpresa = cifEmpresa;
    }

    public String getConceptoContratador2() {
        return conceptoContratador2;
    }

    public void setConceptoContratador2(String conceptoContratador2) {
        this.conceptoContratador2 = conceptoContratador2;
    }

    public String getConceptoContratador1() {
        return conceptoContratador1;
    }

    public void setConceptoContratador1(String concpetoContratador1) {
        this.conceptoContratador1 = concpetoContratador1;
    }

    public String getDniAsistenteLegal() {
        return dniAsistenteLegal;
    }

    public void setDniAsistenteLegal(String dniAsistenteLegal) {
        this.dniAsistenteLegal = dniAsistenteLegal;
    }

    public String getDomicilioTrabajador() {
        return domicilioTrabajador;
    }

    public void setDomicilioTrabajador(String domicilioTrabajador) {
        this.domicilioTrabajador = domicilioTrabajador;
    }

    public String getExpensorCertificadoMinusvalia() {
        return expensorCertificadoMinusvalia;
    }

    public void setExpensorCertificadoMinusvalia(String expensorCertificadoMinusvalia) {
        this.expensorCertificadoMinusvalia = expensorCertificadoMinusvalia;
    }

    public String getFechaNacimientoTrabajador() {
        return fechaNacimientoTrabajador;
    }

    public void setFechaNacimientoTrabajador(String fechaNacimientoTrabajador) {
        this.fechaNacimientoTrabajador = fechaNacimientoTrabajador;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getMunicipioTrabajador() {
        return municipioTrabajador;
    }

    public void setMunicipioTrabajador(String municipioTrabajador) {
        this.municipioTrabajador = municipioTrabajador;
    }

    public String getNacionalidadTrabajador() {
        return nacionalidadTrabajador;
    }

    public void setNacionalidadTrabajador(String nacionalidadTrabajador) {
        this.nacionalidadTrabajador = nacionalidadTrabajador;
    }

    public String getNifContratador() {
        return nifContratador;
    }

    public void setNifContratador(String nifContratador) {
        this.nifContratador = nifContratador;
    }

    public String getNifTrabajador() {
        return nifTrabajador;
    }

    public void setNifTrabajador(String nifTrabajador) {
        this.nifTrabajador = nifTrabajador;
    }

    public String getNivelFormativoTrabajador() {
        return nivelFormativoTrabajador;
    }

    public void setNivelFormativoTrabajador(String nivelFormativoTrabajador) {
        this.nivelFormativoTrabajador = nivelFormativoTrabajador;
    }

    public String getNombreAsistenteLegal() {
        return nombreAsistenteLegal;
    }

    public void setNombreAsistenteLegal(String nombreAsistenteLegal) {
        this.nombreAsistenteLegal = nombreAsistenteLegal;
    }

    public String getNombreCentroTrabajo() {
        return nombreCentroTrabajo;
    }

    public void setNombreCentroTrabajo(String nombreCentroTrabajo) {
        this.nombreCentroTrabajo = nombreCentroTrabajo;
    }

    public String getNombreContratador() {
        return nombreContratador;
    }

    public void setNombreContratador(String nombreContratador) {
        this.nombreContratador = nombreContratador;
    }

    public String getNombreTrabajador() {
        return nombreTrabajador;
    }

    public void setNombreTrabajador(String nombreTrabajador) {
        this.nombreTrabajador = nombreTrabajador;
    }

    public String getNumeroSSTrabajdor() {
        return numeroSSTrabajdor;
    }

    public void setNumeroSSTrabajdor(String numeroSSTrabajdor) {
        this.numeroSSTrabajdor = numeroSSTrabajdor;
    }

    public String getPaisCentroTrabajo() {
        return paisCentroTrabajo;
    }

    public void setPaisCentroTrabajo(String paisCentroTrabajo) {
        this.paisCentroTrabajo = paisCentroTrabajo;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getProvinciaTrabajador() {
        return provinciaTrabajador;
    }

    public void setProvinciaTrabajador(String provinciaTrabajador) {
        this.provinciaTrabajador = provinciaTrabajador;
    }

    public boolean isTiempoCompleto() {
        return tiempoCompleto;
    }

    public void setTiempoCompleto(boolean tiempoCompleto) {
        this.tiempoCompleto = tiempoCompleto;
    }

    public ContratoJAEDOC(){
        super();
        setAnio(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)+"");
        setTiempoCompleto(true);
        setCifEmpresa("Q-2818002 D");
        setNombreContratador("EUSEBIO");
        setApellidosContratador("JIMÉNEZ ARROYO");
        setNifContratador("00694544 J");
        setConceptoContratador1("EL SECRETARIO GENERAL");
        setConceptoContratador2("(P.D. del Presidente del CSIC. Resolución 20-02-2008");
        setNivelFormativoTrabajador("DOCTOR");
        setNombreTrabajador(getEmpleado().getNombre());
        setApellidosTrabajador(getEmpleado().getApellidos());
        setFechaNacimientoTrabajador(getEmpleado().getFechaNacimiento().toString());
        setNumeroSSTrabajdor(getEmpleado().getNumSeguridadSocial());
        setDomicilioTrabajador(getEmpleado().getDireccion());


    }
}
