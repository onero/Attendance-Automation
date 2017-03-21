/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.detailedStudent.controller;

import attendanceautomation.be.Student;
import attendanceautomation.gui.model.SchoolClassModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author gta1
 */
public class StudentInformationTopViewController implements Initializable {

    @FXML
    private Label lblStudentName;
    @FXML
    private Label lblStudentEmail;
    @FXML
    private Label lblStudentPhone;
    @FXML
    private Label lblFieldOfStudy;
    @FXML
    private Label lblStudentSemester;
    @FXML
    private Label lblStudentClass;
    @FXML
    private ListView<?> listTeachers;
    @FXML
    private BorderPane studentBorderPane;
    @FXML
    private BorderPane TeacherBorderPane;
    @FXML
    private BorderPane rootBorderPane;

    private static StudentInformationTopViewController instance;

    public static StudentInformationTopViewController getInstance() {
        return instance;
    }

    public StudentInformationTopViewController() {
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        setStudentInfo();
    }

    /**
     * Sets the selected student
     *
     */
    private void setStudentInfo() {
        Student currentStudent = SchoolClassModel.getInstance().getCurrentStudent();
        lblStudentName.setText(currentStudent.getFullName());
        lblStudentEmail.setText(currentStudent.getEmail());
        //TODO ALH: Make dynamic
        lblFieldOfStudy.setText("Datamatiker");
        //TODO ALH: Make dynamic
        lblStudentClass.setText(SchoolClassModel.getInstance().getCurrentSchoolClass().getSchoolClassName());
        //TODO ALH: Make dynamic
        lblStudentSemester.setText("2.");

    }

}
