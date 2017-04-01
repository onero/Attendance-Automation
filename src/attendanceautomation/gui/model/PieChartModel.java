/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.model;

import attendanceautomation.be.Student;
import attendanceautomation.bll.AttendanceManager;
import attendanceautomation.gui.views.sharedComponents.pieChart.controller.PieChartViewController;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;

public class PieChartModel {

    private static PieChartModel instance;

    private final SchoolClassModel schoolClassModel;

    private final AttendanceManager attendanceManager;

    private final ArrayList<Data> pieChartData;

    private final ObservableList<Data> computedPieChartData;

    private ObservableList<Data> currentClassPieChartData;

    public static PieChartModel getInstance() {
        if (instance == null) {
            instance = new PieChartModel();
        }
        return instance;
    }

    public PieChartModel() {
        pieChartData = new ArrayList<>();

        schoolClassModel = SchoolClassModel.getInstance();

        computedPieChartData = FXCollections.observableArrayList();

        attendanceManager = new AttendanceManager();

        currentClassPieChartData = FXCollections.observableArrayList();
    }

    /**
     * Calculates the total attendance for the student and returns the
     * percentage as a double
     *
     * @param student
     * @return
     */
//    public List<Data> getStudentAttendance(Student student) {
//        return attendanceManager.computeStudentAttendance(student);
//    }
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
        //Check if there are students in the pieChart
        if (pieChartData.isEmpty()) {
            addNewStudentToChartData(student);
        } else {
            if (studentNotInChart(student)) {
                //If student isn't there, add the student
                addNewStudentToChartData(student);
            } else {
                //Update the student in the chart
                pieChartData.stream()
                        .filter(data -> data.getName().equals(student.getFullName()))
                        .forEach(s -> s.setPieValue(student.getNonAttendancePercentage().get()));
//                for (Data data : pieChartData) {
//                    //Check if the student is in the data
//                    if (data.getName().equals(student.getFullName())) {
//                        data.setPieValue(student.getNonAttendancePercentage().get());
//                        break;
//                    }
//                }
            }

        }
        PieChartViewController.getInstance().updateChart();
    }

    /**
     * Check if student is not in chart
     *
     * @param student
     * @return
     */
    private boolean studentNotInChart(Student student) {
        return pieChartData.stream().noneMatch(s -> s.getName().equals(student.getFullName()));
    }

    private void addNewStudentToChartData(Student student) {
        Data nonAttendance = new Data(student.getFullName(), student.getNonAttendancePercentage().get());
        pieChartData.add(nonAttendance);
    }

    /**
     * Find the students with nonAttendance and add them to the pirChartData
     */
    private void addNonAttendantStudentsToChartData() {
        List<Student> currentSchoolClassStudents = schoolClassModel.getCurrentSchoolClass().getStudents();

        for (Student student : currentSchoolClassStudents) {
            if (student.getNonAttendancePercentage().get() > 0) {
                addNewStudentToChartData(student);
            }
        }
    }

    /**
     * Reset all pieChartData and update PieChart
     */
    public void resetPieChart() {
        pieChartData.clear();
        computedPieChartData.clear();
        addNonAttendantStudentsToChartData();
    }

    /**
     *
     * @return piechart data
     */
    public ObservableList<Data> getPieChartData() {
        return computedPieChartData;
    }

    /**
     * Gets the pieChartData for the currentClass pieChart.
     *
     * @return
     */
    public ObservableList<Data> getCurrentClassPieChartData() {
        return currentClassPieChartData;
    }

    /**
     * Clears currentClassPieChartData and then sets the new data.
     *
     * @param list
     */
    public void setCurrentClassPieChartData(ObservableList<Data> list) {
        currentClassPieChartData.clear();
        currentClassPieChartData = list;
    }

    /**
     * Find the percentage of attendace and updates the currentClassPieChart.
     *
     * @param present
     * @param absence
     */
    void updateCurrentClassPieChat(List<Student> present, List<Student> absence) {
        double presentInProcent = attendanceManager.calculatePresentProcent(present, absence);
        double absenceInProcent = 100 - presentInProcent;

        currentClassPieChartData.clear();
        currentClassPieChartData.addAll(new PieChart.Data("Tilstedeværende", presentInProcent),
                new PieChart.Data("Fraværende", absenceInProcent));
    }

}
