/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be.enums;

public enum ESchoolSubject {
    SCO("SCO"),
    SDE("SDE"),
    ITO("ITO"),
    DBOS("DBOS");

    private final String text;

    private ESchoolSubject(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
