/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.sharedComponents.studentAttendanceInformation.controller;

import attendanceautomation.be.NonAttendance;
import attendanceautomation.be.SchoolClass;
import attendanceautomation.be.SchoolClassSemesterLesson;
import attendanceautomation.be.SchoolSemesterSubject;
import attendanceautomation.be.Student;
import attendanceautomation.gui.model.SchoolClassModel;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
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

    private boolean isStudentLogin;

    private Student student;

    private final SchoolClass schoolClass;

    public WeekCheckBoxViewController() {
        listOfCheckBoxes = new ArrayList<>();
        schoolClass = SchoolClassModel.getInstance().getCurrentSchoolClass();
        schoolClassModel = SchoolClassModel.getInstance();
        allSemesterLessons = new ArrayList<>(schoolClass.getSemesterLessons());
        isStudentLogin = false;
    }

    /**
     * Set data for the student and the week
     *
     * @param newStudent
     * @param week
     */
    public void setWeekData(Student newStudent, List<Integer> week) {
        student = newStudent;
        try {
            populateWeekHBoxWithCheckBoxes(week);
        } catch (ParseException ex) {
            Logger.getLogger(WeekCheckBoxViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Set data for the subject and the week
     *
     * @param newStudent
     * @param week
     * @param semesterSubject
     */
    public void setSubjectWeekData(Student newStudent, List<Integer> week, SchoolSemesterSubject semesterSubject) {
        student = newStudent;
        pouplateLessonHBoxWithCheckBoxes(week, semesterSubject);
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
    private void pouplateLessonHBoxWithCheckBoxes(List<Integer> week, SchoolSemesterSubject subject) {
        createCheckBoxesForTheSubjectForEachDayInCurrentWeek(week, subject);
    }

    /**
     * Create checkboxces for each day current subject
     *
     * @param startDate
     * @param endDate
     * @param subject
     */
    private void createCheckBoxesForTheSubjectForEachDayInCurrentWeek(List<Integer> week, SchoolSemesterSubject subject) {
        //For each schoolday in the schoolweek
        for (int day : week) {
            getAllLessonOnThisDay(day);
            //Create a nice new checkbox (SO WE CAN KEEP TRACK OF STUDENTS!)
            CheckBox newCheckBox = new CheckBox();
            //Start with disabling the checkbox
            newCheckBox.setDisable(true);
            if (!lessonsThisDay.isEmpty()) {
                //Set the checkbox visible again if the day contains the subject
                checkIfLessonIsOnThisDay(subject, newCheckBox, day);
                //Checks if the user is a student, if true disables the checkbox again.
                checkIsStudent(newCheckBox);
            }
            //Add the checkbox to the view
            horizontalCheckBoxPane.getChildren().add(newCheckBox);
            //Add the checkbox to our array, so we can keep track of it
            listOfCheckBoxes.add(newCheckBox);
            //Increase the date we are putting in

        }
        isStudentLogin = false;
    }

    /**
     * For each lesson on this day, check if the lesson has this current subject
     *
     * @param subject
     * @param newCheckBox
     * @param startDate
     */
    private void checkIfLessonIsOnThisDay(SchoolSemesterSubject subject, CheckBox newCheckBox, int startDate) {
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
    private void getAllLessonOnThisDay(int startDate) {
        lessonsThisDay = new ArrayList<>();
        //Find all the lessons on this day
        for (SchoolClassSemesterLesson allSemesterLesson : allSemesterLessons) {
            if (allSemesterLesson.getDate().getDate() == startDate) {
                lessonsThisDay.add(allSemesterLesson);
            }
        }
    }

    /**
     * Creates checkboxes for each day in the week
     */
    private void populateWeekHBoxWithCheckBoxes(List<Integer> week) throws ParseException {
        addACheckBoxForEachDayInCurrentWeek(week);
    }

    public void setIsStudentLogin() {
        isStudentLogin = true;
    }

    /**
     * For each day in the current week add a CheckBox
     *
     * @param week
     * @param endDate
     */
    private void addACheckBoxForEachDayInCurrentWeek(List<Integer> week) {
        //For each schoolday in the schoolweek
        for (int day : week) {
            getAllLessonOnThisDay(day);
            //Create a nice new checkbox (SO WE CAN KEEP TRACK OF STUDENTS!)
            CheckBox newCheckBox = new CheckBox();
            //Add the checkbox to the view
            horizontalCheckBoxPane.getChildren().add(newCheckBox);
            if (!lessonsThisDay.isEmpty()) {
                //Check if the student has nonAttendance this day
                checkDayForStudentNonAttendance(newCheckBox, day);
                //Add the checkbox to our array, so we can keep track of it
                listOfCheckBoxes.add(newCheckBox);
                addWeekChangeListenerToCheckBox(newCheckBox, lessonsThisDay);
            } else {
                newCheckBox.setDisable(true);
            }
        }
    }

    private void checkIsStudent(CheckBox newCheckBox) {
        //Increase the date we are putting in
        if (isStudentLogin) {
            newCheckBox.setDisable(true);
        }
    }

    /**
     * Checks for student attendance
     *
     * @param schoolDay
     * @param newCheckBox
     */
    private void checkDayForStudentNonAttendance(CheckBox newCheckBox, int day) {
        //Check if student has nonAttendance
        if (student.getNonAttendance().size() > 0) {
            //For each HashMap
            for (NonAttendance nonAttendance : student.getNonAttendance()) {
                //Check if the student was nonAttendant this schoolDay
                if (nonAttendance.getSchoolClassSemesterLesson().getDate().getDate() == day) {
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
    private void checkSubjectInDayForStudentNonAttendance(CheckBox newCheckBox, int day, SchoolSemesterSubject subject) {
        //Check if student has nonAttendance
        if (student.getNonAttendance().size() > 0) {
            //For each HashMap
            for (NonAttendance nonAttendance : student.getNonAttendance()) {
                //Check if the student was nonAttendant this schoolDay
                if (nonAttendance.getSchoolClassSemesterLesson().getDate().getDate() == day
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
