/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.rootView.controller;

import attendanceautomation.be.enums.EFXMLNames;
import attendanceautomation.gui.model.SchoolClassModel;
import attendanceautomation.gui.views.sharedComponents.componentsHolder.controller.ComponentsHolderViewController;
import attendanceautomation.gui.views.sharedComponents.searchView.controller.SearchViewController;
import attendanceautomation.gui.views.sharedComponents.whiteComponentHolder.controller.WhiteComponentHolderController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
    private Node LOGOUT_BUTTON;

    private Node SEARCH_BAR;
    private Node ComboBox;
    private Node SEARCH_COMBO_HOLDER;
    private Node WHITE_COMPONENT_HOLDER_VIEW;
    private Node EMPTY_TOP_BAR;

    private WhiteComponentHolderController whiteComponentHolderController;
    private SearchViewController searchViewController;

    public static RootViewController getInstance() {
        return instance;
    }
    @FXML
    private Button startButton;
    @FXML
    private Button allStudentsButton;

    public RootViewController() {
        try {
            MAIN_VIEW = createMainView();
            ALL_STUDENTS_VIEW = createAllStudents();
            DETAILED_STUDENT_VIEW = createDetailedStudentView();
            LOGIN_VIEW = createLoginView();
            LOGOUT_BUTTON = createLogoutView();

            SEARCH_BAR = createSearchBarNode();
//            ComboBox = createComboBox();
            SEARCH_COMBO_HOLDER = createSearchComboHolder();
            EMPTY_TOP_BAR = createEmptyTopBar();
            WHITE_COMPONENT_HOLDER_VIEW = createWhiteComponentHolderView();
        } catch (IOException ex) {
            System.out.println("MainView not loaded! " + ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        borderPane.setCenter(WHITE_COMPONENT_HOLDER_VIEW);
        ShowBottomButtons(false);
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
     * Creates the LoginView and sets it's RootViewController.
     *
     * @return
     * @throws IOException
     */
    private Node createLoginView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.LOGIN_VIEW.toString()));
        Node node = loader.load();
        return node;
    }

    private Node createLogoutView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.LOGOUT_BUTTON.toString()));
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
        SchoolClassModel.getInstance().sortStudentsOnName();
        try {
//            SchoolClassModel.getInstance().updateStudentInfo();
            createAllStudents();
        } catch (IOException ex) {
            System.out.println("Couldn't recreate allStudentsView");
            System.out.println(ex);
        }
        whiteComponentHolderController.setBorderPaneCenter(ALL_STUDENTS_VIEW);
        searchViewController.showSearchBar(true);
    }

    /**
     * In the WhiteComponentController. Sets the center and the topView.
     *
     * @param event
     */
    @FXML
    public void handleStartView(ActionEvent event) {
        whiteComponentHolderController.setBorderPaneCenter(MAIN_VIEW);
        searchViewController.showSearchBar(true);
    }

    /**
     * Sets the node to be the detailed student view
     *
     * selectedStudent
     */
    public void handleDetailedStudentView() {
        whiteComponentHolderController.setBorderPaneCenter(DETAILED_STUDENT_VIEW);
        searchViewController.showSearchBar(false);
    }

    /**
     * Returns the user to the login page.
     */
    public void handleLogout() {
        whiteComponentHolderController.setBorderPaneCenter(LOGIN_VIEW);
        whiteComponentHolderController.setBorderPaneTop(EMPTY_TOP_BAR);
        ShowBottomButtons(false);

    }

    /**
     * Sends the user to the right screen depending on which user it is. If it's
     * a teacher he gets sent to the start view if it's a student he will be
     * sent to the detailed student view.
     */
    public void handleLogin() {
        whiteComponentHolderController.setBorderPaneCenter(MAIN_VIEW);
        whiteComponentHolderController.setBorderPaneTop(SEARCH_COMBO_HOLDER);
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
        whiteComponentHolderController.setBorderPaneTop(EMPTY_TOP_BAR);
        whiteComponentHolderController.setBorderPaneCenter(LOGIN_VIEW);
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
        searchViewController = loader.getController();
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
        controller.setBorderPaneRight(LOGOUT_BUTTON);
        //Removed until need be!
//        controller.setBorderPaneRight(ComboBox);
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

    /**
     * Makes it so that you can see and use the buttons in the bottom bar.
     */
    public void ShowBottomButtons(boolean visible) {
        startButton.setDisable(!visible);
        startButton.setVisible(visible);
        allStudentsButton.setDisable(!visible);
        allStudentsButton.setVisible(visible);
    }
}
