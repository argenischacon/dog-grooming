package com.argenischacon.gui.owner;

import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.ArrayList;
import javax.swing.*;

import com.argenischacon.gui.main.MainFrame;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.argenischacon.dto.owner.OwnerListDto;
import com.argenischacon.gui.common.PaginatedTableModel;
import com.argenischacon.gui.common.SearchTextField;
import java.awt.Cursor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.argenischacon.service.OwnerService;
import com.argenischacon.service.exception.OwnerWithDogsException;
import com.argenischacon.service.impl.OwnerServiceImpl;

public class OwnerPanel extends javax.swing.JPanel {

    private static final Logger logger = LoggerFactory.getLogger(OwnerPanel.class);
    private static final String DEFAULT_ICON = "/icons/default.svg";
    private static final int ICON_SIZE = 48;
    private final OwnerService ownerService;
    private final MainFrame mainFrame;
    private static final int ROWS_PER_PAGE = 20;
    private PaginatedTableModel model;
    private int currentPage = 1;
    private String currentSearchText = "";

    public OwnerPanel(MainFrame mainFrame) {
        this.ownerService = new OwnerServiceImpl();
        this.mainFrame = mainFrame;
        initComponents();
        loadIcons();
        initTable();
        updateButtons();
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
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        reloadOwnerTableButton = new javax.swing.JButton();
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(32767, 5));
        jScrollPane1 = new javax.swing.JScrollPane();
        ownersTable = new javax.swing.JTable();
        paginationPanel = new javax.swing.JPanel();
        firstButton = new javax.swing.JButton();
        filler8 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        prevButton = new javax.swing.JButton();
        filler9 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        pageLabel = new javax.swing.JLabel();
        filler10 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        nextButton = new javax.swing.JButton();
        filler11 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        lastButton = new javax.swing.JButton();
        searchPanel = new javax.swing.JPanel();
        searchTextField = new SearchTextField();
        filler16 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        searchButton = new javax.swing.JButton();

        buttonsPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        buttonsPanel.setLayout(new javax.swing.BoxLayout(buttonsPanel, javax.swing.BoxLayout.Y_AXIS));
        buttonsPanel.add(filler1);

        createOwnerButton.setToolTipText("Nuevo dueño");
        createOwnerButton.setAlignmentX(0.5F);
        createOwnerButton.setMaximumSize(new java.awt.Dimension(60, 60));
        createOwnerButton.setMinimumSize(new java.awt.Dimension(60, 60));
        createOwnerButton.setPreferredSize(new java.awt.Dimension(60, 60));
        createOwnerButton.putClientProperty("JButton.buttonType", "toolBarButton");
        createOwnerButton.addActionListener(this::createOwnerButtonActionPerformed);
        buttonsPanel.add(createOwnerButton);
        buttonsPanel.add(filler2);

        viewOwnerButton.setToolTipText("Ver dueño");
        viewOwnerButton.setAlignmentX(0.5F);
        viewOwnerButton.setMaximumSize(new java.awt.Dimension(60, 60));
        viewOwnerButton.setMinimumSize(new java.awt.Dimension(60, 60));
        viewOwnerButton.setPreferredSize(new java.awt.Dimension(60, 60));
        viewOwnerButton.putClientProperty("JButton.buttonType", "toolBarButton");
        viewOwnerButton.addActionListener(this::viewOwnerButtonActionPerformed);
        buttonsPanel.add(viewOwnerButton);
        buttonsPanel.add(filler3);

        updateOwnerButton.setToolTipText("Editar dueño");
        updateOwnerButton.setAlignmentX(0.5F);
        updateOwnerButton.setMaximumSize(new java.awt.Dimension(60, 60));
        updateOwnerButton.setMinimumSize(new java.awt.Dimension(60, 60));
        updateOwnerButton.setPreferredSize(new java.awt.Dimension(60, 60));
        updateOwnerButton.putClientProperty("JButton.buttonType", "toolBarButton");
        updateOwnerButton.addActionListener(this::updateOwnerButtonActionPerformed);
        buttonsPanel.add(updateOwnerButton);
        buttonsPanel.add(filler4);

