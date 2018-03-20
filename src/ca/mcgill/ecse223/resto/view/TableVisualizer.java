package ca.mcgill.ecse223.resto.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import com.sun.javafx.geom.Ellipse2D;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;

class TableVisualizer extends JPanel {

	private static final long serialVersionUID = 5765666411683246454L;

	// UI elements
	private List<Rectangle2D> rectangles = new ArrayList<Rectangle2D>();
	private List<Rectangle2D> seats = new ArrayList<Rectangle2D>();

	int lineHeight;
	private static final int RECTHEIGHT = 20;
	private static final int SPACING = 10;
	//private static final int MAXNUMBEROFTableSSHOWN = 10;

	private HashMap<Rectangle2D, Table> Tables;
	private String TableDetails;
	private Table selectedTable;
	private int firstVisibleTable;

	public TableVisualizer() {
		super();
		init();
	}

	private void init() {
		Tables = new HashMap<Rectangle2D, Table>();
		selectedTable = null;
		firstVisibleTable = 0;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				for (Rectangle2D rectangle : rectangles) {
					if (rectangle.contains(x, y)) {
						selectedTable = Tables.get(rectangle);
						break;
					}
				}
				repaint();
			}
		});
	}

	public void setSeats(Table table) {
		Tables = new HashMap<Rectangle2D, Table>();
		TableDetails = null;
		selectedTable = null;
		firstVisibleTable = 0;
		repaint();
	}


	private void doDrawing(Graphics g) {
		RestoApp r = RestoAppApplication.getRestoApp();

		int number = 0;
		
			Graphics2D g2d = (Graphics2D) g.create();
			BasicStroke thickStroke = new BasicStroke(4);
			g2d.setStroke(thickStroke);
			lineHeight = (number - 1) * (RECTHEIGHT + SPACING);
			//g2d.drawLine(LINEX, LINETOPY, LINEX, LINETOPY + lineHeight);

			BasicStroke thinStroke = new BasicStroke(2);
			g2d.setStroke(thinStroke);
			rectangles.clear();
			seats.clear();
			Tables.clear();
			int index = 0;
			int visibleIndex = 0;
			for (Table table : r.getCurrentTables()) {
				if (index >= firstVisibleTable) {
					
					int LINEX = table.getX();
					int RECTWIDTH = table.getWidth();
					int LINETOPY = table.getY();
					int RECTHEIGHT = table.getLength();
					
					int maxSeats = table.getSeats().size();
					int sCount = 0;
					
					Rectangle2D rectangle = new Rectangle2D.Float(table.getX(), table.getY(), table.getWidth(), table.getLength());
					rectangles.add(rectangle);
					Tables.put(rectangle, table);

					g2d.setColor(Color.WHITE);
					g2d.fill(rectangle);
					g2d.setColor(Color.BLACK);
					g2d.draw(rectangle);
					g2d.drawString(new Integer(table.getNumber()).toString(), (table.getX()+(table.getX()+table.getWidth()))/2, (table.getY()+(table.getY()+table.getLength()))/2);

					//adding seats
					int distance = 2*(table.getLength() + table.getWidth())/(table.getSeats().size());
					
					System.out.println("DISTANCE: " + distance);
					System.out.println("seats: " + table.getSeats().size());

					int tempX = table.getX();
					int tempY = table.getY();
					
					while(tempX <= table.getX()+table.getWidth() && sCount<maxSeats) {
						//make a seat
						Rectangle2D seat = new Rectangle2D.Float(tempX, table.getY()-10, 10, 10);
						seats.add(seat);
						g2d.draw(seat);
						sCount++;
						tempX += distance;
						//System.out.println("aaaaaaaaaaaaaa");
					}
					
					tempX = table.getX();
					tempY = table.getY();
					while(tempY <= table.getY()+table.getLength() && sCount<maxSeats) {
						//make a seat
						Rectangle2D seat = new Rectangle2D.Float(table.getX() + table.getWidth(), tempY, 10, 10);
						seats.add(seat);
						g2d.draw(seat);
						sCount++;
						tempY += distance;
						//System.out.println("bbbbbbbbbbbbbbbb");
					}
					
					tempX = table.getX();
					tempY = table.getY();
					while(tempX <= table.getX()+table.getWidth() && sCount<maxSeats) {
						//make a seat
						Rectangle2D seat = new Rectangle2D.Float(tempX, table.getY()+table.getLength(), 10, 10);
						seats.add(seat);
						g2d.draw(seat);
						sCount++;
						tempX += distance;
						//System.out.println("ccccccccccccccc");
					}
					
					tempX = table.getX();
					tempY = table.getY();
					while(tempY <= table.getY()+table.getLength() && sCount<maxSeats) {
						//make a seat
						Rectangle2D seat = new Rectangle2D.Float(table.getX()-10, tempY, 10, 10);
						seats.add(seat);
						g2d.draw(seat);
						sCount++;
						tempY += distance;
						//System.out.println("dddddddddddddddd");
					}
					
					
					if (selectedTable != null && selectedTable.equals(table)) {
						g2d.drawString(TableDetails, (table.getX()+(table.getX()+table.getWidth()))/2, (table.getY()+(table.getY()+table.getLength()))/2);
					}

					visibleIndex++;
				}
				index++;
			}
			
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}

}