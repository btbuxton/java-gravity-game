package net.blabux;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = -974519052373581194L;

	private final float fps;
	private final Entity rootEntity;

	public GamePanel(float fps, Entity rootEntity) {
		this.fps = fps;
		this.rootEntity = rootEntity;
	}

	public void show() {
		JFrame frame = new JFrame("Gravity");
		frame.add(this);
		frame.setSize(640, 480);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loop();
	}

	private void loop() {
		rootEntity.init();
		long wait = Math.round(1000 / fps);
		boolean stop = false;
		while (!stop) {
			long timeToRun = timeToRun(() -> {
				rootEntity.update();
				repaint();
			});
			long delay = wait - timeToRun;
			if (delay > 0) {
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					stop = true;
				}
			}
		}
	}

	private long timeToRun(Runnable run) {
		long begin = System.currentTimeMillis();
		run.run();
		return System.currentTimeMillis() - begin;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		rootEntity.draw(g2d);
	}

}
