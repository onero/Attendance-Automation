/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controls;

import attendanceautomation.be.Student;
import attendanceautomation.gui.controller.components.studentAttendanceInformation.StudentAttendanceInformationViewController;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

/**
 *
 * @author Rasmus
 */
public class AllStudentsNonAttendanceCell extends ListCell<Student> {

    private Node view;

    private StudentAttendanceInformationViewController controller;

    public AllStudentsNonAttendanceCell() {
    }

    @Override
    protected void updateItem(Student student, boolean empty) {
        super.updateItem(student, empty);
        if (empty) {
            setGraphic(null);
        } else {
            controller.setStudentInfo(student);
            setGraphic(view);
        }
    }

    /**
     * Sets the controller.
     *
     * @param controller
     */
    public void setController(StudentAttendanceInformationViewController controller) {
        this.controller = controller;
    }

    /**
     * Sets the view displayed in the ListView.
     *
     * @param view
     */
    public void setView(Node view) {
        this.view = view;
    }
}
