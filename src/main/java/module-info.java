module forest.inventorysystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens forest.inventorysystem to javafx.fxml;
    exports forest.inventorysystem;
    exports forest.inventorysystem.controller;
    exports forest.inventorysystem.model;
    opens forest.inventorysystem.controller to javafx.fxml;
}