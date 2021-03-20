import java.util.*;
import java.util.stream.Stream;

public class NormalProblem extends Problem {
    private Map<Student, List<School>> studentPreferences;
    private Map<School, List<Student>> schoolPreferences;

    public NormalProblem(List<Student> students, List<School> schools) {
        super(students, schools);
        studentPreferences = new HashMap<>();
        schoolPreferences = new HashMap<>();
    }

    public void addStudent(Student student) {
        if(students.contains(student)) {
            return;
        }
        students.add(student);
    }

    public void addStudent(Stream<Student> studentStream) {
        studentStream.filter((s) -> !students.contains(s)).forEach(this::addStudent);
    }

    public void addSchool(School school) {
        if(schools.contains(school)) {
            return;
        }
        schools.add(school);
    }

    public void addSchool(Stream<School> schoolStream) {
        schoolStream.filter((s) -> !schools.contains(s)).forEach(this::addSchool);
    }

    public void addStudentPreferences(Student student, List<School> schools) {
        if(students.contains(student) && this.schools.containsAll(schools)) {
            studentPreferences.put(student, schools);
        }
    }

    public void shuffle() {
        for(List<School> schools : studentPreferences.values()) {
            Collections.shuffle(schools);
        }
    }

    public void addSchoolPreferences(School school, List<Student> students) {
        if(schools.contains(school) && this.students.containsAll(students)) {
            schoolPreferences.put(school, students);
        }
    }

    public void removeStudent(String name) {
        students.removeIf((s) -> s.getName().equals(name));
    }
    public void removeStudent(Student student) {
        students.removeIf((s) -> s.equals(student));
    }
    public void removeSchool(String name) {
        schools.removeIf((s) -> s.getName().equals(name));
    }
    public void removeSchool(School school) {
        schools.removeIf((s) -> s.equals(school));
    }

    public Stream<Student> studentsWhoLike(List<School> schools) {
        return students.stream().filter((s) -> studentPreferences.get(s).containsAll(schools));
    }

    public Stream<School> schoolTopPreference(Student student) {
        return schools.stream().filter((s) -> schoolPreferences.get(s).get(0).equals(student));
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<School> getSchools() {
        return schools;
    }

    public Map<Student, List<School>> getStudentPreferences() {
        return studentPreferences;
    }

    public Map<School, List<Student>> getSchoolPreferences() {
        return schoolPreferences;
    }

    public void printProblem(String prefix, String suffix) {
        System.out.print(prefix);

        studentPreferences.forEach((student, preferredSchools) -> {
            System.out.print("\nStudent " + student.getName() + " wants to go to these schools, in the following order:");
            preferredSchools.forEach((school -> System.out.print("\n\t" + school.getName())));
        });

        schoolPreferences.forEach((school, preferredStudents) -> {
            System.out.print("\n" + school.getName() + " wants these students, in the following order:");
            preferredStudents.forEach((student -> System.out.print("\n\t" + student.getName())));
        });

        System.out.print(suffix);
    }
}
