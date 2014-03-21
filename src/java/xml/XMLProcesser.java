
package xml;

import java.sql.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.*;
import jsf.util.EuroCurrency;
import rss.WorkersUtil;

public class XMLProcesser extends HttpServlet {
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/xml;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String query;
        if((query=request.getParameter("query"))!=null){
            if(query.equals("pedidos")){
                pedidosToXML(out);
            }else if(query.equals("empleados")){
                //empleadosToXML(out);
            }

        }
        out.close();
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


    private void writeLinea(XMLStreamWriter xmlw, ResultSet rs) throws XMLStreamException, SQLException {
        String descripcion;
        xmlw.writeEmptyElement("linea");

        descripcion = rs.getString("DESCRIPCIONALTERNATIVA");
        xmlw.writeAttribute("descripcion", descripcion != null ? descripcion : "");

        xmlw.writeAttribute("cantidad", Integer.toString(rs.getInt("CANTIDAD")));
        xmlw.writeAttribute("preciounitario", EuroCurrency.getAsString(rs.getLong("PRECIOUNITARIO")));
        xmlw.writeAttribute("ivaapliado", EuroCurrency.getAsString(rs.getLong("IVAAPLICADO")));
    }


    private void pedidosToXML(PrintWriter out) {
        ResultSet rs;
        Connection conn = null;
        Statement stat=null;
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        
        try {
            conn = WorkersUtil.getInstance().getConnection();
            stat = conn.createStatement();

            rs=stat.executeQuery(
                    "SELECT " +
                        "PEDIDO.NUMPEDIDO, " +
                        "PEDIDO.FECHA, " +
                        "PEDIDO.FECHACANCELACION, " +
                        "PEDIDO.ESTADOPEDIDO_NOMBRE, " +
                        "PEDIDO.PROVEEDOR_RAZONSOCIAL, " +
                        "PEDIDO.DATOSFACTURACION_RAZONSOCIAL, " +
                        "PEDIDO.DATOSENTREGA_RAZONSOCIAL, " +
                        "PEDIDO.ENTIDAD_NOMBRE, " +
                        "PEDIDO.PROYECTO_ID, " +
                        "PEDIDO.SOLICITANTE_ID, " +
                        "PEDIDO.ASIGNACION, " +
                        "LINEAPEDIDO.ID, " +
                        "LINEAPEDIDO.CANTIDAD, " +
                        "LINEAPEDIDO.DESCRIPCIONALTERNATIVA, " +
                        "LINEAPEDIDO.PRECIOUNITARIO, " +
                        "LINEAPEDIDO.IVAAPLICADO " +
                     "FROM PEDIDO LEFT JOIN LINEAPEDIDO ON PEDIDO.NUMPEDIDO = LINEAPEDIDO.PEDIDO_NUMPEDIDO " +
                     "WHERE PEDIDO.BORRADO=0");


            XMLOutputFactory xmlof = XMLOutputFactory.newInstance();

            XMLStreamWriter xmlw = xmlof.createXMLStreamWriter(out);

            xmlw.writeStartDocument();

            xmlw.writeStartElement("pedidos");


            String numPedidoAnterior="";
            String numPedido, descripcion, estado, proveedor, entrega, entidadsolicitante;
            Date fecha, fechaCancelacion;
            while (rs.next()) {
                numPedido=rs.getString("NUMPEDIDO");
                if(numPedido.equals(numPedidoAnterior)){
                    writeLinea(xmlw, rs);
                }else{
                    if(!numPedidoAnterior.isEmpty()){
                        xmlw.writeEndElement(); // Para lineas
                        xmlw.writeEndElement();// Para pedido
                    }
                    numPedidoAnterior=numPedido;
                    
                    xmlw.writeStartElement("pedido");

                    xmlw.writeAttribute("numpedido", numPedido);

                    fecha=rs.getDate("FECHA");
                    xmlw.writeAttribute("fecha", fecha!=null?dateFormatter.format(fecha):"");

                    fechaCancelacion = rs.getDate("FECHACANCELACION");
                    xmlw.writeAttribute("fechacancelacion", fechaCancelacion!=null?dateFormatter.format(fechaCancelacion):"");

                    estado=rs.getString("ESTADOPEDIDO_NOMBRE");
                    xmlw.writeAttribute("estado",estado!=null?estado:"" );

                    proveedor=rs.getString("PROVEEDOR_RAZONSOCIAL");
                    xmlw.writeAttribute("proveedor", proveedor!=null?proveedor:"");

                    entrega=rs.getString("DATOSENTREGA_RAZONSOCIAL");
                    xmlw.writeAttribute("entrega", entrega!=null?entrega:"");

                    entidadsolicitante=rs.getString("ENTIDAD_NOMBRE");
                    xmlw.writeAttribute("entidadsolicitante", entidadsolicitante!=null?entidadsolicitante:"");

                    xmlw.writeStartElement("lineas");

                    writeLinea(xmlw, rs);
                }

            }

                    xmlw.writeEndElement();//Para lineas

                xmlw.writeEndElement();//Para pedido

            xmlw.writeEndDocument();//Para pedidos

            xmlw.close();
        } catch (XMLStreamException xmlEx) {
            Logger.getLogger(XMLProcesser.class.getName()).log(Level.SEVERE, null, xmlEx);
            out.println("NON");
        } catch(SQLException sqlEx){
            Logger.getLogger(XMLProcesser.class.getName()).log(Level.SEVERE, null, sqlEx);
            out.println("NON");
        }catch(Exception ex){
            Logger.getLogger(XMLProcesser.class.getName()).log(Level.SEVERE, null, ex);
            out.println("NON");
        }finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException sqlEx) {
                    Logger.getLogger(XMLProcesser.class.getName()).log(Level.SEVERE, null, sqlEx);
                }
            }

        }
    }

