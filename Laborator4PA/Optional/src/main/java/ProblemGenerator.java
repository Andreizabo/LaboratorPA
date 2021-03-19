import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProblemGenerator {
    public static Problem generateProblem(int maxStudentsSchools, int minSchoolCapacity, int maxSchoolCapacity) {
        if(maxSchoolCapacity < minSchoolCapacity) {
            return null;
        }
        Faker faker = new Faker();
        Random random = new Random();
        Problem problem = new Problem(new ArrayList<>(), new ArrayList<>());

        problem.addStudent(
                IntStream.rangeClosed(0, maxStudentsSchools)
                        .mapToObj(i -> new Student(faker.name().fullName(), 5 + random.nextDouble() * 5))
        );

        problem.addSchool(
                IntStream.rangeClosed(0, maxStudentsSchools)
                        .mapToObj(i -> new School(faker.pokemon().name() + " School", minSchoolCapacity + random.nextInt(maxSchoolCapacity - minSchoolCapacity)))
        );

        problem.getStudents().forEach((s) -> problem.addStudentPreferences(s,
                problem.getSchools().stream().filter((sc) -> random.nextBoolean() || random.nextBoolean())
                        .collect(Collectors.toList())));

        problem.shuffle();

        problem.getSchools().forEach((s) -> problem.addSchoolPreferences(s,
                problem.getStudents().stream().filter((st) -> problem.getStudentPreferences().get(st).contains(s))
                        .sorted((st1, st2) -> {
                            if(st2.getGrade().compareTo(st1.getGrade()) == 0) {
                                return st1.getName().compareTo(st2.getName());
                            }
                            return st2.getGrade().compareTo(st1.getGrade());
                        }).collect(Collectors.toList())));

        return problem;
    }
}
