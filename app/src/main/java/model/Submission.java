package model;

import java.util.Objects;

public class Submission {
    private String answer;
    private float mark;
    private Student student;
    private Homework homework;
    private DBHelper submissionDBHelper;

    public Submission(String answer, Student student, Homework homework) {
        this.answer = answer;
        this.student = student;
        this.homework = homework;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }

    public Student getStudent() {
        return student;
    }

    public Homework getHomework() {
        return homework;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Submission that = (Submission) o;
        return student.equals(that.student) && homework.equals(that.homework);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, homework);
    }

}
