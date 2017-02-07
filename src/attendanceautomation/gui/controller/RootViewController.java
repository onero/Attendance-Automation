/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller;

import attendanceautomation.AttendanceAutomationMain;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author gta1
 */
public class RootViewController implements Initializable {

    @FXML
    private BorderPane borderPane;

    private static RootViewController instance;

    private Node MAIN_VIEW;
    private Node ALL_STUDENTS_VIEW;
    private Node DETAILED_STUDENT_VIEW;

    public static RootViewController getInstance() {
        return instance;
    }

    public RootViewController() {
        try {
            MAIN_VIEW = createMainView();
            ALL_STUDENTS_VIEW = createAllStudents();
            DETAILED_STUDENT_VIEW = createDetailedStudentView();
        } catch (IOException ex) {
            System.out.println("MainView not loaded! " + ex.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        setCenter(MAIN_VIEW);
    }

    /**
     * Creates the node containing MainView.
     *
     * @return
     * @throws IOException
     */
    private Node createMainView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(AttendanceAutomationMain.MAIN_VIEW_STRING));
        Node node = loader.load();
        return node;
    }

    /**
     * Creates the node containing AllStudentsView.
     *
     * @return
     * @throws IOException
     */
    private Node createAllStudents() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(AttendanceAutomationMain.ALL_STUDENTS_STRING));
        Node node = loader.load();
        return node;
    }

    /**
     * Create the StudentInformationTopView
     *
     * @return
     * @throws IOException
     */
    private Node createDetailedStudentView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(AttendanceAutomationMain.DETAILED_STUDENT_STRING));
        Node node = loader.load();
        return node;
    }

    /**
     * Sets the All_STUDENTS_VIEW as the center node
     *
     * @param event
     */
    @FXML
    private void handleAllStudentsButton(ActionEvent event) {
        setCenter(ALL_STUDENTS_VIEW);
    }

    /**
     * Sets the MAIN_VIEW as the center view
     *
     * @param event
     */
    @FXML
    private void handleStartView(ActionEvent event) {
        setCenter(MAIN_VIEW);
    }

    /**
     * Sets the center node in the borderpane to the parsed view
     *
     * @param newView
     */
    private void setCenter(Node newView) {
        borderPane.setCenter(newView);
    }

    /**
     * Sets the node to be the detailed student view
     *
     * @param selectedStudent
     */
    public void selectDetailedStudentView() {
        setCenter(DETAILED_STUDENT_VIEW);
    }

}
