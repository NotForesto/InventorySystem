module forest.inventorysystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens forest.inventorysystem to javafx.fxml;
    exports forest.inventorysystem;
}