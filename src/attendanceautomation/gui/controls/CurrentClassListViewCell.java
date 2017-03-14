/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controls;

import attendanceautomation.be.Student;
import attendanceautomation.gui.views.currentClassView.controller.NameCurrentClassListViewController;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

/**
 *
 * @author Skovgaard
 */
public class CurrentClassListViewCell extends ListCell<Student> {

    private Node view;

    private NameCurrentClassListViewController controller;

    /**
     * Sets the controller.
     * @param controller
     */
    public void setController(NameCurrentClassListViewController controller) {
        this.controller = controller;
    }

    @Override
    protected void updateItem(Student item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            controller.setStudentInfo(item);
            setGraphic(view);
        }
    }

    /**
     * Sets the view.
     *
     * @param view
     */
    public void setView(Node view) {
        this.view = view;
    }

}
