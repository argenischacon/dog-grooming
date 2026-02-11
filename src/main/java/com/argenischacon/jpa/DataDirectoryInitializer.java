package com.argenischacon.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataDirectoryInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataDirectoryInitializer.class);
    private static final String DATA_FOLDER = ".dog-grooming/data";

    private DataDirectoryInitializer(){}

    public static void ensureDataDirectoryExists(){
        try{
            String home = System.getProperty("user.home");

            if(home == null || home.isBlank()){
                throw new IllegalArgumentException("The user's home directory could not be determined");
            }

            Path dataPath = Paths.get(home, DATA_FOLDER);
            Files.createDirectories(dataPath);

            logger.info("Data directory created in: {}", dataPath.toAbsolutePath());
        }catch (Exception e){
           logger.error("Error creating data directory for H2", e);
           throw new RuntimeException("The database directory could not be initialized", e);
        }

    }
}
