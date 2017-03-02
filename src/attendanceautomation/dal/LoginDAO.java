/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.dal;

import attendanceautomation.be.Student;
import java.util.ArrayList;

/**
 *
 * @author Rasmus
 */
public class LoginDAO {

    private final Student mockStudent;
    private final Student mockTeacher;

    private final ArrayList<Student> mockListOfStudents;

    public LoginDAO() {
        mockStudent = new Student("Rasmus", "Lindved");
        mockTeacher = new Student("Adam", "Hansen");
        mockListOfStudents = new ArrayList<>();

        mockListOfStudents.add(mockStudent);
        mockListOfStudents.add(mockTeacher);
    }

    public ArrayList<Student> getMockStudents() {
        return mockListOfStudents;
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
