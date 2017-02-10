/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.components.studentAttendanceInformation;

import attendanceautomation.be.SchoolDay;
import attendanceautomation.be.SchoolWeek;
import attendanceautomation.be.Student;
import attendanceautomation.gui.model.SchoolClassModel;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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

    private SchoolWeek schoolWeek;

    public WeekCheckBoxViewController() {
        listOfCheckBoxes = new ArrayList<>();
        schoolClassModel = SchoolClassModel.getInstance();
    }

    public void setStudent(Student newStudent, SchoolWeek weekend) {
        student = newStudent;
        schoolWeek = weekend;
        setSchoolWeek(weekend);
        pouplateWeekHBoxWithCheckBoxes();
    }

    private void setSchoolWeek(SchoolWeek schoolWeek) {
        this.schoolWeek = schoolWeek;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * Creates 5 checkBoxes and puts them in the horizontalCheckBoxPane and
     * listOfCheckBoxes.
     */
    private void pouplateWeekHBoxWithCheckBoxes() {
        //For each schoolday in the schoolweek
        for (SchoolDay schoolDay : schoolWeek.getSchoolDays()) {
            //Create a nice new checkbox (SO WE CAN KEEP TRACK OF STUDENTS!)
            CheckBox newCheckBox = new CheckBox();
            checkForStudentNonAttendance(schoolDay, newCheckBox);
            //Add the checkbox to the view
            horizontalCheckBoxPane.getChildren().add(newCheckBox);
            //Add the checkbox to our array, so we can keep track of it
            listOfCheckBoxes.add(newCheckBox);

            addChangeListenerToCheckBox(newCheckBox, schoolDay);
        }
    }

    private void checkForStudentNonAttendance(SchoolDay schoolDay, CheckBox newCheckBox) {
        //Check if student has nonAttendance
        if (student.getNonAttendance().size() > 0) {
            //For each HashMap
            for (HashMap<SchoolWeek, SchoolDay> hashMap : student.getNonAttendance()) {
                //Check if the student was nonAttendant this schoolWeek
                if (hashMap.containsKey(schoolWeek)) {
                    //Check if the student was nonAttendant this schoolDay
                    if (hashMap.containsValue(schoolDay)) {
                        //If he was then check the box
                        newCheckBox.setSelected(true);
                    }
                }

            }
        }
    }

    /**
     * Add a changelistener to the checkbox when it is checked! (SO WE CAN
     * PUNISH STUDENTS!!!)
     *
     * @param newCheckBox
     * @param schoolDay
     */
    private void addChangeListenerToCheckBox(CheckBox newCheckBox, SchoolDay schoolDay) {
        newCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                //Create a hashmap of the attendance
                HashMap<SchoolWeek, SchoolDay> attendance = new HashMap<>();
                attendance.put(schoolWeek, schoolDay);
                //If the student was not attending class
                if (newCheckBox.isSelected()) {
                    //PUNISH STUDENT!
                    student.addNonAttendance(attendance);
                    //If the student was infact attending school
                } else {
                    //Forgive the child <3
                    student.removeNonAttendance(attendance);
                }
            }
        });
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
