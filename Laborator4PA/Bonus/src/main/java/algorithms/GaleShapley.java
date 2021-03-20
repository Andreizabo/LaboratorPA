import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GaleShapley implements Algorithm {
    @Override
    public Solution solve(Problem problem) {
        CustomProblem customProblem = (CustomProblem)problem;
        Solution solution = new Solution();

        List<School> schoolSlots = new ArrayList<>();
        customProblem.getSchools().forEach(s -> {
            for(int i = 0; i < s.getCapacity(); ++i) {
                schoolSlots.add(new School(s.getName(), s.getCapacity()));
            }
        });

        Map<Student, Integer> schoolIndex = new HashMap<>();
        customProblem.students.forEach(student -> schoolIndex.put(student, 0));

        boolean allGood = false;

        while (!allGood) {
            allGood = true;
            for(Student student : customProblem.getStudents()) {
                if(solution.getAssignment(student) != null) {
                    continue;
                }
                if(customProblem.getStudentPreferences().get(student).size() <= schoolIndex.get(student)) {
                    continue;
                }
                allGood = false;

                List<School> wantedSchools = customProblem.getStudentPreferences().get(student).get(schoolIndex.get(student));

                for(School wantedSchool : wantedSchools) {
                    if(solution.getAssignment(student) == null) {
                        for(School slot : schoolSlots) {
                            if(slot.getName().equals(wantedSchool.getName())) {
                                if (solution.getAssignment(slot) == null) {
                                    solution.addAssignment(student, slot);
                                }
                                else {
                                    Student otherStudent = solution.getAssignment(slot);
                                    if (customProblem.getSchoolPreferences(slot.getName()).indexOf(otherStudent) > customProblem.getSchoolPreferences(slot.getName()).indexOf(student)) {
                                        solution.removeAssignment(otherStudent);
                                        solution.addAssignment(student, slot);
                                    }
                                }
                            }
                        }
                    }
                    else {
                        break;
                    }
                }

                schoolIndex.put(student, schoolIndex.get(student) + 1);
            }
        }

        return solution;
    }
}
