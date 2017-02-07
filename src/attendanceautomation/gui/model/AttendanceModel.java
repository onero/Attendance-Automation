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

        //TODO ALH: Make sure than this can be dynamic for more classes
        for (Student student : SchoolClassModel.getInstance().getSchoolClasses().get(0).getStudents()) {
            if (student.getAttendancePercentage() > 0) {
                PieChart.Data pieChartEntry = new PieChart.Data(student.getFullName(), student.getAttendancePercentage());
                pieChartData.add(pieChartEntry);
            }
        }
        computedPieChartData.addAll(attendanceManager.computeAttendance(pieChartData));
        pieChartData = computedPieChartData;
    }

    /**
     *
     * @return piechart data
     */
    public ObservableList<PieChart.Data> getPieChartData() {
        return pieChartData;
    }

}
