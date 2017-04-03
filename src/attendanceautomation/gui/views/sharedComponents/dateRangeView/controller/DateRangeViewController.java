/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.sharedComponents.dateRangeView.controller;

import attendanceautomation.gui.model.SchemaModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Mathias
 */
public class DateRangeViewController implements Initializable {

    @FXML
    private Label lblStartDate;
    @FXML
    private Label lblEndDate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bindLabels();
    }

    private void bindLabels() {
        lblStartDate.textProperty().bind(SchemaModel.getInstance().getPropertyStartDate());
        lblEndDate.textProperty().bind(SchemaModel.getInstance().getPropertyEndDate());
    }
}
