/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.model;

import attendanceautomation.be.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

public class AttendanceModel {

    private final ObservableList<PieChart.Data> pieChartData;

    public AttendanceModel() {
        pieChartData = FXCollections.observableArrayList();

        for (Student student : SchoolClassModel.getInstance().getSchoolClasses().get(0).getStudents()) {
            if (student.getAttendancePercentage() > 0) {
                PieChart.Data pieChartEntry = new PieChart.Data(student.getFullName(), student.getAttendancePercentage());
                pieChartData.add(pieChartEntry);
            }
        }
    }

    public ObservableList<PieChart.Data> getPieChartData() {
        return pieChartData;
    }

}
