package roll_JFrame;

public class Student {
	private String name;
	private int time;
	public Student(String na,int ti) {
		// TODO Auto-generated constructor stub
		this.name=na;this.time=ti;
	}
	public String getName() {
		return name;
	}
	public int getTime() {
		return time;
	}
	public void hitted() {
		time++;
	}
}
