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
import attendanceautomation.be.Teacher;
import attendanceautomation.dal.AttendanceAutomationDAOFacade;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
     * Get the updated studentInfo form DB for a specific period.
     *
     * @param schollClassId
     * @param startdate
     * @param endDate
     * @return
     */
    public List<Student> getStudentsWithDataFromSchoolClassForSpecificPeriod(int schollClassId, String startdate, String endDate) {
        List<Student> schoolClassStudents = AADAOFacade.getStudentsFromSchoolClass(schollClassId);
        getNonAttendanceForALlStudentsForSpecificPeriod(schoolClassStudents, startdate, endDate);
        return schoolClassStudents;
    }

    /**
     * Get the updated studentOnfo for specific date from DB.
     *
     * @param schoolClassId
     * @param date
     * @return
     */
    public List<Student> getStudentsWithDataFromSchoolClassForSpecificDate(int schoolClassId, String date) {
        List<Student> schoolClassStudents = AADAOFacade.getStudentsFromSchoolClass(schoolClassId);
        getNonAttendanceForAllStudentsForSpecificDate(schoolClassStudents, date);
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
     * Gets all schoolClass data for a specific time period.
     *
     * @param id
     * @param startDate
     * @param endDate
     * @return
     */
    public SchoolClass getAllSchoolClassDataBySchoolClassIdForSpecificPeriod(int id, String startDate, String endDate) {
        SchoolClass schoolClass = getSchoolClassById(id);

        addStudentsToSchoolClassForSpecificPeriod(id, schoolClass, startDate, endDate);
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
     * Add students with their NonAttendance to the schoolClass for a specifi
     * period.
     *
     * @param id
     * @param schoolClass
     * @param startDate
     * @param endDate
     */
    private void addStudentsToSchoolClassForSpecificPeriod(int id, SchoolClass schoolClass, String startDate, String endDate) {
        List<Student> schoolClassStudnets = getStudentsWithDataFromSchoolClassForSpecificPeriod(id, startDate, endDate);
        schoolClass.addAllStudents(schoolClassStudnets);
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
     * Get NonAttendance for all students in current schoolClasss for specific
     * period.
     *
     * @param schoolClassStudents
     * @param startDate
     * @param endDate
     */
    private void getNonAttendanceForALlStudentsForSpecificPeriod(List<Student> schoolClassStudents, String startDate, String endDate) {
        for (Student schoolClassStudent : schoolClassStudents) {
            schoolClassStudent.addAllNonAttendance(AADAOFacade.getNonAttendanceForStudentsByIDForSpecificPeriod(schoolClassStudent.getID(), startDate, endDate));
        }
    }

    /**
     * Get NonAttendacce for all students in current schoolClass for specific
     * date.
     *
     * Viloation of DRY!
     *
     * @param schoolClassStudents
     * @param date
     */
    private void getNonAttendanceForAllStudentsForSpecificDate(List<Student> schoolClassStudents, String date) {
        for (Student schoolClassStudent : schoolClassStudents) {
            schoolClassStudent.addAllNonAttendance(AADAOFacade.getNonAttendanceForStudentByIDForSepcificDate(schoolClassStudent.getID(), date));
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
     * @param teacher
     * @return
     */
    public HashMap<Integer, String> loadAcademyLocationsTeacherIsTeaching(Academy currentAcademy, Teacher teacher) {
        return AADAOFacade.loadAcademyLocationsTeacherIsTeaching(currentAcademy, teacher);
    }

    /**
     * Get schoolClassIds by location
     *
     * @param currentLocationID
     * @param teacherID
     * @return
     */
    public HashMap<Integer, String> getSchoolClassHashMapByLocationAndTeacher(int currentLocationID, int teacherID) {
        return AADAOFacade.getSchoolClassHashMapByLocationAndTeacher(currentLocationID, teacherID);
    }

    /**
     * Get teacher by email
     *
     * @param teacherEmail
     * @return
     */
    public Teacher getTeacherByEmail(String teacherEmail) {
        return AADAOFacade.getTeacherByEmail(teacherEmail);
    }

    /**
     * Get all teacher schoolClassNames for specific semester
     *
     * @param schoolClassIDs
     * @param semesterName
     * @return
     */
    public List<String> getAllTeacherSchoolClassesBySemester(List<Integer> schoolClassIDs, String semesterName) {
        return AADAOFacade.getAllTeacherSchoolClassesBySemester(schoolClassIDs, semesterName);
    }

    /**
     *
     * @param currentSchoolClass
     * @param semesterID
     */
    public void getSchoolClassSemesterDataBySchoolClassAndSemesterID(SchoolClass currentSchoolClass, int semesterID) {
        currentSchoolClass.getSemesterLessons().clear();
        currentSchoolClass.getSemesterSubjects().clear();
        currentSchoolClass.addAllSemesterLessonsToClass(AADAOFacade.getSchoolClassSemesterLessonsBySchoolClassIDAndSemesterID(currentSchoolClass.getID(), semesterID));
        currentSchoolClass.addAllSemesterSubjects(AADAOFacade.getSchoolClassSemesterSubjectsBySchoolCLassIDAndSemesterID(currentSchoolClass.getID(), semesterID));
    }

    /**
     * Converts a semester name ti an ID.
     *
     * @param semesterName
     * @return
     */
    public int getSemesterIDByName(String semesterName) {
        return AADAOFacade.getSemesterIDByName(semesterName);
    }

    /**
     * Filters the students Nonattendance by semester and returns a List of the
     * students.
     *
     * @param schoolClassID
     * @param semesterID
     * @return
     */
    public List<Student> getAllStudentDataBySemester(int schoolClassID, int semesterID) {
        List<Student> students = AADAOFacade.getStudentsFromSchoolClass(schoolClassID);
        for (Student student : students) {
            student.addAllNonAttendance(AADAOFacade.getAllNonAttendanceForStudentBySemester(student.getID(), semesterID));
        }
        return students;
    }

    /**
     * Makes a List from the Set given in the param, and then get one teacher
     * from the DB, by their name, at a time for each name given.
     *
     * @param teacherNames
     * @return
     */
    public List<Teacher> getTeachersByNames(Set<String> teacherNames) {
        List<Teacher> teachers = new ArrayList<>();
        for (String teacherName : teacherNames) {
            teachers.add(AADAOFacade.getOneTeacherByName(teacherName));
        }
        return teachers;
    }
}
