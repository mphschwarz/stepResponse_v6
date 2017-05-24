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

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

public class TopView extends JPanel implements Observer, ActionListener {
	private JButton btberechnen = new JButton("Berechnen");
	private Controller controller;
	private SchrittantwortPlot schrittantwortPlot;
	public UebertragungsfunktionsPanel uebertragungsfunktionsPanel;
	private PNSEbene pnsEbene = new PNSEbene();
	private MenuBar menuBar;
	public EingabePanel eingabePanel;
	public FilterPanel filterPanel;
	public SSEbene ssEbene;
	public Plotschritt plotschritt;
	public StatusBar statusBar;
	public PolListe polListe;

	
	private JProgressBar jPBar = new JProgressBar(0, 100);
	private JTextArea jtpols = new JTextArea(5, 20);

	private JMenuBar jMenuBar = new JMenuBar();
	private JMenu fileMenu, editMenu;

//	private double[] heiri = { 0, 0.1, 0.3, 0.5, 0.7, 1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.55, 1.50, 1.45, 1.50, 1.52, 1.48, 1.51,
//			1.49, 1.5 };
//	private double[] hans = new double[heiri.length];

	public JPanel panel1 = new JPanel();
	public JPanel panel2 = new JPanel();
	public JPanel panel3 = new JPanel(new GridBagLayout());
	public JPanel panel4 = new JPanel();
	public JPanel panel5 = new JPanel();
	public JPanel panel6 = new JPanel();
	public JPanel panel7 = new JPanel();
	
	public boolean zustandfilter = true;

	protected JButton btOk = new JButton("OK");

//	private double[] x0;

	public TopView(Controller controller) {
		super(new GridBagLayout());
		this.controller = controller;

		eingabePanel = new EingabePanel(controller);
		filterPanel = new FilterPanel(controller);
		schrittantwortPlot = new SchrittantwortPlot();
		uebertragungsfunktionsPanel = new UebertragungsfunktionsPanel();
		ssEbene = new SSEbene();
		plotschritt = new Plotschritt();
		statusBar=new StatusBar();
		polListe=new PolListe();

//		panel1.setPreferredSize(new Dimension(600, 400));
		panel1.setBorder(MyBorderFactory.createMyBorder(" Schrittantwort "));
		panel2.setPreferredSize(new Dimension(200, 200));
		panel2.setBorder(MyBorderFactory.createMyBorder(" s-Ebene "));
		panel3.setPreferredSize(new Dimension(600, 200));
		panel3.setBorder(MyBorderFactory.createMyBorder(" Eingabe "));
		panel4.setPreferredSize(new Dimension(200, 200));
		panel4.setBorder(MyBorderFactory.createMyBorder("Filter"));
		panel5.setPreferredSize(new Dimension(200, 200));
		panel5.setBorder(MyBorderFactory.createMyBorder("Pol/Nullstellen"));
		panel6.setPreferredSize(new Dimension(200, 200));
		panel6.setBorder(MyBorderFactory.createMyBorder("�bertragungsfunktion"));
		panel7.setPreferredSize(new Dimension(600, 200));
		panel7.setBorder(MyBorderFactory.createMyBorder("Statusbar"));
		jtpols.setEditable(false);
		jtpols.setFont(new Font("", Font.ITALIC, 18));
		jtpols.setSize(400, 400);
		jtpols.setText("-0.3+i 0.2" + "\n" + "-0.3-i 0.2" + "\n" + "-0.2+i 0.1" + "\n" + "-0.2-i 0.1");

		add(panel1, new GridBagConstraints(0, 0, 2, 2, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 5, 5), 0, 0));
		add(panel2, new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 5, 5), 0, 0));
		add(panel3, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 5, 5), 0, 0));
		add(panel4, new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 5, 5), 0, 0));
		add(panel5, new GridBagConstraints(3, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 5, 5), 0, 0));
		add(panel6, new GridBagConstraints(0, 4, 4, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 5, 5), 0, 0));
		

		panel4.add(filterPanel, new GridBagConstraints(0, 0, 3, 3, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 10, 10, 10), 0, 0));
		panel3.add(eingabePanel, new GridBagConstraints(0, 0, 3, 3, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
		panel1.add(schrittantwortPlot, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
		panel2.add(pnsEbene, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 10, 10, 10), 0, 0));
		panel5.add(polListe, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 10, 10, 10), 0, 0));
		panel6.add(uebertragungsfunktionsPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
		panel7.add(statusBar, new GridBagConstraints(0, 5, 4, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));

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

//		Model model = (Model) obs;
		System.out.println("TEST!!!!!!!!!!!!!!!!");
		schrittantwortPlot.update(obs, obj);
		pnsEbene.update(obs, obj);
		polListe.update(obs, obj);
		System.out.println("Update Topview");
		uebertragungsfunktionsPanel.update(obs, obj); 	// KS: �bertragungsfunktion
//		repaint();

	}



	public void setMenuBar(MenuBar menuBar) {
		this.menuBar=menuBar;
	}

}