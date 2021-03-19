import java.util.HashMap;
import java.util.Map;

public class Solution {
    private Map<Student, School> assignments;

    public Solution() {
        assignments = new HashMap<>();
    }

    public void addAssignment(Student student, School school) {
        assignments.put(student, school);
    }

    public School getAssignment(Student student) {
        return assignments.get(student);
    }

    public boolean existsAssignment(Student student) {
        return assignments.containsKey(student);
    }

    public void printSolution(String prefix, String suffix) {
        System.out.print(prefix);

        assignments.forEach(
                (student, school) -> {
                    if(school == null) {
                        System.out.print("\nStudent " + student.getName() + " was not assigned to any school");
                    }
                    else {
                        System.out.print("\nStudent " + student.getName() + " will go to " + school.getName());
                    }
                }
        );

        System.out.print(suffix);
    }
}
