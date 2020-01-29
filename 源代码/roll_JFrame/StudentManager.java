package roll_JFrame;

import java.util.ArrayList;

import javax.swing.JLabel;

public class StudentManager {
	private ArrayList<Student> list =new ArrayList<Student>();
	public static boolean runSignal=false;
	private int stopTime=20000;
	private int maxTime=20000,minTime=15000;
	public void setStopTime(int time) {stopTime=time*1000;}
	public void setMaxTime(int time) {maxTime=time*1000;}
	public StudentManager(JLabel tf) {
		rollrun(tf);
	}
	public void addStudent(String name,int time) {
		Student someone=new Student(name, time);
		list.add(someone);
	}
	public int getNum() {return list.size();}
	public Student getStudent(int num) {return list.get(num);}
	public void rollrun(JLabel tf) {
		System.out.println(3);
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					System.out.println(runSignal);
					if (StudentManager.runSignal) {
						int time=(int)(Math.random()*(maxTime-minTime)+minTime);
						int i;
						//************************
						Audiotest at=new Audiotest("music/"+(((int)(Math.random()*(100-1)+1))%2)+".wav");
						at.start();
						//************************
						for(i=(int)(Math.random()*(list.size()-1-0)+0);time>0;i++) {
							if(!runSignal)
								break;
							int get=i%(list.size());
							tf.setText(list.get(get).getName());
							try {
								Thread.sleep(50);
							} catch (InterruptedException e) {e.printStackTrace();}
//							time=time-70;
						}
						list.get(i%list.size()).hitted();
						runSignal=false;
						at.stopMusic();
					}
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {e.printStackTrace();}
					
				}
			}
		}).start();
		System.out.println(5);
	}
}
