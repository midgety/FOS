package fps.gui;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Image;

import fps.main.Camera;
import fps.main.Component;
import fps.maps.Map;
import fps.player.Player;

public class GuiIngame extends Gui {
	
	private Player p;
	private Image pointer;
	
	public void render() {
		p.showView();
		if(Map.currentMap != null) Map.currentMap.renderMap();
		renderOnScreen();
	}
	
	private void renderOnScreen() {
		init2D();
		pointer.draw(Display.getWidth()/2-pointer.getWidth()/2, Display.getHeight()/2-pointer.getHeight()/2);
	}
	
	public void update() {
		p.update();
		Camera.setAspect((float)Display.getWidth() / (float)Display.getHeight());
		if(Map.currentMap != null) Map.currentMap.updateMap();
	}
	
	public void init() {
		p = new Player();
		p.init();
		pointer = Component.getImage("res/gui/pointer.png");
		Map.setMap(new Map("map1.png"));
	}
}
