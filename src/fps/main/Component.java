package fps.main;

import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.newdawn.slick.Image;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Component {
	
	private static long lastDelta, delta, lastTick, frames;
	private static int fps;
	
	private static HashMap<String, Image> images = new HashMap<String, Image>();
	private static HashMap<String, BufferedImage> buffImages = new HashMap<String, BufferedImage>();
	private static HashMap<String, Texture> textures = new HashMap<String, Texture>();
	
	public static Image getImage(String s) {
		if(!images.containsKey(s)) loadImage(s);
		return images.get(s);
	}
	
	private static void loadImage(String s) {
		try {
			Image img = new Image(s);
			images.put(s, img);
			System.out.println("Image located at ["+s+"] has been loaded!");
		}catch(Exception e){
			try {
				Image img = new Image("res/missing.png");
				images.put(s, img);
				System.err.println("Image located at ["+s+"] could not be loaded!");
			}catch(Exception e2){}
		}
	}
	
	public static BufferedImage getBuffImage(String s) {
		if(!buffImages.containsKey(s)) loadBuffImage(s);
		return buffImages.get(s);
	}
	
	private static void loadBuffImage(String s) {
		try {
			BufferedImage img = ImageIO.read(ResourceLoader.getResourceAsStream(s));
			buffImages.put(s, img);
			System.out.println("BufferedImage located at ["+s+"] has been loaded!");
		}catch(Exception e){
			try {
				BufferedImage img = ImageIO.read(ResourceLoader.getResourceAsStream("res/missing.png"));
				buffImages.put(s, img);
				System.err.println("BufferedImage located at ["+s+"] could not be loaded!");
			}catch(Exception e2){}
		}
	}
	
	public static Texture getTexture(String s) {
		if(!textures.containsKey(s)) loadTexture(s);
		return textures.get(s);
	}
	
	private static void loadTexture(String s) {
		try {
			Texture tex = TextureLoader.getTexture(".png", ResourceLoader.getResourceAsStream(s));
			textures.put(s, tex);
			System.out.println("Texture located at ["+s+"] has been loaded!");
		}catch(Exception e){
			try {
				Texture tex = TextureLoader.getTexture(".png", ResourceLoader.getResourceAsStream("res/missing.png"));
				textures.put(s, tex);
				System.err.println("Texture located at ["+s+"] could not be loaded!");
			}catch(Exception e2){}
		}
	}
	
	public static void updateDelta() {
		delta = (System.currentTimeMillis() - lastDelta);
		lastDelta = System.currentTimeMillis();
	}
	
	public static float getDelta() {
		return (float)delta / 1000f;
	}
	
	public static int getFps() {
		if(lastTick == 0) lastTick = System.nanoTime();
		frames++;
		if(System.nanoTime() - lastTick > 1000000000L) {
			fps = (int)frames;
			
			frames = 0;
			lastTick = System.nanoTime();
		}
		
		return fps;
	}
	
	public static FloatBuffer getFloatBuffer(float[] args) {
		FloatBuffer fb = BufferUtils.createFloatBuffer(args.length);
		fb.put(args);
		fb.flip();
		return fb;
	}
}
