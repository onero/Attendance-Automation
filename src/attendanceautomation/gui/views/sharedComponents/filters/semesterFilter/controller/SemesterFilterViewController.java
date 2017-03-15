/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.sharedComponents.filters.semesterFilter.controller;

import attendanceautomation.be.SchoolSemesterSubject;
import attendanceautomation.gui.model.SchoolClassModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    @FXML
    private ComboBox<String> comboSemester;

    private final SchoolClassModel schoolClassModel;
    private final ObservableList<String> semesters = FXCollections.observableArrayList();

    public SemesterFilterViewController() {
        schoolClassModel = SchoolClassModel.getInstance();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeCombo();

        comboSemester.setItems(semesters);
        comboSemester.getSelectionModel().selectFirst();
    }

    /**
     * Fill it up
     */
    private void initializeCombo() {
        semesters.clear();
        for (SchoolSemesterSubject semesterSubject : schoolClassModel.getCurrentSchoolClass().getSemesterSubjects()) {
            if (!semesters.contains(semesterSubject.getSemester().toString())) {
                semesters.add(semesterSubject.getSemester().toString());
            }
        }
    }

    @FXML
    private void handleSelectSemester(Event event) {

    }

}
