/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be.enums;

public enum ESemester {

    FIRST_SEMESTER("1. Semester"),
    SECOND_SEMESTER("2. Semester"),
    THIRD_SEMESTER("3. Semester"),
    FOURTH_SEMESTER("4. Semester"),
    FIFTH_SEMESTER("5. Semester");

    private final String text;

    private ESemester(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
