/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.components.studentAttendanceInformation;

import attendanceautomation.be.NonAttendance;
import attendanceautomation.be.SchoolClass;
import attendanceautomation.be.SchoolClassSemesterLesson;
import attendanceautomation.be.SchoolSemesterSubject;
import attendanceautomation.be.Student;
import attendanceautomation.gui.model.SchoolClassModel;
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

    private final SchoolClassModel schoolClassModel;

    private final ArrayList<CheckBox> listOfCheckBoxes;

    private final ArrayList<SchoolClassSemesterLesson> allSemesterLessons;
    private ArrayList<SchoolClassSemesterLesson> lessonsThisDay;

    private Student student;

    private final SchoolClass schoolClass;

    public WeekCheckBoxViewController() {
        listOfCheckBoxes = new ArrayList<>();
        schoolClass = SchoolClassModel.getInstance().getCurrentSchoolClass();
        schoolClassModel = SchoolClassModel.getInstance();
        allSemesterLessons = new ArrayList<>(schoolClass.getSemesterLessons());
    }

    /**
     * Set data for the student and the week
     *
     * @param newStudent
     * @param startDate
     */
    public void setWeekData(Student newStudent, Calendar startDate) {
        student = newStudent;
        try {
            populateWeekHBoxWithCheckBoxes(startDate);
        } catch (ParseException ex) {
            Logger.getLogger(WeekCheckBoxViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Set data for the subject and the week
     *
     * @param newStudent
     * @param startCal
     * @param semesterSubject
     */
    public void setSubjectWeekData(Student newStudent, Calendar startCal, SchoolSemesterSubject semesterSubject) {
        student = newStudent;
        pouplateLessonHBoxWithCheckBoxes(startCal, semesterSubject);
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
    private void pouplateLessonHBoxWithCheckBoxes(Calendar startCal, SchoolSemesterSubject subject) {
        Calendar endCal = setLastDayOfCurrentWeek(startCal);
        Date startDate = startCal.getTime();
        Date endDate = endCal.getTime();

        createCheckBoxesForTheSubjectForEachDayInCurrentWeek(startDate, endDate, subject);
    }

    /**
     * Create checkboxces for each day current subject
     *
     * @param startDate
     * @param endDate
     * @param subject
     */
    private void createCheckBoxesForTheSubjectForEachDayInCurrentWeek(Date startDate, Date endDate, SchoolSemesterSubject subject) {
        //For each schoolday in the schoolweek
        while (startDate.before(endDate)) {
            getAllLessonOnThisDay(startDate);
            //Create a nice new checkbox (SO WE CAN KEEP TRACK OF STUDENTS!)
            CheckBox newCheckBox = new CheckBox();
            //Start with disabling the checkbox
            newCheckBox.setDisable(true);
            //Set the checkbox visible again if the day contains the subject
            checkIfLessonIsOnThisDay(subject, newCheckBox, startDate);
            //Add the checkbox to the view
            horizontalCheckBoxPane.getChildren().add(newCheckBox);
            //Add the checkbox to our array, so we can keep track of it
            listOfCheckBoxes.add(newCheckBox);
            //Increase the date we are putting in
            startDate.setDate(startDate.getDate() + 1);
        }
    }

    /**
     * For each lesson on this day, check if the lesson has this current subject
     *
     * @param subject
     * @param newCheckBox
     * @param startDate
     */
    private void checkIfLessonIsOnThisDay(SchoolSemesterSubject subject, CheckBox newCheckBox, Date startDate) {
        for (SchoolClassSemesterLesson lesson : lessonsThisDay) {
            //If the lesson is in this subject
            if (lesson.getSemesterSubject().getID() == subject.getID()) {
                //Subject is in this day, so the checkbox should be visible
                newCheckBox.setDisable(false);
                //Check if the student has nonAttendance this day
                checkSubjectInDayForStudentNonAttendance(newCheckBox, startDate, subject);
                addSubjectChangeListenerToCheckBox(newCheckBox, lesson);
                break;
            }
        }
    }

    /**
     * Updates the arraylist lessonsThisDay with the lessons for the day
     *
     * @param startDate
     */
    private void getAllLessonOnThisDay(Date startDate) {
        lessonsThisDay = new ArrayList<>();
        //Find all the lessons on this day
        for (SchoolClassSemesterLesson allSemesterLesson : allSemesterLessons) {
            if (allSemesterLesson.getDate().getDay() == startDate.getDay()) {
                lessonsThisDay.add(allSemesterLesson);
            }
        }
    }

    /**
     * Sets the Calendar data to be the last day of the current week
     *
     * @param startCal
     * @return
     */
    private Calendar setLastDayOfCurrentWeek(Calendar startCal) {
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
        return endCal;
    }

    /**
     * Creates checkboxes for each day in the week
     */
    private void populateWeekHBoxWithCheckBoxes(Calendar startCal) throws ParseException {
        Calendar endCal = setLastDayOfCurrentWeek(startCal);
        Date startDate = startCal.getTime();
        Date endDate = endCal.getTime();

        addACheckBoxForEachDayInCurrentWeek(startDate, endDate);
    }

    /**
     * For each day in the current week add a CheckBox
     *
     * @param startDate
     * @param endDate
     */
    private void addACheckBoxForEachDayInCurrentWeek(Date startDate, Date endDate) {
        //For each schoolday in the schoolweek
        while (startDate.before(endDate)) {
            getAllLessonOnThisDay(startDate);
            //Create a nice new checkbox (SO WE CAN KEEP TRACK OF STUDENTS!)
            CheckBox newCheckBox = new CheckBox();
            //Check if the student has nonAttendance this day
            checkDayForStudentNonAttendance(newCheckBox, startDate);
            //Add the checkbox to the view
            horizontalCheckBoxPane.getChildren().add(newCheckBox);
            //Add the checkbox to our array, so we can keep track of it
            listOfCheckBoxes.add(newCheckBox);
            addWeekChangeListenerToCheckBox(newCheckBox, lessonsThisDay);
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
                if (nonAttendance.getSchoolClassSemesterLesson().getDate().getDate() == day.getDate()) {
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
    private void checkSubjectInDayForStudentNonAttendance(CheckBox newCheckBox, Date day, SchoolSemesterSubject subject) {
        //Check if student has nonAttendance
        if (student.getNonAttendance().size() > 0) {
            //For each HashMap
            for (NonAttendance nonAttendance : student.getNonAttendance()) {
                //Check if the student was nonAttendant this schoolDay
                if (nonAttendance.getSchoolClassSemesterLesson().getDate().getDate() == day.getDate()
                        && nonAttendance.getSchoolClassSemesterLesson().getSemesterSubject().getID() == subject.getID()) {
                    newCheckBox.setSelected(true);
                    break;
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
    private void addWeekChangeListenerToCheckBox(CheckBox newCheckBox, ArrayList<SchoolClassSemesterLesson> lessonsThisDay) {
        newCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                //If the student was not attending class
                if (newCheckBox.isSelected()) {
                    //PUNISH STUDENT!
                    for (SchoolClassSemesterLesson lesson : lessonsThisDay) {
                        NonAttendance newNonAttendance = new NonAttendance(lesson, student.getID());
                        student.addNonAttendance(newNonAttendance);
                        schoolClassModel.addNewNonAttendance(newNonAttendance);
                    } //If the student was infact attending school
                } else {
                    //Forgive the child <3
                    for (SchoolClassSemesterLesson lesson : lessonsThisDay) {
                        NonAttendance nonAttendanceToRemove = new NonAttendance(lesson, student.getID());
                        student.removeNonAttendance(nonAttendanceToRemove);
                        schoolClassModel.removeNonAttendance(nonAttendanceToRemove);
                    }
                }
            }
        }
        );
    }

    /**
     * Add a changelistener to the checkbox when it is checked! (SO WE CAN
     * PUNISH STUDENTS!!!)
     *
     * @param newCheckBox
     * @param schoolDay
     */
    private void addSubjectChangeListenerToCheckBox(CheckBox newCheckBox, SchoolClassSemesterLesson semesterlesson) {
        newCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                NonAttendance newNonAttendance = new NonAttendance(semesterlesson, student.getID());
                //If the student was not attending class
                if (newCheckBox.isSelected()) {
                    //PUNISH STUDENT!
                    student.addNonAttendance(newNonAttendance);
                    schoolClassModel.addNewNonAttendance(newNonAttendance);
                    //If the student was infact attending school
                } else {
                    //Forgive the child <3
                    student.removeNonAttendance(newNonAttendance);
                    schoolClassModel.removeNonAttendance(newNonAttendance);
                }
            }
        });
    }
}
