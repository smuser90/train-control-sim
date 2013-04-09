package System;

import java.awt.AlphaComposite;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.SplashScreen;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Log.LogPanel;
import TrackController.TCPanel;
import CTC.CTCPanel;

public class System_GUI {

	private JFrame frmCtcss;
	
	static void renderSplashFrame(Graphics2D g, int frame) {
        final String[] comps = {"foo", "bar", "baz"};
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(120,140,200,40);
        g.setPaintMode();
        g.setColor(Color.BLACK);
        g.drawString("Loading "+comps[(frame/5)%3]+"...", 120, 150);
    }
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
	            // Set System L&F
	        UIManager.setLookAndFeel(
	            UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (UnsupportedLookAndFeelException e) {
	       // handle exception
	    }
	    catch (ClassNotFoundException e) {
	       // handle exception
	    }
	    catch (InstantiationException e) {
	       // handle exception
	    }
	    catch (IllegalAccessException e) {
	       // handle exception
	    }
		
		final SplashScreen splash = SplashScreen.getSplashScreen();
        if (splash == null) {
            System.out.println("SplashScreen.getSplashScreen() returned null");
            return;
        }
        Graphics2D g = splash.createGraphics();
        if (g == null) {
            System.out.println("g is null");
            return;
        }
        for(int i=0; i<50; i++) {
            renderSplashFrame(g, i);
            splash.update();
            try {
                Thread.sleep(90);
            }
            catch(InterruptedException e) {
            }
        }
        splash.close();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System_GUI window = new System_GUI();
					window.frmCtcss.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public System_GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCtcss = new JFrame();
		frmCtcss.setTitle("CTCSS");
		frmCtcss.setBounds(100, 100, 1000, 600);
		frmCtcss.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCtcss.getContentPane().setLayout(null);
		
		JPanel panel = new LogPanel();
		panel.setBounds(10, 400, 964, 130);
		frmCtcss.getContentPane().add(panel);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.GRAY);
		tabbedPane.setBounds(320, 11, 654, 378);
		frmCtcss.getContentPane().add(tabbedPane);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		tabbedPane.addTab("Track Model", null, panel_2, null);
		panel_2.setLayout(null);
		
		JPanel panel_3 = new TCPanel();
		tabbedPane.addTab("Track Controller", null, panel_3, null);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		tabbedPane.addTab("Train Model", null, panel_4, null);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		tabbedPane.addTab("Train Controller", null, panel_5, null);
		
		JPanel panel_1 = new CTCPanel();
		panel_1.setBounds(10, 11, 300, 378);
		frmCtcss.getContentPane().add(panel_1);
		
		JMenuBar menuBar = new JMenuBar();
		frmCtcss.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmLogin = new JMenuItem("Login");
		mnFile.add(mntmLogin);
		
		JMenuItem mntmLogout = new JMenuItem("Logout");
		mnFile.add(mntmLogout);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnFile.add(mntmAbout);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		JMenu mnSimulation = new JMenu("Simulation");
		menuBar.add(mnSimulation);
		
		JMenuItem mntmRun = new JMenuItem("Run");
		mnSimulation.add(mntmRun);
		
		JMenuItem mntmPause = new JMenuItem("Pause");
		mnSimulation.add(mntmPause);
		
		JMenuItem mntmSetSpeed = new JMenuItem("Set Speed");
		mnSimulation.add(mntmSetSpeed);
		
		JMenuItem mntmMetrics = new JMenuItem("Metrics");
		mnSimulation.add(mntmMetrics);
	}
}
