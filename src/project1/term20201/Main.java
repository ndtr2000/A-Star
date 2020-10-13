package project1.term20201;


public class Main {
	
	public static void main(String[] args) {
		Node startNode = new Node (0, 0);
		Node endNode = new Node (19,0);
		AStar aStar = new AStar();
		aStar.setEndNode(endNode);
		aStar.setStartNode(startNode);
		int cells = 20;
		int [][] map = new int[cells][cells];
		for (int x = 0; x < cells; x++) {
			for (int y = 0; y < cells; y++)
				map[x][y] = 3;
		}
		for (int i = 0; i<= 10; i ++) {
			map[10][i] = 2;
		}

		aStar.setMap(map);
		aStar.findPath();
		System.out.println(aStar.getPath());
	}

	
}
