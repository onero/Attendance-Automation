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

public class Student implements Comparable<Student> {

    private final DoubleProperty nonAttendancePercentage;

    private final int ID;

    private final String firstName;

    private final String lastName;

    private final String fullName;

    private final String email;

    //TODO MSP: make a field for field of study.
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
     * @param nonAttendanceToRemove
     */
    public void removeNonAttendance(NonAttendance nonAttendanceToRemove) {
        //Remove nonAttendance if the lesson id match
        nonAttendance.removeIf(a
                -> a.getSchoolClassSemesterLesson().getID()
                == nonAttendanceToRemove.getSchoolClassSemesterLesson().getID());

        updateNonAttendancePercentage();
        PieChartModel.getInstance().checkIfStudentIsInChart(this);
    }

    /**
     * Update the nonAttendancePercentage
     */
    private void updateNonAttendancePercentage() {
        AttendanceManager manager = new AttendanceManager();
        nonAttendancePercentage.set(manager.computeStudentAttendance(this));
    }

    /**
     * Compare this student to another student on first name
     *
     * @param s
     * @return
     */
    @Override
    public int compareTo(Student s) {
        return this.getFirstName().
                compareToIgnoreCase(s.getFirstName());
    }

}
