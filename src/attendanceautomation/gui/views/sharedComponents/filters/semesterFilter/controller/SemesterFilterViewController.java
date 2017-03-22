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

    public static SemesterFilterViewController getInstance() {
        if (instance == null) {
            instance = new SemesterFilterViewController();
        }
        return instance;
    }

    @FXML
    private ComboBox<String> comboSemester;

    private final SchoolClassModel schoolClassModel;

    public SemesterFilterViewController() {
        schoolClassModel = SchoolClassModel.getInstance();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        comboSemester.setItems(schoolClassModel.getSemesters());
    }

    public void setPromptText() {
        comboSemester.setPromptText("Vælg semester");
    }

    public int getSemesterID() {
        return schoolClassModel.getSemesterIDByName(comboSemester.getSelectionModel().getSelectedItem());
    }

    public boolean isSemesterSelected() {
        return !comboSemester.getSelectionModel().isEmpty();
    }

    /**
     * Fetches the relevant data from the DB and updates the view.
     *
     * @param event
     */
    @FXML
    private void handleSelectSemester(Event event) {
    }

}
