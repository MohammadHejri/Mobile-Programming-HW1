package edu.sharif.courseware.model;

import java.util.Objects;

public class Homework {
    private String name;
    private String question;
    private Course course;

    public Homework(String name, String question, Course course) {
        this.name = name;
        this.question = question;
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Course getCourse() {
        return course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Homework homework = (Homework) o;
        return name.equals(homework.name) && course.equals(homework.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, course);
    }
}
