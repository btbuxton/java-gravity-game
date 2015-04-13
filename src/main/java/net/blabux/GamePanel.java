package net.blabux;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.blabux.util.FrameTimer;
import net.blabux.util.FrameTimer.Stopper;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = -974519052373581194L;

	private final float fps;
	private final Entity rootEntity;
	private volatile Stopper timerStop;
	private volatile JFrame frame;

	public GamePanel(float fps, Entity rootEntity) {
		this.fps = fps;
		this.rootEntity = rootEntity;
	}

	public void show() {
		frame = new JFrame("Gravity");
		frame.add(this);
		frame.setSize(640, 480);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startLoop();
	}

	private void startLoop() {
		rootEntity.init();
		FrameTimer timer = new FrameTimer(fps,() -> {
			Toolkit.getDefaultToolkit().sync();
			rootEntity.update();
			repaint();
		});
		timerStop = timer.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		rootEntity.draw(g2d);
	}

	public void restart() {
		Stopper stop = timerStop;
		if (null != stop) stop.stop();
		startLoop();
	}

	public void exit() {
		JFrame root = frame;
		if (null != root) {
			root.dispose();
		}
		
	}

}
