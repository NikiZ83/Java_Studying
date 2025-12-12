import java.util.EmptyStackException;

public class Stack<T> {
    private T[] data;
    private int size;

    @SuppressWarnings("unchecked")
    public Stack(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be > 0");
        data = (T[]) new Object[capacity];
        size = 0;
    }

    // добавление в стек
    public void push(T element) {
        if (size == data.length) {
            throw new IllegalStateException("Stack is full");
        }
        data[size++] = element;
    }

    // удаление и возврат верхнего элемента
    public T pop() {
        if (size == 0) throw new EmptyStackException();
        T elem = data[--size];
        data[size] = null; // помочь сборщику мусора
        return elem;
    }

    // просмотр верхнего элемента без удаления
    public T peek() {
        if (size == 0) throw new EmptyStackException();
        return data[size - 1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    // пример использования
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>(10);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(stack.pop()); // 3
        System.out.println(stack.peek()); // 2
        stack.push(4);
        System.out.println(stack.pop()); // 4
    }
}
