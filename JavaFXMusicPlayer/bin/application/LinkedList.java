package application;

public class LinkedList {

    Node head, tail;
    public boolean isEmpty() {
        return this.head == null;
    }

    public void addToEnd(String filePath) {
        Node node = new Node(filePath);
        if (!isEmpty()) {
            this.tail.next = node;
            node.prev = this.tail;
            this.tail = node;
        } else {
            this.head = this.tail = node;
        }
    }

	public void printList(){
		for(Node temp = this.head; temp!=null; temp = temp.next){
			System.out.println(temp.filepath);
	}
	}

}
