/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rasmus
 */
public class LoginDAO {

    private static LoginDAO instance;

    private DBConnectionManager cm;

    public static LoginDAO getInstance() {
        if (instance == null) {
            instance = new LoginDAO();
        }
        return instance;
    }

    private LoginDAO() {
        cm = null;
        try {
            cm = DBConnectionManager.getInstance();
        } catch (IOException ex) {
            System.out.println("Couldn't make connection to DB");
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Check if user is in DB
     *
     * @param userEmail
     * @return
     * @throws com.microsoft.sqlserver.jdbc.SQLServerException
     */
    public boolean checkIfUserIsInDB(String userEmail) throws SQLServerException, SQLException {
        String sql = "SELECT * FROM Person "
                + "WHERE Email = ?";
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, userEmail);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get a list of all songs from the DB
     *
     * @param userEmail
     * @return
     * @throws SQLException
     */
    public boolean checkIfUserIsTeacher(String userEmail) throws SQLException {
        String sql = "SELECT * FROM Teacher t "
                + "JOIN Person p ON p.ID = t.PersonID "
                + "WHERE p.Email = ?";
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, userEmail);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        }
        return false;
    }

    /**
     * MockPassword for student.
     *
     * @return
     */
    public String getMockStudentPassword() {
        return "student";
    }

    /**
     * MockPassword for teacher.
     *
     * @return
     */
    public String getMockTeacherPassword() {
        return "teacher";
    }

}
