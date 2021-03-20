import java.util.*;
import java.util.stream.Stream;

public abstract class Problem {
    protected List<Student> students;
    protected List<School> schools;

    public Problem(List<Student> students, List<School> schools) {
        this.students = new ArrayList<>();
        this.schools = new ArrayList<>();
        students.forEach(this::addStudent);
        schools.forEach(this::addSchool);
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

    public List<Student> getStudents() {
        return students;
    }

    public List<School> getSchools() {
        return schools;
    }

}
