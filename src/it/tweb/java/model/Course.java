package it.tweb.java.model;

import java.util.Objects;

public class Course {
    private int id;
    private int teacherID;
    private String teacherName;
    private String teacherSurname;
    private int subjectID;
    private String subjectName;

    public Course(int id, int teacherID, int subjectID){
        this.id = id;
        this.teacherID = teacherID;
        this.subjectID = subjectID;
    }

    public Course(int id, int teacherID, String teacherName, String teacherSurname, int subjectID, String subjectName){
        this.id = id;
        this.teacherID = teacherID;
        this.subjectID = subjectID;
        this.teacherName = teacherName;
        this.teacherSurname = teacherSurname;
        this.subjectName = subjectName;
    }

    public int getId(){
        return id;
    }


    public int getTeacherID() {
        return teacherID;
    }

    public int getSubjectID() {
        return subjectID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        Course course = (Course) o;
        return getId() == course.getId() &&
                getTeacherID() == course.getTeacherID() &&
                getSubjectID() == course.getSubjectID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTeacherID(), getSubjectID());
    }
}
