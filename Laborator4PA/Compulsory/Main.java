import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Student[] studentArray = IntStream.rangeClosed(0, 3)
                .mapToObj(i -> new Student("S" + i))
                .toArray(Student[]::new);

        School[] schoolArray = IntStream.rangeClosed(0, 2)
                .mapToObj(i -> new School("H" + i, (i == 0 ? 1 : 2)))
                .toArray(School[]::new);

        List<Student> students = new LinkedList<>(Arrays.asList(studentArray));
        Set<School> schools = new TreeSet<>(Arrays.asList(schoolArray));

        students.sort(Comparator.comparing(Student::getName));

        System.out.print("\nSorted students:\n\t");
        students.forEach((s) -> System.out.print(s.getName() + " "));
        System.out.print("\n");

        Map<Student, List<School>> studentPreferences = new LinkedHashMap<>();
        studentPreferences.put(students.get(0), Arrays.asList(
                schoolArray[0],
                schoolArray[1],
                schoolArray[2]
                ));
        studentPreferences.put(students.get(1), Arrays.asList(
                schoolArray[0],
                schoolArray[1],
                schoolArray[2]
        ));
        studentPreferences.put(students.get(2), Arrays.asList(
                schoolArray[0],
                schoolArray[1]
        ));
        studentPreferences.put(students.get(3), Arrays.asList(
                schoolArray[0],
                schoolArray[2]
        ));

        Map<School, List<Student>> schoolPreferences = new TreeMap<>();
        schoolPreferences.put(schoolArray[0], Arrays.asList(
                students.get(3),
                students.get(0),
                students.get(1),
                students.get(2)
        ));
        schoolPreferences.put(schoolArray[1], Arrays.asList(
                students.get(0),
                students.get(2),
                students.get(1)
        ));
        schoolPreferences.put(schoolArray[2], Arrays.asList(
                students.get(0),
                students.get(1),
                students.get(3)
        ));

        studentPreferences.forEach((student, preferredSchools) -> {
                System.out.print("\nStudent " + student.getName() + " wants to go to these schools, in the following order:");
                preferredSchools.forEach((school -> System.out.print("\n\t" + school.getName())));
        });

        schoolPreferences.forEach((school, preferredStudents) -> {
            System.out.print("\nSchool " + school.getName() + " wants these students, in the following order:");
            preferredStudents.forEach((student -> System.out.print("\n\t" + student.getName())));
        });
    }
}
