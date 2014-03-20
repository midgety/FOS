package fps.main;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glViewport;

import java.io.InputStream;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.PNGDecoder;
import org.newdawn.slick.util.ResourceLoader;

import fps.gui.Gui;
import fps.gui.GuiIngame;

public class Main {

	public static final String TITLE = "FOS - Alpha 1.0";
	
	public Main() {
		initDisplay();
		init();
		update();
	}

	private void initDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(1280, 720));
			Display.setTitle(TITLE);
			Display.setIcon(new ByteBuffer[] {
					loadIcon("res/icons/128x.png"),
					loadIcon("res/icons/64x.png"),
					loadIcon("res/icons/32x.png"),
					loadIcon("res/icons/16x.png")
			});
			Display.setResizable(true);
			Display.create();
		} catch (Exception e) {}
	}
	
	private static ByteBuffer loadIcon(String s) {
		try {
			InputStream fis = ResourceLoader.getResourceAsStream(s);
			try {
				PNGDecoder decoder = new PNGDecoder(fis);
				ByteBuffer bb = ByteBuffer.allocateDirect(decoder.getWidth()*decoder.getHeight()*4);
				decoder.decode(bb, decoder.getWidth()*4, PNGDecoder.Format.RGBA);
				bb.flip();
				return bb;
			} finally {
				fis.close();
			}
		}catch(Exception e){}
		return null;
	}

	private void update() {
		while (!Display.isCloseRequested()) {
			glViewport(0, 0, Display.getWidth(), Display.getHeight());
			Display.setTitle(TITLE+" - FPS: "+Component.getFps());
			Component.updateDelta();
			Gui.currentScreen.update();
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glLoadIdentity();
			Gui.currentScreen.render();
			Display.update();
		}
	}

	private void init() {
		Component.updateDelta();
		Gui.setGui(new GuiIngame());
		Gui.currentScreen.init();
	}

	public static void main(String[] args) {
		new Main();
	}
}
