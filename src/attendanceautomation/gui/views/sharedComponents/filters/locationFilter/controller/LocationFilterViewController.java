/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.sharedComponents.filters.locationFilter.controller;

import attendanceautomation.gui.model.SchoolClassModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author Mathias
 */
public class LocationFilterViewController implements Initializable {

    @FXML
    private ComboBox<String> comboLocationFilter;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboLocationFilter.setItems(SchoolClassModel.getInstance().getLocationNames());
        comboLocationFilter.getSelectionModel().selectFirst();
    }

    @FXML
    private void handleLocationFilter(Event event) {

    }

}
