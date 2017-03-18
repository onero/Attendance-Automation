/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.sharedComponents.filters.allSchoolClassesSemesterFilter.controller;

import attendanceautomation.be.enums.ESemester;
import attendanceautomation.gui.model.SchoolClassModel;
import attendanceautomation.gui.views.sharedComponents.filters.schoolClassFilter.controller.SchoolClassFilterViewController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author gta1
 */
public class AllSchoolClassesSemesterFilterViewController implements Initializable {

    @FXML
    private ComboBox<String> comboSemester;

    private final SchoolClassModel schoolClassModel;

    public AllSchoolClassesSemesterFilterViewController() {
        schoolClassModel = SchoolClassModel.getInstance();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList semesters = FXCollections.observableArrayList();
        semesters.add("Alle");
        semesters.add(ESemester.FIRST_SEMESTER.toString());
        semesters.add(ESemester.SECOND_SEMESTER.toString());
        semesters.add(ESemester.THIRD_SEMESTER.toString());
        semesters.add(ESemester.FOURTH_SEMESTER.toString());
        semesters.add(ESemester.FIFTH_SEMESTER.toString());

        comboSemester.setItems(semesters);
        comboSemester.getSelectionModel().selectFirst();
    }

    @FXML
    private void handleSelectSemester() {
        String semester = comboSemester.getSelectionModel().getSelectedItem();
        if (semester.toLowerCase().equals("alle")) {
            schoolClassModel.resetSchoolNamesAndIDs();
        } else {
            schoolClassModel.updateSchoolClassesOnSemester(semester);
        }
        SchoolClassFilterViewController.getInstance().openBox();
        schoolClassModel.clearSemesters();
    }

}
