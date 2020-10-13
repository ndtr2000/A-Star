package project1.term20201;

public class Node {
	private Node parent;
	private int x;
	private int y;
	private int g;
	private int h;
	private int f;
	
	
	
	//Constructor
	public Node(Node parent, int x, int y) {

		this.parent = parent;
		this.x = x;
		this.y = y;
	}
	
	public Node( int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Node() {
		
	}
	
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getG() {
		return g;
	}
	public void setG(int g) {
		this.g = g;
	}
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	public int getF() {
		return f;
	}
	public void setF(int f) {
		this.f = f;
	}


	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[x=" + x + ", y=" + y + "]";
	}
	
	
}