        deleteOwnerButton.setToolTipText("Eliminar dueño");
        deleteOwnerButton.setAlignmentX(0.5F);
        deleteOwnerButton.setMaximumSize(new java.awt.Dimension(60, 60));
        deleteOwnerButton.setMinimumSize(new java.awt.Dimension(60, 60));
        deleteOwnerButton.setPreferredSize(new java.awt.Dimension(60, 60));
        deleteOwnerButton.putClientProperty("JButton.buttonType", "toolBarButton");
        deleteOwnerButton.addActionListener(this::deleteOwnerButtonActionPerformed);
        buttonsPanel.add(deleteOwnerButton);
        buttonsPanel.add(filler5);
        buttonsPanel.add(filler6);

        reloadOwnerTableButton.setToolTipText("Recargar tabla");
        reloadOwnerTableButton.setAlignmentX(0.5F);
        reloadOwnerTableButton.setMaximumSize(new java.awt.Dimension(60, 60));
        reloadOwnerTableButton.setMinimumSize(new java.awt.Dimension(60, 60));
        reloadOwnerTableButton.setPreferredSize(new java.awt.Dimension(60, 60));
        reloadOwnerTableButton.putClientProperty("JButton.buttonType", "toolBarButton");
        reloadOwnerTableButton.addActionListener(this::reloadOwnerTableButtonActionPerformed);
        buttonsPanel.add(reloadOwnerTableButton);
        buttonsPanel.add(filler7);

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

        paginationPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        firstButton.setText("Primero");
        firstButton.setMaximumSize(new java.awt.Dimension(79, 23));
        firstButton.setMinimumSize(new java.awt.Dimension(79, 23));
        firstButton.setPreferredSize(new java.awt.Dimension(79, 23));
        firstButton.addActionListener(this::firstButtonActionPerformed);
        paginationPanel.add(firstButton);
        paginationPanel.add(filler8);

        prevButton.setText("Anterior");
        prevButton.setMaximumSize(new java.awt.Dimension(79, 23));
        prevButton.setMinimumSize(new java.awt.Dimension(79, 23));
        prevButton.setPreferredSize(new java.awt.Dimension(79, 23));
        prevButton.addActionListener(this::prevButtonActionPerformed);
        paginationPanel.add(prevButton);
        paginationPanel.add(filler9);

        pageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pageLabel.setText("Página 1 de 100");
        pageLabel.setPreferredSize(new java.awt.Dimension(150, 16));
        paginationPanel.add(pageLabel);
        paginationPanel.add(filler10);

        nextButton.setText("Siguiente");
        nextButton.addActionListener(this::nextButtonActionPerformed);
        paginationPanel.add(nextButton);
        paginationPanel.add(filler11);

        lastButton.setText("Último");
        lastButton.setMaximumSize(new java.awt.Dimension(79, 23));
        lastButton.setMinimumSize(new java.awt.Dimension(79, 23));
        lastButton.setPreferredSize(new java.awt.Dimension(79, 23));
        lastButton.addActionListener(this::lastButtonActionPerformed);
        paginationPanel.add(lastButton);

        searchPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        searchPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        searchTextField.setMaximumSize(new java.awt.Dimension(300, 22));
        searchTextField.setMinimumSize(new java.awt.Dimension(300, 22));
        searchTextField.setPreferredSize(new java.awt.Dimension(300, 22));
        searchTextField.addActionListener(this::searchButtonActionPerformed);
        searchPanel.add(searchTextField);
        searchPanel.add(filler16);

