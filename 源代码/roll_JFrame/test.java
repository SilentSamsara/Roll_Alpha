package roll_JFrame;


public class test {
	
	public static void main(String[] argv) {
		Audiotest at=new Audiotest("music/begin.wav");
		at.start();
		try {
			Thread.sleep(15000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		at.stopMusic();
		System.out.println(at.stopSignal);
	}
}
