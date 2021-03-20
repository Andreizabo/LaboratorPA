import java.util.*;
import java.util.stream.Stream;

public class CustomProblem extends Problem {
    private Map<Student, List<List<School>>> studentPreferences;
    private Map<School, List<Student>> schoolPreferences;

    public CustomProblem(List<Student> students, List<School> schools) {
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

    public void addStudentPreferences(Student student, List<List<School>> schools) {
        if(students.contains(student)) {
            for(List<School> school : schools) {
                if(!this.schools.containsAll(school)) {
                    return;
                }
            }
            studentPreferences.put(student, schools);
        }
    }

    public void shuffle() {
        for(List<List<School>> schools : studentPreferences.values()) {
            for(List<School> school : schools) {
                Collections.shuffle(school);
            }
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
        return students.stream().filter((s) -> {
            List<School> school = new ArrayList<>();
            studentPreferences.get(s).forEach(school::addAll);
            return school.containsAll(schools);
        });
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

    public Map<Student, List<List<School>>> getStudentPreferences() {
        return studentPreferences;
    }

//    public int getStudentPreferencesSize(Student student) {
//        int count = 0;
//        for(List<School> schools : studentPreferences.get(student)) {
//            count += schools.size();
//        }
//        return count;
//    }

    public Map<School, List<Student>> getSchoolPreferences() {
        return schoolPreferences;
    }

    public List<Student> getSchoolPreferences(String schoolName) {
        return schoolPreferences.get(schools.stream().filter(s -> s.getName().equals(schoolName)).findFirst().orElse(null));
    }

    public void printProblem(String prefix, String suffix) {
        System.out.print(prefix);

        studentPreferences.forEach((student, preferredSchools) -> {
            System.out.print("\nStudent " + student.getName() + " wants to go to these schools, in the following order:");
            preferredSchools.forEach(schools -> {
                System.out.print("\n\t" + schools.get(0).getName());
                if(schools.size() > 1) {
                    for(int i = 1; i < schools.size(); ++i) {
                        System.out.print(" or " + schools.get(i).getName());
                    }
                }
            });
        });

        schoolPreferences.forEach((school, preferredStudents) -> {
            System.out.print("\n" + school.getName() + " wants these students, in the following order:");
            preferredStudents.forEach((student -> System.out.print("\n\t" + student.getName())));
        });

        System.out.print(suffix);
    }
}
