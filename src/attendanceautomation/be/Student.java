/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be;

public class Student extends Person {

    private String mFirstName;

    private String mLastName;

    private final String mFullName = mFirstName + " " + mLastName;

    private int mPhone;

    private String mEmail;

    public Student(String firstName, String lastName, int number, String email) {
        mFirstName = firstName;
        mLastName = lastName;
        mPhone = number;
        mEmail = email;
    }

    /**
     *
     * @return first name
     */
    public String getmFirstName() {
        return mFirstName;
    }

    /**
     *
     * @return last name
     */
    public String getmLastName() {
        return mLastName;
    }

    /**
     *
     * @return full name as First name combined with last name, seperated by
     * space
     */
    public String getmFullName() {
        return mFullName;
    }

    /**
     *
     * @return phone number
     */
    public int getmPhone() {
        return mPhone;
    }

    /**
     *
     * @return email
     */
    public String getmEmail() {
        return mEmail;
    }

    public void setmPhone(int mPhone) {
        this.mPhone = mPhone;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

}
