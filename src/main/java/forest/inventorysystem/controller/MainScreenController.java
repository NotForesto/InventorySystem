package forest.inventorysystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainScreenController {
    @FXML
    private Label welcomeText;



    @FXML
    public void toAddPart(ActionEvent actionEvent) {
        System.out.println("Clicked");
    }
}