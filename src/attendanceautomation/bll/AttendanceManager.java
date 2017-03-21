
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll;

import attendanceautomation.be.NonAttendance;
import attendanceautomation.be.SchoolClassSemesterLesson;
import attendanceautomation.be.Student;
import attendanceautomation.be.enums.ESchoolSubject;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import javafx.scene.chart.PieChart.Data;

public class AttendanceManager {

    private ArrayList<Data> allStudentsData;
    private ArrayList<Data> studentData;

    private ArrayList<String> names;
    private ArrayList<Double> values;

    private double total;

    private double percent;

    private final DecimalFormat decimalFormatter;

    public AttendanceManager() {
        decimalFormatter = new DecimalFormat("#.##");
    }

    private final int SCHOOL_LESSONS_IN_A_MONTH = 20;

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
        computeTotal();
        computeAllAttendancePercentage();
        return allStudentsData;
    }

    /**
     * Computate the total attendance percentage for each student, calculated on
     * the amount of students with NonAttendance
     */
    private void computeAllAttendancePercentage() {
        percent = 0;
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
        studentData = new ArrayList<>();
        double amountOfStudentNonattendances = student.getNonAttendance().size();
        double studentNonattendancePercentage = ((amountOfStudentNonattendances / SCHOOL_LESSONS_IN_A_MONTH) * 100);
        double studentAttendancePercentage = (SCHOOL_LESSONS_IN_A_MONTH - amountOfStudentNonattendances);

        Data nonAttendance = new Data("Fravær", studentNonattendancePercentage);

        Data attendance = new Data("Fremmøde", studentAttendancePercentage);

        studentData.add(nonAttendance);
        studentData.add(attendance);
        return studentData;
    }

}
