package pro2e.teamX.userinterface;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;

import pro2e.teamX.matlabfunctions.MatlabFunktionen3;;

public class FilterPanel extends JPanel implements ActionListener {

	public JRadioButton rbKeinfilter = new JRadioButton("Kein Filter");
	public JRadioButton rbSmoothingfilter = new JRadioButton("Smoothing");
	public JScrollBar jsbar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 20);

	public JRadioButton rbRechteck = new JRadioButton("Rechteckfenster");
	public JRadioButton rbGauss = new JRadioButton("Gaussfenster");

	public JLabel lbgrenzfrequenz = new JLabel("Grenzfrequenz:");
	public JLabel lbAnzahlwerte = new JLabel("Fensterbreite in ‰:");
	public Controller controller;

	public static JIntegerTextField tfAnzahlwerte = new JIntegerTextField(30);

	public Box.Filler bfiller;

	public static final int RECHTECK = 0, GAUSS = 1;
	public static final int KEINFILTER = 0, SMOOTHING = 1, TIEFPASS = 2;

	/**
	 * Das Panel wird gebaut und die einzelnen Komponenten darauf platziert.
	 * Alle Komponenten werden mit den ActionListener verbunden.
	 */
	public FilterPanel(Controller controller) {

		this.controller = controller;

		this.setLayout(new GridBagLayout());

		this.setBorder(MyBorderFactory.createMyBorder(" Smoothing Filter "));
		tfAnzahlwerte.setRange(0, 100);
		tfAnzahlwerte.setValue(1);
		add(rbKeinfilter, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(1, 1, 1, 1), 0, 0));
		add(rbSmoothingfilter, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(1, 1, 1, 1), 0, 0));

		add(lbgrenzfrequenz, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(1, 1, 1, 1), 0, 0));

		add(lbAnzahlwerte, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));
		add(tfAnzahlwerte, new GridBagConstraints(1, 1, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));

		add(rbRechteck, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(1, 1, 1, 1), 0, 0));
		add(rbGauss, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(1, 1, 1, 1), 0, 0));

		add(Box.createVerticalGlue(), new GridBagConstraints(0, 4, 3, 0, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.VERTICAL, new Insets(1, 1, 1, 1), 0, 0));

		rbKeinfilter.addActionListener(this);
		rbSmoothingfilter.addActionListener(this);
		rbSmoothingfilter.setEnabled(false);
		rbKeinfilter.setEnabled(false);
		lbgrenzfrequenz.setVisible(false);

		lbAnzahlwerte.setVisible(false);
		tfAnzahlwerte.setVisible(false);
		rbGauss.addActionListener(this);
		rbRechteck.addActionListener(this);
		rbRechteck.setSelected(true);
		rbKeinfilter.setSelected(true);

		rbGauss.setVisible(false);
		rbRechteck.setVisible(false);

	}

	public void setActionListener(ActionListener actionListener) {
		tfAnzahlwerte.addActionListener(actionListener);
	}

	/**
	 * Bei einem ActionEvent werden entweder die Checkboxen GAUSS ,RECHTECK und das Textfeld sichtbar und setzten den entsprechenen Fenstertyp in der Matlabfunktion setFenster().
	 * Wenn Anwenden gedrückt wird, wird die Methode filtern() mit der aus dem Textfeld ausgelesenen Wert aufgerufen.
	 */

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == rbSmoothingfilter) {

			rbSmoothingfilter.setVisible(true);
			rbKeinfilter.setSelected(false);
			rbRechteck.setVisible(true);
			rbGauss.setVisible(true);
			tfAnzahlwerte.setVisible(true);
			lbAnzahlwerte.setVisible(true);
			lbgrenzfrequenz.setVisible(false);

			Box.createHorizontalGlue().setVisible(false);
			MatlabFunktionen3.setFiltertyp(SMOOTHING);

			int anzahlWerte = Integer.parseInt(tfAnzahlwerte.getText());
			controller.filtern(anzahlWerte);
		}
		if (e.getSource() == rbKeinfilter) {

			rbKeinfilter.setSelected(true);

			rbRechteck.setVisible(false);
			rbGauss.setVisible(false);
			tfAnzahlwerte.setVisible(false);
			lbAnzahlwerte.setVisible(false);
			lbgrenzfrequenz.setVisible(false);
			rbSmoothingfilter.setSelected(false);
			MatlabFunktionen3.setFiltertyp(KEINFILTER);
			controller.unfiltern();
		}

		if (e.getSource() == rbGauss) {
			rbGauss.setSelected(true);
			rbRechteck.setSelected(false);
			MatlabFunktionen3.setFenster(GAUSS);

			int anzahlWerte = Integer.parseInt(tfAnzahlwerte.getText());
			controller.filtern(anzahlWerte);
		}
		if (e.getSource() == rbRechteck) {
			rbGauss.setSelected(false);
			rbRechteck.setSelected(true);
			MatlabFunktionen3.setFenster(RECHTECK);

			int anzahlWerte = Integer.parseInt(tfAnzahlwerte.getText());
			controller.filtern(anzahlWerte);
		}
	}
}
