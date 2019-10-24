package com.jindanupajit.Oct21Java.AdditionalClassExercises.LostAndFoundApplication.controller;


import com.jindanupajit.Oct21Java.AdditionalClassExercises.LostAndFoundApplication.LostAndFoundApplication;
import com.jindanupajit.Oct21Java.AdditionalClassExercises.LostAndFoundApplication.entity.Item;
import com.jindanupajit.Oct21Java.AdditionalClassExercises.utils.Menu.Menu;

import java.util.Scanner;

public class MainController {

enum ViewType{
    ALL,
    LOST_AND_FOUND;
}
    public int run() {
        Event event = new Event(Event.Menu.MAIN_MENU);

        while(event.getEvent() != Event.Menu.QUIT) {
            event = MenuEventDispatcher(event);
        }
        return 0;
    }

    protected  Event MenuEventDispatcher (Event event) {

        switch (event.getEvent()) {
            case MAIN_MENU:
                return MainMenu(event.getMetaData());
            case VIEW:
                return View(event.getMetaData());
            case CREATE:
                return CreateItem(event.getMetaData());
            case ITEM_VIEW:
                return ViewItem(event.getMetaData());
            case MARK_FOUND:
            case RESTORE:
                return MarkFound(event.getMetaData());
            case MARK_LOST:
                return MarkLost(event.getMetaData());
            case DELETE_REQUEST:
            case DELETE_CONFIRM:
                return DeleteConfirm(event.getMetaData());
            case QUIT:
                default:
                return new Event(Event.Menu.QUIT);

        }
    }

    protected  Event MainMenu(Object data) {
        System.out.println("Main Menu");
        System.out.println("=========");
        Menu menu = new Menu();
        menu.getMenuList().addNext(new Event(Event.Menu.VIEW,ViewType.LOST_AND_FOUND), "View lost and found database");
        menu.getMenuList().addNext(new Event(Event.Menu.CREATE), "Create new record");
        menu.getMenuList().addNext(new Event(Event.Menu.VIEW,ViewType.ALL), "View all database including deleted items");
        menu.getMenuList().addNext(new Event(Event.Menu.QUIT),"Quit");
        while(true) {
            Event choice = menu.waitForInput();
            switch(choice.getEvent()) {
                case BAD_CHOICE:
                    System.out.println("** Bad choice, try again!");
                    break;
                case BACK:
                    //System.out.println("* Back: Main Menu -> Main Menu");
                    return new Event(Event.Menu.MAIN_MENU);
                default:
                    return choice;
            }

        }
    }

    protected Event View(Object data) {
        class MissingItemCounter {
            public int total = 0;
            public int clothing = 0;
            public int pet = 0;
            public int other = 0;
        };
        final MissingItemCounter missing = new MissingItemCounter();


        ViewType viewType = data==null?ViewType.LOST_AND_FOUND:(ViewType) data;

        System.out.println("Lost and Found");
        System.out.println("==============");
        Menu menu = new Menu();



        LostAndFoundApplication.AllItems.forEach(

                item -> {



                    if ((viewType == ViewType.ALL) || (item.getStatus() != Item.Status.FOUND_DELETED))
                        menu.getMenuList().addNext(new Event(Event.Menu.ITEM_VIEW, item),
                                String.format("%-40s %-10s [ %-6s ]",
                                        item.getName(),
                                        item.getCategory().getLabel(),
                                        item.getStatus().getLabel()
                                ));
                    switch (item.getCategory()) {
                        case CLOTHING:
                            if (item.getStatus() == Item.Status.LOST)
                                ++missing.clothing;
                            break;
                        case PET:
                            if (item.getStatus() == Item.Status.LOST)
                                ++missing.pet;
                            break;
                        case OTHER:
                            if (item.getStatus() == Item.Status.LOST)
                                ++missing.other;
                            break;
                        default:
                            // What is that?
                    }


                }
        );

        missing.total = missing.clothing + missing.pet+ missing.other;

        int missingCategory = 0;
        String message = "";
        String clothing = "";
        String pet = "";
        String other = "";


        if (missing.clothing > 0) {
            clothing = String.format("%d item%s of clothing", missing.clothing, missing.clothing > 1 ? "s" : "");
            ++missingCategory;
        }

        if (missing.pet > 0) {
            pet = String.format("%d pet%s",missing.pet,missing.pet>1?"s":"");
            ++missingCategory;
        }

        if (missing.other > 0) {
            other = String.format("%d other item%s",missing.other,missing.other>1?"s":"");
            ++missingCategory;
        }

        switch (missingCategory) {
            case 0:
                    message = "nothing";
                    break;
            case 1:
                    // Only one message from missing category
                    message = clothing+pet+other;
                    break;
            case 2:
                if (missing.clothing > 0)
                    clothing += " and ";
                else
                    pet += " and ";
                message = clothing+pet+other;
                break;
            case 3:
                    message = clothing+", "+pet+" and "+other;
                    break;


        }

        message = "There "+((missing.total > 1)?"are ":"is ")+message+" missing.";

        System.out.println(message+"\n");

        while(true) {
            Event choice = menu.waitForInput();
            switch(choice.getEvent()) {
                case BAD_CHOICE:
                    System.out.println("** Bad choice, try again!");
                    break;
                case BACK:
                    //System.out.println("* Back: View -> Main Menu");
                    return new Event(Event.Menu.MAIN_MENU);
                default:
                    return choice;
            }

        }
    }

