import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProblemGenerator {
    public static NormalProblem generateNormalProblem(int maxStudentsSchools, int minSchoolCapacity, int maxSchoolCapacity) {
        if(maxSchoolCapacity < minSchoolCapacity) {
            return null;
        }
        Faker faker = new Faker();
        Random random = new Random();
        NormalProblem normalProblem = new NormalProblem(new ArrayList<>(), new ArrayList<>());

        normalProblem.addStudent(
                IntStream.rangeClosed(0, maxStudentsSchools)
                        .mapToObj(i -> new Student(faker.name().fullName(), 5 + random.nextDouble() * 5))
        );

        normalProblem.addSchool(
                IntStream.rangeClosed(0, maxStudentsSchools)
                        .mapToObj(i -> new School(faker.pokemon().name() + " School", minSchoolCapacity + random.nextInt(maxSchoolCapacity - minSchoolCapacity)))
        );

        normalProblem.getStudents().forEach((s) -> normalProblem.addStudentPreferences(s,
                normalProblem.getSchools().stream().filter((sc) -> random.nextBoolean() || random.nextBoolean())
                        .collect(Collectors.toList())));

        normalProblem.shuffle();

        normalProblem.getSchools().forEach((s) -> normalProblem.addSchoolPreferences(s,
                normalProblem.getStudents().stream().filter((st) -> normalProblem.getStudentPreferences().get(st).contains(s))
                        .sorted((st1, st2) -> {
                            if(st2.getGrade().compareTo(st1.getGrade()) == 0) {
                                return st1.getName().compareTo(st2.getName());
                            }
                            return st2.getGrade().compareTo(st1.getGrade());
                        }).collect(Collectors.toList())));

        return normalProblem;
    }

    public static CustomProblem generateCustomProblem(int maxStudents, int maxSchools) {
        Faker faker = new Faker();
        Random random = new Random();
        CustomProblem customProblem = new CustomProblem(new ArrayList<>(), new ArrayList<>());

        customProblem.addStudent(
                IntStream.rangeClosed(0, maxStudents)
                        .mapToObj(i -> new Student(faker.name().fullName(), 5 + random.nextDouble() * 5))
        );

        customProblem.addSchool(
                IntStream.rangeClosed(0, maxSchools)
                        .mapToObj(i -> new School(faker.pokemon().name() + " School", 1))
        );

        int remaining = maxStudents - maxSchools;
        if(remaining > 0) {
            int count = remaining / maxSchools;

            for (int i = 0; i < count; ++i) {
                customProblem.getSchools().forEach(s -> s.setCapacity(s.getCapacity() + 1));
            }

            count = remaining % maxSchools;

            for (int i = 0; i < count; ++i) {
                int index = random.nextInt(maxSchools);
                customProblem.getSchools().get(index).setCapacity(customProblem.getSchools().get(index).getCapacity() + 1);
            }
        }

        customProblem.getStudents().forEach((s) -> {
            List<School> addedSchools = customProblem.getSchools().stream().filter((sc) -> random.nextInt(100) > 5).collect(Collectors.toList()); // 95% chance to add a school
            List<List<School>> studentPref = new ArrayList<>();
            addedSchools.forEach(sc -> {
                if(random.nextBoolean() || studentPref.size() == 0) {
                    studentPref.add(new ArrayList<>());
                    studentPref.get(studentPref.size() - 1).add(sc);
                }
                else {
                    studentPref.get(studentPref.size() - 1).add(sc);
                }
            });
            customProblem.addStudentPreferences(s, studentPref);
        });

        customProblem.shuffle();

        customProblem.getSchools().forEach((s) -> customProblem.addSchoolPreferences(s,
                customProblem.getStudents().stream().filter((st) -> customProblem.getStudentPreferences().get(st).contains(s))
                        .sorted((st1, st2) -> {
                            if(random.nextBoolean()) {
                                return -1;
                            }
                            if(st2.getGrade().compareTo(st1.getGrade()) == 0) {
                                return st1.getName().compareTo(st2.getName());
                            }
                            return st2.getGrade().compareTo(st1.getGrade());
                        }).collect(Collectors.toList())));

        return customProblem;
    }
}
