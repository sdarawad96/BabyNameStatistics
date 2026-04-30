package edu.westga.comp2320.babynamestatistics.controller;

import edu.westga.comp2320.babynamestatistics.model.BabyNameRecord;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Controller for the Baby Name Statistics application.
 * Handles opening, saving, searching, adding, deleting, and selecting baby name records.
 */
public class BabyNameController {

    @FXML
    private Button addButton;

    @FXML
    private Button deleteAllButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button searchButton;

    @FXML
    private RadioButton maleRadio;

    @FXML
    private RadioButton femaleRadio;

    @FXML
    private TextField nameField;

    @FXML
    private TextField yearField;

    @FXML
    private TextField frequencyField;

    @FXML
    private ListView<BabyNameRecord> recordListView;

    /**
     * Group for the gender radio buttons.
     */
    private ToggleGroup genderGroup;

    /**
     * Stores all records, including records hidden by search.
     */
    private ArrayList<BabyNameRecord> allRecords;

    /**
     * Initializes the controller after the FXML file loads.
     */
    @FXML
    private void initialize() {
        this.allRecords = new ArrayList<>();
        this.genderGroup = new ToggleGroup();

        this.maleRadio.setToggleGroup(this.genderGroup);
        this.femaleRadio.setToggleGroup(this.genderGroup);

        this.nameField.textProperty().addListener((observable, oldValue, newValue) -> this.updateButtonStates());
        this.yearField.textProperty().addListener((observable, oldValue, newValue) -> this.updateButtonStates());
        this.frequencyField.textProperty().addListener((observable, oldValue, newValue) -> this.updateButtonStates());

        this.genderGroup.selectedToggleProperty().addListener(
                (observable, oldValue, newValue) -> this.updateButtonStates()
        );

        this.recordListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    this.displayRecord(newValue);
                    this.updateButtonStates();
                }
        );

        this.updateButtonStates();
    }

    /**
     * Opens a CSV file selected by the user.
     */
    @FXML
    private void onOpenClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open CSV File");

        File file = fileChooser.showOpenDialog(this.recordListView.getScene().getWindow());

        if (file != null) {
            this.loadDataFromFile(file);
        }
    }

    /**
     * Loads baby name records from the selected CSV file.
     *
     * @param file the CSV file to load
     */
    private void loadDataFromFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            this.allRecords.clear();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                String name = parts[0];
                String gender = parts[1];
                int year = Integer.parseInt(parts[2]);
                int frequency = Integer.parseInt(parts[3]);

                BabyNameRecord record = new BabyNameRecord(name, gender, year, frequency);
                this.allRecords.add(record);
            }

            this.sortRecords();
            this.showAllRecords();

        } catch (Exception error) {
            this.showError("Open Error", "Could not open file",
                    "There was a problem loading the CSV file.");
            this.updateButtonStates();
            return;
        }

        this.updateButtonStates();
    }

    /**
     * Displays all records in the list view.
     */
    private void showAllRecords() {
        this.recordListView.getItems().clear();

        for (BabyNameRecord record : this.allRecords) {
            this.recordListView.getItems().add(record);
        }
    }

    /**
     * Sorts records by year descending, gender female before male,
     * frequency descending, and name ascending.
     */
    private void sortRecords() {
        this.allRecords.sort(
                Comparator.comparingInt(BabyNameRecord::getYear).reversed()
                        .thenComparing(BabyNameRecord::getGender)
                        .thenComparing(Comparator.comparingInt(BabyNameRecord::getFrequency).reversed())
                        .thenComparing(BabyNameRecord::getName)
        );
    }

    /**
     * Displays the selected record in the form fields.
     *
     * @param record the selected baby name record
     */
    private void displayRecord(BabyNameRecord record) {
        if (record == null) {
            return;
        }

        this.nameField.setText(record.getName());
        this.yearField.setText(String.valueOf(record.getYear()));
        this.frequencyField.setText(String.valueOf(record.getFrequency()));

        if (record.getGender().equals("M")) {
            this.maleRadio.setSelected(true);
        } else {
            this.femaleRadio.setSelected(true);
        }
    }

    /**
     * Handles the Search button click.
     * Shows exactly the records that match the entered field values.
     */
    @FXML
    private void onSearchClick() {
        String name = this.nameField.getText().trim().toLowerCase();
        String yearText = this.yearField.getText().trim();
        String frequencyText = this.frequencyField.getText().trim();
        String gender = this.getSelectedGender();

        if (!this.isIntegerOrEmpty(yearText) || !this.isIntegerOrEmpty(frequencyText)) {
            this.showError("Search Error", "Invalid input",
                    "Year and frequency must be integers.");
            return;
        }

        this.recordListView.getItems().clear();

        for (BabyNameRecord record : this.allRecords) {
            boolean matchesName = name.isEmpty()
                    || record.getName().toLowerCase().contains(name);
            boolean matchesGender = gender.isEmpty()
                    || record.getGender().equals(gender);
            boolean matchesYear = yearText.isEmpty()
                    || String.valueOf(record.getYear()).equals(yearText);
            boolean matchesFrequency = frequencyText.isEmpty()
                    || String.valueOf(record.getFrequency()).equals(frequencyText);

            if (matchesName && matchesGender && matchesYear && matchesFrequency) {
                this.recordListView.getItems().add(record);
            }
        }

        this.updateButtonStates();
    }

    /**
     * Handles the Add button click.
     * Adds a record using the form field values.
     */
    @FXML
    private void onAddClick() {
        String name = this.nameField.getText().trim();
        String yearText = this.yearField.getText().trim();
        String frequencyText = this.frequencyField.getText().trim();

        if (name.isEmpty() || yearText.isEmpty() || frequencyText.isEmpty()
                || this.genderGroup.getSelectedToggle() == null) {
            this.showError("Add Error", "Missing input",
                    "Name, gender, year, and frequency are required.");
            return;
        }

        if (!this.isIntegerOrEmpty(yearText) || !this.isIntegerOrEmpty(frequencyText)) {
            this.showError("Add Error", "Invalid input",
                    "Year and frequency must be integers.");
            return;
        }

        String gender = this.getSelectedGender();
        int year = Integer.parseInt(yearText);
        int frequency = Integer.parseInt(frequencyText);

        BabyNameRecord record = new BabyNameRecord(name, gender, year, frequency);

        this.allRecords.add(record);
        this.sortRecords();
        this.showAllRecords();
        this.recordListView.getSelectionModel().select(record);
        this.updateButtonStates();
    }

    /**
     * Handles the Delete button click.
     * Deletes the selected record from the application.
     */
    @FXML
    private void onDeleteClick() {
        BabyNameRecord selectedRecord = this.recordListView.getSelectionModel().getSelectedItem();

        if (selectedRecord != null) {
            this.allRecords.remove(selectedRecord);
            this.recordListView.getItems().remove(selectedRecord);
            this.clearForm();
        }

        this.updateButtonStates();
    }

    /**
     * Handles the Delete All button click.
     * Deletes all records, including records hidden by search.
     */
    @FXML
    private void onDeleteAllClick() {
        this.allRecords.clear();
        this.recordListView.getItems().clear();
        this.clearForm();
        this.updateButtonStates();
    }

    /**
     * Handles the Save menu item click.
     * Saves all current records to a CSV file.
     */
    @FXML
    private void onSaveClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save CSV File");

        File file = fileChooser.showSaveDialog(this.recordListView.getScene().getWindow());

        if (file == null) {
            return;
        }

        try (PrintWriter writer = new PrintWriter(file)) {
            for (BabyNameRecord record : this.allRecords) {
                writer.println(record.getName() + ","
                        + record.getGender() + ","
                        + record.getYear() + ","
                        + record.getFrequency());
            }
        } catch (Exception error) {
            this.showError("Save Error", "Could not save file",
                    "There was a problem saving the CSV file.");
        }
    }

    /**
     * Handles the About menu item click.
     */
    @FXML
    private void onAboutClick() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Baby Name Frequency App");
        alert.setHeaderText("Message");
        alert.setContentText("This application manages baby name frequency records.\n\n" +
                "Author: Sondus Darawad");
        alert.showAndWait();
    }

    /**
     * Gets the selected gender.
     *
     * @return the selected gender, or an empty string if none is selected
     */
    private String getSelectedGender() {
        if (this.maleRadio.isSelected()) {
            return "M";
        }

        if (this.femaleRadio.isSelected()) {
            return "F";
        }

        return "";
    }

    /**
     * Checks whether the text is empty or a valid integer.
     *
     * @param text the text to check
     * @return true if the text is empty or an integer
     */
    private boolean isIntegerOrEmpty(String text) {
        if (text.isEmpty()) {
            return true;
        }

        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException error) {
            return false;
        }
    }

    /**
     * Clears the form fields.
     */
    private void clearForm() {
        this.nameField.clear();
        this.yearField.clear();
        this.frequencyField.clear();
        this.genderGroup.selectToggle(null);
        this.recordListView.getSelectionModel().clearSelection();
    }

    /**
     * Updates button states based on the current data and form values.
     */
    private void updateButtonStates() {
        boolean hasRecords = !this.allRecords.isEmpty();
        boolean hasSelection = this.recordListView.getSelectionModel().getSelectedItem() != null;

        boolean hasName = !this.nameField.getText().trim().isEmpty();
        boolean hasYear = !this.yearField.getText().trim().isEmpty();
        boolean hasFrequency = !this.frequencyField.getText().trim().isEmpty();
        boolean hasGender = this.genderGroup.getSelectedToggle() != null;

        this.searchButton.setDisable(!hasRecords);
        this.deleteButton.setDisable(!hasSelection);
        this.deleteAllButton.setDisable(!hasRecords);
        this.addButton.setDisable(!(hasName && hasYear && hasFrequency && hasGender));
    }

    /**
     * Shows an error alert.
     *
     * @param title the alert title
     * @param header the alert header
     * @param content the alert content
     */
    private void showError(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}