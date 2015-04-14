package net.blabux;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import net.blabux.util.WithDefaultsKeyListener;

public class GravityGame implements Entity {
	private Ship ship;
	private List<Mine> mines;
	private Entity laser;
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
		laser = new Nothing();
	}

	public void update() {
		entitiesDo(Entity::update);
		calculateGravityPull();
		checkForDeath();
	}

	private void checkForDeath() {
		for (Mine mine : mines) {
			if (ship.bounds().intersects(mine.bounds())) {
				die();
			}
		}
		if (!bounds().contains(ship.bounds())) {
			die();
		}

	}

	private void die() {
		panel.restart();
	}

	private void calculateGravityPull() {
		Point shipCenter = ship.center();
		double sumX = shipCenter.x;
		double sumY = shipCenter.y;
		for (Mine each : mines) {
			Point eachCenter = each.center();
			double dist = shipCenter.distance(eachCenter);
			if (dist < 100) {
				double angle = Math.atan2(eachCenter.y - shipCenter.y,
						eachCenter.x - shipCenter.x);
				sumX += Math.cos(angle) * Math.pow((100 - dist), 2);
				sumY += Math.sin(angle) * Math.pow((100 - dist), 2);
			}
		}
		double moveAngle = Math.atan2(sumY - shipCenter.y, sumX - shipCenter.x);
		ship.moveBy(Math.cos(moveAngle), Math.sin(moveAngle));
	}

	public void draw(Graphics2D g) {
		entitiesDo(entity -> entity.draw(g));
	}

	@Override
	public Rectangle bounds() {
		return panel.getBounds();
	}

	public void createLaser(Rectangle bounds, double heading) {
		if (0 != laser.bounds().width) {
			return;
		}
		laser = new Laser(bounds, heading, () -> laser = new Nothing());
	}

	private void start() {
		panel = new GamePanel(30, this);
		addKeyListener();
		panel.show();
	}

	private void addKeyListener() {
		panel.setFocusable(true);
		panel.addKeyListener(new WithDefaultsKeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case 37: // left arrow
					ship.rotateLeft();
					break;
				case 38: // up arrow
					ship.faster();
					break;
				case 39: // right arrow
					ship.rotateRight();
					break;
				case 40: // down arrow
					ship.slower();
					break;
				case 27: // escape
					die();
					panel.exit();
					break;
				case ' ':
					ship.fire(GravityGame.this);
					break;
				default:
					System.out.println("Unknown key: " + e);
					break;
				}

			}
		});
	}

	private void createMines() {
		mines = new ArrayList<Mine>(10);
		Dimension size = new Dimension(10, 10);
		for (int i = 0; i < 10; i++) {
			Point point = getRandomPointWithin(size);
			mines.add(new Mine(new Rectangle(point.x, point.y, size.width,
					size.height)));
		}
	}

	private Point getRandomPointWithin(Dimension within) {
		Rectangle bounds = bounds();
		int x = random.nextInt(bounds.width - within.width);
		int y = random.nextInt(bounds.height - within.height);
		return new Point(x, y);
	}

	private void entitiesDo(Consumer<Entity> action) {
		mines.stream().forEach(action);
		action.accept(laser);
		action.accept(ship);
	}
}
