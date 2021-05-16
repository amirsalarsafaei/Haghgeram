package com.SalarJavaDevGroup.Models;

import com.SalarJavaDevGroup.util.Filter;

import java.util.ArrayList;

//Group is for making list of users
public class Group {
    ArrayList<String> users;
    String name;

    public Group(String name) {
        this.name = name;
        users = new ArrayList<>();
    }
    //adds user to list
    public void addUser(String user) {
        if (!Filter.boolFind(users, user))
            users.add(user);
    }
    //deletes user from list
    public void delUser(String user) {
        Filter.delFind(users, user);
    }

    //checks if a user exists in list
    public Boolean existUser(String user) {
        return Filter.boolFind(users, user);
    }

    //gets list name
    public String getName() {
        return name;
    }
    //gets users in list
    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }


}
