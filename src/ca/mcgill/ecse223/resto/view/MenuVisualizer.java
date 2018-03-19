//package ca.mcgill.ecse223.resto.view;
//
//import java.awt.BasicStroke;
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.geom.Rectangle2D;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import javax.swing.JPanel;
//
//import ca.mcgill.ecse223.resto.application.RestoAppApplication;
//import ca.mcgill.ecse223.resto.model.RestoApp;
//import ca.mcgill.ecse223.resto.model.Menu;
//
//class MenuVisualizer extends JPanel {
//
//	private static final long serialVersionUID = -7403802774454467836L;
//
//	// UI elements
//	private List<Rectangle2D> rectangles = new ArrayList<Rectangle2D>();
//	private static final int LINEX = 30;
//	private static final int LINETOPY = 30; 
//	int lineHeight;
//	private static final int RECTWIDTH = 40;
//	private static final int RECTHEIGHT = 20;
//	private static final int SPACING = 10;
//	
//    // data elements
//	private Menu menu;
//	private HashMap<Rectangle2D, Menu> menus;
//	private String menuDetails;
//	private Menu selectedMenu;
//	private int firstVisibleMenu;
//
//	public MenuVisualizer() {
//		super();
//		init();
//	}
//
//	private void init() {
//		Menus = new HashMap<Rectangle2D, Menu>();
//		selectedMenu = null;
//		firstVisibleMenu = 0;
//		addMouseListener(new MouseAdapter() {
//			@Override
//			public void mousePressed(MouseEvent e) {
//				int x = e.getX();
//				int y = e.getY();
//				for (Rectangle2D rectangle : rectangles) {
//					if (rectangle.contains(x, y)) {
//						selectedMenu = Menus.get(rectangle);
//						break;
//					}
//				}
//				repaint();
//			}
//		});
//	}
//
//	public void setMenus(Menu menu) {
//		menus = new HashMap<Rectangle2D, Menu>();
//		menuDetails = null;
//		selectedMenu = null;
//		firstVisibleMenu = 0;
//		repaint();
//	}
//
//	public void moveUp() {
//		if (firstVisibleMenu > 0) {
//			firstVisibleMenu--;			
//			repaint();
//		}
//	}
//
//	private void doDrawing(Graphics g) {
//		RestoApp r = RestoAppApplication.getRestoApp();
//
//		int number = 0;
//		
//			Graphics2D g2d = (Graphics2D) g.create();
//			BasicStroke thickStroke = new BasicStroke(4);
//			g2d.setStroke(thickStroke);
//			lineHeight = (number - 1) * (RECTHEIGHT + SPACING);
//			g2d.drawLine(LINEX, LINETOPY, LINEX, LINETOPY + lineHeight);
//
//			BasicStroke thinStroke = new BasicStroke(2);
//			g2d.setStroke(thinStroke);
//			rectangles.clear();
//			menus.clear();
//			int index = 0;
//			int visibleIndex = 0;
//			for (Menu Menu : r.getMenus()) {
//				if (index >= firstVisibleMenu) {
//					Rectangle2D rectangle = new Rectangle2D.Float(LINEX - RECTWIDTH / 2, LINETOPY - RECTHEIGHT / 2 + visibleIndex * (RECTHEIGHT + SPACING), RECTWIDTH, RECTHEIGHT);
//					rectangles.add(rectangle);
//					Menus.put(rectangle, Menu);
//
//					g2d.setColor(Color.WHITE);
//					g2d.fill(rectangle);
//					g2d.setColor(Color.BLACK);
//					g2d.draw(rectangle);
//					g2d.drawString(new Integer(Menu.getNumber()).toString(), LINEX - RECTWIDTH / 4, LINETOPY + RECTHEIGHT / 4 + visibleIndex * (RECTHEIGHT + SPACING));
//
//					if (selectedMenu != null && selectedMenu.equals(Menu)) {
//						g2d.drawString(MenuDetails, LINEX + RECTWIDTH * 3 / 4, LINETOPY + RECTHEIGHT / 4 + visibleIndex * (RECTHEIGHT + SPACING));
//					}
//
//					visibleIndex++;
//				}
//				index++;
//			}
//	}
//
//	@Override
//	public void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		doDrawing(g);
//	}
//
//}
