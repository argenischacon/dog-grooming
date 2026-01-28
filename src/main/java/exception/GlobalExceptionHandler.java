package exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        logger.error("Uncaught exception in thread {}: {}", t.getName(), e.getMessage(), e);

        JOptionPane.showMessageDialog(
                null,
                "Ha ocurrido un error inesperado\n"
                        + "La aplicación puede no funcionar correctamente.",
                "Error crítico",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
