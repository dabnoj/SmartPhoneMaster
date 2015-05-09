package containers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserPreferences;

import misc.Clock;

import components.MyLabel;
import components.MyPanel;
import components.MyTransparentButton;



@SuppressWarnings("serial")
public class Frame extends JFrame {

	private int posX = 0;
	private int posY = 0;
	private boolean inverted = false;

	//panneaux personnalisés
	private MyPanel panelCard;
	private PanelHome panelHome;
	private PanelContactList panelContact;
	private PanelContacts panelContacts;
	private PanelBrowserApp panelFacebook;
	private PanelBrowserApp panelTwitter;
	private PanelBrowserApp panelWiki;
	private PanelBrowserApp panelGmail;
	private PanelBrowserApp panelInternet;
	private PanelBrowserApp panelRadio;
	private PanelBrowserApp panelMeteo;


	//boxs relatifs à chaque Panel
	private Box boxHome;
	private Box boxContacts;
	private Box boxContactList;
	private Box boxFacebook;
	private Box boxTwitter;
	private Box boxWiki;
	private Box boxGmail;
	private Box boxInternet;
	private Box boxRadio;
	private Box boxMeteo;
	//panneaux de base (servant à la mise en forme du Frame)
	private JPanel panelNorth = new JPanel(new GridLayout(1, 3));
	private JPanel panelSouth = new JPanel();
	private JPanel panelBrand = new JPanel();

	//bouton Home
	private MyTransparentButton buttonHome = new MyTransparentButton("Home",new ImageIcon(
			"SmartPhoneImages/home.png"));

	//labels personnalisés
	private MyLabel labelBattery = new MyLabel(new ImageIcon(
			"SmartPhoneImages/battery.png"));
	private final MyLabel labelClock = new MyLabel("");
	private final MyLabel labelBrand = new MyLabel("HTC");

	//instance de la classe clock
	private Clock clock = new Clock(labelClock);

