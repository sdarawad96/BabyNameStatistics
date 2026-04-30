package edu.westga.comp2320.babynamestatistics.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;

public class BabyNameController {

    // Buttons
    @FXML private Button addButton;
    @FXML private Button deleteAllButton;
    @FXML private Button deleteButton;
    @FXML private Button searchButton;

    // Radio Buttons
    @FXML private RadioButton maleRadio;
    @FXML private RadioButton femaleRadio;

    // Text Fields
    @FXML private TextField nameField;
    @FXML private TextField yearField;
    @FXML private TextField frequencyField;

    // ListView
    @FXML private ListView<String> recordListView; // we'll fix type later


    @FXML
    private void onSearchClick() {
        System.out.println("Search clicked");
    }

    @FXML
    private void onAddClick() {
        System.out.println("Add clicked");
    }

    @FXML
    private void onDeleteClick() {
        System.out.println("Delete clicked");
    }

    @FXML
    private void onDeleteAllClick() {
        System.out.println("Delete All clicked");
    }

    @FXML
    private void onOpenClick() {
        System.out.println("Open clicked");
    }

    @FXML
    private void onSaveClick() {
        System.out.println("Save clicked");
    }

    @FXML
    private void onAboutClick() {
        System.out.println("About clicked");
    }
}