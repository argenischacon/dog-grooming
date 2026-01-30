package gui.dog;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import dto.dog.DogListDto;
import gui.common.NonEditableTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.DogService;
import service.impl.DogServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.List;

public class DogPanel extends javax.swing.JPanel {

    private static final Logger logger = LoggerFactory.getLogger(DogPanel.class);
    private static final String DEFAULT_ICON = "/icons/default.svg";
    private static final int ICON_SIZE = 48;
    private final DogService dogService;
    private final DefaultTableModel model;

    public DogPanel() {
        this.dogService = new DogServiceImpl();
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
        createDogButton = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(32767, 5));
        viewDogButton = new javax.swing.JButton();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(32767, 5));
        updateDogButton = new javax.swing.JButton();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(32767, 5));
        deleteDogButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        dogsTable = new javax.swing.JTable();

        buttonsPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        buttonsPanel.setLayout(new javax.swing.BoxLayout(buttonsPanel, javax.swing.BoxLayout.Y_AXIS));
        buttonsPanel.add(filler1);

        createDogButton.setToolTipText("Nuevo perro");
        createDogButton.setAlignmentX(0.5F);
        createDogButton.setMaximumSize(new java.awt.Dimension(60, 60));
        createDogButton.setMinimumSize(new java.awt.Dimension(60, 60));
        createDogButton.setPreferredSize(new java.awt.Dimension(60, 60));
        createDogButton.addActionListener(this::createDogButtonActionPerformed);
        buttonsPanel.add(createDogButton);
        buttonsPanel.add(filler2);

        viewDogButton.setToolTipText("Ver perro");
        viewDogButton.setAlignmentX(0.5F);
        viewDogButton.setMaximumSize(new java.awt.Dimension(60, 60));
        viewDogButton.setMinimumSize(new java.awt.Dimension(60, 60));
        viewDogButton.setPreferredSize(new java.awt.Dimension(60, 60));
        buttonsPanel.add(viewDogButton);
        buttonsPanel.add(filler3);

        updateDogButton.setToolTipText("Editar perro");
        updateDogButton.setAlignmentX(0.5F);
        updateDogButton.setMaximumSize(new java.awt.Dimension(60, 60));
        updateDogButton.setMinimumSize(new java.awt.Dimension(60, 60));
        updateDogButton.setPreferredSize(new java.awt.Dimension(60, 60));
        buttonsPanel.add(updateDogButton);
        buttonsPanel.add(filler4);

        deleteDogButton.setToolTipText("Eliminar perro");
        deleteDogButton.setAlignmentX(0.5F);
        deleteDogButton.setMaximumSize(new java.awt.Dimension(60, 60));
        deleteDogButton.setMinimumSize(new java.awt.Dimension(60, 60));
        deleteDogButton.setPreferredSize(new java.awt.Dimension(60, 60));
        buttonsPanel.add(deleteDogButton);

        dogsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(dogsTable);

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

    private void createDogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createDogButtonActionPerformed
        JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        DogCreateDialog dialog = new DogCreateDialog(mainFrame, true);
        dialog.setLocationRelativeTo(mainFrame);
        dialog.setVisible(true);
        if (dialog.isSuccess()) {
            populateTable();
        }
    }//GEN-LAST:event_createDogButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JButton createDogButton;
    private javax.swing.JButton deleteDogButton;
    private javax.swing.JTable dogsTable;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton updateDogButton;
    private javax.swing.JButton viewDogButton;
    // End of variables declaration//GEN-END:variables

    private void initializeTable() {
        model.setColumnIdentifiers(new String[]{"id", "name", "breed", "color", "owner_id", "owner_name"});
        dogsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dogsTable.setModel(model);
    }

    private void populateTable() {
        SwingWorker<List<DogListDto>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<DogListDto> doInBackground() throws Exception {
                return dogService.list(0, 10);
            }

            @Override
            protected void done() {
                try {
                    model.setRowCount(0);
                    for (DogListDto d : get()) {
                        model.addRow(new Object[]{d.getId(), d.getName(), d.getDogBreed(), d.getColor(), d.getOwnerId(), d.getOwnerName()});
                    }
                } catch (Exception e) {
                    logger.error("Error al cargar los datos de los perros", e);
                    JOptionPane.showMessageDialog(DogPanel.this, "No se pudieron cargar los datos",
                            "Error de Carga", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    private void loadIcons() {
        setIconSVG(createDogButton, "/icons/dog/create.svg");
        setIconSVG(viewDogButton, "/icons/dog/view.svg");
        setIconSVG(updateDogButton, "/icons/dog/update.svg");
        setIconSVG(deleteDogButton, "/icons/dog/delete.svg");
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
