/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controls;

import attendanceautomation.be.Student;
import attendanceautomation.gui.controller.NameStatisticsViewController;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

/**
 *
 * @author Rasmus
 */
public class StudentListViewCell extends ListCell<Student>{
        
    private Node view;
    private NameStatisticsViewController controller;
    
    public StudentListViewCell(){        
    }

    @Override
    protected void updateItem(Student item, boolean empty) {
        super.updateItem(item, empty);
        if(empty){
            setGraphic(null);
        }else{
            controller.setStudentInfo((attendanceautomation.be.Student) item);
            setGraphic(view);
        }
    }
    
    /**
     * Sets the controller.
     * Must be a NameStatisticsViewController.
     * @param controller 
     */
    public void setController(NameStatisticsViewController controller){
        this.controller = controller;
    }
    
    /**
     * Sets the view that should be displayed in the ListView.
     * @param view 
     */
    public void setView(Node view){
        this.view = view;
    }
}
