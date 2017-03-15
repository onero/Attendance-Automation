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
import attendanceautomation.bll.CurrentClassManager;
import attendanceautomation.bll.SchoolClassManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SchoolClassModel {

    private static SchoolClassModel instance;

    private final SchoolClassManager schoolClassManager;

    private final Academy currentAcademy;

    private final CurrentClassManager currentClassManager;

    private int currentLocationID;

    private final ObservableList<String> locationNames;

    private Student currentStudent;

    private Teacher currentTeacher;

    private HashMap<Integer, String> schoolClassForTeacherAtCurrentLocation;
    private List<Integer> schoolClassIDs;

    private final ObservableList<String> teacherSchoolClassNames;

    private final ObservableList<Student> currentClassStudents;

    private SchoolClass currentSchoolClass;
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
        searchString = "";
        students = FXCollections.observableArrayList();
        studentsFromDB = new ArrayList<>(students);
        currentAcademy = new Academy(1, "EASV");
        locationNames = FXCollections.observableArrayList();
        teacherSchoolClassNames = FXCollections.observableArrayList();
        currentClassManager = new CurrentClassManager();
        currentClassStudents = FXCollections.observableArrayList();
    }

    /**
     * Load newest data from DB
     */
    public void loadDataFromDB() {
        students.clear();
        studentsFromDB.clear();
        loadAcademyLocationsTeacherIsTeaching();
        //TODO ALH: Dynamic locations
        loadSchoolClassByLocation(1);
    }

    /**
     * Load schoolclass by location
     *
     * @param location
     */
    public void loadSchoolClassByLocation(int location) {
        if (currentLocationID != location) {
            teacherSchoolClassNames.clear();
            currentLocationID = location;
            schoolClassForTeacherAtCurrentLocation = schoolClassManager.getSchoolClassHashMapByLocationAndTeacher(currentLocationID, currentTeacher.getTeacherID());
            teacherSchoolClassNames.addAll(schoolClassForTeacherAtCurrentLocation.values());
            schoolClassIDs = new ArrayList<>(schoolClassForTeacherAtCurrentLocation.keySet());
            int nextSchoolClassForTeacher = schoolClassIDs.get(0);
            loadSchoolClassData(nextSchoolClassForTeacher);
        }
    }

    /**
     * Load schoolclass with students
     *
     * @param schoolClassID
     */
    private void loadSchoolClassData(int schoolClassID) {
        if (currentSchoolClass != null) {
            if (schoolClassID != currentSchoolClass.getID()) {
                setCurrentSchoolClass(schoolClassID);
            }
        } else {
            setCurrentSchoolClass(schoolClassID);
        }
    }

    /**
     * Set current schoolClass based on schoolClassID
     *
     * @param schoolClassID
     */
    private void setCurrentSchoolClass(int schoolClassID) {
        resetStudents();
        currentSchoolClass = schoolClassManager.getAllSchoolClassDataBySchoolClassId(schoolClassID);
        studentsFromDB.addAll(currentSchoolClass.getStudents());
        students.addAll(studentsFromDB);
        sortStudentsOnAttendance();
    }

    public void resetStudents() {
        studentsFromDB.clear();
        students.clear();
    }

    public void updateStudentInfo() {
        students.clear();
        students.addAll(schoolClassManager.getStudentsWithDataFromSchoolClass(currentSchoolClass.getID()));
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

    /**
     * Add parsed SchoolClass to the observable array
     *
     * @param newSchoolClass
     */
    public void setCurrentSchoolClass(SchoolClass newSchoolClass) {
        currentSchoolClass = newSchoolClass;
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

    public void loadStudentData(String studentEmail) {
        currentSchoolClass = schoolClassManager.getSchoolClassFromStudentEmail(studentEmail);
        currentStudent = schoolClassManager.getStudentByEmail(studentEmail);
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
    public void loadAcademyLocationsTeacherIsTeaching() {
        currentAcademy.addAllLocation(schoolClassManager.loadAcademyLocationsTeacherIsTeaching(currentAcademy, currentTeacher));
        getAcademyLocationNames();
    }

    /**
     * Add location names to observable list
     */
    private void getAcademyLocationNames() {
        for (String location : currentAcademy.getLocations().values()) {
            locationNames.add(location);
        }
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

    /**
     * Load a schoolClass by name
     *
     * @param schoolClassName
     */
    public void loadSchoolClassByName(String schoolClassName) {
        loadSchoolClassData(schoolClassIDs.get(teacherSchoolClassNames.indexOf(schoolClassName)));
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
     * Clears currentClassStudents. Then gets a new list of students from the
     * database.
     */
    public void updateCurrentClassStudents() {
        currentClassStudents.clear();
        List<Student> listOfCurrentClassStudents = currentClassManager.getStudentsFromCurrentSchoolClass(currentTeacher.getTeacherID());
        for (Student student : listOfCurrentClassStudents) {
            currentClassStudents.add(student);
        }
    }

    /**
     * Gets currentClassStudents.
     *
     * @return
     */
    public ObservableList<Student> getCurrentClassStudents() {
        return currentClassStudents;
    }
}
