package com.dev.activedir.domain;

/**
 * Describe user information
 */
public class UserInfo {

    /**
     * user first name
     */
    private String firstName;
    /**
     * user last name
     */
    private String lastName;
    /**
     * new user username
     */
    private String userName;
    /**
     * user password
     */
    private String password;
    /**
     * organization unit, where user will be created
     */
    private String organizationUnit;

    public UserInfo() {}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOrganizationUnit() {
        return organizationUnit;
    }

    public void setOrganizationUnit(String organizationUnit) {
        this.organizationUnit = organizationUnit;
    }
}
