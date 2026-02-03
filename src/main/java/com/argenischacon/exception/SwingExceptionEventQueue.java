package com.argenischacon.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class SwingExceptionEventQueue extends EventQueue {

    private final static Logger logger = LoggerFactory.getLogger(SwingExceptionEventQueue.class);

    @Override
    protected void dispatchEvent(AWTEvent event) {
        try {
            super.dispatchEvent(event);
        } catch (Exception e) {
            logger.error("Error processing Swing event", e);

            JOptionPane.showMessageDialog(
                    null,
                    "Se produjo un error en la interfaz gráfica.",
                    "Error de Interfaz Gráfica",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
