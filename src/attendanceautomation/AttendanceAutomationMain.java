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

    public static final String MAIN_VIEW_STRING = FOLDER + "main/MainView.fxml";
    public static final String MAIN_PIE_CHART_STRING = FOLDER + "main/PieChartView.fxml";
    public static final String MAIN_SEARCH_BAR_STRING = FOLDER + "main/SearchView.fxml";

    public static final String ALL_STUDENTS_STRING = FOLDER + "allStudents/AllStudentsView.fxml";

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
