package components;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author  Jmoulin
 */
@SuppressWarnings("unused")
public class MyPanel extends JPanel {

	
	private JFrame frame;
	/**
	 * @uml.property  name="myPanel"
	 * @uml.associationEnd  
	 */
	private MyPanel myPanel;
	private int componentW;
	private int componentH;
	private int marginW;
	private int marginH;


	public MyPanel(MyPanel myPanel, int marginW, int marginH){
		this.myPanel = myPanel;
		this.marginH = marginH;
		this.marginW = marginW;
		this.componentW = myPanel.getWidth();
		this.componentH = myPanel.getHeight();
		setOpaque(false);

	}


	public MyPanel(JFrame frame, int marginW, int marginH){
		this.frame = frame;
		this.marginH = marginH;
		this.marginW = marginW;
		this.componentW = frame.getWidth();
		this.componentH = frame.getHeight();
		setOpaque(false);

	}

	private static final long serialVersionUID = 1L;

	@Override
	public Dimension getMinimumSize() {
		
		return new Dimension(componentW -marginW, componentH -marginH);
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(componentW -marginW, componentH -marginH);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(componentW -marginW, componentH - marginH);
	}
	
	
	


}
