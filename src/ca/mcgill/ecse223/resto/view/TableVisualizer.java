package ca.mcgill.ecse223.resto.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Table;

class TableVisualizer extends JPanel {

	private static final long serialVersionUID = 5765666411683246454L;

	// UI elements
	private List<Rectangle2D> rectangles = new ArrayList<Rectangle2D>();
	private static final int LINEX = 30;
	private static final int LINETOPY = 30; 
	int lineHeight;
	private static final int RECTWIDTH = 40;
	private static final int RECTHEIGHT = 20;
	private static final int SPACING = 10;
	//private static final int MAXNUMBEROFTableSSHOWN = 10;

	// data elements
	private Table table;
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
		int number = 0;
		RestoApp r = RestoAppApplication.getRestoApp();
			Graphics2D g2d = (Graphics2D) g.create();
			BasicStroke thickStroke = new BasicStroke(4);
			g2d.setStroke(thickStroke);
			lineHeight = (number - 1) * (RECTHEIGHT + SPACING);
			g2d.drawLine(LINEX, LINETOPY, LINEX, LINETOPY + lineHeight);

			BasicStroke thinStroke = new BasicStroke(2);
			g2d.setStroke(thinStroke);
			rectangles.clear();
			Tables.clear();
			int index = 0;
			int visibleIndex = 0;

	        g2d.setPaint(new Color(150, 150, 150));

	        RenderingHints rh = new RenderingHints(
	                RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON);

	        rh.put(RenderingHints.KEY_RENDERING,
	               RenderingHints.VALUE_RENDER_QUALITY);

	        g2d.setRenderingHints(rh);
	        g2d.fillRect(100, 100, 100, 100);


			for (Table Table : r.getCurrentTables()) {
		        g2d.fillRect(table.getY(), table.getX(), table.getWidth(), table.getLength());


					
					g2d.drawString(new Integer(Table.getNumber()).toString(), table.getX()+1000 - table.getWidth() / 4, table.getY() + table.getLength() / 4 + visibleIndex * (table.getLength() + SPACING));
					System.out.println("done");
					if (selectedTable != null && selectedTable.equals(Table)) {
						g2d.drawString(TableDetails, table.getX() + table.getWidth() * 3 / 4, table.getY() + table.getLength() / 4 + visibleIndex * (table.getLength() + SPACING));
					}

					visibleIndex++;
				
			}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}

}