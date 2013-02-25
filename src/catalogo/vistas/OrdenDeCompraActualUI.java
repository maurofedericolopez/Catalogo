package catalogo.vistas;

import catalogo.controladores.JPA.OrdenDeCompraJpaController;
import catalogo.modelo.ProductoOrdenCompra;
import catalogo.vistas.modelo.ProductoOrdenDeCompraTableModel;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Mauro Federico Lopez
 */
public class OrdenDeCompraActualUI extends javax.swing.JPanel {

    private OrdenDeCompraJpaController ordenDeCompraJpaController;
    private ProductoOrdenDeCompraTableModel productoOrdenDeCompraTableModel;

    /**
     * Creates new form OrdenDeCompraActualUI
     */
    private OrdenDeCompraActualUI() {
        initComponents();
    }

    public OrdenDeCompraActualUI(OrdenDeCompraJpaController ordenDeCompraJpaController) {
        this();
        this.ordenDeCompraJpaController = ordenDeCompraJpaController;
        this.ordenDeCompraJpaController.obtenerProductosOrdenDeCompraEnCurso();
        productoOrdenDeCompraTableModel = new ProductoOrdenDeCompraTableModel();
        tablaProductosOrdenCompra.setModel(productoOrdenDeCompraTableModel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelEliminarProducto = new javax.swing.JPanel();
        botonQuitarProducto = new javax.swing.JButton();
        jspTabla = new javax.swing.JScrollPane();
        tablaProductosOrdenCompra = new javax.swing.JTable();
        panelBotones = new javax.swing.JPanel();
        botonComprarOrdenCompra = new javax.swing.JButton();
        botonCancelarOrdenCompra = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Órden de compra actual", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 20))); // NOI18N
        setMaximumSize(new java.awt.Dimension(700, 500));
        setMinimumSize(new java.awt.Dimension(700, 500));
        setPreferredSize(new java.awt.Dimension(700, 500));
        setLayout(new java.awt.BorderLayout());

        panelEliminarProducto.setMaximumSize(new java.awt.Dimension(129, 433));
        panelEliminarProducto.setMinimumSize(new java.awt.Dimension(129, 433));

        botonQuitarProducto.setText("Quitar Producto");
        botonQuitarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonQuitarProductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelEliminarProductoLayout = new javax.swing.GroupLayout(panelEliminarProducto);
        panelEliminarProducto.setLayout(panelEliminarProductoLayout);
        panelEliminarProductoLayout.setHorizontalGroup(
            panelEliminarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEliminarProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonQuitarProducto)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelEliminarProductoLayout.setVerticalGroup(
            panelEliminarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEliminarProductoLayout.createSequentialGroup()
                .addComponent(botonQuitarProducto)
                .addGap(0, 410, Short.MAX_VALUE))
        );

        add(panelEliminarProducto, java.awt.BorderLayout.LINE_START);

        tablaProductosOrdenCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaProductosOrdenCompra.getTableHeader().setReorderingAllowed(false);
        jspTabla.setViewportView(tablaProductosOrdenCompra);

        add(jspTabla, java.awt.BorderLayout.CENTER);

        botonComprarOrdenCompra.setText("Comprar Orden de Compra");
        botonComprarOrdenCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonComprarOrdenCompraActionPerformed(evt);
            }
        });
        panelBotones.add(botonComprarOrdenCompra);

        botonCancelarOrdenCompra.setText("Cancelar Orden de Compra");
        botonCancelarOrdenCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarOrdenCompraActionPerformed(evt);
            }
        });
        panelBotones.add(botonCancelarOrdenCompra);

        add(panelBotones, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void botonQuitarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonQuitarProductoActionPerformed
        try {
            int filaSeleccionada = tablaProductosOrdenCompra.getSelectedRow();
            if(filaSeleccionada >= 0) {
                ordenDeCompraJpaController.eliminarProductoOrdenDeCompra(filaSeleccionada);
                ArrayList<ProductoOrdenCompra> productosOrdenDeCompraEnCurso = ordenDeCompraJpaController.obtenerProductosOrdenDeCompraEnCurso();
                productoOrdenDeCompraTableModel.setProductosOrdenDeCompra(productosOrdenDeCompraEnCurso);
            } else {
                throw new Exception("No ha seleccionado ningún producto.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            tablaProductosOrdenCompra.clearSelection();
        }
    }//GEN-LAST:event_botonQuitarProductoActionPerformed

    private void botonComprarOrdenCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonComprarOrdenCompraActionPerformed
        try {
            ordenDeCompraJpaController.comprarOrdenDeCompraEnCurso();
            ArrayList<ProductoOrdenCompra> productosOrdenDeCompraEnCurso = ordenDeCompraJpaController.obtenerProductosOrdenDeCompraEnCurso();
            productoOrdenDeCompraTableModel.setProductosOrdenDeCompra(productosOrdenDeCompraEnCurso);
            JOptionPane.showMessageDialog(null, "Se ha completado la compra de productos.", "Enhorabuena", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_botonComprarOrdenCompraActionPerformed

    private void botonCancelarOrdenCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarOrdenCompraActionPerformed
        ordenDeCompraJpaController.cancelarOrdenDeCompraEnCurso();
        ArrayList<ProductoOrdenCompra> productosOrdenDeCompraEnCurso = ordenDeCompraJpaController.obtenerProductosOrdenDeCompraEnCurso();
        productoOrdenDeCompraTableModel.setProductosOrdenDeCompra(productosOrdenDeCompraEnCurso);
    }//GEN-LAST:event_botonCancelarOrdenCompraActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonCancelarOrdenCompra;
    private javax.swing.JButton botonComprarOrdenCompra;
    private javax.swing.JButton botonQuitarProducto;
    private javax.swing.JScrollPane jspTabla;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelEliminarProducto;
    private javax.swing.JTable tablaProductosOrdenCompra;
    // End of variables declaration//GEN-END:variables
}
