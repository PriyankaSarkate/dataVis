module com.pitt.visualization {
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.swing;
	requires com.fasterxml.jackson.databind;
	requires formats.api;
	requires ome.xml;
	exports com.pitt.visualization;
	opens com.pitt.visualization to javafx.graphics;
}
