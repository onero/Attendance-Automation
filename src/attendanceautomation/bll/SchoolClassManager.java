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
import attendanceautomation.bll.sorting.ISortStrategy;
import attendanceautomation.bll.sorting.SortStudentsOnAttendance;
import attendanceautomation.bll.sorting.SortStudentsOnNameStrategy;
import attendanceautomation.dal.AttendanceAutomationDAOFacade;
import attendanceautomation.gui.model.SchemaModel;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class SchoolClassManager {

    private static SchoolClassManager instance;

    private final AttendanceAutomationDAOFacade AADAOFacade;

    private ISortStrategy sortOnNameStrategy;
    private ISortStrategy sortOnAttendanceStrategy;

    public static SchoolClassManager getInstance() {
        if (instance == null) {
            instance = new SchoolClassManager();
        }
        return instance;
    }

    private SchoolClassManager() {
        AADAOFacade = AttendanceAutomationDAOFacade.getInstance();

        sortOnAttendanceStrategy = new SortStudentsOnAttendance();
        sortOnNameStrategy = new SortStudentsOnNameStrategy();
    }

    public int getSchoolClassIdByName(String schoolClassName) {
        return AADAOFacade.getSchoolClassIdByName(schoolClassName);
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
     * Gets all schoolClass data for a specific time period.
     *
     * @param schoolClassID
     * @param startDate
     * @param endDate
     * @return
     */
    public SchoolClass getAllSchoolClassDataBySchoolClassIdForSpecificPeriod(int schoolClassID, String startDate, String endDate) {
        SchoolClass schoolClass = getSchoolClassByIdForSpecificPeriod(schoolClassID, startDate, endDate);

        schoolClass.addAllStudents(getStudentsFromSchoolClassForSpecificPeriod(schoolClassID, startDate, endDate));
        return schoolClass;
    }

    /**
     * Gets the schoolClass with the given ID for the specified period.
     *
     * @param schoolClassId
     * @param startDate
     * @param endDate
     * @return
     */
    private SchoolClass getSchoolClassByIdForSpecificPeriod(int schoolClassId, String startDate, String endDate) {
        SchoolClass schoolClass = AADAOFacade.getSchoolClassByID(schoolClassId);
        schoolClass.addAllSemesterSubjects(AADAOFacade.getSchoolSemesterSubjectsInSchoolClassForSpecificPeriod(schoolClassId, startDate, endDate));
        schoolClass.addAllSemesterLessonsToClass(AADAOFacade.getSchoolSemesterLessonsInSchoolClassForSpecificPeriod(schoolClassId, startDate, endDate));
        return schoolClass;
    }

    /**
     * Add students with their NonAttendance to the schoolClass for a specifi
     * period.
     *
     * @param schoolClassID
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Student> getStudentsFromSchoolClassForSpecificPeriod(int schoolClassID, String startDate, String endDate) {
        return getStudentsWithDataFromSchoolClassForSpecificPeriod(schoolClassID, startDate, endDate);
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
        SchoolClass schoolClassWithAllDataExceptStudents = getSchoolClassByIdForSpecificPeriod(schoolClass.getID(), SchemaModel.getInstance().getStartDate(), SchemaModel.getInstance().getEndDate());
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
    public boolean isUserInDB(String userEmail) throws SQLException {
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
     * @param schoolClassID
     * @param semesterID
     * @return
     */
    public SchoolClass getSchoolClassBySemester(int schoolClassID, int semesterID) {
        SchoolClass schoolClass = AADAOFacade.getSchoolClassByID(schoolClassID);
        schoolClass.addAllStudents(getAllStudentDataBySemester(schoolClassID, semesterID));
        schoolClass.addAllSemesterLessonsToClass(AADAOFacade.getSchoolClassSemesterLessonsBySchoolClassIDAndSemesterID(schoolClassID, semesterID));
        schoolClass.addAllSemesterSubjects(AADAOFacade.getSchoolClassSemesterSubjectsBySchoolCLassIDAndSemesterID(schoolClassID, semesterID));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date start = schoolClass.getSemesterLessons().get(0).getDate();
        String startDate = df.format(start);
        SchemaModel.getInstance().setStartDate(startDate);

        Date end = schoolClass.getSemesterLessons().get(schoolClass.getSemesterLessons().size() - 1).getDate();
        String endDate = df.format(end);
        SchemaModel.getInstance().setEndDate(endDate);

        SchemaModel.getInstance().setCurrentMonth(startDate, endDate);
        return schoolClass;
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

    public List<String> getAllSchoolClassSemestersOnSchoolClassName(String schoolClassName) {
        return AADAOFacade.getAllSchoolClassSemestersBySchoolClassName(schoolClassName);
    }

    /**
     * Sort two students on attendance in descending order
     *
     * @param students
     */
    public void sortStudentsOnAttendance(List<Student> students) {
        sortOnAttendanceStrategy.sort(students);
    }

    /**
     * Natural sort students on name
     *
     * @param students
     */
    public void sortStudentsOnName(List<Student> students) {
        sortOnNameStrategy.sort(students);
    }
}
