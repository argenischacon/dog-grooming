package com.argenischacon.gui.dog;

import com.argenischacon.dto.dog.DogDetailDto;
import com.argenischacon.dto.dog.DogFormDto;
import com.argenischacon.dto.owner.OwnerListDto;
import com.argenischacon.gui.common.PaginatedListModel;
import com.argenischacon.gui.common.SearchTextField;
import com.argenischacon.service.DogService;
import com.argenischacon.service.OwnerService;
import com.argenischacon.service.exception.BusinessException;
import com.argenischacon.service.impl.DogServiceImpl;
import com.argenischacon.service.impl.OwnerServiceImpl;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class DogUpdateDialog extends javax.swing.JDialog {

    private static final Logger logger = LoggerFactory.getLogger(DogUpdateDialog.class);
    private static final String DEFAULT_ICON = "/icons/default.svg";
    private static final int ICON_SIZE_LABEL = 80;
    private static final int ICON_SIZE_BUTTON = 16;
    private final DogService dogService;
    private final OwnerService ownerService;
    private final Validator validator;
    private boolean success = false;
    private PaginatedListModel model;
    private static final int ROWS_PER_PAGE = 15;
    private int currentPage = 1;
    private String currentSearchText = "";
    private final Long dogId;

    public DogUpdateDialog(java.awt.Frame parent, boolean modal, Long idDog) {
        super(parent, modal);
        this.dogService = new DogServiceImpl();
        this.ownerService = new OwnerServiceImpl();
        this.dogId = idDog;
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        initComponents();
        loadIcons();
        initList();
        loadDogData();
    }

    private void loadIcons() {
        setIconSVG("/icons/dog/update.svg", ICON_SIZE_LABEL, dogIconLabel::setIcon);
        setIconSVG("/icons/pagination/first.svg", ICON_SIZE_BUTTON, firstButton::setIcon);
        setIconSVG("/icons/pagination/prev.svg", ICON_SIZE_BUTTON, prevButton::setIcon);
        setIconSVG("/icons/pagination/next.svg", ICON_SIZE_BUTTON, nextButton::setIcon);
        setIconSVG("/icons/pagination/last.svg", ICON_SIZE_BUTTON, lastButton::setIcon);
    }

    private void initList() {
        model = new PaginatedListModel(new ArrayList<OwnerListDto>(), ROWS_PER_PAGE, 0L);
        ownerList.setModel(model);

        // Cargar datos de forma asíncrona
        loadPageData();
    }

    private void loadDogData() {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        //Disable form components for prevent editing while loading
        nameTextField.setEnabled(false);
        dogBreedTextField.setEnabled(false);
        colorTextField.setEnabled(false);
        allergicCheckBox.setEnabled(false);
        specialAttentionComboBox.setEnabled(false);
        observationsTextArea.setEnabled(false);
        ownerList.setEnabled(false);
        saveButton.setEnabled(false);
        saveButton.setText("Cargando...");

        // Object[] contendrá: [0] DogDetailDto dogDto, [1] Long ownerIndex
        SwingWorker<Object[], Void> worker = new SwingWorker<>() {
            @Override
            protected Object[] doInBackground() throws Exception {
                DogDetailDto dogDto = dogService.findById(dogId);
                long ownerIndex = ownerService.countByIdGreaterThan(dogDto.getOwner().getId());
                return new Object[] {dogDto, ownerIndex};
            }

            @Override
            protected void done() {
                try {
                    Object[] result = get();
                    DogDetailDto dogDto = (DogDetailDto) result[0];
                    long ownerIndex = (long) result[1];

                    currentPage = Math.toIntExact(ownerIndex / ROWS_PER_PAGE) + 1;
                    populateForm(dogDto);

                } catch (InterruptedException | ExecutionException e) {
                    logger.error("Error loading dog data", e);
                    JOptionPane.showMessageDialog(DogUpdateDialog.this, "Error al cargar los datos del perro", "Error", JOptionPane.ERROR_MESSAGE);
                    dispose();
                } finally {
                    setCursor(Cursor.getDefaultCursor());
                    nameTextField.setEnabled(true);
                    dogBreedTextField.setEnabled(true);
                    colorTextField.setEnabled(true);
                    allergicCheckBox.setEnabled(true);
                    specialAttentionComboBox.setEnabled(true);
                    observationsTextArea.setEnabled(true);
                    ownerList.setEnabled(true);
                    saveButton.setEnabled(true);
                    saveButton.setText("Guardar cambios");
                }
            }
        };

        worker.execute();
    }

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        DogFormDto dto = new DogFormDto();
        dto.setName((nameTextField.getText().isBlank())? null : nameTextField.getText());
        dto.setDogBreed((dogBreedTextField.getText().isBlank())? null : dogBreedTextField.getText());
        dto.setColor((colorTextField.getText().isBlank())? null : colorTextField.getText());
        dto.setAllergic(allergicCheckBox.isSelected());
        dto.setSpecialAttention(specialAttentionComboBox.isSelected());
        dto.setObservations((observationsTextArea.getText().isBlank())? null : observationsTextArea.getText());

        OwnerListDto selectedOwner = ownerList.getSelectedValue();
        dto.setOwnerId((selectedOwner == null)? null : selectedOwner.getId());

        Set<ConstraintViolation<DogFormDto>> violations = validator.validate(dto);

        if(!violations.isEmpty()){
            StringBuilder sb = new StringBuilder("<html><body><b>Por favor corrija los siguientes errores:</b><ul>");
            for(ConstraintViolation<DogFormDto> violation: violations){
                sb.append("<li>").append(violation.getMessage()).append("</li>");
            }
            sb.append("</ul></body></html>");
            JOptionPane.showMessageDialog(this, sb.toString(), "Error de validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        executeDogUpdate(dto);
    }//GEN-LAST:event_saveButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

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
        if(currentPage < model.getTotalPages()){
            currentPage++;
            loadPageData();
        }
    }//GEN-LAST:event_nextButtonActionPerformed

    private void lastButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastButtonActionPerformed
        currentPage = model.getTotalPages();
        loadPageData();
    }//GEN-LAST:event_lastButtonActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        currentSearchText = searchTextField.getText().trim();
        currentPage = 1;
        loadPageData();
    }//GEN-LAST:event_searchButtonActionPerformed

    @SuppressWarnings("unchecked")
    private void loadPageData() {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        int offset = (currentPage - 1) * ROWS_PER_PAGE;

        // Object[] contendrá: [0] Long totalRecords, [1] List<OwnerListDto> pageData
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

                return new Object[]{total, owners};
            }

            @Override
            protected void done() {
                try {
                    Object[] result = get();
                    Long totalRecords = (Long) result[0];
                    List<OwnerListDto> pageData = (List<OwnerListDto>) result[1];

                    model.updateData(pageData, totalRecords);
                    ownerList.clearSelection();
                    updateButtons();
                } catch (Exception e) {
                    logger.error("Error retrieving owners list", e);
                    JOptionPane.showMessageDialog(
                            DogUpdateDialog.this,
                            "Error al obtener la lista de dueños",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                } finally {
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        };
        worker.execute();
    }

    private void executeDogUpdate(DogFormDto dto) {
        saveButton.setEnabled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                dogService.update(dogId, dto);
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    success = true;
                    JOptionPane.showMessageDialog(DogUpdateDialog.this, "Perro actualizado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } catch (InterruptedException | ExecutionException e) {
                    Throwable cause = e.getCause();
                    if (cause instanceof BusinessException) {
                        logger.warn("Business exception updating dog: {}", cause.getMessage());
                        JOptionPane.showMessageDialog(DogUpdateDialog.this, cause.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
                    } else {
                        logger.error("Error updating dog with ID: {}", dogId, cause);
                        JOptionPane.showMessageDialog(DogUpdateDialog.this, "Error al actualizar el perro: " + cause.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } finally {
                    saveButton.setEnabled(true);
                    setCursor(Cursor.getDefaultCursor());
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

    public boolean isSuccess(){
        return success;
    }

    private void populateForm(DogDetailDto dto) {
        nameTextField.setText(dto.getName());
        dogBreedTextField.setText(dto.getDogBreed());
        colorTextField.setText(dto.getColor());
        allergicCheckBox.setSelected(dto.isAllergic());
        specialAttentionComboBox.setSelected(dto.isSpecialAttention());
        observationsTextArea.setText(dto.getObservations());
        ownerList.setSelectedValue(dto.getOwner(), true);
    }

    private void setIconSVG(String path, int size, Consumer<Icon> iconSetter) {
        iconSetter.accept(loadSVG(path, size));
    }

    private FlatSVGIcon loadSVG(String path, int size) {
        try {
            URL resource = getClass().getResource(path);
            if (resource == null) {
                throw new IllegalArgumentException("No se encontró el recurso: " + path);
            }
            logger.debug("Loading SVG icon: {}", path);
            return new FlatSVGIcon(resource).derive(size, size);
        } catch (Exception e) {
            logger.warn("Error loading SVG icon: {}", path, e);
            return new FlatSVGIcon(getClass().getResource(DEFAULT_ICON))
                    .derive(size, size);
        }
    }

    private static class OwnerListCellRenderer extends DefaultListCellRenderer {
        private final Icon ownerIcon;

        public OwnerListCellRenderer() {
            URL url = getClass().getResource("/icons/owner.svg");
            this.ownerIcon = (url != null) ? new FlatSVGIcon(url).derive(16, 16) : null;
        }

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            OwnerListDto owner = (OwnerListDto) value;
            label.setText(String.join(" - ", owner.getDni(), owner.getName() + " " + owner.getLastname()));
            label.setIcon(ownerIcon);
            return label;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        dogIconLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        dogBreedLabel = new javax.swing.JLabel();
        dogBreedTextField = new javax.swing.JTextField();
        colorLabel = new javax.swing.JLabel();
        colorTextField = new javax.swing.JTextField();
        specialAttentionComboBox = new javax.swing.JCheckBox();
        allergicCheckBox = new javax.swing.JCheckBox();
        observationsLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        observationsTextArea = new javax.swing.JTextArea();
        ownerLabel = new javax.swing.JLabel();
        searchPanel = new javax.swing.JPanel();
        searchTextField = new SearchTextField();
        filler16 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        searchButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        ownerList = new javax.swing.JList<>();
        paginationPanel = new javax.swing.JPanel();
        firstButton = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        prevButton = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        pageLabel = new javax.swing.JLabel();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        nextButton = new javax.swing.JButton();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        lastButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Editar Perro");
        setResizable(false);

        dogIconLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        nameLabel.setText("Nombre:");

        dogBreedLabel.setText("Raza:");

        colorLabel.setText("Color:");

        specialAttentionComboBox.setText("Requiere atención especial");

        allergicCheckBox.setText("Tiene alergias");

        observationsLabel.setText("Observaciones:");

        observationsTextArea.setColumns(20);
        observationsTextArea.setRows(5);
        jScrollPane1.setViewportView(observationsTextArea);

        ownerLabel.setText("Dueño:");

        searchPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        searchPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING, 0, 5));

        searchTextField.setMaximumSize(new java.awt.Dimension(300, 22));
        searchTextField.setMinimumSize(new java.awt.Dimension(300, 22));
        searchTextField.setPreferredSize(new java.awt.Dimension(300, 22));
        searchTextField.addActionListener(this::searchButtonActionPerformed);
        searchPanel.add(searchTextField);
        searchPanel.add(filler16);

        searchButton.setText("Buscar");
        searchButton.addActionListener(this::searchButtonActionPerformed);
        searchPanel.add(searchButton);

        ownerList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ownerList.setCellRenderer(new OwnerListCellRenderer());
        jScrollPane2.setViewportView(ownerList);

        firstButton.setMaximumSize(new java.awt.Dimension(48, 23));
        firstButton.setMinimumSize(new java.awt.Dimension(48, 23));
        firstButton.setPreferredSize(new java.awt.Dimension(48, 23));
        firstButton.addActionListener(this::firstButtonActionPerformed);
        paginationPanel.add(firstButton);
        paginationPanel.add(filler1);

        prevButton.setMaximumSize(new java.awt.Dimension(23, 23));
        prevButton.setMinimumSize(new java.awt.Dimension(23, 23));
        prevButton.setPreferredSize(new java.awt.Dimension(23, 23));
        prevButton.addActionListener(this::prevButtonActionPerformed);
        paginationPanel.add(prevButton);
        paginationPanel.add(filler2);

        pageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pageLabel.setText("Página 1 de 100");
        pageLabel.setPreferredSize(new java.awt.Dimension(150, 16));
        paginationPanel.add(pageLabel);
        paginationPanel.add(filler3);

        nextButton.setMaximumSize(new java.awt.Dimension(23, 23));
        nextButton.setMinimumSize(new java.awt.Dimension(23, 23));
        nextButton.setPreferredSize(new java.awt.Dimension(23, 23));
        nextButton.addActionListener(this::nextButtonActionPerformed);
        paginationPanel.add(nextButton);
        paginationPanel.add(filler4);

        lastButton.setMaximumSize(new java.awt.Dimension(48, 23));
        lastButton.setMinimumSize(new java.awt.Dimension(48, 23));
        lastButton.setPreferredSize(new java.awt.Dimension(48, 23));
        lastButton.addActionListener(this::lastButtonActionPerformed);
        paginationPanel.add(lastButton);

        saveButton.setText("Guardar cambios");
        saveButton.addActionListener(this::saveButtonActionPerformed);

        cancelButton.setText("Cancelar");
        cancelButton.addActionListener(this::cancelButtonActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(allergicCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(specialAttentionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(dogBreedLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(dogBreedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(colorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(colorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(31, 31, 31)))
                        .addComponent(dogIconLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(observationsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(ownerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPane1)
                            .addComponent(paginationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(searchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(24, 24, 24))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(104, 104, 104)
                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(133, 133, 133))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(dogIconLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nameLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dogBreedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dogBreedLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(colorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(colorLabel))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(allergicCheckBox)
                            .addComponent(specialAttentionComboBox))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(observationsLabel))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(ownerLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(searchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(paginationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 21, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox allergicCheckBox;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel colorLabel;
    private javax.swing.JTextField colorTextField;
    private javax.swing.JLabel dogBreedLabel;
    private javax.swing.JTextField dogBreedTextField;
    private javax.swing.JLabel dogIconLabel;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler16;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.JButton firstButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton lastButton;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JButton nextButton;
    private javax.swing.JLabel observationsLabel;
    private javax.swing.JTextArea observationsTextArea;
    private javax.swing.JLabel ownerLabel;
    private javax.swing.JList<OwnerListDto> ownerList;
    private javax.swing.JLabel pageLabel;
    private javax.swing.JPanel paginationPanel;
    private javax.swing.JButton prevButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton searchButton;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JCheckBox specialAttentionComboBox;
    // End of variables declaration//GEN-END:variables

}
