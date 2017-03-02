/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.dal;

import attendanceautomation.be.SchoolClass;
import attendanceautomation.be.SchoolSemesterSubject;
import attendanceautomation.be.enums.ESchoolSubject;
import attendanceautomation.be.enums.ESemester;
import attendanceautomation.be.enums.ETeacher;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SchoolClassDAO {

    private static SchoolClassDAO instance;

    private DBConnectionManager cm;

    public static SchoolClassDAO getInstance() {
        if (instance == null) {
            instance = new SchoolClassDAO();
        }
        return instance;
    }

    private SchoolClassDAO() {
        cm = null;
        try {
            cm = DBConnectionManager.getInstance();
        } catch (IOException ex) {
            System.out.println("Coudn't make connection to DB");
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get a specific SchoolClass on its ID
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public SchoolClass getSchoolClassByID(int id) throws SQLException {
        String sql = "SELECT * FROM SchoolClass WHERE ID = ?";
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return getOneSchoolClass(rs);
            } else {
                return null;
            }
        }
    }

    /**
     * Get a list of all songs from the DB
     *
     * @return
     * @throws SQLException
     */
    public List<SchoolClass> getAllSchoolClasses() throws SQLException {
        List<SchoolClass> schoolClasses = new ArrayList<>();

        String sql = "SELECT * FROM SchoolClass";
        try (Connection con = cm.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                schoolClasses.add(getOneSchoolClass(rs));
            }
            return schoolClasses;
        }
    }

    /**
     * Retrieve a single song from the DB
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private SchoolClass getOneSchoolClass(ResultSet rs) throws SQLException {
        int ID = rs.getInt("ID");
        String name = rs.getString("Name");

        SchoolClass newSchoolClass = new SchoolClass(ID, name);

        return newSchoolClass;
    }

    /**
     * Get a list of all SchoolSemesterSubjects from the DB with their relevant
     * data
     *
     * @param schoolClassID
     * @return
     * @throws SQLException
     */
    public ArrayList<SchoolSemesterSubject> getAllSchoolSemesterSubjectsFromSpecificSchoolClass(int schoolClassID) throws SQLException {
        ArrayList<SchoolSemesterSubject> schoolSemesterSubjects = new ArrayList<>();
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
                + "WHERE c.ID = ?";

        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, schoolClassID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                schoolSemesterSubjects.add(getOneSchoolSemesterSubject(rs));
            }
            return schoolSemesterSubjects;
        }
    }

    /**
     * Get a list of all SchoolSemesterSubjects from the DB with their relevant
     * data
     *
     * @return
     * @throws SQLException
     */
    public ArrayList<SchoolSemesterSubject> getAllSchoolSemesterSubjects() throws SQLException {
        ArrayList<SchoolSemesterSubject> schoolSemesterSubjects = new ArrayList<>();

        String sql = "SELECT "
                + "semesterSubject.ID "
                + "AS "
                + "'SemesterID' , "
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
                + "JOIN Teacher t ON semesterSubject.TeacherID = t.ID ";

        try (Connection con = cm.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                schoolSemesterSubjects.add(getOneSchoolSemesterSubject(rs));
            }
            return schoolSemesterSubjects;
        }
    }

    /**
     * Retrieve a single SchoolSemesterSubject from the DB
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private SchoolSemesterSubject getOneSchoolSemesterSubject(ResultSet rs) throws SQLException {
        int SemesterSubjectID = rs.getInt("SemesterID");
        int SchoolClassID = rs.getInt("SchoolClassID");
        String SchoolClassName = rs.getString("SchoolClassName");
        String Semester = rs.getString("SemesterName");
        String SchoolSubjectName = rs.getString("SchoolSubjectName");
        String TeacherName = rs.getString("TeacherFirstName");

        SchoolClass schoolClass = new SchoolClass(SchoolClassID, SchoolClassName);
        ESemester semester = ESemester.getESemesterFromString(Semester);
        ESchoolSubject subject = ESchoolSubject.valueOf(SchoolSubjectName);
        ETeacher teacher = ETeacher.getETeacherFromString(TeacherName);

        SchoolSemesterSubject newSubject = new SchoolSemesterSubject(SemesterSubjectID, schoolClass, semester, subject, teacher);

        return newSubject;
    }

}
