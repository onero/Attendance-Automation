/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.components.studentAttendanceInformation;

import attendanceautomation.be.NonAttendance;
import attendanceautomation.be.SchoolClassSemesterLesson;
import attendanceautomation.be.SchoolLesson;
import attendanceautomation.be.SchoolSemesterSubject;
import attendanceautomation.be.Student;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private final ArrayList<CheckBox> listOfCheckBoxes;

    private Student student;

    public WeekCheckBoxViewController() {
        listOfCheckBoxes = new ArrayList<>();
    }

    /**
     * Set data for the student and the week
     *
     * @param newStudent
     * @param startDate
     * @param weekLessons
     */
    public void setWeekData(Student newStudent, Calendar startDate, ArrayList<ArrayList<SchoolClassSemesterLesson>> weekLessons) {
        student = newStudent;
        try {
            pouplateWeekHBoxWithCheckBoxes(startDate, weekLessons);
        } catch (ParseException ex) {
            Logger.getLogger(WeekCheckBoxViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Set data for the subject and the week
     *
     * @param newStudent
     * @param semesterSubject
     */
    public void setSubjectWeekData(Student newStudent, SchoolSemesterSubject semesterSubject) {
        student = newStudent;
        pouplateSubjectHBoxWithCheckBoxes(semesterSubject);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * CreatescheckBoxes for each subject and puts them in the
     * horizontalCheckBoxPane and listOfCheckBoxes.
     */
    private void pouplateSubjectHBoxWithCheckBoxes(SchoolSemesterSubject semesterSubject) {
        CheckBox newCheckBox;
        //For each schoolday in the schoolweek
        for (int schoolDay = 0; schoolDay < 5; schoolDay++) {
            //Create a nice new checkbox (SO WE CAN KEEP TRACK OF STUDENTS!)
            newCheckBox = new CheckBox();
            //Check if the schoolDay contains the subject
//            if (schoolDay.containsSubject(schoolLesson.getSubject())) {
//                //Check if the student has attendance, so the checkbox should be checked
//                checkSubjectForStudentNonAttendance(schoolDay, schoolLesson.getSubject(), newCheckBox);
//            } else {
//                //If the day doesn't contain the subject set the checkbox disabled
//                newCheckBox.setDisable(true);
//            }
            //Add the checkbox to the view
            horizontalCheckBoxPane.getChildren().add(newCheckBox);
            //Add the checkbox to our array, so we can keep track of it
            listOfCheckBoxes.add(newCheckBox);

//            addSubjectChangeListenerToCheckBox(newCheckBox, schoolDay, schoolLesson);
        }
    }

    /**
     * Creates 5 checkBoxes and puts them in the horizontalCheckBoxPane and
     * listOfCheckBoxes.
     */
    private void pouplateWeekHBoxWithCheckBoxes(Calendar startCal, ArrayList<ArrayList<SchoolClassSemesterLesson>> weekLessons) throws ParseException {
        Calendar endCal = Calendar.getInstance();
        //Get year of startDate
        endCal.set(Calendar.YEAR, startCal.get(1));
        //Get month of startDate
        endCal.set(Calendar.MONTH, startCal.get(2));
        //Make sure that first day of the week is monday!!!
        endCal.setFirstDayOfWeek(Calendar.MONDAY);
        //Set the endDate to be on friday the same week
        //TODO ALH: Research possibility to be more explicit here - Set DAY_OF_WEEK = Friday?
        endCal.set(Calendar.DAY_OF_MONTH, startCal.get(5) + 4);
        Date startDate = startCal.getTime();
        Date endDate = endCal.getTime();
        //For each schoolday in the schoolweek
        while (startDate.before(endDate)) {
            //Create a nice new checkbox (SO WE CAN KEEP TRACK OF STUDENTS!)
            CheckBox newCheckBox = new CheckBox();
            //Create list of schoolLessons this day
            int schoolDayWithLessons = startDate.getDay();
//            ArrayList<SchoolLesson> schoolDayLessons = weekLessons.get(schoolDayWithLessons);
            //Check if the student has nonAttendance this day
            checkDayForStudentNonAttendance(newCheckBox, startDate);
            //Add the checkbox to the view
            horizontalCheckBoxPane.getChildren().add(newCheckBox);
            //Add the checkbox to our array, so we can keep track of it
            listOfCheckBoxes.add(newCheckBox);
//            addWeekChangeListenerToCheckBox(newCheckBox, schoolDayLessons);
            //Increase the date we are putting in
            startDate.setDate(startDate.getDate() + 1);
        }
    }

    /**
     * Checks for student attendance
     *
     * @param schoolDay
     * @param newCheckBox
     */
    private void checkDayForStudentNonAttendance(CheckBox newCheckBox, Date day) {
        //Check if student has nonAttendance
        if (student.getNonAttendance().size() > 0) {
            //For each HashMap
            for (NonAttendance nonAttendance : student.getNonAttendance()) {
                //Check if the student was nonAttendant this schoolDay
                if (nonAttendance.getSchoolClassSemesterLesson().getDate().equals(day)) {
                    newCheckBox.setSelected(true);
                    break;
                }
            }

        }
    }

    /**
     * Checks for student attendance
     *
     * @param schoolDay
     * @param newCheckBox
     */
//private void checkSubjectForStudentNonAttendance(ESchoolSubject subject, CheckBox newCheckBox) {
//Check if student has nonAttendance
//        if (student.getNonAttendance().size() > 0) {
//            //For each nonAttendance
//            for (NonAttendance nonAttendance : student.getNonAttendance()) {
//                //Check if the student was nonAttendant this schoolWeek
//                if (nonAttendance.getWeekWithoutAttendance() == schoolWeek) {
//                    //Check if the student was nonAttendant this schoolDay
//                    if (nonAttendance.getDayWithoutAttendance() == schoolDay) {
//                        if (nonAttendance.getLessonWithoutAttendance().getSubject() == subject) {
//                            newCheckBox.setSelected(true);
//                        }
//                    }
//                }
//
//            }
//        }
//}
    /**
     * Add a changelistener to the checkbox when it is checked! (SO WE CAN
     * PUNISH STUDENTS!!!)
     *
     * @param newCheckBox
     * @param schoolDay
     */
    private void addWeekChangeListenerToCheckBox(CheckBox newCheckBox, ArrayList<SchoolLesson> lessonsThisDay) {
        newCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                //If the student was not attending class
                if (newCheckBox.isSelected()) {
                    //PUNISH STUDENT!
                    for (SchoolLesson lesson : lessonsThisDay) {
//                        NonAttendance newNonAttendance = new NonAttendance(, student.getID());
                    }
                    //If the student was infact attending school
                } else {
                    //Forgive the child <3
//                    for (SchoolLesson lesson : schoolDay.getLessons()) {
//                        NonAttendance newNonAttendance = new NonAttendance(schoolWeek, schoolDay, lesson);
//                        student.removeNonAttendance(newNonAttendance);
//                    }
                }
            }
        });
    }

    /**
     * Add a changelistener to the checkbox when it is checked! (SO WE CAN
     * PUNISH STUDENTS!!!)
     *
     * @param newCheckBox
     * @param schoolDay
     */
//private void addSubjectChangeListenerToCheckBox(CheckBox newCheckBox, SchoolLesson schoolLesson) {
//        newCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//                NonAttendance newNonAttendance = new NonAttendance(schoolWeek, schoolDay, schoolLesson);
//                //If the student was not attending class
//                if (newCheckBox.isSelected()) {
//                    //PUNISH STUDENT!
//                    student.addNonAttendance(newNonAttendance);
//                    //If the student was infact attending school
//                } else {
//                    //Forgive the child <3
//                    student.removeNonAttendance(newNonAttendance);
//                }
//            }
//        });
//}
    /**
     * Returns listOfCheckboxes.
     *
     * @return
     */
//    public ArrayList<CheckBox> getCheckBoxes() {
//        return listOfCheckBoxes;
//    }
}
