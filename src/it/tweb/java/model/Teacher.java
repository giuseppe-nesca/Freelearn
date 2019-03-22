package it.tweb.java.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Teacher {
    private int id;
    private String name;
    private String surname;
//    private Set<Course> courses = new HashSet<>();

    public Teacher(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

//    public void mergetTeacher (Teacher teacher) {
//        if (this.equals(teacher)) {
//            this.courses.addAll(teacher.getCourses());
//        }
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        Teacher teacher = (Teacher) o;
        return getId() == teacher.getId() &&
                getName().equals(teacher.getName()) &&
                getSurname().equals(teacher.getSurname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSurname());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

//    public Set<Course> getCourses() {
//        return courses;
//    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
//                ", courses=" + courses +
                '}';
    }
}
