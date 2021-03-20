public class Student {
    private String name;
    private Double grade;

    public Student(String name, Double grade) {
        this.name = name;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Student)obj).getName().equals(getName());
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
