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
public class DeclaracionJuradaJAEDOC extends ContratoBasico{
    private String anio;

    public Resource getInforme(){
        String path=FacesContext.getCurrentInstance().getExternalContext().getInitParameter("jsf.util.REPORTS_PATH");
        InputStream in=null;
        ByteArrayOutputStream outStream= new ByteArrayOutputStream();
        try{
            File reportfile=new File(path+"\\personal\\contratos\\declaracionJuradaJAEDOC.jasper");
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

        h.put("ANO", getAnio());

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

    public DeclaracionJuradaJAEDOC(){
        super();
        setAnio(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)+"");
    }
}
