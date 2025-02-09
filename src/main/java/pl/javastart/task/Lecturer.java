package pl.javastart.task;

public class Lecturer {
    int id;
    String degree, firstName, lastName;

    Lecturer(int id, String degree, String firstName, String lastName) {
        this.id = id;
        this.degree = degree;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getDescription() {
        return degree + " " + firstName + " " + lastName;
    }

    public String toString() {
        return getDescription();
    }
}