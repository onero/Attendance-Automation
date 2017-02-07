/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

public class AttendanceManager {

    private ObservableList<PieChart.Data> studentData;

    private ArrayList<String> names;
    private ArrayList<Double> values;

    private double total;

    private double percent;

    /**
     * Generate an ObservableList<PieChart.Data> with inforation about each
     * student with their total nonattendance percentage
     *
     * @param pieChartData
     * @return
     */
    public ObservableList<PieChart.Data> computeAttendance(ObservableList<PieChart.Data> pieChartData) {
        studentData = FXCollections.observableArrayList();
        names = new ArrayList<>();
        values = new ArrayList<>();
        addNamesToArrays(pieChartData);
        total = 0;
        computeTotal();
        percent = 0;
        createPieChartDataForEachStudent();
        return studentData;
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
            PieChart.Data pieChartEntry = new PieChart.Data(names.get(i), Math.round(percent));
            studentData.add(pieChartEntry);
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

}