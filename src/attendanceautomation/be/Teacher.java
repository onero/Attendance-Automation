/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be;

public class Teacher extends Person {

    private final int teacherID;

    public Teacher(int ID, int teacherID, String firstName, String lastName, String email) {
        super(ID, firstName, lastName, email);
        this.teacherID = teacherID;
    }

    public int getTeacherID() {
        return teacherID;
    }

}
