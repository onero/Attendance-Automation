/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be;

import java.util.ArrayList;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SchoolClass {

    private final String schoolClassName;

    private final ObservableList<Student> students;

    private final ArrayList<SchoolWeek> schoolWeeks;

    public SchoolClass(String name, ArrayList<SchoolWeek> schoolWeeks) {
        this.schoolWeeks = schoolWeeks;
        schoolClassName = name;
        students = FXCollections.observableArrayList();
    }

    /**
     *
     * @return school class name
     */
    public String getSchoolClassName() {
        return schoolClassName;
    }

    /**
     *
     * @return school weeks
     */
    public ArrayList<SchoolWeek> getSchoolWeeks() {
        return schoolWeeks;
    }

    /**
     * Add parsed student to the array
     *
     * @param newStudent
     */
    public void addStudentToClass(Student newStudent) {
        students.add(newStudent);
    }

    /**
     * Add all students to class
     *
     * @param allStudents
     */
    public void addAllStudents(ArrayList<Student> allStudents) {
        students.addAll(allStudents);
    }

    /**
     * Removed parsed student
     *
     * @param studentToRemove
     */
    public void removeStudent(Student studentToRemove) {
        students.remove(studentToRemove);
    }

    /**
     *
     * @return name of schoolclass
     */
    public String getName() {
        return schoolClassName;
    }

    /**
     *
     * @return students
     */
    public ObservableList<Student> getStudents() {
        sortStudents();
        return students;
    }

    /**
     * Sort the students after attendance
     */
    private void sortStudents() {
        Collections.sort(students, (Student s1, Student s2)
                -> Integer.toString(s2.getNonAttendancePercentage()).compareTo(
                        Integer.toString(s1.getNonAttendancePercentage())));
    }

}
