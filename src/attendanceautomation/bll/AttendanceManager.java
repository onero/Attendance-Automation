/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll;

import attendanceautomation.be.Student;
import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart.Data;

public class AttendanceManager {

    private final ObservableList<Data> allStudentsData;
    private final ObservableList<Data> studentData;

    private final ArrayList<String> names;
    private final ArrayList<Double> values;

    private double total;

    private double percent;

    private final int SCHOOL_DAYS_IN_A_MONTH = 20;

    public AttendanceManager() {
        allStudentsData = FXCollections.observableArrayList();
        studentData = FXCollections.observableArrayList();
        names = new ArrayList<>();
        values = new ArrayList<>();
    }

    /**
     * Generate an ObservableList<PieChart.Data> with inforation about each
     * student with their total nonattendance percentage
     *
     * @param pieChartData
     * @return
     */
    public ObservableList<Data> computeAllAttendance(ObservableList<Data> pieChartData) {
        allStudentsData.clear();
        addNamesToArrays(pieChartData);
        total = 0;
        computeTotal();
        computeAllAttendancePercentage();
        return allStudentsData;
    }

    private void computeAllAttendancePercentage() {
        percent = 0;
        for (int i = 0; i < names.size(); i++) {
            percent = (total / values.get(i)) * 100;
            Data computedData = new Data(names.get(i), percent);
            allStudentsData.add(computedData);
        }
    }

    /**
     * From all the values in the array compute the total amount
     */
    private void computeTotal() {
        for (Double value : values) {
            total += value;
        }
    }

    /**
     * Extract names and values from the chartdata and fill the corresponding
     * arrays
     *
     * @param pieChartData
     */
    private void addNamesToArrays(ObservableList<Data> pieChartData) {
        for (Data data : pieChartData) {
            names.add(data.getName());
            values.add(data.getPieValue());
        }
    }

    /**
     * Calculate the attendance of the student
     *
     * @param student
     * @return attendance
     */
    public ObservableList<Data> computeStudentAttendance(Student student) {
        studentData.clear();
        double amountOfStudentNonattendances = student.getNonAttendance().size();
        DoubleProperty studentAttendancePercentage = new SimpleDoubleProperty(SCHOOL_DAYS_IN_A_MONTH - amountOfStudentNonattendances);
        DoubleProperty studentNonattendancePercentage = new SimpleDoubleProperty((amountOfStudentNonattendances / 20) * 100);

        Data nonAttendance = new Data("Fravær", 0);
        nonAttendance.pieValueProperty().bind(studentNonattendancePercentage);

        Data attendance = new Data("Fremmøde", 0);
        attendance.pieValueProperty().bind(studentAttendancePercentage);

        studentData.add(nonAttendance);
        studentData.add(attendance);
        return studentData;
    }

}
