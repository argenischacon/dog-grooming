package gui;

import java.net.URL;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import dto.owner.OwnerListDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.OwnerService;
import service.impl.OwnerServiceImpl;

public class OwnerPanel extends javax.swing.JPanel {

    private static final Logger logger = LoggerFactory.getLogger(OwnerPanel.class);
    private static final String DEFAULT_ICON = "/icons/default.svg";
    private static final int ICON_SIZE = 48;
    private final OwnerService ownerService;
    private final DefaultTableModel model;

    public OwnerPanel() {
        this.ownerService = new OwnerServiceImpl();
        this.model = new NonEditableTableModel();
        initComponents();
        loadIcons();
        initializeTable();
        populateTable();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonsPanel = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(32767, 5));
        createOwnerButton = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(32767, 5));
        viewOwnerButton = new javax.swing.JButton();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(32767, 5));
        updateOwnerButton = new javax.swing.JButton();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(32767, 5));
        deleteOwnerButton = new javax.swing.JButton();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(32767, 5));
        jScrollPane1 = new javax.swing.JScrollPane();
        ownersTable = new javax.swing.JTable();

        buttonsPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        buttonsPanel.setLayout(new javax.swing.BoxLayout(buttonsPanel, javax.swing.BoxLayout.Y_AXIS));
        buttonsPanel.add(filler1);

        createOwnerButton.setToolTipText("Nuevo dueño");
        createOwnerButton.setAlignmentX(0.5F);
        createOwnerButton.setMaximumSize(new java.awt.Dimension(60, 60));
        createOwnerButton.setPreferredSize(new java.awt.Dimension(75, 75));
        createOwnerButton.putClientProperty("JButton.buttonType", "toolBarButton");
        createOwnerButton.addActionListener(this::createOwnerButtonActionPerformed);
        buttonsPanel.add(createOwnerButton);
        buttonsPanel.add(filler2);

        viewOwnerButton.setToolTipText("Ver dueño");
        viewOwnerButton.setAlignmentX(0.5F);
        viewOwnerButton.setMaximumSize(new java.awt.Dimension(60, 60));
        viewOwnerButton.setPreferredSize(new java.awt.Dimension(75, 75));
        viewOwnerButton.putClientProperty("JButton.buttonType", "toolBarButton");
        buttonsPanel.add(viewOwnerButton);
        buttonsPanel.add(filler3);

        updateOwnerButton.setToolTipText("Editar dueño");
        updateOwnerButton.setAlignmentX(0.5F);
        updateOwnerButton.setMaximumSize(new java.awt.Dimension(60, 60));
        updateOwnerButton.setPreferredSize(new java.awt.Dimension(75, 75));
        updateOwnerButton.putClientProperty("JButton.buttonType", "toolBarButton");
        buttonsPanel.add(updateOwnerButton);
        buttonsPanel.add(filler4);

        deleteOwnerButton.setToolTipText("Eliminar dueño");
        deleteOwnerButton.setAlignmentX(0.5F);
        deleteOwnerButton.setMaximumSize(new java.awt.Dimension(60, 60));
        deleteOwnerButton.setPreferredSize(new java.awt.Dimension(75, 75));
        deleteOwnerButton.putClientProperty("JButton.buttonType", "toolBarButton");
        buttonsPanel.add(deleteOwnerButton);
        buttonsPanel.add(filler5);

        ownersTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(ownersTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(buttonsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void createOwnerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createOwnerButtonActionPerformed
        JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        OwnerCreateDialog dialog = new OwnerCreateDialog(mainFrame, true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        if(dialog.isSuccess()){
            populateTable();
        }
    }//GEN-LAST:event_createOwnerButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JButton createOwnerButton;
    private javax.swing.JButton deleteOwnerButton;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable ownersTable;
    private javax.swing.JButton updateOwnerButton;
    private javax.swing.JButton viewOwnerButton;
    // End of variables declaration//GEN-END:variables

    private void initializeTable() {
        model.setColumnIdentifiers(new String[]{"id", "dni", "name", "lastname", "phone"});
        ownersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ownersTable.setModel(model);
    }

    private void populateTable() {
        SwingWorker<List<OwnerListDto>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<OwnerListDto> doInBackground() throws Exception {
                return ownerService.list(0, 10);
            }

            @Override
            protected void done() {
                try {
                    model.setRowCount(0);
                    for (OwnerListDto o : get()) {
                        model.addRow(new Object[]{o.getId(), o.getDni(), o.getName(), o.getLastname(), o.getPhone()});
                    }
                } catch (Exception e) {
                    logger.error("Error al cargar los datos de los propietarios", e);
                    javax.swing.JOptionPane.showMessageDialog(OwnerPanel.this, "No se pudieron cargar los datos",
                            "Error de Carga", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    private void loadIcons() {
        setIconSVG(createOwnerButton, "/icons/owner/create.svg");
        setIconSVG(viewOwnerButton, "/icons/owner/view.svg");
        setIconSVG(updateOwnerButton, "/icons/owner/update.svg");
        setIconSVG(deleteOwnerButton, "/icons/owner/delete.svg");
    }

    private void setIconSVG(JButton button, String path) {
        FlatSVGIcon icon = loadSVG(path);
        if (icon == null) {
            icon = loadSVG(DEFAULT_ICON);
        }
        button.setIcon(icon);
    }

    private FlatSVGIcon loadSVG(String path) {
        try {
            URL resource = getClass().getResource(path);
            if (resource == null) {
                throw new IllegalArgumentException("No se encontró el recurso: " + path);
            }
            logger.debug("Cargando icono SVG: {}", path);
            return new FlatSVGIcon(resource).derive(ICON_SIZE, ICON_SIZE);
        } catch (Exception e) {
            logger.warn("Error al cargar el icono SVG: {}", path, e);
            return null;
        }
    }
}
