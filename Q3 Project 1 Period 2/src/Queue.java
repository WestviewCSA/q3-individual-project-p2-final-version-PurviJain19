
public class Queue {
	
    private int[] arr;
    private int front;
    private int rear;
    private int size;
    private int capacity;
    
    public Queue(int capacity) {
        this.capacity = capacity;
        arr = new int[capacity];
        front = 0;
        size = 0;
        rear = -1;
          
    }

    public void enqueue(int value) {
        if (size == capacity) {
            System.out.println("Queue is full");
            return;
        }
        rear = (rear + 1) % capacity;
        arr[rear] = value;
        size++;
    }

    public int dequeue() {
        if (size == 0) {
            System.out.println("Queue is empty");
            return -1;
        }
        int removed = arr[front];
        front = (front + 1) % capacity;
        size--;
        return removed;
    }

    public int peek() {
       
    	if (size == 0) {
            System.out.println("Queue is empty");
            return -1;
        }
        
    	return arr[front];
    }

    public boolean isEmpty() {
    	return size == 0;
        
    }

    public void printQueue() {
       
    	if (size == 0) {
            System.out.println("Queue is empty");
            return;
        }

        System.out.print("Queue- ");
       
        for (int i = 0; i < size; i++) {
            System.out.print(arr[(front + i) % capacity] + " ");
        }
        
        System.out.println();
    }
    
}