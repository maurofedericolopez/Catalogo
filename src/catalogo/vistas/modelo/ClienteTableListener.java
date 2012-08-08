package catalogo.vistas.modelo;

import catalogo.vistas.MisClientes;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Mauro Federico Lopez
 */
public class ClienteTableListener implements ListSelectionListener {

    MisClientes form;
    int rowSelected = -1;

    // It is necessary to keep the table since it is not possible
    // to determine the table from the event's source
    public ClienteTableListener(MisClientes form) {
        super();
        this.form = form;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        // If cell selection is enabled, both row and column change events are fired
        if ( this.rowSelected != form.getJtMisClientes().getSelectedRow() && form.getJtMisClientes().getSelectedRow()>=0) { 
            System.out.println(form.getJtMisClientes().getSelectedRow());
          this.rowSelected=form.getJtMisClientes().getSelectedRow();
        }
 
        if (e.getValueIsAdjusting()) {
            // The mouse button has not yet been released
        }
    }
    
}
