/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.detailedStudent;

import attendanceautomation.be.Student;
import attendanceautomation.gui.model.SchoolClassModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author gta1
 */
public class StudentInformationTopViewController implements Initializable {

    @FXML
    private HBox HBox;
    @FXML
    private GridPane GridPane;
    @FXML
    private BorderPane BorderPaneLeft;
    @FXML
    private BorderPane BorderPaneCenter;
    @FXML
    private BorderPane BorderPaneRight;
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
    private PieChart PieChart;

    private static StudentInformationTopViewController instance;

    private Student currentStudent;

    public static StudentInformationTopViewController getInstance() {
        return instance;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("Grapefruit", 13),
                        new PieChart.Data("Oranges", 25),
                        new PieChart.Data("Plums", 10),
                        new PieChart.Data("Pears", 22),
                        new PieChart.Data("Apples", 30));
        PieChart.setData(pieChartData);
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
        lblStudentClass.setText(SchoolClassModel.getInstance().getSchoolClasses().get(0).getName());
        lblStudentPhone.setText("" + currentStudent.getPhone());
        //TODO ALH: Make dynamic
        lblStudentSemester.setText("2.");
    }

}
