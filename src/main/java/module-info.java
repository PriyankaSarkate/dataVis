module com.pitt.visualization {
    requires javafx.controls;
    requires javafx.fxml;
	requires com.fasterxml.jackson.databind;
	requires formats.api;
	requires java.desktop;
	requires javafx.swing;

    opens com.pitt.visualization to javafx.fxml;
    exports com.pitt.visualization;
}