/*
    private void writeContrato(XMLStreamWriter xmlw, ResultSet rs) throws XMLStreamException, SQLException {
        String cId,cRegistroPersonal, cTelefono, cEmail, cDepartamento,
                    cServicio, cLineaInvestigacion, cLineaInvestigacionPadre,
                    cOrganismo, cLocalizacion, cJornadaLaboral, cTipoEmpleado,
                    cNivel, cTelefonoTipoEmpleado, cEmailTipoEmpleado,
                    cAdscripcion, cTipoBecario, cCuerpo, cPuestoTrabajo,
                    cGrupo, cCategoria, cEspecialidadLaboral,
                    cTipoLaboralTemporal, cIPNombre, cIPApellidos;

            Date cFechaInicioContrato, cFechaFinContrato,
                    cFechaInicioIact, cFechaFinIact;

        xmlw.writeEmptyElement("contrato");

        descripcion = rs.getString("DESCRIPCIONALTERNATIVA");
        xmlw.writeAttribute("descripcion", descripcion != null ? descripcion : "");

        xmlw.writeAttribute("cantidad", Integer.toString(rs.getInt("CANTIDAD")));
        xmlw.writeAttribute("preciounitario", EuroCurrency.getAsString(rs.getLong("PRECIOUNITARIO")));
        xmlw.writeAttribute("ivaapliado", EuroCurrency.getAsString(rs.getLong("IVAAPLICADO")));
    }

    
    private void empleadosToXML(PrintWriter out){
        ResultSet rs;
        Connection conn = null;
        Statement stat=null;
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        try {
            conn = WorkersUtil.getInstance().getConnection();
            stat = conn.createStatement();

            rs=stat.executeQuery(
                    "SELECT " +
                        "E.ID, " +
                        "E.NOMBRE, " +
                        "E.APELLIDOS, " +
                        "E.FECHANACIMIENTO, " +
                        "E.DIRECCION, " +
                        "E.TELEFONO, " +
                        "E.NUMSEGURIDADSOCIAL, " +
                        "E.NUMCUENTABANCARIA, " +
                        "E.WEB, " +
                        "E.DNI, " +
                        "E.SEXO, " +
                        "E.CONTRATOACTUAL_ID, " +
                        "C.ID, " +
                        "C.REGISTROPERSONAL, " +
                        "C.TELEFONO, " +
                        "C.EMAIL, " +
                        "C.FECHAINICIOCONTRATO, " +
                        "C.FECHAFINCONTRATO, " +
                        "C.FECHAINICIOIACT, " +
                        "C.FECHAFINIACT, " +
                        "C.DEPARTAMENTO_NOMBRE, " +
                        "C.SERVICIO_NOMBRE, " +
                        "C.ORGANISMO_NOMBRE, " +
                        "C.LOCALIZACION, " +
                        "LINEAINVESTIGACION.NOMBRE, "+
                        "LINEAINVESTIGACION.LINEAINVESTIGACIONPADRE_NOMBRE, "+
                        "JORNADALABORAL.NOMBRE, " +
                        "TIPOEMPLEADO.TIPOEMPLEADO, " +
                        "TIPOEMPLEADO.NIVEL, " +
                        "TIPOEMPLEADO.TELEFONO, " +
                        "TIPOEMPLEADO.EMAIL, " +
                        "TIPOEMPLEADO.ADSCRIPCION_NOMBRE, " +
                        "TIPOEMPLEADO.TIPOBECARIO_NOMBRE, " +
                        "TIPOEMPLEADO.CUERPO_NOMBRE, " +
                        "TIPOEMPLEADO.PUESTOTRABAJO_NOMBRE, " +
                        "TIPOEMPLEADO.GRUPO_NOMBRE, " +
                        "TIPOEMPLEADO.CATEGORIA_NOMBRE, " +
                        "TIPOEMPLEADO.ESPECIALIDADLABORAL_NOMBRE, " +
                        "TIPOEMPLEADO.TIPOLABORALTEMPORAL_NOMBRE, " +
                        "IP.NOMBRE, " +
                        "IP.APELLIDOS " +
                    "FROM EMPLEADO E " +
                            "LEFT JOIN CONTRATO C ON E.ID = C.EMPLEADO_ID " +
                            "LEFT JOIN LINEAINVESTIGACION ON C.LINEAINVESTIGACION_NOMBRE = LINEAINVESTIGACION.NOMBRE " +
                            "LEFT JOIN TIPOEMPLEADO ON C.ID=TIPOEMPLEADO.CONTRATOEMPLEADO_ID " +
                            "LEFT JOIN JORNADALABORAL ON JORNADALABORAL.ID = C.JORNADALABORAL_ID " +
                            "LEFT JOIN EMPLEADO IP ON TIPOEMPLEADO.INVESTIGADORPRINCIPAL_ID = IP.ID " +
                    "WHERE E.BORRADO=0 " +
                    "ORDER BY E.APELLIDOS, E.ID");


            XMLOutputFactory xmlof = XMLOutputFactory.newInstance();

            XMLStreamWriter xmlw = xmlof.createXMLStreamWriter(out);

            xmlw.writeStartDocument();

            xmlw.writeStartElement("empleados");


            String eIdAnterior="";
            
            String eId, eNombre, eApellidos, eDireccion, eTelefono, 
                    eNumSeguridadSocial, eNumCuentaBancaria, eWeb,
                    eDni, eSexo, eContratoActual;
            Date eFechaNacimiento;

            String cId,cRegistroPersonal, cTelefono, cEmail, cDepartamento,
                    cServicio, cLineaInvestigacion, cLineaInvestigacionPadre,
                    cOrganismo, cLocalizacion, cJornadaLaboral, cTipoEmpleado,
                    cNivel, cTelefonoTipoEmpleado, cEmailTipoEmpleado,
                    cAdscripcion, cTipoBecario, cCuerpo, cPuestoTrabajo,
                    cGrupo, cCategoria, cEspecialidadLaboral,
                    cTipoLaboralTemporal, cIPNombre, cIPApellidos;

            Date cFechaInicioContrato, cFechaFinContrato, 
                    cFechaInicioIact, cFechaFinIact;

            while (rs.next()) {
                eId=rs.getString("E.ID");
                if(eId.equals(eIdAnterior)){
                    writeContrato(xmlw, rs);
                }else{
                    if(!c.isEmpty()){
                        xmlw.writeEndElement(); // Para lineas
                        xmlw.writeEndElement();// Para pedido
                    }
                    numPedidoAnterior=numPedido;

                    xmlw.writeStartElement("pedido");

                    xmlw.writeAttribute("numpedido", numPedido);

                    fecha=rs.getDate("FECHA");
                    xmlw.writeAttribute("fecha", fecha!=null?dateFormatter.format(fecha):"");

                    fechaCancelacion = rs.getDate("FECHACANCELACION");
                    xmlw.writeAttribute("fechacancelacion", fechaCancelacion!=null?dateFormatter.format(fechaCancelacion):"");

                    estado=rs.getString("ESTADOPEDIDO_NOMBRE");
                    xmlw.writeAttribute("estado",estado!=null?estado:"" );

                    proveedor=rs.getString("PROVEEDOR_RAZONSOCIAL");
                    xmlw.writeAttribute("proveedor", proveedor!=null?proveedor:"");

                    entrega=rs.getString("DATOSENTREGA_RAZONSOCIAL");
                    xmlw.writeAttribute("entrega", entrega!=null?entrega:"");

                    entidadsolicitante=rs.getString("ENTIDAD_NOMBRE");
                    xmlw.writeAttribute("entidadsolicitante", entidadsolicitante!=null?entidadsolicitante:"");

                    xmlw.writeStartElement("lineas");
                }

            }

                    xmlw.writeEndElement();//Para lineas

                xmlw.writeEndElement();//Para pedido

            xmlw.writeEndDocument();//Para pedidos

            xmlw.close();
        } catch (XMLStreamException xmlEx) {
            Logger.getLogger(XMLProcesser.class.getName()).log(Level.SEVERE, null, xmlEx);
            out.println("NON");
        } catch(SQLException sqlEx){
            Logger.getLogger(XMLProcesser.class.getName()).log(Level.SEVERE, null, sqlEx);
            out.println("NON");
        }catch(Exception ex){
            Logger.getLogger(XMLProcesser.class.getName()).log(Level.SEVERE, null, ex);
            out.println("NON");
        }finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException sqlEx) {
                    Logger.getLogger(XMLProcesser.class.getName()).log(Level.SEVERE, null, sqlEx);
                }
            }

        }
    }*/
}
