package net.blabux;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class Ship implements Entity {
	private static final double INIT_DEG = 270d;
	private static final double TRI1 = Math.toRadians(0d);
	private static final double TRI2 = Math.toRadians(135d);
	private static final double TRI3 = Math.toRadians(225d);
	private static final double TURN = Math.toRadians(10d);
	private static final double ACCELERATE = 1.0;

	private double x;
	private double y;
	private double width;
	private double height;
	private double heading;
	private double speed;

	public Ship(Rectangle bounds) {
		this.x = bounds.x;
		this.y = bounds.y;
		this.width = bounds.width;
		this.height = bounds.height;
		this.heading = Math.toRadians(INIT_DEG);
	}

	@Override
	public void update() {
		x = x + Math.cos(heading) * speed;
		y = y + Math.sin(heading) * speed;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		Point center = center();
		double radius = radius();
		Point p1 = forAngle(center, radius, TRI1);
		Point p2 = forAngle(center, radius, TRI2);
		Point p3 = forAngle(center, radius, TRI3);
		g.drawLine(p1.x, p1.y, p2.x, p2.y);
		g.drawLine(p2.x, p2.y, p3.x, p3.y);
		g.drawLine(p3.x, p3.y, p1.x, p1.y);
	}

	private double radius() {
		return Math.min(width / 2, height / 2);
	}

	private Point forAngle(Point center, double radius, double angle) {
		return new Point(
				(int) (center.x + (radius * Math.cos(heading + angle))),
				(int) (center.y + (radius * Math.sin(heading + angle))));
	}

	@Override
	public Rectangle bounds() {
		return new Rectangle((int)Math.round(x),(int)Math.round(y),(int)Math.round(width),(int)Math.round(height));
	}

	public void faster() {
		speed = speed + ACCELERATE;
		if (speed > 5)
			speed = 5.0;
	}

	public void slower() {
		speed = speed - ACCELERATE;
		if (speed < 0)
			speed = 0.0;
	}

	public void rotateLeft() {
		heading = heading - TURN;
	}

	public void rotateRight() {
		heading = heading + TURN;
	}

	public void moveBy(double deltaX, double deltaY) {
		x += deltaX;
		y += deltaY;
	}
}
