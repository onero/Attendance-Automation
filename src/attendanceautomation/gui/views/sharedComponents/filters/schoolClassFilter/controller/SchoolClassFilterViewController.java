/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.sharedComponents.filters.schoolClassFilter.controller;

import attendanceautomation.gui.model.SchoolClassModel;
import attendanceautomation.gui.views.sharedComponents.filters.semesterFilter.controller.SemesterFilterViewController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
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

    private static SchoolClassFilterViewController instance;

    public static SchoolClassFilterViewController getInstance() {
        return instance;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        comboSchoolClass.setItems(SchoolClassModel.getInstance().getSchoolClassNames());
        comboSchoolClass.setPromptText("Vælg klasse");
    }

    public String getSchoolName() {
        return comboSchoolClass.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void handleSelectSchoolClass() {
        String schoolClassName = comboSchoolClass.getSelectionModel().getSelectedItem();
        if (schoolClassName != null) {
            Runnable task = () -> {
                Platform.runLater(() -> {
                    SchoolClassModel.getInstance().getSemestersOnSchoolClassName(schoolClassName);
                    SemesterFilterViewController.getInstance().setPromptText();
                });
            };
            new Thread(task).start();
        }
    }

    /**
     * Show content of comboBox
     */
    public void openBox() {
        comboSchoolClass.show();
    }
}
