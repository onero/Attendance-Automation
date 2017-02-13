/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.components.studentAttendanceInformation;

import attendanceautomation.be.SchoolClass;
import attendanceautomation.be.SchoolLesson;
import attendanceautomation.be.SchoolWeek;
import attendanceautomation.be.Student;
import attendanceautomation.be.enums.EFXMLNames;
import attendanceautomation.gui.model.SchoolClassModel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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

    private final SchoolClass schoolClass;

    public StudentAttendanceInformationViewController() {
        schoolClassModel = SchoolClassModel.getInstance();
        schoolClass = schoolClassModel.getSchoolClasses().get(0);
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
            fillUpHBoxWithWeeks();
        } catch (IOException ex) {
            System.out.println("Couldn't fill up HBoxes");
        }
    }

    /**
     * Create the SubjectView
     *
     */
    public void createSubjectView() {
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
        for (SchoolLesson lesson : schoolClass.getLessons()) {
            //Create the subject Hbox
            HBox subjectHBox = new HBox();
            subjectHBox.setPrefHeight(59);
            subjectHBox.setPrefWidth(320);
            //Create a label for the HBox with the schoolSubject
            Label subjectName = new Label(lesson.getSubject().toString());
            subjectName.setMinWidth(150);
            subjectName.setPrefWidth(150);
            //Add the label
            subjectHBox.getChildren().add(subjectName);
            try {
                //Fill the subject HBox with checkboxes
                fillSubjectHboxWithCheckBoxes(schoolClass, subjectHBox, lesson);
            } catch (IOException e) {
                System.out.println("Couldn't create subjects");
            }
            //Add the Subject HBox to the VBox list of subjects
            VBox.getChildren().add(subjectHBox);
        }
    }

    /**
     * Fill HBox with checkboxes
     *
     * @param schoolClass
     * @param subjectHBox
     * @throws IOException
     */
    private void fillSubjectHboxWithCheckBoxes(SchoolClass schoolClass, HBox subjectHBox, SchoolLesson schoolLesson) throws IOException {
        //For each week in the current month
        for (SchoolWeek schoolWeek : schoolClass.getSchoolWeeks()) {
            //add checkboxes to the HBox
            subjectHBox.getChildren().add(createSubjectCheckBoxes(schoolWeek, schoolLesson));
            //And fillerLabel in between
            subjectHBox.getChildren().add(createFillerLabel());
        }
    }

    /**
     * Fill HBox with checkboxes
     *
     * @param schoolClass
     * @param subjectHBox
     * @throws IOException
     */
    private void fillWeekHboxWithCheckBoxes(SchoolClass schoolClass, HBox subjectHBox) throws IOException {
        //For each week in the current month
        for (SchoolWeek schoolWeek : schoolClass.getSchoolWeeks()) {
            //add checkboxes to the HBox
            subjectHBox.getChildren().add(createWeekCheckBoxes(schoolWeek));
            //If it is not the last week
            if (schoolClass.getSchoolWeeks().indexOf(schoolWeek) != this.schoolClass.getSchoolWeeks().size() - 1) {
                //And fillerLabel after the checkboxes
                subjectHBox.getChildren().add(createFillerLabel());
            }
        }
    }

    /**
     * Fills the hBox up to look nicely.
     *
     * @throws IOException
     */
    private void fillUpHBoxWithWeeks() throws IOException {
        fillWeekHboxWithCheckBoxes(schoolClass, HBox);
    }

    /**
     * Creates a ParentCheckBoxView
     */
    private Node createWeekCheckBoxes(SchoolWeek schoolWeek) throws IOException {
        FXMLLoader weekCheckBoxLoader = new FXMLLoader(getClass().getResource(EFXMLNames.WEEK_CHECK_BOX_VIEW.toString()));
        Node node = weekCheckBoxLoader.load();
        WeekCheckBoxViewController controller = weekCheckBoxLoader.getController();
        controller.setWeekData(student, schoolWeek);
        return node;
    }

    /**
     * Creates a ParentCheckBoxView
     */
    private Node createSubjectCheckBoxes(SchoolWeek schoolWeek, SchoolLesson schoolLesson) throws IOException {
        FXMLLoader weekCheckBoxLoader = new FXMLLoader(getClass().getResource(EFXMLNames.WEEK_CHECK_BOX_VIEW.toString()));
        Node node = weekCheckBoxLoader.load();
        WeekCheckBoxViewController controller = weekCheckBoxLoader.getController();
        controller.setSubjectWeekData(student, schoolWeek, schoolLesson);
        return node;
    }

    /**
     * Creates a fillerLabel.
     *
     * @return
     * @throws IOException
     */
    private Node createFillerLabel() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.FILLER_LABEL.toString()));
        Node node = loader.load();
        return node;
    }

}
