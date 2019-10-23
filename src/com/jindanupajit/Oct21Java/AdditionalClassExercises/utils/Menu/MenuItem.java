package com.jindanupajit.Oct21Java.AdditionalClassExercises.utils.Menu;

import com.jindanupajit.Oct21Java.AdditionalClassExercises.LostAndFoundApplication.controller.Event;

public class MenuItem implements Comparable<MenuItem> {

    private String label;
    private long priority;
    private Event event;

    public MenuItem(String label, long priority,Event event) {
        this.label = label;
        this.priority = priority;
        this.event = event;
    }

    public MenuItem() {
        this("",0, new Event(Event.Menu.QUIT,null));
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

    public Event getEvent() {
        return event;
    }
}
