/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.sharedComponents.filters.semesterFilter.controller;

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
public class SemesterFilterViewController implements Initializable {

    private static SemesterFilterViewController instance;

    @FXML
    private ComboBox<String> comboSemester;

    private final SchoolClassModel schoolClassModel;

    public static SemesterFilterViewController getInstance() {
        return instance;
    }

    public SemesterFilterViewController() {
        schoolClassModel = SchoolClassModel.getInstance();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        schoolClassModel.updateSemesters();
        comboSemester.setItems(schoolClassModel.getSemesters());
        selectLatest();
    }

    @FXML
    private void handleSelectSemester(Event event) {

    }

    /**
     * Select the first element
     */
    public void selectLatest() {
        comboSemester.getSelectionModel().selectLast();
    }

}
