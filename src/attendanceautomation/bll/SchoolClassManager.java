/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll;

import attendanceautomation.be.Academy;
import attendanceautomation.be.NonAttendance;
import attendanceautomation.be.SchoolClass;
import attendanceautomation.be.Student;
import attendanceautomation.dal.AttendanceAutomationDAOFacade;
import java.util.HashMap;
import java.util.List;

public class SchoolClassManager {

    private static SchoolClassManager instance;

    private final AttendanceAutomationDAOFacade AADAOFacade;

    public static SchoolClassManager getInstance() {
        if (instance == null) {
            instance = new SchoolClassManager();
        }
        return instance;
    }

    private SchoolClassManager() {
        AADAOFacade = AttendanceAutomationDAOFacade.getInstance();
    }

    /**
     * Get the updated studentInfo from DB
     *
     * @param schoolClassId
     * @return
     */
    public List<Student> getStudentsWithDataFromSchoolClass(int schoolClassId) {
        List<Student> schoolClassStudents = AADAOFacade.getStudentsFromSchoolClass(schoolClassId);
        getNonAttendanceForAllStudents(schoolClassStudents);
        return schoolClassStudents;
    }

    /**
     * Get all SchoolClass data
     *
     * @param id
     * @return
     */
    public SchoolClass getAllSchoolClassDataBySchoolClassId(int id) {
        SchoolClass schoolClass = getSchoolClassById(id);

        addStudentsToSchoolClass(id, schoolClass);
        return schoolClass;
    }

    /**
     * Get SchoolClass with subjects and lessons by SchoolClassId
     *
     * @param id
     * @return
     */
    private SchoolClass getSchoolClassById(int id) {
        SchoolClass schoolClass = AADAOFacade.getSchoolClassByID(id);
        schoolClass.addAllSemesterSubjects(AADAOFacade.getSchoolSemesterSubjectsInSchoolClass(id));
        schoolClass.addAllSemesterLessonsToClass(AADAOFacade.getSchoolSemesterLessonsInSchoolClass(id));
        return schoolClass;
    }

    /**
     * Add students with their NonAttendance to the schoolClass
     *
     * @param id
     * @param schoolClass
     */
    private void addStudentsToSchoolClass(int id, SchoolClass schoolClass) {
        List<Student> schoolClassStudents = getStudentsWithDataFromSchoolClass(id);
        schoolClass.addAllStudents(schoolClassStudents);
    }

    /**
     * Get NonAttendance for all students in current SchoolClass
     *
     * @param schoolClassStudents
     * @param id
     */
    private void getNonAttendanceForAllStudents(List<Student> schoolClassStudents) {
        for (Student schoolClassStudent : schoolClassStudents) {
            schoolClassStudent.addAllNonAttendance(AADAOFacade.getNonAttendanceForStudentByID(schoolClassStudent.getID()));
        }
    }

    /**
     * Update attendance for student
     *
     * @param newNonAttendance
     */
    public void addNewAttendance(NonAttendance newNonAttendance) {
        AADAOFacade.addNewNonAttendance(newNonAttendance);
    }

    /**
     * Update attendance for student
     *
     * @param newNonAttendance
     */
    public void removeNonAttendance(NonAttendance newNonAttendance) {
        AADAOFacade.removeNonAttendanceFromSpecificStudent(newNonAttendance);
    }

    /**
     * Get a specific SchoolClass from student id
     *
     * @param studentEmail
     * @return
     */
    public SchoolClass getSchoolClassFromStudentEmail(String studentEmail) {
        SchoolClass schoolClass = AADAOFacade.getSchoolClassFromStudentEmail(studentEmail);
        SchoolClass schoolClassWithAllDataExceptStudents = getSchoolClassById(schoolClass.getID());
        return schoolClassWithAllDataExceptStudents;
    }

    /**
     * Get a student by email
     *
     * @param studentEmail
     * @return
     */
    public Student getStudentByEmail(String studentEmail) {
        Student student = AADAOFacade.getStudentByEmail(studentEmail);
        student.addAllNonAttendance(AADAOFacade.getNonAttendanceForStudentByID(student.getID()));
        return student;
    }

    /**
     * Check is user is a teacher
     *
     * @param userEmail
     * @return
     */
    public boolean isTeacher(String userEmail) {
        return AADAOFacade.isTeacher(userEmail);
    }

    /**
     * Check if user is in DB
     *
     * @param userEmail
     * @return
     */
    public boolean isUserInDB(String userEmail) {
        return AADAOFacade.isUserInDB(userEmail);
    }

    /**
     * Get all locations from given academy
     *
     * @param currentAcademy
     * @return
     */
    public HashMap<Integer, String> getAcademyLocations(Academy currentAcademy) {
        return AADAOFacade.getAcademyLocations(currentAcademy);
    }

    /**
     * Get schoolClassIds by location
     *
     * @param currentLocationID
     * @return
     */
    public List<Integer> getSchoolClassIdsByLocation(int currentLocationID) {
        return AADAOFacade.getSchoolClassIdsByLocation(currentLocationID);
    }

}
