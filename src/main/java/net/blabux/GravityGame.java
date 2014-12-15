package net.blabux;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GravityGame implements Entity {
	private Ship ship;
	private List<Mine> mines;
	private GamePanel panel;
	private Random random;

	public static void main(String[] args) {
		GravityGame main = new GravityGame();
		main.start();
	}

	public void init() {
		random = new Random();
		Point center = center();
		ship = new Ship(new Rectangle(center.x, center.y, 20, 20));
		createMines();
	}

	public void update() {
		ship.update();
		for (Mine each : mines) {
			each.update();
		}
		Point shipCenter = ship.center();
		double sumX = shipCenter.x;
		double sumY = shipCenter.y;
		for (Mine each : mines) {
			Point eachCenter = each.center();
			double dist = shipCenter.distance(eachCenter);
			if (dist < 100) {
				double angle = Math.atan2(eachCenter.y - shipCenter.y, eachCenter.x - shipCenter.x);
				sumX += Math.cos(angle) * (100 - dist);
				sumY += Math.sin(angle) * (100 - dist);
			}
		}
		double moveAngle = Math.atan2(sumY - shipCenter.y, sumX - shipCenter.x);
		ship.moveBy(Math.cos(moveAngle), Math.sin(moveAngle));
	}

	public void draw(Graphics2D g) {
		for (Mine each : mines) {
			each.draw(g);
		}
		ship.draw(g);
	}

	@Override
	public Rectangle bounds() {
		return panel.getBounds();
	}

	private void start() {
		panel = new GamePanel(30, this);
		addKeyListener();
		panel.show();
	}

	private void addKeyListener() {
		panel.setFocusable(true);
		panel.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case 37:
					leftArrowPressed();
					break;
				case 38:
					upArrowPressed();
					break;
				case 39:
					rightArrowPressed();
					break;
				case 40:
					downArrowPressed();
					break;
				default:
					System.out.println(e);
					break;
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

		});
	}

	private void upArrowPressed() {
		ship.faster();
	}

	private void downArrowPressed() {
		ship.slower();
	}

	private void leftArrowPressed() {
		ship.rotateLeft();
	}

	private void rightArrowPressed() {
		ship.rotateRight();
	}

	private void createMines() {
		mines = new ArrayList<Mine>(10);
		Dimension size = new Dimension(10,10);
		for (int i = 0; i < 10; i++) {
			Point point = getRandomPointWithin(size);
			mines.add(new Mine(new Rectangle(point.x, point.y, size.width, size.height)));
		}
	}

	private Point getRandomPointWithin(Dimension within) {
		Rectangle bounds = bounds();
		int x = random.nextInt(bounds.width - within.width);
		int y = random.nextInt(bounds.height - within.height);
		return new Point(x,y);
	}
}
