/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.rootView.controller;

import attendanceautomation.be.enums.EFXMLNames;
import attendanceautomation.gui.model.SchoolClassModel;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private Node ACTION_COMPONENT_HOLDER;
    private Node WHITE_COMPONENT_HOLDER_VIEW;
    private Node EMPTY_TOP_BAR;
    private Node CURRENT_CLASS_VIEW;

    private Node FILTER_BUTTON;
    private Node LOCATION_FILTER_VIEW;

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
        try {
            LOGIN_VIEW = createLoginView();
            LOGOUT_BUTTON = createLogoutView();
            EMPTY_TOP_BAR = createEmptyTopBar();
            WHITE_COMPONENT_HOLDER_VIEW = createWhiteComponentHolderView();

        } catch (IOException ex) {
            System.out.println("MainView not loaded! " + ex);
        }
        schoolClassModel = SchoolClassModel.getInstance();
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
        detailedStudentViewController = loader.getController();
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
        schoolClassModel.sortStudentsOnName();
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
        SchoolClassModel.getInstance().sortStudentsOnAttendance();
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
        searchViewController.showSearchBar(true);

    }

    /**
     * Sends the user to the right screen depending on which user it is. If it's
     * a teacher he gets sent to the start view if it's a student he will be
     * sent to the detailed student view.
     *
     * @param teacherId
     */
    public void handleTeacherLogin(String teacherId) {
        Runnable task = () -> {
            schoolClassModel.loadDataFromDB();
            Platform.runLater(() -> {
                try {
                    createTeacherViews();
                    ShowBottomButtons(true);
                    LoginViewController.getInstance().hideSpinner();
                    LoginViewController.getInstance().showLoginButton();
                } catch (IOException ex) {
                    System.out.println("Error whilst logging in as teacher");
                    System.out.println(ex);
                }
            });
        };
        new Thread(task).start();
    }

    /**
     * Create all views for the teacher
     *
     * @throws IOException
     */
    private void createTeacherViews() throws IOException {
        MAIN_VIEW = createMainView();
        ALL_STUDENTS_VIEW = createAllStudents();
        DETAILED_STUDENT_VIEW = createDetailedStudentView();
        SEARCH_BAR = createSearchBarNode();
        FILTER_BUTTON = createFilterButton();
        ACTION_COMPONENT_HOLDER = createActionComponentHolder();
        whiteComponentHolderController.setBorderPaneCenter(MAIN_VIEW);
        whiteComponentHolderController.setBorderPaneTop(ACTION_COMPONENT_HOLDER);
    }

    /**
     * Creates the filter button
     *
     * @return
     * @throws IOException
     */
    private Node createFilterButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.FILTER_BUTTON.toString()));
        Node node = loader.load();
        return node;
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
            whiteComponentHolderController.setBorderPaneCenter(DETAILED_STUDENT_VIEW);
            whiteComponentHolderController.setBorderPaneTop(ACTION_COMPONENT_HOLDER);
            ShowBottomButtons(false);
            searchViewController.showSearchBar(false);
        } catch (Exception e) {
            System.out.println(e);
        }

        detailedStudentViewController.setIsStudentLogin();
        detailedStudentViewController.setCurrentStudent(schoolClassModel.getCurrentStudent());
        LoginViewController.getInstance().hideSpinner();
        LoginViewController.getInstance().showLoginButton();

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
     * Creates the comboBox.
     *
     * @return
     * @throws IOException
     */
    private Node createLocationFilterView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.LOCATION_FILTER_VIEW.toString()));
        Node node = loader.load();
        return node;
    }

    /**
     * Creates the holder for the searchBar and the comboBox.
     *
     * @return
     * @throws IOException
     */
    private Node createActionComponentHolder() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.COMPONENTS_HOLDER_VIEW.toString()));
        Node node = loader.load();
        ComponentsHolderViewController controller = loader.getController();
        controller.setBorderPaneLeft(SEARCH_BAR);

        controller.setBorderPaneCenter(FILTER_BUTTON);

        controller.setBorderPaneRight(LOGOUT_BUTTON);
        return node;
    }

    /**
     * Open filters modal
     */
    public void handleFilters() {
        Stage primStage = (Stage) borderPane.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.FILTER_HOLER_VIEW.toString()));
        try {
            Parent root = loader.load();
            Scene filterHolder = new Scene(root);
            Stage filterModal = new Stage();
            filterModal.setScene(filterHolder);

            filterModal.initModality(Modality.WINDOW_MODAL);
            filterModal.initOwner(primStage);

            LOCATION_FILTER_VIEW = createLocationFilterView();
            FilterHolderViewController controller = loader.getController();
            controller.setCenter(LOCATION_FILTER_VIEW);

            filterModal.show();
        } catch (IOException ex) {
            Logger.getLogger(RootViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
     *
     * @param visible
     */
    public void ShowBottomButtons(boolean visible) {
        startButton.setDisable(!visible);
        startButton.setVisible(visible);
        allStudentsButton.setDisable(!visible);
        allStudentsButton.setVisible(visible);
        currentClass.setDisable(!visible);
        currentClass.setVisible(visible);

    }

    @FXML
    private void handleCurrentClassBtn(ActionEvent event) {
        try {
            CURRENT_CLASS_VIEW = createCurrentClassView();
        } catch (IOException ex) {
            Logger.getLogger(RootViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        whiteComponentHolderController.setBorderPaneCenter(CURRENT_CLASS_VIEW);
    }

    /**
     * Creates the node of the current class view.
     *
     * @return @throws IOException
     */
    private Node createCurrentClassView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.CURRENT_CLASS_VIEW.toString()));
        Node node = loader.load();
        return node;
    }
}
