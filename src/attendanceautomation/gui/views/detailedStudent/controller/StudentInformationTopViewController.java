/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.detailedStudent.controller;

import attendanceautomation.be.Student;
import attendanceautomation.be.enums.EFXMLNames;
import attendanceautomation.gui.views.sharedComponents.pieChart.controller.PieChartViewController;
import attendanceautomation.gui.model.SchoolClassModel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
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
    private ImageView photoOfStudent;
    @FXML
    private BorderPane studentBorderPane;
    @FXML
    private BorderPane holderBorderPane;
    @FXML
    private BorderPane TeacherBorderPane;
    @FXML
    private BorderPane absenceBorderPane;

    private Node pieChart;

    private FXMLLoader loader;

    private static StudentInformationTopViewController instance;

    private Student currentStudent;

    public static StudentInformationTopViewController getInstance() {
        return instance;
    }

    public StudentInformationTopViewController() {
        try {
            pieChart = createPieChartNode();
        } catch (IOException ex) {
            Logger.getLogger(StudentInformationTopViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        absenceBorderPane.setLeft(pieChart);
    }

    /**
     * Sets the selected student
     *
     * @param selectedStudent
     */
    public void setStudentInfo(Student selectedStudent) {
        currentStudent = selectedStudent;
        lblStudentName.setText(currentStudent.getFullName());
        lblStudentEmail.setText(currentStudent.getEmail());
        //TODO ALH: Make dynamic
        lblFieldOfStudy.setText("Datamatiker");
        //TODO ALH: Make dynamic
        lblStudentClass.setText(SchoolClassModel.getInstance().getCurrentSchoolClass().getSchoolClassName());
        //TODO ALH: Make dynamic
        lblStudentSemester.setText("2.");
        PieChartViewController controller = loader.getController();
//        controller.setData(AttendanceModel.getInstance().getStudentAttendance(currentStudent));

    }

    /**
     * Creates the node for the PieChartView.
     *
     * @return
     * @throws IOException
     */
    private Node createPieChartNode() throws IOException {
        loader = new FXMLLoader(getClass().getResource(EFXMLNames.PIE_CHART_VIEW.toString()));
        Node node = loader.load();
        return node;
    }

}