/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.main;

import attendanceautomation.be.SchoolClass;
import attendanceautomation.be.SchoolDay;
import attendanceautomation.be.SchoolLesson;
import attendanceautomation.be.SchoolWeek;
import attendanceautomation.be.Student;
import attendanceautomation.be.enums.EFXMLNames;
import attendanceautomation.gui.controller.components.PieChartViewController;
import attendanceautomation.gui.model.SchoolClassModel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Rasmus
 */
public class MainViewController implements Initializable {

    @FXML
    private BorderPane borderPane;

    private Node PIE_CHART_NODE;
    private Node LIST_VIEW;

    private static MainViewController instance;

    private FXMLLoader mainPieChartLoader;

    public static MainViewController getInstance() {
        return instance;
    }

    private SchoolClassModel schoolClassModel;

    public MainViewController() {
        schoolClassModel = SchoolClassModel.getInstance();
        try {
            PIE_CHART_NODE = createPieChartNode();
            LIST_VIEW = createListView();
        } catch (IOException ex) {
            System.out.println("PieChart not loaded!");
        }
//        testBEData();
    }

    private void testBEData() {
        //For each SchoolClass
        for (SchoolClass schoolClass : schoolClassModel.getSchoolClasses()) {
            //Print name of class
            System.out.println(schoolClass.getSchoolClassName());
            //For each student
            for (Student student : schoolClass.getStudents()) {
                //Print name and attendance
                System.out.println(student.getFullName());
                System.out.println(student.getNonAttendancePercentage() + " %");
            }
            //For each schoolweek
            for (SchoolWeek schoolWeek : schoolClass.getSchoolWeeks()) {
                //Print weeknumber
                System.out.println(schoolWeek.getWeekNumber());
                //For each day
                for (SchoolDay schoolDay : schoolWeek.getSchoolDays()) {
                    //Print name of day
                    System.out.println(schoolDay.getSchoolDayName());
                    //For each lesson that day
                    for (SchoolLesson lesson : schoolDay.getLessons()) {
                        //Print lesson info
                        System.out.println(lesson.getLesson());
                    }
                }
            }
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override

    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        borderPane.setCenter(PIE_CHART_NODE);
        borderPane.setLeft(LIST_VIEW);
    }

    /**
     * Creates the node for the PieChartView.
     *
     * @return
     * @throws IOException
     */
    private Node createPieChartNode() throws IOException {
        mainPieChartLoader = new FXMLLoader(getClass().getResource(EFXMLNames.PIE_CHART_VIEW.toString()));
        Node node = mainPieChartLoader.load();
        updatePieData();
        return node;
    }

    /**
     * Updated the data in the pieChart
     */
    public void updatePieData() {
        PieChartViewController controller = mainPieChartLoader.getController();
        controller.setData();
    }

    /**
     * Creates the node for the listView.
     *
     * @return
     * @throws IOException
     */
    private Node createListView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.LIST_OF_NAMES_STATISTICS_VIEW.toString()));
        Node node = loader.load();
        ListOfNameStatisticsViewController controller = loader.getController();
        controller.setItemsInList(schoolClassModel);
        return node;
    }

    /**
     * Returns the instance of SchoolClassModel.
     *
     * @return
     */
    public SchoolClassModel getSchoolClassModel() {
        return schoolClassModel;
    }
}
