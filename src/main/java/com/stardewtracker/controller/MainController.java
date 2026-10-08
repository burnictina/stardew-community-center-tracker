package com.stardewtracker.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import com.stardewtracker.model.Bundle;
import com.stardewtracker.model.Item;
import com.stardewtracker.model.Room;
import com.stardewtracker.model.SaveFile;
import com.stardewtracker.repository.BundleRepository;
import com.stardewtracker.repository.ItemRepository;
import com.stardewtracker.repository.RoomRepository;
import com.stardewtracker.repository.SaveFileRepository;
import com.stardewtracker.service.RoomService;
import com.stardewtracker.service.SaveService;

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

    @FXML 
    private ComboBox<SaveFile> saveComboBox;

    private RoomService roomService;

    private SaveService saveService;

    private SaveFile currentSave;

    public MainController() {
        ItemRepository itemRepository = new ItemRepository();

        BundleRepository bundleRepository = new BundleRepository(itemRepository);

        RoomRepository roomRepository = new RoomRepository(bundleRepository);

        roomService = new RoomService(roomRepository);

        SaveFileRepository saveFileRepository = new SaveFileRepository(itemRepository);

        saveService = new SaveService(saveFileRepository, itemRepository);
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

        saveComboBox.getItems().addAll(saveService.getAllSaves());

        saveComboBox.setConverter(new StringConverter<SaveFile>() {
            @Override 
            public String toString(SaveFile saveFile) {
                if (saveFile == null ) {
                    return "";                    
                }

                return saveFile.getName();
            }

            @Override 
            public SaveFile fromString(String string) {
                return null;
            }
        });

        saveComboBox.getSelectionModel()
        .selectedItemProperty()
        .addListener((observable, oldSave, newSave) ->{
            if (newSave != null) {
                currentSave = newSave;

                Bundle selectedBundle = bundleListView.getSelectionModel().getSelectedItem();

                if(selectedBundle != null) {
                    showBundle(selectedBundle);
                }
            }
        });

        if (!saveComboBox.getItems().isEmpty()) {
            saveComboBox.getSelectionModel().selectFirst();
        }
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

            CheckBox checkBox = new CheckBox(item.getName());

            if(currentSave != null) {
                checkBox.setSelected(currentSave.isItemCompleted(item));
            }

            checkBox.setOnAction(event -> {
                if (currentSave == null) {
                    return;
                }

                if (checkBox.isSelected()) {
                    saveService.completeItem(currentSave, item.getId());
                } else {
                    saveService.uncompleteItem(currentSave, item.getId());
                }
            });

            itemContainer.getChildren().add(checkBox);
        }
    }

    @FXML 
    private void handleSave() {
        if (currentSave == null) {
            return;
        }

        saveService.save(currentSave);
    }
}