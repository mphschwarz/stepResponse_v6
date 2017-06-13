package pro2e.teamX.userinterface;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pro2e.teamX.model.PREFERENZEN;

public class EinstellungenFminSearch extends JDialog implements ActionListener {

	public JFormattedDoubleTextField tfrelativerFehler = new JFormattedDoubleTextField(30);
	public JFormattedDoubleTextField tfabsoluterFehler = new JFormattedDoubleTextField(30);
	public JFormattedDoubleTextField tfschrittlaenge = new JFormattedDoubleTextField(30);
	public JFormattedDoubleTextField tfshiftT = new JFormattedDoubleTextField(30);
	public JIntegerTextField tfiterationen = new JIntegerTextField(30);
	public JIntegerTextField tfStart = new JIntegerTextField(30);
	

	public JButton btübernhemen = new JButton("Übernehmen");
	public JButton btabbrechen = new JButton("Abbrechen");
	static JPanel panel = new JPanel();

	public MenuBar menubar;

	public boolean abbrechen = false;
	public boolean übernehmen = false;
	/**
 * EinstellungenFminSearch wird verwendet um die Einstellungen des fminSearchs zu Optimieren. In dieser Methode werden die Textfelder,
 * Beschriftungen, Buttons hinzugefügt und mit dem ActionListener verbunden.
 * @param frame
 * @param title
 */
	public EinstellungenFminSearch(JFrame frame, String title) {
		super(frame, title, true);
		setLayout(new GridBagLayout());

		panel.setBackground(getBackground());
		tfrelativerFehler.setText("" + PREFERENZEN.fminRelativ);
		tfabsoluterFehler.setText("" + PREFERENZEN.fminAbsolut);
		tfschrittlaenge.setText("" + PREFERENZEN.fminStep);
		tfiterationen.setText("" + PREFERENZEN.fminIter);
		tfshiftT.setText(""+PREFERENZEN.shiftT);
		tfStart.setText(""+PREFERENZEN.tStart);
		add(new JLabel("Relativer Fehler: "), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
		add(tfrelativerFehler, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));

		add(new JLabel("Absoluter Fehler: "), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
		add(tfabsoluterFehler, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));

		add(new JLabel("Schrittlänge step: "), new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
		add(tfschrittlaenge, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));

		add(new JLabel("Iterationen: "), new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
		add(tfiterationen, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		add(new JLabel("shiftT Einstellungen: "), new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
		add(tfshiftT, new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		add(new JLabel("Startwerte Abschneiden "), new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
		add(tfStart, new GridBagConstraints(1, 5, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));

		add(Box.createVerticalGlue(), new GridBagConstraints(0, 6, 1, 1, 0.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.VERTICAL, new Insets(10, 10, 10, 10), 0, 0));

		add(btübernhemen, new GridBagConstraints(0, 7, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(10, 10, 10, 10), 0, 0));
		add(btabbrechen, new GridBagConstraints(1, 7, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(10, 10, 10, 10), 0, 0));

		btübernhemen.addActionListener(this);
		btabbrechen.addActionListener(this);
		pack();
	}
/**
 * Beim Aufruf des Actionperformed werden die Werte aus den Textfeldern gelesen und in den PREFERENZEN Attribute geschrieben.
 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btabbrechen) {
			this.dispose();
		}

		if (e.getSource() == btübernhemen) {
			this.übernehmen = true;
			PREFERENZEN.fminRelativ = Double.parseDouble(tfrelativerFehler.getText());
			PREFERENZEN.fminAbsolut = Double.parseDouble(tfabsoluterFehler.getText());
			PREFERENZEN.fminStep = Double.parseDouble(tfschrittlaenge.getText());
			PREFERENZEN.fminIter = Integer.parseInt(tfiterationen.getText());
			PREFERENZEN.shiftT=Double.parseDouble(tfshiftT.getText());
			PREFERENZEN.tStart=Integer.parseInt(tfStart.getText());
			this.dispose();

		}
	}
}
