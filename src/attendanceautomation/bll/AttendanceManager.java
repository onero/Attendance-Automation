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
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;

public class AttendanceManager {

    private final ObservableList<PieChart.Data> allStudentsData;
    private final ObservableList<PieChart.Data> studentData;

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
    public ObservableList<PieChart.Data> computeAllAttendance(ObservableList<PieChart.Data> pieChartData) {
        addNamesToArrays(pieChartData);
        total = 0;
        computeTotal();
        percent = 0;
//        createPieChartDataForEachStudent();
        return allStudentsData;
    }

    /**
     * For each student copmute the nonattendance percentage and add their name
     * and percentage to the piechartdata
     */
    private void createPieChartDataForEachStudent() {
        //For each value find the percentage compared to the total value
        for (int i = 0; i < names.size(); i++) {
            //Calculate the percent for the person
            percent = (values.get(i) / total) * 100;
            //Create new entry for the person
            Data pieChartEntry = new PieChart.Data(names.get(i), Math.round(percent));
            allStudentsData.add(pieChartEntry);
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
    private void addNamesToArrays(ObservableList<PieChart.Data> pieChartData) {
        for (PieChart.Data data : pieChartData) {
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
