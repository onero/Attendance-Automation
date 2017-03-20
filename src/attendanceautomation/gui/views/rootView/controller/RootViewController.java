/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.rootView.controller;

import attendanceautomation.be.Teacher;
import attendanceautomation.be.enums.EFXMLName;
import attendanceautomation.gui.model.SchoolClassModel;
import attendanceautomation.gui.views.NodeFactory;
import attendanceautomation.gui.views.detailedStudent.controller.DetailedStudentViewController;
import attendanceautomation.gui.views.login.controller.LoginViewController;
import attendanceautomation.gui.views.sharedComponents.componentsHolder.controller.ComponentsHolderViewController;
import attendanceautomation.gui.views.sharedComponents.filters.filterHolder.controller.FilterHolderViewController;
import attendanceautomation.gui.views.sharedComponents.searchView.controller.SearchViewController;
import attendanceautomation.gui.views.sharedComponents.whiteComponentHolder.controller.WhiteComponentHolderController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author gta1
 */
public class RootViewController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private HBox refreshBox;

    private static RootViewController instance;

    private NodeFactory nodeFactory;

    private Node MAIN_VIEW;
    private Node ALL_STUDENTS_VIEW;
    private Node DETAILED_STUDENT_VIEW;
    private Node LOGIN_VIEW;
    private Node LOGOUT_BUTTON;

    private Node LOADING_DATA_VIEW;

    private Node SEARCH_BAR;
    private Node MONTH_COMBOBOX;
    private Node ACTION_COMPONENT_HOLDER;
    private Node WHITE_COMPONENT_HOLDER_VIEW;
    private Node EMPTY_COMPONENT_HOLDER_VIEW;
    private Node CURRENT_CLASS_VIEW;

    private Node FILTER_BUTTON;
    private Node LOCATION_FILTER_VIEW;
    private Node SCHOOLCLASS_FILTER_VIEW;
    private Node ALL_SCHOOLCLASS_FILTER_VIEW;
    private Node SEMESTER_FILTER_VIEW;

    private BorderPane FILTER_PANE;

    private Node currentView;

    private WhiteComponentHolderController whiteComponentHolderController;
    private SearchViewController searchViewController;
    private DetailedStudentViewController detailedStudentViewController;

    private final SchoolClassModel schoolClassModel;

    public static RootViewController getInstance() {
        return instance;
    }
    @FXML
    private Button startButton;
    @FXML
    private Button allStudentsButton;
    @FXML
    private Button currentClass;

    public RootViewController() {
        nodeFactory = NodeFactory.getInstance();
        schoolClassModel = SchoolClassModel.getInstance();

        try {
            LOGIN_VIEW = nodeFactory.createNewView(EFXMLName.LOGIN_VIEW);
            LOGOUT_BUTTON = nodeFactory.createNewView(EFXMLName.LOGOUT_BUTTON);
            EMPTY_COMPONENT_HOLDER_VIEW = nodeFactory.createNewView(EFXMLName.COMPONENTS_HOLDER_VIEW);
            WHITE_COMPONENT_HOLDER_VIEW = createWhiteComponentHolderView();

        } catch (IOException ex) {
            System.out.println("MainView not loaded! ");
            System.out.println(ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        borderPane.setCenter(WHITE_COMPONENT_HOLDER_VIEW);
        ShowHideAdminButtons(false);
        refreshBox.setVisible(false);
    }

    /**
     * In the WhiteComponentController. Sets the center and the topView.
     *
     * @param event
     */
    @FXML
    private void handleAllStudentsButton(ActionEvent event) {
        //Recreate allStudentsView to ensure updated view with NonAttendance
        schoolClassModel.sortStudentsOnName();

        ALL_STUDENTS_VIEW = nodeFactory.createNewView(EFXMLName.ALL_STUDENTS_VIEW);

        switchCenterView(ALL_STUDENTS_VIEW);
        showNode(SEARCH_BAR);
        showNode(MONTH_COMBOBOX);
    }

    /**
     * In the WhiteComponentController. Sets the center and the topView.
     *
     * @param event
     */
    @FXML
    public void handleStartView(ActionEvent event) {
        switchCenterView(MAIN_VIEW);
        showNode(SEARCH_BAR);
        hideNode(MONTH_COMBOBOX);
        SchoolClassModel.getInstance().sortStudentsOnAttendance();
    }

    /**
     * Sets the node to be the detailed student view
     *
     * selectedStudent
     */
    public void handleDetailedStudentView() {
        try {
            DETAILED_STUDENT_VIEW = createDetailedStudentView();
        } catch (IOException ex) {
            Logger.getLogger(RootViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        switchCenterView(DETAILED_STUDENT_VIEW);
        hideNode(SEARCH_BAR);
        showNode(MONTH_COMBOBOX);
    }

    public void setRefreshBoxVisibility(boolean value) {
        refreshBox.setVisible(value);
    }

    /**
     * Returns the user to the login page.
     */
    public void handleLogout() {
        switchCenterView(LOGIN_VIEW);
        LoginViewController.getInstance().resetLogin();
        whiteComponentHolderController.setBorderPaneTop(EMPTY_COMPONENT_HOLDER_VIEW);
        ShowHideAdminButtons(false);

    }

    /**
     * Sends the user to the right screen depending on which user it is. If it's
     * a teacher he gets sent to the start view if it's a student he will be
     * sent to the detailed student view.
     *
     * @param teacherEmail
     */
    public void handleTeacherLogin(String teacherEmail) {
        Runnable task = () -> {
            Teacher teacher = schoolClassModel.getTeacherByEmail(teacherEmail);
            if (schoolClassModel.checkForNewTeacher(teacher)) {
                LOADING_DATA_VIEW = nodeFactory.createNewView(EFXMLName.LOADING_DATA_VIEW);
                schoolClassModel.setCurrentTeacher(teacher);
                loadTeacherView();
                schoolClassModel.updateCurrentClassStudents();
                schoolClassModel.loadDataFromDB();
                Platform.runLater(() -> {
                    switchCenterView(MAIN_VIEW);
                });
            } else {
                Platform.runLater(() -> {
                    switchCenterView(MAIN_VIEW);
                    setRefreshBoxVisibility(true);
                    whiteComponentHolderController.setBorderPaneTop(ACTION_COMPONENT_HOLDER);
                    ShowHideAdminButtons(true);
                    SchoolClassModel.getInstance().updateStudentData();
                });
            }
        };
        new Thread(task).start();
    }

    /**
     * Load the teacher view
     */
    private void loadTeacherView() {
        Platform.runLater(() -> {
            try {
                createTeacherViews();
                ShowHideAdminButtons(true);
                LoginViewController.getInstance().resetLogin();
            } catch (IOException ex) {
                System.out.println("Error whilst logging in as teacher");
                System.out.println(ex);
            }

            switchCenterView(LOADING_DATA_VIEW);
        });
    }

    /**
     * Create all views for the teacher
     *
     * @throws IOException
     */
    private void createTeacherViews() throws IOException {
        SEARCH_BAR = createSearchBarNode();
        MAIN_VIEW = nodeFactory.createNewView(EFXMLName.MAIN_VIEW);
        ACTION_COMPONENT_HOLDER = createActionComponentHolder();
        whiteComponentHolderController.setBorderPaneTop(ACTION_COMPONENT_HOLDER);
    }

    /**
     * Puts in a new Node in the center of the application
     *
     * @param node
     */
    private void switchCenterView(Node node) {
        whiteComponentHolderController.setBorderPaneCenter(node);
        currentView = node;
    }

    /**
     * Login in as student
     *
     * @param userId
     */
    public void handleStudentLogin(String userId) {
        schoolClassModel.loadStudentData(userId);
        try {
            DETAILED_STUDENT_VIEW = createDetailedStudentView();
            switchCenterView(DETAILED_STUDENT_VIEW);
            whiteComponentHolderController.setBorderPaneTop(ACTION_COMPONENT_HOLDER);
            ShowHideAdminButtons(false);
        } catch (Exception e) {
            System.out.println(e);
        }
        LoginViewController.getInstance().resetLogin();

    }

    /**
     * Reloads the current Node
     */
    public void reloadView() {
        if (currentView == ALL_STUDENTS_VIEW) {
            ALL_STUDENTS_VIEW = nodeFactory.createNewView(EFXMLName.ALL_STUDENTS_VIEW);
            switchCenterView(ALL_STUDENTS_VIEW);
        }
        if (currentView == DETAILED_STUDENT_VIEW) {
            try {
                DETAILED_STUDENT_VIEW = createDetailedStudentView();
                switchCenterView(DETAILED_STUDENT_VIEW);
            } catch (IOException ex) {
                Logger.getLogger(RootViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Creates the holder for the searchBar and the comboBox.
     *
     * @return
     * @throws IOException
     */
    private Node createActionComponentHolder() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLName.COMPONENTS_HOLDER_VIEW.toString()));
        Node node = loader.load();
        ComponentsHolderViewController controller = loader.getController();
        controller.setBorderPaneLeft(SEARCH_BAR);

        FILTER_PANE = new BorderPane();

        FILTER_BUTTON = nodeFactory.createNewView(EFXMLName.FILTER_BUTTON);
        MONTH_COMBOBOX = nodeFactory.createNewView(EFXMLName.MONTH_COMBO_BOX_VIEW);

        FILTER_PANE.setCenter(FILTER_BUTTON);
        FILTER_PANE.setRight(MONTH_COMBOBOX);
        hideNode(MONTH_COMBOBOX);

        controller.setBorderPaneCenter(FILTER_PANE);

        controller.setBorderPaneRight(LOGOUT_BUTTON);
        return node;
    }

    /**
     * Open filters modal
     */
    public void handleFilters() {
        Stage primStage = (Stage) borderPane.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLName.FILTER_HOLER_VIEW.toString()));
        try {
            Parent root = loader.load();
            Scene filterHolder = new Scene(root);
            Stage filterModal = new Stage();
            filterModal.setScene(filterHolder);

            filterModal.initModality(Modality.WINDOW_MODAL);
            filterModal.initOwner(primStage);

            LOCATION_FILTER_VIEW = nodeFactory.createNewView(EFXMLName.LOCATION_FILTER_VIEW);
            HBox schoolClassFilters = createAllSchoolClassesFilter();
            SEMESTER_FILTER_VIEW = nodeFactory.createNewView(EFXMLName.SEMESTER_FILTER_VIEW);

            FilterHolderViewController controller = loader.getController();
            controller.addFilter(LOCATION_FILTER_VIEW);
            controller.addFilter(schoolClassFilters);
            controller.addFilter(SEMESTER_FILTER_VIEW);

            filterModal.show();
        } catch (IOException ex) {
            Logger.getLogger(RootViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Display Node
     *
     * @param node
     */
    public void showNode(Node node) {
        node.setDisable(false);
        node.setVisible(true);
    }

    /**
     * Hide Node
     *
     * @param node
     */
    public void hideNode(Node node) {
        node.setDisable(true);
        node.setVisible(false);
    }

    /**
     * Makes it so that you can see and use the buttons in the bottom bar.
     *
     * @param visible
     */
    public void ShowHideAdminButtons(boolean visible) {
        startButton.setDisable(!visible);
        startButton.setVisible(visible);
        allStudentsButton.setDisable(!visible);
        allStudentsButton.setVisible(visible);
        currentClass.setDisable(!visible);
        currentClass.setVisible(visible);
        if (SEARCH_BAR != null) {
            FILTER_PANE.setDisable(!visible);
            FILTER_PANE.setVisible(visible);
            SEARCH_BAR.setDisable(!visible);
            SEARCH_BAR.setVisible(visible);
        }

    }

    @FXML
    private void handleCurrentClassBtn() {
        CURRENT_CLASS_VIEW = nodeFactory.createNewView(EFXMLName.CURRENT_CLASS_VIEW);

        switchCenterView(CURRENT_CLASS_VIEW);
        hideNode(MONTH_COMBOBOX);
    }

    /**
     * Creates the componentHolder that holds the center and topView of mainView
     * and allStudentsView.
     *
     * @return
     * @throws IOException
     */
    private Node createWhiteComponentHolderView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLName.WHITE_COMPONENT_HOLDER.toString()));
        Node node = loader.load();
        whiteComponentHolderController = loader.getController();
        whiteComponentHolderController.setBorderPaneTop(EMPTY_COMPONENT_HOLDER_VIEW);
        switchCenterView(LOGIN_VIEW);
        return node;
    }

    /**
     * Create a HBox Node containing filters for allSchoolClasses
     *
     * @return
     * @throws IOException
     */
    private HBox createAllSchoolClassesFilter() throws IOException {
        SCHOOLCLASS_FILTER_VIEW = nodeFactory.createNewView(EFXMLName.SCHOOLCLASS_FILTER_VIEW);
        ALL_SCHOOLCLASS_FILTER_VIEW = nodeFactory.createNewView(EFXMLName.ALL_SCHOOLCLASSES_SEMESTER_FILTER_VIEW);
        HBox schoolClassHbox = new HBox();
        schoolClassHbox.getChildren().add(SCHOOLCLASS_FILTER_VIEW);
        schoolClassHbox.getChildren().add(ALL_SCHOOLCLASS_FILTER_VIEW);
        return schoolClassHbox;
    }

    /**
     * Creates the searchBar.
     *
     * @return
     * @throws IOException
     */
    private Node createSearchBarNode() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLName.SEARCH_VIEW.toString()));
        Node node = loader.load();
        searchViewController = loader.getController();
        return node;
    }

    /**
     * Create the StudentInformationTopView
     *
     * @return
     * @throws IOException
     */
    private Node createDetailedStudentView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLName.DETAILED_STUDENT_VIEW.toString()));
        Node node = loader.load();
        detailedStudentViewController = loader.getController();
        return node;
    }
}
