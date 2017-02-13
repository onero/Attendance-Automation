/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.model;

import attendanceautomation.be.MockData;
import attendanceautomation.be.SchoolClass;
import attendanceautomation.be.Student;
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

        addMockData();
    }

    /**
     * Add data to the model
     */
    private void addMockData() {
        //Add mockdata
        MockData mockData = new MockData();
        schoolClasses.add(mockData.getEasv2016A());
        for (int i = 0; i < schoolClasses.size(); i++) {
            students.addAll(schoolClasses.get(i).getStudents());
        }
        studentSearchList.addAll(students);
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

    public ObservableList<Student> getStudentSearchList() {
        return studentSearchList;
    }

    public ObservableList<Student> getStudents() {
        return students;
    }

    /**
     * Sort list on nonAttendance Descending
     */
    public void sortStudents() {
        Collections.sort(studentSearchList, (Student o1, Student o2)
                -> (o1.getNonAttendancePercentage().get() < o2.getNonAttendancePercentage().get()) ? 1 : 0);
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
