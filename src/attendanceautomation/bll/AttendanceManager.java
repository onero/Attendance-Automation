
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
    private ArrayList<Data> studentData;

    private ArrayList<String> names;
    private ArrayList<Double> values;

    private double total;

    private double percent;

    private final DecimalFormat decimalFormatter;

    private final int ALL_SCHOOL_LESSONS;
    private DecimalFormat testFormatter;

    public AttendanceManager() {
        decimalFormatter = new DecimalFormat("#.##");
        testFormatter = new DecimalFormat("#.00");
        ALL_SCHOOL_LESSONS = SchoolClassModel.getInstance().getCurrentSchoolClass().getSemesterLessons().size();
    }

    /**
     * Generate an ObservableList<PieChart.Data> with inforation about each
     * student with their total nonattendance percentage
     *
     * @param pieChartData
     * @return
     */
    public ArrayList<Data> computeAllAttendance(ArrayList<Data> pieChartData) {
        allStudentsData = new ArrayList<>();
        names = new ArrayList<>();
        values = new ArrayList<>();
        addNamesAndValuesToArrays(pieChartData);
        total = 0;
        percent = 0;
        computeTotal();
        computeAllAttendancePercentage();
        return allStudentsData;
    }

    /**
     * Computate the total attendance percentage for each student, calculated on
     * the amount of students with NonAttendance
     */
    private void computeAllAttendancePercentage() {
        for (int i = 0; i < names.size(); i++) {
            //Calculate
            percent = (values.get(i) / total) * 100;

            formatDouble();

            Data computedData = new Data(names.get(i), percent);
            allStudentsData.add(computedData);
        }
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
    private void addNamesAndValuesToArrays(ArrayList<Data> pieChartData) {
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
    public ArrayList<Data> computeStudentAttendance(Student student) {
        percent = 0;
        studentData = new ArrayList<>();
        double amountOfStudentNonattendances = student.getNonAttendance().size();
        percent = ((amountOfStudentNonattendances / ALL_SCHOOL_LESSONS) * 100);
        formatDouble();
        double studentAttendancePercentage = (ALL_SCHOOL_LESSONS - amountOfStudentNonattendances);

        Data nonAttendance = new Data("Fravær", percent);

        Data attendance = new Data("Fremmøde", studentAttendancePercentage);

        studentData.add(nonAttendance);
        studentData.add(attendance);
        return studentData;
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
