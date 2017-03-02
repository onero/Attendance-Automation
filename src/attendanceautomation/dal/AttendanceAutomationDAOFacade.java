/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.dal;

import attendanceautomation.be.NonAttendance;
import attendanceautomation.be.SchoolClass;
import attendanceautomation.be.SchoolSemesterSubject;
import attendanceautomation.be.Student;
import attendanceautomation.bll.SchoolClassManager;
import java.sql.SQLException;
import java.util.List;
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

        return schoolClass;
    }

    /**
     * Add nonattendance for each student
     */
    private void addNonAttendanceToStudents() {
        //For each student
        for (Student student : schoolClass.getStudents()) {
            //Get a hold of their attendance
            List<NonAttendance> nonAttendanceForStudent = attendanceDAO.getAllNonAttendanceForASpecificStudent(student.getID());
            for (NonAttendance nonAttendance : nonAttendanceForStudent) {
                student.addNonAttendance(nonAttendance);
            }
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
            schoolClass.addAllSemesterSubjectsToClass(schoolClassDAO.getAllSchoolSemesterSubjectsFromSpecificSchoolClass(schoolClassID));
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

    /**
     * Get all students from DB
     *
     * @return
     */
    private List<Student> getAllSchoolClassStudents() {
        try {
            return studentDAO.getAllSchoolClassStudents();
        } catch (SQLException ex) {
            System.out.println("Couldn't load students");
            Logger.getLogger(SchoolClassManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Get all SchoolClasses from DB
     *
     * @return
     */
    private List<SchoolClass> getAllSchoolClasses() {
        try {
            return schoolClassDAO.getAllSchoolClasses();
        } catch (SQLException ex) {
            System.out.println("Couldn't load SchoolClasses");
            Logger.getLogger(SchoolClassManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Get all SchoolClasses from DB
     *
     * @return
     */
    private List<SchoolSemesterSubject> getAllSchoolClassSemesterSubjects() {
        try {
            return schoolClassDAO.getAllSchoolSemesterSubjects();
        } catch (SQLException ex) {
            Logger.getLogger(SchoolClassManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
