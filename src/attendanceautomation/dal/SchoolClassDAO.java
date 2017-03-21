/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.dal;

import attendanceautomation.be.Academy;
import attendanceautomation.be.SchoolClass;
import attendanceautomation.be.SchoolClassSemesterLesson;
import attendanceautomation.be.SchoolSemesterSubject;
import attendanceautomation.be.Teacher;
import attendanceautomation.be.enums.ESchoolSubject;
import attendanceautomation.be.enums.ESemester;
import attendanceautomation.be.enums.ETeacher;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SchoolClassDAO {

    private static SchoolClassDAO instance;

    private DBConnectionManager cm;

    private List<SchoolClassSemesterLesson> schoolClassSemesterSubjects;

    private List<SchoolSemesterSubject> schoolSemesterSubjects;

    private List<SchoolClass> schoolClasses;

    public static SchoolClassDAO getInstance() {
        if (instance == null) {
            instance = new SchoolClassDAO();
        }
        return instance;
    }

    private SchoolClassDAO() {
        cm = null;
        try {
            cm = DBConnectionManager.getInstance();
        } catch (IOException ex) {
            System.out.println("Coudn't make connection to DB");
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get a specific SchoolClass on its ID
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public SchoolClass getSchoolClassByID(int id) throws SQLException {
        SchoolClass schoolClass;
        String sql = "SELECT * FROM SchoolClass WHERE ID = ?";
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                schoolClass = getOneSchoolClass(rs);
            } else {
                schoolClass = null;
            }
        }
        return schoolClass;
    }

    /**
     * Get a list of all songs from the DB
     *
     * @return
     * @throws SQLException
     */
    public List<SchoolClass> getAllSchoolClasses() throws SQLException {
        schoolClasses = new ArrayList<>();

        String sql = "SELECT * FROM SchoolClass";
        try (Connection con = cm.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                schoolClasses.add(getOneSchoolClass(rs));
            }
            return schoolClasses;
        }
    }

    /**
     * Retrieve a single song from the DB
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private SchoolClass getOneSchoolClass(ResultSet rs) throws SQLException {
        int ID = rs.getInt("ID");
        String name = rs.getString("Name");

        SchoolClass newSchoolClass = new SchoolClass(ID, name);

        return newSchoolClass;
    }

    /**
     * Get all SchoolClassSemesterLessons from a specific class
     *
     * @param schoolClassID
     * @return
     * @throws SQLServerException
     * @throws SQLException
     */
    public List<SchoolClassSemesterLesson> getAllSchoolSemesterLessonsFromSpecificSchoolClass(int schoolClassID) throws SQLServerException, SQLException {
        schoolClassSemesterSubjects = new ArrayList<>();
        //Get a hold of all the subjects the SchoolClass has
        getAllSchoolSemesterSubjectsFromSpecificSchoolClass(schoolClassID);
        String sql = "SELECT semesterLesson.ID "
                + "AS 'SemesterLessonID', "
                + "semesterLesson.SchoolClassSemesterSubjectID "
                + "AS 'SemesterSubjectID', "
                + "semesterLesson.Date "
                + "AS 'SemesterLessonDate' "
                + "FROM SchoolClassSemesterLesson semesterLesson "
                + "JOIN SchoolClassSemesterSubject semesterSubject ON semesterLesson.SchoolClassSemesterSubjectID = semesterSubject.ID "
                + "JOIN Semester sem ON sem.ID = semesterSubject.SemesterID "
                + "WHERE semesterSubject.SchoolClassID = ? ";

        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, schoolClassID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                schoolClassSemesterSubjects.add(getOneSchoolSemesterLessons(rs));
            }
            return schoolClassSemesterSubjects;
        }
    }

    /**
     * Retrieve a single SchoolSemesterSubject from the DB
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private SchoolClassSemesterLesson getOneSchoolSemesterLessons(ResultSet rs) throws SQLException {
        int SemesterLessonID = rs.getInt("SemesterLessonID");
        int SchoolClassSemesterSubjectID = rs.getInt("SemesterSubjectID");
        Date lessonDate = rs.getDate("SemesterLessonDate");

        //For each SchoolClassSemesterSubject check if this lesson was about it
        SchoolSemesterSubject lessonSubject = null;
        for (SchoolSemesterSubject schoolSemesterSubject : schoolSemesterSubjects) {
            if (schoolSemesterSubject.getID() == SchoolClassSemesterSubjectID) {
                lessonSubject = schoolSemesterSubject;
                break;
            }
        }

        SchoolClassSemesterLesson newLesson = new SchoolClassSemesterLesson(SemesterLessonID, lessonSubject, lessonDate);

        return newLesson;
    }

    /**
     * Get a list of all SchoolSemesterSubjects from the DB with their relevant
     * data
     *
     * @param schoolClassID
     * @return
     * @throws SQLException
     */
    public List<SchoolSemesterSubject> getAllSchoolSemesterSubjectsFromSpecificSchoolClass(int schoolClassID) throws SQLException {
        schoolSemesterSubjects = new ArrayList<>();
        String sql = "SELECT "
                + "semesterSubject.ID "
                + "AS "
                + "'SemesterID', "
                + "c.ID "
                + "AS "
                + "'SchoolClassID', "
                + "c.Name "
                + "AS "
                + "'SchoolClassName', "
                + "sem.Name "
                + "AS "
                + "'SemesterName', "
                + "schoolSubject.Name "
                + "AS "
                + "'SchoolSubjectName', "
                + "p.FirstName "
                + "AS "
                + "'TeacherFirstName'"
                + "FROM SchoolClassSemesterSubject semesterSubject "
                + "JOIN SchoolClass c ON semesterSubject.SchoolClassID = c.ID "
                + "JOIN Semester sem ON semesterSubject.SemesterID = sem.ID "
                + "JOIN SchoolSubject schoolSubject ON semesterSubject.SchoolSubjectID = schoolSubject.ID "
                + "JOIN Teacher t ON semesterSubject.TeacherID = t.ID "
                + "JOIN Person p ON p.ID = t.PersonID "
                + "WHERE c.ID = ? ";

        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, schoolClassID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                schoolSemesterSubjects.add(getOneSchoolSemesterSubject(rs));
            }
            return schoolSemesterSubjects;
        }
    }

    /**
     * Retrieve a single SchoolSemesterSubject from the DB
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    public SchoolSemesterSubject getOneSchoolSemesterSubject(ResultSet rs) throws SQLException {
        int SemesterSubjectID = rs.getInt("SemesterID");
        int SchoolClassID = rs.getInt("SchoolClassID");
        String SchoolClassName = rs.getString("SchoolClassName");
        String Semester = rs.getString("SemesterName");
        String SchoolSubjectName = rs.getString("SchoolSubjectName");
        String TeacherName = rs.getString("TeacherFirstName");

        SchoolClass schoolClass = new SchoolClass(SchoolClassID, SchoolClassName);
        ESemester semester = ESemester.getESemesterFromString(Semester);
        ESchoolSubject subject = ESchoolSubject.valueOf(SchoolSubjectName);
        ETeacher teacher = ETeacher.getETeacherFromString(TeacherName);

        SchoolSemesterSubject newSubject = new SchoolSemesterSubject(SemesterSubjectID, schoolClass, semester, subject, teacher);

        return newSubject;
    }

    /**
     * Get a schoolclass id by student id
     *
     * @param studentEmail
     * @return
     * @throws SQLServerException
     * @throws SQLException
     */
    public SchoolClass getSchoolClassIdByStudentEmail(String studentEmail) throws SQLServerException, SQLException {
        String sql = "SELECT sc.ID "
                + "AS 'SchoolClassID' "
                + "FROM SchoolClassStudent scs "
                + "JOIN Student s ON s.ID = scs.StudentID "
                + "JOIN SchoolClass sc ON sc.ID = scs.SchoolClassID "
                + "JOIN Person p ON p.ID = s.PersonID "
                + "WHERE p.Email = ?";
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, studentEmail);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return getSchoolClassByID(rs.getInt("SchoolClassID"));
            }
            return null;
        }
    }

    /**
     * Get all locations from given academy
     *
     * @param currentAcademy
     * @param teacher
     * @return
     * @throws SQLServerException
     * @throws SQLException
     */
    public HashMap<Integer, String> loadAcademyLocationsTeacherIsTeaching(Academy currentAcademy, Teacher teacher) throws SQLServerException, SQLException {
        HashMap<Integer, String> locations = new HashMap<>();
        String sql = "SELECT DISTINCT(location.ID) AS 'LocationID', location.Name AS 'LocationName' "
                + "FROM AcademyLocation academyLocation "
                + "JOIN Academy academy ON academy.ID = academyLocation.AcademyID "
                + "JOIN Location location ON location.ID = academyLocation.LocationID "
                + "JOIN AcademySchoolClass academySC ON academySC.AcademyLocationID = location.ID "
                + "JOIN SchoolClassSemesterSubject semesterSubject ON semesterSubject.SchoolClassID = academySC.SchoolClassID "
                + "JOIN Teacher t ON t.ID = semesterSubject.TeacherID "
                + "WHERE academy.ID = ? "
                + "AND "
                + "t.ID = ? ";

        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, currentAcademy.getID());
            ps.setInt(2, teacher.getTeacherID());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                locations.putAll(getOneLocation(rs));
            }
            return locations;
        }
    }

    /**
     * Get one locations
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private HashMap<Integer, String> getOneLocation(ResultSet rs) throws SQLException {
        int locationID = rs.getInt("LocationID");
        String locationName = rs.getString("LocationName");

        HashMap<Integer, String> location = new HashMap<>();
        location.put(locationID, locationName);
        return location;
    }

    /**
     * Get all schoolclassIds by given locationID
     *
     * @param currentLocationID
     * @param teacherID
     * @return
     * @throws com.microsoft.sqlserver.jdbc.SQLServerException
     */
    public HashMap<Integer, String> getSchoolClassHashMapByLocationAndTeacher(int currentLocationID, int teacherID) throws SQLServerException, SQLException {
        HashMap<Integer, String> classes = new HashMap<>();
        String sql = "SELECT DISTINCT(sc.ID) AS 'SchoolClassID' "
                + "FROM Location location "
                + "JOIN AcademyLocation aLocation ON aLocation.LocationID = location.ID  "
                + "JOIN AcademySchoolClass aSchoolClass ON aSchoolClass.AcademyLocationID = aLocation.ID "
                + "JOIN SchoolClass sc ON sc.ID = aSchoolClass.SchoolClassID "
                + "JOIN SchoolClassSemesterSubject semesterSubject ON semesterSubject.SchoolClassID = aSchoolClass.SchoolClassID "
                + "WHERE location.ID = ? "
                + "AND "
                + "semesterSubject.TeacherID = ? ";

        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, currentLocationID);
            ps.setInt(2, teacherID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SchoolClass schoolClass = getSchoolClassByID(rs.getInt("SchoolClassID"));
                classes.put(schoolClass.getID(), schoolClass.getSchoolClassName());
            }
            return classes;
        }
    }

    /**
     * Get teacher from DB by adamlars90@gmail.coms
     *
     * @param teacherEmail
     * @return
     * @throws com.microsoft.sqlserver.jdbc.SQLServerException
     */
    public Teacher getTeacherByEmail(String teacherEmail) throws SQLServerException, SQLException {
        String sql = "SELECT p.ID AS 'PersonID', t.ID AS 'TeacherID', p.FirstName AS 'TeacherFirstName', p.LastName AS 'TeacherLastName', p.Email AS 'TeacherEmail' "
                + "FROM Teacher t "
                + "JOIN Person p ON p.ID = t.PersonID "
                + "WHERE p.Email = ? ";

        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, teacherEmail);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return getOneTeacher(rs);
            }
            return null;
        }
    }

    /**
     * Get one teacher
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private Teacher getOneTeacher(ResultSet rs) throws SQLException {
        int ID = rs.getInt("PersonID");
        int TeacherID = rs.getInt("TeacherID");
        String FirstName = rs.getString("TeacherFirstName");
        String LastName = rs.getString("TeacherLastName");
        String Email = rs.getString("TeacherEmail");

        Teacher teacher = new Teacher(ID, TeacherID, FirstName, LastName, Email);
        return teacher;
    }

    /**
     * <<<<<<< HEAD Gets the ID of the first schoolClass for the parsed teacher
     * on the parsed day. Returns 0 if no class is found. TODO RKL: Make so it's
     * not the first schoolClass.
     *
     * @param teacherID
     * @param dateHalfHourBefore
     * @param dateHalfHourAfter
     * @return
     * @throws SQLException
     */
    public List<Integer> getSchoolClassIDForSpecificTeacherAndDate(int teacherID, String dateHalfHourBefore, String dateHalfHourAfter) throws SQLException {
        List<Integer> schoolClassIDs = new ArrayList<>();
        String sql = "SELECT sc.ID FROM SchoolClass sc "
                + "JOIN SchoolClassSemesterSubject scss ON sc.ID = scss.SchoolClassID "
                + "JOIN Teacher t ON scss.TeacherID = t.ID "
                + "JOIN SchoolClassSemesterLesson scsl ON scss.ID = scsl.SchoolClassSemesterSubjectID "
                + "WHERE t.ID = ? AND scsl.Date BETWEEN ? AND ?";

        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, teacherID);
            ps.setString(2, dateHalfHourBefore);
            ps.setString(3, dateHalfHourAfter);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                schoolClassIDs.add(rs.getInt("ID"));
            }
        }
        return schoolClassIDs;
    }

    /*
     * Get all teacher schoolClassNames for specific semester
     *
     * @param schoolClassIDs
     * @param semester
     * @return
     */
    public List<String> getAllTeacherSchoolClassesBySemester(List<Integer> schoolClassIDs, String semesterName) throws SQLServerException, SQLException {
        List<String> schoolClassNames = new ArrayList<>();
        String sql = "SELECT DISTINCT(sc.Name) AS 'SchoolClassName' FROM SchoolClass sc "
                + "JOIN SchoolClassSemesterSubject semesterSubject ON semesterSubject.SchoolClassID = sc.ID "
                + "JOIN Semester sem ON sem.ID = semesterSubject.SemesterID "
                + "WHERE "
                + "sc.ID = ? "
                + "AND "
                + "sem.Name = ? ";

        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            for (Integer schoolClassID : schoolClassIDs) {

                ps.setInt(1, schoolClassID);
                ps.setString(2, semesterName);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    schoolClassNames.add(getOneSchoolClassName(rs));
                }
            }
            return schoolClassNames;
        }
    }

    /**
     * Get one schoolClassName
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private String getOneSchoolClassName(ResultSet rs) throws SQLException {
        String SchoolClassName = rs.getString("SchoolClassName");

        return SchoolClassName;
    }

    /**
     * Gets lessons with the params from the DB.
     *
     * @param schoolClassID
     * @param semesterID
     * @return
     * @throws SQLException
     */
    public List<SchoolClassSemesterLesson> getAllSchoolClassSemesterLessonsBySchoolClassIDAndSemesterID(int schoolClassID, int semesterID) throws SQLException {
        schoolClassSemesterSubjects = new ArrayList<>();
        //Get a hold of all the subjects the SchoolClass has
        getAllSchoolSemesterSubjectsFromSpecificSchoolClass(schoolClassID);
        String sql = "SELECT semesterLesson.ID "
                + "AS 'SemesterLessonID', "
                + "semesterLesson.SchoolClassSemesterSubjectID "
                + "AS 'SemesterSubjectID', "
                + "semesterLesson.Date "
                + "AS 'SemesterLessonDate' "
                + "FROM SchoolClassSemesterLesson semesterLesson "
                + "JOIN SchoolClassSemesterSubject semesterSubject ON semesterLesson.SchoolClassSemesterSubjectID = semesterSubject.ID "
                + "WHERE semesterSubject.SchoolClassID = ? "
                + "AND semesterSubject.SemesterID = ?";

        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, schoolClassID);
            ps.setInt(2, semesterID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                schoolClassSemesterSubjects.add(getOneSchoolSemesterLessons(rs));
            }
            return schoolClassSemesterSubjects;
        }
    }

    /**
     * Gets subjects with the params from the DB.
     *
     * @param schoolClassID
     * @param semesterID
     * @return
     * @throws SQLException
     */
    public List<SchoolSemesterSubject> getAllSchoolClassSemesterSubjectsBySchoolClassIDAndSemesterID(int schoolClassID, int semesterID) throws SQLException {
        schoolClassSemesterSubjects = new ArrayList<>();
        //Get a hold of all the subjects the SchoolClass has
        getAllSchoolSemesterSubjectsFromSpecificSchoolClass(schoolClassID);
        String sql = "SELECT "
                + "semesterSubject.ID "
                + "AS "
                + "'SemesterID', "
                + "c.ID "
                + "AS "
                + "'SchoolClassID', "
                + "c.Name "
                + "AS "
                + "'SchoolClassName', "
                + "sem.Name "
                + "AS "
                + "'SemesterName', "
                + "schoolSubject.Name "
                + "AS "
                + "'SchoolSubjectName', "
                + "p.FirstName "
                + "AS "
                + "'TeacherFirstName'"
                + "FROM SchoolClassSemesterSubject semesterSubject "
                + "JOIN SchoolClass c ON semesterSubject.SchoolClassID = c.ID "
                + "JOIN Semester sem ON semesterSubject.SemesterID = sem.ID "
                + "JOIN SchoolSubject schoolSubject ON semesterSubject.SchoolSubjectID = schoolSubject.ID "
                + "JOIN Teacher t ON semesterSubject.TeacherID = t.ID "
                + "JOIN Person p ON p.ID = t.PersonID "
                + "WHERE c.ID = ? "
                + "AND sem.ID = ?";

        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, schoolClassID);
            ps.setInt(2, semesterID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                schoolSemesterSubjects.add(getOneSchoolSemesterSubject(rs));
            }
            return schoolSemesterSubjects;
        }
    }

    /**
     * Converts a semester name to an ID.
     *
     * @param semesterName
     * @return
     * @throws SQLException
     */
    public int getSemesterIDByName(String semesterName) throws SQLException {
        String sql = "SELECT "
                + "Sem.ID AS 'SemesterID' "
                + "FROM "
                + "Semester sem "
                + "WHERE "
                + "sem.Name = ?";

        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, semesterName);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return getOneSemester(rs);
            }
            return 0;
        }
    }

    /**
     * Fetches the semesterID from the given resultset.
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private int getOneSemester(ResultSet rs) throws SQLException {
        int semesterID = rs.getInt("SemesterID");
        return semesterID;
    }

}
