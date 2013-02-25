package catalogo.vistas.modelo;

import catalogo.controladores.JPA.OrdenDeCompraJpaController;
import catalogo.modelo.OrdenCompra;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mauro Federico Lopez
 */
public class OrdenesDeCompraTableModel extends AbstractTableModel implements Observer {

    private String[] columnas = {"Fecha", "Código de envío", "enviado"};
    private ArrayList<OrdenCompra> ordenesDeCompra = new ArrayList();
    private OrdenDeCompraJpaController ordenDeCompraJpaController;

    public OrdenesDeCompraTableModel(OrdenDeCompraJpaController ordenDeCompraJpaController) {
        super();
        this.ordenDeCompraJpaController = ordenDeCompraJpaController;
        this.ordenDeCompraJpaController.addObserver(this);
        ordenesDeCompra = ordenDeCompraJpaController.obtenerTodasLasOrdenesDeCompra();
    }

    @Override
    public int getRowCount() {
        return ordenesDeCompra.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0 :
                return ordenesDeCompra.get(rowIndex).getFecha();
            case 1 :
                return ordenesDeCompra.get(rowIndex).getCodigoEnvio();
            case 2 :
                return ordenesDeCompra.get(rowIndex).getEnviado();
            default:
                return null;
        }
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0 :
                return Date.class;
            case 1 :
                return String.class;
            case 2 :
                return Boolean.class;
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

    @Override
    public void update(Observable o, Object arg) {
        ordenesDeCompra = ordenDeCompraJpaController.obtenerTodasLasOrdenesDeCompra();
        fireTableDataChanged();
    }

    public OrdenCompra obtenerOrdenDeCompra(int filaSeleccionada) {
        return this.ordenesDeCompra.get(filaSeleccionada);
    }

}
