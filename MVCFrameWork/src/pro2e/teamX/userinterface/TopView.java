package pro2e.teamX.userinterface;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import pro2e.teamX.MVCFramework;
import pro2e.teamX.model.Model;

public class TopView extends JPanel implements Observer, ActionListener {

	private Controller controller;
	private JButton btberechnen = new JButton("Berechnen");
	private SchrittantwortPlot schrittantwortPlot;
	private PNSEbene pnsEbene = new PNSEbene();
	private MenuBar menuBar;
	public EingabePanel eingabePanel;
	public FilterPanel filterPanel;
	public StatusBar statusBar;
	public PolListe polListe;
	public UebertragungsfunktionsPanel uebertragungsfunktionsPanel;

	private JProgressBar jPBar = new JProgressBar(0, 100);
	private JTextArea jtpols = new JTextArea(5, 20);

	private JMenuBar jMenuBar = new JMenuBar();
	private JMenu fileMenu, editMenu;

	//public JPanel pSchrittantwort = new JPanel();
	//	public JPanel pSEbene = new JPanel();
	//	public JPanel pEingabe = new JPanel(new GridBagLayout());
	//	public JPanel pFilter = new JPanel();
	//	public JPanel pPolNullstellen = new JPanel();
	//	public JPanel pUebertragungsfunktion = new JPanel();
	//	public JPanel pStatusbar =new JPanel();

	public boolean zustandfilter = true;
	protected JButton btOk = new JButton("OK");

	public TopView(Controller controller) {
		super(new GridBagLayout());
		this.controller = controller;

		schrittantwortPlot = new SchrittantwortPlot();
		polListe = new PolListe();
		eingabePanel = new EingabePanel(controller);
		filterPanel = new FilterPanel(); // controller fehl im filterpanel -> ergänzen :)
		uebertragungsfunktionsPanel = new UebertragungsfunktionsPanel();
		statusBar = new StatusBar();

		schrittantwortPlot
				.setPreferredSize(new Dimension((int) (MVCFramework.appWidth * 0.5), (int) (MVCFramework.appHeight * 0.75)));
		pnsEbene.setPreferredSize(new Dimension((int) (MVCFramework.appWidth * 0.25), (int) (MVCFramework.appHeight * 0.75)));
		polListe.setPreferredSize(new Dimension((int) (MVCFramework.appWidth * 0.25), (int) (MVCFramework.appHeight * 0.75)));
		eingabePanel.setPreferredSize(new Dimension((int) (MVCFramework.appWidth * 0.5), (int) (MVCFramework.appHeight * 0.2)));
		filterPanel.setPreferredSize(new Dimension((int) (MVCFramework.appWidth * 0.25), (int) (MVCFramework.appHeight * 0.2)));
		uebertragungsfunktionsPanel
				.setPreferredSize(new Dimension((int) (MVCFramework.appWidth * 0.25), (int) (MVCFramework.appHeight * 0.2)));
		statusBar.setPreferredSize(new Dimension((int) (MVCFramework.appWidth), (int) (MVCFramework.appHeight * 0.05)));

		add(schrittantwortPlot, new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 5, 5), 0, 0));
		add(pnsEbene, new GridBagConstraints(2, 0, 1, 1, 0.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
				new Insets(5, 5, 5, 5), 0, 0));
		add(polListe, new GridBagConstraints(3, 0, 1, 1, 0.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
				new Insets(5, 5, 5, 5), 0, 0));
		add(eingabePanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 0, 0));
		add(filterPanel, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 0, 0));
		add(uebertragungsfunktionsPanel, new GridBagConstraints(2, 1, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

		eingabePanel.tfOrdnung.addActionListener(this);
		eingabePanel.tfOrdnungbis.addActionListener(this);
		eingabePanel.tfZeitNormierung.addActionListener(this);
		eingabePanel.tfStartwerte.addActionListener(this);
		eingabePanel.btberechen.addActionListener(this);
		eingabePanel.btAbbruch.addActionListener(this);
		eingabePanel.jcordnungVon.addActionListener(this);
		eingabePanel.jcordnungBis.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == eingabePanel.btberechen) {
			controller.setParameter();
		}
		if (e.getSource() == eingabePanel.btAbbruch) {
			System.exit(0);
		}
	}

	@Override
	public void update(Observable obs, Object obj) {

		Model model = (Model) obs;
		System.out.println("TEST!!!!!!!!!!!!!!!!");
		schrittantwortPlot.update(obs, obj);
		pnsEbene.update(obs, obj);
		polListe.update(obs, obj);
		uebertragungsfunktionsPanel.update(obs, obj);
		System.out.println("Update Topview");
		repaint();
	}

	public void setMenuBar(MenuBar menuBar) {
		this.menuBar = menuBar;
	}
}
