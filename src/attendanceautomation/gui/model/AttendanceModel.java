/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.model;

import attendanceautomation.be.Student;
import attendanceautomation.bll.AttendanceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

public class AttendanceModel {

    private static AttendanceModel instance;

    private final AttendanceManager attendanceManager;

    private ObservableList<PieChart.Data> pieChartData;

    private final ObservableList<PieChart.Data> computedPieChartData;

    public static AttendanceModel getInstance() {
        if (instance == null) {
            instance = new AttendanceModel();
        }
        return instance;
    }

    public AttendanceModel() {
        pieChartData = FXCollections.observableArrayList();
        computedPieChartData = FXCollections.observableArrayList();

        attendanceManager = new AttendanceManager();

        addNonAttendantStudentsToChartData();

        //Get percentage data back
        computedPieChartData.addAll(attendanceManager.computeAllAttendance(pieChartData));
        pieChartData = computedPieChartData;
    }

    /**
     * Calculates the total attendance for the student and returns the
     * percentage as a double
     *
     * @param student
     * @return
     */
    public ObservableList<PieChart.Data> getStudentAttendance(Student student) {
        return attendanceManager.computeStudentAttendance(student);
    }

    /**
     * Find the students with nonAttendance and add them to the pirChartData
     */
    private void addNonAttendantStudentsToChartData() {
        //TODO ALH: Make sure than this can be dynamic for more classes
        for (Student student : SchoolClassModel.getInstance().getSchoolClasses().get(0).getStudents()) {
            if (student.getNonAttendancePercentage() > 0) {
                PieChart.Data pieChartEntry = new PieChart.Data(student.getFullName(), student.getNonAttendancePercentage());
                pieChartData.add(pieChartEntry);
            }
        }
    }

    /**
     *
     * @return piechart data
     */
    public ObservableList<PieChart.Data> getPieChartData() {
        return pieChartData;
    }

}
