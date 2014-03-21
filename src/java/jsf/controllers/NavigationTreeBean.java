package jsf.controllers;

import com.icesoft.faces.component.tree.IceUserObject;

import javax.faces.context.FacesContext;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

public class NavigationTreeBean {

    // tree default model, used as a value for the tree component
    private DefaultTreeModel model;
    private DefaultMutableTreeNode rootTreeNode;
    private DefaultMutableTreeNode personalNode;
    private DefaultMutableTreeNode consultarPersonalNode;
    private DefaultMutableTreeNode consultarPersonalPorTipoPersonalNode;
    private DefaultMutableTreeNode comprasNode;
    private DefaultMutableTreeNode consultarComprasNode;
    private DefaultMutableTreeNode facturasNode;
    private DefaultMutableTreeNode proyectosNode;
    private DefaultMutableTreeNode consultarProyectosNode;
    private DefaultMutableTreeNode culturaCientificaNode;
    private DefaultMutableTreeNode consultarCulturaPorAnio;
    private DefaultMutableTreeNode consultarCulturaPorInvestigador;
    private DefaultMutableTreeNode datosAuxiliaresNode;
    private DefaultMutableTreeNode departamentosNode;
    private DefaultMutableTreeNode cuerposNode;
    private DefaultMutableTreeNode especialidadesLaboralesNode;
    private DefaultMutableTreeNode gruposNode;
    private DefaultMutableTreeNode puestosTrabajoNode;
    private DefaultMutableTreeNode proveedoresNode;
    private DefaultMutableTreeNode datosFacturacionNode;
    private DefaultMutableTreeNode datosEntregaNode;
    private DefaultMutableTreeNode entidadesFinanciadorasNode;
    private DefaultMutableTreeNode organismosNode;
    private DefaultMutableTreeNode tiposProyectoNode;
    private DefaultMutableTreeNode pedidosProyectoNode;
    private DefaultMutableTreeNode realizarPedidoNode;
    private DefaultMutableTreeNode inventario;
    private DefaultMutableTreeNode gasto;


    public NavigationTreeBean() {
        LoginController login = (LoginController) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "login");
        IceUserObject tempUserObject;


        rootTreeNode = new DefaultMutableTreeNode();
        tempUserObject = new IceUserObject(rootTreeNode);
        tempUserObject.setText("Administración IACT");
        tempUserObject.setExpanded(true);
        tempUserObject.setAction("index");
        rootTreeNode.setUserObject(tempUserObject);

        // model is accessed by by the ice:tree component
        model = new DefaultTreeModel(rootTreeNode);



