package com.jindanupajit.Oct21Java;


import com.jindanupajit.Oct21Java.AdditionalClassExercises.utils.Menu.*;

public class LostAndFoundApplication {

    public static void  main(String[] args) {

        Menu menu = new Menu();
        MenuItem a = menu.getMenuList().addNext("Test 1");
        MenuItem b = menu.getMenuList().addNext("B Choice");
        MenuItem c = menu.getMenuList().addNext("HAHAH");

        MenuItem choice = menu.wait("Choose > ");

        if (choice.equals(a)) {
            System.out.println("Good A");
        } else if (choice.equals(b)) {
            System.out.println("Good B");
        } else if (choice.equals(c)) {
            System.out.println("Good C");
        } else {
            System.out.println("Bad Choice");
        }
    }
}
