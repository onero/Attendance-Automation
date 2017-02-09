/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.components;

import attendanceautomation.be.Student;
import attendanceautomation.gui.model.SchoolClassModel;
import java.net.URL;
import java.util.ArrayList;
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
    
    private ArrayList<Student> studentsForSearch;
    
    private final SchoolClassModel SCModel = SchoolClassModel.getInstance();

    public SearchViewController() {
        studentsForSearch = new ArrayList();
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
        studentsForSearch.clear();
        SCModel.addData();
        for (Student student : SCModel.getStudents()) {
            if (student.getFullName().toLowerCase().contains(value.toLowerCase())) {
                studentsForSearch.add(student);
            }
        }
        
        SCModel.setStudents(studentsForSearch);
        
//        for (Student student : studentsForSearch) {
//            System.out.println(student.getFullName());
//        }
//        System.out.println("");
    }

    
}
