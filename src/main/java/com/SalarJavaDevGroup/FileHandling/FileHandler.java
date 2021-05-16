package com.SalarJavaDevGroup.FileHandling;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public abstract class FileHandler {
    private static final Logger logger = LogManager.getLogger(FileHandler.class);

    //loading one of config files location files that we are sure it exists
    public static File loadFile(String propertyKey) {
        try {
            InputStream input = new FileInputStream("./config/Addresses.properties");
            Properties properties = new Properties();
            properties.load(input);
            input.close();
            return new File(properties.getProperty(propertyKey));
        } catch (IOException e) {
            e.printStackTrace();
            logger.fatal("Couldn't Load File\n"
                    + "{'Config' : 'Addresses', 'key' : " + propertyKey + "}");
        }
        return null;
    }

    public static String loadLocation(String propertyKey) {
        try {
            InputStream input = new FileInputStream("./config/Addresses.properties");
            Properties properties = new Properties();
            properties.load(input);
            input.close();
            return properties.getProperty(propertyKey);
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return null;
    }
    //to clear a txt file first we delete it then we will remake it
    public static void ClearFile(File file) {
        if (file.delete()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("Couldn't Make File after delete (clearing) : " + file.getAbsolutePath());
            }
        }
        else {
            logger.error("Couldn't delete File for creating (clearing) : " + file.getAbsolutePath());
        }
    }
    //deleting the whole folder
    public static void delete(File file) {
        if (!file.exists())
            return;
        for (File f: file.listFiles())
            delete(f);
        file.delete();
    }
    //checking if the key exist in parameter file in config address
    public static boolean Exist(String propertyKey, String key) {
        File file = loadFile(propertyKey);
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                if (scan.nextLine().equals(key)) {
                    scan.close();
                    return true;
                }
            }
            scan.close();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            logger.fatal("Config address " + propertyKey + " file doesn't exist!");
        }
        return false;
    }

    //deleting element from a file
    public static void delExist(String propertyKey, String key) {
        File file = loadFile(propertyKey);
        ArrayList<String> ar = new ArrayList<>();
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String tmp = scan.nextLine();
                if (!tmp.equals(key))
                    ar.add(tmp);
            }
            file.delete();
            file.createNewFile();
            PrintStream printStream = new PrintStream(new FileOutputStream(file));
            for (String s : ar) {
                printStream.println(s);
            }
            printStream.close();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            logger.fatal("Config address " + propertyKey + " file doesn't exist!");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Couldn't make new " + propertyKey + " file");
        }
    }
    public static ImageView getImage(String property) {
        try {
            InputStream input = new FileInputStream("./config/ImageLinks.properties");
            Properties properties = new Properties();
            properties.load(input);
            input.close();
            FileInputStream inputImage = new FileInputStream(properties.getProperty(property));
            Image image = new Image(inputImage);
            ImageView imageView = new ImageView(image);
            inputImage.close();
            return imageView;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static void saveImage(File file, int id, String type) {

        try {
            File dest = new File(loadLocation(type) + id);
            Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static ImageView loadImage(int id, String type) {
        FileInputStream inputImage = null;
        try {
            inputImage = new FileInputStream(loadLocation(type) + id);
            Image image = new Image(inputImage);
            ImageView imageView = new ImageView(image);
            inputImage.close();
            return imageView;
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return null;
    }

}
