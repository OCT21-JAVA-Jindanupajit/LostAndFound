package com.jindanupajit.Oct21Java.AdditionalClassExercises.utils.Menu;




import com.jindanupajit.Oct21Java.AdditionalClassExercises.LostAndFoundApplication.controller.Event;

import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Menu {
    private MenuList menuList = null;
    private HashMap<Integer, MenuItem> menuMap = null;

    public Menu() {
        this.menuList = new MenuList();
    }
    public Menu (MenuList menuList) {
        this.menuList = (menuList==null)?new MenuList():menuList;
    }

    public MenuList getMenuList() {
        return this.menuList;
    }
    public HashMap<Integer, MenuItem>  getMenuMap() {
        return this.menuMap;
    }

    public Event waitForInput(String prompt) {
        showMenu();
        int choice = getChoice(prompt);
        MenuItem menuItem = null;

        if (choice < 0) {
            System.out.println("Bad Request");
            return new Event(Event.Menu.BAD_CHOICE, 400);
        }
        else if (choice == 0) {
            return new Event(Event.Menu.BACK);
        }
        else if ((menuItem = menuMap.getOrDefault(choice, null)) == null) {
            System.out.println("Resource Not Found");
            return new Event(Event.Menu.BAD_CHOICE, 404);
        }
        else {
            //System.out.println("Goto " + menuItem.getLabel());
            return menuItem.getEvent();
        }
    }

    public Event waitForInput() {
        return waitForInput("Choose > ");
    }

    protected void showMenu () {
        if (this.menuList == null) return;

        MenuList myMenu = (MenuList) menuList.clone();

        Collections.sort(myMenu);

        menuMap = new HashMap<>();

        int i = 0;
        while(!myMenu.isEmpty()) {
            MenuItem item = myMenu.remove(0);
            menuMap.putIfAbsent(++i,item );
        }
        menuMap.forEach( (k,menuItem) -> System.out.println(String.format("\t%3d. %-50s",k,menuItem.getLabel())) ) ;
        System.out.println("\n\t  0. Go Back");
    }

    protected int getChoice(String prompt) {
        Scanner keyboard = new Scanner(System.in);

        System.out.print(prompt==null?" > ":prompt);

        try {
           return keyboard.nextInt();
        } catch (Exception ex) {

            return -1;
        }


    }

}
