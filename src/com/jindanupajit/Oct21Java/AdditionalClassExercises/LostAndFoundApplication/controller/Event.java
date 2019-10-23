package com.jindanupajit.Oct21Java.AdditionalClassExercises.LostAndFoundApplication.controller;

import java.util.Stack;

public class Event {


    public enum Menu {
        BACK,
        BAD_CHOICE,
        MAIN_MENU,
        VIEW,
        ITEM_VIEW,
        DELETE_REQUEST,
        DELETE_CONFIRM,
        MARK_LOST,
        MARK_FOUND,
        RESTORE,
        QUIT
    }

    private Event.Menu event;
    private Object metaData;

    private static final Stack<Event> EventQueue = new Stack<>();
    public Event(Menu event) {
        this(event, null);
    }
    public Event(Menu event, Object metaData) {
        this.event = event;
        this.metaData = metaData;
    }
    public Menu getEvent() {
        return event;
    }

    public Object getMetaData() {
        return metaData;
    }

    public static Stack<Event> getEventQueue() {
        return EventQueue;
    }




}
