package fps.player;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import fps.blocks.Block;
import fps.main.Camera;
import fps.main.Component;
import fps.maps.Map;

public class Player {
	
	private static String name;
	
	private Camera cam;
	public static boolean collidingDown;
	public static float fallVelocity;
	
	private float jumpVelocity;
	private boolean jumping, canJump;
	
	public void showView() {
		cam.initGL();
		cam.updateView();
	}
	
	public void update() {
		while(Mouse.next() && Mouse.getEventButtonState()) {
			if(Mouse.isButtonDown(0)) new Projectile().spawn(this, -Camera.getX()+0.01f, -Camera.getY()-0.01f, -Camera.getZ(), Camera.getPitch(), -Camera.getYaw());
		}
		
		if(Camera.getY() == -1.5f) canJump = true;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) Camera.move(1, Component.getDelta()*0.3f);
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) Camera.move(0, Component.getDelta()*0.3f);
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) Camera.move(1, -Component.getDelta()*0.3f);
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) Camera.move(0, -Component.getDelta()*0.3f);
		if(jumping) cam.updateJump(-jumpVelocity);
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && canJump) {
			if(!jumping) jumpVelocity = 0.13f;
			canJump = false;
			jumping = true;
		}
		
		if(!collidingDown || Map.currentMap.getTile((int)-Camera.getX(), (int)-Camera.getZ()) == Block.blockVoid) {
			if(!jumping) fallVelocity += Component.getDelta()*0.0005f;
			Camera.hover(fallVelocity);
		}
		
		while(Keyboard.next()) {
			if(Keyboard.isKeyDown(Keyboard.KEY_TAB)) Mouse.setGrabbed(!Mouse.isGrabbed());
			if(Keyboard.isKeyDown(Keyboard.KEY_R)) {
				fallVelocity = 0;
				Camera.setY(-2f);
			}
		}
		
		if(Mouse.isGrabbed()) {
			Camera.rotatePitch((float)-Mouse.getDY()/2);
			Camera.rotateYaw((float)Mouse.getDX()/2);
			Mouse.setCursorPosition(Display.getWidth()/2, Display.getHeight()/2);
		}
		
		if(jumpVelocity > 0) {
			jumpVelocity -= Component.getDelta()*0.0005f;
		}else
		{
			jumping = false;
		}
		
		if(cam.getY() > 50) Map.currentMap.respawn();
	}
	
	public void init() {
		cam = new Camera();
	}
}
