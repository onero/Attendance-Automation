/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.allStudents;

import attendanceautomation.be.Student;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author gta1
 */
public class StudentAttendanceInformationViewController implements Initializable {

    @FXML
    private HBox HBox;
    @FXML
    private Label lblStudent;
    
    private Student student;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            fillUpHBox();
        } catch (Exception e) {
        }

    }

    /**
     * Creates a ParentCheckBoxView
     */
    private Node createParentCheckBoxView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/attendanceautomation/gui/view/allStudents/ParentCheckBoxView.fxml"));
        Node node = loader.load();
        return node;
    }
    
    /**
     * Creates a fillerLabel.
     * @return
     * @throws IOException 
     */
    private Node createFillerLabel() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/attendanceautomation/gui/view/components/FillerLabel.fxml"));
        Node node = loader.load();
        return node;
    }
    
    /**
     * Sets the info about the parsed student.
     * @param item 
     */
    public void setStudentInfo(Student item) {
        student = item;
        lblStudent.setText(student.getFullName());
    }
    
    /**
     * Fills the hBox up to look nicely.
     * @throws IOException 
     */
    private void fillUpHBox() throws IOException {
        HBox.getChildren().add(createParentCheckBoxView());
        HBox.getChildren().add(createFillerLabel());
        HBox.getChildren().add(createParentCheckBoxView());
        HBox.getChildren().add(createFillerLabel());
        HBox.getChildren().add(createParentCheckBoxView());
        HBox.getChildren().add(createFillerLabel());
        HBox.getChildren().add(createParentCheckBoxView());
    }

}
