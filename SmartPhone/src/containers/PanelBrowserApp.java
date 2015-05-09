package containers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserPreferences;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import components.MyPanel;
import components.MyTextField;
import components.MyTransparentButton;

@SuppressWarnings("serial")
public class PanelBrowserApp extends MyPanel {


	private Browser browser;
	private JPanel panelNorth;
	private MyTransparentButton buttonBack = new MyTransparentButton("<<<");
	private MyTransparentButton buttonForward = new MyTransparentButton(">>>");
	
	public PanelBrowserApp(MyPanel myPanel, int marginW, int marginH) {
		super(myPanel, marginW, marginH);
		setLayout(new BorderLayout());
		panelNorth = new JPanel();
		panelNorth.add(buttonBack);
		panelNorth.add(new JLabel());
		panelNorth.add(buttonForward);
		//very nice one //Mozilla/5.0 (Linux; Android 5.1; ro-ro; SAMSUNG SM-G900F Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Version/2.1 Chrome/34.0.1847.76 Mobile Safari/537.36 
		//meilleur //Mozilla/5.0 (Linux; Android 5.1; Nexus 4 Build/LMY47O) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.109 Mobile Safari/537.36
		//not bad // Mozilla/5.0 (Macintosh; U; Intel Mac OS X; en) AppleWebKit/525.18.1 (KHTML, like Gecko) Version/3.1.1 Iris/1.1.7 Safari/525.20
		BrowserPreferences.setUserAgent("Mozilla/5.0 (Linux; Android 5.0.2; ro-ro;; LG-D802 Build/LMY47V; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/2.1 Chrome/42.0.2311.137 Mobile Safari/537.36");
		browser = new Browser();
		
		panelNorth.setOpaque(false);
		panelNorth.setLayout(new GridLayout(1,3));
		buttonBack.addActionListener(new EcouteurBouton());
		buttonBack.setActionCommand("back");
		buttonForward.addActionListener(new EcouteurBouton());
		buttonForward.setActionCommand("forward");
		BrowserView browserView = new BrowserView(browser);
		browserView.setOpaque(false);
		add(BorderLayout.NORTH,panelNorth);
		add(BorderLayout.CENTER,browserView);

	}
	

	public void LoadUrl(String URL){
		browser.loadURL(URL);
	}
	
	
	class EcouteurBouton implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getActionCommand().equals("back")) {
				browser.goBack();
			}

			if (e.getActionCommand().equals("forward")) {
				browser.goForward();
			}


		}

	}
}
