/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.dal;

/**
 *
 * @author Rasmus
 */
public class LoginDAO {

    public LoginDAO() {
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
