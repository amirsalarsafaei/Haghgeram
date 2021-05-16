package com.SalarJavaDevGroup.LogicalAgent;

import com.SalarJavaDevGroup.FileHandling.FileHandler;
import com.SalarJavaDevGroup.FileHandling.Load;
import com.SalarJavaDevGroup.Models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AuthAgent {
    private static final Logger logger = LogManager.getLogger(AuthAgent.class);
    public String login(String username, String Password) {
        User user = Load.LoadUser(username);
        if (user == null || !user.getPassword().equals(Password)) {
            logger.info(username + " couldn't login");
            return "Username and password don't Match";

        }

        logger.info(username + " logged in");
        return "success";
    }

    public void register(String name, String surname, String username, String password, String email, String phone,
                         LocalDate birthdate, String bio) {
        new User(username, password, name, surname, birthdate, email, phone, bio, LocalDateTime.now(), false, true);
        logger.info(username + " registered");
    }

    public boolean PhoneExists(String phone) {
        return FileHandler.Exist("users.phones", phone);
    }

    public boolean UserExists(String phone) {
        return FileHandler.Exist("users.usernames", phone);
    }

}
