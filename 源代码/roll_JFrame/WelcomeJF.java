package roll_JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.FrameBorderStyle;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class WelcomeJF {
	private JFrame jf;
	private JPanel jp=new JPanel();
	private JLabel jl=new JLabel(new ImageIcon("Images/Roll.jpg"));//553*452
	private JLabel jl2=new JLabel();
	private JButton jb1=new JButton("开始");
	private JButton jb2=new JButton("停止");
	private StudentManager sm;
	public WelcomeJF() {
		// TODO 自动生成的构造函数存根
		jf=new JFrame("没人举手？点个名吧！");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setIconImage((new ImageIcon("Images/evil.png")).getImage());
		jf.setSize(700,600);
		jf.setLayout(new BorderLayout());
		jf.setLocationRelativeTo(null);
		jf.setBackground(Color.BLACK);
		jf.setResizable(false);
		sm=new StudentManager(jl2);
		jp.setLayout(new BorderLayout());
		jp.setLocation(0,0);
		jb1.setBounds(270,500,152,40);
		jb2.setBounds(270,500,152,40);
		jl2.setBounds(196,250,300,75);
		jb2.setEnabled(false);
		jb1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StudentManager.runSignal=true;
				jl.add(jb2);
				jl.remove(jb1);
			}
		});
		
		jb2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StudentManager.runSignal=false;
				Audiotest at=new Audiotest("music/end.wav");
				at.start();
				jl.add(jb1);
				jl.remove(jb2);
			}
		});
		jl.setLayout(null);
		readdate();
		
		
		jl2.setHorizontalAlignment((int) JTextField.CENTER_ALIGNMENT);
		jl2.setOpaque(true);
		jl2.setBackground(Color.WHITE);
		jl2.setFont(new Font("宋体",Font.PLAIN,30));
		jl2.setText(sm.getStudent(0).getName());
		
		
		jp.add(jl);
		jl.add(jb1);
		jl.add(jl2);
		jf.add(jp);
		jf.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {}
			@Override
			public void windowIconified(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowDeactivated(WindowEvent e) {}
			@Override
			public void windowClosed(WindowEvent e) {}
			@Override
			public void windowActivated(WindowEvent e) {}
			@Override
			public void windowClosing(WindowEvent e) {
				writeCSV();
			}
		});
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {
					if(StudentManager.runSignal)
					{
						jb1.setEnabled(false);
						jb2.setEnabled(true);
					}
					else {
						jb1.setEnabled(true);
						jb2.setEnabled(false);
					}
					try {
						Thread.sleep(500);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		}).start();
		(new Audiotest("music/begin.wav")).start();
		jf.setVisible(true);//显示
	}
	public void readdate() {
		try {
			ArrayList<String[]> csvFileList = new ArrayList<String[]>();
			String csvFilePath = "date.csv";
			CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName("GBK"));
			reader.readHeaders();
			while (reader.readRecord()) {
			csvFileList.add(reader.getValues()); 
			}
			reader.close();
			for (int row = 0; row < csvFileList.size(); row++) {
				if (csvFileList.get(row)[1].equals("")) {
					sm.addStudent(csvFileList.get(row)[0], 0);
				}
				else
					sm.addStudent(csvFileList.get(row)[0], Integer.parseInt(csvFileList.get(row)[1]));
			}
			System.out.println("--------CSV文件读取成功--------");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeCSV() {
		String csvFilePath = "date.csv";
		try {
			CsvWriter csvWriter = new CsvWriter(csvFilePath, ',', Charset.forName("GBK"));
			String[] csvHeaders = {"姓名","命中次数"};
			csvWriter.writeRecord(csvHeaders);
			for (int i = 0; i < sm.getNum(); i++) {
				String[] csvContent = {sm.getStudent(i).getName(),""+sm.getStudent(i).getTime()};
				csvWriter.writeRecord(csvContent);
			}
			csvWriter.close();
			System.out.println("--------CSV文件已经写入--------");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String argv[]) {
		try {
			BeautyEyeLNFHelper.frameBorderStyle=FrameBorderStyle.generalNoTranslucencyShadow;
			BeautyEyeLNFHelper.translucencyAtFrameInactive = false;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			UIManager.put("ToolBar.isPaintPlainBackground", Boolean.TRUE);
			UIManager.put("RootPane.setupButtonVisible", false);
		}catch (Exception e) {
		}
		
		new WelcomeJF();
	}
}
