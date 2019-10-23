package com.jindanupajit.Oct21Java.AdditionalClassExercises.utils.Menu;

import java.util.ArrayList;

public class MenuList extends ArrayList<MenuItem> {
    private int priority_auto_increment = 0;

    public int getPriority() {
        return priority_auto_increment;
    }

    public int setPriority(int value) {
        return priority_auto_increment = value;
    }

    public int getNextPriority() {
        return ++priority_auto_increment;
    }

    public MenuItem addNext(String label) {
        MenuItem item = new MenuItem(label,this.getNextPriority());
        this.add(item);
        return item;
    }
}
