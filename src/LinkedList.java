public class LinkedList<T> {
    private Node<T> head;
    private int size = 0;

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    public T get(int index) {
        if (index < 0 || index >= size) return null;
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) return;
        if (index == 0) {
            head = head.next;
        } else {
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            current.next = current.next.next;
        }
        size--;
    }

    public int size() {
        return size;
    }

    public Node<T> getHead() {
        return head;
    }

    public void bubbleSort() {
        if (head == null || head.next == null) return;

        boolean swapped;
        do {
            Node<T> current = head;
            Node<T> prev = null;
            swapped = false;

            while (current.next != null) {
                Transaction t1 = (Transaction) current.data;
                Transaction t2 = (Transaction) current.next.data;

                if (t1.getDate().compareTo(t2.getDate()) > 0) {
                    Node<T> tmp = current.next;
                    current.next = tmp.next;
                    tmp.next = current;

                    if (prev == null) {
                        head = tmp;
                    } else {
                        prev.next = tmp;
                    }
                    swapped = true;
                    prev = tmp;
                } else {
                    prev = current;
                    current = current.next;
                }
            }
        } while (swapped);
    }
}
class Node<T> {
    T data;
    Node<T> next;

    public Node(T data) {
        this.data = data;
        this.next = null;
    }
}