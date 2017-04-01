
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll;

import attendanceautomation.be.Student;
import attendanceautomation.gui.model.SchoolClassModel;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.chart.PieChart.Data;

public class AttendanceManager {

    private ArrayList<Data> allStudentsData;

    private double total;

    private double percent;

    private final DecimalFormat decimalFormatter;

    private int ALL_SCHOOL_LESSONS;

    public AttendanceManager() {
        decimalFormatter = new DecimalFormat("#.##");
    }

    /**
     * Generate an ObservableList<PieChart.Data> with inforation about each
     * student with their total nonattendance percentage
     *
     * @param pieChartData
     * @return
     */
    public ArrayList<Data> computeAllAttendance(List<Data> pieChartData) {
        allStudentsData = new ArrayList<>();
        total = 0;
        pieChartData.stream().forEach(s -> total += s.getPieValue());

        computeAllAttendancePercentage(pieChartData);
        return allStudentsData;
    }

    /**
     * Computate the total attendance percentage for each student, calculated on
     * the amount of students with NonAttendance
     */
    private void computeAllAttendancePercentage(List<Data> pieChartData) {
        pieChartData.stream()
                .forEach(s -> allStudentsData.add(new Data(
                s.getName(),
                s.getPieValue() / total * 100)));
    }

    /**
     * Format number according to right format
     */
    private void formatDouble() {
        String percentFormatted = decimalFormatter.format(percent);
        try {
            //Parse formatted string to double
            percent = decimalFormatter.parse(percentFormatted).doubleValue();
        } catch (ParseException ex) {
            System.out.println("Cannot convert number " + ex);
        }
    }

    /**
     * Calculate the attendance of the student
     *
     * @param student
     * @return attendance
     */
    public double computeStudentAttendance(Student student) {
        //Get a hold of all lessons
        ALL_SCHOOL_LESSONS = SchoolClassModel.getInstance().getCurrentSchoolClass().getSemesterLessons().size();
        //Get a hold of all the students nonAttendance
        double amountOfStudentNonattendances = student.getNonAttendance().size();
        //Calculate students absence in percent
        percent = ((amountOfStudentNonattendances / ALL_SCHOOL_LESSONS) * 100);
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
