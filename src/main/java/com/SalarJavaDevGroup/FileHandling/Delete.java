package com.SalarJavaDevGroup.FileHandling;

import com.SalarJavaDevGroup.Models.User;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public abstract class Delete {
    private static final Logger logger = LogManager.getLogger(Delete.class);
    public static void delUser(User user) {
        File dir = FileHandler.loadFile("users.directory");
        if (dir != null) {
            File userFile = new File(dir.getPath() + "/" + user.getUsername() + "/user.data");
            if (!userFile.exists())
                return;
            FileHandler.delExist("users.usernames", user.getUsername());
            FileHandler.delExist("users.phones", user.getPhoneNumber());
            FileHandler.delExist("users.emails", user.getEmail());
            deleteAll(userFile.getParentFile());
            Load.openUsers.remove(user);
        }
        else {
            logger.fatal("users Dir doesnt exist!");
        }
    }

    public static void deleteAll(File file) {
        if (file.isDirectory())
            for (File f: file.listFiles())
                deleteAll(f);
        file.delete();
        logger.info("file " + file.getName() + " deleted");
    }
}
