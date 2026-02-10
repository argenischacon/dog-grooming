package com.argenischacon.gui.dog;

import com.argenischacon.dto.dog.DogListDto;
import com.argenischacon.gui.common.PaginatedTableModel;
import com.argenischacon.gui.common.SearchTextField;
import com.argenischacon.gui.main.MainFrame;
import com.argenischacon.service.DogService;
import com.argenischacon.service.exception.BusinessException;
import com.argenischacon.service.impl.DogServiceImpl;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DogPanel extends javax.swing.JPanel {

    private static final Logger logger = LoggerFactory.getLogger(DogPanel.class);
    private static final String DEFAULT_ICON = "/icons/default.svg";
    private static final int ICON_SIZE = 48;
    private final DogService dogService;
    private final MainFrame mainFrame;
    private static final int ROWS_PER_PAGE = 20;
    private PaginatedTableModel model;
    private int currentPage = 1;
    private String currentSearchText = "";

    public DogPanel(MainFrame mainFrame) {
        this.dogService = new DogServiceImpl();
        this.mainFrame = mainFrame;
        initComponents();
        loadIcons();
        initTable();
        updateButtons();
    }

    private void loadIcons() {
        setIconSVG(createDogButton, "/icons/dog/create.svg");
        setIconSVG(viewDogButton, "/icons/dog/view.svg");
        setIconSVG(updateDogButton, "/icons/dog/update.svg");
        setIconSVG(deleteDogButton, "/icons/dog/delete.svg");
        setIconSVG(reloadDogTableButton, "/icons/reload.svg");
    }

    private void initTable() {
        dogsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        String[] columnNames = new String[]{"id", "Nombre", "Raza", "Color", "owner_id", "Dueño"};
        model = new PaginatedTableModel(new ArrayList<>(), ROWS_PER_PAGE, 0L, columnNames);
        dogsTable.setModel(model);

        hideColumn(dogsTable, "id");
        hideColumn(dogsTable, "owner_id");

        // Cargar datos asíncronamente
        loadPageData();
    }

    private void createDogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createDogButtonActionPerformed
        JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        DogCreateDialog dialog = new DogCreateDialog(mainFrame, true);
        dialog.setLocationRelativeTo(mainFrame);
        dialog.setVisible(true);
        if (dialog.isSuccess()) {
            loadPageData();
        }
    }//GEN-LAST:event_createDogButtonActionPerformed

    private void viewDogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewDogButtonActionPerformed
        Long dogId = getSelectedDogId();
        if (dogId != null) {
            JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            DogViewDialog dialog = new DogViewDialog(mainFrame, true, dogId);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }
    }//GEN-LAST:event_viewDogButtonActionPerformed

    private void updateDogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateDogButtonActionPerformed
        Long dogId = getSelectedDogId();
        if (dogId != null) {
            JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            DogUpdateDialog dialog = new DogUpdateDialog(mainFrame, true, dogId);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            if (dialog.isSuccess()) {
                loadPageData();
            }
        }
    }//GEN-LAST:event_updateDogButtonActionPerformed

    private void deleteDogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteDogButtonActionPerformed
        int viewRow = dogsTable.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una fila");
            return;
        }

        int modelRow = dogsTable.convertRowIndexToModel(viewRow);
        Long dogId = Long.valueOf((String) model.getValueAt(modelRow, 0));
        Object name = model.getValueAt(modelRow, 1);
        Object breed = model.getValueAt(modelRow, 2);

        JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        int option = JOptionPane.showConfirmDialog(
                mainFrame,
                "¿Desea eliminar al perro '" + name + "' (" + breed + ")?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (option == JOptionPane.YES_OPTION) {
            executeDogDelete(dogId);
        }
    }//GEN-LAST:event_deleteDogButtonActionPerformed

    private void reloadDogTableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reloadDogTableButtonActionPerformed
        reloadDogTableButton.setEnabled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        searchTextField.setText("");
        currentSearchText = "";
        currentPage = 1;
        loadPageData();
        reloadDogTableButton.setEnabled(true);
        setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_reloadDogTableButtonActionPerformed

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
                List<DogListDto> dogs;

                if (currentSearchText.isEmpty()) {
                    total = dogService.count();
                    dogs = dogService.list(offset, ROWS_PER_PAGE);
                } else {
                    total = dogService.countSearch(currentSearchText);
                    dogs = dogService.search(currentSearchText, offset, ROWS_PER_PAGE);
                }

                // Transformar DTO a formato de tabla
                List<String[]> data = new ArrayList<>();
                for (DogListDto d : dogs) {
                    data.add(new String[]{
                            String.valueOf(d.getId()),
                            d.getName(),
                            d.getDogBreed(),
                            d.getColor(),
                            String.valueOf(d.getOwnerId()),
                            d.getOwnerName()
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
                    JOptionPane.showMessageDialog(DogPanel.this,
                            "Error al cargar datos: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    mainFrame.showOverlay(false);
                }
            }
        };
        worker.execute();
    }

    private void executeDogDelete(Long dogId) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                dogService.delete(dogId);
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    JOptionPane.showMessageDialog(
                            DogPanel.this,
                            "Perro eliminado exitosamente",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    loadPageData();
                } catch (InterruptedException | ExecutionException e) {
                    Throwable cause = e.getCause();
                    if (cause instanceof BusinessException) {
                        logger.warn("Business exception deleting dog: {}", cause.getMessage());
                        JOptionPane.showMessageDialog(DogPanel.this, cause.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
                    } else {
                        logger.error("Error deleting dog", cause);
                        JOptionPane.showMessageDialog(DogPanel.this, "Error al eliminar el perro: " + cause.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

    private Long getSelectedDogId() {
        int viewRow = dogsTable.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una fila");
            return null;
        }

        int modelRow = dogsTable.convertRowIndexToModel(viewRow);

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
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        reloadDogTableButton = new javax.swing.JButton();
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(32767, 5));
        jScrollPane1 = new javax.swing.JScrollPane();
        dogsTable = new javax.swing.JTable();
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

        createDogButton.setToolTipText("Nuevo perro");
        createDogButton.setAlignmentX(0.5F);
        createDogButton.setMaximumSize(new java.awt.Dimension(60, 60));
        createDogButton.setMinimumSize(new java.awt.Dimension(60, 60));
        createDogButton.setPreferredSize(new java.awt.Dimension(60, 60));
        createDogButton.putClientProperty("JButton.buttonType", "toolBarButton");
        createDogButton.addActionListener(this::createDogButtonActionPerformed);
        buttonsPanel.add(createDogButton);
        buttonsPanel.add(filler2);

        viewDogButton.setToolTipText("Ver perro");
        viewDogButton.setAlignmentX(0.5F);
        viewDogButton.setMaximumSize(new java.awt.Dimension(60, 60));
        viewDogButton.setMinimumSize(new java.awt.Dimension(60, 60));
        viewDogButton.setPreferredSize(new java.awt.Dimension(60, 60));
        viewDogButton.putClientProperty("JButton.buttonType", "toolBarButton");
        viewDogButton.addActionListener(this::viewDogButtonActionPerformed);
        buttonsPanel.add(viewDogButton);
        buttonsPanel.add(filler3);

        updateDogButton.setToolTipText("Editar perro");
        updateDogButton.setAlignmentX(0.5F);
        updateDogButton.setMaximumSize(new java.awt.Dimension(60, 60));
        updateDogButton.setMinimumSize(new java.awt.Dimension(60, 60));
        updateDogButton.setPreferredSize(new java.awt.Dimension(60, 60));
        updateDogButton.putClientProperty("JButton.buttonType", "toolBarButton");
        updateDogButton.addActionListener(this::updateDogButtonActionPerformed);
        buttonsPanel.add(updateDogButton);
        buttonsPanel.add(filler4);

        deleteDogButton.setToolTipText("Eliminar perro");
        deleteDogButton.setAlignmentX(0.5F);
        deleteDogButton.setMaximumSize(new java.awt.Dimension(60, 60));
        deleteDogButton.setMinimumSize(new java.awt.Dimension(60, 60));
        deleteDogButton.setPreferredSize(new java.awt.Dimension(60, 60));
        deleteDogButton.putClientProperty("JButton.buttonType", "toolBarButton");
        deleteDogButton.addActionListener(this::deleteDogButtonActionPerformed);
        buttonsPanel.add(deleteDogButton);
        buttonsPanel.add(filler6);

        reloadDogTableButton.setToolTipText("Recargar tabla");
        reloadDogTableButton.setAlignmentX(0.5F);
        reloadDogTableButton.setMaximumSize(new java.awt.Dimension(60, 60));
        reloadDogTableButton.setMinimumSize(new java.awt.Dimension(60, 60));
        reloadDogTableButton.setPreferredSize(new java.awt.Dimension(60, 60));
        reloadDogTableButton.putClientProperty("JButton.buttonType", "toolBarButton");
        reloadDogTableButton.addActionListener(this::reloadDogTableButtonActionPerformed);
        buttonsPanel.add(reloadDogTableButton);
        buttonsPanel.add(filler7);

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
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 953, Short.MAX_VALUE)
                            .addComponent(paginationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 953, Short.MAX_VALUE)))
                    .addComponent(searchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(buttonsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1)
                .addGap(0, 0, 0)
                .addComponent(paginationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JButton createDogButton;
    private javax.swing.JButton deleteDogButton;
    private javax.swing.JTable dogsTable;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler10;
    private javax.swing.Box.Filler filler11;
    private javax.swing.Box.Filler filler16;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.Box.Filler filler8;
    private javax.swing.Box.Filler filler9;
    private javax.swing.JButton firstButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton lastButton;
    private javax.swing.JButton nextButton;
    private javax.swing.JLabel pageLabel;
    private javax.swing.JPanel paginationPanel;
    private javax.swing.JButton prevButton;
    private javax.swing.JButton reloadDogTableButton;
    private javax.swing.JButton searchButton;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JButton updateDogButton;
    private javax.swing.JButton viewDogButton;
    // End of variables declaration//GEN-END:variables
}
