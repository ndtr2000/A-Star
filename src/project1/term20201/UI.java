package project1.term20201;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class UI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int cells = 20;
	private final int mapSize = 660;
	private final int height = 660;
	private int cellSize = mapSize/cells;
	private int tool;
	private Node startNode = new Node(-1, -1);
	private Node endNode = new Node(-1, -1); 
	private boolean solved = false;
	JLabel label = new JLabel("New label");
	private int[][] map;
	Map canvas;
	AStar aStar = new AStar();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI frame = new UI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UI() {
		
		resetMap();
		
		JPanel contentPane;
		JComboBox<Object> toolBox;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 950, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "CONTROL", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 200, 660);
		contentPane.add(panel);
		panel.setLayout(null);
		String s1[] = {"Start", "End", "Wall", "Erase"};
		toolBox = new JComboBox<Object>(s1);
		toolBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				tool = toolBox.getSelectedIndex();
			}
		});
		toolBox.setBounds(36, 202, 104, 22);
		panel.add(toolBox);
		
		JLabel lbToolbox = new JLabel("Tool box");
		lbToolbox.setFont(new Font("Righteous", Font.PLAIN, 13));
		lbToolbox.setBounds(36, 179, 64, 14);
		panel.add(lbToolbox);
		
		JButton btnSearchButton = new JButton("Start search");
		btnSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (startNode.getX()>-1 && endNode.getX() >-1 && solved == false) {
					initAStar();
					findPath();
					updateMap();
					solved = true;
				}
				else {
					SearchDialog sDialog = new SearchDialog();
					sDialog.setVisible(true);
				}
					
			}
		});
		btnSearchButton.setFont(new Font("Righteous", Font.PLAIN, 12));
		btnSearchButton.setBounds(36, 367, 104, 30);
		panel.add(btnSearchButton);
		
		JButton btnClearButton = new JButton("Clear Map");
		btnClearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMap();
				reset();
				updateMap();
			}
		});
		btnClearButton.setFont(new Font("Righteous", Font.PLAIN, 12));
		btnClearButton.setBounds(36, 472, 104, 30);
		panel.add(btnClearButton);
		label.setFont(new Font("Tahoma", Font.PLAIN, 8));
		
		
		label.setBounds(10, 26, 168, 110);
		panel.add(label);
		
		canvas = new Map();
		canvas.setBounds(240, 10, mapSize+1, height+1);
		contentPane.add(canvas);
		
	}
	public boolean findPath() {

		if (aStar.findPath()) {
			for (Node path: aStar.getPath()) 
					this.map[path.getX()][path.getY()] = 5;
			this.map[startNode.getX()][startNode.getY()] = 0;
			this.map[endNode.getX()][endNode.getY()] = 1;
			return true;
		}	
		return false;
	}
	
	public void initAStar() {
		
		aStar.setStartNode(startNode);
		aStar.setEndNode(endNode);
		aStar.setMap(map);
	}
	
	public void resetMap() {
		startNode.setX(-1);
		startNode.setY(-1);
		endNode.setX(-1);
		endNode.setY(-1);
		map = new int[cells][cells];
		for (int x = 0; x < cells; x++) {
			for (int y = 0; y < cells; y++)
				map[x][y] = 3;
		}
		
	}
	public void updateMap() {
		
		cellSize = mapSize/cells;
		canvas.repaint();
	}
	public void reset() {
		solved = false;
		
	}
	
	class Map extends JPanel implements MouseListener, MouseMotionListener{	

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;


		public Map() {
			addMouseListener(this);
			addMouseMotionListener(this);
		}

		public void paintComponent(Graphics g) {	
			super.paintComponent(g);
			
			for(int x = 0; x < map.length; x++) {	//Paint each node on the grid
				for(int y = 0; y < map[0].length; y++) {
					switch(map[x][y]) {
						case 0:
							g.setColor(Color.GREEN); //Start Node
							break;
						case 1:
							g.setColor(Color.RED);	//End Node
							break;
						case 2:
							g.setColor(Color.BLACK); // Wall
							break;
						case 3:
							g.setColor(Color.WHITE); //Normal Node
							break;
						case 4:
							g.setColor(Color.CYAN); //Checked Node
							break;
						case 5:
							g.setColor(Color.YELLOW); //Path
							break;
					}
					g.fillRect(x*cellSize,y*cellSize,cellSize,cellSize);
					g.setColor(Color.BLACK);
					g.drawRect(x*cellSize,y*cellSize,cellSize,cellSize);

				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			try {
				int x = e.getX()/cellSize;	//Get x and y of the mouse click relation on the map
				int y = e.getY()/cellSize;
				if ((tool == 2 || tool == 3) && (map[x][y] != 0 && map [x][y] !=1))
					map[x][y] = tool;
				updateMap();	
			}catch (Exception z) {}
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {}

		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {
			try {
				int x = e.getX()/cellSize;	//Get x and y of the mouse click relation on the map
				int y = e.getY()/cellSize;
				switch (tool) {
					case 0:{ //Start Node
						if (map[x][y] != 2) {//if not wall
							if(startNode.getX() > -1 && startNode.getY() >-1)
								map[startNode.getX()][startNode.getY()] = 3;
							startNode.setX(x);
							startNode.setY(y);
							map[x][y] = 0;
						}
						break;
					}
					case 1:{ //End node
						if (map[x][y] != 2) {
							if(endNode.getX() > -1 && endNode.getY() >-1)
								map[endNode.getX()][endNode.getY()] = 3;
							endNode.setX(x);
							endNode.setY(y);
							map[x][y] = 1;
						}
						break;
					}
					default:
						if (map[x][y] != 0 && map[x][y] != 1)
							map[x][y] = tool;
						break;
				}
				updateMap();
			}catch (Exception z) {}
			
		}


		@Override
		public void mouseReleased(MouseEvent e) {}
	}
	
	
	
}
