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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SchoolClassModel {

    private static SchoolClassModel instance;

    private final ObservableList<SchoolClass> schoolClasses;
    private ObservableList<Student> students;

    public static SchoolClassModel getInstance() {
        if (instance == null) {
            instance = new SchoolClassModel();
        }
        return instance;
    }

    public SchoolClassModel() {
        schoolClasses = FXCollections.observableArrayList();
        students = FXCollections.observableArrayList();
        addData();
    }
    
    public void addData() {
        schoolClasses.clear();
        students.clear();
        MockData mockData = new MockData();
        schoolClasses.add(mockData.getEasv2016A());
        
        for (int i = 0; i < schoolClasses.size(); i++) {
            students.addAll(schoolClasses.get(i).getStudents());
        }
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

//    private void addStudents() {
//        for (int i = 0; i < schoolClasses.size(); i++) {
//            students.addAll(schoolClasses.get(i).getStudents());
//        }
//    }

    public ObservableList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> studentsFromSearch) {
        students.clear();
        students.addAll(studentsFromSearch);
    }
    
    
}
