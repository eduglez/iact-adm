package compras.jsfcontroller;

import compras.modelo.LineaPedido;
import compras.modelo.Producto;
import compras.modelo.Proveedor;
import java.util.Collection;
import java.util.Iterator;

public class FiltrosLineaPedido {

    public static void filtrarInventariable(Collection<LineaPedido> c) {
        Producto p;
        for (Iterator<LineaPedido> it = c.iterator(); it.hasNext();) {
            p = it.next().getProducto();
            if (p != null) {
                if (p.getNombre() != null) {
                    if (p.getNombre().equals("Inventariable")) {
                        it.remove();
                    }
                }
            }
        }
    }

    public static void filtrarFungible(Collection<LineaPedido> c) {
        Producto p;
        for (Iterator<LineaPedido> it = c.iterator(); it.hasNext();) {
            p = it.next().getProducto();
            if (p != null) {
                if (p.getNombre() != null) {
                    if (p.getNombre().equals("Fungible")) {
                        it.remove();
                    }
                }
            }
        }
    }

    public static void filtrarOtrosGastos(Collection<LineaPedido> c) {
        Producto p;
        for (Iterator<LineaPedido> it = c.iterator(); it.hasNext();) {
            p = it.next().getProducto();
            if (p != null) {
                if (p.getNombre() != null) {
                    if (p.getNombre().equals("Otros Gastos")) {
                        it.remove();
                    }
                }
            }

        }
    }

    public static void seleccionarPorProveedor(Collection<LineaPedido> c, Proveedor p) {
        for (Iterator<LineaPedido> it = c.iterator(); it.hasNext();) {
            LineaPedido lp = it.next();
            if (lp.getPedido().getProveedor() == null || (!lp.getPedido().getProveedor().equals(p))) {
                it.remove();
            }
        }
    }
}
