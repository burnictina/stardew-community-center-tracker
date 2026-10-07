package com.stardewtracker.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import com.stardewtracker.model.Bundle;
import com.stardewtracker.model.Item;
import com.stardewtracker.model.Room;
import com.stardewtracker.repository.BundleRepository;
import com.stardewtracker.repository.ItemRepository;
import com.stardewtracker.repository.RoomRepository;
import com.stardewtracker.service.RoomService;

public class MainController {

    @FXML 
    private ListView<Room> roomListView;

    @FXML 
    private Label roomTitleLabel;

    @FXML 
    private ListView<Bundle> bundleListView;

    @FXML 
    private Label bundleTitleLabel;

    @FXML 
    private VBox itemContainer;

    private RoomService roomService;

    public MainController() {
        ItemRepository itemRepository = new ItemRepository();

        BundleRepository bundleRepository = new BundleRepository(itemRepository);

        RoomRepository roomRepository = new RoomRepository(bundleRepository);

        roomService = new RoomService(roomRepository);
    }

    @FXML 
    private void initialize() {
        roomListView.getItems().addAll(roomService.getAllRooms());

        roomListView.setCellFactory(listView -> new ListCell<Room>(){
            @Override protected void updateItem(Room room, boolean empty) {
                super.updateItem(room, empty);
                if  (empty || room == null){
                    setText(null);
                } else {
                    setText(room.getName());
                }
            }
        });

        roomListView.getSelectionModel()
        .selectedItemProperty()
        .addListener((observable, oldRoom, newRoom) -> {
            if (newRoom != null) {
                showRoom(newRoom);
            }
        });

        bundleListView.setCellFactory(listView -> new ListCell<Bundle>(){
            @Override
            protected void updateItem(Bundle bundle, boolean empty) {
                super.updateItem(bundle, empty);
                if  (empty || bundle == null){
                    setText(null);
                } else {
                    setText(bundle.getName());
                }
            }
        });

        bundleListView.getSelectionModel()
        .selectedItemProperty()
        .addListener((observable, oldBundle, newBundle) -> {
            if (newBundle != null) {
                showBundle(newBundle);
            }
        });
    }

    private void showRoom (Room room) {
        roomTitleLabel.setText(room.getName());

        bundleListView.getItems().clear();
        bundleListView.getItems().addAll(room.getRequiredBundles());

        bundleTitleLabel.setText("Select a bundle");
        itemContainer.getChildren().clear();
        
    }

    private void showBundle (Bundle bundle) {
        bundleTitleLabel.setText(bundle.getName());

        itemContainer.getChildren().clear();
        
        for (Item item : bundle.getRequiredItems()) {
            Label itemLabel = new Label(item.getName());
            itemContainer.getChildren().add(itemLabel);
        }
    }
}