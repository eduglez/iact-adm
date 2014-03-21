package jsf.util;

import compras.modelo.Factura;
import compras.modelo.Pedido;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

public class FacturaComparator implements Comparator<Factura> {

    private boolean ascendente;
    private int tipoOrden;

    public static final int POR_PEDIDO_MAS_RECIENTE=0;

    public int compare(Factura o1, Factura o2) {
        switch(tipoOrden){
            case POR_PEDIDO_MAS_RECIENTE:
                if(ascendente){
                    Pedido pedidoFactura1=o1.getPedidoMasReciente();
                    if(pedidoFactura1==null)
                        return Integer.MAX_VALUE;

                    Pedido pedidoFactura2=o2.getPedidoMasReciente();
                    if(pedidoFactura2==null)
                        return Integer.MIN_VALUE;

                    return pedidoFactura1.getFecha().compareTo(pedidoFactura2.getFecha());
                }else{
                    Pedido pedidoFactura1=o1.getPedidoMasReciente();
                    if(pedidoFactura1==null)
                        return Integer.MIN_VALUE;

                    Pedido pedidoFactura2=o2.getPedidoMasReciente();
                    if(pedidoFactura2==null)
                        return Integer.MAX_VALUE;

                    return pedidoFactura2.getFecha().compareTo(pedidoFactura1.getFecha());

                }

            default:

                break;
        }
        
        return 0;
    }

    private Pedido getPedidoMasReciente(Collection<Pedido> pedidos){
        if(pedidos.isEmpty())
            return null;

        Iterator<Pedido> itPedidos=pedidos.iterator();
        Pedido pedidoMasReciente=itPedidos.next();
        Pedido pedido;
        while(itPedidos.hasNext()){
            pedido=itPedidos.next();
            if(pedido.getFecha()!=null){
                if(pedidoMasReciente.getFecha()==null){
                    pedidoMasReciente=pedido;
                }else{
                    if(pedidoMasReciente.getFecha().compareTo(pedido.getFecha())<0)
                        pedidoMasReciente=pedido;
                }

            }
        }

        return pedidoMasReciente!=null?pedidoMasReciente:null;

    }

    private Pedido getPedidoMenosReciente(Collection<Pedido> pedidos){
        if(pedidos.isEmpty())
            return null;

        Iterator<Pedido> itPedidos=pedidos.iterator();
        Pedido pedidoMenosReciente=itPedidos.next();
        Pedido pedido;
        while(itPedidos.hasNext()){
            pedido=itPedidos.next();
            if(pedido.getFecha()!=null){
                if(pedidoMenosReciente.getFecha()==null){
                    pedidoMenosReciente=pedido;
                }else{
                    if(pedidoMenosReciente.getFecha().compareTo(pedido.getFecha())>0)
                        pedidoMenosReciente=pedido;
                }
            }
        }

        return pedidoMenosReciente!=null?pedidoMenosReciente:null;

    }

    public FacturaComparator(int tipoOrden, boolean ascendente){
        this.tipoOrden=tipoOrden;
        this.ascendente=ascendente;
    }



}
