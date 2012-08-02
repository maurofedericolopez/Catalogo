package catalogo.vistas.modelo;

import catalogo.Catalogo;
import catalogo.controladores.OrdenCompraJpaController;
import catalogo.controladores.exceptions.NonexistentEntityException;
import catalogo.modelo.OrdenCompra;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mauro Federico Lopez
 */
public class OrdenCompraTableModel extends AbstractTableModel {

    private String[] columnas = {"ID","Cliente","Fecha","Estado Envío","Código Envío"};
    private List<OrdenCompra> ordenes;
    private OrdenCompraJpaController controlador;

    public OrdenCompraTableModel() {
        this.controlador = Catalogo.getOrdenCompraC();
        this.ordenes = (List<OrdenCompra>) this.controlador.findOrdenCompraEntities();
    }

    @Override
    public int getRowCount() {
        return controlador.getOrdenCompraCount();
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
                return ordenes.get(rowIndex).getId();
            case 1:
                return ordenes.get(rowIndex).getMiCliente().getApellido()+" "+ordenes.get(rowIndex).getMiCliente().getNombre();
            case 2:
                return ordenes.get(rowIndex).getMiFecha();
            case 3:
                return ordenes.get(rowIndex).getEnviado();
            case 4:
                return ordenes.get(rowIndex).getCodigoEnvio();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        try {
            OrdenCompra aux = controlador.findOrdenCompra((Long)getValueAt(rowIndex,0));
            switch(columnIndex) {
                case 3:
                    aux.setEnviado((Boolean)aValue);
                case 4:
                    aux.setCodigoEnvio((Long)aValue);
            }
            controlador.edit(aux);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(OrdenCompraTableModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(OrdenCompraTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(columnIndex > 2)
            return true;
        else
            return false;
    }

}
