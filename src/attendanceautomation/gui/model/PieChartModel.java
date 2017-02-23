/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.model;

import attendanceautomation.be.Student;
import attendanceautomation.bll.AttendanceManager;
import attendanceautomation.gui.controller.main.MainViewController;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart.Data;

public class PieChartModel {

    private static PieChartModel instance;

    private final AttendanceManager attendanceManager;

    private ArrayList<Data> pieChartData;

    private ObservableList<Data> computedPieChartData;

    public static PieChartModel getInstance() {
        if (instance == null) {
            instance = new PieChartModel();
        }
        return instance;
    }

    public PieChartModel() {
        pieChartData = new ArrayList<>();

        computedPieChartData = FXCollections.observableArrayList();

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
    public ArrayList<Data> getStudentAttendance(Student student) {
        return attendanceManager.computeStudentAttendance(student);
    }

    /**
     * Compute the pieChartData according to total percentage for each student
     */
    public void computeTotalPieChartPercentage() {
        computedPieChartData.clear();
        computedPieChartData.addAll(attendanceManager.computeAllAttendance(pieChartData));
    }

    /**
     * Check if the student is already in the data
     *
     * @param student
     */
    public void checkIfStudentIsInChart(Student student) {
        boolean studentIsThere = false;
        //Check if there are students in the pieChart
        if (pieChartData.isEmpty()) {
            addNewStudentToChartData(student);
        } else {
            for (Data data : pieChartData) {
                //Check if the student is in the data
                if (data.getName().equals(student.getFullName())) {
                    data.setPieValue(student.getNonAttendancePercentage().get());
                    studentIsThere = true;
                }
            }
            if (!studentIsThere) {
                //If student isn't there, add the student
                addNewStudentToChartData(student);
            }
        }
        MainViewController.getInstance().updatePieData();
    }

    private void addNewStudentToChartData(Student student) {
        Data nonAttendance = new Data(student.getFullName(), student.getNonAttendancePercentage().get());
        pieChartData.add(nonAttendance);
    }

    /**
     * Find the students with nonAttendance and add them to the pirChartData
     */
    private void addNonAttendantStudentsToChartData() {
        //TODO ALH: Make sure than this can be dynamic for more classes
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
        computeTotalPieChartPercentage();
        return computedPieChartData;
    }

}
