package fps.blocks;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import fps.main.Camera;
import fps.player.Player;
import fps.player.Projectile;

public class CollisionBox {
	
	private float x, y, z;
	private float w, h, l;
	
	public CollisionBox(float x, float y, float z, float w, float h, float l) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		this.h = h;
		this.l = l;
	}
	
	public void drawCollisionBox() {
		glPushMatrix();
		glTranslatef(x, y, z);
		glScalef(1.1f, 1.1f, 1.1f);
		glTranslatef(-0.05f, 0.05f, -0.05f);
		glColor4f(1, 0, 0, 0.5f);
		glBegin(GL_QUADS); {
			glVertex3f(0, h, 0);
			glVertex3f(w, h, 0);
			glVertex3f(w, h, l);
			glVertex3f(0, h, l);
			glVertex3f(0, 0, 0);
			glVertex3f(w, 0, 0);
			glVertex3f(w, 0, l);
			glVertex3f(0, 0, l);
			glVertex3f(0, 0, 0);
			glVertex3f(w, 0, 0);
			glVertex3f(w, h, 0);
			glVertex3f(0, h, 0);
			glVertex3f(0, 0, l);
			glVertex3f(w, 0, l);
			glVertex3f(w, h, l);
			glVertex3f(0, h, l);
			glVertex3f(0, 0, 0);
			glVertex3f(0, h, 0);
			glVertex3f(0, h, l);
			glVertex3f(0, 0, l);
			glVertex3f(w, 0, 0);
			glVertex3f(w, h, 0);
			glVertex3f(w, h, l);
			glVertex3f(w, 0, l);
		}
		glEnd();
		glPopMatrix();
	}
	
	public void updateCollision(float pX, float pY, float pZ, float pW, float pH, float pL) {
		if(pX+0.3f > x && pX+0.2f < x+0.4f && pZ > z && pZ < z+l+0.2f && pY > y && pY < y+h) {
			Camera.setX(-x+0.2f);
		}
		if(pX+0.2f > x+w && pX+0.2f < x+w+0.4f && pZ > z && pZ < z+l+0.2f && pY > y && pY < y+h) {
			Camera.setX(-x-w-0.2f);
		}
		
		if(pZ+0.3f > z && pZ+0.2f < z+0.4f && pX > x && pX < x+w+0.2f && pY > y && pY < y+h) {
			Camera.setZ(-z+0.2f);
		}
		if(pZ+0.2f > z+l && pZ+0.2f < z+l+0.4f && pX > x && pX < x+w+0.2f && pY > y && pY < y+h) {
			Camera.setZ(-z-l-0.2f);
		}
		
		if(pY+0.2f > y+h && pY < y+h+1.5f && pZ > z && pZ < z+l+0.3f && pX > x && pX < x+w+0.3f) {
			Player.collidingDown = true;
			Player.fallVelocity = 0;
			Camera.setY(-y-h-1.5f);
		}else
		{
			Player.collidingDown = false;
		}
	}
	
	public void updateProjectileCollision(float pX, float pY, float pZ, float pW, float pH, float pL, Projectile p) {
		if(pX+0.3f > x && pX+0.2f < x+0.4f && pZ > z && pZ < z+l+0.2f && pY > y && pY < y+h) {
			Projectile.removedLazers.add(p);
		}
		if(pX+0.2f > x+w && pX+0.2f < x+w+0.4f && pZ > z && pZ < z+l+0.2f && pY > y && pY < y+h) {
			Projectile.removedLazers.add(p);
		}
		
		if(pZ+0.3f > z && pZ+0.2f < z+0.4f && pX > x && pX < x+w+0.2f && pY > y && pY < y+h) {
			Projectile.removedLazers.add(p);
		}
		if(pZ+0.2f > z+l && pZ+0.2f < z+l+0.4f && pX > x && pX < x+w+0.2f && pY > y && pY < y+h) {
			Projectile.removedLazers.add(p);
		}
		if(pY+0.2f > y && pY < y+h && pZ > z && pZ < z+l+0.3f && pX > x && pX < x+w+0.3f) {
			Projectile.removedLazers.add(p);
		}
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	}

	public float getH() {
		return h;
	}

	public void setH(float h) {
		this.h = h;
	}

	public float getL() {
		return l;
	}

	public void setL(float l) {
		this.l = l;
	}
}
