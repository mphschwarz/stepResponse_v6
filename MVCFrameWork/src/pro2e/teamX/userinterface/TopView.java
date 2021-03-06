package pro2e.teamX.userinterface;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.util.Observable;
import java.util.Observer;
import java.util.StringTokenizer;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import pro2e.teamX.MVCFramework;
import pro2e.teamX.model.Model;

public class TopView extends JPanel implements Observer, ActionListener {
	private JButton btberechnen = new JButton("Berechnen");
	private SchrittantwortPlot schrittantwortPlot = new SchrittantwortPlot();
	private PNSEbene pnsEbene = new PNSEbene();
	public EingabePanel eingabePanel = new EingabePanel();
	public FilterPanel filterPanel = new FilterPanel();
	public SEbene sEbene=new SEbene();
	
	 Controller controller;
	
	public UebertragungsfunktionsPanel uebertragungsfunktionsPanel = new UebertragungsfunktionsPanel();
	private JProgressBar jPBar = new JProgressBar(0, 100);
	private JTextArea jtpols = new JTextArea(5, 20);
	
	

	private JMenuBar jMenuBar = new JMenuBar();
	private JMenu fileMenu, editMenu;

	private double[] heiri = { 0, 0.1, 0.3, 0.5, 0.7, 1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.55, 1.50, 1.45, 1.50, 1.52, 1.48, 1.51,
			1.49, 1.5 };
	private double[] hans = new double[heiri.length];

	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();
	private JPanel panel3 = new JPanel(new GridBagLayout());
	private JPanel panel4 = new JPanel();
	private JPanel panel5 = new JPanel();
	private JPanel panel6 = new JPanel();
	public boolean zustandfilter = true;

	protected JButton btOk = new JButton("OK");

	private double[] x0;

	public TopView(Controller controller) {
		super(new GridBagLayout());
		this.controller = controller;
		panel1.setPreferredSize(new Dimension(600, 400));
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
		panel6.setBorder(MyBorderFactory.createMyBorder("Übertragungsfunktion"));

		jtpols.setEditable(false);
		jtpols.setFont(new Font("", Font.ITALIC, 18));
		jtpols.setSize(400, 400);
		jtpols.setText("-0.3+i 0.2" + "\n" + "-0.3-i 0.2" + "\n" + "-0.2+i 0.1" + "\n" + "-0.2-i 0.1");

		add(panel1, new GridBagConstraints(0, 0, 2, 2, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 10, 10, 10), 0, 0));
		add(panel2, new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 10, 10, 10), 0, 0));
		add(panel3, new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 10, 10, 10), 0, 0));
		add(panel4, new GridBagConstraints(2, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 10, 10, 10), 0, 0));
		add(panel5, new GridBagConstraints(3, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 10, 10, 10), 0, 0));
		add(panel6, new GridBagConstraints(3, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 10, 10, 10), 0, 0));

		panel4.add(filterPanel, new GridBagConstraints(0, 0, 3, 3, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 10, 10, 10), 0, 0));
		panel3.add(eingabePanel, new GridBagConstraints(0, 0, 3, 3, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
		panel1.add(schrittantwortPlot, new GridBagConstraints(0, 0, 2, 2, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
		panel2.add(pnsEbene, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 10, 10, 10), 0, 0));
		
		panel5.add(jtpols, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 10, 10, 10), 0, 0));
		panel6.add(uebertragungsfunktionsPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
	
		eingabePanel.tfOrdnung.addActionListener(this);
		eingabePanel.tfOrdnungbis.addActionListener(this);
		eingabePanel.tfZeitNormierung.addActionListener(this);
		eingabePanel.tfStartwerte.addActionListener(this);
		eingabePanel.btberechen.addActionListener(this);
		eingabePanel.btAbbruch.addActionListener(this);


		for (int i = 0; i < heiri.length; i++) {
			hans[i] = i;
		}
		schrittantwortPlot.setData(hans, heiri, hans);

	}

	public void update(Observable obs, Object obj) {}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == eingabePanel.btberechen) {
try {
	int zahl=Integer.parseInt(eingabePanel.tfOrdnung.getText());
	
	
} catch (NumberFormatException e2) {
	eingabePanel.tfOrdnung.setText("1....10");
}
			System.out.println("Test Button");
			controller.setParameter();

		}
		

	}

	private double[] stringToCoeff(String s) {
		StringTokenizer tokenizer = new StringTokenizer(s, ", ");
		double[] res = new double[tokenizer.countTokens()];
		for (int i = 0; i < res.length; i++) {
			res[i] = Double.parseDouble(tokenizer.nextToken());
		}

		return res;
	}
	

	




}
