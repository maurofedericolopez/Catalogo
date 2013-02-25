package catalogo.vistas.modelo;

import catalogo.Catalogo;
import catalogo.controladores.JPA.ProductoJpaController;
import catalogo.modelo.Producto;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mauro Federico Lopez
 */
public class ProductoTableModel extends AbstractTableModel implements Observer {

    private String[] columnas = {"Código", "Nombre", "Descripción", "Precio"};
    private ArrayList<Producto> productos;
    private ProductoJpaController productoJpaController;

    public ProductoTableModel() {
        super();
        productoJpaController = Catalogo.getProductoJpaController();
        productoJpaController.addObserver(this);
        productos = productoJpaController.obtenerProductos();
    }

    @Override
    public int getRowCount() {
        return productos.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnas[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0:
                return productos.get(rowIndex).getCodigo();
            case 1:
                return productos.get(rowIndex).getNombre();
            case 2:
                return productos.get(rowIndex).getDescripcion();
            case 3:
                return productos.get(rowIndex).getPrecio();
            default:
                return null;
        }
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0 :
                return Long.class;
            case 1 :
                return String.class;
            case 2 :
                return String.class;
            case 3 :
                return String.class;
            case 4 :
                return Double.class;
            default :
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public void update(Observable o, Object arg) {
        productos = productoJpaController.obtenerProductos();
        fireTableDataChanged();
    }

    public Producto obtenerProducto(Integer filaSeleccionada) {
        return productos.get(filaSeleccionada);
    }

}
