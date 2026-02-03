package com.argenischacon.gui.dog;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.argenischacon.dto.dog.DogDetailDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.argenischacon.service.DogService;
import com.argenischacon.service.impl.DogServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class DogViewDialog extends javax.swing.JDialog {

    private static final Logger logger = LoggerFactory.getLogger(DogViewDialog.class);
    private static final String DEFAULT_ICON = "/icons/default.svg";
    private static final int DEFAULT_ICON_SIZE = 80;
    private static final int LABEL_ICON_SIZE = 16;
    private final DogService dogService;
    private final Long idDog;

    public DogViewDialog(java.awt.Frame parent, boolean modal, Long idDog) {
        super(parent, modal);
        this.dogService = new DogServiceImpl();
        this.idDog = idDog;
        initComponents();
        loadIcons();
        loadDogData();
    }

    private static void makeReadOnly(JCheckBox checkBox){
        checkBox.setModel(new DefaultButtonModel(){
            @Override
            public void setPressed(boolean b) {}
            @Override
            public void setArmed(boolean b) {}
        });
    }

    private void loadDogData() {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        okButton.setText("Cargando...");

        SwingWorker<DogDetailDto, Void> worker = new SwingWorker<DogDetailDto, Void>() {
            @Override
            protected DogDetailDto doInBackground() throws Exception {
                return dogService.findById(idDog);
            }

            @Override
            protected void done() {
                try {
                    DogDetailDto dto = get();
                    populateForm(dto);
                } catch (InterruptedException | ExecutionException e) {
                    logger.error("Error loading dog data", e);
                    JOptionPane.showMessageDialog(DogViewDialog.this, "Error al cargar los datos del perro", "Error", JOptionPane.ERROR_MESSAGE);
                    dispose();
                } finally {
                    setCursor(Cursor.getDefaultCursor());
                    okButton.setText("Listo");
                }
            }
        };

        worker.execute();
    }

    private void populateForm(DogDetailDto dto) {
        nameDataLabel.setText(dto.getName());
        dogBreedDataLabel.setText(dto.getDogBreed());
        colorDataLabel.setText(dto.getColor());
        specialAttentionCheckBox.setSelected(dto.isSpecialAttention());
        allergicCheckBox.setSelected(dto.isAllergic());
        observationsDataTextArea.setText(dto.getObservations());
        ownerDataLabel.setText(dto.getOwner().getDni() + " - " + dto.getOwner().getName() + " " + dto.getOwner().getLastname());
    }

    private void loadIcons() {
        setIconSVG(dogIconLabel, "/icons/dog/create.svg", DEFAULT_ICON_SIZE);
        setIconSVG(ownerDataLabel, "/icons/owner.svg", LABEL_ICON_SIZE);
    }

    private void setIconSVG(JLabel label, String path, int iconSize) {
        FlatSVGIcon icon = loadSVG(path, iconSize);
        if (icon == null) {
            icon = loadSVG(DEFAULT_ICON, iconSize);
        }
        label.setIcon(icon);
    }

    private FlatSVGIcon loadSVG(String path, int iconSize) {
        try {
            URL resource = getClass().getResource(path);
            if (resource == null) {
                throw new IllegalArgumentException("No se encontró el recurso: " + path);
            }
            logger.debug("Loading SVG icon: {}", path);
            return new FlatSVGIcon(resource).derive(iconSize, iconSize);
        } catch (Exception e) {
            logger.warn("Error loading SVG icon: {}", path, e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        dogIconLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        nameDataLabel = new javax.swing.JLabel();
        dogBreedLabel = new javax.swing.JLabel();
        dogBreedDataLabel = new javax.swing.JLabel();
        colorLabel = new javax.swing.JLabel();
        colorDataLabel = new javax.swing.JLabel();
        specialAttentionCheckBox = new javax.swing.JCheckBox();
        allergicCheckBox = new javax.swing.JCheckBox();
        observationsLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        observationsDataTextArea = new javax.swing.JTextArea();
        ownerLabel = new javax.swing.JLabel();
        ownerDataLabel = new javax.swing.JLabel();
        okButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ver Perro");
        setResizable(false);

        dogIconLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        nameLabel.setText("Nombre:");

        nameDataLabel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        dogBreedLabel.setText("Raza:");

        dogBreedDataLabel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        colorLabel.setText("Color:");

        colorDataLabel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        specialAttentionCheckBox.setText("Requiere atención especial");
        specialAttentionCheckBox.setFocusable(false);
        specialAttentionCheckBox.setRolloverEnabled(false);
        makeReadOnly(specialAttentionCheckBox);

        allergicCheckBox.setText("Tiene alergias");
        allergicCheckBox.setFocusable(false);
        allergicCheckBox.setRolloverEnabled(false);
        makeReadOnly(allergicCheckBox);

        observationsLabel.setText("Observaciones:");

        observationsDataTextArea.setEditable(false);
        observationsDataTextArea.setColumns(20);
        observationsDataTextArea.setRows(5);
        observationsDataTextArea.setFocusable(false);
        observationsDataTextArea.setOpaque(false);
        jScrollPane1.setViewportView(observationsDataTextArea);

        ownerLabel.setText("Dueño:");

        ownerDataLabel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        okButton.setText("Listo");
        okButton.addActionListener(this::okButtonActionPerformed);

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
                                .addComponent(specialAttentionCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(dogBreedLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(colorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(23, 23, 23)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(colorDataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nameDataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dogBreedDataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(24, 24, 24)))
                        .addComponent(dogIconLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(observationsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(ownerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addComponent(ownerDataLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(24, 24, 24))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(265, 265, 265)
                .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(dogIconLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameLabel)
                            .addComponent(nameDataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dogBreedLabel)
                            .addComponent(dogBreedDataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(colorLabel)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(allergicCheckBox)
                                    .addComponent(specialAttentionCheckBox)))
                            .addComponent(colorDataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(observationsLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ownerLabel)
                    .addComponent(ownerDataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        dispose();
    }//GEN-LAST:event_okButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox allergicCheckBox;
    private javax.swing.JLabel colorDataLabel;
    private javax.swing.JLabel colorLabel;
    private javax.swing.JLabel dogBreedDataLabel;
    private javax.swing.JLabel dogBreedLabel;
    private javax.swing.JLabel dogIconLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel nameDataLabel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextArea observationsDataTextArea;
    private javax.swing.JLabel observationsLabel;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel ownerDataLabel;
    private javax.swing.JLabel ownerLabel;
    private javax.swing.JCheckBox specialAttentionCheckBox;
    // End of variables declaration//GEN-END:variables

}