	public Frame(final int width,final int height) {
		// set visible ici
		setResizable(false);
		// taille de la frame,bordures et couleur + misc
		setSize(width, height);
		panelNorth.setBorder(new EmptyBorder(15, 25, 0, 25));
		setUndecorated(true);
		setShape(new RoundRectangle2D.Double(0, 0, getWidth(), 
				getHeight(), 25,25));
		getContentPane().setBackground(new Color(0,0,0));
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// ajout du drag and drop (sur mouse et mouseMotion #logicalRulez)
		addMouseListener(new DragAndDropListener());
		addMouseMotionListener(new DragAndDropListener());

		labelBrand.setForeground(Color.WHITE);

		// regler la batterie a droite
		labelBattery.setHorizontalAlignment(SwingConstants.RIGHT);

		// ajout et reglage des Jcomponents dans le southPanel
		panelSouth.setOpaque(false);
		panelSouth.add(buttonHome);
		panelCard = new MyPanel(this,25,50);

		// reglage horloge
		setClock();
		labelClock.setForeground(new Color(52, 73, 94));
		panelNorth.add(labelClock);
		// trailing space
		panelNorth.add(new MyLabel());
		panelNorth.add(labelBattery);
		panelNorth.setOpaque(false);
		panelBrand.add(labelBrand);
		panelBrand.setOpaque(false);
		// set mainPanel (background et position)

		panelCard.setBackground(new Color(30, 34, 45));
		panelCard.setOpaque(true);
		// ajout dans le content pane
		getContentPane().add(BorderLayout.CENTER, createBox(panelCard));
		getContentPane().add(BorderLayout.SOUTH, panelSouth);
		getContentPane().add(BorderLayout.NORTH, panelBrand);
		// set visible avant de choper longeur largeur des panel -> sinon null
		setVisible(true);

		//instanciation des différents Panels
		panelHome = new PanelHome(panelCard, 50, 75);
		panelInternet = new PanelBrowserApp(panelCard, 0, 0);
		panelContacts = new PanelContacts(panelCard, 50, 75);
		panelContact  = new PanelContactList(panelCard,50,75);
		panelFacebook = new PanelBrowserApp(panelCard,0,0);
		panelTwitter = new PanelBrowserApp(panelCard,0,0);
		panelWiki = new PanelBrowserApp(panelCard,0,0);
		panelGmail = new PanelBrowserApp(panelCard,0,0);
		panelRadio = new PanelBrowserApp(panelCard,0,0);
		panelMeteo = new PanelBrowserApp(panelCard,0,0);
		
		panelCard.setLayout(new BorderLayout());

		//instanctiation des box via la méthode createBox
		boxHome = createBox(panelHome);
		boxContacts = createBox(panelContacts);
		boxContactList = createBox(panelContact);
		boxFacebook = createBox(panelFacebook);
		boxTwitter = createBox(panelTwitter);
		boxWiki = createBox(panelWiki);
		boxGmail = createBox(panelGmail);
		boxInternet = createBox(panelInternet);
		boxRadio = createBox(panelRadio);
		boxMeteo = createBox(panelMeteo);
		//ajout des Components dans les Panels correspondants
		//set invisible pour les panel contact et contacts		
		panelCard.add(BorderLayout.CENTER, boxHome);
		panelCard.add(BorderLayout.NORTH, panelNorth);
		//reglage des listener
		EcouteurBouton ecouteurBouton = new EcouteurBouton();
		panelHome.getButtonContact().addActionListener(ecouteurBouton);
		panelHome.getButtonContact().setActionCommand("contacts");		
		panelContacts.getBajoutContacts().addActionListener(ecouteurBouton);
		panelContacts.getBajoutContacts().setActionCommand("ajoutContact");		
		panelContact.getButtonSwitchePanel().addActionListener(ecouteurBouton);
		panelContact.getButtonSwitchePanel().setActionCommand("retourPanneauContact");
		panelHome.getButtonFacebook().addActionListener(ecouteurBouton);
		panelHome.getButtonFacebook().setActionCommand("Facebook");
		panelHome.getButtonTwitter().addActionListener(ecouteurBouton);
		panelHome.getButtonTwitter().setActionCommand("Twitter");
		panelHome.getButtonWiki().addActionListener(ecouteurBouton);
		panelHome.getButtonWiki().setActionCommand("Wiki");
		panelHome.getButtonGmail().addActionListener(ecouteurBouton);
		panelHome.getButtonGmail().setActionCommand("Gmail");
		panelHome.getButtonRadio().addActionListener(ecouteurBouton);
		panelHome.getButtonRadio().setActionCommand("Radio");
		panelHome.getButtonMeteo().addActionListener(ecouteurBouton);
		panelHome.getButtonMeteo().setActionCommand("Meteo");
		
		panelHome.getButtonInternet().addActionListener(ecouteurBouton);
		panelHome.getButtonInternet().setActionCommand("Internet");
		//regla du listener sur le boutton Home
		buttonHome.addActionListener(ecouteurBouton);
		buttonHome.setActionCommand("home");

		//ajout du lisnter double click sur la frame (portrait paysage)
		// à retravailler c'est un beta fait en 2mn
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2) {
					
						if(!inverted){
							dispose();
							Frame f = new Frame(900,500);
							f.inverted = true;
							
						}
						else{
							dispose();
							new Frame(500,900);
							
						}
					}
				
			}});

	}

	public Box createBox(JPanel panel) {
		Box box = new Box(BoxLayout.Y_AXIS);
		box.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		box.add(Box.createVerticalGlue());
		box.add(panel);
		box.add(Box.createVerticalGlue());
		return box;
	}

	/**
	 * void method pour mettre la putain d'heure
	 */
	public void setClock() {
		Thread thread1 = new Thread(clock);
		thread1.start();
	}


	/**
	 * class pour permettre le drag and drop
	 * easy! peasy! take it easy
	 * 
	 * @author Jmoulin
	 *
	 */
	class DragAndDropListener extends MouseAdapter {
		@Override
		public void mouseDragged(final MouseEvent evt) {
			setLocation(evt.getXOnScreen() - posX, evt.getYOnScreen() - posY);
		}

		@Override
		public void mousePressed(final MouseEvent evt) {
			posX = evt.getX();posY = evt.getY();
		}
	}


	class EcouteurBouton implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getActionCommand().equals("retourPanneauContact")) {
				panelNorth.setVisible(true);
				boxContacts.setVisible(true);
				boxContactList.setVisible(false);
				boxHome.setVisible(false);	
			}

			if (e.getActionCommand().equals("contacts")) {
				panelNorth.setVisible(true);
				boxContacts.setVisible(true);
				panelCard.add(boxContacts);
				boxHome.setVisible(false);
				boxContactList.setVisible(false);	
			}

			if (e.getActionCommand().equals("Facebook")) {
				panelNorth.setVisible(false);
				boxFacebook.setVisible(true);
				panelCard.add(boxFacebook);
				boxHome.setVisible(false);
				panelFacebook.LoadUrl("https://m.facebook.fr");

			}

			if (e.getActionCommand().equals("Twitter")) {
				panelNorth.setVisible(false);
				boxTwitter.setVisible(true);
				panelCard.add(boxTwitter);
				boxHome.setVisible(false);
				panelTwitter.LoadUrl("https://m.Twitter.com/fr");

			}

			if (e.getActionCommand().equals("Wiki")) {
				panelNorth.setVisible(false);
				boxWiki.setVisible(true);
				panelCard.add(boxWiki);
				boxHome.setVisible(false);
				panelWiki.LoadUrl("https://fr.m.wikipedia.org");

			}
			if (e.getActionCommand().equals("Gmail")) {
				panelNorth.setVisible(false);
				boxGmail.setVisible(true);
				panelCard.add(boxGmail);
				boxHome.setVisible(false);
				panelGmail.LoadUrl("http://m.Gmail.com");

			}

			if (e.getActionCommand().equals("home")) {
				panelNorth.setVisible(true);
				boxHome.setVisible(true);
				boxRadio.setVisible(false);
				boxTwitter.setVisible(false);
				boxWiki.setVisible(false);
				boxFacebook.setVisible(false);
				boxContacts.setVisible(false);
				boxContactList.setVisible(false);
				boxGmail.setVisible(false);
				boxInternet.setVisible(false);
				boxMeteo.setVisible(false);

			}

			if (e.getActionCommand().equals("ajoutContact")) {
				panelNorth.setVisible(true);
				boxContactList.setVisible(true);
				panelCard.remove(boxContactList);
			
				panelCard.add(boxContactList);
				boxHome.setVisible(false);
				boxContacts.setVisible(false);
			}

			if (e.getActionCommand().equals("Internet")) {
				panelNorth.setVisible(false);
				boxInternet.setVisible(true);
				panelCard.add(boxInternet);
				boxHome.setVisible(false);
				panelInternet.LoadUrl("google.fr");
			}
			if (e.getActionCommand().equals("Radio")) {
				panelNorth.setVisible(false);
				boxRadio.setVisible(true);
				panelCard.add(boxRadio);
				boxHome.setVisible(false);
				panelRadio.LoadUrl("www.internet-radio.com");
			}
			
			if (e.getActionCommand().equals("Meteo")) {
				panelNorth.setVisible(false);
				boxMeteo.setVisible(true);
				panelCard.add(boxMeteo);
				boxHome.setVisible(false);
				panelMeteo.LoadUrl("http://www.meteosuisse.admin.ch/home.html?tab=overview");
			}
		}
	}
}
