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
import attendanceautomation.gui.model.SchemaModel;
import attendanceautomation.gui.model.SchoolClassModel;
import java.io.IOException;
import java.net.URL;
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

    private boolean isStudentLogin;

    public StudentAttendanceInformationViewController() {
        schoolClassModel = SchoolClassModel.getInstance();
        schoolClass = schoolClassModel.getCurrentSchoolClass();
        schemaModel = SchemaModel.getInstance();
        isStudentLogin = false;
    }

    public void setIsStudentLogin() {
        isStudentLogin = true;
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
     * @param newStudent
     */
    public void createSubjectView(Student newStudent) {
        student = newStudent;
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
        //TODO ALH: Make for each loop dynamic for each class
        for (SchoolSemesterSubject semesterSubject : schoolClass.getSemesterSubjects()) {
            //Create the subject Hbox
            HBox subjectHBox = new HBox();
            subjectHBox.setPrefHeight(59);
            subjectHBox.setPrefWidth(320);
            //Create a label for the HBox with the schoolSubject
            Label subjectName = new Label(semesterSubject.getSubject().toString());
            subjectName.setMinWidth(150);
            subjectName.setPrefWidth(150);
//            subjectName.setTextFill(Color.web("#fff"));
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
        isStudentLogin = false;
    }

    /**
     * Fill HBox with checkboxes
     *
     * @param schoolClass
     * @param subjectHBox
     * @throws IOException
     */
    private void fillSubjectHboxWithCheckBoxes(HBox subjectHBox, SchoolSemesterSubject semesterSubject) throws IOException {
        switch (schemaModel.getCurrentWeekNumber()) {
            case 5:
                subjectHBox.getChildren().add(createSubjectCheckBoxes(schemaModel.getFirstWeekFebruary(), semesterSubject));
                break;
            case 6:
                subjectHBox.getChildren().add(createSubjectCheckBoxes(schemaModel.getSecondWeekFebruary(), semesterSubject));
                break;
            case 7:
                subjectHBox.getChildren().add(createSubjectCheckBoxes(schemaModel.getThirdWeekFebruary(), semesterSubject));
                break;
            case 8:
                subjectHBox.getChildren().add(createSubjectCheckBoxes(schemaModel.getLastWeekFebruary(), semesterSubject));
                break;
            default:
                //Create subject checkBoxes for each week in february
                for (List<Integer> week : schemaModel.getWeeksInFebruary()) {
                    subjectHBox.getChildren().add(createSubjectCheckBoxes(week, semesterSubject));
                    if (!week.equals(schemaModel.getLastWeekFebruary())) {
                        subjectHBox.getChildren().add(createFillerLabel());
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
        switch (schemaModel.getCurrentWeekNumber()) {
            case 5:
                HBox.getChildren().add(createWeekCheckBoxes(schemaModel.getFirstWeekFebruary()));
                break;
            case 6:
                HBox.getChildren().add(createWeekCheckBoxes(schemaModel.getSecondWeekFebruary()));
                break;
            case 7:
                HBox.getChildren().add(createWeekCheckBoxes(schemaModel.getThirdWeekFebruary()));
                break;
            case 8:
                HBox.getChildren().add(createWeekCheckBoxes(schemaModel.getLastWeekFebruary()));
                break;
            default:
                //Create day checkBoxes for each week in february
                for (List<Integer> week : schemaModel.getWeeksInFebruary()) {
                    HBox.getChildren().add(createWeekCheckBoxes(week));
                    if (!week.equals(schemaModel.getLastWeekFebruary())) {
                        HBox.getChildren().add(createFillerLabel());
                    }
                }
        }

    }

    /**
     * Creates a ParentCheckBoxView
     */
    private Node createWeekCheckBoxes(List<Integer> week) throws IOException {
        FXMLLoader weekCheckBoxLoader = new FXMLLoader(getClass().getResource(EFXMLName.WEEK_CHECK_BOX_VIEW.toString()));
        Node node = weekCheckBoxLoader.load();
        WeekCheckBoxViewController controller = weekCheckBoxLoader.getController();
        controller.setWeekData(student, week);
        return node;
    }

    private void checkIsStudentLogin(WeekCheckBoxViewController controller) {
        if (isStudentLogin) {
            controller.setIsStudentLogin();
        }
    }

    /**
     * Creates a ParentCheckBoxView
     */
    private Node createSubjectCheckBoxes(List<Integer> week, SchoolSemesterSubject semesterSubject) throws IOException {
        FXMLLoader weekCheckBoxLoader = new FXMLLoader(getClass().getResource(EFXMLName.WEEK_CHECK_BOX_VIEW.toString()));
        Node node = weekCheckBoxLoader.load();
        WeekCheckBoxViewController controller = weekCheckBoxLoader.getController();
        checkIsStudentLogin(controller);
        controller.setSubjectWeekData(student, week, semesterSubject);
        return node;
    }

    /**
     * Creates a fillerLabel.
     *
     * @return
     * @throws IOException
     */
    private Node createFillerLabel() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLName.FILLER_LABEL.toString()));
        Node node = loader.load();
        return node;
    }
}
