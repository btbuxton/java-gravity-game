package net.blabux;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

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
		step(wait).actionPerformed(null);
	}

	private ActionListener step(final long wait) {
		final long lastRun = System.currentTimeMillis();
		return (e) -> {
			Toolkit.getDefaultToolkit().sync();
			rootEntity.update();
			repaint();
			long now = System.currentTimeMillis();
			long delay = wait - now + lastRun;
			if (delay < 0) {
				delay = 0;
			}
			Timer next = new Timer((int) delay, step(wait));
			next.setRepeats(false);
			next.start();
		};
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		rootEntity.draw(g2d);
	}

}
