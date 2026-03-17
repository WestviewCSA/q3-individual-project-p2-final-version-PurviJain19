public class Runner {

    static class Queue {
        private int[] arr;
        private int front, rear, size, capacity;

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
            if (size == 0) return -1;
            return arr[front];
        
        }
   
    }

    
    public static void main(String[] args) {

    	Queue q = new Queue(5);

        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);

        System.out.println(q.dequeue());
        System.out.println(q.peek());
    }
    
}

