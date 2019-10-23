package com.jindanupajit.Oct21Java.AdditionalClassExercises.LostAndFoundApplication.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ItemCollection extends ArrayList<Item> {

    private HashMap<Item.Category,Integer> counter = new HashMap<>();

    // Ugly hack!
    public boolean addAndRescan(Item item) {
        boolean ret = super.add(item);
        rescan();
        return ret;
    }




    public void rescan() {
        counter = new HashMap<>();
        this.forEach(item ->increaseCategoryCounter(item.getCategory()));
    }

    public HashMap<Item.Category, Integer> getCounter() {
        return counter;
    }

    public int getCategoryCounter(Item.Category category) {
        if (!counter.containsKey(category))
            return 0;
        return counter.get(category);
    }

    public int setCategoryCounter(Item.Category category,Integer newCounter) {

        counter.putIfAbsent(category,0);
        counter.replace(category,newCounter);
        return newCounter;
    }

    public void increaseCategoryCounter(Item.Category category) {
        counter.putIfAbsent(category,0);
        counter.replace(category,counter.get(category)+1);
    }
    public void decreaseCategoryCounter(Item.Category category) {
        counter.putIfAbsent(category,0);
        counter.replace(category,counter.get(category)-1);
    }


}