        if (login.isAdm()) {

            personalNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(personalNode);
            tempUserObject.setText("Personal");
            tempUserObject.setLeaf(false);
            tempUserObject.setAction("personal-listar");
            personalNode.setUserObject(tempUserObject);
            rootTreeNode.add(personalNode);

            consultarPersonalPorTipoPersonalNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(consultarPersonalPorTipoPersonalNode);
            tempUserObject.setText("por Tipo de Personal");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("personal-consultar-portipopersonal");
            consultarPersonalPorTipoPersonalNode.setUserObject(tempUserObject);
            personalNode.add(consultarPersonalPorTipoPersonalNode);

            comprasNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(comprasNode);
            tempUserObject.setText("Compras");
            tempUserObject.setLeaf(false);
            tempUserObject.setAction("compras-listar");
            comprasNode.setUserObject(tempUserObject);
            rootTreeNode.add(comprasNode);

            facturasNode=new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(facturasNode);
            tempUserObject.setText("Facturas");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("facturas-listar");
            facturasNode.setUserObject(tempUserObject);
            comprasNode.add(facturasNode);

            consultarComprasNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(consultarComprasNode);
            tempUserObject.setText("por Productos en Pedidos");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("compras-consultar");
            consultarComprasNode.setUserObject(tempUserObject);
            comprasNode.add(consultarComprasNode);

            

            proyectosNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(proyectosNode);
            tempUserObject.setText("Proyectos");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("proyectos-listar");
            proyectosNode.setUserObject(tempUserObject);
            rootTreeNode.add(proyectosNode);

            culturaCientificaNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(culturaCientificaNode);
            tempUserObject.setText("Cultura científica");
            tempUserObject.setLeaf(false);
            tempUserObject.setAction("culturacientifica-listar");
            culturaCientificaNode.setUserObject(tempUserObject);
            rootTreeNode.add(culturaCientificaNode);

            consultarCulturaPorAnio = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(consultarCulturaPorAnio);
            tempUserObject.setText("por Año");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("culturacientifica-consultar-por-anio");
            consultarCulturaPorAnio.setUserObject(tempUserObject);
            culturaCientificaNode.add(consultarCulturaPorAnio);

            consultarCulturaPorInvestigador = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(consultarCulturaPorAnio);
            tempUserObject.setText("por Investigador");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("culturacientifica-consultar-por-investigador");
            consultarCulturaPorInvestigador.setUserObject(tempUserObject);
            culturaCientificaNode.add(consultarCulturaPorInvestigador);

            inventario = new DefaultMutableTreeNode();
            tempUserObject= new IceUserObject(inventario);
            tempUserObject.setText("Inventario");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("inventario");
            inventario.setUserObject(tempUserObject);
            rootTreeNode.add(inventario);

            gasto = new DefaultMutableTreeNode();
            tempUserObject= new IceUserObject(gasto);
            tempUserObject.setText("Gasto");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("gasto");
            gasto.setUserObject(tempUserObject);
            rootTreeNode.add(gasto);


            datosAuxiliaresNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(datosAuxiliaresNode);
            tempUserObject.setText("Datos auxiliares");
            tempUserObject.setLeaf(false);
            tempUserObject.setAction("datos-auxiliares");
            datosAuxiliaresNode.setUserObject(tempUserObject);
            rootTreeNode.add(datosAuxiliaresNode);

            departamentosNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(departamentosNode);
            tempUserObject.setText("Departamentos");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("departamentos");
            departamentosNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(departamentosNode);

            cuerposNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(cuerposNode);
            tempUserObject.setText("Cuerpos");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("cuerpos");
            cuerposNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(cuerposNode);


            especialidadesLaboralesNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(especialidadesLaboralesNode);
            tempUserObject.setText("Especialidades laborales");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("especialidades-laborales");
            especialidadesLaboralesNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(especialidadesLaboralesNode);

            gruposNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(gruposNode);
            tempUserObject.setText("Grupos");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("grupos");
            gruposNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(gruposNode);

            puestosTrabajoNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(puestosTrabajoNode);
            tempUserObject.setText("Puestos de trabajo");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("puestos-trabajo");
            puestosTrabajoNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(puestosTrabajoNode);

            proveedoresNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(proveedoresNode);
            tempUserObject.setText("Proveedores");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("proveedores");
            proveedoresNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(proveedoresNode);

            datosFacturacionNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(datosFacturacionNode);
            tempUserObject.setText("Datos de facturación");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("datos-facturacion");
            datosFacturacionNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(datosFacturacionNode);

            datosEntregaNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(datosEntregaNode);
            tempUserObject.setText("Datos de entrega");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("datos-entrega");
            datosEntregaNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(datosEntregaNode);

            entidadesFinanciadorasNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(entidadesFinanciadorasNode);
            tempUserObject.setText("Entidades Financiadoras");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("entidades-financiadoras");
            entidadesFinanciadorasNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(entidadesFinanciadorasNode);

            tiposProyectoNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(tiposProyectoNode);
            tempUserObject.setText("Tipos de proyecto");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("tipos-proyecto");
            tiposProyectoNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(tiposProyectoNode);

            organismosNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(organismosNode);
            tempUserObject.setText("Organismos");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("organismos");
            organismosNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(organismosNode);

        } else if (login.isGerencia()) {
                        personalNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(personalNode);
            tempUserObject.setText("Personal");
            tempUserObject.setLeaf(false);
            tempUserObject.setAction("personal-listar");
            personalNode.setUserObject(tempUserObject);
            rootTreeNode.add(personalNode);

            consultarPersonalPorTipoPersonalNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(consultarPersonalPorTipoPersonalNode);
            tempUserObject.setText("por Tipo de Personal");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("personal-consultar-portipopersonal");
            consultarPersonalPorTipoPersonalNode.setUserObject(tempUserObject);
            personalNode.add(consultarPersonalPorTipoPersonalNode);

            comprasNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(comprasNode);
            tempUserObject.setText("Compras");
            tempUserObject.setLeaf(false);
            tempUserObject.setAction("compras-listar");
            comprasNode.setUserObject(tempUserObject);
            rootTreeNode.add(comprasNode);

            facturasNode=new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(facturasNode);
            tempUserObject.setText("Facturas");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("facturas-listar");
            facturasNode.setUserObject(tempUserObject);
            comprasNode.add(facturasNode);
            
            consultarComprasNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(consultarComprasNode);
            tempUserObject.setText("por Productos en Pedidos");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("compras-consultar");
            consultarComprasNode.setUserObject(tempUserObject);
            comprasNode.add(consultarComprasNode);

            

            proyectosNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(proyectosNode);
            tempUserObject.setText("Proyectos");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("proyectos-listar");
            proyectosNode.setUserObject(tempUserObject);
            rootTreeNode.add(proyectosNode);

            culturaCientificaNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(culturaCientificaNode);
            tempUserObject.setText("Cultura científica");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("culturacientifica-listar");
            culturaCientificaNode.setUserObject(tempUserObject);
            rootTreeNode.add(culturaCientificaNode);


            datosAuxiliaresNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(datosAuxiliaresNode);
            tempUserObject.setText("Datos auxiliares");
            tempUserObject.setLeaf(false);
            tempUserObject.setAction("datos-auxiliares");
            datosAuxiliaresNode.setUserObject(tempUserObject);
            rootTreeNode.add(datosAuxiliaresNode);

            departamentosNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(departamentosNode);
            tempUserObject.setText("Departamentos");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("departamentos");
            departamentosNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(departamentosNode);

            cuerposNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(cuerposNode);
            tempUserObject.setText("Cuerpos");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("cuerpos");
            cuerposNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(cuerposNode);


            especialidadesLaboralesNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(especialidadesLaboralesNode);
            tempUserObject.setText("Especialidades laborales");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("especialidades-laborales");
            especialidadesLaboralesNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(especialidadesLaboralesNode);

            gruposNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(gruposNode);
            tempUserObject.setText("Grupos");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("grupos");
            gruposNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(gruposNode);

            puestosTrabajoNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(puestosTrabajoNode);
            tempUserObject.setText("Puestos de trabajo");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("puestos-trabajo");
            puestosTrabajoNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(puestosTrabajoNode);

            proveedoresNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(proveedoresNode);
            tempUserObject.setText("Proveedores");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("proveedores");
            proveedoresNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(proveedoresNode);

            datosFacturacionNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(datosFacturacionNode);
            tempUserObject.setText("Datos de facturación");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("datos-facturacion");
            datosFacturacionNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(datosFacturacionNode);

            datosEntregaNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(datosEntregaNode);
            tempUserObject.setText("Datos de entrega");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("datos-entrega");
            datosEntregaNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(datosEntregaNode);

            entidadesFinanciadorasNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(entidadesFinanciadorasNode);
            tempUserObject.setText("Entidades Financiadoras");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("entidades-financiadoras");
            entidadesFinanciadorasNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(entidadesFinanciadorasNode);

            tiposProyectoNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(tiposProyectoNode);
            tempUserObject.setText("Tipos de proyecto");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("tipos-proyecto");
            tiposProyectoNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(tiposProyectoNode);

            organismosNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(organismosNode);
            tempUserObject.setText("Organismos");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("organismos");
            organismosNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(organismosNode);
        } else if (login.isCompras()) {
                        personalNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(personalNode);
            tempUserObject.setText("Personal");
            tempUserObject.setLeaf(false);
            tempUserObject.setAction("personal-listar");
            personalNode.setUserObject(tempUserObject);
            rootTreeNode.add(personalNode);

            consultarPersonalPorTipoPersonalNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(consultarPersonalPorTipoPersonalNode);
            tempUserObject.setText("por Tipo de Personal");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("personal-consultar-portipopersonal");
            consultarPersonalPorTipoPersonalNode.setUserObject(tempUserObject);
            personalNode.add(consultarPersonalPorTipoPersonalNode);

            comprasNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(comprasNode);
            tempUserObject.setText("Compras");
            tempUserObject.setLeaf(false);
            tempUserObject.setAction("compras-listar");
            comprasNode.setUserObject(tempUserObject);
            rootTreeNode.add(comprasNode);

            facturasNode=new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(facturasNode);
            tempUserObject.setText("Facturas");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("facturas-listar");
            facturasNode.setUserObject(tempUserObject);
            comprasNode.add(facturasNode);
            
            consultarComprasNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(consultarComprasNode);
            tempUserObject.setText("por Productos en Pedidos");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("compras-consultar");
            consultarComprasNode.setUserObject(tempUserObject);
            comprasNode.add(consultarComprasNode);

            

            proyectosNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(proyectosNode);
            tempUserObject.setText("Proyectos");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("proyectos-listar");
            proyectosNode.setUserObject(tempUserObject);
            rootTreeNode.add(proyectosNode);

            datosAuxiliaresNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(datosAuxiliaresNode);
            tempUserObject.setText("Datos auxiliares");
            tempUserObject.setLeaf(false);
            tempUserObject.setAction("datos-auxiliares");
            datosAuxiliaresNode.setUserObject(tempUserObject);
            rootTreeNode.add(datosAuxiliaresNode);

            proveedoresNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(proveedoresNode);
            tempUserObject.setText("Proveedores");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("proveedores");
            proveedoresNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(proveedoresNode);

            datosFacturacionNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(datosFacturacionNode);
            tempUserObject.setText("Datos de facturación");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("datos-facturacion");
            datosFacturacionNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(datosFacturacionNode);

            datosEntregaNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(datosEntregaNode);
            tempUserObject.setText("Datos de entrega");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("datos-entrega");
            datosEntregaNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(datosEntregaNode);

        } else if (login.isPersonal()) {
            personalNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(personalNode);
            tempUserObject.setText("Personal");
            tempUserObject.setLeaf(false);
            tempUserObject.setAction("personal-listar");
            personalNode.setUserObject(tempUserObject);
            rootTreeNode.add(personalNode);

            consultarPersonalPorTipoPersonalNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(consultarPersonalPorTipoPersonalNode);
            tempUserObject.setText("por Tipo de Personal");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("personal-consultar-portipopersonal");
            consultarPersonalPorTipoPersonalNode.setUserObject(tempUserObject);
            personalNode.add(consultarPersonalPorTipoPersonalNode);

            comprasNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(comprasNode);
            tempUserObject.setText("Compras");
            tempUserObject.setLeaf(false);
            tempUserObject.setAction("compras-listar");
            comprasNode.setUserObject(tempUserObject);
            rootTreeNode.add(comprasNode);

            consultarComprasNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(consultarComprasNode);
            tempUserObject.setText("por Productos en Pedidos");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("compras-consultar");
            consultarComprasNode.setUserObject(tempUserObject);
            comprasNode.add(consultarComprasNode);

            proyectosNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(proyectosNode);
            tempUserObject.setText("Proyectos");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("proyectos-listar");
            proyectosNode.setUserObject(tempUserObject);
            rootTreeNode.add(proyectosNode);


            datosAuxiliaresNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(datosAuxiliaresNode);
            tempUserObject.setText("Datos auxiliares");
            tempUserObject.setLeaf(false);
            tempUserObject.setAction("datos-auxiliares");
            datosAuxiliaresNode.setUserObject(tempUserObject);
            rootTreeNode.add(datosAuxiliaresNode);

            departamentosNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(departamentosNode);
            tempUserObject.setText("Departamentos");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("departamentos");
            departamentosNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(departamentosNode);

            cuerposNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(cuerposNode);
            tempUserObject.setText("Cuerpos");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("cuerpos");
            cuerposNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(cuerposNode);


            especialidadesLaboralesNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(especialidadesLaboralesNode);
            tempUserObject.setText("Especialidades laborales");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("especialidades-laborales");
            especialidadesLaboralesNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(especialidadesLaboralesNode);

            gruposNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(gruposNode);
            tempUserObject.setText("Grupos");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("grupos");
            gruposNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(gruposNode);

            puestosTrabajoNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(puestosTrabajoNode);
            tempUserObject.setText("Puestos de trabajo");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("puestos-trabajo");
            puestosTrabajoNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(puestosTrabajoNode);
           
            organismosNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(organismosNode);
            tempUserObject.setText("Organismos");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("organismos");
            organismosNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(organismosNode);
        } else if (login.isProyectos()) {

            personalNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(personalNode);
            tempUserObject.setText("Personal");
            tempUserObject.setLeaf(false);
            tempUserObject.setAction("personal-listar");
            personalNode.setUserObject(tempUserObject);
            rootTreeNode.add(personalNode);

            consultarPersonalPorTipoPersonalNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(consultarPersonalPorTipoPersonalNode);
            tempUserObject.setText("por Tipo de Personal");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("personal-consultar-portipopersonal");
            consultarPersonalPorTipoPersonalNode.setUserObject(tempUserObject);
            personalNode.add(consultarPersonalPorTipoPersonalNode);

            comprasNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(comprasNode);
            tempUserObject.setText("Compras");
            tempUserObject.setLeaf(false);
            tempUserObject.setAction("compras-listar");
            comprasNode.setUserObject(tempUserObject);
            rootTreeNode.add(comprasNode);

            consultarComprasNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(consultarComprasNode);
            tempUserObject.setText("por Productos en Pedidos");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("compras-consultar");
            consultarComprasNode.setUserObject(tempUserObject);
            comprasNode.add(consultarComprasNode);

            proyectosNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(proyectosNode);
            tempUserObject.setText("Proyectos");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("proyectos-listar");
            proyectosNode.setUserObject(tempUserObject);
            rootTreeNode.add(proyectosNode);

            datosAuxiliaresNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(datosAuxiliaresNode);
            tempUserObject.setText("Datos auxiliares");
            tempUserObject.setLeaf(false);
            tempUserObject.setAction("datos-auxiliares");
            datosAuxiliaresNode.setUserObject(tempUserObject);
            rootTreeNode.add(datosAuxiliaresNode);

            entidadesFinanciadorasNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(entidadesFinanciadorasNode);
            tempUserObject.setText("Entidades Financiadoras");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("entidades-financiadoras");
            entidadesFinanciadorasNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(entidadesFinanciadorasNode);

            tiposProyectoNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(tiposProyectoNode);
            tempUserObject.setText("Tipos de proyecto");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("tipos-proyecto");
            tiposProyectoNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(tiposProyectoNode);

            organismosNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(organismosNode);
            tempUserObject.setText("Organismos");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("organismos");
            organismosNode.setUserObject(tempUserObject);
            datosAuxiliaresNode.add(organismosNode);
        } else if (login.isSecretaria()) {
                        personalNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(personalNode);
            tempUserObject.setText("Personal");
            tempUserObject.setLeaf(false);
            tempUserObject.setAction("personal-listar");
            personalNode.setUserObject(tempUserObject);
            rootTreeNode.add(personalNode);

            consultarPersonalPorTipoPersonalNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(consultarPersonalPorTipoPersonalNode);
            tempUserObject.setText("por Tipo de Personal");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("personal-consultar-portipopersonal");
            consultarPersonalPorTipoPersonalNode.setUserObject(tempUserObject);
            personalNode.add(consultarPersonalPorTipoPersonalNode);

            comprasNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(comprasNode);
            tempUserObject.setText("Compras");
            tempUserObject.setLeaf(false);
            tempUserObject.setAction("compras-listar");
            comprasNode.setUserObject(tempUserObject);
            rootTreeNode.add(comprasNode);

            consultarComprasNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(consultarComprasNode);
            tempUserObject.setText("por Productos en Pedidos");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("compras-consultar");
            consultarComprasNode.setUserObject(tempUserObject);
            comprasNode.add(consultarComprasNode);

            proyectosNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(proyectosNode);
            tempUserObject.setText("Proyectos");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("proyectos-listar");
            proyectosNode.setUserObject(tempUserObject);
            rootTreeNode.add(proyectosNode);

        } else if (login.isInvestigador()) {
            pedidosProyectoNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(pedidosProyectoNode);
            tempUserObject.setText("Pedidos proyecto");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("compras-listar");
            pedidosProyectoNode.setUserObject(tempUserObject);
            rootTreeNode.add(pedidosProyectoNode);

            culturaCientificaNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(culturaCientificaNode);
            tempUserObject.setText("Cultura cientifica");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("culturacientifica-listar");
            culturaCientificaNode.setUserObject(tempUserObject);
            rootTreeNode.add(culturaCientificaNode);

        } else if (login.isServicios()) {
        }else if(login.isCulturaCientifica()){
            culturaCientificaNode = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(culturaCientificaNode);
            tempUserObject.setText("Cultura científica");
            tempUserObject.setLeaf(false);
            tempUserObject.setAction("culturacientifica-listar");
            culturaCientificaNode.setUserObject(tempUserObject);
            rootTreeNode.add(culturaCientificaNode);

            consultarCulturaPorAnio = new DefaultMutableTreeNode();
            tempUserObject = new IceUserObject(consultarCulturaPorAnio);
            tempUserObject.setText("por Año");
            tempUserObject.setLeaf(true);
            tempUserObject.setAction("culturacientifica-consultar-por-anio");
            consultarCulturaPorAnio.setUserObject(tempUserObject);
            culturaCientificaNode.add(consultarCulturaPorAnio);

//            consultarCulturaPorInvestigador = new DefaultMutableTreeNode();
//            tempUserObject = new IceUserObject(consultarCulturaPorAnio);
//            tempUserObject.setText("por Investigador");
//            tempUserObject.setLeaf(true);
//            tempUserObject.setAction("culturacientifica-consultar-por-investigador");
//            consultarCulturaPorInvestigador.setUserObject(tempUserObject);
//            culturaCientificaNode.add(consultarCulturaPorInvestigador);

        }

    }

    public DefaultTreeModel getModel() {
        return model;
    }
}
