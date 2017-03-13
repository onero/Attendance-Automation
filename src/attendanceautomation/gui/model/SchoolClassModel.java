/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.model;

import attendanceautomation.be.NonAttendance;
import attendanceautomation.be.SchoolClass;
import attendanceautomation.be.Student;
import attendanceautomation.bll.SchoolClassManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SchoolClassModel {

    private static SchoolClassModel instance;

    private final SchoolClassManager schoolClassManager;

    private Student currentStudent;

    private SchoolClass currentSchoolClass;
    private final List<Student> studentsFromDB;
    private final ObservableList<Student> students;
    private String searchString;

    public static SchoolClassModel getInstance() {
        if (instance == null) {
            instance = new SchoolClassModel();
        }
        return instance;
    }

    private SchoolClassModel() {
        schoolClassManager = SchoolClassManager.getInstance();
        searchString = "";
        students = FXCollections.observableArrayList();
        studentsFromDB = new ArrayList<>(students);
    }

    /**
     * Load newest data from DB
     */
    public void loadDataFromDB() {
        //TODO ALH: Load standard class for teacher
        students.clear();
        studentsFromDB.clear();
        currentSchoolClass = schoolClassManager.getAllSchoolClassDataForSpecificSchoolClass(1);
        studentsFromDB.addAll(currentSchoolClass.getStudents());
        students.addAll(studentsFromDB);
        sortStudentsOnAttendance();
    }

    public void updateStudentInfo() {
        students.clear();
        students.addAll(schoolClassManager.getStudentsWithDataFromSchoolClass(currentSchoolClass.getID()));
    }

    /**
     * Add new studentNonAttendance to DB
     *
     * @param newNonAttendance
     */
    public void addNewNonAttendance(NonAttendance newNonAttendance) {
        schoolClassManager.addNewAttendance(newNonAttendance);
    }

    /**
     * Remove studentNonAttendance from DB
     *
     * @param attendanceToRemove
     */
    public void removeNonAttendance(NonAttendance attendanceToRemove) {
        schoolClassManager.removeNonAttendance(attendanceToRemove);
    }

    /**
     * Add parsed SchoolClass to the observable array
     *
     * @param newSchoolClass
     */
    public void setCurrentSchoolClass(SchoolClass newSchoolClass) {
        currentSchoolClass = newSchoolClass;
    }

    public SchoolClass getCurrentSchoolClass() {
        return currentSchoolClass;
    }

    /**
     *
     * @return students from search
     */
    public List<Student> getStudentsFromDB() {
        return studentsFromDB;
    }

    public ObservableList<Student> getStudents() {
        return students;
    }

    /**
     * Sort list on nonAttendance Descending
     */
    public void sortStudentsOnAttendance() {
        Collections.sort(students, (Student o1, Student o2)
                -> (o1.getNonAttendancePercentage().get() < o2.getNonAttendancePercentage().get()) ? 1 : 0);
    }

    /**
     * Sort list on FullName Ascending
     */
    public void sortStudentsOnName() {
        Collections.sort(students, (Student o1, Student o2)
                -> (o1.getFullName().compareTo(o2.getFullName())));
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchString() {
        return searchString;
    }

    public void updateStudentsFromSearch(Student student) {
        students.add(student);
    }

    public void loadStudentData(String studentEmail) {
        currentSchoolClass = schoolClassManager.getSchoolClassFromStudentEmail(studentEmail);
        currentStudent = schoolClassManager.getStudentByEmail(studentEmail);
    }

    /**
     * Check if user is in DB
     *
     * @param userEmail
     * @return
     */
    public boolean isUserInDB(String userEmail) {
        return schoolClassManager.isUserInDB(userEmail);
    }

    /**
     * Get the current student
     *
     * @return
     */
    public Student getCurrentStudent() {
        return currentStudent;
    }

}
