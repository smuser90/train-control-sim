/*
 *		TrackModelModuleTester.java
 *		This is the standalone pane for testing without integration with other parts of the system. It can load the 
 *		TrackModelPanel and Line files etc.
 *		Author: Kai Manuel
 *		Date Created: 04/07/2013
 *		Date Last Updated: 04/25/2013
 */

package TrackModel;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class TrackModelModuleTester
{

	private JFrame frame;
	private static TrackModelModule tm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		setGUILAndF();
		tm = new TrackModelModule();
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					TrackModelModuleTester window = new TrackModelModuleTester();
					window.frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TrackModelModuleTester()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 690, 470);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = tm.getPanel();
		panel.setBounds(10, 11, 650, 350);
		frame.getContentPane().add(panel);
	}

	private static void setGUILAndF()
	{
		try
		{
			// Set System L&F
			if (UIManager
					.getSystemLookAndFeelClassName()
					.toString()
					.equals("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"))
				UIManager.setLookAndFeel(UIManager
						.getSystemLookAndFeelClassName());
		}
		catch (UnsupportedLookAndFeelException e)
		{
			// handle exception
		}
		catch (ClassNotFoundException e)
		{
			// handle exception
		}
		catch (InstantiationException e)
		{
			// handle exception
		}
		catch (IllegalAccessException e)
		{
			// handle exception
		}
	}
}
