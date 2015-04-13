package net.blabux.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public interface WithDefaultsKeyListener extends KeyListener {
	@Override
	default void keyTyped(KeyEvent e) {
	}

	@Override
	default void keyPressed(KeyEvent e) {
	}

	@Override
	default void keyReleased(KeyEvent e) {
	}
}
