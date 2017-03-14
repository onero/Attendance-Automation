/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.currentClassView.controller;

import attendanceautomation.be.Student;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Skovgaard
 */
public class NameCurrentClassListViewController implements Initializable {

    @FXML
    private Label lblNameOfStudent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    /**
     * Binds the students name to the label.
     * @param student 
     */
    public void setStudentInfo(Student student) {
        lblNameOfStudent.setText(student.getFullName());
    }
    
}
