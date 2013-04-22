package TrackDisplay;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import TrackModel.Section;
import TrackModel.TrackModelPanel;

public class SectionListener extends MouseAdapter
{
	private PanelButton m_bt;
	private TrackModelPanel m_tmp;
	private Section m_sec;
	
	public SectionListener(PanelButton bt, TrackModelPanel tmp, Section sec)
	{
		m_bt = bt;
		m_tmp = tmp;
		m_sec = sec;
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
		m_bt.isHighLighted = false;
		m_tmp.setPanel(m_sec.getPanel(), m_sec.getLine().getName() + "-" + m_sec.getName());
		//m_bt.buttonAction();
	}
}
