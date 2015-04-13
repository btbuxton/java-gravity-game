package net.blabux.util;

import java.awt.event.ActionListener;

import javax.swing.Timer;

public class FrameTimer {
	private final float fps;
	private final Runnable runnable;
	private boolean stop;
	
	public FrameTimer(float fps, Runnable runnable) {
		this.fps = fps;
		this.runnable = runnable;
		this.stop = false;
	}
	public Stopper start() {
		Stopper stopper = () -> stop = true;
		long wait = Math.round(1000 / fps);
		step(wait, stopper).actionPerformed(null);
		return stopper;
	}

	private ActionListener step(long wait, Stopper stopper) {
		final long lastRun = System.currentTimeMillis();
		return (ignore) -> {
			if (stop) {
				return;
			}
			runnable.run();
			long now = System.currentTimeMillis();
			long delay = wait - now + lastRun;
			if (delay < 0) {
				delay = 0;
			}
			Timer next = new Timer((int) delay, step(wait, stopper));
			next.setRepeats(false);
			next.start();
		};
	}
	
	public static interface Stopper {
		void stop();
	}
}
