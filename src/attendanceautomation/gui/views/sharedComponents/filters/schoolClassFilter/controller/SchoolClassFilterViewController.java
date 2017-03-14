/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.sharedComponents.filters.schoolClassFilter.controller;

import attendanceautomation.gui.model.SchoolClassModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author gta1
 */
public class SchoolClassFilterViewController implements Initializable {

    @FXML
    private ComboBox<String> comboSchoolClass;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboSchoolClass.setItems(SchoolClassModel.getInstance().getSchoolClassNames());
        comboSchoolClass.getSelectionModel().selectFirst();
    }

    @FXML
    private void handleSelectSchoolClass() {
        String schoolClassName = comboSchoolClass.getSelectionModel().getSelectedItem();
        SchoolClassModel.getInstance().loadSchoolClassByName(schoolClassName);
    }

}
