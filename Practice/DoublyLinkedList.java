public class DoublyLinkedList<T>{
    Node head;
    class Node{
        T data;
        Node next;
        Node  previous;
        public Node(T data){
            this.data = data;
        }
        public Node(T data, Node previous){
            this.data = data;
            this.previous = previous;
        }
    }

    public void add(T data){
        Node curr = head;
        if (head == null){
            head = new Node(data);
        }
        else{
            while(curr.next!=null){
                curr = curr.next;
            }
            Node newNode = new Node(data, curr);
            curr.next = newNode;
        }
    }
    public void delete(int index){
        if (index == 0){
            if (head.next == null){
                head = null;
            }
            else {
                head = head.next;
                head.previous = null;
            }
        }
        else{
            Node curr = head;
            for (int i = 0; i < index; i++){
                curr = curr.next;
            }
            curr.previous.next = curr.next;
        }
    }
    public String toString(){
        String returnString = "";
        Node curr = head;
        while(curr != null){
            returnString += curr.data + "\n";
            curr = curr.next;
        }
        return returnString;
    }
    public static void main(String[] args){
        DoublyLinkedList<Integer> d = new DoublyLinkedList<>();
        d.add(1);
        d.add(2);
        d.add(3);
        d.delete(2);
        System.out.println(d);
    }
}