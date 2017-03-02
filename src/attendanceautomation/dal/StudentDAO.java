/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.dal;

import attendanceautomation.be.Student;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
            System.out.println("Coudn't make connection to DB");
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
                + "WHERE cs.SchoolClassID = " + schoolClassID;
        try (Connection con = cm.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                students.add(getOneStudent(rs));
            }
            return students;
        }
    }

    /**
     * Get a list of all songs from the DB
     *
     * @return
     * @throws SQLException
     */
    public List<Student> getAllSchoolClassStudents() throws SQLException {
        List<Student> students = new ArrayList<>();

        String sql = "SELECT * FROM Student";
        try (Connection con = cm.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
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

}
