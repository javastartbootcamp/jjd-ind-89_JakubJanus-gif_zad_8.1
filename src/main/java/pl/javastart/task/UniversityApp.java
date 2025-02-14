package pl.javastart.task;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class UniversityApp {
    private final Map<Integer, Lecturer> lecturers = new HashMap<>();
    private final Map<String, Group> groups = new HashMap<>();
    private final Map<Integer, Student> students = new HashMap<>();

    /**
     * Tworzy prowadzącego zajęcia.
     * W przypadku gdy prowadzący z zadanym id już istnieje, wyświetlany jest komunikat:
     * "Prowadzący z id [id_prowadzacego] już istnieje"
     *
     * @param id        - unikalny identyfikator prowadzącego
     * @param degree    - stopień naukowy prowadzącego
     * @param firstName - imię prowadzącego
     * @param lastName  - nazwisko prowadzącego
     */
    public void createLecturer(int id, String degree, String firstName, String lastName) {
        if (lecturers.containsKey(id)) {
            System.out.println("Prowadzący z id " + id + " już istnieje");
        } else {
            Lecturer newLecturer = new Lecturer(id, degree, firstName, lastName);
            lecturers.put(id, newLecturer);
            System.out.println("Prowadzący " + newLecturer.getDescription() + " został dodany.");
        }
    }

    /**
     * Tworzy grupę zajęciową.
     * W przypadku gdy grupa z zadanym kodem już istnieje, wyświetla się komunikat:
     * "Grupa [kod grupy] już istnieje"
     * W przypadku gdy prowadzący ze wskazanym id nie istnieje wyświetla się komunikat:
     * "Prowadzący o id [id prowadzacego] nie istnieje"
     *
     * @param code       - unikalny kod grupy
     * @param name       - nazwa przedmiotu (np. "Podstawy programowania")
     * @param lecturerId - identyfikator prowadzącego. Musi zostać wcześniej utworzony za pomocą metody {@link #createLecturer(int, String, String, String)}
     */
    public void createGroup(String code, String name, int lecturerId) {
        if (groups.containsKey(code)) {
            System.out.println("Grupa " + code + " już istnieje");
        } else if (!lecturers.containsKey(lecturerId)) {
            System.out.println("Prowadzący o id " + lecturerId + " nie istnieje");
        } else {
            groups.put(code, new Group(code, name, lecturers.get(lecturerId)));
        }
    }


    /**
     * Dodaje studenta do grupy zajęciowej.
     * W przypadku gdy grupa zajęciowa nie istnieje wyświetlany jest komunikat:
     * "Grupa [kod grupy] nie istnieje
     *
     * @param index     - unikalny numer indeksu studenta
     * @param groupCode - kod grupy utworzonej wcześniej za pomocą {@link #createGroup(String, String, int)}
     * @param firstName - imię studenta
     * @param lastName  - nazwisko studenta
     */
    public void addStudentToGroup(int index, String groupCode, String firstName, String lastName) {
        if (!groups.containsKey(groupCode)) {
            System.out.println("Grupa " + groupCode + " nie istnieje");
            return;
        }

        Group group = groups.get(groupCode);

        if (group.hasStudent(index)) {
            System.out.println("Student o indeksie " + index + " jest już w grupie " + groupCode);
            return;
        }

        students.putIfAbsent(index, new Student(index, firstName, lastName));
        group.addStudent(students.get(index));
    }

    /**
     * Wyświetla informacje o grupie w zadanym formacie.
     * Oczekiwany format:
     * Kod: [kod_grupy]
     * Nazwa: [nazwa przedmiotu]
     * Prowadzący: [stopień naukowy] [imię] [nazwisko]
     * Uczestnicy:
     * [nr indeksu] [imie] [nazwisko]
     * [nr indeksu] [imie] [nazwisko]
     * [nr indeksu] [imie] [nazwisko]
     * W przypadku gdy grupa nie istnieje, wyświetlany jest komunikat w postaci: "Grupa [kod] nie znaleziona"
     *
     * @param groupCode - kod grupy, dla której wyświetlić informacje
     */
    public void printGroupInfo(String groupCode) {
        if (!groups.containsKey(groupCode)) {
            System.out.println("Grupa " + groupCode + " nie znaleziona");
            return;
        }
        System.out.println(groups.get(groupCode).getDescription());
    }

    /**
     * Dodaje ocenę końcową dla wskazanego studenta i grupy.
     * Student musi być wcześniej zapisany do grupy za pomocą {@link #addStudentToGroup(int, String, String, String)}
     * W przypadku, gdy grupa o wskazanym kodzie nie istnieje, wyświetlany jest komunikat postaci:
     * "Grupa pp-2022 nie istnieje"
     * W przypadku gdy student nie jest zapisany do grupy, wyświetlany jest komunikat w
     * postaci: "Student o indeksie 179128 nie jest zapisany do grupy pp-2022"
     * W przypadku gdy ocena końcowa już istnieje, wyświetlany jest komunikat w postaci:
     * "Student o indeksie 179128 ma już wystawioną ocenę dla grupy pp-2022"
     *
     * @param studentIndex - numer indeksu studenta
     * @param groupCode    - kod grupy
     * @param grade        - ocena
     */
    public void addGrade(int studentIndex, String groupCode, double grade) {
        if (!groups.containsKey(groupCode)) {
            System.out.println("Grupa " + groupCode + " nie istnieje");
            return;
        }
        if (!groups.get(groupCode).hasStudent(studentIndex)) {
            System.out.println("Student o indeksie " + studentIndex + " nie jest zapisany do grupy " + groupCode);
            return;
        }
        if (!groups.get(groupCode).addGrade(studentIndex, grade)) {
            System.out.println("Student o indeksie " + studentIndex + " ma już wystawioną ocenę dla grupy " + groupCode);
        }
    }

    /**
     * Wyświetla wszystkie oceny studenta.
     * Przykładowy wydruk:
     * Podstawy programowania: 5.0
     * Programowanie obiektowe: 5.5
     *
     * @param index - numer indesku studenta dla którego wyświetlić oceny
     */
    public void printGradesForStudent(int index) {
        Student student = students.get(index);
        if (student == null) {
            System.out.println("Brak ocen dla studenta o indeksie " + index);
            return;
        }
        student.printGrades();
    }

    /**
     * Wyświetla oceny studentów dla wskazanej grupy.
     * Przykładowy wydruk:
     * 179128 Marcin Abacki: 5.0
     * 179234 Dawid Donald: 4.5
     * 189521 Anna Kowalska: 5.5
     *
     * @param groupCode - kod grupy, dla której wyświetlić oceny
     */
    public void printGradesForGroup(String groupCode) {
        if (!groups.containsKey(groupCode)) {
            System.out.println("Grupa " + groupCode + " nie istnieje");
            return;
        }
        groups.get(groupCode).printGrades();
    }

    /**
     * Wyświetla wszystkich studentów. Każdy student powinien zostać wyświetlony tylko raz.
     * Każdy student drukowany jest w nowej linii w formacie [nr_indesku] [imie] [nazwisko]
     * Przykładowy wydruk:
     * 179128 Marcin Abacki
     * 179234 Dawid Donald
     * 189521 Anna Kowalska
     */
    public void printAllStudents() {
        students.values().stream()
                .sorted(Comparator.comparingInt(s -> s.index))
                .forEach(s -> System.out.println(s.getDescription()));
    }
}