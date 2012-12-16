package catalogo.vistas.modelo;

import catalogo.Catalogo;
import catalogo.controladores.ProductosController;
import catalogo.modelo.Producto;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mauro Federico Lopez
 */
public class ProductoTableModel extends AbstractTableModel {

    private String[] columnas = {"Código", "Nombre", "Descripción", "Precio", "Ruta Imagen"};
    private ArrayList<Producto> productos;
    private ProductosController controlador;

    public ProductoTableModel() {
        super();
        controlador = Catalogo.productosController;
        productos = controlador.obtenerProductos();
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
            case 4:
                return productos.get(rowIndex).getPathImage();
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
                return Double.class;
            case 4 :
                return String.class;
            default :
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

}
