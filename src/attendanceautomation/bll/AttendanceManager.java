
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll;

import attendanceautomation.be.Student;
import attendanceautomation.gui.model.SchoolClassModel;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.chart.PieChart.Data;

public class AttendanceManager {

    private ArrayList<Data> allStudentsData;

    private double total;

    private double percent;

    private int amountOfSchoolLessons;

    /**
     * Generate an ObservableList<PieChart.Data> with inforation about each
     * student with their total nonattendance percentage
     *
     * @param pieChartData
     * @return
     */
    public ArrayList<Data> computeAllAttendance(List<Data> pieChartData) {
        allStudentsData = new ArrayList<>();
        computeTotal(pieChartData);
        computeAllAttendancePercentage(pieChartData);
        return allStudentsData;
    }

    /**
     * Calculate the total of all nonAttendance
     *
     * @param pieChartData
     */
    private void computeTotal(List<Data> pieChartData) {
        total = 0;
        pieChartData.stream().forEach(chartData -> total += chartData.getPieValue());
    }

    /**
     * Computate the total attendance percentage for each student, calculated on
     * the amount of students with NonAttendance
     */
    private void computeAllAttendancePercentage(List<Data> pieChartData) {
        pieChartData.stream()
                //For each of the students in the chartData
                .forEach(chartData -> allStudentsData.add(new Data(
                //Create new data with the nonAttendance percentage compared to other students
                chartData.getName(),
                //value divided by total times 100 for percent
                chartData.getPieValue() / total * 100)));
    }

    /**
     * Calculate the attendance of the student
     *
     * @param student
     * @return attendance
     */
    public double computeStudentAttendance(Student student) {
        //Get a hold of all lessons
        amountOfSchoolLessons = SchoolClassModel.getInstance().getCurrentSchoolClass().getSemesterLessons().size();
        //Get a hold of all the students nonAttendance
        double amountOfStudentNonattendances = student.getNonAttendance().size();
        //Calculate students absence in percent
        percent = (amountOfStudentNonattendances / amountOfSchoolLessons) * 100;
        return percent;
    }

    /**
     * Calculates the procent of students precent.
     *
     * @param presentList
     * @param absenceList
     * @return
     */
    public double calculatePresentProcent(List<Student> presentList, List<Student> absenceList) {
        double present = presentList.size();
        double absence = absenceList.size();
        return Math.round((present / (present + absence)) * 100);
    }

}
