package com.jindanupajit.Oct21Java.AdditionalClassExercises.utils.Menu;




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
    public MenuItem wait(String prompt) {
        showMenu();
        return menuMap.getOrDefault(getChoice(prompt), null);
    }
    protected void showMenu () {
        if (this.menuList == null) return;

        MenuList myMenu = (MenuList) menuList.clone();

        Collections.sort(myMenu);

        menuMap = new HashMap<>();

        int i = 0;
        while(!myMenu.isEmpty())
             menuMap.put(++i,myMenu.remove(0));

        menuMap.forEach( (k,menuItem) -> System.out.println(String.format("\t%3d. %-50s",k,menuItem.getLabel())) ) ;


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
