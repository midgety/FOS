package fps.sound;

public class Sound {
	
	private org.newdawn.slick.Sound sound;
	private static org.newdawn.slick.Sound[] sounds = new org.newdawn.slick.Sound[256];
	private int id;
	
	public Sound(String s) {
		try {
			sound = new org.newdawn.slick.Sound(s);
			for(int i = 0; i < sounds.length; i++) {
				if(sounds[i] == null) {
					sounds[i] = this.sound;
					i = id;
				}
			}
		}catch(Exception e){}
	}
	
	public void play(float vol) {
		sounds[id].play(1, vol);
	}
	
	public void playAt(float x, float y, float z, float vol) {
		sounds[id].playAt(1, vol, x, y, z);
	}
	
	public boolean playing() {
		return sounds[id].playing();
	}
}
