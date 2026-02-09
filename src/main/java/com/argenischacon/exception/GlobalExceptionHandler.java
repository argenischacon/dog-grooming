package com.argenischacon.exception;

import com.argenischacon.service.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (e instanceof BusinessException) {
            logger.warn("Uncaught business exception in thread {}: {}", t.getName(), e.getMessage());
            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage(),
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );
        } else {
            logger.error("Uncaught exception in thread {}: {}", t.getName(), e.getMessage(), e);
            JOptionPane.showMessageDialog(
                    null,
                    "Ha ocurrido un error crítico inesperado.\n" +
                            "La aplicación puede no funcionar correctamente.\n" +
                            "Detalle: " + e.getMessage(),
                    "Error Crítico",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
