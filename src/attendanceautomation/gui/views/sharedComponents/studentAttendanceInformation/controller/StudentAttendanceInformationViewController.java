/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.sharedComponents.studentAttendanceInformation.controller;

import attendanceautomation.be.SchoolClass;
import attendanceautomation.be.SchoolSemesterSubject;
import attendanceautomation.be.Student;
import attendanceautomation.be.enums.EFXMLName;
import attendanceautomation.be.enums.ESemester;
import attendanceautomation.gui.model.SchemaModel;
import attendanceautomation.gui.model.SchoolClassModel;
import attendanceautomation.gui.views.NodeFactory;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 */
public class StudentAttendanceInformationViewController implements Initializable {

    @FXML
    private HBox HBox;
    @FXML
    private Label lblStudent;
    @FXML
    private VBox VBox;

    private Student student;

    private final SchoolClassModel schoolClassModel;

    private final SchemaModel schemaModel;

    private final SchoolClass schoolClass;

    public StudentAttendanceInformationViewController() {
        schoolClassModel = SchoolClassModel.getInstance();
        schoolClass = schoolClassModel.getCurrentSchoolClass();
        schemaModel = SchemaModel.getInstance();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * Sets the info about the parsed student.
     *
     * @param newStudent
     */
    public void setStudentInfo(Student newStudent) {
        student = newStudent;
        //Reset checkboxes
        HBox.getChildren().remove(1, HBox.getChildren().size());
        lblStudent.setText(student.getFullName());
        try {
            fillWeekHboxWithCheckBoxes();
        } catch (IOException ex) {
            System.out.println("Couldn't fill up HBoxes");
            System.out.println(ex);
        }
    }

    /**
     * Create the SubjectView
     *
     */
    public void createSubjectView() {
        student = schoolClassModel.getCurrentStudent();
        //Clear the VBox list
        VBox.getChildren().clear();
        createHBoxesForEachSubject(schoolClass);
    }

    /**
     * Create a new HBox for each subject
     *
     * @param schoolClass
     */
    private void createHBoxesForEachSubject(SchoolClass schoolClass) {
        for (SchoolSemesterSubject semesterSubject : schoolClass.getSemesterSubjects()) {
            //Check if currentSemester is set
            if (schoolClassModel.getCurrentSemester() != null) {
                //Check if semester semesterSubject is from currentSemester
                if (schoolClassModel.getCurrentSemester().equals(semesterSubject.getSemester())) {
                    createSubjectHBox(semesterSubject);
                }
            } else {
                //Just take from latest semester
                //TODO ALH: Make this dynamic to latest semester
                schoolClassModel.updateStudentData();
                if (semesterSubject.getSemester().equals(ESemester.SECOND_SEMESTER)) {
                    createSubjectHBox(semesterSubject);
                }
            }
        }
    }

    private void createSubjectHBox(SchoolSemesterSubject semesterSubject) {
        //Create the subject Hbox
        HBox subjectHBox = new HBox();
        subjectHBox.setPrefHeight(59);
        subjectHBox.setPrefWidth(320);
        //Create a label for the HBox with the schoolSubject
        Label subjectName = new Label(semesterSubject.getSubject().toString());
        subjectName.setMinWidth(150);
        subjectName.setPrefWidth(150);
        //Add the label
        subjectHBox.getChildren().add(subjectName);
        try {
            //Fill the subject HBox with checkboxes
            fillSubjectHboxWithCheckBoxes(subjectHBox, semesterSubject);
        } catch (IOException ex) {
            Logger.getLogger(StudentAttendanceInformationViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Add the Subject HBox to the VBox list of subjects
        VBox.getChildren().add(subjectHBox);
    }

    /**
     * Fill HBox with checkboxes
     *
     * @param schoolClass
     * @param subjectHBox
     * @throws IOException
     */
    private void fillSubjectHboxWithCheckBoxes(HBox subjectHBox, SchoolSemesterSubject semesterSubject) throws IOException {

        switch (schemaModel.getCurrentWeekOfMonthNumber()) {
            case 1:
                subjectHBox.getChildren().add(createSubjectCheckBoxes(schemaModel.getFirstWeekOfMonth(), semesterSubject));
                break;
            case 2:
                subjectHBox.getChildren().add(createSubjectCheckBoxes(schemaModel.getSecondWeekOfMonth(), semesterSubject));
                break;
            case 3:
                subjectHBox.getChildren().add(createSubjectCheckBoxes(schemaModel.getThirdWeekOfMonth(), semesterSubject));
                break;
            case 4:
                subjectHBox.getChildren().add(createSubjectCheckBoxes(schemaModel.getLastWeekOfMonth(), semesterSubject));
                break;
            default:
                //Create subject checkBoxes for each week in february
                for (List<Date> week : schemaModel.getWeeksOfMonth()) {
                    subjectHBox.getChildren().add(createSubjectCheckBoxes(week, semesterSubject));
                    if (!week.equals(schemaModel.getLastWeekOfMonth())) {
                        subjectHBox.getChildren().add(NodeFactory.getInstance().createNewView(EFXMLName.FILLER_LABEL));
                    }
                }
        }
    }

    /**
     * Fill HBox with checkboxes
     *
     * @param schoolClass
     * @param subjectHBox
     * @throws IOException
     */
    private void fillWeekHboxWithCheckBoxes() throws IOException {
        switch (schemaModel.getCurrentWeekOfMonthNumber()) {
            case 1:
                HBox.getChildren().add(createWeekCheckBoxes(schemaModel.getFirstWeekOfMonth()));
                break;
            case 2:
                HBox.getChildren().add(createWeekCheckBoxes(schemaModel.getSecondWeekOfMonth()));
                break;
            case 3:
                HBox.getChildren().add(createWeekCheckBoxes(schemaModel.getThirdWeekOfMonth()));
                break;
            case 4:
                HBox.getChildren().add(createWeekCheckBoxes(schemaModel.getLastWeekOfMonth()));
                break;
            default:
                //Create day checkBoxes for each week in february
                for (List<Date> week : schemaModel.getWeeksOfMonth()) {
                    HBox.getChildren().add(createWeekCheckBoxes(week));
                    if (!week.equals(schemaModel.getLastWeekOfMonth())) {
                        HBox.getChildren().add(NodeFactory.getInstance().createNewView(EFXMLName.FILLER_LABEL));
                    }
                }
        }

    }

    /**
     * Creates a ParentCheckBoxView
     */
    private Node createWeekCheckBoxes(List<Date> week) throws IOException {
        FXMLLoader weekCheckBoxLoader = new FXMLLoader(getClass().getResource(EFXMLName.WEEK_CHECK_BOX_VIEW.toString()));
        Node node = weekCheckBoxLoader.load();
        WeekCheckBoxViewController controller = weekCheckBoxLoader.getController();
        controller.setWeekData(student, week);
        return node;
    }

    /**
     * Creates a ParentCheckBoxView
     */
    private Node createSubjectCheckBoxes(List<Date> week, SchoolSemesterSubject semesterSubject) throws IOException {
        FXMLLoader weekCheckBoxLoader = new FXMLLoader(getClass().getResource(EFXMLName.WEEK_CHECK_BOX_VIEW.toString()));
        Node node = weekCheckBoxLoader.load();
        WeekCheckBoxViewController controller = weekCheckBoxLoader.getController();
        controller.setSubjectWeekData(student, week, semesterSubject);
        return node;
    }
}
