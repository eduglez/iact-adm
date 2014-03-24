package compras.jsfcontroller;

import compras.modelo.LineaPedido;
import java.util.Comparator;


public class LineaPedidoComparator implements Comparator<LineaPedido>{

      private boolean ascendente;

    private String columna;

    public LineaPedidoComparator(boolean ascendente, String columna){
        this.ascendente=ascendente;
        this.columna=columna;
    }


    public int compare(LineaPedido o1, LineaPedido o2) {
        if(columna==null)
            return 0;
        if(columna.equals("nombreProducto")){
            if(o1.getDescripcionAlternativa()==null)
                return Integer.MAX_VALUE;

            if(o2.getDescripcionAlternativa()==null)
                return Integer.MIN_VALUE;

            if(ascendente){
                return o1.getDescripcionAlternativa().compareTo(o2.getDescripcionAlternativa());
            }else{
                return o2.getDescripcionAlternativa().compareTo(o1.getDescripcionAlternativa());
            }
        }else if(columna.equals("proveedor")){
            if(o1.getPedido().getProveedor()==null)
                return Integer.MAX_VALUE;

            if(o2.getPedido().getProveedor()==null)
                return Integer.MIN_VALUE;

           if(ascendente){
                return o1.getPedido().getProveedor().getRazonSocial().compareTo(o2.getPedido().getProveedor().getRazonSocial());
            }else{
                return o2.getPedido().getProveedor().getRazonSocial().compareTo(o1.getPedido().getProveedor().getRazonSocial());
            }

        }else if(columna.equals("idPedido")){
           
            if(ascendente){
                return o1.getPedido().getId().compareTo(o2.getPedido().getId());
            }else{
                return o2.getPedido().getId().compareTo(o1.getPedido().getId());
            }
        }else if(columna.equals("fechaPedido")){
            if(o1.getPedido().getFecha()==null)
                return Integer.MAX_VALUE;

            if(o2.getPedido().getFecha()==null)
                return Integer.MIN_VALUE;


            if(ascendente){
                return o1.getPedido().getFecha().compareTo(o2.getPedido().getFecha());
            }else{
                return o2.getPedido().getFecha().compareTo(o1.getPedido().getFecha());
            }
        }else if(columna.equals("tipoProducto")){
            if(o1.getProducto()==null)
                return Integer.MAX_VALUE;

            if(o2.getProducto()==null)
                return Integer.MIN_VALUE;


            if(ascendente){
                return o1.getProducto().getNombre().compareTo(o2.getProducto().getNombre());
            }else{
                return o2.getProducto().getNombre().compareTo(o1.getProducto().getNombre());
            }
        }else
            return 0;
    }


}
