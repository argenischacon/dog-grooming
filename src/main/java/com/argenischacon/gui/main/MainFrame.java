package com.argenischacon.gui.main;

import com.argenischacon.gui.dog.DogPanel;
import com.argenischacon.gui.owner.OwnerPanel;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.argenischacon.jpa.JpaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class MainFrame extends javax.swing.JFrame {

    private static final Logger logger = LoggerFactory.getLogger(MainFrame.class);
    private final CardLayout cardLayout;
    private static final int ICON_SIZE = 32;
    private static final String DEFAULT_ICON = "/icons/default.svg";

    public MainFrame() {
        initComponents();

        setTitle("Peluquería Canina");
        setResizable(false);
        setLocationRelativeTo(null);

        loadAppIcons();
        loadIcons();
        this.cardLayout = (CardLayout) contentPanel.getLayout();

        //Add panels
        contentPanel.add(new DogPanel(), "DOG");
        contentPanel.add(new OwnerPanel(), "OWNER");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        mainPanel = new javax.swing.JPanel();
        contentPanel = new javax.swing.JPanel();
        menuPanel = new javax.swing.JPanel();
        ownersButton = new javax.swing.JToggleButton();
        dogsButton = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        contentPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        contentPanel.setLayout(new java.awt.CardLayout());

        menuPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        buttonGroup.add(ownersButton);
        ownersButton.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        ownersButton.setText("Dueños");
        ownersButton.putClientProperty("JButton.buttonType", "roundRect");
        ownersButton.addActionListener(this::ownersButtonActionPerformed);

        buttonGroup.add(dogsButton);
        dogsButton.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        dogsButton.setSelected(true);
        dogsButton.setText("Perros");
        dogsButton.putClientProperty("JButton.buttonType", "roundRect");
        dogsButton.addActionListener(this::dogsButtonActionPerformed);

        javax.swing.GroupLayout menuPanelLayout = new javax.swing.GroupLayout(menuPanel);
        menuPanel.setLayout(menuPanelLayout);
        menuPanelLayout.setHorizontalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuPanelLayout.createSequentialGroup()
                .addContainerGap(225, Short.MAX_VALUE)
                .addComponent(dogsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(ownersButton, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(263, 263, 263))
        );
        menuPanelLayout.setVerticalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuPanelLayout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dogsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ownersButton, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addComponent(menuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dogsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dogsButtonActionPerformed
        cardLayout.show(contentPanel, "DOG");
    }//GEN-LAST:event_dogsButtonActionPerformed

    private void ownersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ownersButtonActionPerformed
        cardLayout.show(contentPanel, "OWNER");
    }//GEN-LAST:event_ownersButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        JpaUtil.shutdown();
    }//GEN-LAST:event_formWindowClosing

    private void loadAppIcons() {
        try {
            URL url = getClass().getResource("/icons/app-icon.svg");
            if (url == null) {
                logger.warn("Icono SVG no encontrado en el classpath: {}", "/icons/app-icon.svg");
                return;
            }

            FlatSVGIcon baseSvg = new FlatSVGIcon(url);

            int[] sizes = {16, 32, 64, 128, 256};
            List<Image> icons = new ArrayList<>();

            for (int s : sizes) {
                Image img = baseSvg.derive(s, s).getImage();
                if (img != null) {
                    icons.add(img);
                } else {
                    logger.warn("No se pudo generar imagen de tamaño {}x{} desde SVG", s, s);
                }
            }

            if (!icons.isEmpty()) {
                setIconImages(icons);
                logger.info("Window icons loaded successfully ({} sizes)", icons.size());
            } else {
                logger.warn("No valid icon generated from SVG");
            }

        } catch (Exception e) {
            logger.error("Error processing SVG for window icons", e);
        }
    }

    private void loadIcons() {
        setIconSVG(dogsButton, "/icons/dog.svg");
        setIconSVG(ownersButton, "/icons/owner.svg");
    }

    private void setIconSVG(AbstractButton button, String path) {
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
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JToggleButton dogsButton;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JToggleButton ownersButton;
    // End of variables declaration//GEN-END:variables
}
