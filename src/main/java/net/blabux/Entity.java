package net.blabux;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public interface Entity {
	default void init() {
	}

	default void update() {
	}

	void draw(Graphics2D g);

	Rectangle bounds();

	default Point center() {
		Rectangle bounds = bounds();
		return new Point((int) (bounds.getX() + (bounds.getWidth() / 2)),
				(int) (bounds.getY() + (bounds.getHeight() / 2)));
	}
}
