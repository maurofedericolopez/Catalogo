package catalogo.vistas.modelo;

import catalogo.modelo.ProductoOrdenCompra;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mauro Federico Lopez
 */
public class ProductoOrdenDeCompraTableModel extends AbstractTableModel {

    private String[] columnas = {"Código", "Nombre", "Precio", "Cantidad", "Total"};
    private ArrayList<ProductoOrdenCompra> productosOrdenDeCompra = new ArrayList();

    public ProductoOrdenDeCompraTableModel() {
        super();
    }

    @Override
    public int getRowCount() {
        return productosOrdenDeCompra.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0 :
                return productosOrdenDeCompra.get(rowIndex).getProducto().getCodigo();
            case 1 :
                return productosOrdenDeCompra.get(rowIndex).getProducto().getNombre();
            case 2 :
                return productosOrdenDeCompra.get(rowIndex).getProducto().getPrecio();
            case 3 :
                return productosOrdenDeCompra.get(rowIndex).getCantidad();
            case 4 :
                return productosOrdenDeCompra.get(rowIndex).getMontoTotal();
            default:
                return null;
        }
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0 :
                return String.class;
            case 1 :
                return String.class;
            case 2 :
                return Double.class;
            case 3 :
                return Long.class;
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
    public String getColumnName(int columnIndex) {
        return columnas[columnIndex];
    }

    /**
     * @param productosOrdenDeCompra the productosOrdenDeCompra to set
     */
    public void setProductosOrdenDeCompra(ArrayList<ProductoOrdenCompra> productosOrdenDeCompra) {
        this.productosOrdenDeCompra = productosOrdenDeCompra;
        fireTableDataChanged();
    }

}
