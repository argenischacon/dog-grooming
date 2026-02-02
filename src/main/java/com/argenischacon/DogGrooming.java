package com.argenischacon;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.argenischacon.exception.GlobalExceptionHandler;
import com.argenischacon.exception.SwingExceptionEventQueue;
import com.argenischacon.gui.main.MainFrame;
import com.argenischacon.jpa.JpaUtil;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class DogGrooming {
    public static void main(String[] args) throws InterruptedException {
        // Change accent color
        FlatLaf.setGlobalExtraDefaults(Map.of(
                "@accentColor", "#FF69B4" // Rosado
        ));

        // Install theme
        FlatLightLaf.setup();

        // Warm up
        try {
            JpaUtil.warmUp();
        } catch (ExceptionInInitializerError e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error inicializando la base de datos\n",
                    "Error crítico",
                    JOptionPane.ERROR_MESSAGE
            );
            System.exit(1);
        }

        // Global exception handler for all threads
        Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler());

        // Specific exception handler for Swing events
        Toolkit.getDefaultToolkit()
                .getSystemEventQueue()
                .push(new SwingExceptionEventQueue());

        // Show main window
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
