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
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author gta1
 */
public class FilterHolderViewController implements Initializable {

    private static FilterHolderViewController instance;

    @FXML
    private BorderPane borderPaneCenter;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
    }

    public static FilterHolderViewController getInstance() {
        return instance;
    }

    public void setCenter(Node filter) {
        borderPaneCenter.setCenter(filter);
    }

}
