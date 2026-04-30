module edu.westga.comp2320.babynamestatistics {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.westga.comp2320.babynamestatistics to javafx.fxml;
    exports edu.westga.comp2320.babynamestatistics;
    exports edu.westga.comp2320.babynamestatistics.controller;
    opens edu.westga.comp2320.babynamestatistics.controller to javafx.fxml;
}