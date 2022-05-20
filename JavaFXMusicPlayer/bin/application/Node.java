package application;


public class Node {
	String filepath;
	Node next,prev;
	public  Node(String filepath, Node next, Node prev) {
		this.filepath=filepath;
		this.next=next;
		this.prev=prev;

	}
	public Node(String filepath) {
		this(filepath,null,null);
	}
}
