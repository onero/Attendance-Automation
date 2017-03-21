/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.model;

import attendanceautomation.be.Academy;
import attendanceautomation.be.NonAttendance;
import attendanceautomation.be.SchoolClass;
import attendanceautomation.be.SchoolSemesterSubject;
import attendanceautomation.be.Student;
import attendanceautomation.be.Teacher;
import attendanceautomation.be.enums.ESemester;
import attendanceautomation.bll.CurrentClassManager;
import attendanceautomation.bll.SchoolClassManager;
import attendanceautomation.gui.views.rootView.controller.RootViewController;
import attendanceautomation.gui.views.sharedComponents.filters.semesterFilter.controller.SemesterFilterViewController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SchoolClassModel {

    private static SchoolClassModel instance;

    private SchemaModel schemaModel;

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

    private String startDate;
    private String endDate;

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

            /**
             * Load newest data from DB
             */
            teacherSchoolClassNames.clear();
            currentLocationID = location;
            schoolClassForTeacherAtCurrentLocation = schoolClassManager.getSchoolClassHashMapByLocationAndTeacher(currentLocationID, currentTeacher.getTeacherID());
            resetSchoolNamesAndIDs();
            int nextSchoolClassForTeacher = schoolClassIDs.get(0);
            loadSchoolClassData(nextSchoolClassForTeacher);
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
     * Update schoolClassNames on semester
     *
     * @param semester
     */
    public void updateSchoolClassesOnSemester(String semester) {
        List<String> semesterSchoolClasses = schoolClassManager.getAllTeacherSchoolClassesBySemester(schoolClassIDs, semester);
        teacherSchoolClassNames.clear();
        teacherSchoolClassNames.addAll(semesterSchoolClasses);
    }

    /**
     * Set current schoolClass based on schoolClassID
     *
     * @param schoolClassID
     */
    private void setCurrentSchoolClass(int schoolClassID) {
        if (schemaModel.getStartDate() != null && schemaModel.getEndDate() != null) {
            currentSchoolClass = schoolClassManager.getAllSchoolClassDataBySchoolClassIdForSpecificPeriod(schoolClassID, schemaModel.getStartDate(), schemaModel.getEndDate());
        } else {
            currentSchoolClass = schoolClassManager.getAllSchoolClassDataBySchoolClassId(schoolClassID);
        }

        resetStudents();
        studentsFromDB.addAll(currentSchoolClass.getStudents());
        students.addAll(studentsFromDB);
        PieChartModel.getInstance().resetPieChart();
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
        Runnable task = () -> {
            List<Student> updatedStudents = schoolClassManager.getStudentsWithDataFromSchoolClass(currentSchoolClass.getID());
            Platform.runLater(() -> {
                resetStudents();
                studentsFromDB.addAll(updatedStudents);
                students.addAll(studentsFromDB);
                sortStudentsOnAttendance();
                PieChartModel.getInstance().resetPieChart();
                RootViewController.getInstance().setRefreshBoxVisibility(false);
            });
        };
        new Thread(task).start();
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
     * Clears currentClassStudentsWithAbsence. Then gets a new list of students
     * from the database.
     */
    public void updateCurrentClassStudents() {
        currentClassStudentsAbsence.clear();
        currentClassStudentsPresent.clear();
        List<Student> listOfCurrentClassStudents = currentClassManager.getStudentsFromCurrentSchoolClass(currentTeacher.getTeacherID());

        List<Student> listOfCurrentClassStudentsPresent = currentClassManager.findStudentsPresent(listOfCurrentClassStudents);
        List<Student> listOfCurrentClassStudentsAbsence = currentClassManager.findStudentsAbsence(listOfCurrentClassStudents);

        for (Student student : listOfCurrentClassStudentsAbsence) {
            currentClassStudentsAbsence.add(student);
        }

        for (Student student : listOfCurrentClassStudentsPresent) {
            currentClassStudentsPresent.add(student);
        }
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
     */
    public void updateSemesters() {
        clearSemesters();
        for (SchoolSemesterSubject semesterSubject : getCurrentSchoolClass().getSemesterSubjects()) {
            if (!semesters.contains(semesterSubject.getSemester().toString())) {
                semesters.add(semesterSubject.getSemester().toString());
            }
        }
        SemesterFilterViewController.getInstance().selectLatest();
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
     * Sets the startDate to be searched on.
     *
     * @param date
     */
    public void setStartDate(String date) {
        startDate = date;
    }

    /**
     * Sets the endDate to be searched on.
     *
     * @param date
     */
    public void setEndDate(String date) {
        endDate = date;
    }

    /**
     * Gets the startDate.
     *
     * @return
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Gets the endDate.
     *
     * @return
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Fetches the data from the DB and updates the view.
     *
     * @param semesterID
     */
    public void updateSchoolClassSemester(int semesterID) {
        resetStudents();
        schoolClassManager.getSchoolClassSemesterDataBySchoolClassAndSemesterID(currentSchoolClass, semesterID);
        studentsFromDB.addAll(schoolClassManager.getAllStudentDataBySemester(currentSchoolClass.getID(), semesterID));
        students.addAll(studentsFromDB);
        PieChartModel.getInstance().resetPieChart();
        sortStudentsOnAttendance();
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
}
