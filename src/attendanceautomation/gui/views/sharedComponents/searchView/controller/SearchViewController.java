/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.sharedComponents.searchView.controller;

import attendanceautomation.be.Student;
import attendanceautomation.gui.model.SchoolClassModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Skovgaard
 */
public class SearchViewController implements Initializable {

    @FXML
    private TextField txtSearch;
    private final SchoolClassModel SCModel = SchoolClassModel.getInstance();
    @FXML
    private HBox hBoxSearch;
    @FXML
    private Button btnClear;

    public SearchViewController() {
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            updateListView(newValue);
        });
    }

    @FXML
    private void handleClear(ActionEvent event) {
        txtSearch.clear();
        SCModel.sortStudentsOnAttendance();
    }

    private void updateListView(String value) {
        SCModel.getStudents().clear();
        for (Student student : SCModel.getStudentsFromDB()) {
            if (student.getFullName().toLowerCase().contains(value.toLowerCase())) {
                SCModel.updateStudentsFromSearch(student);
            }
        }
    }

    public void showSearchBar(boolean visible) {
        txtSearch.setDisable(!visible);
        txtSearch.setVisible(visible);
        btnClear.setDisable(!visible);
        btnClear.setVisible(visible);
    }

}
