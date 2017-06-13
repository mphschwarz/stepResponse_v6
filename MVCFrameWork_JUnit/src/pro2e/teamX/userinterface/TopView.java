package pro2e.teamX.userinterface;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;

import pro2e.teamX.MVCFramework;
import pro2e.teamX.model.Model;
import pro2e.teamX.model.PREFERENZEN;

public class TopView extends JPanel implements Observer, ActionListener {

	private Controller controller;
	private SchrittantwortPlot schrittantwortPlot;
	public PNSEbene pnsEbene = new PNSEbene();
	public EingabePanel eingabePanel;
	public FilterPanel filterPanel;
	public StatusBar statusBar;
	public PolListe polListe;
	public UebertragungsfunktionsPanel uebertragungsfunktionsPanel;
	

	public boolean zustandfilter = true;
	protected JButton btOk = new JButton("OK");

	/**
	 * Die TopView erzeugt die Panels und baut das GUI. Es regristriert die Buttons und CheckBoxen des EingabePanels.
	 * @param controller
	 */
	public TopView(Controller controller) {
		super(new GridBagLayout());
		this.controller = controller;

		schrittantwortPlot = new SchrittantwortPlot();
		polListe = new PolListe();
		eingabePanel = new EingabePanel(controller);
		filterPanel = new FilterPanel(controller);
		uebertragungsfunktionsPanel = new UebertragungsfunktionsPanel();
		statusBar = new StatusBar();

		schrittantwortPlot
				.setPreferredSize(new Dimension((int) (MVCFramework.appWidth * 0.35), (int) (MVCFramework.appHeight * 0.6)));
		pnsEbene.setPreferredSize(new Dimension((int) (MVCFramework.appWidth * 0.35), (int) (MVCFramework.appHeight * 0.6)));
		polListe.setPreferredSize(new Dimension((int) (MVCFramework.appWidth * 0.3), (int) (MVCFramework.appHeight * 0.6)));
		eingabePanel
				.setPreferredSize(new Dimension((int) (MVCFramework.appWidth * 0.35), (int) (MVCFramework.appHeight * 0.25)));
		filterPanel
				.setPreferredSize(new Dimension((int) (MVCFramework.appWidth * 0.35), (int) (MVCFramework.appHeight * 0.25)));
		uebertragungsfunktionsPanel
				.setPreferredSize(new Dimension((int) (MVCFramework.appWidth * 0.3), (int) (MVCFramework.appHeight * 0.25)));
		statusBar.setPreferredSize(new Dimension((int) (MVCFramework.appWidth), (int) (MVCFramework.appHeight * 0.15)));

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
		
		

		

		eingabePanel.btberechen.addActionListener(this);
		eingabePanel.btAbbruch.addActionListener(this);
		eingabePanel.jcordnungVon.addActionListener(this);
		eingabePanel.jcordnungBis.addActionListener(this);
		filterPanel.setActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int anzahlWerte = Integer.parseInt(FilterPanel.tfAnzahlwerte.getText());
				controller.filtern(anzahlWerte);
			}
		});

	}

	/**
	 * Ruft bei betätigung des btBerechnen Buttons die Methode setParameter() des Controllers auf. Bei betätigung des Abbruch Buttons wird der PREFERENZEN.abbrechen boolean auf
	 * auf true gesetzt um den Fminsearch zu unterbinden.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == eingabePanel.btberechen) {
			controller.setParameter();
		}
		if (e.getSource() == eingabePanel.btAbbruch) {

			PREFERENZEN.abbrechen = true;
		}
	}

	/**
	 * Ruft update() der jeweiligen Panel auf.(schrittantwortPlot,pnsEbene, polListe,uebertragungsfunktionsPanel) auf.
	 */
	@Override
	public void update(Observable obs, Object obj) {
		schrittantwortPlot.update(obs, obj);
		pnsEbene.update(obs, obj);
		polListe.update(obs, obj);
		uebertragungsfunktionsPanel.update(obs, obj);

		repaint();
	}

	public void setMenuBar(MenuBar menuBar) {}
}
