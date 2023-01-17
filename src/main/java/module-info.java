module com.pitt.visualization {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.pitt.visualization to javafx.fxml;
    exports com.pitt.visualization;
}
