/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be;

import attendanceautomation.be.enums.ESchoolDayName;
import attendanceautomation.be.enums.ESchoolSubject;
import attendanceautomation.be.enums.ESchoolWeekNumber;
import attendanceautomation.be.enums.ETeacher;
import java.util.ArrayList;

public class MockData {

    private final ArrayList<Student> easv2016AStudents;

    private final ArrayList<SchoolDay> schoolDays;

    private final ArrayList<SchoolWeek> schoolWeeks;

    private final SchoolClass EASV2016A;

    private final SchoolLesson SCO = new SchoolLesson(ESchoolSubject.SCO, ETeacher.PETER_STEGGER);
    private final SchoolLesson SDE = new SchoolLesson(ESchoolSubject.SDE, ETeacher.JEPPE_LED);
    private final SchoolLesson ITO = new SchoolLesson(ESchoolSubject.ITO, ETeacher.LARS_BILDE);
    private final SchoolLesson DBOS = new SchoolLesson(ESchoolSubject.DBOS, ETeacher.BENT_PEDERSEN);

    public MockData() {
        easv2016AStudents = new ArrayList<>();
        schoolDays = new ArrayList<>();
        schoolWeeks = new ArrayList<>();
        createSchoolDays();
        createSchoolWeeks();
        createStudents();
        EASV2016A = new SchoolClass("EASV2016A", schoolWeeks);
        EASV2016A.addLessonToClass(SCO);
        EASV2016A.addLessonToClass(SDE);
        EASV2016A.addLessonToClass(ITO);
        EASV2016A.addLessonToClass(DBOS);
        EASV2016A.addAllStudents(easv2016AStudents);
    }

    /**
     * Create students
     */
    private void createStudents() {
        Student adam = new Student("Adam", "Hansen");
        Student bo = new Student("Bo", "Sinclair");
        Student casper = new Student("Casper", "Rødgaard");
        Student casperJ = new Student("Casper", "Jensen");
        Student emil = new Student("Emil", "Johansen");
        Student frederik = new Student("Frederik", "Dyrberg");
        Student jacob = new Student("Jacob", "Enemark");
        Student jens = new Student("Jens", "Nissen");
        Student jesper = new Student("Jesper", "Riis");
        Student joan = new Student("Joan", "Christensen");
        Student kenneth = new Student("Kenneth", "Kruse");
        Student kenni = new Student("Kenni", "Rasmussen");
        Student lucas = new Student("Lucas", "Rasmussen");
        Student mads = new Student("Mads", "Lorentzen");
        Student mathias = new Student("Mathias", "Plougmann");
        Student mathiasSkov = new Student("Mathias", "Rasmussen");
        Student michael = new Student("Michael", "Ibsen");
        Student mickei = new Student("Mickaei", "Jensen");
        Student miklas = new Student("Miklas", "Kruchov");
        Student nicolai = new Student("Nicolai", "Larsen");
        Student patrick = new Student("Patrick", "Hansen");
        Student rasmus = new Student("Rasmus", "Lindved");
        Student simon = new Student("Simon", "Birkedal");
        Student simonH = new Student("Simon", "Hansen");
        Student stefan = new Student("Stefan", "Olsen");
        Student stephan = new Student("Stephan", "Rosengreen");
        Student stephanF = new Student("Stephan", "Fuhlendorff");
        Student thomas = new Student("Thomas", "Hansen");

        easv2016AStudents.add(adam);
        easv2016AStudents.add(bo);
        easv2016AStudents.add(casper);
        easv2016AStudents.add(casperJ);
        easv2016AStudents.add(emil);
        easv2016AStudents.add(frederik);
        easv2016AStudents.add(jacob);
        easv2016AStudents.add(jens);
        easv2016AStudents.add(jesper);
        easv2016AStudents.add(joan);
        easv2016AStudents.add(kenneth);
        easv2016AStudents.add(kenni);
        easv2016AStudents.add(lucas);
        easv2016AStudents.add(mads);
        easv2016AStudents.add(mathias);
        easv2016AStudents.add(mathiasSkov);
        easv2016AStudents.add(michael);
        easv2016AStudents.add(mickei);
        easv2016AStudents.add(miklas);
        easv2016AStudents.add(nicolai);
        easv2016AStudents.add(patrick);
        easv2016AStudents.add(rasmus);
        easv2016AStudents.add(simon);
        easv2016AStudents.add(simonH);
        easv2016AStudents.add(stefan);
        easv2016AStudents.add(stephan);
        easv2016AStudents.add(stephanF);
        easv2016AStudents.add(thomas);
    }

    /**
     *
     * @return the class EASV2016A
     */
    public SchoolClass getEasv2016A() {
        return EASV2016A;
    }

    private void createSchoolDays() {
        createMonday();
        createTuesday();
        createWednesday();
        createThursday();
        createFriday();
    }

    private void createMonday() {
        SchoolDay monday = new SchoolDay(ESchoolDayName.MONDAY);

        monday.addLesson(SCO);
        monday.addLesson(SDE);

        schoolDays.add(monday);
    }

    private void createTuesday() {
        SchoolDay tuesday = new SchoolDay(ESchoolDayName.TUEDAY);

        tuesday.addLesson(SDE);
        tuesday.addLesson(ITO);

        schoolDays.add(tuesday);
    }

    private void createWednesday() {
        SchoolDay wednesday = new SchoolDay(ESchoolDayName.WEDNESDAY);

        wednesday.addLesson(DBOS);

        schoolDays.add(wednesday);
    }

    private void createThursday() {
        SchoolDay thursday = new SchoolDay(ESchoolDayName.THURSDAY);

        thursday.addLesson(SCO);

        schoolDays.add(thursday);
    }

    private void createFriday() {
        SchoolDay friday = new SchoolDay(ESchoolDayName.FRIDAY);

        friday.addLesson(SCO);

        schoolDays.add(friday);
    }

    private void createSchoolWeeks() {
        SchoolWeek week5 = new SchoolWeek(ESchoolWeekNumber.WEEK_5);
        SchoolWeek week6 = new SchoolWeek(ESchoolWeekNumber.WEEK_6);
        SchoolWeek week7 = new SchoolWeek(ESchoolWeekNumber.WEEK_7);
        SchoolWeek week8 = new SchoolWeek(ESchoolWeekNumber.WEEK_8);
        schoolWeeks.add(week5);
        schoolWeeks.add(week6);
        schoolWeeks.add(week7);
        schoolWeeks.add(week8);
        addSchoolDaysToWeeks();
    }

    private void addSchoolDaysToWeeks() {
        for (SchoolWeek schoolWeek : schoolWeeks) {
            schoolWeek.setSchoolDays(schoolDays);
        }
    }

}
