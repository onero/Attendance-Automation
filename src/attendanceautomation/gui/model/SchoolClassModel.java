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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SchoolClassModel {

    private static SchoolClassModel instance;

    private final SchoolClassManager schoolClassManager;

    private SchoolClass currentSchoolClass;
    private final ObservableList<Student> students;
    private final ObservableList<Student> studentSearchList;
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
        studentSearchList = FXCollections.observableArrayList();
        loadDataFromDB();
    }

    /**
     * Load newest data from DB
     */
    private void loadDataFromDB() {
        currentSchoolClass = schoolClassManager.getAllSchoolClassDataForSpecificSchoolClass(1);
        students.addAll(currentSchoolClass.getStudents());
    }

    public void updateStudentInfo() {
        students.clear();
        students.addAll(schoolClassManager.getUpdatedStudents());
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
    public ObservableList<Student> getStudentSearchList() {
        sortStudentsOnAttendance();
        return studentSearchList;
    }

    public ObservableList<Student> getStudents() {
        sortStudentsOnName();
        return students;
    }

    /**
     * Sort list on nonAttendance Descending
     */
    public void sortStudentsOnAttendance() {
        Collections.sort(studentSearchList, (Student o1, Student o2)
                -> (o1.getNonAttendancePercentage().get() < o2.getNonAttendancePercentage().get()) ? 1 : 0);
    }

    /**
     * Sort list on FullName Ascending
     */
    public void sortStudentsOnName() {
        Collections.sort(students, (Student o1, Student o2)
                -> (o1.getFullName().compareTo(o2.getFullName())));
    }

    public void setStudents(ArrayList<Student> studentsFromSearch) {
        students.clear();
        students.addAll(studentsFromSearch);
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchString() {
        return searchString;
    }

    public void updateStudentsFromSearch(Student student) {
        studentSearchList.add(student);
    }

}
