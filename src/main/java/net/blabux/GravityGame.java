package net.blabux;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GravityGame implements Entity {
	private Ship ship;
	private GamePanel panel;

	public static void main(String[] args) {
		GravityGame main = new GravityGame();
		main.start();
	}

	public void init() {
		Point center = center();
		this.ship = new Ship(new Rectangle(center.x, center.y, 10, 10));
		createMines();
	}

	public void update() {
		this.ship.update();
	}

	public void draw(Graphics2D g) {
		this.ship.draw(g);
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
		
	}
}
