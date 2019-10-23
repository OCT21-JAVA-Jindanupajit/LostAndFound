package com.jindanupajit.Oct21Java.AdditionalClassExercises.utils.Menu;

import com.jindanupajit.Oct21Java.AdditionalClassExercises.LostAndFoundApplication.controller.Event;

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

    public MenuItem addNext(Event event, String label) {
        MenuItem item = new MenuItem(label,this.getNextPriority(),event);
        this.add(item);
        return item;
    }
}
