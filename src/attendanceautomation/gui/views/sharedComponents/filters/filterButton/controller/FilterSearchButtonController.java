/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.sharedComponents.filters.filterButton.controller;

import attendanceautomation.gui.views.rootView.controller.RootViewController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author gta1
 */
public class FilterSearchButtonController implements Initializable {

    @FXML
    private void handleSearch(ActionEvent event) {
        RootViewController.getInstance().handleFilterSearch();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
