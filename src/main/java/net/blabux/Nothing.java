package net.blabux;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Nothing implements Entity {
	private final Rectangle bounds;

	public Nothing() {
		bounds = new Rectangle(0, 0, 0, 0);
	}

	@Override
	public void draw(Graphics2D g) {
	}

	@Override
	public Rectangle bounds() {
		return bounds;
	}

}
