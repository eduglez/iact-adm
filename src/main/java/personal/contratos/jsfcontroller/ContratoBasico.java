/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package personal.contratos.jsfcontroller;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import javax.faces.context.FacesContext;
import personal.jsfcontroller.EmpleadoController;
import personal.modelo.Empleado;

/**
 *
 * @author edu
 */
public class ContratoBasico {
    private String nombre;
    private String apellidos;
    private String dni;
    private Empleado empleado;
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @param apellidos the apellidos to set
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * @return the dni
     */
    public String getDni() {
        return dni;
    }

    /**
     * @param dni the dni to set
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    public ConcurrentHashMap getParametrosInforme(){
        ConcurrentHashMap h=new ConcurrentHashMap();

        h.put("NOMBRE", getNombre());
        h.put("APELLIDOS", getApellidos());
        h.put("DNI", getDni());
        return h;
    }

    public ContratoBasico(){
        EmpleadoController e=(EmpleadoController)FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(),null,"personal");
        if(e!=null){
            empleado=e.getEmpleado();
            setNombre(e.getEmpleado().getNombre());
            setApellidos(e.getEmpleado().getApellidos());
            setDni(e.getEmpleado().getDni());
        }
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    
}
