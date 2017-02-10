/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.components.studentAttendanceInformation;

import attendanceautomation.be.SchoolClass;
import attendanceautomation.be.SchoolWeek;
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

/**
 * FXML Controller class
 *
 * @author gta1
 */
public class StudentAttendanceInformationViewController implements Initializable {

    @FXML
    private HBox HBox;
    @FXML
    private Label lblStudent;

    private Student student;

    private final SchoolClassModel schoolClassModel;

    public StudentAttendanceInformationViewController() {
        schoolClassModel = SchoolClassModel.getInstance();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * Creates a ParentCheckBoxView
     */
    private Node createWeekCheckBoxes(SchoolWeek schoolWeek) throws IOException {
        FXMLLoader weekCheckBoxLoader = new FXMLLoader(getClass().getResource(EFXMLNames.WEEK_CHECK_BOX_VIEW.toString()));
        Node node = weekCheckBoxLoader.load();
        WeekCheckBoxViewController controller = weekCheckBoxLoader.getController();
        controller.setStudent(student, schoolWeek);
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
            Logger.getLogger(StudentAttendanceInformationViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Fills the hBox up to look nicely.
     *
     * @throws IOException
     */
    private void fillUpHBoxWithWeeks() throws IOException {
        SchoolClass schoolClass = schoolClassModel.getSchoolClasses().get(0);
        for (SchoolWeek schoolWeek : schoolClass.getSchoolWeeks()) {
            HBox.getChildren().add(createWeekCheckBoxes(schoolWeek));
            HBox.getChildren().add(createFillerLabel());
        }
    }

}
