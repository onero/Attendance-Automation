/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author gta1
 */
public class AttendanceAutomationMain extends Application {

    private static final String FOLDER = "/attendanceautomation/gui/view/";
    public static final String STUDENTS_ATTENDANCEINFORMATION_STRING = FOLDER + "components/studentAttendanceInformation/StudentAttendanceInformationView.fxml";
    public static final String PIE_CHART_STRING = FOLDER + "components/PieChartView.fxml";
    public static final String SEARCH_BAR_STRING = FOLDER + "components/SearchView.fxml";
    public static final String PARENT_CHECKBOX_STRING = FOLDER + "components/studentAttendanceInformation/ParentCheckBoxView.fxml";
    public static final String DAYS_IN_MONTH_STRING = FOLDER + "components/studentAttendanceInformation/DaysInMonthView.fxml";

    public static final String MAIN_VIEW_STRING = FOLDER + "main/MainView.fxml";

    public static final String ALL_STUDENTS_STRING = FOLDER + "allStudents/AllStudentsView.fxml";
    public static final String LIST_ALL_STUDENTS_STRING = FOLDER + "allStudents/ListOfAllStudentsNonAttendanceView.fxml";
    public static final String FILLER_LABEL_STRING = FOLDER + "components/FillerLabel.fxml";

    public static final String DETAILED_STUDENT_STRING = FOLDER + "detailedStudent/DetailedStudentView.fxml";
    public static final String STUDENT_INFORMATION_TOP_STRING = FOLDER + "detailedStudent/StudentInformationTopView.fxml";

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("gui/view/RootView.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
