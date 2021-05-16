package com.SalarJavaDevGroup.util;

import com.SalarJavaDevGroup.FileHandling.FileHandler;

import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Scanner;

import static com.SalarJavaDevGroup.util.Validate.*;

public abstract class ProfileFields {

    public static String get_string(boolean required, String parameter, Scanner scan, PrintStream print) {

        print.print(ConsoleColors.CYAN_BRIGHT+ "Please Enter your " + parameter);
        if (required) {
            print.print("(This Field is Required)\n");
        }
        else
            print.print("(Or Press enter to skip)\n");
        String res = scan.nextLine();
        while (required && res.equals("")) {
            print.println(ConsoleColors.RED_BRIGHT + "This Field can't be blank");
            res = scan.nextLine();
        }
        return res;
    }

    public static LocalDate getDate(Scanner scan, PrintStream print) {
        print.println(ConsoleColors.CYAN_BRIGHT +
                "Please Enter Birth date in format yyyy-mm-dd" +
                "(Or leave blank to skip)");
        String date = scan.nextLine();
        if (date.equals(""))
            return null;
        while (!validDate(date)) {
            print.println(ConsoleColors.RED + "The date is invalid");
            print.println(ConsoleColors.CYAN_BRIGHT +
                    "Please Enter Birth date in format yyyy-mm-dd" +
                    "(Or leave blank to skip)");
            date = scan.nextLine();
            if (date.equals(""))
                return null;
        }
        return LocalDate.parse(date);
    }

    public static String getName(Scanner scan, PrintStream print) {
        return get_string(true, "name", scan, print);
    }

    public static String getFamilyName(Scanner scan, PrintStream print) {
        return get_string(true, "surname", scan, print);
    }

    public static String getUsername(Scanner scan, PrintStream print) {
        String UserName = get_string(true, "Username", scan, print);

        while(FileHandler.Exist("users.usernames", UserName)) {
            print.println(ConsoleColors.RED_BRIGHT + "Username exists please enter another one");
            UserName = get_string(true, "Username", scan, print);
        }
        return UserName;
    }

    public static String getPassword(Scanner scan, PrintStream print) {
        String Password = get_string(true, "Password", scan, print);
        while(!Password.equals(get_string(true, "Password again", scan, print))) {
            print.println(ConsoleColors.RED_BRIGHT + "Passwords don't match try again");
            Password = get_string(true, "Password", scan, print);
        }
        return Password;
    }

    public static String getEmail(Scanner scan, PrintStream print) {
        String Email = null;
        Email = get_string(false, "Email", scan, print);
        while (!Email.equals("") && !validEmail(Email)) {
            print.println(ConsoleColors.RED_BRIGHT + "The email format is wrong! The required format:\n" +
                    "example@sample.sth");
            Email = get_string(false, "Email", scan, print);
        }
        return Email;
    }

    public static String getPhone(Scanner scan, PrintStream print) {
        String PhoneNumber = null;
        PhoneNumber = get_string(false, "Phone", scan, print);
        while (!PhoneNumber.equals("") && !validPhone(PhoneNumber)) {
            print.println(ConsoleColors.RED_BRIGHT + "The phone format is wrong!\n" +
                    " The phone number must start with 0 and should be 11 digit long");
            PhoneNumber = get_string(false, "Phone", scan, print);
        }
        while (FileHandler.Exist("users.phones", PhoneNumber)) {
            print.println(ConsoleColors.RED_BRIGHT + "This phone already exists");
            PhoneNumber = get_string(false, "Phone", scan, print);
            while (!PhoneNumber.equals("") && !validPhone(PhoneNumber)) {
                print.println(ConsoleColors.RED_BRIGHT + "The phone format is wrong!\n" +
                        " The phone number must start with 0 and should be 11 digit long");
                PhoneNumber = get_string(false, "Phone", scan, print);
            }
        }
        return PhoneNumber;
    }

    public static String getBio(Scanner scan, PrintStream print) {
        return get_string(false, "Biography", scan, print);
    }
}
