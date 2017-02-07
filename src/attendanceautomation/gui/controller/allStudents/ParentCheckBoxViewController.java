/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.allStudents;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author gta1
 */
public class ParentCheckBoxViewController implements Initializable {

    @FXML
    private BorderPane checkBoxPane;
    @FXML
    private HBox horizontalCheckBoxPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void handleNewCheckBox() {
        CheckBox newCheckBox = new CheckBox();
        horizontalCheckBoxPane.getChildren().add(newCheckBox);
    }

}
