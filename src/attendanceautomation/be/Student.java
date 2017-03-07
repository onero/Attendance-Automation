/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be;

import attendanceautomation.bll.AttendanceManager;
import attendanceautomation.gui.model.PieChartModel;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.chart.PieChart.Data;

public class Student {

    private final DoubleProperty nonAttendancePercentage;

    private final int ID;

    private final String firstName;

    private final String lastName;

    private final String fullName;

    private final String email;

    private final ArrayList<NonAttendance> nonAttendance;

    /**
     * Create real student from DB
     *
     * @param id
     * @param firstName
     * @param lastName
     * @param email
     */
    public Student(int id, String firstName, String lastName, String email) {
        ID = id;
        this.firstName = firstName;
        this.lastName = lastName;
        fullName = this.firstName + " " + this.lastName;
        this.email = email;
        nonAttendance = new ArrayList<>();
        nonAttendancePercentage = new SimpleDoubleProperty(0);
    }

    public int getID() {
        return ID;
    }

    /**
     *
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @return full name as First name combined with last name, seperated by
     * space
     */
    public String getFullName() {
        return fullName;
    }

    /**
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @return attendance percentage
     */
    public DoubleProperty getNonAttendancePercentage() {
        if (nonAttendance.size() > 0) {
            updateNonAttendancePercentage();
        }
        return nonAttendancePercentage;
    }

    /**
     *
     * @return nonAttendance
     */
    public ArrayList<NonAttendance> getNonAttendance() {
        return nonAttendance;
    }

    /**
     * Adds the parsed day in week to nonAttendance
     *
     * @param newNonAttendance
     */
    public void addNonAttendance(NonAttendance newNonAttendance) {
        nonAttendance.add(newNonAttendance);
        updateNonAttendancePercentage();
        PieChartModel.getInstance().checkIfStudentIsInChart(this);
    }

    public void addAllNonAttendance(List<NonAttendance> allNonAttendance) {
        nonAttendance.addAll(allNonAttendance);
    }

    /**
     * Adds the parsed day in week to nonAttendance
     *
     * @param attendance
     */
    public void removeNonAttendance(NonAttendance attendance) {
        for (NonAttendance nonAttendance1 : nonAttendance) {
            if (nonAttendance1.getSchoolClassSemesterLesson().getID() == attendance.getSchoolClassSemesterLesson().getID()) {
                nonAttendance.remove(nonAttendance1);
                break;
            }
        }
        updateNonAttendancePercentage();
        PieChartModel.getInstance().checkIfStudentIsInChart(this);
    }

    /**
     * Update the nonAttendancePercentage
     */
    private void updateNonAttendancePercentage() {
        AttendanceManager manager = new AttendanceManager();
        Data computedNonAttendance = manager.computeStudentAttendance(this).get(0);
        nonAttendancePercentage.set(computedNonAttendance.getPieValue());
    }

}
