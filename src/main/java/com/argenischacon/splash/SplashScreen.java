package com.argenischacon.splash;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class SplashScreen extends JWindow {
    private static final Logger logger = LoggerFactory.getLogger(SplashScreen.class);
    private JProgressBar progressBar;
    private JLabel statusLabel;
    private JLabel logoLabel;
    private static final String DEFAULT_ICON = "/icons/default.svg";
    private static final int DEFAULT_ICON_SIZE = 128;

    public SplashScreen(String logoPath) {
        initComponents();
        setIconSVG(logoLabel, logoPath);
    }

    public SplashScreen() {
        this(DEFAULT_ICON);
    }

    private void initComponents() {
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        contentPanel.setBackground(new Color(255, 255, 255));

        logoLabel = new JLabel();
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        statusLabel = new JLabel("Loading...");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setValue(0);

        contentPanel.add(logoLabel, BorderLayout.CENTER);
        contentPanel.add(statusLabel, BorderLayout.NORTH);
        contentPanel.add(progressBar, BorderLayout.SOUTH);

        setContentPane(contentPanel);
        setSize(new Dimension(500, 300));
        setLocationRelativeTo(null);
    }

    private void setIconSVG(JLabel logoLabel, String path) {
        FlatSVGIcon icon = loadSVG(path);
        if (icon == null && !DEFAULT_ICON.equals(path)) {
            icon = loadSVG(DEFAULT_ICON);
        }
        logoLabel.setIcon(icon);
    }

    private FlatSVGIcon loadSVG(String path) {
        try {
            URL resource = getClass().getResource(path);
            if (resource == null) {
                throw new IllegalArgumentException("Resource not found: : " + path);
            }
            logger.debug("Loading SVG icon: {}", path);
            return new FlatSVGIcon(resource).derive(DEFAULT_ICON_SIZE, DEFAULT_ICON_SIZE);
        } catch (Exception e) {
            logger.warn("Error loading SVG icon: {}", path, e);
            return null;
        }
    }

    public void setStatusText(String text) {
        statusLabel.setText(text);
    }

    public void setProgress(int value) {
        progressBar.setValue(value);
    }

    public int getProgress(){
        return progressBar.getValue();
    }
}
