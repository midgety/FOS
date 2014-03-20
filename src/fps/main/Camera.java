package fps.main;

import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

public class Camera {
	
	private static float fov, aspect, near, far;
	private static float x, y, z;
	private static float pitch, yaw;
	
	public Camera() {
		this.fov = 70;
		this.aspect = (float)Display.getWidth() / (float)Display.getHeight();
		this.near = 0.03f;
		this.far = 1000.0f;
		initGL();
	}
	
	public void initGL() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(fov, aspect, near, far);
		glMatrixMode(GL_MODELVIEW);
		glDisable(GL_TEXTURE_2D);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_ALPHA_TEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glEnable(GL_FOG);
		glFog(GL_FOG_DENSITY, Component.getFloatBuffer(new float[]{0.08f, 1, 1, 1}));
	}
	
	public void updateView() {
		glRotatef(pitch, 1, 0, 0);
		glRotatef(yaw, 0, 1, 0);
		glTranslatef(x, y, z);
	}
	
	public static void move(int dir, float amnt) {
		z += amnt * Math.sin(Math.toRadians(yaw + 90 * dir));
		x += amnt * Math.cos(Math.toRadians(yaw + 90 * dir));
	}
	
	public static void rotateYaw(float amnt) {
		yaw += amnt;
	}
	
	public static void rotatePitch(float amnt) {
		pitch += amnt;
		if(pitch > 90) pitch = 90;
		if(pitch < -90) pitch = -90;
	}

	public static void hover(float amnt) {
		y += amnt;
	}

	public static float getFov() {
		return fov;
	}

	public static void setFov(float fov) {
		Camera.fov = fov;
	}

	public static float getAspect() {
		return aspect;
	}

	public static void setAspect(float aspect) {
		Camera.aspect = aspect;
	}

	public static float getX() {
		return x;
	}

	public static void setX(float x) {
		Camera.x = x;
	}

	public static float getY() {
		return y;
	}

	public static void setY(float y) {
		Camera.y = y;
	}

	public static float getZ() {
		return z;
	}

	public static void setZ(float z) {
		Camera.z = z;
	}

	public static float getPitch() {
		return pitch;
	}

	public static void setPitch(float pitch) {
		Camera.pitch = pitch;
	}

	public static float getYaw() {
		return yaw;
	}

	public static void setYaw(float yaw) {
		Camera.yaw = yaw;
	}

	public void updateJump(float vel) {
		hover(vel);
	}
}
