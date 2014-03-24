package jsf.controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.TimeZone;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import rss.WorkersUtil;

public class LoginController {

    private String cambContAnt;

    private String cambContNueva1;

    private String cambContNueva2;

    private String cambEmailAnt;

    private String cambEmailNuevo;

    private String dni;

    private boolean gerencia;
    private boolean adm;
    private boolean secretaria;
    private boolean proyectos;
    private boolean personal;
    private boolean compras;
    private boolean servicios;
    private boolean investigador;
    private boolean culturaCientifica;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }


    public String getTimeZone(){
        return TimeZone.getDefault().getID();
    }

    public String getCambEmailAnt() {
        return cambEmailAnt;
    }

    public void setCambEmailAnt(String cambEmailAnt) {
        this.cambEmailAnt = cambEmailAnt;
    }

    public String getCambEmailNuevo() {
        return cambEmailNuevo;
    }

    public void setCambEmailNuevo(String cambEmailNuevo) {
        this.cambEmailNuevo = cambEmailNuevo;
    }

    public String getName(){
        return getCurrentContext().getExternalContext().getRemoteUser();
    }


    public boolean isGerencia(){
        return gerencia;
    }

    public boolean isAdm(){
        return adm;
    }

    public boolean isSecretaria(){
        return secretaria;
    }

    public boolean isServicios(){
        return servicios;
    }

    public boolean isProyectos(){
        return proyectos;
    }

    public boolean isPersonal(){
        return personal;
    }

    public boolean isCompras(){
        return compras;
    }

    public boolean isInvestigador(){
        return investigador;
    }

    public boolean isCulturaCientifica() {
        return culturaCientifica;
    }


    private FacesContext getCurrentContext(){
        return FacesContext.getCurrentInstance();
    }
    public LoginController() {

         Connection conn;
                Statement stat;
                ResultSet rs;
                String emailFromDataBase="";



        try {
            conn = WorkersUtil.getInstance().getConnection();
            stat = conn.createStatement();
            rs = stat.executeQuery("SELECT EMAIL, DNI FROM USERS WHERE LOGIN='"+getName()+"'");

            if (rs.next()) {
                    emailFromDataBase = rs.getString(1);
                    setDni(rs.getString(2));
                }

             setCambEmailAnt(emailFromDataBase);
             setCambEmailNuevo(emailFromDataBase);

             conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            jsf.util.JsfUtil.addErrorMessage("No se ha podido conectar con la base de datos.");

        }
                
               proyectos=false;gerencia=false;adm=false;secretaria=false;servicios=false;
               investigador=false;personal=false;compras=false;

                if(getCurrentContext().getExternalContext().isUserInRole("proyectos")){
                    proyectos=true;
                }else if(getCurrentContext().getExternalContext().isUserInRole("gerencia")){
                    gerencia=true;
                }else if(getCurrentContext().getExternalContext().isUserInRole("adm")){
                    adm=true;
                }else if(getCurrentContext().getExternalContext().isUserInRole("secretaria")){
                    secretaria=true;
                }else if(getCurrentContext().getExternalContext().isUserInRole("servicios")){
                    servicios=true;
                }else if(getCurrentContext().getExternalContext().isUserInRole("compras")){
                    compras=true;
                }else if(getCurrentContext().getExternalContext().isUserInRole("personal")){
                    personal=true;
                }else if(getCurrentContext().getExternalContext().isUserInRole("investigador")){
                    investigador=true;
                }else if(getCurrentContext().getExternalContext().isUserInRole("culturacientifica")){
                    culturaCientifica=true;
                }

    }

    public String logout(){
        HttpSession session = (HttpSession) getCurrentContext().getExternalContext().getSession(false);
        session.invalidate();
        
        return "logout";
    }

    public String getCambContAnt() {
        return cambContAnt;
    }

    public void setCambContAnt(String cambContAnt) {
        this.cambContAnt = cambContAnt;
    }

    public String getCambContNueva1() {
        return cambContNueva1;
    }

    public void setCambContNueva1(String cambContNueva1) {
        this.cambContNueva1 = cambContNueva1;
    }

    public String getCambContNueva2() {
        return cambContNueva2;
    }

    public void setCambContNueva2(String cambContNueva2) {
        this.cambContNueva2 = cambContNueva2;
    }


    public String cambiarEmail(){
        Connection conn;
        Statement stat;
        ResultSet rs;

                if(cambEmailNuevo==null||cambEmailNuevo.equals("")){
                    jsf.util.JsfUtil.addErrorMessage("No puede estar en blanco");
                    return null;
                }

        try {
            conn = WorkersUtil.getInstance().getConnection();
            stat = conn.createStatement();



            if(cambEmailAnt==null||!cambEmailAnt.equals(cambEmailNuevo)){
                stat.execute("UPDATE USERS SET EMAIL='"+cambEmailNuevo+"' WHERE LOGIN='"+getName()+"'");
                cambEmailAnt=cambEmailNuevo;
            }

            conn.close();
            jsf.util.JsfUtil.addErrorMessage("Email cambiados satisfactoriamente");
        } catch (Exception ex) {
            jsf.util.JsfUtil.addErrorMessage("No se ha podido conectar con la base de datos.");
            return null;
        }

      return null;


    }
    public String cambiarCont(){
                Connection conn;
                Statement stat;
                ResultSet rs;
                String passFromDataBase="";

                if(cambContNueva1==null||cambContNueva1.equals("")){
                    jsf.util.JsfUtil.addErrorMessage("No puede estar en blanco");
                    return null;
                }
                
                if(cambContNueva2==null||cambContNueva2.equals("")){
                    jsf.util.JsfUtil.addErrorMessage("No puede estar en blanco");
                    return null;
                }

                if(!cambContNueva1.equals(cambContNueva2)){
                    jsf.util.JsfUtil.addErrorMessage("Las dos nuevas contraseñas deben ser iguales");
                    return null;
                }



        try {
            conn = WorkersUtil.getInstance().getConnection();
            stat = conn.createStatement();
            rs = stat.executeQuery("SELECT PASSWORD FROM USERS WHERE LOGIN='"+getName()+"'");
            if (rs.next()) {
                    passFromDataBase = rs.getString(1);
                }


            System.out.println(passFromDataBase);
            System.out.println(toMD5(cambContAnt));

              if(!passFromDataBase.equals(toMD5(cambContAnt))){
            jsf.util.JsfUtil.addErrorMessage("Ha introducio contraseña no válida");
            return null;
        }


            stat.execute("UPDATE USERS SET PASSWORD='"+toMD5(cambContNueva1)+"' WHERE LOGIN='"+getName()+"'");

            conn.close();
            jsf.util.JsfUtil.addErrorMessage("Contraseña cambiada satisfactoriamente");
        } catch (Exception ex) {
            jsf.util.JsfUtil.addErrorMessage("No se ha podido conectar con la base de datos.");
            return null;
        }

      return null;
       




                 

    }
    

   private static final char[] HEXADECIMAL = { '0', '1', '2', '3',
        '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    private  String toMD5(String stringToHash)  {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(stringToHash.getBytes());
            StringBuilder sb = new StringBuilder(2 * bytes.length);
            for (int i = 0; i < bytes.length; i++) {
                int low = (int)(bytes[i] & 0x0f);
                int high = (int)((bytes[i] & 0xf0) >> 4);
                sb.append(HEXADECIMAL[high]);
                sb.append(HEXADECIMAL[low]);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            //exception handling goes here
            return null;
        }
    }


    
}
