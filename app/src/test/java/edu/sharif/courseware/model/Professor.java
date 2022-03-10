package edu.sharif.courseware.model;

public class Professor extends User {
    private String universityName;

    public Professor(String username, String password, String firstname, String lastname, String universityName) {
        super(username, password, firstname, lastname);
        this.universityName = universityName;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }
}
