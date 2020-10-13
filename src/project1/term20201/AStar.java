package project1.term20201;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.Math; 

public class AStar {
	private int[][] map ;
	private Node startNode;
	private Node endNode;
	private ArrayList <Node> openList;
	private ArrayList <Node> closedList;
	private ArrayList <Node> path;
	
	
	public boolean findPath(){
		openList = new ArrayList<Node>();
		closedList = new ArrayList<Node>();
		path = new ArrayList<Node>();
		this.map[startNode.getX()][startNode.getY()] = 3;
		this.map[endNode.getX()][endNode.getY()] = 3;
		Node curNode;
		openList.add(startNode);
		int newPos[][] = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}, {0, -1}, {0,1}, {-1,0}, {1,0}};
		while (openList.size() > 0) {
			curNode = getLowestFInOpen();
			closedList.add(curNode);
			openList.remove(curNode);			
			
			if (curNode.getX() == endNode.getX() && curNode.getY() == endNode.getY()) {
				 
				Node current = curNode;
				while(current != null) {
					path.add(current);
					current = current.getParent();
				}
				Collections.reverse(path);
				return true;
			}
			ArrayList <Node> children = new ArrayList<>();
			
			//Add child
			for (int[] pos: newPos) {
				int childPos[] = {curNode.getX() + pos [0], curNode.getY() + pos[1]};
				
				if (childPos[0]>=map.length || childPos[0]<0 || childPos[1] >=map[0].length || childPos[1]<0) {
					continue;
				}
				if (map[childPos[0]][childPos[1]] ==2) {
					continue;
				}
				Node childNode = new Node(curNode, childPos[0], childPos[1]);
				children.add(childNode);
				
			}
			
			for (Node child: children) {
				int temp = 0;
				for (Node node: closedList) {
					if (child.equals(node))
						temp = 1;
				}
				if (temp == 1) {
					continue;
				}
					
				child.setG(curNode.getG()+1);
				child.setH((int) Math.pow(child.getX() - endNode.getX(), 2) + (int) Math.pow(child.getY() - endNode.getY(), 2));
				child.setF(child.getG() + child.getH());
				for (Node node: openList ) {
					if (child.equals(node) && child.getG() > node.getG())
						continue;
				}
				openList.add(child);
			}				
		}
		
		return false;
	}
	
	
	public Node getLowestFInOpen() {
		Node lowest = openList.get(0);
		for (int i = 0; i <openList.size(); i ++) {
			if (openList.get(i).getF() < lowest.getF())
				lowest = openList.get(i);
		}
		return lowest;
	}
		
	
	public AStar() {
		
	}
	
	public int[][] getMap() {
		return map;
	}
	public void setMap(int map[][]) {
		this.map = map;
		
	}
	

	public Node getStartNode() {
		return startNode;
	}
	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}
	public Node getEndNode() {
		return endNode;
	}
	public void setEndNode(Node endNode) {
		this.endNode = endNode;
	}
	public ArrayList<Node> getOpenList() {
		return openList;
	}
	public void setOpenList(ArrayList<Node> openList) {
		this.openList = openList;
	}
	public ArrayList<Node> getClosedList() {
		return closedList;
	}
	public void setClosedList(ArrayList<Node> closedList) {
		this.closedList = closedList;
	}
	public ArrayList<Node> getPath() {
		return path;
	}
	public void setPath(ArrayList<Node> path) {
		this.path = path;
	}
	public String toString() {
		String s = new String();
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[0].length; y++)
				s += String.valueOf(map[x][y]);
			
		}
		return s;
	}

}
