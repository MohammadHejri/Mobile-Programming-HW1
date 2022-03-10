package edu.sharif.courseware.model;

import java.util.ArrayList;
import java.util.Objects;

public class Course {
    private String name;
    private final Professor owner;
    private ArrayList<Student> members;

    public Course(String name, Professor owner) {
        this.name = name;
        this.owner = owner;
        this.members = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Professor getOwner() {
        return owner;
    }

    public ArrayList<Student> getMembers() {
        return members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return name.equals(course.name) && owner.equals(course.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, owner);
    }
}
