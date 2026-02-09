package com.argenischacon.exception;

import com.argenischacon.service.exception.BusinessException;
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
            handleException(e);
        }
    }

    private void handleException(Throwable e) {
        if (e instanceof BusinessException) {
            logger.warn("Business exception: {}", e.getMessage());
            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage(),
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );
        } else {
            logger.error("Unexpected error in Swing event dispatch thread", e);
            JOptionPane.showMessageDialog(
                    null,
                    "Ha ocurrido un error inesperado.\n" +
                            "Por favor, contacte al administrador si el problema persiste.\n" +
                            "Detalle: " + e.getMessage(),
                    "Error del Sistema",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
