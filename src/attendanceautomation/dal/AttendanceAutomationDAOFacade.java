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
import com.microsoft.sqlserver.jdbc.SQLServerException;
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

    public List<String> getAllSchoolClassSemestersBySchoolClassName(String schoolClassName) {
        try {
            return schoolClassDAO.getAllSchoolClassSemestersBySchoolClassName(schoolClassName);
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceAutomationDAOFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int getSchoolClassIdByName(String schoolClassName) {
        try {
            return schoolClassDAO.getSchoolClassIdByName(schoolClassName);
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceAutomationDAOFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public List<SchoolClassSemesterLesson> getSchoolSemesterLessonsInSchoolClassForSpecificPeriod(int schoolClassId, String startDate, String endDate) {
        try {
            return schoolClassDAO.getAllSchoolSemesterLessonsFromSpecificSchoolClassForSpecificPeriod(schoolClassId, startDate, endDate);
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceAutomationDAOFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<SchoolSemesterSubject> getSchoolSemesterSubjectsInSchoolClassForSpecificPeriod(int schoolClassId, String startDate, String endDate) {
        try {
            return schoolClassDAO.getAllSchoolSemesterSubjectsFromSpecificSchoolClassForSpecificPeriod(schoolClassId, startDate, endDate);
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceAutomationDAOFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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
     * Get all nonAttendance for specific students for specific period.
     *
     * @param studentID
     * @param startDate
     * @param endDate
     * @return
     */
    public List<NonAttendance> getNonAttendanceForStudentsByIDForSpecificPeriod(int studentID, String startDate, String endDate) {
        return attendanceDAO.getAllNonAttendanceForASpecificStudentForASpecificDate(studentID, startDate, endDate);
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
    public boolean isUserInDB(String userEmail) throws SQLException, SQLServerException {
        return loginDAO.checkIfUserIsInDB(userEmail);
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
     * @param dateEigthHoursBefore
     * @param dateHalfHourAfter
     * @return
     */
    public List<Integer> getSchoolClassID(int teacherID, String dateEigthHoursBefore, String dateHalfHourAfter) {
        try {
            return schoolClassDAO.getSchoolClassIDForSpecificTeacherAndDate(teacherID, dateEigthHoursBefore, dateHalfHourAfter);
        } catch (SQLException ex) {
            System.out.println(ex);
            throw new RuntimeException("Couldn't get the schoolClassID");
        }
    }

    /*
     * Get all teacher schoolClassNames for specific semester
     *
     * @param schoolClassIDs
     * @param semester
     * @return
     */
    public List<String> getAllTeacherSchoolClassesBySemester(List<Integer> schoolClassIDs, String semesterName) {
        try {
            return schoolClassDAO.getAllTeacherSchoolClassesBySemester(schoolClassIDs, semesterName);
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceAutomationDAOFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Gets lessons with the params from the DB.
     *
     * @param schoolClassID
     * @param semesterID
     * @return
     */
    public List<SchoolClassSemesterLesson> getSchoolClassSemesterLessonsBySchoolClassIDAndSemesterID(int schoolClassID, int semesterID) {
        try {
            return schoolClassDAO.getAllSchoolClassSemesterLessonsBySchoolClassIDAndSemesterID(schoolClassID, semesterID);
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceAutomationDAOFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Gets subjects with the params from the DB.
     *
     * @param schoolClassID
     * @param semesterID
     * @return
     */
    public List<SchoolSemesterSubject> getSchoolClassSemesterSubjectsBySchoolCLassIDAndSemesterID(int schoolClassID, int semesterID) {
        try {
            return schoolClassDAO.getAllSchoolClassSemesterSubjectsBySchoolClassIDAndSemesterID(schoolClassID, semesterID);
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceAutomationDAOFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Converts a semester name to an ID.
     *
     * @param semesterName
     * @return
     */
    public int getSemesterIDByName(String semesterName) {
        try {
            return schoolClassDAO.getSemesterIDByName(semesterName);
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceAutomationDAOFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Gets the Nonattendance for a specific student.
     *
     * @param StudentID
     * @param semesterID
     * @return
     */
    public List<NonAttendance> getAllNonAttendanceForStudentBySemester(int StudentID, int semesterID) {
        return attendanceDAO.getAllNonAttendanceForASpecificStudentBySemester(StudentID, semesterID);
    }

    /**
     * Calls the SchoolClassDao and gets one teacher by their name.
     *
     * @param teacherName
     * @return
     */
    public Teacher getOneTeacherByName(String teacherName) {
        try {
            return schoolClassDAO.getOneTeacherByName(teacherName);
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceAutomationDAOFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
