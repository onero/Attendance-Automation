/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.model;

import attendanceautomation.be.Academy;
import attendanceautomation.be.NonAttendance;
import attendanceautomation.be.SchoolClass;
import attendanceautomation.be.Student;
import attendanceautomation.be.Teacher;
import attendanceautomation.be.enums.ESemester;
import attendanceautomation.bll.CurrentClassManager;
import attendanceautomation.bll.SchoolClassManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SchoolClassModel {

    private static SchoolClassModel instance;

    private final SchemaModel schemaModel;

    private final SchoolClassManager schoolClassManager;

    private final Academy currentAcademy;

    private final CurrentClassManager currentClassManager;

    private int currentLocationID;

    private final ObservableList<String> locationNames;

    private final ObservableList<String> semesters;

    private Student currentStudent;
    private SchoolClass currentSchoolClass;
    private ESemester currentSemester;
    private Teacher currentTeacher;

    private HashMap<Integer, String> schoolClassForTeacherAtCurrentLocation;
    private List<Integer> schoolClassIDs;

    private final ObservableList<String> teacherSchoolClassNames;

    private final ObservableList<Student> currentClassStudentsAbsence;
    private final ObservableList<Student> currentClassStudentsPresent;

    private final List<Student> studentsFromDB;
    private final ObservableList<Student> students;
    private String searchString;

    public static SchoolClassModel getInstance() {
        if (instance == null) {
            instance = new SchoolClassModel();
        }
        return instance;
    }

    private SchoolClassModel() {
        schoolClassManager = SchoolClassManager.getInstance();
        schemaModel = SchemaModel.getInstance();
        searchString = "";
        students = FXCollections.observableArrayList();
        studentsFromDB = new ArrayList<>(students);
        currentAcademy = new Academy(1, "EASV");
        locationNames = FXCollections.observableArrayList();
        teacherSchoolClassNames = FXCollections.observableArrayList();
        currentClassManager = new CurrentClassManager();
        currentClassStudentsAbsence = FXCollections.observableArrayList();
        currentClassStudentsPresent = FXCollections.observableArrayList();
        semesters = FXCollections.observableArrayList();
    }

    /**
     * Load newest data from DB
     */
    public void loadDataFromDB() {
        resetStudents();
        getAcademyLocationsTeacherIsTeaching();
        //TODO ALH: Dynamic locations
        getSchoolClassNamesByLocation(1);
        loadNextSchoolClassForTeacher();
    }

    /**
     * Load schoolclass by location
     *
     * @param location
     */
    public void getSchoolClassNamesByLocation(int location) {
        if (currentLocationID != location) {
            teacherSchoolClassNames.clear();
            currentLocationID = location;
            schoolClassForTeacherAtCurrentLocation = schoolClassManager.getSchoolClassHashMapByLocationAndTeacher(currentLocationID, currentTeacher.getTeacherID());
            resetSchoolNamesAndIDs();
            resetStudents();
        }
    }

    /**
     * Reset List of schoolClassNames & schoolClassIDs
     */
    public void resetSchoolNamesAndIDs() {
        teacherSchoolClassNames.clear();
        teacherSchoolClassNames.addAll(schoolClassForTeacherAtCurrentLocation.values());
        schoolClassIDs = new ArrayList<>(schoolClassForTeacherAtCurrentLocation.keySet());
    }

    /**
     * Update schoolClassNames on semester
     *
     * @param semester
     */
    public void updateSchoolClassNamesOnSemester(String semester) {
        List<String> semesterSchoolClasses = schoolClassManager.getAllTeacherSchoolClassesBySemester(schoolClassIDs, semester);
        teacherSchoolClassNames.clear();
        teacherSchoolClassNames.addAll(semesterSchoolClasses);
    }

    /**
     * Clear Lists of students
     */
    public void resetStudents() {
        studentsFromDB.clear();
        students.clear();
    }

    /**
     * Gets a fresh list of all students in currentClass with nonAttendance
     */
    public void updateStudentData() {
        List<Student> updatedStudents = schoolClassManager.getStudentsFromSchoolClassForSpecificPeriod(currentSchoolClass.getID(), schemaModel.getStartDate(), schemaModel.getEndDate());

        resetStudents();
        studentsFromDB.addAll(updatedStudents);
        students.addAll(studentsFromDB);
    }

    /**
     * Add new studentNonAttendance to DB
     *
     * @param newNonAttendance
     */
    public void addNewNonAttendance(NonAttendance newNonAttendance) {
        schoolClassManager.addNewAttendance(newNonAttendance);
    }

    /**
     * Remove studentNonAttendance from DB
     *
     * @param attendanceToRemove
     */
    public void removeNonAttendance(NonAttendance attendanceToRemove) {
        schoolClassManager.removeNonAttendance(attendanceToRemove);
    }

    public SchoolClass getCurrentSchoolClass() {
        return currentSchoolClass;
    }

    public void setCurrentTeacher(Teacher teacher) {
        currentTeacher = teacher;
    }

    /**
     *
     * @return students from search
     */
    public List<Student> getStudentsFromDB() {
        return studentsFromDB;
    }

    public ObservableList<Student> getStudents() {
        return students;
    }

    /**
     * Sort list on nonAttendance Descending
     */
    public void sortStudentsOnAttendance() {
        Collections.sort(students, (Student o1, Student o2)
                -> (o1.getNonAttendancePercentage().get() < o2.getNonAttendancePercentage().get()) ? 1 : 0);
    }

    /**
     * Sort list on FullName Ascending
     */
    public void sortStudentsOnName() {
        Collections.sort(students, (Student o1, Student o2)
                -> (o1.getFullName().compareTo(o2.getFullName())));
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    /**
     *
     * @return searchString
     */
    public String getSearchString() {
        return searchString;
    }

    /**
     * add student from search to observable list
     *
     * @param student
     */
    public void updateStudentsFromSearch(Student student) {
        students.add(student);
    }

    /**
     * Check if user is in DB
     *
     * @param userEmail
     * @return
     */
    public boolean isUserInDB(String userEmail) {
        return schoolClassManager.isUserInDB(userEmail);
    }

    /**
     * Get the current student
     *
     * @return
     */
    public Student getCurrentStudent() {
        return currentStudent;
    }

    /**
     * Get academy locations from DB
     */
    public void getAcademyLocationsTeacherIsTeaching() {
        currentAcademy.addAllLocation(schoolClassManager.loadAcademyLocationsTeacherIsTeaching(currentAcademy, currentTeacher));
        getAcademyLocationNames();
    }

    /**
     * Add location names to observable list
     */
    private void getAcademyLocationNames() {
        locationNames.addAll(currentAcademy.getLocations().values());
    }

    /**
     *
     * @return currentAcademy
     */
    public Academy getCurrentAcademy() {
        return currentAcademy;
    }

    /**
     *
     * @return list of location names
     */
    public ObservableList<String> getLocationNames() {
        return locationNames;
    }

    /**
     * Get the location id of parsed location name
     *
     * @param location
     * @return
     */
    public int getLocationID(String location) {
        List<Integer> keys = new ArrayList<>(currentAcademy.getLocations().keySet());
        int locationId = keys.get(locationNames.indexOf(location));
        return locationId;
    }

    public ObservableList<String> getSchoolClassNames() {
        return teacherSchoolClassNames;
    }

    /**
     * Get a teacher by adamlars90@gmail.coms
     *
     * @param teacherEmail
     * @return
     */
    public Teacher getTeacherByEmail(String teacherEmail) {
        return schoolClassManager.getTeacherByEmail(teacherEmail);
    }

    public int getSchoolClassIdByName(String schoolClassName) {
        return schoolClassManager.getSchoolClassIdByName(schoolClassName);
    }

    public Teacher getCurrentTeacher() {
        return currentTeacher;
    }

    /**
     * Check for new teacher
     *
     * @param teacher
     * @return
     */
    public boolean checkForNewTeacher(Teacher teacher) {
        if (currentTeacher == null) {
            return true;
        }
        return currentTeacher.getTeacherID() != teacher.getTeacherID();
    }

    /**
     * Clears currentClassStudentsWithAbsence. Then gets a new list of students
     * from the database.
     *
     * @param mockSwitch
     */
    public void updateCurrentClassStudents(int mockSwitch) {
        currentClassStudentsAbsence.clear();
        currentClassStudentsPresent.clear();

        List<Student> listOfCurrentClassStudentsPresent = new ArrayList<>();
        List<Student> listOfCurrentClassStudentsAbsence = new ArrayList<>();

        switch (mockSwitch) {
            case 0: {
                List<Student> listOfCurrentClassStudents = currentClassManager.getStudentsFromCurrentSchoolClass(currentTeacher.getTeacherID());
                listOfCurrentClassStudentsPresent = currentClassManager.findStudentsAbsentOrPresent(listOfCurrentClassStudents, true);
                listOfCurrentClassStudentsAbsence = currentClassManager.findStudentsAbsentOrPresent(listOfCurrentClassStudents, false);
                PieChartModel.getInstance().updateCurrentClassPieChat(listOfCurrentClassStudentsPresent, listOfCurrentClassStudentsAbsence);
                fillCurrentClassLists(listOfCurrentClassStudentsAbsence, listOfCurrentClassStudentsPresent);
                break;
            }
            case 1: {
                findMockStudents(listOfCurrentClassStudentsPresent, listOfCurrentClassStudentsAbsence, 1, 4);
                break;
            }
            case 2: {
                findMockStudents(listOfCurrentClassStudentsPresent, listOfCurrentClassStudentsAbsence, 2, 3);
                break;
            }
            case 3: {
                findMockStudents(listOfCurrentClassStudentsPresent, listOfCurrentClassStudentsAbsence, 2, 3);
                break;
            }
            default: {
                break;
            }
        }

    }

    /**
     * Fills the lists in CurrentClassView with students.
     *
     * @param listOfCurrentClassStudentsAbsence
     * @param listOfCurrentClassStudentsPresent
     */
    private void fillCurrentClassLists(List<Student> listOfCurrentClassStudentsAbsence, List<Student> listOfCurrentClassStudentsPresent) {
        for (Student student : listOfCurrentClassStudentsAbsence) {
            currentClassStudentsAbsence.add(student);
        }

        for (Student student : listOfCurrentClassStudentsPresent) {
            currentClassStudentsPresent.add(student);
        }
    }

    /**
     * Find the mockStudent and fills the CurrentClassView with thier data.
     *
     * @param listPresent
     * @param listAbsence
     * @param present
     * @param absent
     */
    private void findMockStudents(List<Student> listPresent, List<Student> listAbsence, int present, int absent) {
        listPresent = currentClassManager.findMockStudents(present);
        listAbsence = currentClassManager.findMockStudents(absent);
        PieChartModel.getInstance().updateCurrentClassPieChat(listPresent, listAbsence);
        fillCurrentClassLists(listAbsence, listPresent);
    }

    /**
     * Gets currentClassStudentsAbsence.
     *
     * @return
     */
    public ObservableList<Student> getCurrentClassStudentsAbsence() {
        return currentClassStudentsAbsence;
    }

    /**
     * Gets the currentClassStudentsPresent
     *
     * @return
     */
    public ObservableList<Student> getCurrentClassStudentsPresent() {
        return currentClassStudentsPresent;
    }

    /**
     * Clear semesters
     */
    public void clearSemesters() {
        semesters.clear();
    }

    /**
     * Fill it up
     *
     * @param schoolClassName
     */
    public void getSemestersOnSchoolClassName(String schoolClassName) {
        clearSemesters();
        semesters.addAll(schoolClassManager.getAllSchoolClassSemestersOnSchoolClassName(schoolClassName));
    }

    /**
     *
     * @return semesters.
     */
    public ObservableList<String> getSemesters() {
        return semesters;
    }

    /**
     *
     * @param currentStudent
     */
    public void setCurrentStudent(Student currentStudent) {
        this.currentStudent = currentStudent;
    }

    /**
     * Converts a semester name to an ID.
     *
     * @param semesterName
     * @return
     */
    public int getSemesterIDByName(String semesterName) {
        return schoolClassManager.getSemesterIDByName(semesterName);
    }

    public ESemester getCurrentSemester() {
        return currentSemester;
    }

    /**
     * Fetches the data from the DB and updates the view.
     *
     * @param schoolClassID
     * @param semesterID
     */
    public void loadSchoolClassDataBySemester(int schoolClassID, int semesterID) {
        resetStudents();
        currentSchoolClass = schoolClassManager.getSchoolClassBySemester(schoolClassID, semesterID);
        studentsFromDB.addAll(schoolClassManager.getAllStudentDataBySemester(currentSchoolClass.getID(), semesterID));
        students.addAll(studentsFromDB);
    }

    /**
     * Load a schoolClass by name
     *
     * @param schoolClassName
     */
    public void loadSchoolClassDataByName(String schoolClassName) {
        int schoolClassID = schoolClassManager.getSchoolClassIdByName(schoolClassName);
        loadSchoolClassDataById(schoolClassID);
    }

    /**
     * Load schoolclass with students
     *
     * @param schoolClassID
     */
    public void loadSchoolClassDataById(int schoolClassID) {
        loadCurrentSchoolClassByPeriodAndID(schoolClassID);
    }

    /**
     * Gets schoolClass and student by student email
     *
     * @param studentEmail
     */
    public void loadStudentDataByEmail(String studentEmail) {
        currentSchoolClass = schoolClassManager.getSchoolClassFromStudentEmail(studentEmail);
        currentStudent = schoolClassManager.getStudentByEmail(studentEmail);
    }

    /**
     * Load current schoolClass based on schoolClassID
     *
     * @param schoolClassID
     */
    public void loadCurrentSchoolClassByPeriodAndID(int schoolClassID) {
        currentSchoolClass = schoolClassManager.getAllSchoolClassDataBySchoolClassIdForSpecificPeriod(schoolClassID, schemaModel.getStartDate(), schemaModel.getEndDate());
        resetStudents();
        fillStudents();
    }

    private void fillStudents() {
        studentsFromDB.addAll(currentSchoolClass.getStudents());
        students.addAll(studentsFromDB);
    }

    /**
     * Gets the next schoolclass for current teacher
     */
    private void loadNextSchoolClassForTeacher() {
        int nextSchoolClassForTeacher = schoolClassIDs.get(0);
        loadSchoolClassDataById(nextSchoolClassForTeacher);
    }

    /**
     * Calls the Manager to get a list of teacher objects.
     *
     * @param teacherNames
     * @return
     */
    public List<Teacher> getTeachersByNames(Set<String> teacherNames) {
        return schoolClassManager.getTeachersByNames(teacherNames);
    }
}
