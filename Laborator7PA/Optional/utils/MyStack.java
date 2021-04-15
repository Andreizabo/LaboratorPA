import java.util.ArrayList;
import java.util.List;

public class MyStack<T> {
    private List<T> elements;

    public MyStack(List<T> elements) {
        this.elements = elements;
    }

    public void push(T element) {
        elements.add(element);
    }

    public T top() {
        return elements.get(elements.size() - 1);
    }

    public void pop() {
        elements.remove(elements.size() - 1);
    }

    public boolean contains(T element) {
        return elements.contains(element);
    }

    public int size() { return elements.size(); }

    public MyStack<T> clone() {
        return new MyStack<>(new ArrayList<>(elements));
    }
}