        searchButton.setText("Buscar");
        searchButton.addActionListener(this::searchButtonActionPerformed);
        searchPanel.add(searchButton);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
                            .addComponent(paginationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(searchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(buttonsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(paginationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void loadIcons() {
        setIconSVG(createOwnerButton, "/icons/owner/create.svg");
        setIconSVG(viewOwnerButton, "/icons/owner/view.svg");
        setIconSVG(updateOwnerButton, "/icons/owner/update.svg");
        setIconSVG(deleteOwnerButton, "/icons/owner/delete.svg");
        setIconSVG(reloadOwnerTableButton, "/icons/reload.svg");
    }

    private void initTable() {
        ownersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        String[] columnNames = new String[]{"id", "dni", "name", "lastname", "phone"};
        model = new PaginatedTableModel(new ArrayList<>(), ROWS_PER_PAGE, 0L, columnNames);
        ownersTable.setModel(model);

        hideColumn(ownersTable, "id");

        // Cargar datos asíncronamente
        loadPageData();
    }

    private void createOwnerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createOwnerButtonActionPerformed
        JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        OwnerCreateDialog dialog = new OwnerCreateDialog(mainFrame, true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        if (dialog.isSuccess()) {
            loadPageData();
        }
    }//GEN-LAST:event_createOwnerButtonActionPerformed

    private void updateOwnerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateOwnerButtonActionPerformed
        Long ownerId = getSelectedOwnerId();
        if (ownerId != null) {
            JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            OwnerUpdateDialog dialog = new OwnerUpdateDialog(mainFrame, true, ownerId);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            if (dialog.isSuccess()) {
                loadPageData();
            }
        }
    }//GEN-LAST:event_updateOwnerButtonActionPerformed

    private void viewOwnerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewOwnerButtonActionPerformed
        Long ownerId = getSelectedOwnerId();
        if (ownerId != null) {
            JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            OwnerViewDialog dialog = new OwnerViewDialog(mainFrame, true, ownerId);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }
    }//GEN-LAST:event_viewOwnerButtonActionPerformed

    private void deleteOwnerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteOwnerButtonActionPerformed
        int viewRow = ownersTable.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una fila");
            return;
        }

        int modelRow = ownersTable.convertRowIndexToModel(viewRow);
        Long ownerId = Long.valueOf((String) model.getValueAt(modelRow, 0));
        Object name = model.getValueAt(modelRow, 2);
        Object dni = model.getValueAt(modelRow, 1);

        JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        int option = JOptionPane.showConfirmDialog(
                mainFrame,
                "¿Desea eliminar al dueño '" + name + "' (DNI: " + dni + ")?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (option == JOptionPane.YES_OPTION) {
            executeOwnerDelete(ownerId);
        }
    }//GEN-LAST:event_deleteOwnerButtonActionPerformed

    private void reloadOwnerTableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reloadOwnerTableButtonActionPerformed
        reloadOwnerTableButton.setEnabled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        searchTextField.setText("");
        currentSearchText = "";
        currentPage = 1;
        loadPageData();
        reloadOwnerTableButton.setEnabled(true);
        setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_reloadOwnerTableButtonActionPerformed

