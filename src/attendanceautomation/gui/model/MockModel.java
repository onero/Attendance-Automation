/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.model;

import attendanceautomation.be.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Rasmus
 */
public class MockModel {
    
    private final ObservableList<Student> students;
    
    public MockModel(){
        students = FXCollections.observableArrayList();
        createMockStudents();
    }
    
    /**
     * Fills the observableArray with mockData so RKL can set up the listView.
     * TODO: Put real data in here.
     */
    private void createMockStudents() {
        for(int i = 0; i < 50; i++){
            students.add(new Student("Daisy", "Duck", 88888888, "some@email.com"));
        }
    }
    
    /**
     * Gets the list with students.
     * @return 
     */
    public ObservableList<Student> getObservableStudents(){
        return students;
    }
}
