package com.SalarJavaDevGroup.Models;

import com.SalarJavaDevGroup.util.ConsoleColors;
import com.SalarJavaDevGroup.FileHandling.Save;

import java.io.PrintStream;

//comment is a class of tweet with back
public class Comment extends Tweet{
    int id_back;
    public Comment(String from, String Content, int Back) {
        super(from, Content, false);
        this.id_back = Back;
        Save.save(this, "comments", id);
    }


    //saves itself
    @Override
    public void save() {
        Save.save(this, "comments", id);
    }
}
