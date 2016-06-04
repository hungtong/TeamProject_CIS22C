import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;

@SuppressWarnings("serial")	// with this annotation, i don't have to initialize static final serial
public class GraphGenerator extends JFrame {
	
	private static final int DEFAULT_DIMENSION = 30;
	private static final String DEFAULT_WINDOW_TITLE = GraphGenerator.class.getSimpleName();
	
	int width;	// width of each vertex
	int height; // height of each vertex
	ArrayList<SimpleVertex> vertices;	// list of vertices
	ArrayList<SimpleEdge> edges;		// list of edges
	
	public GraphGenerator() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 'X' button closes application
		this.setTitle(DEFAULT_WINDOW_TITLE);	// set title for application
		
		vertices = new ArrayList<>();
		edges = new ArrayList<>();
		
		width = DEFAULT_DIMENSION;
		height = DEFAULT_DIMENSION;		
	}
	
	public GraphGenerator(int width, int height) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 'X' button closes application
		this.setTitle(DEFAULT_WINDOW_TITLE);	// set title for application
		
		vertices = new ArrayList<>();
		edges = new ArrayList<>();
		
		this.width = width;
		this.height = height;
	}
	
	public void drawVertex(String name, int xPixel, int yPixel) {
		for (int i = 0; i < vertices.size(); i++)
			if (name.equalsIgnoreCase(vertices.get(i).name))
				return;
		vertices.add(new SimpleVertex(name, xPixel, yPixel));
	}
	
	public void connect(int source, int destination) {
		edges.add(new SimpleEdge(source, destination));
	}
	
	public void drawGraph() {
		this.repaint();
	}
	
	@Override
	public void paint(Graphics graph) {
		FontMetrics fontMetrics = graph.getFontMetrics();	// get the font to write text
		int vertexHeight = Math.max(height, fontMetrics.getHeight());	// vertex has to be tall enough to hold text
		
		// draw the edges
		graph.setColor(Color.black);
		SimpleVertex edgeSource, edgeDesination;
		for (SimpleEdge currentEdge : edges) {
			edgeSource = vertices.get(currentEdge.source);
			edgeDesination = vertices.get(currentEdge.destination);
			
			// draw the edge to connect two vertices
			graph.drawLine(
				edgeSource.xPixel,
				edgeSource.yPixel,
				edgeDesination.xPixel,
				edgeDesination.yPixel
			);
			
			/* For Euler Circuit, we do not concern about cost nor distance between two vertices
			 // draw a cost in the middle of the edge
			graph.drawString(
				String.valueOf(currentEdge.cost),
				edgeSource.xPixel + Math.abs(edgeSource.xPixel - edgeDesination.xPixel) / 2,
				edgeSource.yPixel + Math.abs(edgeSource.yPixel - edgeDesination.yPixel) / 2
			);
			*/
		}
		
		int vertexWidth;
		for (SimpleVertex currentVertex : vertices) {
			vertexWidth = Math.max(width, fontMetrics.stringWidth(currentVertex.name) + width / 2);
			
			graph.setColor(Color.white);
			graph.fillOval(
				currentVertex.xPixel - vertexWidth / 2,
				currentVertex.yPixel - vertexHeight / 2,
				vertexWidth,
				vertexHeight
			);
			
			// draw the outer of each vertex
			graph.setColor(Color.black);
			graph.drawOval(
				currentVertex.xPixel - vertexWidth / 2,
				currentVertex.yPixel - vertexHeight / 2,
				vertexWidth,
				vertexHeight
			);
			
			// write text inside the vertex
			graph.drawString(
				currentVertex.name,
				currentVertex.xPixel - fontMetrics.stringWidth(currentVertex.name) / 2,
				currentVertex.yPixel + fontMetrics.getHeight() / 2
			);
		}
	}
	
	private class SimpleEdge {
		private int source;
		private int destination;

		private SimpleEdge(int source, int destination) {
			this.source = source;
			this.destination = destination;
		}
	}
	
	private class SimpleVertex {
		private String name;
		private int xPixel;
		private int yPixel;
		
		private SimpleVertex(String name, int xPixel, int yPixel) {
			this.name = name;
			this.xPixel = xPixel;
			this.yPixel = yPixel;
		}
	}

}
