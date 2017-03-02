/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SchoolClass {

    private final int ID;

    private final String schoolClassName;

    private final ArrayList<Student> students;

    private final ArrayList<SchoolSemesterSubject> semesterSubjects;

    public SchoolClass(int ID, String name) {
        this.ID = ID;
        schoolClassName = name;
        students = new ArrayList<>();
        semesterSubjects = new ArrayList<>();
    }

    public int getID() {
        return ID;
    }

    /**
     *
     * @return school class name
     */
    public String getSchoolClassName() {
        return schoolClassName;
    }

    /**
     * Add the suject with teacher to the array
     *
     * @param lesson
     */
    public void addSemesterSubjectToClass(SchoolSemesterSubject lesson) {
        semesterSubjects.add(lesson);
    }

    /**
     * Add all subjects to SchoolClass
     *
     * @param subjects
     */
    public void addAllSemesterSubjectsToClass(List<SchoolSemesterSubject> subjects) {
        semesterSubjects.addAll(subjects);
    }

    /**
     *
     * @return lessons for the class
     */
    public ArrayList<SchoolSemesterSubject> getSemesterSubjects() {
        return semesterSubjects;
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
    public void addAllStudents(List<Student> allStudents) {
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
     * @return students
     */
    public ArrayList<Student> getStudents() {
        sortStudents();
        return students;
    }

    /**
     * Sort the students after attendance
     */
    private void sortStudents() {
        Collections.sort(students, (Student s1, Student s2)
                -> Double.toString(s2.getNonAttendancePercentage().get()).compareTo(
                        Double.toString(s1.getNonAttendancePercentage().get())));
    }

}
