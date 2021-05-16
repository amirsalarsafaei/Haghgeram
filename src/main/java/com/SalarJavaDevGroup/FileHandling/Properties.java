package com.SalarJavaDevGroup.FileHandling;

import com.SalarJavaDevGroup.GraphicAgent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class Properties {
    private static final Logger logger = LogManager.getLogger(Properties.class);
    public static int loadSize(String propertyKey) {
        try {
            InputStream input = new FileInputStream("./config/Sizes.properties");
            java.util.Properties properties = new java.util.Properties();
            properties.load(input);
            input.close();
            return Integer.parseInt(properties.getProperty(propertyKey));
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            logger.fatal("couldn't find Size properties");
        }
        logger.fatal("couldn't find size " + propertyKey);
        return 0;
    }

    public static int loadNumbers(String propertyKey) {
        try {
            InputStream input = new FileInputStream("./config/Numbers.properties");
            java.util.Properties properties = new java.util.Properties();
            properties.load(input);
            input.close();
            return Integer.parseInt(properties.getProperty(propertyKey));
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            logger.fatal("couldn't find Numbers properties");
        }
        logger.error("couldn't find number " + propertyKey);
        return 0;
    }

    public static String loadDialog(String propertyKey) {
        try {
            InputStream input = new FileInputStream("./config/Dialog.properties");
            java.util.Properties properties = new java.util.Properties();
            properties.load(input);
            input.close();
            return properties.getProperty(propertyKey);
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            logger.fatal("couldn't find dialog properties");
        }
        logger.error("couldn't find dialog " + propertyKey);
        return "";
    }
}
