/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.components.studentAttendanceInformation;

import attendanceautomation.be.SchoolClass;
import attendanceautomation.be.SchoolSemesterSubject;
import attendanceautomation.be.Student;
import attendanceautomation.be.enums.EFXMLNames;
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

    private Node weeksInMonthView;

    private final SchoolClassModel schoolClassModel;

    private final SchoolClass schoolClass;

    public StudentAttendanceInformationViewController() {
        schoolClassModel = SchoolClassModel.getInstance();
        schoolClass = schoolClassModel.getCurrentSchoolClass();
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

//        createWeeksInCurrentMonth();
        createHBoxesForEachSubject(schoolClass);
    }

    /**
     * Create and insert the weeks in current month
     */
    private void createWeeksInCurrentMonth() {
        try {
            weeksInMonthView = createWeeksInMonthView();
        } catch (IOException ex) {
            System.out.println("Shit!");
        }
        //Add the weeks in the month above the subjects
        VBox.getChildren().add(weeksInMonthView);
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
    }

    /**
     * Fill HBox with checkboxes
     *
     * @param schoolClass
     * @param subjectHBox
     * @throws IOException
     */
    private void fillSubjectHboxWithCheckBoxes(HBox subjectHBox, SchoolSemesterSubject semesterSubject) throws IOException {
        subjectHBox.getChildren().add(createSubjectCheckBoxes(semesterSubject));
        //And fillerLabel in between
        subjectHBox.getChildren().add(createFillerLabel());
        //add checkboxes to the HBox
        subjectHBox.getChildren().add(createSubjectCheckBoxes(semesterSubject));
        //And fillerLabel in between
        subjectHBox.getChildren().add(createFillerLabel());
        //add checkboxes to the HBox
        subjectHBox.getChildren().add(createSubjectCheckBoxes(semesterSubject));
        //And fillerLabel in between
        subjectHBox.getChildren().add(createFillerLabel());
        //add checkboxes to the HBox
        subjectHBox.getChildren().add(createSubjectCheckBoxes(semesterSubject));
    }

    /**
     * Fills the hBox up to look nicely.
     *
     * @throws IOException
     */
    private void fillUpHBoxWithWeeks() throws IOException {
        fillWeekHboxWithCheckBoxes();
    }

    /**
     * Fill HBox with checkboxes
     *
     * @param schoolClass
     * @param subjectHBox
     * @throws IOException
     */
    private void fillWeekHboxWithCheckBoxes() throws IOException {
        //TODO ALH: Make this dynamic
        //add checkboxes to the HBox
        HBox.getChildren().add(createWeekCheckBoxes());
        HBox.getChildren().add(createFillerLabel());
        HBox.getChildren().add(createWeekCheckBoxes());
        HBox.getChildren().add(createFillerLabel());
        HBox.getChildren().add(createWeekCheckBoxes());
        HBox.getChildren().add(createFillerLabel());
        HBox.getChildren().add(createWeekCheckBoxes());
    }

    /**
     * Creates a ParentCheckBoxView
     */
    private Node createWeekCheckBoxes() throws IOException {
        FXMLLoader weekCheckBoxLoader = new FXMLLoader(getClass().getResource(EFXMLNames.WEEK_CHECK_BOX_VIEW.toString()));
        Node node = weekCheckBoxLoader.load();
        WeekCheckBoxViewController controller = weekCheckBoxLoader.getController();
        controller.setWeekData(student);
        return node;
    }

    /**
     * Creates a ParentCheckBoxView
     */
    private Node createSubjectCheckBoxes(SchoolSemesterSubject semesterSubject) throws IOException {
        FXMLLoader weekCheckBoxLoader = new FXMLLoader(getClass().getResource(EFXMLNames.WEEK_CHECK_BOX_VIEW.toString()));
        Node node = weekCheckBoxLoader.load();
        WeekCheckBoxViewController controller = weekCheckBoxLoader.getController();
        controller.setSubjectWeekData(student, semesterSubject);
        return node;
    }

    /**
     * Creates the node of the daysInMonthView
     *
     * @return
     * @throws IOException
     */
    private Node createWeeksInMonthView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.DAYS_IN_MONTH_VIEW.toString()));
        Node node = loader.load();
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
