/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.dal;

import attendanceautomation.be.Student;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentDAO {

    private static StudentDAO instance;

    private DBConnectionManager cm;

    public static StudentDAO getInstance() {
        if (instance == null) {
            instance = new StudentDAO();
        }
        return instance;
    }

    private StudentDAO() {
        cm = null;
        try {
            cm = DBConnectionManager.getInstance();
        } catch (IOException ex) {
            System.out.println("Couldn't make connection to DB");
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get a list of all songs from the DB
     *
     * @param schoolClassID
     * @return
     * @throws SQLException
     */
    public List<Student> getAllSchoolClassStudentsFromSpecificSchoolClass(int schoolClassID) throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * "
                + "FROM Student s "
                + "JOIN SchoolClassStudent cs ON s.ID = cs.StudentID "
                + "JOIN Person p ON p.ID = s.PersonID "
                + "WHERE cs.SchoolClassID = ?";
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, schoolClassID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                students.add(getOneStudent(rs));
            }
            return students;
        }
    }

    /**
     * Retrieve a single song from the DB
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private Student getOneStudent(ResultSet rs) throws SQLException {
        int ID = rs.getInt("ID");
        String firstName = rs.getString("FirstName");
        String lastName = rs.getString("lastName");
        String email = rs.getString("Email");

        Student newStudent = new Student(ID, firstName, lastName, email);

        return newStudent;
    }

    /**
     * Get student by email
     *
     * @param studentEmail
     * @return
     */
    Student getStudentByEmail(String studentEmail) throws SQLException {
        String sql = "SELECT * FROM STUDENT s "
                + "JOIN Person p ON p.ID = s.PersonID "
                + "WHERE Email = ?";
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, studentEmail);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return getOneStudent(rs);
            }
        }
        return null;
    }

}
