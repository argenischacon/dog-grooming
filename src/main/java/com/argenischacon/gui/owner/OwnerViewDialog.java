package com.argenischacon.gui.owner;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.argenischacon.dto.dog.DogListDto;
import com.argenischacon.dto.owner.OwnerDetailDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.argenischacon.service.OwnerService;
import com.argenischacon.service.impl.OwnerServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;

public class OwnerViewDialog extends javax.swing.JDialog {

    private static final Logger logger = LoggerFactory.getLogger(OwnerViewDialog.class);
    private static final String DEFAULT_ICON = "/icons/default.svg";
    private static final int ICON_SIZE = 80;
    private final OwnerService ownerService;
    private final Long ownerId;
    private final DefaultListModel<DogListDto> dogModel;

    public OwnerViewDialog(java.awt.Frame parent, boolean modal, Long ownerId) {
        super(parent, modal);
        this.ownerService = new OwnerServiceImpl();
        this.ownerId = ownerId;
        this.dogModel = new DefaultListModel<>();
        initComponents();
        loadIcons();
        loadOwnerData();
    }

    private static class DogListCellRenderer extends DefaultListCellRenderer {
        private final Icon dogIcon;

        public DogListCellRenderer() {
            URL url = getClass().getResource("/icons/dog.svg");
            this.dogIcon = (url != null) ? new FlatSVGIcon(url).derive(16, 16) : null;
        }

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            DogListDto dog = (DogListDto) value;
            label.setText(String.join(" - ", dog.getName() + " " + dog.getDogBreed()));
            label.setIcon(dogIcon);
            return label;
        }
    }

    private static class NonSelectableListSelectionModel extends DefaultListSelectionModel{
        @Override
        public void setSelectionInterval(int index0, int index1) {
        }
    }

    private void loadOwnerData() {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        okButton.setText("Cargando...");

        SwingWorker<OwnerDetailDto, Void> worker = new SwingWorker<OwnerDetailDto, Void>() {
            @Override
            protected OwnerDetailDto doInBackground() throws Exception {
                return ownerService.findById(ownerId);
            }

            @Override
            protected void done() {
                try {
                    OwnerDetailDto ownerDto = get();
                    populateForm(ownerDto);
                } catch (InterruptedException | ExecutionException e) {
                    logger.error("Error loading owner data", e);
                    JOptionPane.showMessageDialog(OwnerViewDialog.this, "Error al cargar los datos del dueño", "Error", JOptionPane.ERROR_MESSAGE);
                    dispose();
                } finally {
                    setCursor(Cursor.getDefaultCursor());
                    okButton.setText("Listo");
                }
            }
        };

        worker.execute();
    }

    private void populateForm(OwnerDetailDto ownerDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        dniDataLabel.setText(ownerDto.getDni());
        nameDataLabel.setText(ownerDto.getName());
        lastnameDataLabel.setText(ownerDto.getLastname());
        if (ownerDto.getBirthdate() != null) {
            birthdateDataLabel.setText(ownerDto.getBirthdate().format(formatter));
        }
        phoneDataLabel.setText(ownerDto.getPhone());
        emailDataLabel.setText((ownerDto.getEmail() == null) ? "No tiene" : ownerDto.getEmail());
        dogModel.clear();
        dogModel.addAll(ownerDto.getDogs());
    }

    private void loadIcons() {
        setIconSVG(ownerIconLabel, "/icons/owner/view.svg");
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

        jPanel1 = new javax.swing.JPanel();
        ownerIconLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        dogsDataList = new JList<>(dogModel);
        okButton = new javax.swing.JButton();
        nameDataLabel = new javax.swing.JLabel();
        dniDataLabel = new javax.swing.JLabel();
        phoneDataLabel = new javax.swing.JLabel();
        lastnameDataLabel = new javax.swing.JLabel();
        birthdateDataLabel = new javax.swing.JLabel();
        emailDataLabel = new javax.swing.JLabel();
        dniLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        birthdateLabel = new javax.swing.JLabel();
        lastnameLabel = new javax.swing.JLabel();
        phoneLabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        dogsLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ver Dueño");
        setResizable(false);

        ownerIconLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        dogsDataList.setCellRenderer(new DogListCellRenderer());
        dogsDataList.setSelectionModel(new NonSelectableListSelectionModel());
        jScrollPane1.setViewportView(dogsDataList);

        okButton.setText("Listo");
        okButton.addActionListener(this::okButtonActionPerformed);

        nameDataLabel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        dniDataLabel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        phoneDataLabel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lastnameDataLabel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        birthdateDataLabel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        emailDataLabel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        dniLabel.setText("DNI:");

        nameLabel.setText("Nombre:");

        birthdateLabel.setText("Fecha de nacimiento:");

        lastnameLabel.setText("Apellido:");

        phoneLabel.setText("Telefono:");

        emailLabel.setText("Email:");

        dogsLabel.setText("Perros que posee:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(birthdateLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dniLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lastnameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(phoneLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(emailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dogsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(phoneDataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameDataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(dniDataLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lastnameDataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(birthdateDataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(emailDataLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(ownerIconLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(276, 276, 276)
                .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(dniDataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(nameDataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(dniLabel)
                                .addGap(18, 18, 18)
                                .addComponent(nameLabel)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lastnameLabel)
                            .addComponent(lastnameDataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(birthdateDataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(phoneDataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(emailDataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(birthdateLabel)
                                .addGap(18, 18, 18)
                                .addComponent(phoneLabel)
                                .addGap(24, 24, 24)
                                .addComponent(emailLabel)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dogsLabel)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(ownerIconLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        dispose();
    }//GEN-LAST:event_okButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel birthdateDataLabel;
    private javax.swing.JLabel birthdateLabel;
    private javax.swing.JLabel dniDataLabel;
    private javax.swing.JLabel dniLabel;
    private javax.swing.JList<DogListDto> dogsDataList;
    private javax.swing.JLabel dogsLabel;
    private javax.swing.JLabel emailDataLabel;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lastnameDataLabel;
    private javax.swing.JLabel lastnameLabel;
    private javax.swing.JLabel nameDataLabel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel ownerIconLabel;
    private javax.swing.JLabel phoneDataLabel;
    private javax.swing.JLabel phoneLabel;
    // End of variables declaration//GEN-END:variables
}
