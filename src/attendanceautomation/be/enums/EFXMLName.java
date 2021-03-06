/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be.enums;

/**
 *
 * @author Rasmus
 */
public enum EFXMLName {
    FOLDER("/attendanceautomation/gui/views/"),
    MAIN_VIEW(FOLDER + "main/view/MainView.fxml"),
    NAME_STATISTICS_VIEW(FOLDER + "main/view/NameStatisticsView.fxml"),
    LIST_OF_NAMES_STATISTICS_VIEW(FOLDER + "main/view/ListOfNameStatisticsView.fxml"),
    DETAILED_STUDENT_VIEW(FOLDER + "detailedStudent/view/DetailedStudentView.fxml"),
    STUDENT_INFORMATION_TOP_VIEW(FOLDER + "detailedStudent/view/StudentInformationTopView.fxml"),
    FILLER_LABEL(FOLDER + "sharedComponents/fillerLabel/view/FillerLabel.fxml"),
    PIE_CHART_VIEW(FOLDER + "sharedComponents/pieChart/view/PieChartView.fxml"),
    SEARCH_VIEW(FOLDER + "sharedComponents/searchView/view/SearchView.fxml"),
    COMPONENTS_HOLDER_VIEW(FOLDER + "sharedComponents/componentsHolder/view/ComponentsHolderView.fxml"),
    ALL_COMPONENT_HOLDER(FOLDER + "sharedComponents/allComponentHolder/view/AllComponentHolder.fxml"),
    WEEK_CHECK_BOX_VIEW(FOLDER + "sharedComponents/studentAttendanceInformation/view/WeekCheckBoxView.fxml"),
    STUDENTS_ATTENDANCE_INFORMATION(FOLDER + "sharedComponents/studentAttendanceInformation/view/StudentAttendanceInformationView.fxml"),
    ALL_STUDENTS_VIEW(FOLDER + "allStudents/view/AllStudentsView.fxml"),
    LIST_OF_ALL_STUDENTS_NON_ATTENDANCE_VIEW(FOLDER + "allStudents/view/ListOfAllStudentsNonAttendanceView.fxml"),
    MONTH_COMBO_BOX_VIEW(FOLDER + "sharedComponents/monthComboBox/view/MonthComboboxView.fxml"),
    LOGIN_VIEW(FOLDER + "login/view/LoginView.fxml"),
    LOGOUT_BUTTON(FOLDER + "login/view/LogoutView.fxml"),
    WEEK_AND_DAYS_BAR(FOLDER + "sharedComponents/weeksAndDaysBar/view/WeeksAndDaysBarView.fxml"),
    FILTER_BUTTON(FOLDER + "sharedComponents/filters/filterButton/view/FilterButton.fxml"),
    FILTER_SEARCH_BUTTON(FOLDER + "sharedComponents/filters/filterButton/view/FilterSearchButton.fxml"),
    FILTER_HOLER_VIEW(FOLDER + "sharedComponents/filters/filterHolder/view/FilterHolderView.fxml"),
    LOCATION_FILTER_VIEW(FOLDER + "sharedComponents/filters/locationFilter/view/LocationFilterView.fxml"),
    SCHOOLCLASS_FILTER_VIEW(FOLDER + "sharedComponents/filters/schoolClassFilter/view/SchoolClassFilterView.fxml"),
    SEMESTER_FILTER_VIEW(FOLDER + "sharedComponents/filters/semesterFilter/view/SemesterFilterView.fxml"),
    ALL_SCHOOLCLASSES_SEMESTER_FILTER_VIEW(FOLDER + "sharedComponents/filters/allSchoolClassesSemesterFilter/view/AllSchoolClassesSemesterFilterView.fxml"),
    CURRENT_CLASS_VIEW(FOLDER + "currentClassView/view/currentClassView.fxml"),
    CURRENT_CLASS_LIST_VIEW(FOLDER + "currentClassView/view/CurrentClassListView.fxml"),
    NAME_CURRENT_CLASS_LIST_VIEW(FOLDER + "currentClassView/view/NameCurrentClassListView.fxml"),
    LOADING_DATA_VIEW(FOLDER + "sharedComponents/loadingData/view/LoadingDataView.fxml"),
    CURRENT_CLASS_PIE_CHART(FOLDER + "currentClassView/view/CurrentClassPieChart.fxml"),
    REFRESHING_DATA_VIEW(FOLDER + "sharedComponents/loadingData/view/RefreshingDataView.fxml"),
    REFRESH_DATA_BUTTON(FOLDER + "sharedComponents/loadingData/view/RefreshDataButton.fxml"),
    DATEPICKER_VIEW(FOLDER + "sharedComponents/filters/datePicker/view/DatePickerView.fxml"),
    DATE_RANGE_VIEW(FOLDER + "sharedComponents/dateRangeView/view/DateRangeView.fxml");

    private final String text;

    private EFXMLName(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
