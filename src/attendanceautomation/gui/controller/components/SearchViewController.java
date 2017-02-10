/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.components;

import attendanceautomation.be.Student;
import attendanceautomation.gui.model.SchoolClassModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Skovgaard
 */
public class SearchViewController implements Initializable {

    @FXML
    private TextField txtSearch;
    private final SchoolClassModel SCModel = SchoolClassModel.getInstance();

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
    }
    
    private void updateListView(String value) {
        SCModel.getStudentSearchList().clear();
        for (Student student : SCModel.getStudents()) {
            if (student.getFullName().toLowerCase().contains(value.toLowerCase())) {
                SCModel.updateStudentsFromSearch(student);
            }
        }
    }

    
}
