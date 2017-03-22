/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.detailedStudent.controller;

import attendanceautomation.be.SchoolSemesterSubject;
import attendanceautomation.be.Student;
import attendanceautomation.be.Teacher;
import attendanceautomation.be.enums.ESchoolSubject;
import attendanceautomation.bll.SubjectManager;
import attendanceautomation.gui.model.SchoolClassModel;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private TableView<Teacher> listTeachers;
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
    @FXML
    private Label lblTotalAbsence;
    @FXML
    private Label lblScoAbsence;
    @FXML
    private Label lblSdeAbsence;
    @FXML
    private Label lblItoAbsence;
    @FXML
    private Label lblDbosAbsence;
    @FXML
    private TableColumn<Teacher, String> columnTeacher;
    @FXML
    private TableColumn<SchoolSemesterSubject, String> columnSubject;
    @FXML
    private TableColumn<Teacher, String> columnEmail;

    private Set<String> teacherNames;

    private ObservableList<Teacher> teachers;

    private SubjectManager subMgr;

    public StudentInformationTopViewController() {
        teachers = FXCollections.observableArrayList();
        subMgr = new SubjectManager();

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        initializeTeachers();
        setStudentInfo();
        setCellFactory();
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

        lblTotalAbsence.setText(currentStudent.getNonAttendancePercentage().get() + " %");

        subMgr.subjectAbsenceCalculation(currentStudent, ESchoolSubject.SCO);

        lblScoAbsence.setText(subMgr.subjectAbsenceCalculation(currentStudent, ESchoolSubject.SCO) + " %");
        lblItoAbsence.setText(subMgr.subjectAbsenceCalculation(currentStudent, ESchoolSubject.ITO) + " %");
        lblSdeAbsence.setText(subMgr.subjectAbsenceCalculation(currentStudent, ESchoolSubject.SDE) + " %");
        lblDbosAbsence.setText(subMgr.subjectAbsenceCalculation(currentStudent, ESchoolSubject.DBOS) + " %");
    }

    /**
     * Fills the tableView of teachers with info.
     */
    private void setCellFactory() {
        listTeachers.setItems(teachers);
        columnTeacher.setCellValueFactory(i -> i.getValue().getFullNameProperty());
        columnEmail.setCellValueFactory(i -> i.getValue().getEmailProperty());
        //TODO MSP: SetCellValueFactory on columnSubject.
    }

    /**
     * Uses SchoolSemesterSubjects to get all the names of the teachers, and
     * then calls the database through the layers to get a list of teacher
     * objects.
     */
    private void initializeTeachers() {
        teacherNames = new HashSet<>();
        for (SchoolSemesterSubject semesterSubject : SchoolClassModel.getInstance().getCurrentSchoolClass().getSemesterSubjects()) {
            if (!teacherNames.contains(semesterSubject.getTeacher().toString())) {
                teacherNames.add(semesterSubject.getTeacher().toString());
            }
        }
        teachers.addAll(SchoolClassModel.getInstance().getTeachersByNames(teacherNames));
    }
}
