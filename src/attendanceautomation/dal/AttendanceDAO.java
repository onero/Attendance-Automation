/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.dal;

import attendanceautomation.be.NonAttendance;
import attendanceautomation.be.SchoolClassSemesterLesson;
import attendanceautomation.be.SchoolSemesterSubject;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AttendanceDAO {

    private static AttendanceDAO instance;

    private List<SchoolSemesterSubject> schoolSemesterSubjects;

    private DBConnectionManager cm;

    public static AttendanceDAO getInstance() {
        if (instance == null) {
            instance = new AttendanceDAO();
        }
        return instance;
    }

    private AttendanceDAO() {
        cm = null;
        try {
            cm = DBConnectionManager.getInstance();
        } catch (IOException ex) {
            System.out.println("Couldn't make connection to DB");
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get a list of all NonAttendance for a specific studentttt
     *
     * @param studentID
     * @return
     */
    public List<NonAttendance> getAllNonAttendanceForASpecificStudent(int studentID) {
        try {
            List<SchoolClassSemesterLesson> schoolClassSemesterLessonsForStudent = getAllSchoolClassSemesterLessonsASpecificStudentDidNotAttend(studentID);
            List<NonAttendance> nonAttendanceForSpecificStudent;
            nonAttendanceForSpecificStudent = new ArrayList<>();
            for (SchoolClassSemesterLesson schoolClassSemesterLesson : schoolClassSemesterLessonsForStudent) {
                NonAttendance newNonAttendance = new NonAttendance(schoolClassSemesterLesson, studentID);
                nonAttendanceForSpecificStudent.add(newNonAttendance);
            }
            return nonAttendanceForSpecificStudent;
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Get a list of all semesterLessons the student did not attend from the DB
     *
     * @param studentID
     * @return
     * @throws SQLException
     */
    private List<SchoolClassSemesterLesson> getAllSchoolClassSemesterLessonsASpecificStudentDidNotAttend(int studentID) throws SQLException {
        List<SchoolClassSemesterLesson> schoolClassSemesterLessons = new ArrayList<>();
        String sql = "SELECT semesterLesson.ID AS 'SemesterLessonID', "
                + "semesterLesson.SchoolClassSemesterSubjectID 'SemesterSubjectID', "
                + "semesterLesson.Date AS 'SemesterLessonDate' "
                + "FROM StudentLessonNonAttendance nonAttendance "
                + "JOIN SchoolClassSemesterLesson semesterLesson ON nonAttendance.SchoolClassSemesterLessonID = semesterLesson.ID "
                + "WHERE nonAttendance.StudentID = ?";
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, studentID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                schoolClassSemesterLessons.add(getOneSchoolClassSemesterLesson(rs));
            }
            return schoolClassSemesterLessons;
        }
    }

    /**
     * Retrieve a single semesterLesson from the DB
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private SchoolClassSemesterLesson getOneSchoolClassSemesterLesson(ResultSet rs) throws SQLException {
        int semesterLessonID = rs.getInt("SemesterLessonID");
        int semesterSubjectID = rs.getInt("SemesterSubjectID");
        Date semesterLessonDate = rs.getDate("SemesterLessonDate");

        SchoolSemesterSubject semesterSubject = getSchoolSemesterSubjectsFromSpecificSemesterID(semesterSubjectID);
        SchoolClassSemesterLesson semesterLesson = new SchoolClassSemesterLesson(semesterLessonID, semesterSubject, semesterLessonDate);

        return semesterLesson;
    }

    /**
     * Get a list of all SchoolSemesterSubjects from the DB with their relevant
     * data
     *
     * @param semesterSubjectID
     * @return
     * @throws SQLException
     */
    public SchoolSemesterSubject getSchoolSemesterSubjectsFromSpecificSemesterID(int semesterSubjectID) throws SQLException {
        schoolSemesterSubjects = new ArrayList<>();
        String sql = "SELECT "
                + "semesterSubject.ID "
                + "AS "
                + "'SemesterID', "
                + "c.ID "
                + "AS "
                + "'SchoolClassID', "
                + "c.Name "
                + "AS "
                + "'SchoolClassName', "
                + "sem.Name "
                + "AS "
                + "'SemesterName', "
                + "schoolSubject.Name "
                + "AS "
                + "'SchoolSubjectName', "
                + "t.FirstName "
                + "AS "
                + "'TeacherFirstName'"
                + "FROM SchoolClassSemesterSubject semesterSubject "
                + "JOIN SchoolClass c ON semesterSubject.SchoolClassID = c.ID "
                + "JOIN Semester sem ON semesterSubject.SemesterID = sem.ID "
                + "JOIN SchoolSubject schoolSubject ON semesterSubject.SchoolSubjectID = schoolSubject.ID "
                + "JOIN Teacher t ON semesterSubject.TeacherID = t.ID "
                + "WHERE semesterSubject.ID = ?";

        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, semesterSubjectID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return SchoolClassDAO.getInstance().getOneSchoolSemesterSubject(rs);
            }
            return null;
        }
    }

    /**
     * Add NonAttendance to DB
     *
     * @param newNonAttendance
     */
    public void addNonAttendance(NonAttendance newNonAttendance) {
        String sql = "INSERT INTO StudentLessonNonAttendance"
                + "(SchoolClassSemesterLessonID, StudentID) "
                + "VALUES(?, ?)";
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, newNonAttendance.getSchoolClassSemesterLesson().getID());
            ps.setInt(2, newNonAttendance.getStudentID());

            ps.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println();
            System.out.println("Couldn't add NonAttendance to DB");
            System.out.println(sqlException);
        }
    }

    /**
     * Add NonAttendance to DB
     *
     * @param newNonAttendance
     */
    public void removeNonAttendance(NonAttendance newNonAttendance) {
        String sql = "DELETE FROM StudentLessonNonAttendance "
                + "WHERE SchoolClassSemesterLessonID = ? "
                + "AND StudentID = ?";
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, newNonAttendance.getSchoolClassSemesterLesson().getID());
            ps.setInt(2, newNonAttendance.getStudentID());

            ps.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println();
            System.out.println("Couldn't remove NonAttendance to DB");
            System.out.println(sqlException);
        }
    }
}
