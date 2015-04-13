package net.blabux;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Mine extends Object implements Entity {
	private double x;
	private double y;
	private double width;
	private double height;

	public Mine(Rectangle bounds) {
		this.x = bounds.x;
		this.y = bounds.y;
		this.width = bounds.width;
		this.height = bounds.height;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.red);
		g.fillRect((int)x, (int)y, (int)width, (int)height);
	}

	@Override
	public Rectangle bounds() {
		return new Rectangle((int)Math.round(x),(int)Math.round(y),(int)Math.round(width),(int)Math.round(height));
	}

}
