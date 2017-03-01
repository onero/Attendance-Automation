/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.model;

import attendanceautomation.be.SchoolClass;
import attendanceautomation.be.SchoolSemesterSubject;
import attendanceautomation.be.Student;
import attendanceautomation.be.enums.ESchoolSubject;
import attendanceautomation.be.enums.ETeacher;
import java.util.ArrayList;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SchoolClassModel {

    private static SchoolClassModel instance;

    private final ObservableList<SchoolClass> schoolClasses;
    private final ObservableList<Student> students;
    private final ObservableList<Student> studentSearchList;
    private String searchString;

    public static SchoolClassModel getInstance() {
        if (instance == null) {
            instance = new SchoolClassModel();
        }
        return instance;
    }

    public SchoolClassModel() {
        searchString = "";
        schoolClasses = FXCollections.observableArrayList();
        students = FXCollections.observableArrayList();
        studentSearchList = FXCollections.observableArrayList();
        addMockStudents();
    }

    private void addMockStudents() {
        SchoolClass CS2016A = new SchoolClass("CS2016A");
        schoolClasses.add(CS2016A);

        SchoolSemesterSubject SCO = new SchoolSemesterSubject(ESchoolSubject.SCO, ETeacher.PETER_STEGGER);
        SchoolSemesterSubject SDE = new SchoolSemesterSubject(ESchoolSubject.SDE, ETeacher.JEPPE_LED);
        SchoolSemesterSubject ITO = new SchoolSemesterSubject(ESchoolSubject.ITO, ETeacher.LARS_BILDE);
        SchoolSemesterSubject DBOS = new SchoolSemesterSubject(ESchoolSubject.DBOS, ETeacher.BENT_PEDERSEN);
        CS2016A.addSemesterSubjectToClass(SCO);
        CS2016A.addSemesterSubjectToClass(SDE);
        CS2016A.addSemesterSubjectToClass(ITO);
        CS2016A.addSemesterSubjectToClass(DBOS);

        Student adam = new Student("Adam", "Hansen");
        Student rasmus = new Student("Rasmus", "Lindved");
        students.add(adam);
        students.add(rasmus);
    }

    /**
     * Add parsed SchoolClass to the observable array
     *
     * @param newSchoolClass
     */
    public void addSchoolClass(SchoolClass newSchoolClass) {
        schoolClasses.add(newSchoolClass);
    }

    /**
     * Remove selected schoolclass
     *
     * @param schoolClassToRemove
     */
    public void removeSchoolClass(SchoolClass schoolClassToRemove) {
        schoolClasses.remove(schoolClassToRemove);
    }

    /**
     *
     * @return school classes
     */
    public ObservableList<SchoolClass> getSchoolClasses() {
        return schoolClasses;
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
