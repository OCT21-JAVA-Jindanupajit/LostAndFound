package com.jindanupajit.Oct21Java.AdditionalClassExercises.utils.Menu;

public class MenuItem implements Comparable<MenuItem> {

    private String label;
    private long priority;

    public MenuItem(String label, long priority) {
        this.label = label;
        this.priority = priority;
    }

    public MenuItem() {
        this("",0);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long getPriority() {
        return priority;
    }

    public void setPriority(long priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(MenuItem o) {
        if  (this.priority == o.getPriority()) {
           return this.label.compareToIgnoreCase(o.getLabel());
        }
        return (this.priority > o.getPriority()) ? 1 : -1;
    }
}
