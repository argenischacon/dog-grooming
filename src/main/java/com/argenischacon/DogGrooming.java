package com.argenischacon;

import com.argenischacon.splash.SplashScreen;
import com.argenischacon.splash.SplashWorker;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.argenischacon.exception.GlobalExceptionHandler;
import com.argenischacon.exception.SwingExceptionEventQueue;
import com.argenischacon.gui.main.MainFrame;
import com.argenischacon.jpa.JpaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class DogGrooming {

    private static final Logger logger = LoggerFactory.getLogger(DogGrooming.class);

    public static void main(String[] args) throws InterruptedException {
        // Global exception handler for all threads
        Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler());

        // Change accent color
        FlatLaf.setGlobalExtraDefaults(Map.of(
                "@accentColor", "#FF69B4"
        ));

        // Install theme
        FlatLightLaf.setup();

        SwingUtilities.invokeLater(() -> {
            // Specific exception handler for Swing events
            Toolkit.getDefaultToolkit()
                    .getSystemEventQueue()
                    .push(new SwingExceptionEventQueue());

            SplashScreen splashScreen = new SplashScreen("/icons/app-icon.svg");

            SplashWorker worker = SplashWorker.create(splashScreen)
                    .addTask("Inicializando base de datos...", JpaUtil::warmUp)
                    .onError(e -> {
                        logger.error("Error initializing app", e);
                        JOptionPane.showMessageDialog(null, "Error inicializando app: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        System.exit(1); // Close app on critical error)
                    })
                    .onFinished(() -> {
                        // Show main window
                        new MainFrame().setVisible(true);
                    })
                    .build();

            worker.start();
        });
    }
}
