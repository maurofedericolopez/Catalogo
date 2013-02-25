package catalogo.vistas;

import catalogo.Catalogo;
import catalogo.controladores.JPA.VendedorJpaController;
import catalogo.controladores.JPA.OrdenDeCompraJpaController;
import catalogo.modelo.Vendedor;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

/**
 *
 * @author Mauro Federico Lopez
 */
public class VendedorUI extends javax.swing.JFrame {

    private OrdenDeCompraJpaController ordenDeCompraJpaController;
    private VendedorJpaController clienteJpaController;

    /**
     * Creates new form VendedorUI
     */
    public VendedorUI() {
        initComponents();
        clienteJpaController = Catalogo.getVendedorJpaController();
        agregarComponenteAlCentro(panelIniciarSesion);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelIniciarSesion = new javax.swing.JPanel();
        etiquetaNombreDeUsuario = new javax.swing.JLabel();
        campoNombreDeUsuario = new javax.swing.JTextField();
        etiquetaContraseña = new javax.swing.JLabel();
        campoContraseña = new javax.swing.JPasswordField();
        botonIniciarSesion = new javax.swing.JButton();
        barraMenu = new javax.swing.JMenuBar();
        menuOperaciones = new javax.swing.JMenu();
        itemMenuCatalogo = new javax.swing.JMenuItem();
        itemMenuOrdenDeCompraActual = new javax.swing.JMenuItem();
        itemMenuOrdenesPendientes = new javax.swing.JMenuItem();
        itemMenuHistorialOrdenCompra = new javax.swing.JMenuItem();
        itemMenuMisDatos = new javax.swing.JMenuItem();
        itemMenuCerrarSesion = new javax.swing.JMenuItem();

        panelIniciarSesion.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Iniciar Sesión", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 20))); // NOI18N
        panelIniciarSesion.setMaximumSize(new java.awt.Dimension(700, 500));
        panelIniciarSesion.setMinimumSize(new java.awt.Dimension(700, 500));
        panelIniciarSesion.setPreferredSize(new java.awt.Dimension(700, 500));

        etiquetaNombreDeUsuario.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        etiquetaNombreDeUsuario.setText("Nombre de usuario");

        campoNombreDeUsuario.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        etiquetaContraseña.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        etiquetaContraseña.setText("Contraseña");

        campoContraseña.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        botonIniciarSesion.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        botonIniciarSesion.setText("Iniciar Sesión");
        botonIniciarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonIniciarSesionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelIniciarSesionLayout = new javax.swing.GroupLayout(panelIniciarSesion);
        panelIniciarSesion.setLayout(panelIniciarSesionLayout);
        panelIniciarSesionLayout.setHorizontalGroup(
            panelIniciarSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIniciarSesionLayout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addGroup(panelIniciarSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(etiquetaContraseña, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(etiquetaNombreDeUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelIniciarSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(campoNombreDeUsuario)
                    .addComponent(campoContraseña)
                    .addComponent(botonIniciarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(148, Short.MAX_VALUE))
        );
        panelIniciarSesionLayout.setVerticalGroup(
            panelIniciarSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIniciarSesionLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(panelIniciarSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiquetaNombreDeUsuario)
                    .addComponent(campoNombreDeUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(panelIniciarSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiquetaContraseña)
                    .addComponent(campoContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonIniciarSesion)
                .addContainerGap(327, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Vendedor");
        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(700, 500));

        menuOperaciones.setMnemonic('e');
        menuOperaciones.setText("Operaciones");
        menuOperaciones.setEnabled(false);

        itemMenuCatalogo.setMnemonic('t');
        itemMenuCatalogo.setText("Ver catálogo");
        itemMenuCatalogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuCatalogoActionPerformed(evt);
            }
        });
        menuOperaciones.add(itemMenuCatalogo);

        itemMenuOrdenDeCompraActual.setText("Órden de compra actual");
        itemMenuOrdenDeCompraActual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuOrdenDeCompraActualActionPerformed(evt);
            }
        });
        menuOperaciones.add(itemMenuOrdenDeCompraActual);

