/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller;

import attendanceautomation.be.enums.EFXMLNames;
import attendanceautomation.gui.controller.components.ComponentsHolderViewController;
import attendanceautomation.gui.controller.components.WhiteComponentHolderController;
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
    private Node LOGIN_VIEW;

    private Node SEARCH_BAR;
    private Node ComboBox;
    private Node SEARCH_COMBO_HOLDER;
    private Node WHITE_COMPONENT_HOLDER_VIEW;
    private Node EMPTY_TOP_BAR;

    private WhiteComponentHolderController whiteComponentHolderController;

    public static RootViewController getInstance() {
        return instance;
    }

    public RootViewController() {
        try {
            MAIN_VIEW = createMainView();
            ALL_STUDENTS_VIEW = createAllStudents();
            DETAILED_STUDENT_VIEW = createDetailedStudentView();
            LOGIN_VIEW = createLoginView();

            SEARCH_BAR = createSearchBarNode();
            ComboBox = createComboBox();
            SEARCH_COMBO_HOLDER = createSearchComboHolder();
            WHITE_COMPONENT_HOLDER_VIEW = createWhiteComponentHolderView();
            EMPTY_TOP_BAR = createEmptyTopBar();
        } catch (IOException ex) {
            System.out.println("MainView not loaded! " + ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
//        borderPane.setCenter(WHITE_COMPONENT_HOLDER_VIEW);
        borderPane.setCenter(LOGIN_VIEW);
    }

    /**
     * Creates the node containing MainView.
     *
     * @return
     * @throws IOException
     */
    private Node createMainView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.MAIN_VIEW.toString()));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.ALL_STUDENTS_VIEW.toString()));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.DETAILED_STUDENT_VIEW.toString()));
        Node node = loader.load();
        return node;
    }
    
    /**
     * Creates the LoginView
     * @return
     * @throws IOException 
     */
    private Node createLoginView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.LOGIN_VIEW.toString()));
        Node node = loader.load();
        return node;
    }

    /**
     * In the WhiteComponentController. Sets the center and the topView.
     *
     * @param event
     */
    @FXML
    private void handleAllStudentsButton(ActionEvent event) {
        //Recreate allStudentsView to ensure updated view with NonAttendance
        try {
            createAllStudents();
        } catch (IOException ex) {
            System.out.println("Couldn't recreate allStudentsView");
        }
        whiteComponentHolderController.setBorderPaneCenter(ALL_STUDENTS_VIEW);
        whiteComponentHolderController.setBoderPaneTop(SEARCH_COMBO_HOLDER);
    }

    /**
     * In the WhiteComponentController. Sets the center and the topView.
     *
     * @param event
     */
    @FXML
    private void handleStartView(ActionEvent event) {
        whiteComponentHolderController.setBorderPaneCenter(MAIN_VIEW);
        whiteComponentHolderController.setBoderPaneTop(SEARCH_COMBO_HOLDER);
    }

    /**
     * Sets the node to be the detailed student view
     *
     * @param selectedStudent
     */
    public void selectDetailedStudentView() {
        whiteComponentHolderController.setBorderPaneCenter(DETAILED_STUDENT_VIEW);
        whiteComponentHolderController.setBoderPaneTop(EMPTY_TOP_BAR);
    }

    /**
     * Creates the componentHolder that holds the center and topView of mainView
     * and allStudentsView.
     *
     * @return
     * @throws IOException
     */
    private Node createWhiteComponentHolderView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.WHITE_COMPONENT_HOLDER.toString()));
        Node node = loader.load();
        whiteComponentHolderController = loader.getController();
        whiteComponentHolderController.setBoderPaneTop(SEARCH_COMBO_HOLDER);
        whiteComponentHolderController.setBorderPaneCenter(MAIN_VIEW);
        return node;
    }

    /**
     * Creates the searchBar.
     *
     * @return
     * @throws IOException
     */
    private Node createSearchBarNode() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.SEARCH_VIEW.toString()));
        Node node = loader.load();
        return node;
    }

    /**
     * Creates the comboBox.
     *
     * @return
     * @throws IOException
     */
    private Node createComboBox() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.MONTH_COMBO_BOX_VIEW.toString()));
        Node node = loader.load();
        return node;
    }

    /**
     * Creates the holder for the searchBar and the comboBox.
     *
     * @return
     * @throws IOException
     */
    private Node createSearchComboHolder() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.COMPONENTS_HOLDER_VIEW.toString()));
        Node node = loader.load();
        ComponentsHolderViewController controller = loader.getController();
        controller.setBorderPaneLeft(SEARCH_BAR);
        controller.setBorderPaneRight(ComboBox);
        return node;
    }

    /**
     * Creates the emptyTopBar.
     *
     * @return
     * @throws IOException
     */
    private Node createEmptyTopBar() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.COMPONENTS_HOLDER_VIEW.toString()));
        Node node = loader.load();
        return node;
    }

}
