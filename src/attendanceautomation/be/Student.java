/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be;

import attendanceautomation.bll.AttendanceManager;
import attendanceautomation.bll.EmailFactory;
import attendanceautomation.bll.IDFactory;
import attendanceautomation.gui.model.AttendanceModel;
import attendanceautomation.gui.model.SchoolClassModel;
import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.chart.PieChart.Data;

public class Student {

//    private double nonAttendancePercentage;
    private final DoubleProperty nonAttendancePercentage;

    private final int ID;

    private final String firstName;

    private final String lastName;

    private final String fullName;

    private final String email;

    private final AttendanceManager manager;

    private final ArrayList<NonAttendance> nonAttendance;

    private int phone;

    public Student(String firstName, String lastName) {
        ID = IDFactory.getNewID();
        this.firstName = firstName;
        this.lastName = lastName;
        fullName = this.firstName + " " + this.lastName;
        email = EmailFactory.getnewEmail(ID, firstName);
        nonAttendance = new ArrayList<>();
        nonAttendancePercentage = new SimpleDoubleProperty(0);
        manager = new AttendanceManager();

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
     * @return phone number
     */
    public int getPhone() {
        return phone;
    }

    /**
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Update the phone number
     *
     * @param mPhone
     */
    public void setPhone(int mPhone) {
        this.phone = mPhone;
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
        AttendanceModel.getInstance().checkIfStudentIsInChart(this);
        SchoolClassModel.getInstance().sortStudentsOnAttendance();
    }

    /**
     * Adds the parsed day in week to nonAttendance
     *
     * @param attendance
     */
    public void removeNonAttendance(NonAttendance attendance) {
        for (NonAttendance nonAttend : nonAttendance) {
            if (nonAttend.getWeekWithoutAttendance() == attendance.getWeekWithoutAttendance()) {
                if (nonAttend.getDayWithoutAttendance() == attendance.getDayWithoutAttendance()) {
                    if (nonAttend.getLessonWithoutAttendance() == attendance.getLessonWithoutAttendance()) {
                        nonAttendance.remove(nonAttend);
                        break;
                    }
                }
            }
        }
        updateNonAttendancePercentage();
        SchoolClassModel.getInstance().sortStudentsOnAttendance();
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
