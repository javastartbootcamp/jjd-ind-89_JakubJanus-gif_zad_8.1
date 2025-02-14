package pl.javastart.task;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Group {
    String code;
    String name;
    Lecturer lecturer;
    Map<Integer, Student> students = new HashMap<>();
    Map<Integer, Double> grades = new HashMap<>();

    Group(String code, String name, Lecturer lecturer) {
        this.code = code;
        this.name = name;
        this.lecturer = lecturer;
    }

    void addStudent(Student student) {
        students.put(student.index, student);
    }

    boolean hasStudent(int studentIndex) {
        return students.containsKey(studentIndex);
    }

    boolean addGrade(int studentIndex, double grade) {
        return grades.putIfAbsent(studentIndex, grade) == null;
    }

    void printGrades() {
        students.values().stream()
                .filter(s -> grades.containsKey(s.index))
                .sorted(Comparator.comparingInt(s -> s.index))
                .forEach(s -> System.out.println(s.index + " " + s.firstName + " " + s.lastName + ": " + grades.get(s.index)));
    }

    public String getDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("Kod: ").append(code).append("\n");
        sb.append("Nazwa: ").append(name).append("\n");
        sb.append("Prowadzący: ").append(lecturer).append("\n");
        sb.append("Uczestnicy:\n");
        students.values().stream()
                .sorted(Comparator.comparingInt(s -> s.index))
                .forEach(s -> sb.append(s.index).append(" ").append(s.firstName).append(" ").append(s.lastName).append("\n"));
        return sb.toString();
    }
}
