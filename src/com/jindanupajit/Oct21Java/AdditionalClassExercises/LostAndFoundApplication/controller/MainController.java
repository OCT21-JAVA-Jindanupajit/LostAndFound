package com.jindanupajit.Oct21Java.AdditionalClassExercises.LostAndFoundApplication.controller;


import com.jindanupajit.Oct21Java.AdditionalClassExercises.LostAndFoundApplication.LostAndFoundApplication;
import com.jindanupajit.Oct21Java.AdditionalClassExercises.LostAndFoundApplication.entity.Item;
import com.jindanupajit.Oct21Java.AdditionalClassExercises.utils.Menu.Menu;

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

        ViewType viewType = data==null?ViewType.LOST_AND_FOUND:(ViewType) data;

        System.out.println("Lost and Found");
        System.out.println("==============");
        Menu menu = new Menu();
        //todo: cleanup
        final int[] missingClothing = {0};
        final int[] missingPet = {0};
        final int[] missingOther = {0};

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
                                ++missingClothing[0];
                            break;
                        case PET:
                            if (item.getStatus() == Item.Status.LOST)
                                ++missingPet[0];
                            break;
                        case OTHER:
                            if (item.getStatus() == Item.Status.LOST)
                                ++missingOther[0];
                            break;
                        default:
                            // What is that?
                    }


                }
        );

        int missing = missingClothing[0] + missingPet[0] + missingOther[0];

        if (missing == 0) {
            System.out.println("There is nothing missing.");
        }
        else {
            System.out.print("There");
        }

        if (missingClothing[0] > 1) {
            System.out.printf(" are %d items of clothing",missingClothing[0]);
        } else if (missingClothing[0] == 1) {
            System.out.print(" are 1 item of clothing");
        }

        if (missingPet[0] > 1) {
            System.out.printf(" are %d items of clothing",missingClothing[0]);
        } else if (missingClothing[0] == 1) {
            System.out.print(" are 1 item of clothing");
        }

        System.out.println(" missing");






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
                "\n\t+------------------------------------------+",
                item.getId(),
                item.getStatus()==Item.Status.FOUND_DELETED?"DELETED":"       ",
                item.getName(),
                item.getCategory().getLabel(),
                item.getStatus().getLabel()
        );


    }

}
