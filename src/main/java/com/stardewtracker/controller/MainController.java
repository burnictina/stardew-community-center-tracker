package com.stardewtracker.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.util.Optional;

import com.stardewtracker.model.Bundle;
import com.stardewtracker.model.Item;
import com.stardewtracker.model.Room;
import com.stardewtracker.model.SaveFile;
import com.stardewtracker.repository.BundleRepository;
import com.stardewtracker.repository.ItemRepository;
import com.stardewtracker.repository.RoomRepository;
import com.stardewtracker.repository.SaveFileRepository;
import com.stardewtracker.service.ProgressService;
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

    @FXML 
    private Label progressLabel;

    private RoomService roomService;

    private SaveService saveService;

    private ProgressService progressService;

    private SaveFile currentSave;

    public MainController() {
        ItemRepository itemRepository = new ItemRepository();

        BundleRepository bundleRepository = new BundleRepository(itemRepository);

        RoomRepository roomRepository = new RoomRepository(bundleRepository);

        roomService = new RoomService(roomRepository);

        SaveFileRepository saveFileRepository = new SaveFileRepository(itemRepository);

        saveService = new SaveService(saveFileRepository, itemRepository);

        progressService = new ProgressService();
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

                updateProgress();
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

                updateProgress();
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

    @FXML 
    private void handleNewSave() {
        TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle("New Save");
        dialog.setHeaderText("Create a new save");
        dialog.setContentText("Save name: ");

        Optional<String> result = dialog.showAndWait();

        if(result.isEmpty()){
            return;
        }

        String name = result.get().trim();

        if(name.isEmpty()){
            showError("Invalid save name","Save cannot be empty.");
            return;
        }

        try{
        SaveFile newSave = saveService.createSave(name);

        saveComboBox.getItems().add(newSave);
        saveComboBox.getSelectionModel().select(newSave);
        }
        catch(RuntimeException e){
            showError("Could not create save", e.getMessage());
        }
    }

    private void updateProgress() {
        if (currentSave == null) {
            progressLabel.setText("Progress: 0 / 0 (0%)");
            return;
        }

        int completed = progressService.getCompletedCount(roomService.getAllRooms(), currentSave);

        int required = progressService.getRequiredCount(roomService.getAllRooms());

        int percentage = 0;

        if (required > 0) {
            percentage = (int) Math.round((double) completed / required * 100);
        }

        progressLabel.setText(
            "Progress: "
            + completed
            + " / "
            + required
            + " ("
            + percentage
            + "%)"
        );
    }
    private void showError(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}