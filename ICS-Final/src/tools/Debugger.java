package tools;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class Debugger {

	private JFrame frame;
	private JTextField FPS;
	private JTextField TPS;
	public static long lastPaintDelay = 0;
	public static long lastGameDelay = 0;

//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Debugger window = new Debugger();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public Debugger() {
		initialize();
		 Timer t = new Timer();
		    t.schedule(new TimerTask() {
		        @Override public void run() {
		        	FPS.setText(lastPaintDelay/1000000D + " ");
		        	TPS.setText(lastGameDelay/1000000D + " ");
		        }
		    }, 0L, 100L);
	}
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[][grow][][grow]", "[][]"));
		
		JLabel FPSLabel = new JLabel("FPS");
		frame.getContentPane().add(FPSLabel, "cell 1 0");
		
		JLabel TPSLabel = new JLabel("TPS");
		frame.getContentPane().add(TPSLabel, "cell 3 0");
		
		FPS = new JTextField();
		FPS.setText("FPS");
		frame.getContentPane().add(FPS, "cell 1 1,growx");
		FPS.setColumns(10);
		
		TPS = new JTextField();
		TPS.setText("TPS");
		frame.getContentPane().add(TPS, "cell 3 1,growx");
		TPS.setColumns(10);
		frame.setVisible(true);
	}

}
