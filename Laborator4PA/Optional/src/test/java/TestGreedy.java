import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestGreedy {
    @Test
    public void main() {
        Problem problem = ProblemGenerator.generateProblem(5, 2, 3);
        Algorithm greedy = new Greedy();
        Solution solution = greedy.solve(problem);

        List<Student> sortedStudents = new ArrayList<>(problem.getStudents());
        sortedStudents.sort((st1, st2) -> {
            if(st2.getGrade().compareTo(st1.getGrade()) == 0) {
                return st1.getName().compareTo(st2.getName());
            }
            return st2.getGrade().compareTo(st1.getGrade());
        });

        Assert.assertEquals(solution.getAssignment(sortedStudents.get(0)).getName(), problem.getStudentPreferences().get(sortedStudents.get(0)).get(0).getName());
        // The student with the highest grade will always be assigned to his first preferred school
    }
}
