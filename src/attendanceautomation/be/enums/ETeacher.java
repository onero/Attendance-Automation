/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be.enums;

public enum ETeacher {
    PETER_STEGGER("Peter Stegger"),
    JEPPE_LED("Jeppe Led"),
    BENT_PEDERSEN("Bent Pedersen"),
    LARS_BILDE("Lars Bilde"),
    STIG_IVERSEN("Stig Iversen");

    private final String text;

    private ETeacher(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    /**
     * Get the ESemester matching the string
     *
     * @param eTeacher
     * @return
     */
    public static ETeacher getETeacherFromString(String eTeacher) {
        switch (eTeacher) {
            case "Peter":
                return PETER_STEGGER;
            case "Jeppe":
                return JEPPE_LED;
            case "Bent":
                return BENT_PEDERSEN;
            case "Lars":
                return LARS_BILDE;
            case "Stig":
                return STIG_IVERSEN;
            default:
                return null;
        }
    }
}
