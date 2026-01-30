package gui.dog;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import dto.dog.DogFormDto;
import dto.owner.OwnerFormDto;
import dto.owner.OwnerListDto;
import gui.owner.OwnerCreateDialog;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.DogService;
import service.OwnerService;
import service.impl.DogServiceImpl;
import service.impl.OwnerServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.Set;

public class DogCreateDialog extends javax.swing.JDialog {

    private static final Logger logger = LoggerFactory.getLogger(DogCreateDialog.class);
    private static final String DEFAULT_ICON = "/icons/default.svg";
    private static final int ICON_SIZE = 80;
    private final OwnerService ownerService;
    private final DogService dogService;
    private final Validator validator;
    private boolean success = false;
    private final DefaultListModel<OwnerListDto> ownerModel;

    public DogCreateDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.ownerService = new OwnerServiceImpl();
        this.dogService = new DogServiceImpl();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.ownerModel = new DefaultListModel<>();
        initComponents();
        loadIcons();
        populateList();
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

    public boolean isSuccess() {
        return success;
    }

    private void loadIcons() {
        setIconSVG(dogIconLabel, "/icons/dog/create.svg");
    }

    private void setIconSVG(JLabel label, String path) {
        FlatSVGIcon icon = loadSVG(path);
        if (icon == null) {
            icon = loadSVG(DEFAULT_ICON);
        }
        label.setIcon(icon);
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
        jScrollPane2 = new javax.swing.JScrollPane();
        ownerList = new JList<OwnerListDto>(ownerModel);
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Crear Perro");

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

        ownerList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ownerList.setCellRenderer(new OwnerListCellRenderer());
        jScrollPane2.setViewportView(ownerList);

        saveButton.setText("Guardar");
        saveButton.addActionListener(this::saveButtonActionPerformed);

        cancelButton.setText("Cancelar");
        cancelButton.addActionListener(this::cancelButtonActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(95, 95, 95)
                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(174, 174, 174))
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
                            .addComponent(jScrollPane1))))
                .addGap(24, 24, 24))
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
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(ownerLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void populateList() {
        SwingWorker<List<OwnerListDto>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<OwnerListDto> doInBackground() throws Exception {
                return ownerService.list(0, 1000);
            }

            @Override
            protected void done() {
                try {
                    List<OwnerListDto> list = get();
                    ownerModel.clear();
                    ownerModel.addAll(list);
                } catch (Exception e) {
                    logger.error("Error al obtener la lista de dueños", e);
                    JOptionPane.showMessageDialog(
                            DogCreateDialog.this,
                            "Error al obtener la lista de dueños",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
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

        executeDogCreation(dto);
    }//GEN-LAST:event_saveButtonActionPerformed

    private void executeDogCreation(DogFormDto dto) {
        saveButton.setEnabled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                dogService.create(dto);
                return null;
            }

            @Override
            protected void done() {
                try{
                    get();
                    success = true;
                    JOptionPane.showMessageDialog(DogCreateDialog.this, "Perro creado exitosamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }catch (Exception e){
                    logger.error("Error al crear al perro", e);
                    JOptionPane.showMessageDialog(DogCreateDialog.this, "Error al crear el perro", "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    saveButton.setEnabled(true);
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        };
        worker.execute();
    }

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox allergicCheckBox;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel colorLabel;
    private javax.swing.JTextField colorTextField;
    private javax.swing.JLabel dogBreedLabel;
    private javax.swing.JTextField dogBreedTextField;
    private javax.swing.JLabel dogIconLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JLabel observationsLabel;
    private javax.swing.JTextArea observationsTextArea;
    private javax.swing.JLabel ownerLabel;
    private javax.swing.JList<OwnerListDto> ownerList;
    private javax.swing.JButton saveButton;
    private javax.swing.JCheckBox specialAttentionComboBox;
    // End of variables declaration//GEN-END:variables

}
