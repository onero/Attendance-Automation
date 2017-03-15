/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.dal;

import attendanceautomation.be.Academy;
import attendanceautomation.be.NonAttendance;
import attendanceautomation.be.SchoolClass;
import attendanceautomation.be.SchoolClassSemesterLesson;
import attendanceautomation.be.SchoolSemesterSubject;
import attendanceautomation.be.Student;
import attendanceautomation.be.Teacher;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AttendanceAutomationDAOFacade {

    private static AttendanceAutomationDAOFacade instance;

    private final StudentDAO studentDAO;
    private final SchoolClassDAO schoolClassDAO;
    private final AttendanceDAO attendanceDAO;
    private final LoginDAO loginDAO;

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
        loginDAO = LoginDAO.getInstance();
    }

    /**
     * Update information for schoolclass students
     *
     * @param schoolClassId
     * @return
     */
    public List<Student> getStudentsFromSchoolClass(int schoolClassId) {
        return getStudentsInSchoolClass(schoolClassId);
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
     * Add data for all the students in the specific SchoolClass
     *
     * @param schoolClassID
     */
    private List<Student> getStudentsInSchoolClass(int schoolClassID) {
        try {
            return studentDAO.getAllSchoolClassStudentsFromSpecificSchoolClass(schoolClassID);
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceAutomationDAOFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Add data for all the SchoolSemesterSubject
     *
     * @param schoolClassID
     * @return
     */
    public List<SchoolSemesterSubject> getSchoolSemesterSubjectsInSchoolClass(int schoolClassID) {
        try {
            return schoolClassDAO.getAllSchoolSemesterSubjectsFromSpecificSchoolClass(schoolClassID);
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceAutomationDAOFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Add data for all the SchoolSemesterSubject
     *
     * @param schoolClassID
     * @return
     */
    public List<SchoolClassSemesterLesson> getSchoolSemesterLessonsInSchoolClass(int schoolClassID) {
        try {
            return schoolClassDAO.getAllSchoolSemesterLessonsFromSpecificSchoolClass(schoolClassID);
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceAutomationDAOFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Get a specific SchoolCLass by its ID
     *
     * @param id
     * @return
     */
    public SchoolClass getSchoolClassByID(int id) {
        try {
            return schoolClassDAO.getSchoolClassByID(id);
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceAutomationDAOFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Get a specific SchoolClass by a studentID
     *
     * @param studentEmail
     * @return
     */
    public SchoolClass getSchoolClassFromStudentEmail(String studentEmail) {
        try {
            return schoolClassDAO.getSchoolClassIdByStudentEmail(studentEmail);
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceAutomationDAOFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Get one student by email
     *
     * @param studentEmail
     * @return
     */
    public Student getStudentByEmail(String studentEmail) {
        try {
            return studentDAO.getStudentByEmail(studentEmail);
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceAutomationDAOFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Get all nonattendance for specific student
     *
     * @param studentID
     * @return
     */
    public List<NonAttendance> getNonAttendanceForStudentByID(int studentID) {
        return attendanceDAO.getAllNonAttendanceForASpecificStudent(studentID);
    }

    /**
     * Check if the user is a Teacher
     *
     * @param userEmail
     * @return
     */
    public boolean isTeacher(String userEmail) {
        boolean isTeacher = false;
        try {
            isTeacher = loginDAO.checkIfUserIsTeacher(userEmail);
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceAutomationDAOFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isTeacher;
    }

    /**
     * Check if user is in DB
     *
     * @param userEmail
     * @return
     */
    public boolean isUserInDB(String userEmail) {
        try {
            return loginDAO.checkIfUserIsInDB(userEmail);
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceAutomationDAOFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Get All locations from given academy
     *
     * @param currentAcademy
     * @param teacher
     * @return
     */
    public HashMap<Integer, String> loadAcademyLocationsTeacherIsTeaching(Academy currentAcademy, Teacher teacher) {
        try {
            return schoolClassDAO.loadAcademyLocationsTeacherIsTeaching(currentAcademy, teacher);
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceAutomationDAOFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Get all schoolClass ids by location
     *
     * @param currentLocationID
     * @param teacherID
     * @return
     */
    public HashMap<Integer, String> getSchoolClassHashMapByLocationAndTeacher(int currentLocationID, int teacherID) {
        try {
            return schoolClassDAO.getSchoolClassHashMapByLocationAndTeacher(currentLocationID, teacherID);
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceAutomationDAOFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Get a teacher from DB by adamlars90@gmail.coms
     *
     * @param teacherEmail
     * @return
     */
    public Teacher getTeacherByEmail(String teacherEmail) {
        try {
            return schoolClassDAO.getTeacherByEmail(teacherEmail);
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceAutomationDAOFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Finds the schoolClass for the parsed teacher on the parsed Date. Then
     * retrieves a list of students from that schoolClass.
     *
     * @param teacherID
     * @param date
     * @return
     */
    public List<Student> getStudentsFromCurrentClass(int teacherID, String date) {
        try {
            int schoolClassID = schoolClassDAO.getSchoolClassIDForSpecificTeacherAndDate(teacherID, date);
            return studentDAO.getAllSchoolClassStudentsFromSpecificSchoolClass(schoolClassID);
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceAutomationDAOFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
