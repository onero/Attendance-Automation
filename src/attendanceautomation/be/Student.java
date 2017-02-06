/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be;

import attendanceautomation.bll.EmailFactory;
import attendanceautomation.bll.IDFactory;

public class Student {

    private final int ID;

    private final String firstName;

    private final String lastName;

    private final String fullName;

    private final String email;

    private int phone;

    public Student(String firstName, String lastName) {
        ID = IDFactory.getNewID();
        this.firstName = firstName;
        this.lastName = lastName;
        fullName = this.firstName + " " + this.lastName;
        email = EmailFactory.getnewEmail(ID, firstName);
    }

    /**
     *
     * @return first name
     */
    public String getmFirstName() {
        return firstName;
    }

    /**
     *
     * @return last name
     */
    public String getmLastName() {
        return lastName;
    }

    /**
     *
     * @return full name as First name combined with last name, seperated by
     * space
     */
    public String getmFullName() {
        return fullName;
    }

    /**
     *
     * @return phone number
     */
    public int getmPhone() {
        return phone;
    }

    /**
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Update the phone number
     *
     * @param mPhone
     */
    public void setPhone(int mPhone) {
        this.phone = mPhone;
    }

}
