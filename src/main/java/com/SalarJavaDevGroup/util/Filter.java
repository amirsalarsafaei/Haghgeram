package com.SalarJavaDevGroup.util;

import com.SalarJavaDevGroup.Models.Group;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public abstract class Filter {
    private static final Logger logger = LogManager.getLogger(Filter.class);
    public static Boolean boolFind(ArrayList<Integer> vec, int key) {
        for (int i : vec)
            if (i == key)
                return true;
        return false;
    }

    public static Boolean boolFind(ArrayList<String> vec, String key) {
        for (String i : vec)
            if (i.equals(key))
                return true;
        return false;
    }

    public static int Find(ArrayList<Integer> vec, int key) {
        for (int i = 0; i < vec.size(); i++)
            if (key == vec.get(i))
                return i;
        return -1;
    }

    public static int Find(ArrayList<String> vec, String key) {
        for (int i = 0; i < vec.size(); i++)
            if (key.equals(vec.get(i)))
                return i;
        return -1;
    }

    public static int FindGroup(ArrayList<Group> vec, String key) {
        for (int i = 0; i < vec.size(); i++)
            if (key.equals(vec.get(i).getName()))
                return i;
        return -1;
    }
    public static void delFind(ArrayList<String> vec, String key) {
        int index = Find(vec, key);
        if (index == -1) {
            logger.warn("In method delfind a key was not found to delete");
            return;
        }
        vec.remove(index);
    }
    public static void delFind(ArrayList<Integer> vec, int key) {
        int index = Find(vec, key);
        if (index == -1) {
            logger.warn("In method delfind a key was not found to delete");
            return;
        }
        vec.remove(index);
    }

    public static void delFindGroup(ArrayList<Group> vec, String key) {
        int index = FindGroup(vec, key);
        if (index == -1) {
            logger.warn("In method delfind a key was not found to delete");
            return;
        }
        vec.remove(index);
    }

    public static Boolean boolFindGroup(ArrayList<Group> vec, String key) {
        int index = FindGroup(vec, key);
        return index != -1;
    }
}
