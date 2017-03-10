package com.runescape;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Toolkit;

public final class GameFrame extends Frame {

	private final GameApplet applet;
	public Toolkit toolkit = Toolkit.getDefaultToolkit();
	public Dimension screenSize = toolkit.getScreenSize();
	public int screenWidth = (int) screenSize.getWidth();
	public int screenHeight = (int) screenSize.getHeight();
	protected final Insets insets;
	private static final long serialVersionUID = 1L;

	public GameFrame(GameApplet applet, int width, int height, boolean resizable, boolean fullscreen) {
		this.applet = applet;
		setTitle(Configuration.CLIENT_NAME + " - Game Launcher");
		setResizable(resizable);
		setUndecorated(fullscreen);
		setVisible(true);
		insets = getInsets();
		if (resizable) {
			setMinimumSize(new Dimension(766 + insets.left + insets.right, 536 + insets.top + insets.bottom));
		}
		setSize(width + insets.left + insets.right, height + insets.top + insets.bottom);
		setLocationRelativeTo(null);
		setBackground(Color.BLACK);
		requestFocus();
		toFront();
	}

	@Override
	public Graphics getGraphics() {
		final Graphics graphics = super.getGraphics();
		Insets insets = this.getInsets();
		graphics.fillRect(0, 0, getWidth(), getHeight());
		graphics.translate(insets != null ? insets.left : 0, insets != null ? insets.top : 0);
		return graphics;
	}

	public int getFrameWidth() {
		Insets insets = this.getInsets();
		return getWidth() - (insets.left + insets.right);
	}

	public int getFrameHeight() {
		Insets insets = this.getInsets();
		return getHeight() - (insets.top + insets.bottom);
	}

	@Override
	public void update(Graphics graphics) {
		applet.update(graphics);
	}

	@Override
	public void paint(Graphics graphics) {
		applet.paint(graphics);
	}
}