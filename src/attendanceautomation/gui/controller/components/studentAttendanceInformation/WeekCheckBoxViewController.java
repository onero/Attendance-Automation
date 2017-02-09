/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.components.studentAttendanceInformation;

import attendanceautomation.be.SchoolClass;
import attendanceautomation.be.SchoolDay;
import attendanceautomation.be.SchoolWeek;
import attendanceautomation.be.Student;
import attendanceautomation.gui.model.SchoolClassModel;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author gta1
 */
public class WeekCheckBoxViewController implements Initializable {

    @FXML
    private BorderPane checkBoxPane;
    @FXML
    private HBox horizontalCheckBoxPane;

    private SchoolClassModel schoolClassModel;

    private ArrayList<CheckBox> listOfCheckBoxes;

    private Student student;

    //TODO ALH: Change this to a SchoolWeek
    private int weekNumber;

    public WeekCheckBoxViewController() {
        listOfCheckBoxes = new ArrayList<>();
    }

    public void setStudent(Student newStudent) {
        student = newStudent;
    }

    public void setSchoolWeekNumber(int number) {
        weekNumber = number;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pouplateWeekHBoxWithCheckBoxes();
    }

    /**
     * Creates 5 checkBoxes and puts them in the horizontalCheckBoxPane and
     * listOfCheckBoxes.
     */
    private void pouplateWeekHBoxWithCheckBoxes() {
        schoolClassModel = SchoolClassModel.getInstance();
        SchoolClass schoolClass = schoolClassModel.getSchoolClasses().get(0);
        SchoolWeek schoolWeek = schoolClass.getSchoolWeeks().get(0);

        //TODO ALH: Bind the weekCheckBoxes to a week
        for (SchoolDay schoolDay : schoolWeek.getSchoolDays()) {
            CheckBox newCheckBox = new CheckBox();
            //Add a changelistener to the checkbox when it is checked!
            newCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    //TODO ALH: Convert this to data for the student
                    System.out.println("You just gave "
                            + student.getFullName()
                            + " unAttendance "
                            + "in week " + (weekNumber + 1)
                            + " on day " + (listOfCheckBoxes.indexOf(newCheckBox) + 1));
                }
            });
            horizontalCheckBoxPane.getChildren().add(newCheckBox);
            listOfCheckBoxes.add(newCheckBox);
        }
    }

    /**
     * Returns listOfCheckboxes.
     *
     * @return
     */
    public ArrayList<CheckBox> getCheckBoxes() {
        return listOfCheckBoxes;
    }

}
