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
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import Log.Log;
import Simulator.Simulator;
import TrackController.TrackControllerModule;
import TrackModel.TrackModelModule;
import CTC.CTCModule;
import TrainController.TrainControllerModule;
import TrainModel.TrainModelModule;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class System_GUI {

	private JFrame frmCtcss;
	private static Log log;
	private static CTCModule ctc;
	private static Graphics2D g = null;
	private static SplashScreen splash = null;
	private static TrainModelModule tm;
	private static TrackModelModule trm;
	private static TrackControllerModule trc;
	private static TrainControllerModule tnc;
	private static Simulator sim;
	private static boolean loggedIn = false;
	private static boolean foundSplash = true;
	
	private JPanel panel_1;
	private JTabbedPane tabbedPane;
	private JMenuItem mntmLogout;
	private JMenu mnFile;
	private JMenuItem mntmLogin;
	private JMenuItem mntmRun;
	private JMenuItem mntmPause;
	private JMenu mnSimulation;
	
	private System_GUI sys;
	static void renderSplashFrame(int frame) {
        final String[] comps = {"Log", "CTC", "TrackModel", "TrackController", "TrainModel", "TrainController", "Simulator"};
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(120,140,200,40);
        g.setPaintMode();
        g.setColor(Color.BLACK);
        if(frame != comps.length)
        	g.drawString("Loading "+comps[frame]+"...", 120, 150);
        else
        	g.drawString("Preparing System...", 120, 150);
    }
	
	private static void setGUILAndF() {
		try {
            	// Set System L&F
			if(UIManager.getSystemLookAndFeelClassName().toString().equals("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"))
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			else
				try {
				    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				        if ("Nimbus".equals(info.getName())) {
				            UIManager.setLookAndFeel(info.getClassName());
				            break;
				        }
				    }
				} catch (Exception e) {
				    // If Nimbus is not available the look and feel will be set to the default java look and feel.
				}
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
	}
	
	private static void updateSplash(int frame) {
		if(foundSplash) {
			renderSplashFrame(frame);
			splash.update();
			try {
				Thread.sleep(0);//360);
			}
			catch(InterruptedException e) {
			}
		}
	}
	
	private static void setup() {
		updateSplash(0);
		setGUILAndF();
		log = Log.Instance();
		log.append(0, "Log Loaded\n");
		log.append(0, "Look and Feel set to " + UIManager.getLookAndFeel().getName() + "\n");
		updateSplash(1);
		ctc = new CTCModule();
		log.append(0, "CTC Module Loaded\n");
		updateSplash(2);
		trm = new TrackModelModule();
		log.append(0,  "Track Model Module Loaded\n");
		updateSplash(3);
		trc = new TrackControllerModule();
		log.append(0,  "Track Controller Module Loaded\n");
		updateSplash(4);
		tm = new TrainModelModule();
		log.append(0,  "Train Model Module Loaded\n");
		updateSplash(5);
		tnc = new TrainControllerModule();
		tm.receiveController(tnc);
		log.append(0,  "Train Controller Module Loaded\n");
		updateSplash(6);
		sim = new Simulator(ctc, trc, tm, trm);
		ctc.setSimulator(sim);
		log.append(0, "Simulator Started\n");
		for(int i = 0; i < 1; i++)
			updateSplash(7);
		log.append(0, "System Ready\n");
		log.append(3, "Please Login\n");
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		splash = SplashScreen.getSplashScreen();
        if (splash == null) {
            foundSplash = false;
        }
        if(foundSplash) {
        	g = splash.createGraphics();
        	if (g == null) {
        		foundSplash = false;
        	}
        }
        setup();
        if(foundSplash)
        	splash.close();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System_GUI window = new System_GUI();
					sim.setSys(window);
					window.frmCtcss.setResizable(false);
					window.frmCtcss.setVisible(true);
					window.frmCtcss.toFront();
					Thread t = new Thread(sim);
					t.start();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public System_GUI() {
		initialize();
		sys = this;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCtcss = new JFrame();
		frmCtcss.setTitle("CTCSS");
		frmCtcss.setBounds(100, 100, 988, 600);
		frmCtcss.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCtcss.getContentPane().setLayout(null);
		
		// Log
		JPanel panel = log.getPanel();
		panel.setBounds(10, 400, 964, 130);
		
			frmCtcss.getContentPane().add(panel);
		
		// Tabbed display for modules
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.GRAY);
		tabbedPane.setBounds(320, 11, 654, 378);
		if(loggedIn)
			frmCtcss.getContentPane().add(tabbedPane);
		
		// Track Model
		JPanel panel_2 = trm.getPanel();
		tabbedPane.addTab("Track Model", null, panel_2, null);
		panel_2.setLayout(null);
		
		// Track Controller
		JPanel panel_3 = trc.getPanel();
		tabbedPane.addTab("Track Controller", null, panel_3, null);
		
		// Train Model
		JPanel panel_4 = tm.getPanel();
		tabbedPane.addTab("Train Model", null, panel_4, null);
		
		// Train Controller
		JPanel panel_5 = tnc.getPanel();
		tabbedPane.addTab("Train Controller", null, panel_5, null);
		
		// CTC
		panel_1 = ctc.getPanel();
		panel_1.setBounds(10, 11, 300, 378);
		if(loggedIn)
			frmCtcss.getContentPane().add(panel_1);
		
		
		JMenuBar menuBar = new JMenuBar();
		frmCtcss.setJMenuBar(menuBar);
		
		// File Menu
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmLogin = new JMenuItem("Login");
		mntmLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mntmLogin.setEnabled(false);
				new LoginDialog(sys);
			}
		});
		mnFile.add(mntmLogin);
		
		mntmLogout = new JMenuItem("Logout");
		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logout();
			}
		});
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AboutDialog();
			}
		});
		mnFile.add(mntmAbout);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		
		// Simulation Menu
		mnSimulation = new JMenu("Simulation");
		menuBar.add(mnSimulation);
		mnSimulation.setEnabled(false);
		
		mntmRun = new JMenuItem("Run");
		mntmRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				unPause();
			}
		});
		
		mntmPause = new JMenuItem("Pause");
		mntmPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pause();
			}
		});
		mnSimulation.add(mntmPause);
		
		JMenuItem mntmSetSpeed = new JMenuItem("Set Speed");
		mntmSetSpeed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(loggedIn) {
					sim.getSpeedDialog().setVisible(true);
				}
			}
		});
		mnSimulation.add(mntmSetSpeed);
		
		JMenuItem mntmMetrics = new JMenuItem("Metrics");
		mnSimulation.add(mntmMetrics);
	}
	
	// Simply to show functionality for now will be fleshed out more later
	private void login() {
		if(loggedIn) {
			frmCtcss.getContentPane().add(panel_1);
			frmCtcss.getContentPane().add(tabbedPane);
			mnFile.remove(0);
			mnFile.insert(mntmLogout, 0);
			frmCtcss.repaint();
			mnSimulation.setEnabled(true);
		}
		else {
			log.append(3, "Login failed\n");
		}
	}
	
	protected void setLoggedIn(boolean val) {
		loggedIn = val;
		login();
	}
	
	// Simply to show functionality for now will be fleshed out more later
	private void logout() {
		frmCtcss.remove(panel_1);
		frmCtcss.remove(tabbedPane);
		mnFile.remove(0);
		mnFile.insert(mntmLogin, 0);
		frmCtcss.repaint();
		loggedIn = false;
		mnSimulation.setEnabled(false);
		Login.logout();
	}
	
	private void pause() {
		sim.togglePause();
		mnSimulation.remove(0);
		mnSimulation.insert(mntmRun, 0);
		frmCtcss.repaint();
	}
	
	private void unPause() {
		sim.togglePause();
		mnSimulation.remove(0);
		mnSimulation.insert(mntmPause, 0);
		frmCtcss.repaint();
	}
	
	public JFrame getFrame() {
		return this.frmCtcss;
	}
}
