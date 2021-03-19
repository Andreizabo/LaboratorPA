import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Greedy implements Algorithm {
    @Override
    public Solution solve(Problem problem) {
        Solution solution = new Solution();

        List<Student> sortedStudents = new ArrayList<>(problem.getStudents());
        sortedStudents.sort((st1, st2) -> {
            if(st2.getGrade().compareTo(st1.getGrade()) == 0) {
                return st1.getName().compareTo(st2.getName());
            }
            return st2.getGrade().compareTo(st1.getGrade());
        });

        Map<String, Integer> schoolCapacities = new HashMap<>();
        problem.getSchools().forEach((s) -> schoolCapacities.put(s.getName(), s.getCapacity()));

        for(Student student : sortedStudents) {
            for(School school : problem.getStudentPreferences().get(student)) {
                if(schoolCapacities.get(school.getName()) > 0) {
                    schoolCapacities.put(school.getName(), schoolCapacities.get(school.getName()) - 1);
                    solution.addAssignment(student, school);
                    break;
                }
            }
            if(!solution.existsAssignment(student)) {
                solution.addAssignment(student, null);
            }
        }

        return solution;
    }
}
