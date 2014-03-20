package fps.player;

import java.util.ArrayList;
import java.util.List;

import fps.main.Component;
import fps.maps.Map;
import static org.lwjgl.opengl.GL11.*;

public class Projectile {
	
	public static List<Projectile> lazers = new ArrayList<Projectile>();
	public static List<Projectile> removedLazers = new ArrayList<Projectile>();
	
	private float x, y, z;
	private float pitch, yaw;
	private double dir;
	private float velX, velY, velZ;
	private Player p;
	
	public void spawn(Player p, float x, float y, float z, float pitch, float yaw) {
		this.p = p;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		lazers.add(this);
	}
	
	public void render() {
		glPushMatrix();
		glTranslatef(x+velX, y+velY, z+velZ);
		glRotated(yaw+180, 0, 1, 0);
		glRotated(pitch, 1, 0, 0);
		glColor3f(0, 0.5f, 0);
		glBegin(GL_QUADS); {
			glVertex3f(0, 0.02f, 0);
			glVertex3f(0.02f, 0.02f, 0);
			glVertex3f(0.02f, 0.02f, 0.2f);
			glVertex3f(0, 0.02f, 0.2f);
			glVertex3f(0, 0, 0);
			glVertex3f(0.02f, 0, 0);
			glVertex3f(0.02f, 0, 0.2f);
			glVertex3f(0, 0, 0.2f);
			glVertex3f(0, 0, 0);
			glVertex3f(0.02f, 0, 0);
			glVertex3f(0.02f, 0.02f, 0);
			glVertex3f(0, 0.02f, 0);
			glVertex3f(0, 0, 0.2f);
			glVertex3f(0.02f, 0, 0.2f);
			glVertex3f(0.02f, 0.02f, 0.2f);
			glVertex3f(0, 0.02f, 0.2f);
			glVertex3f(0, 0, 0);
			glVertex3f(0, 0.02f, 0);
			glVertex3f(0, 0.02f, 0.2f);
			glVertex3f(0, 0, 0.2f);
			glVertex3f(0.02f, 0, 0);
			glVertex3f(0.02f, 0.02f, 0);
			glVertex3f(0.02f, 0.02f, 0.2f);
			glVertex3f(0.02f, 0, 0.2f);
		}
		glEnd();
		glPopMatrix();
	}
	
	public void update() {
		double X = -Math.sin(yaw / 180f * Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI);
		double Y =  Math.sin((pitch) / 180f * Math.PI);
		double Z = Math.cos((yaw) / 180f * Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI);
		velX += (X)*Component.getDelta()*0.03;
		velY += (-Y)*Component.getDelta()*0.03;
		velZ += (-Z)*Component.getDelta()*0.03;
		
		if(velX > x+100 || velX < x-100 || velZ > z+100 || velZ < z-100 || velY > y+100 || velY < y-100) removedLazers.add(this);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public float getVelX() {
		return velX;
	}

	public float getVelY() {
		return velY;
	}

	public float getVelZ() {
		return velZ;
	}
}
