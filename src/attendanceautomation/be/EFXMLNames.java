/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be;

import static attendanceautomation.AttendanceAutomationMain.FOLDER;

/**
 *
 * @author Rasmus
 */
public enum EFXMLNames {
    MAIN_VIEW(FOLDER + "main/MainView.fxml"),
    NAME_STATISTICS_VIEW(FOLDER + "main/NameStatisticsView.fxml"),
    LIST_OF_NAMES_STATISTICS_VIEW(FOLDER + "main/ListOfNameStatisticsView.fxml"),
    
    DETAILED_STUDENT_VIEW(FOLDER + "detailedStudent/DetailedStudentView.fxml"),
    STUDENT_INFORMATION_TOP_VIEW(FOLDER + "detailedStudent/StudentInformationTopView.fxml"),
    
    FILLER_LABEL(FOLDER + "components/FillerLabel.fxml"),
    PIE_CHART_VIEW(FOLDER + "components/PieChartView.fxml"),
    SEARCH_VIEW(FOLDER + "components/SearchView.fxml"),
    
    DAYS_IN_MONTH_VIEW(FOLDER + "components/studentAttendanceInformation/DaysInMonthView.fxml"),
    PARENT_CHECK_BOX_VIEW(FOLDER + "components/studentAttendanceInformation/ParentCheckBoxView.fxml"),
    STUDENTS_ATTENDANCE_INFORMATION (FOLDER + "components/studentAttendanceInformation/StudentAttendanceInformationView.fxml"),
    
    ALL_STUDENTS_VIEW(FOLDER + "allStudents/AllStudentsView.fxml"),
    LIST_OF_ALL_STUDENTS_NON_ATTENDANCE_VIEW(FOLDER + "allStudents/ListOfAllStudentsNonAttendanceView.fxml"),
    MONTH_COMBO_BOX_VIEW(FOLDER + "allStudents/MonthComboBoxView.fxml");
    
        
    private final String text;
    
    private EFXMLNames(final String text){
        this.text = text;
    }
    
    @Override
    public String toString(){
        return text;
    }
}
