package pl.javastart.task;

import java.util.HashMap;
import java.util.Map;

public class Student {
    int index;
    String firstName, lastName;
    Map<String, Double> grades = new HashMap<>();

    Student(int index, String firstName, String lastName) {
        this.index = index;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    void printGrades() {
        grades.forEach((subject, grade) -> System.out.println(subject + ": " + grade));
    }

    public String getDescription() {
        return index + " " + firstName + " " + lastName;
    }
}