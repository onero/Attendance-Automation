/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be;

import java.util.ArrayList;

public class SchoolClass {

    private final String name;

    private final ArrayList<Student> students;

    public SchoolClass(String name) {
        this.name = name;
        students = new ArrayList<>();
    }

    /**
     * Add parsed student to the array
     *
     * @param newStudent
     */
    public void addStudentToClass(Student newStudent) {
        students.add(newStudent);
    }

    /**
     * Removed parsed student
     *
     * @param studentToRemove
     */
    public void removeStudent(Student studentToRemove) {
        students.remove(studentToRemove);
    }

    /**
     *
     * @return name of schoolclass
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return students
     */
    public ArrayList<Student> getStudents() {
        return students;
    }

}
