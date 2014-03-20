package fps.maps;

import java.awt.image.BufferedImage;
import java.util.Random;

import fps.blocks.Block;
import fps.blocks.BlockBlueFloor;
import fps.blocks.BlockBlueWall;
import fps.blocks.BlockVoid;
import fps.gui.GuiIngame;
import fps.main.Camera;
import fps.main.Component;
import fps.player.Player;
import fps.player.Projectile;

public class Map {
	
	public static Map currentMap;
	
	private String src;
	private BufferedImage map;
	private Block[][] blocks;
	
	public Map(String src) {
		this.src = src;
	}
	
	public static void setMap(Map map) {
		currentMap = map;
		map.initMap();
	}
	
	public void renderMap() {
		for(int x = 0; x < map.getWidth(); x++) {
			for(int y = 0; y < map.getHeight(); y++) {
				if(blocks[x][y] != null) blocks[x][y].render(x, 0, y);
			}
		}
		
		for(Projectile p : Projectile.lazers) {
			p.render();
		}
	}
	
	public void updateMap() {
		for(int x = 0; x < map.getWidth(); x++) {
			for(int y = 0; y < map.getHeight(); y++) {
				if(blocks[x][y] != null) blocks[x][y].updateCollision(-Camera.getX(), -Camera.getY(), -Camera.getZ(), 0.5f, 0.5f, 2);
				if(blocks[x][y] != null) blocks[x][y].update();
				if((int)-Camera.getX() != x || (int)-Camera.getZ() != y || -Camera.getY() != blocks[(int)-Camera.getX()][(int)-Camera.getZ()].getH()+1.5f && blocks[x][y] != null) blocks[x][y].deActivate(); 
			}
		}
		if(getTile((int)-Camera.getX(), (int)-Camera.getZ()) != null && getTile((int)-Camera.getX(), (int)-Camera.getZ()) != Block.blockVoid && -Camera.getY() == getTile((int)-Camera.getX(), (int)-Camera.getZ()).getH()+1.5f) blocks[(int)-Camera.getX()][(int)-Camera.getZ()].activate();
		
		for(Projectile p : Projectile.lazers) {
			p.update();
		}
		
		for(Projectile p : Projectile.removedLazers) {
			if(Projectile.lazers.contains(p)) Projectile.lazers.remove(p);
		}
	}
	
	private void initMap() {
		map = Component.getBuffImage("res/maps/"+src);
		blocks = new Block[map.getWidth()][map.getHeight()];
		
		for(int x = 0; x < map.getWidth(); x++) {
			for(int y = 0; y < map.getHeight(); y++) {
				if(map.getRGB(x, y) == 0xFF0000ff) blocks[x][y] = new BlockBlueWall();
				if(map.getRGB(x, y) == 0xFF00ffff) blocks[x][y] = new BlockBlueFloor();
				if(map.getRGB(x, y) == 0xFF000000) blocks[x][y] = Block.blockVoid;
			}
		}
		for(int x = 0; x < map.getWidth(); x++) {
			for(int y = 0; y < map.getHeight(); y++) {
				if(getTile(x+1, y).isWall()) getTile(x, y).setRight();
				if(getTile(x-1, y).isWall()) getTile(x, y).setLeft();
				if(getTile(x, y+1).isWall()) getTile(x, y).setBack();
				if(getTile(x, y-1).isWall()) getTile(x, y).setFront();
			}
		}
		
		int i = new Random().nextInt(map.getWidth());
		int j = new Random().nextInt(map.getHeight());
		if(getTile(i, j).isWall() || getTile(i, j) == Block.blockVoid) {
			i = new Random().nextInt(map.getWidth());
			j = new Random().nextInt(map.getHeight());
		}else
		{
			Player.collidingDown = true;
			Camera.setX(-i);
			Camera.setZ(-j);
			Camera.setY(-1.7f);
		}
	}
	
	public void respawn() {
		int i = new Random().nextInt(map.getWidth());
		int j = new Random().nextInt(map.getHeight());
		if(getTile(i, j).isWall() || getTile(i, j) == Block.blockVoid) {
			i = new Random().nextInt(map.getWidth());
			j = new Random().nextInt(map.getHeight());
		}else
		{
			Player.collidingDown = true;
			Camera.setX(-i);
			Camera.setZ(-j);
			Camera.setY(-1.7f);
		}
	}
	
	public Block getTile(int x, int y) {
		try {
			return blocks[x][y];
		}catch(Exception e){return Block.blockVoid;}
	}
}
