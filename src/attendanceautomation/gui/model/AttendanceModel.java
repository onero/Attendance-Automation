/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.model;

import attendanceautomation.be.Student;
import attendanceautomation.bll.AttendanceManager;
import attendanceautomation.gui.controller.main.MainViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;

public class AttendanceModel {

    private static AttendanceModel instance;

    private final AttendanceManager attendanceManager;

    private final ObservableList<PieChart.Data> pieChartData;

    public static AttendanceModel getInstance() {
        if (instance == null) {
            instance = new AttendanceModel();
        }
        return instance;
    }

    public AttendanceModel() {
        pieChartData = FXCollections.observableArrayList();

        attendanceManager = new AttendanceManager();

        addNonAttendantStudentsToChartData();
    }

    /**
     * Calculates the total attendance for the student and returns the
     * percentage as a double
     *
     * @param student
     * @return
     */
    public ObservableList<Data> getStudentAttendance(Student student) {
        return attendanceManager.computeStudentAttendance(student);
    }

    /**
     * Check if the student is already in the data
     *
     * @param student
     */
    public void checkIfStudentIsInChart(Student student) {
        boolean studentIsThere = false;
        if (pieChartData.isEmpty()) {
            addNewStudentToChartData(student);
        } else {
            for (Data data : pieChartData) {
                if (data.getName().equals(student.getFullName())) {
                    studentIsThere = true;
                }
            }
            if (!studentIsThere) {
                addNewStudentToChartData(student);
            }
        }
        MainViewController.getInstance().updatePieData();
    }

    private void addNewStudentToChartData(Student student) {
        Data nonAttendance = new Data(student.getFullName(), student.getNonAttendancePercentage().get());
        nonAttendance.pieValueProperty().bind(student.getNonAttendancePercentage());
        pieChartData.add(nonAttendance);
    }

    /**
     * Find the students with nonAttendance and add them to the pirChartData
     */
    private void addNonAttendantStudentsToChartData() {
        //TODO ALH: Make sure than this can be dynamic for more classes
        //TODO ALH: Make sure this is calculated as a total
        for (Student student : SchoolClassModel.getInstance().getSchoolClasses().get(0).getStudents()) {
            if (student.getNonAttendancePercentage().get() > 0) {
                addNewStudentToChartData(student);
            }
        }
    }

    /**
     *
     * @return piechart data
     */
    public ObservableList<Data> getPieChartData() {
        return pieChartData;
    }

}
