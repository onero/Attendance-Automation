/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.rootView.controller;

import attendanceautomation.be.Teacher;
import attendanceautomation.be.enums.EFXMLName;
import attendanceautomation.gui.model.PieChartModel;
import attendanceautomation.gui.model.SchemaModel;
import attendanceautomation.gui.model.SchoolClassModel;
import attendanceautomation.gui.views.NodeFactory;
import attendanceautomation.gui.views.currentClassView.controller.CurrentClassPieChartController;
import attendanceautomation.gui.views.login.controller.LoginViewController;
import attendanceautomation.gui.views.sharedComponents.allComponentHolder.controller.AllComponentHolderController;
import attendanceautomation.gui.views.sharedComponents.componentsHolder.controller.ComponentsHolderViewController;
import attendanceautomation.gui.views.sharedComponents.filters.datePicker.controller.DatePickerViewController;
import attendanceautomation.gui.views.sharedComponents.filters.filterHolder.controller.FilterHolderViewController;
import attendanceautomation.gui.views.sharedComponents.filters.schoolClassFilter.controller.SchoolClassFilterViewController;
import attendanceautomation.gui.views.sharedComponents.filters.semesterFilter.controller.SemesterFilterViewController;
import attendanceautomation.gui.views.sharedComponents.monthComboBox.controller.MonthComboboxViewController;
import attendanceautomation.gui.views.sharedComponents.pieChart.controller.PieChartViewController;
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
import javafx.scene.layout.GridPane;
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
    private GridPane gridButtomBar;

    private static RootViewController instance;

    private NodeFactory nodeFactory;

    private Node MAIN_VIEW;
    private Node ALL_STUDENTS_VIEW;
    private Node DETAILED_STUDENT_VIEW;
    private Node LOGIN_VIEW;
    private Node LOGOUT_BUTTON;

    private Node LOADING_DATA_VIEW;
    private Node REFRESHING_DATA_VIEW;
    private Node REFRESH_DATA_BUTTON;

    private Node SEARCH_BAR;
    private Node MONTH_COMBOBOX;
    private Node ACTION_COMPONENT_HOLDER;
    private Node ALL_COMPONENT_HOLDER_VIEW;
    private Node EMPTY_COMPONENT_HOLDER_VIEW;
    private Node CURRENT_CLASS_VIEW;

    private Node FILTER_BUTTON;
    private Node LOCATION_FILTER_VIEW;
    private Node SCHOOLCLASS_FILTER_VIEW;
    private Node ALL_SCHOOLCLASS_FILTER_VIEW;
    private Node SEMESTER_FILTER_VIEW;
    private Node DATEPICKER_VIEW;

    private Node DATE_RANGE_VIEW;

    private BorderPane FILTER_PANE;

    private Node currentView;

    private Stage filterModal;

    private AllComponentHolderController allComponentHolderController;

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
            ALL_COMPONENT_HOLDER_VIEW = createAllComponentHolderView();
        } catch (IOException ex) {
            System.out.println("MainView not loaded! ");
            System.out.println(ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        borderPane.setCenter(ALL_COMPONENT_HOLDER_VIEW);
        ShowHideAdminButtons(false);
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
        showNode(DATE_RANGE_VIEW);
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
        hideNode(DATE_RANGE_VIEW);
        SchoolClassModel.getInstance().sortStudentsOnAttendance();
    }

    /**
     * Sets the node to be the detailed student view
     *
     * selectedStudent
     */
    public void handleDetailedStudentView() {
        DETAILED_STUDENT_VIEW = nodeFactory.createNewView(EFXMLName.DETAILED_STUDENT_VIEW);
        switchCenterView(DETAILED_STUDENT_VIEW);
        hideNode(SEARCH_BAR);
        showNode(MONTH_COMBOBOX);
        showNode(DATE_RANGE_VIEW);
    }

    /**
     * Returns the user to the login page.
     */
    public void handleLogout() {
        switchCenterView(LOGIN_VIEW);
        LoginViewController.getInstance().resetLogin();
        allComponentHolderController.setBorderPaneTop(EMPTY_COMPONENT_HOLDER_VIEW);
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
                schoolClassModel.setCurrentTeacher(teacher);
                LOADING_DATA_VIEW = nodeFactory.createNewView(EFXMLName.LOADING_DATA_VIEW);
                loadTeacherView();
                schoolClassModel.loadDataFromDB();
                schoolClassModel.updateCurrentClassStudents(0);
                Platform.runLater(() -> {
                    MAIN_VIEW = nodeFactory.createNewView(EFXMLName.MAIN_VIEW);
                    gridButtomBar.add(REFRESH_DATA_BUTTON, 6, 1);
                    currentView = MAIN_VIEW;
                    updateAll();
                    LoginViewController.getInstance().resetLogin();
                });
            } else {
                Platform.runLater(() -> {
                    switchCenterView(MAIN_VIEW);
                    REFRESHING_DATA_VIEW = nodeFactory.createNewView(EFXMLName.REFRESHING_DATA_VIEW);
                    gridButtomBar.add(REFRESHING_DATA_VIEW, 5, 1);
                    allComponentHolderController.setBorderPaneTop(ACTION_COMPONENT_HOLDER);
                    ShowHideAdminButtons(true);
                    gridButtomBar.getChildren().remove(REFRESHING_DATA_VIEW);
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
            switchCenterView(LOADING_DATA_VIEW);
            try {
                createTeacherViews();
            } catch (IOException ex) {
                System.out.println("Error whilst logging in as teacher");
                System.out.println(ex);
            }

        });
    }

    /**
     * Create all views for the teacher
     *
     * @throws IOException
     */
    private void createTeacherViews() throws IOException {
        SEARCH_BAR = nodeFactory.createNewView(EFXMLName.SEARCH_VIEW);
        ACTION_COMPONENT_HOLDER = createActionComponentHolder();
        allComponentHolderController.setBorderPaneTop(ACTION_COMPONENT_HOLDER);
        REFRESH_DATA_BUTTON = nodeFactory.createNewView(EFXMLName.REFRESH_DATA_BUTTON);
        REFRESHING_DATA_VIEW = nodeFactory.createNewView(EFXMLName.REFRESHING_DATA_VIEW);
    }

    /**
     * Inserts the REFRESHING_DATA_VIEW, updates student data and updates the
     * view
     */
    public void handleRefreshData() {
        gridButtomBar.getChildren().remove(REFRESH_DATA_BUTTON);
        gridButtomBar.add(REFRESHING_DATA_VIEW, 6, 1);
        Runnable task = () -> {
            schoolClassModel.updateStudentData();
            Platform.runLater(() -> {
                gridButtomBar.getChildren().remove(REFRESHING_DATA_VIEW);
                gridButtomBar.add(REFRESH_DATA_BUTTON, 6, 1);

                updateAll();
            });
        };
        new Thread(task).start();
    }

    /**
     * Puts in a new Node in the center of the application
     *
     * @param node
     */
    private void switchCenterView(Node node) {
        allComponentHolderController.setBorderPaneCenter(node);
        currentView = node;
    }

    /**
     * Login in as student
     *
     * @param userId
     */
    public void handleStudentLogin(String userId) {
        schoolClassModel.loadStudentDataByEmail(userId);
        DETAILED_STUDENT_VIEW = nodeFactory.createNewView(EFXMLName.DETAILED_STUDENT_VIEW);
        switchCenterView(DETAILED_STUDENT_VIEW);
        ShowHideAdminButtons(false);
        LOGOUT_BUTTON.setDisable(false);
        LOGOUT_BUTTON.setVisible(true);
        allComponentHolderController.setBorderPaneTop(ACTION_COMPONENT_HOLDER);
        LoginViewController.getInstance().resetLogin();
        showNode(DATE_RANGE_VIEW);
    }

    /**
     * Update current view and resets PieChart
     */
    public void updateAll() {
        Runnable task = () -> {
            Platform.runLater(() -> {
                reloadView();
                PieChartModel.getInstance().resetPieChart();
                PieChartViewController.getInstance().updateChart();
                ShowHideAdminButtons(true);
            });
        };
        new Thread(task).start();
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
            DETAILED_STUDENT_VIEW = nodeFactory.createNewView(EFXMLName.DETAILED_STUDENT_VIEW);
            switchCenterView(DETAILED_STUDENT_VIEW);
        }
        if (currentView == MAIN_VIEW) {
            switchCenterView(MAIN_VIEW);
            schoolClassModel.sortStudentsOnAttendance();
        }
        MonthComboboxViewController.getInstance().updateWeekNumbers();
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

        DATE_RANGE_VIEW = nodeFactory.createNewView(EFXMLName.DATE_RANGE_VIEW);
        controller.setBoderPaneTop(DATE_RANGE_VIEW);
        hideNode(DATE_RANGE_VIEW);
        SEARCH_BAR.setVisible(false);

        FILTER_PANE = new BorderPane();

        FILTER_BUTTON = nodeFactory.createNewView(EFXMLName.FILTER_BUTTON);
        MONTH_COMBOBOX = nodeFactory.createNewView(EFXMLName.MONTH_COMBO_BOX_VIEW);

        FILTER_PANE.setCenter(FILTER_BUTTON);
        FILTER_PANE.setVisible(false);
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
            filterModal = new Stage();
            filterModal.setScene(filterHolder);

            filterModal.initModality(Modality.WINDOW_MODAL);
            filterModal.initOwner(primStage);

            LOCATION_FILTER_VIEW = nodeFactory.createNewView(EFXMLName.LOCATION_FILTER_VIEW);
            HBox schoolClassFilters = createAllSchoolClassesFilter();
            SEMESTER_FILTER_VIEW = nodeFactory.createNewView(EFXMLName.SEMESTER_FILTER_VIEW);
            DATEPICKER_VIEW = nodeFactory.createNewView(EFXMLName.DATEPICKER_VIEW);

            FilterHolderViewController controller = loader.getController();
            controller.addFilter(LOCATION_FILTER_VIEW);
            controller.addFilter(schoolClassFilters);
            controller.addFilter(SEMESTER_FILTER_VIEW);
            controller.addFilter(DATEPICKER_VIEW);

            Node okButton = nodeFactory.createNewView(EFXMLName.FILTER_SEARCH_BUTTON);
            controller.addFilter(okButton);
            filterModal.show();
        } catch (IOException ex) {
            Logger.getLogger(RootViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Reload data depending on user selections
     */
    public void handleFilterSearch() {
        String schoolClassName = SchoolClassFilterViewController.getInstance().getSchoolName();
        int schoolClassID = schoolClassModel.getSchoolClassIdByName(schoolClassName);
        if (SemesterFilterViewController.getInstance().isSemesterSelected()) {
            handleSemesterSearch(schoolClassID);

        } else if (DatePickerViewController.getInstance().hasNewDate()) {
            handleDateSearch(schoolClassID);

        } else {
            handleSchoolClassWithoutFilter(schoolClassName);
        }
    }

    private void handleSchoolClassWithoutFilter(String schoolClassName) {
        filterModal.close();
        showLoadingDataView();

        Runnable task = () -> {
            //TODO ALH: Make dynamic (also in SchemaModel!)
            String startDate = "2016-10-31";
            String endDate = "2017-02-28";
            SchemaModel.getInstance().setCurrentMonth(startDate, endDate);
            schoolClassModel.loadSchoolClassDataByName(schoolClassName);
            Platform.runLater(() -> {
                updateAll();
            });
        };
        new Thread(task).start();
    }

    private void handleDateSearch(int schoolClassID) {
        String startDate = DatePickerViewController.getInstance().getStartDate();
        String endDate = DatePickerViewController.getInstance().getEndDate();
        filterModal.close();
        showLoadingDataView();

        SchemaModel.getInstance().setCurrentMonth(startDate, endDate);

        Runnable task = () -> {
            schoolClassModel.loadCurrentSchoolClassByPeriodAndID(schoolClassID);
            Platform.runLater(() -> {
                updateAll();
            });
        };
        new Thread(task).start();
    }

    private void handleSemesterSearch(int schoolClassID) {
        int semesterID = SemesterFilterViewController.getInstance().getSemesterID();
        filterModal.close();
        showLoadingDataView();

        Runnable task = () -> {
            schoolClassModel.loadSchoolClassDataBySemester(schoolClassID, semesterID);
            Platform.runLater(() -> {
                updateAll();
            });
        };
        new Thread(task).start();
    }

    private void showLoadingDataView() {
        allComponentHolderController.setBorderPaneCenter(LOADING_DATA_VIEW);
        ShowHideAdminButtons(false);
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
        LOGOUT_BUTTON.setDisable(!visible);
        LOGOUT_BUTTON.setVisible(visible);
        if (SEARCH_BAR != null) {
            FILTER_PANE.setDisable(!visible);
            FILTER_PANE.setVisible(visible);
            SEARCH_BAR.setDisable(!visible);
            SEARCH_BAR.setVisible(visible);
            REFRESH_DATA_BUTTON.setDisable(!visible);
            REFRESH_DATA_BUTTON.setVisible(visible);
        }
    }

    @FXML
    private void handleCurrentClassBtn() {
        switchCenterView(LOADING_DATA_VIEW);
        ShowHideAdminButtons(false);
        hideNode(DATE_RANGE_VIEW);
        Runnable task = () -> {
            schoolClassModel.updateCurrentClassStudents(0);
            Platform.runLater(() -> {
                if (CURRENT_CLASS_VIEW == null) {
                    CURRENT_CLASS_VIEW = nodeFactory.createNewView(EFXMLName.CURRENT_CLASS_VIEW);
                }
                switchCenterView(CURRENT_CLASS_VIEW);
                ShowHideAdminButtons(true);
                hideNode(MONTH_COMBOBOX);
                CurrentClassPieChartController.getInstance().updateChart();
            });
        };
        new Thread(task).start();
    }

    /**
     * Creates the componentHolder that holds the center and topView of mainView
     * and allStudentsView.
     *
     * @return
     * @throws IOException
     */
    private Node createAllComponentHolderView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLName.ALL_COMPONENT_HOLDER.toString()));
        Node node = loader.load();
        allComponentHolderController = loader.getController();
        allComponentHolderController.setBorderPaneTop(EMPTY_COMPONENT_HOLDER_VIEW);
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
}
