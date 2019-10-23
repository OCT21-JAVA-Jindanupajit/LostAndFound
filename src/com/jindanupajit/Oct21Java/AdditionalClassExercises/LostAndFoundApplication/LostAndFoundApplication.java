package com.jindanupajit.Oct21Java.AdditionalClassExercises.LostAndFoundApplication;


import com.jindanupajit.Oct21Java.AdditionalClassExercises.LostAndFoundApplication.controller.MainController;
import com.jindanupajit.Oct21Java.AdditionalClassExercises.LostAndFoundApplication.entity.Item;
import com.jindanupajit.Oct21Java.AdditionalClassExercises.LostAndFoundApplication.entity.ItemCollection;

public class LostAndFoundApplication {

    public static final ItemCollection AllItems = new ItemCollection();

    public static void  main(String[] args) {
        MainController mainController = new MainController();

        AllItems.add(new Item("White Shirt", Item.Category.CLOTHING, Item.Status.LOST));
        AllItems.add(new Item("Black Shirt", Item.Category.CLOTHING, Item.Status.FOUND));
        AllItems.add(new Item("Blue Shirt", Item.Category.CLOTHING, Item.Status.LOST));

        AllItems.add(new Item("Small Brown Dog", Item.Category.PET, Item.Status.LOST));
        AllItems.add(new Item("Big Black Dog", Item.Category.PET, Item.Status.FOUND));
        AllItems.add(new Item("Medium White Dog", Item.Category.PET, Item.Status.LOST));

        AllItems.add(new Item("Large Box", Item.Category.OTHER, Item.Status.FOUND));
        AllItems.add(new Item("Credit Card", Item.Category.OTHER, Item.Status.LOST));
        AllItems.rescan();

        System.exit(mainController.run());
    }
}
