package TrackDisplay;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SectionListener extends MouseAdapter
{
	private PanelButton m_bt;
	
	public SectionListener(PanelButton bt)
	{
		m_bt = bt;
	}

	@Override
	public void mouseEntered(MouseEvent arg0) 
	{
		m_bt.isHighLighted = true;
		m_bt.repaint();
	}
	
	@Override
	public void mouseExited(MouseEvent e) 
	{
		m_bt.isHighLighted = false;
		m_bt.repaint();
	}
	@Override
	public void mousePressed(MouseEvent e)
	{
		m_bt.buttonAction();
	}
}