    private void firstButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstButtonActionPerformed
        currentPage = 1;
        loadPageData();
    }//GEN-LAST:event_firstButtonActionPerformed

    private void prevButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevButtonActionPerformed
        if (currentPage > 1) {
            currentPage--;
            loadPageData();
        }
    }//GEN-LAST:event_prevButtonActionPerformed

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        if (currentPage < model.getTotalPages()) {
            currentPage++;
            loadPageData();
        }
    }//GEN-LAST:event_nextButtonActionPerformed

    private void lastButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastButtonActionPerformed
        currentPage = model.getTotalPages();
        loadPageData();
    }//GEN-LAST:event_lastButtonActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {
        currentSearchText = searchTextField.getText().trim();
        currentPage = 1;
        loadPageData();
    }

    @SuppressWarnings("unchecked")
    private void loadPageData() {
        mainFrame.showOverlay(true);
        int offset = (currentPage - 1) * ROWS_PER_PAGE;

        // Object[] contendrá: [0] Long totalRecords, [1] List<String[]> pageData
        SwingWorker<Object[], Void> worker = new SwingWorker<>() {
            @Override
            protected Object[] doInBackground() throws Exception {
                Long total;
                List<OwnerListDto> owners;

                if (currentSearchText.isEmpty()) {
                    total = ownerService.count();
                    owners = ownerService.list(offset, ROWS_PER_PAGE);
                } else {
                    total = ownerService.countSearch(currentSearchText);
                    owners = ownerService.search(currentSearchText, offset, ROWS_PER_PAGE);
                }

                // Transformar DTO a formato de tabla
                List<String[]> data = new ArrayList<>();
                for (OwnerListDto o : owners) {
                    data.add(new String[]{
                            String.valueOf(o.getId()),
                            o.getDni(),
                            o.getName(),
                            o.getLastname(),
                            o.getPhone()
                    });
                }
                return new Object[]{total, data};
            }

            @Override
            protected void done() {
                try {
                    Object[] result = get();
                    Long totalRecords = (Long) result[0];
                    List<String[]> pageData = (List<String[]>) result[1];

                    model.updateData(pageData, totalRecords);
                    updateButtons();
                } catch (Exception e) {
                    logger.error("Error loading data", e);
                    JOptionPane.showMessageDialog(OwnerPanel.this,
                            "Error al cargar datos: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    mainFrame.showOverlay(false);
                }
            }
        };
        worker.execute();
    }

    private void executeOwnerDelete(Long ownerId) {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                ownerService.delete(ownerId);
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    JOptionPane.showMessageDialog(OwnerPanel.this, "Dueño eliminado exitosamente", "Exito",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadPageData();
                } catch (Exception e) {
                    if (e instanceof ExecutionException && e.getCause() instanceof OwnerWithDogsException) {
                        JOptionPane.showMessageDialog(OwnerPanel.this, e.getCause().getMessage(), "No se puede eliminar", JOptionPane.WARNING_MESSAGE);
                    } else {
                        logger.error("Error deleting owner", e);
                        JOptionPane.showMessageDialog(OwnerPanel.this, "Error al eliminar el dueño", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        };
        worker.execute();
    }

    private void updateButtons() {
        if (model != null) {
            firstButton.setEnabled(currentPage > 1);
            prevButton.setEnabled(currentPage > 1);
            nextButton.setEnabled(currentPage < model.getTotalPages());
            lastButton.setEnabled(currentPage < model.getTotalPages());
            pageLabel.setText(getPageInfo());
        }
    }

    private String getPageInfo() {
        return "Página " + currentPage + " de " + model.getTotalPages();
    }

    private Long getSelectedOwnerId() {
        int viewRow = ownersTable.getSelectedRow(); //index visible in the table
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una fila");
            return null;
        }

        // Converts the view index to the actual model index
        int modelRow = ownersTable.convertRowIndexToModel(viewRow);

        return Long.valueOf((String) model.getValueAt(modelRow, 0));
    }

    private void hideColumn(JTable table, String columnName) {
        javax.swing.table.TableColumn columnToRemove = null;
        javax.swing.table.TableColumnModel cm = table.getColumnModel();
        for (int i = 0; i < cm.getColumnCount(); i++) {
            javax.swing.table.TableColumn col = cm.getColumn(i);
            Object header = col.getHeaderValue();
            if (header != null && columnName.equals(header.toString())) {
                columnToRemove = col;
                break;
            }
        }
        if (columnToRemove != null) {
            table.removeColumn(columnToRemove);
        }
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
            logger.debug("Loading SVG icon: {}", path);
            return new FlatSVGIcon(resource).derive(ICON_SIZE, ICON_SIZE);
        } catch (Exception e) {
            logger.warn("Error loading SVG icon: {}", path, e);
            return null;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JButton createOwnerButton;
    private javax.swing.JButton deleteOwnerButton;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler10;
    private javax.swing.Box.Filler filler11;
    private javax.swing.Box.Filler filler16;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.Box.Filler filler8;
    private javax.swing.Box.Filler filler9;
    private javax.swing.JButton firstButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton lastButton;
    private javax.swing.JButton nextButton;
    private javax.swing.JTable ownersTable;
    private javax.swing.JLabel pageLabel;
    private javax.swing.JPanel paginationPanel;
    private javax.swing.JButton prevButton;
    private javax.swing.JButton reloadOwnerTableButton;
    private javax.swing.JButton searchButton;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JButton updateOwnerButton;
    private javax.swing.JButton viewOwnerButton;
    // End of variables declaration//GEN-END:variables
}
