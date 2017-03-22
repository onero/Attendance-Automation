/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.detailedStudent.controller;

import attendanceautomation.be.enums.EFXMLName;
import attendanceautomation.gui.views.NodeFactory;
import attendanceautomation.gui.views.sharedComponents.studentAttendanceInformation.controller.StudentAttendanceInformationViewController;
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

    private Node weeksAndDaysBar;

    private FXMLLoader attendanceLoader;

    private StudentAttendanceInformationViewController studentAttendanceInfoController;

    public static DetailedStudentViewController getInstance() {
        return instance;
    }

    public DetailedStudentViewController() {
        instance = this;
        studentInformationTopView = NodeFactory.getInstance().createNewView(EFXMLName.STUDENT_INFORMATION_TOP_VIEW);
        studentAttendanceInformationCenterView = NodeFactory.getInstance().createNewView(EFXMLName.STUDENTS_ATTENDANCE_INFORMATION);
        StudentAttendanceInformationViewController.getInstance().createSubjectView();
        weeksAndDaysBar = NodeFactory.getInstance().createNewView(EFXMLName.WEEK_AND_DAYS_BAR);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        borderPane.setTop(studentInformationTopView);
        borderPane.setCenter(weeksAndDaysBar);
        borderPane.setBottom(studentAttendanceInformationCenterView);
    }

    public StudentAttendanceInformationViewController getStudentAttendanceInfoController() {
        return studentAttendanceInfoController;
    }

}
