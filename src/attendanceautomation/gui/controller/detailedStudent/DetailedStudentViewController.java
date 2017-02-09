/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.detailedStudent;

import attendanceautomation.AttendanceAutomationMain;
import attendanceautomation.be.enums.EFXMLNames;
import attendanceautomation.be.Student;
import attendanceautomation.gui.controller.components.studentAttendanceInformation.StudentAttendanceInformationViewController;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        try {
            studentInformationTopView = createTopView();
            studentAttendanceInformationCenterView = createCenterView();
        } catch (Exception e) {
            System.out.println("Couldn't create topView " + e);
        }
        borderPane.setTop(studentInformationTopView);
        borderPane.setCenter(studentAttendanceInformationCenterView);
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

    /**
     * Sets the current student
     *
     * @param selectStudent
     */
    public void setCurrentStudent(Student selectStudent) {
        StudentInformationTopViewController.getInstance().setStudentInfo(selectStudent);
        StudentAttendanceInformationViewController controller = attendanceLoader.getController();
        controller.setStudentInfo(selectStudent);
    }

}
