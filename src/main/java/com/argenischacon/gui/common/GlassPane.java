package com.argenischacon.gui.common;

import raven.swing.spinner.SpinnerProgress;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GlassPane extends JPanel {

    public GlassPane() {
        setLayout(new GridBagLayout());
        setOpaque(false);
        setVisible(false);

        // Block ALL interaction (mouse + keyboard)
        setFocusable(true);
        addMouseListener(new MouseAdapter() {});
        addMouseMotionListener(new MouseMotionAdapter() {});
        addKeyListener(new KeyAdapter() {});
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                requestFocusInWindow(); // keeps focused if they try to escape
            }
        });

        initLoadingSpinner();
    }

    private void initLoadingSpinner() {
        // Loading spinner
        SpinnerProgress spinner = new SpinnerProgress();
        spinner.setIndeterminate(true);
        spinner.setOpaque(false);
        spinner.setPreferredSize(new Dimension(50, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;

        add(spinner, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(0, 0, 0, 100)); //40% opacidad
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
    }
}
