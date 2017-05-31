package pro2e.teamX.userinterface;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

import pro2e.teamX.matlabfunctions.Filter;
import pro2e.teamX.matlabfunctions.FilterFactory;
import pro2e.teamX.matlabfunctions.MatlabFunktionen;
import pro2e.teamX.model.Model;

public class FilterPanel extends JPanel implements ActionListener {
	
	public JRadioButton rbKeinfilter 		= new JRadioButton("Kein Filter");
	public JRadioButton rbSmoothingfilter 	= new JRadioButton("Smoothing");
	public JRadioButton rbTiefpassfilter 	= new JRadioButton("Tiefpassfilter");
	
	public JRadioButton	rbRechteck 			= new JRadioButton("Rechteck");
	public JRadioButton rbGauss 			= new JRadioButton("Gauss");
	
	public JLabel lbgrenzfrequenz = new JLabel("Grenzfrequenz:");
	public JLabel lbAnzahlwerte = new JLabel("Anzahl Werte:");
	
//	public JFormattedTextField tfGrenzfrequenz = new JFormattedTextField(30);
	public static JIntegerTextField tfAnzahlwerte = new JIntegerTextField(30);
	
	public Box.Filler bfiller;
	
	public static final int RECHTECK = 0, GAUSS = 1;
	public static final int KEINFILTER = 0, SMOOTHING = 1, TIEFPASS = 2;
	
	

	public FilterPanel() {
		this.setLayout(new GridBagLayout());
		//this.setPreferredSize(new Dimension(400, 200));
		this.setBorder(MyBorderFactory.createMyBorder(" Filter "));
		
		add(rbKeinfilter, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(1, 1, 1, 1), 0, 0));
		add(rbSmoothingfilter, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(1, 1, 1, 1), 0, 0));
		//add(rbTiefpassfilter, new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
		//		new Insets(1, 1, 1, 1), 0, 0));
		
		add(lbgrenzfrequenz, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(1, 1, 1, 1), 0, 0));
//		add(tfGrenzfrequenz, new GridBagConstraints(1, 1, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER,
//				GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));
		add(lbAnzahlwerte, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));
		add(tfAnzahlwerte, new GridBagConstraints(1, 1, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));
		
		add(rbRechteck, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(1, 1, 1, 1), 0, 0));
		add(rbGauss, new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(1, 1, 1, 1), 0, 0));

		
		add(Box.createVerticalGlue(), new GridBagConstraints(0, 3, 3, 0, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.VERTICAL, new Insets(1, 1, 1, 1), 0, 0));


		rbKeinfilter.addActionListener(this);
		rbSmoothingfilter.addActionListener(this);
		rbTiefpassfilter.addActionListener(this);
		lbgrenzfrequenz.setVisible(false);
//		tfGrenzfrequenz.setVisible(false);
		lbAnzahlwerte.setVisible(false);
		tfAnzahlwerte.setVisible(false);
		rbGauss.addActionListener(this);
		rbRechteck.addActionListener(this);
		
		rbRechteck.setSelected(true);
		rbKeinfilter.setSelected(true);
		
		rbGauss.setVisible(false);
		rbRechteck.setVisible(false);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == rbSmoothingfilter) {

			rbKeinfilter.setSelected(false);
			rbTiefpassfilter.setSelected(false);
			rbRechteck.setVisible(true);
			rbGauss.setVisible(true);
			tfAnzahlwerte.setVisible(true);
			lbAnzahlwerte.setVisible(true);
			lbgrenzfrequenz.setVisible(false);
//			tfGrenzfrequenz.setVisible(false);
			Box.createHorizontalGlue().setVisible(false);
			MatlabFunktionen.setFiltertyp(SMOOTHING);
		}
		if (e.getSource() == rbKeinfilter) {
			
			rbKeinfilter.setSelected(true);
			rbTiefpassfilter.setSelected(false);
			rbRechteck.setVisible(false);
			rbGauss.setVisible(false);
			tfAnzahlwerte.setVisible(false);
			lbAnzahlwerte.setVisible(false);
			lbgrenzfrequenz.setVisible(false);
//			tfGrenzfrequenz.setVisible(false);
			rbSmoothingfilter.setSelected(false);
			MatlabFunktionen.setFiltertyp(KEINFILTER);
		}
		if (e.getSource() == rbTiefpassfilter) {
			rbKeinfilter.setSelected(false);
			rbSmoothingfilter.setSelected(false);
			rbTiefpassfilter.setSelected(true);
//			tfGrenzfrequenz.setVisible(true);
			lbgrenzfrequenz.setVisible(true);
			rbGauss.setVisible(false);
			rbRechteck.setVisible(false);
			tfAnzahlwerte.setVisible(false);
			lbAnzahlwerte.setVisible(false);
			MatlabFunktionen.setFiltertyp(TIEFPASS);
		}
		if (e.getSource() == rbGauss) {
			rbGauss.setSelected(true);
			rbRechteck.setSelected(false);
			MatlabFunktionen.setFenster(GAUSS); 	// 1 für GAUSS Globale Variabel geht nicht 
		}
		if (e.getSource() == rbRechteck) {
			rbGauss.setSelected(false);
			rbRechteck.setSelected(true);
			MatlabFunktionen.setFenster(RECHTECK); 	// 0 für RECHTECK Globale Variabel geht nicht 
		}
	}
}
