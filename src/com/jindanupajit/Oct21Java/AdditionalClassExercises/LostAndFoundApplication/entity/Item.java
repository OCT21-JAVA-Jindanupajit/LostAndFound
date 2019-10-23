package com.jindanupajit.Oct21Java.AdditionalClassExercises.LostAndFoundApplication.entity;

public class Item {

    private static long autoinc = 0;
    private long id;
    String name;
    Item.Category category;
    Item.Status status;

    public long getId() {
        return id;
    }

    public enum Category {
        CLOTHING ("Clothing"),
        PET ("Pet"),
        OTHER ("Other");

        private String label;

        Category(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public enum Status {
        LOST ("LOST"),
        FOUND ("FOUND"),
        FOUND_DELETED ("FOUND*");

        private String label;

        Status(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public Item() {
        this("",Category.OTHER,Status.FOUND);
    }

    public Item(String name, Category category, Status status) {
        this.id = ++autoinc;
        this.name = name;
        this.category = category;
        this.status = status;
    }
    public static long getAutoinc() {
        return ++autoinc;
    }

    public static void setAutoinc(long autoinc) {
        Item.autoinc = autoinc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }




}
