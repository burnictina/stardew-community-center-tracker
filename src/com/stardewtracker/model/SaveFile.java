package com.stardewtracker.model;

import java.util.*;

/**
 * 
 * SaveFile
 * 
 * Save file koji pamti jedan save za bundle
 */

public class SaveFile {
    private String name;
    private ArrayList<Room> rooms;
   
    public SaveFile(String name, ArrayList<Room> rooms) {
        this.name = name;
        this.rooms = rooms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addRooms(Room newRoom) {
        rooms.add(newRoom);
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    
}
