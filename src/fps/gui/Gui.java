package fps.gui;

import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.lwjgl.opengl.Display;

public class Gui {
	
	public static Gui currentScreen;
	
	public void render(){}
	public void update(){}
	public void init(){}
	
	public static void setGui(Gui gui) {
		currentScreen = gui;
		gui.init();
	}
	
	public void init2D() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), Display.getHeight(), 0, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glDisable(GL_DEPTH_TEST);
		glEnable(GL_ALPHA_TEST);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public void drawRect(float x, float y, float w, float h, int col) {
		glPushMatrix();
		getCol(col);
		glTranslatef(x, y, 0);
		glBegin(GL_QUADS); {
			glVertex2f(0, 0);
			glVertex2f(w, 0);
			glVertex2f(w, h);
			glVertex2f(0, h);
		}
		glEnd();
		glPopMatrix();
	}
	
	public void drawRect(float x, float y, float w, float h, int col, int col2) {
		glPushMatrix();
		glTranslatef(x, y, 0);
		glBegin(GL_QUADS); {
			getCol(col);
			glVertex2f(0, 0);
			glVertex2f(w, 0);
			getCol(col2);
			glVertex2f(w, h);
			glVertex2f(0, h);
		}
		glEnd();
		glPopMatrix();
	}
	
	public void getCol(int col) {
		float r = col >> 16 & 0xFF;
		float g = col >> 8 & 0xFF;
		float b = col & 0xFF;
		float a = col >> 24 & 0xFF;
		glColor4f((float)(r/255), (float)(g/255), (float)(b/255), (float)(a/255));
	}
}