        itemMenuOrdenesPendientes.setMnemonic('y');
        itemMenuOrdenesPendientes.setText("Órdenes de compra pendientes");
        itemMenuOrdenesPendientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuOrdenesPendientesActionPerformed(evt);
            }
        });
        menuOperaciones.add(itemMenuOrdenesPendientes);

        itemMenuHistorialOrdenCompra.setText("Historial de órdenes de compra");
        itemMenuHistorialOrdenCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuHistorialOrdenCompraActionPerformed(evt);
            }
        });
        menuOperaciones.add(itemMenuHistorialOrdenCompra);

        itemMenuMisDatos.setMnemonic('d');
        itemMenuMisDatos.setText("Mis datos");
        itemMenuMisDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuMisDatosActionPerformed(evt);
            }
        });
        menuOperaciones.add(itemMenuMisDatos);

        itemMenuCerrarSesion.setText("Cerrar sesión");
        itemMenuCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuCerrarSesionActionPerformed(evt);
            }
        });
        menuOperaciones.add(itemMenuCerrarSesion);

        barraMenu.add(menuOperaciones);

        setJMenuBar(barraMenu);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itemMenuCatalogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuCatalogoActionPerformed
        agregarComponenteAlCentro(new CatalogoUI(ordenDeCompraJpaController));
    }//GEN-LAST:event_itemMenuCatalogoActionPerformed

    private void itemMenuOrdenesPendientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuOrdenesPendientesActionPerformed
        agregarComponenteAlCentro(new OrdenesDeCompraPendientesUI(ordenDeCompraJpaController));
    }//GEN-LAST:event_itemMenuOrdenesPendientesActionPerformed

    private void itemMenuMisDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuMisDatosActionPerformed
        MisDatosUI misDatosUI = new MisDatosUI();
        agregarComponenteAlCentro(misDatosUI);
        Vendedor cliente = ordenDeCompraJpaController.getVendedor();
        misDatosUI.setVendedor(cliente);
    }//GEN-LAST:event_itemMenuMisDatosActionPerformed

    private void itemMenuOrdenDeCompraActualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuOrdenDeCompraActualActionPerformed
        agregarComponenteAlCentro(new OrdenDeCompraActualUI(ordenDeCompraJpaController));
    }//GEN-LAST:event_itemMenuOrdenDeCompraActualActionPerformed

    private void botonIniciarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonIniciarSesionActionPerformed
        try {
            String username = campoNombreDeUsuario.getText();
            String password = campoContraseña.getText();
            Vendedor cliente = clienteJpaController.iniciarSesion(username, password);
            ordenDeCompraJpaController = new OrdenDeCompraJpaController(cliente);
            campoNombreDeUsuario.setText("");
            campoContraseña.setText("");
            limpiarCentro();
            menuOperaciones.setEnabled(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_botonIniciarSesionActionPerformed

    private void itemMenuCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuCerrarSesionActionPerformed
        ordenDeCompraJpaController = null;
        menuOperaciones.setEnabled(false);
        agregarComponenteAlCentro(panelIniciarSesion);
    }//GEN-LAST:event_itemMenuCerrarSesionActionPerformed

    private void itemMenuHistorialOrdenCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuHistorialOrdenCompraActionPerformed
        agregarComponenteAlCentro(new HistorialOrdenesDeCompraUI(ordenDeCompraJpaController));
    }//GEN-LAST:event_itemMenuHistorialOrdenCompraActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VendedorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VendedorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VendedorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VendedorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new VendedorUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar barraMenu;
    private javax.swing.JButton botonIniciarSesion;
    private javax.swing.JPasswordField campoContraseña;
    private javax.swing.JTextField campoNombreDeUsuario;
    private javax.swing.JLabel etiquetaContraseña;
    private javax.swing.JLabel etiquetaNombreDeUsuario;
    private javax.swing.JMenuItem itemMenuCatalogo;
    private javax.swing.JMenuItem itemMenuCerrarSesion;
    private javax.swing.JMenuItem itemMenuHistorialOrdenCompra;
    private javax.swing.JMenuItem itemMenuMisDatos;
    private javax.swing.JMenuItem itemMenuOrdenDeCompraActual;
    private javax.swing.JMenuItem itemMenuOrdenesPendientes;
    public static javax.swing.JMenu menuOperaciones;
    private javax.swing.JPanel panelIniciarSesion;
    // End of variables declaration//GEN-END:variables

    private void agregarComponenteAlCentro(JComponent componente) {
        limpiarCentro();
        add(componente, BorderLayout.CENTER);
        componente.setVisible(true);
        componente.updateUI();
        pack();
    }

    private void limpiarCentro() {
        getContentPane().removeAll();
        getContentPane().repaint();
    }

}
