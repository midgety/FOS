package fps.blocks;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import fps.main.Camera;
import fps.main.Component;
import fps.player.Projectile;

public class Block {
	
	private final float l, w, h;
	private int col;
	
	private float x, y, z, opacity;
	private boolean wall, activated;
	private boolean left, right, front, back;
	private CollisionBox collision;
	
	public static Block blockVoid = new BlockVoid();
	
	public Block(float l, float w, float h, int col) {
		this.l = l;
		this.w = w;
		this.h = h;
		this.col = col;
	}
	
	public void render(float x, float y, float z) {
		collision = new CollisionBox(x, y, z, w, h, l);
		if(x <= -Camera.getX()+25 && x >= -Camera.getX()-25 && z <= -Camera.getZ()+25 && z >= -Camera.getZ()-25) {
			glPushMatrix();
			glTranslatef(x, y, z);
			float r = col >> 16 & 0xFF;
			float g = col >> 8 & 0xFF;
			float b = col & 0xFF;
			if(wall) {
				glColor3f(r*0.0006f, g*0.0006f, b*0.0006f);
			}else
			{
				glColor3f(0f, 0f, 0f);
				glColor4f(0.7f, 0.7f, 0.7f, opacity/255);
			}
			
			glBegin(GL_QUADS); {
				glVertex3f(0, h, 0);
				glVertex3f(w, h, 0);
				glVertex3f(w, h, l);
				glVertex3f(0, h, l);
				
				if(!front && wall) {
					glVertex3f(0, 0, 0);
					glVertex3f(w, 0, 0);
					glVertex3f(w, h, 0);
					glVertex3f(0, h, 0);
				}
				if(!back && wall) {
					glVertex3f(0, 0, l);
					glVertex3f(w, 0, l);
					glVertex3f(w, h, l);
					glVertex3f(0, h, l);
				}
				if(!left && wall) {
					glVertex3f(0, 0, 0);
					glVertex3f(0, h, 0);
					glVertex3f(0, h, l);
					glVertex3f(0, 0, l);
				}
				if(!right && wall) {
					glVertex3f(w, 0, 0);
					glVertex3f(w, h, 0);
					glVertex3f(w, h, l);
					glVertex3f(w, 0, l);
				}
			}
			glEnd();
			
			glColor3f(r/255, g/255, b/255);
			glLineWidth(2.5f);
			glBegin(GL_LINES); {
				if(!front && !left) {
					if(wall) {
						glVertex3f(-0.006f, 0, -0.006f);
						glVertex3f(-0.006f, h, -0.006f);
					}
				}
				if(!back && !left) {
					if(wall) {
						glVertex3f(-0.006f, 0, 0.006f+l);
						glVertex3f(-0.006f, h, 0.006f+l);
					}
				}
				if(!front && !right) {
					if(wall) {
						glVertex3f(0.006f+w, 0, -0.006f);
						glVertex3f(0.006f+w, h, -0.006f);
					}
				}
				if(!back && !right) {
					if(wall) {
						glVertex3f(0.006f+w, 0, 0.006f+l);
						glVertex3f(0.006f+w, h, 0.006f+l);
					}
				}
				glVertex3f(-0.006f, h+0.006f, 0);
				glVertex3f(-0.006f, h+0.006f, l);
				
				glVertex3f(0.006f+w, h+0.006f, 0);
				glVertex3f(0.006f+w, h+0.006f, l);
				
				glVertex3f(0, h+0.006f, -0.006f);
				glVertex3f(w, h+0.006f, -0.006f);
				
				glVertex3f(0, h+0.006f, l+0.006f);
				glVertex3f(w, h+0.006f, l+0.006f);
				
				glVertex3f(-0.006f, 0.006f, 0);
				glVertex3f(-0.006f, 0.006f, l);
				
				glVertex3f(0.006f+w, 0.006f, 0);
				glVertex3f(0.006f+w, 0.006f, l);
				
				glVertex3f(0, 0, -0.006f);
				glVertex3f(w, 0, -0.006f);
				
				glVertex3f(0, 0, l+0.006f);
				glVertex3f(w, 0, l+0.006f);
			}
			glEnd();
			glPopMatrix();
		}
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void update() {
		if(activated) {
			if(opacity < 255) opacity += Component.getDelta()*0.8f; 
		}else
		{
			if(opacity > 0) opacity -= Component.getDelta()*0.4f;
		}
	}
	
	public void updateCollision(float pX, float pY, float pZ, float pW, float pH, float pL) {
		if(collision == null) collision = new CollisionBox(x, y, z, w, h, l);
		collision.updateCollision(pX, pY, pZ, pW, pH, pL);
		
		for(Projectile p : Projectile.lazers) {
			collision.updateProjectileCollision(p.getX()+p.getVelX(), p.getY()+p.getVelY(), p.getZ()+p.getVelZ(), 0.02f, 0.2f, pL, p);
		}
	}
	
	public boolean isWall() {
		return wall;
	}

	public void setWall() {
		this.wall = true;
	}
	
	public void setLeft() {
		left = true;
	}
	public void setRight() {
		right = true;
	}
	public void setFront() {
		front = true;
	}
	public void setBack() {
		back = true;
	}
	
	public void activate() {
		activated = true;
	}
	
	public void deActivate() {
		activated = false;
	}

	public float getL() {
		return l;
	}

	public float getW() {
		return w;
	}

	public float getH() {
		return h;
	}
}
