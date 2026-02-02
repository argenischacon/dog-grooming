package com.argenischacon.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class JpaUtil {
    private static final Logger logger = LoggerFactory.getLogger(JpaUtil.class);
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPU");

    private JpaUtil() {
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void warmUp() {
        logger.info("Warming up JPA EntityManagerFactory...");
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try (EntityManager em = emf.createEntityManager()) {
                    em.createNativeQuery("SELECT 1").getSingleResult();
                }
                return null;
            }

            @Override
            protected void done() {
                try {
                    get(); // Check for exceptions from doInBackground()
                    logger.info("JPA EntityManagerFactory warmed up successfully.");
                } catch (Exception e) {
                    logger.error("Error during JPA warm-up.", e);
                }
            }
        };
        worker.execute();
    }

    public static void shutdown() {
        if (emf.isOpen()) {
            emf.close();
            logger.info("JPA EntityManagerFactory closed.");
        }
    }
}
