package rss;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Feeder extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/xml;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String query;
        if((query=request.getParameter("query"))!=null){
            if(query.equals("contratos_finaliza_mes")){
                contratosMenosUnMesToRSS(out);
            }else if(query.equals("proyectos_justificar_mes")){
                proyectosJustificacionMenosUnMesToRSS(out);
            }else if(query.equals("pedidos_pendiente_aceptacion")){
                pedidosPendienteAceptacion(out);
            }else if(query.equals("proyectos_finalizados_vigentes")){
                proyectosFinalizadosQueSonVigentes(out);
            }

        }
        out.close();

    }

    private void contratosMenosUnMesToRSS(PrintWriter out){
        ResultSet rs;
        Connection conn = null;
        Statement stat=null;
        try {
            conn = WorkersUtil.getInstance().getConnection();
            stat = conn.createStatement();


            Calendar c1 = Calendar.getInstance();
            c1.add(Calendar.MONTH, 1);

            PreparedStatement psContratos = conn.prepareStatement(
                    "SELECT NOMBRE, " +
                    "APELLIDOS, " +
                    "DNI, " +
                    "EMPLEADO.TELEFONO, " +
                    "CONTRATO.TELEFONO, " +
                    "CONTRATO.EMAIL, " +
                    "CONTRATO.FECHAFINCONTRATO, EMPLEADO.ID " +
                    "FROM EMPLEADO, CONTRATO " +
                    "WHERE " +
                    "(EMPLEADO.CONTRATOACTUAL_ID " +
                    "= CONTRATO.ID) " +
                    "AND " +
                    "(CONTRATO.FECHAFINCONTRATO <= ?)");

            psContratos.setDate(1, new java.sql.Date(c1.getTimeInMillis()));
            out.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            out.println("<rss xmlns:sy=\"http://purl.org/rss/1.0/modules/syndication/\" version=\"2.0\">");
            out.println("<channel>");
            out.println("<title>Empleados menos de un mes</title>");
            out.println("<link>http://intranet.iact.csic.es/iact-adm/</link>");
            out.println("<description>Empleados a los que le falta menos de un mes para finalizar contrato</description>");
            out.println("<sy:updatePeriod> daily </sy:updatePeriod>");
            out.println("<sy:updateFrequency> 2 </sy:updateFrequency>");
            out.println("<sy:updateBase> 2010-03-05+1:00-11:39 </sy:updateBase>");
//out.println("<lastBuildDate>Mon, 12 Sep 2005 18:37:00 GMT</lastBuildDate>");
            out.println("<language>es</language>");


            if (psContratos.execute()) {
                rs = psContratos.getResultSet();

                String nombre, apellidos, dni, telefono_personal, telefono, email, fechaFinContrato;
                String contenidoMensaje = "";
                long id;
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

                while (rs.next()) {
                    nombre = rs.getString(1);
                    apellidos = rs.getString(2);
                    dni = rs.getString(3);
                    telefono_personal = rs.getString(4);
                    telefono = rs.getString(5);
                    email = rs.getString(6);
                    fechaFinContrato = dateFormatter.format(rs.getDate(7));
                    id=rs.getLong(8);
                    contenidoMensaje += apellidos + ", " + nombre + " (" + dni + ")&lt;br/&gt;";
                    contenidoMensaje += "CONTRATO FINALIZA: " + fechaFinContrato + "&lt;br/&gt;";
                    contenidoMensaje += "CONTACTO: &lt;br/&gt;";
                    contenidoMensaje += "Telefono personal: " + telefono_personal + "&lt;br/&gt;";
                    contenidoMensaje += "Telefono: " + telefono + "&lt;br/&gt;";
                    contenidoMensaje += "EMail: " + email + "&lt;br/&gt;";

                    out.println("<item>");
                    out.println("<title>" + apellidos + ", " + nombre + " -> " + fechaFinContrato + "</title>");

                    out.println("<description>");
                    out.println(contenidoMensaje);
                    out.println("</description>");
                    out.println("<link>http://intranet.iact.csic.es/iact-adm/personal/ver/?id="+id+"</link>");
                    out.println("</item>");

                    contenidoMensaje = "";
                }



            }
            out.println("</channel>");
            out.println("</rss>");


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }

        }
    }

    private void proyectosJustificacionMenosUnMesToRSS(PrintWriter out){
        ResultSet rs;
        Connection conn = null;
        Statement stat=null;
        try {
            conn = WorkersUtil.getInstance().getConnection();
            stat = conn.createStatement();


            Calendar c1 = Calendar.getInstance();
                    c1.add(Calendar.MONTH, 1);

                    PreparedStatement psContratos = conn.prepareStatement(
                            "SELECT PROYECTO.ID, " +
                                "REFERENCIA, " +
                                "TITULO, " +
                                "OBSERVACIONES, " +
                                "FECHAFINPLAZOPROXIMAJUSTIFICACION " +
                            "FROM PROYECTO, JUSTIFICACION " +
                            "WHERE " +
                                    "(PROYECTO.ID " +
                                        "= JUSTIFICACION.PROYECTO_ID) " +
                                "AND " +
                                    "(FECHAFINPLAZOPROXIMAJUSTIFICACION <= ?) " +
                                "AND " +
                                    "(FECHAJUSTIFICACION IS NULL)");

                    psContratos.setDate(1, new java.sql.Date(c1.getTimeInMillis()));
            out.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            out.println("<rss xmlns:sy=\"http://purl.org/rss/1.0/modules/syndication/\" version=\"2.0\">");
            out.println("<channel>");
            out.println("<title>Proyectos a Justificar</title>");
            out.println("<link>http://intranet.iact.csic.es/iact-adm/</link>");
            out.println("<description>Proyectos que hay que justificar en menos de 1 mes</description>");
            out.println("<sy:updatePeriod> daily </sy:updatePeriod>");
            out.println("<sy:updateFrequency> 2 </sy:updateFrequency>");
            out.println("<sy:updateBase> 2010-03-05+1:00-11:39 </sy:updateBase>");
//out.println("<lastBuildDate>Mon, 12 Sep 2005 18:37:00 GMT</lastBuildDate>");
            out.println("<language>es</language>");


            if (psContratos.execute()) {
                        rs = psContratos.getResultSet();

                        String id, referencia, titulo, observaciones, fechaPlazo;
                        String contenidoMensaje="";

                        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

                        while (rs.next()) {
                            id = rs.getString(1);
                            referencia = rs.getString(2);
                            titulo = rs.getString(3);
                            observaciones = rs.getString(4);
                            fechaPlazo=dateFormatter.format(rs.getDate(5));

                            contenidoMensaje+="PROYECTO ID: "+id+"&lt;br/&gt;";
                            contenidoMensaje+="REFERENCIA: "+referencia+"&lt;br/&gt;";
                            contenidoMensaje+="TITULO: "+titulo+"&lt;br/&gt;";
                            contenidoMensaje+="OBSERVACIONES: "+observaciones+"\n";
                            contenidoMensaje+="FECHA PROXIMA JUSTIFICACION: "+fechaPlazo+"&lt;br/&gt;";
                            contenidoMensaje+="\n\n";

                    out.println("<item>");
                    out.println("<title>" + referencia+ " -> " + fechaPlazo + "</title>");

                    out.println("<description>");
                    out.println(contenidoMensaje);
                    out.println("</description>");
                    out.println("<link>http://intranet.iact.csic.es/iact-adm/proyectos/ver/?id="+id+"</link>");
                    out.println("</item>");
                    contenidoMensaje = "";
                }



            }
            out.println("</channel>");
            out.println("</rss>");


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }

        }
    }

    private void proyectosFinalizadosQueSonVigentes(PrintWriter out){
        ResultSet rs;
        Connection conn = null;
        Statement stat=null;
        try {
            conn = WorkersUtil.getInstance().getConnection();
            stat = conn.createStatement();


            Calendar c1 = Calendar.getInstance();


                    PreparedStatement psContratos = conn.prepareStatement(
                            "SELECT PROYECTO.ID, " +
                                "REFERENCIA, " +
                                "TITULO, " +
                                "OBSERVACIONES, " +
                                "FECHAFIN, ESTADOPROYECTO_NOMBRE " +
                            "FROM PROYECTO " +
                            "WHERE (FECHAFIN <= ?) AND ESTADOPROYECTO_NOMBRE='Vigente'");

                    psContratos.setDate(1, new java.sql.Date(c1.getTimeInMillis()));
            out.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            out.println("<rss xmlns:sy=\"http://purl.org/rss/1.0/modules/syndication/\" version=\"2.0\">");
            out.println("<channel>");
            out.println("<title>Proyectos Finalizados Vigentes</title>");
            out.println("<link>http://intranet.iact.csic.es/iact-adm/</link>");
            out.println("<description>Proyectos que han finalizado y aun son vigentes</description>");
            out.println("<sy:updatePeriod> daily </sy:updatePeriod>");
            out.println("<sy:updateFrequency> 2 </sy:updateFrequency>");
            out.println("<sy:updateBase> 2010-03-05+1:00-11:39 </sy:updateBase>");
//out.println("<lastBuildDate>Mon, 12 Sep 2005 18:37:00 GMT</lastBuildDate>");
            out.println("<language>es</language>");


            if (psContratos.execute()) {
                        rs = psContratos.getResultSet();

                        String id, referencia, titulo, observaciones, fechaPlazo;
                        String contenidoMensaje="";
                        String estado;
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

                        while (rs.next()) {
                            id = rs.getString(1);
                            referencia = rs.getString(2);
                            titulo = rs.getString(3);
                            observaciones = rs.getString(4);
                            fechaPlazo=dateFormatter.format(rs.getDate(5));
                            estado=rs.getString(6);
                            contenidoMensaje+="PROYECTO ID: "+id+"&lt;br/&gt;";
                            contenidoMensaje+="REFERENCIA: "+referencia+"&lt;br/&gt;";
                            contenidoMensaje+="TITULO: "+titulo+"&lt;br/&gt;";
                            contenidoMensaje+="ESTADO: "+estado+"&lt;br/&gt;";
                            contenidoMensaje+="OBSERVACIONES: "+observaciones+"\n";
                            contenidoMensaje+="FECHA FINALIZACION: "+fechaPlazo+"&lt;br/&gt;";
                            contenidoMensaje+="\n\n";

                    out.println("<item>");
                    out.println("<title>" + referencia+ " -> " + fechaPlazo + "</title>");

                    out.println("<description>");
                    out.println(contenidoMensaje);
                    out.println("</description>");
                    out.println("<link>http://intranet.iact.csic.es/iact-adm/proyectos/ver/?id="+id+"</link>");
                    out.println("</item>");
                    contenidoMensaje = "";
                }



            }
            out.println("</channel>");
            out.println("</rss>");


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }

        }
    }

    private void pedidosPendienteAceptacion(PrintWriter out){
        ResultSet rs;
        Connection conn = null;
        Statement stat=null;
        try {
            conn = WorkersUtil.getInstance().getConnection();
            stat = conn.createStatement();

            PreparedStatement psContratos = conn.prepareStatement(
                    "SELECT PEDIDO.NUMPEDIDO, " +
                        "PEDIDO.ENTIDAD_NOMBRE, " +
                        "EMPLEADO.NOMBRE, " +
                        "EMPLEADO.APELLIDOS, " +
                        "CONTRATO.TELEFONO, " +
                        "CONTRATO.EMAIL, " +
                        "PEDIDO.NOTAS " +
                    "FROM EMPLEADO, PEDIDO, CONTRATO " +
                    "WHERE " +
                    "(EMPLEADO.CONTRATOACTUAL_ID = CONTRATO.ID) " +
                        "AND (PEDIDO.SOLICITANTE_ID = EMPLEADO.ID) " +
                    "AND (PEDIDO.ESTADOPEDIDO_NOMBRE = 'Pendiente de aceptación')");

            out.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            out.println("<rss xmlns:sy=\"http://purl.org/rss/1.0/modules/syndication/\" version=\"2.0\">");
            out.println("<channel>");
            out.println("<title>Pedidos pendientes de aceptación</title>");
            out.println("<link>http://intranet.iact.csic.es/iact-adm/</link>");
            out.println("<description>Pedidos realizados por personal del IACT pendientes de aceptación</description>");
            out.println("<sy:updatePeriod> daily </sy:updatePeriod>");
            out.println("<sy:updateFrequency> 2 </sy:updateFrequency>");
            out.println("<sy:updateBase> 2010-03-05+1:00-11:39 </sy:updateBase>");
//out.println("<lastBuildDate>Mon, 12 Sep 2005 18:37:00 GMT</lastBuildDate>");
            out.println("<language>es</language>");


            if (psContratos.execute()) {
                rs = psContratos.getResultSet();

                String numPedido, entidad, nombre, apellidos, telefono, email,notas;
                String contenidoMensaje = "";
                
                while (rs.next()) {
                    numPedido = rs.getString(1);
                    entidad = rs.getString(2);
                    nombre = rs.getString(3);
                    apellidos = rs.getString(4);
                    telefono = rs.getString(5);
                    email = rs.getString(6);
                    notas = rs.getString(7);

                    contenidoMensaje += apellidos + ", " + nombre + "&lt;br/&gt;";
                    contenidoMensaje += "PEDIDO NÚMERO: " + numPedido + "&lt;br/&gt;";
                    contenidoMensaje += "CONTACTO: &lt;br/&gt;";
                    contenidoMensaje += "Telefono: " + telefono + "&lt;br/&gt;";
                    contenidoMensaje += "EMail: " + email + "&lt;br/&gt;";
                    contenidoMensaje += "Notas: " + notas + "&lt;br/&gt;";

                    out.println("<item>");
                    out.println("<title>" + apellidos + ", " + nombre + " -> " + numPedido + "</title>");

                    out.println("<description>");
                    out.println(contenidoMensaje);
                    out.println("</description>");
                    out.println("<link>http://intranet.iact.csic.es/iact-adm/compras/ver/?id="+numPedido+"</link>");
                    out.println("</item>");

                    contenidoMensaje = "";
                }



            }
            out.println("</channel>");
            out.println("</rss>");


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
