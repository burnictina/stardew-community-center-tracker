package com.stardewtracker.service;

import com.stardewtracker.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

import com.stardewtracker.model.Room;

public class RoomService {
    private RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms(){
        return roomRepository.getAll();
    }

    public Optional<Room> findRoomById(int id){
        return roomRepository.findById(id);
    }

    public Optional<Room> findRoomByName(String name){
        return roomRepository.findByName(name);
    }

}
