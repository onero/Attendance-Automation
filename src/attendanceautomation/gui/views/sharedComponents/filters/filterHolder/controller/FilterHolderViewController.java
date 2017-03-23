/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.sharedComponents.filters.filterHolder.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author gta1
 */
public class FilterHolderViewController implements Initializable {

    private static FilterHolderViewController instance;

    public static FilterHolderViewController getInstance() {
        if (instance == null) {
            instance = new FilterHolderViewController();
        }
        return instance;
    }

    @FXML
    private VBox vBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
    }

    public void addFilter(Node filter) {
        vBox.getChildren().add(filter);
    }

}