    protected Event CreateItem(Object data) {
        Item item;
        Scanner keyboardScanner = new Scanner(System.in);

            System.out.print("Label > ");
            String label = (keyboardScanner.nextLine()).trim();

            Item.Category category;
            Item.Status status;

            label = label.substring(0, Math.min(label.length(), 40));

            Menu menu = new Menu();
            menu.getMenuList().addNext(new Event(Event.Menu.INPUT,Item.Category.CLOTHING), "Clothing");
            menu.getMenuList().addNext(new Event(Event.Menu.INPUT,Item.Category.PET), "Pet");
            menu.getMenuList().addNext(new Event(Event.Menu.INPUT,Item.Category.OTHER), "Other");
            whileLoop:
            while(true) {
                Event choice = menu.waitForInput("Category > ");

                switch(choice.getEvent()) {
                    case BAD_CHOICE:
                        System.out.println("** Bad choice, try again!");
                        break;
                    case BACK:
                        System.out.println("** Data discarded!");
                        return new Event(Event.Menu.MAIN_MENU);
                    case INPUT:
                        category = (Item.Category) choice.getMetaData();
                        break whileLoop;
                    default:
                        return choice;
                }

            }

            menu = new Menu();
            menu.getMenuList().addNext(new Event(Event.Menu.INPUT,Item.Status.LOST), "Lost");
            menu.getMenuList().addNext(new Event(Event.Menu.INPUT,Item.Status.FOUND), "Found");
            whileLoop:
            while(true) {
                Event choice = menu.waitForInput("Lost or Found? > ");
                switch(choice.getEvent()) {
                    case BAD_CHOICE:
                        System.out.println("** Bad choice, try again!");
                        break;
                    case BACK:
                        System.out.println("** Data discarded!");
                        return new Event(Event.Menu.MAIN_MENU);
                    case INPUT:
                        status = (Item.Status) choice.getMetaData();
                        break whileLoop;
                    default:
                        return choice;
                }

            }

            ItemCardView(item = new Item(label,category,status));

            menu = new Menu();
            menu.getMenuList().addNext(new Event(Event.Menu.INPUT), "Yes, add to database");
            menu.getMenuList().addNext(new Event(Event.Menu.BACK), "No, discard and go back to main menu");
            while(true) {
                Event choice = menu.waitForInput("Add ? > ");
                switch(choice.getEvent()) {
                    case BAD_CHOICE:
                        System.out.println("** Bad choice, try again!");
                        break;
                    case BACK:
                        System.out.println("** Data discarded!");
                        return new Event(Event.Menu.MAIN_MENU);
                    case INPUT:
                        LostAndFoundApplication.AllItems.addAndRescan(item);
                        return new Event(Event.Menu.VIEW,ViewType.LOST_AND_FOUND);
                    default:
                        return choice;
                }

            }
    }

    protected Event ViewItem(Object data) {

        if (data == null)
            return new Event(Event.Menu.VIEW);

        Item item = (Item) data;
        ItemCardView(item);

        System.out.println("\nAction");
        System.out.println("======");
        Menu menu = new Menu();



        if (item.getStatus() == Item.Status.FOUND) {
            menu.getMenuList().addNext(new Event(Event.Menu.MARK_LOST, item), "Mark as LOST");
            menu.getMenuList().addNext(new Event(Event.Menu.DELETE_REQUEST, item), "Delete");
        } else if (item.getStatus() == Item.Status.FOUND_DELETED) {
            menu.getMenuList().addNext(new Event(Event.Menu.MARK_LOST, item), "Restore and mark as LOST");
            menu.getMenuList().addNext(new Event(Event.Menu.MARK_FOUND, item), "Restore and mark as FOUND");
        } else {
            menu.getMenuList().addNext(new Event(Event.Menu.MARK_FOUND, item), "Mark as FOUND");
        }
        while(true) {
            Event choice = menu.waitForInput();
            switch(choice.getEvent()) {
                case BAD_CHOICE:
                    System.out.println("** Bad choice, try again!");
                    break;
                case BACK:
                    return new Event(Event.Menu.VIEW);
                default:
                    return choice;
            }

        }
    }

    protected Event DeleteConfirm(Object data) {
        if (data == null)
            return new Event(Event.Menu.VIEW);

        Item item = (Item) data;

        Menu menu = new Menu();
        System.out.println("Delete ?");
        menu.getMenuList().addNext(new Event(Event.Menu.DELETE_CONFIRM, item), "Yes, delete it!");

        while(true) {
            Event choice = menu.waitForInput();
            switch(choice.getEvent()) {
                case BAD_CHOICE:
                    System.out.println("** Bad choice, try again!");
                    break;
                case BACK:
                    return new Event(Event.Menu.ITEM_VIEW, item);
                case DELETE_CONFIRM:
                    item.setStatus(Item.Status.FOUND_DELETED);
                    return new Event(Event.Menu.ITEM_VIEW, item);
                default:
                    return choice;
            }

        }

    }

    protected Event MarkFound(Object data) {
        if (data == null)
            return new Event(Event.Menu.VIEW);

        Item item = (Item) data;

        item.setStatus(Item.Status.FOUND);
        return new Event(Event.Menu.ITEM_VIEW, item);

    }

    protected Event MarkLost(Object data) {
        if (data == null)
            return new Event(Event.Menu.VIEW);

        Item item = (Item) data;

        item.setStatus(Item.Status.LOST);
        return new Event(Event.Menu.ITEM_VIEW, item);

    }


    private void ItemCardView (Item item) {
        System.out.printf(""+
                "\n\t+------------------------------------------+"+
                "\n\t| Item Number #%3d                 %7s |"+
                "\n\t| %-40s |"+
                "\n\t| Category %-10s Status [ %-6s ]    |"+
                "\n\t+------------------------------------------+\n\n",
                item.getId(),
                item.getStatus()==Item.Status.FOUND_DELETED?"DELETED":"       ",
                item.getName(),
                item.getCategory().getLabel(),
                item.getStatus().getLabel()
        );


    }

}
