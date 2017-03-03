/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.dal;

import attendanceautomation.be.NonAttendance;
import attendanceautomation.be.SchoolClass;
import attendanceautomation.be.Student;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AttendanceAutomationDAOFacade {

    private static AttendanceAutomationDAOFacade instance;

    private final StudentDAO studentDAO;
    private final SchoolClassDAO schoolClassDAO;
    private final AttendanceDAO attendanceDAO;

    private SchoolClass schoolClass;

    public static AttendanceAutomationDAOFacade getInstance() {
        if (instance == null) {
            instance = new AttendanceAutomationDAOFacade();
        }
        return instance;
    }

    private AttendanceAutomationDAOFacade() {
        studentDAO = StudentDAO.getInstance();
        schoolClassDAO = SchoolClassDAO.getInstance();
        attendanceDAO = AttendanceDAO.getInstance();
    }

    /**
     * Get all SchoolClasses with data
     *
     * @param id
     * @return
     */
    public SchoolClass getSchoolClassDataForSpecificSchoolClass(int id) {
        schoolClass = getSchoolClassByID(id);

        addStudentsInSchoolClass(schoolClass.getID());

        addNonAttendanceToStudents();

        addSchoolSemesterSubjectsInSchoolClass(schoolClass.getID());

        addSchoolSemesterLessonsInSchoolClass(schoolClass.getID());

        return schoolClass;
    }

    /**
     * Add NonAttendance to student
     *
     * @param newNonAttendance
     */
    public void addNewNonAttendance(NonAttendance newNonAttendance) {
        attendanceDAO.addNonAttendance(newNonAttendance);
    }

    /**
     * Remove NonAttendance for specific student
     *
     * @param nonAttendanceToRemove
     */
    public void removeNonAttendanceFromSpecificStudent(NonAttendance nonAttendanceToRemove) {
        attendanceDAO.removeNonAttendance(nonAttendanceToRemove);
    }

    /**
     * Add nonattendance for each student
     */
    private void addNonAttendanceToStudents() {
        //For each student
        for (Student student : schoolClass.getStudents()) {
            //Get a hold of their attendance
            student.addAllNonAttendance(attendanceDAO.getAllNonAttendanceForASpecificStudent(student.getID()));
        }
    }

    /**
     * Add data for all the students in the specific SchoolClass
     *
     * @param schoolClassID
     */
    private void addStudentsInSchoolClass(int schoolClassID) {
        try {
            schoolClass.addAllStudents(studentDAO.getAllSchoolClassStudentsFromSpecificSchoolClass(schoolClassID));
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceAutomationDAOFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Add data for all the SchoolSemesterSubject
     */
    private void addSchoolSemesterSubjectsInSchoolClass(int schoolClassID) {
        try {
            schoolClass.addAllSemesterSubjects(schoolClassDAO.getAllSchoolSemesterSubjectsFromSpecificSchoolClass(schoolClassID));
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceAutomationDAOFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Add data for all the SchoolSemesterSubject
     */
    private void addSchoolSemesterLessonsInSchoolClass(int schoolClassID) {
        try {
            schoolClass.addAllSemesterLessonsToClass(schoolClassDAO.getAllSchoolSemesterLessonsFromSpecificSchoolClass(schoolClassID));
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceAutomationDAOFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get a specific SchoolCLass by its ID
     *
     * @param id
     * @return
     */
    private SchoolClass getSchoolClassByID(int id) {
        try {
            return schoolClassDAO.getSchoolClassByID(id);
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceAutomationDAOFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
