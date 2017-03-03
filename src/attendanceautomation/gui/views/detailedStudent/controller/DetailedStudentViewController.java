/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.detailedStudent.controller;

import attendanceautomation.be.Student;
import attendanceautomation.be.enums.EFXMLNames;
import attendanceautomation.gui.views.sharedComponents.studentAttendanceInformation.controller.StudentAttendanceInformationViewController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author gta1
 */
public class DetailedStudentViewController implements Initializable {

    @FXML
    private BorderPane borderPane;

    private static DetailedStudentViewController instance;

    private Node studentInformationTopView;

    private Node studentAttendanceInformationCenterView;

    private FXMLLoader attendanceLoader;

    public static DetailedStudentViewController getInstance() {
        return instance;
    }

    public DetailedStudentViewController() {
        instance = this;
        try {
            studentInformationTopView = createTopView();
            studentAttendanceInformationCenterView = createCenterView();
        } catch (IOException e) {
            System.out.println("Couldn't create topView " + e);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        borderPane.setTop(studentInformationTopView);
        borderPane.setCenter(studentAttendanceInformationCenterView);
    }

    /**
     * Sets the current student
     *
     * @param selectedStudent
     */
    public void setCurrentStudent(Student selectedStudent) {
//        SchoolClassModel.getInstance().updateStudentInfo();
        StudentInformationTopViewController.getInstance().setStudentInfo(selectedStudent);
        StudentAttendanceInformationViewController controller = attendanceLoader.getController();
        controller.createSubjectView(selectedStudent);
    }

    /**
     * Create the StudentInformationTopView
     *
     * @return
     * @throws IOException
     */
    private Node createTopView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.STUDENT_INFORMATION_TOP_VIEW.toString()));
        Node node = loader.load();
        return node;
    }

    /**
     * Create the StudentInformationTopView
     *
     * @return
     * @throws IOException
     */
    private Node createCenterView() throws IOException {
        attendanceLoader = new FXMLLoader(getClass().getResource(EFXMLNames.STUDENTS_ATTENDANCE_INFORMATION.toString()));
        Node node = attendanceLoader.load();
        return node;
    }

}