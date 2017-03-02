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

    /**
     * Get the ESemester matching the string
     *
     * @param eSemester
     * @return
     */
    public static ESemester getESemesterFromString(String eSemester) {
        switch (eSemester) {
            case "1. Semester":
                return FIRST_SEMESTER;
            case "2. Semester":
                return SECOND_SEMESTER;
            case "3. Semester":
                return THIRD_SEMESTER;
            case "4. Semester":
                return FOURTH_SEMESTER;
            case "5. Semester":
                return FIFTH_SEMESTER;
            default:
                return null;
        }
    }
}
