package net.blabux;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class Laser implements Entity {
	private static final double FRONT = Math.toRadians(0d);
	private static final double BACK = Math.toRadians(180d);
	
	private final double heading;
	private final Runnable onDestroy;
	private double x;
	private double y;
	private final double width;
	private final double height;
	
	
	public Laser(Rectangle bounds, double heading, Runnable onDestroy) {
		this.x = bounds.x;
		this.y = bounds.y;
		this.width = bounds.width;
		this.height = bounds.height;
		this.heading = heading;
		this.onDestroy = onDestroy;
	}
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.BLUE);
		if (!g.getClipBounds().contains(bounds())) {
			onDestroy.run();
			return;
		}
		double radius = radius();
		Point center = center();
		Point p1 = forAngle(center, radius, FRONT);
		Point p2 = forAngle(center, radius, BACK);
		g.drawLine(p1.x, p1.y, p2.x, p2.y);
	}

	@Override
	public Rectangle bounds() {
		return new Rectangle((int)Math.round(x),(int)Math.round(y),(int)Math.round(width),(int)Math.round(height));
	}
	
	@Override
	public void update() {
		x = x + Math.cos(heading) * 5.0;
		y = y + Math.sin(heading) * 5.0;
	}
	
	private double radius() {
		return Math.min(width / 2, height / 2);
	}
	
	private Point forAngle(Point center, double radius, double angle) {
		return new Point(
				(int) (center.x + (radius * Math.cos(heading + angle))),
				(int) (center.y + (radius * Math.sin(heading + angle))));
	}

}
