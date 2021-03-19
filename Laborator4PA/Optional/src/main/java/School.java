import java.util.List;

public class School implements Comparable<School> {
    private String name;
    private int capacity;

    public School(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public int compareTo(School o) {
        return o.getName().compareTo(this.getName());
    }

    @Override
    public boolean equals(Object obj) {
        return ((School)obj).getName().equals(getName());
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
