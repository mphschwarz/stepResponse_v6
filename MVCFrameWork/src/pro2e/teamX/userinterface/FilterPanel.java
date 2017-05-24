package pro2e.teamX.userinterface;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class FilterPanel extends JPanel implements ActionListener {
	public JCheckBox keinfilter = new JCheckBox("Kein Filter");
	public JCheckBox smoothingfilter = new JCheckBox("Smoothing");
	public JCheckBox Tiefpassfilter = new JCheckBox("Tiefpassfilter");
	
	public JCheckBox cbRechteck = new JCheckBox("Rechteck");
	public JCheckBox cbGauss = new JCheckBox("Gauss");
	
	public JRadioButton rbKeinfilter 		= new JRadioButton("Kein Filter");
	public JRadioButton rbSmoothingfilter 	= new JRadioButton("Smoothing");
	public JRadioButton rbTiefpassfilter 	= new JRadioButton("Tiefpassfilter");
	
	public JRadioButton	rbRechteck 			= new JRadioButton("Rechteck");
	public JRadioButton rbGauss 			= new JRadioButton("Gauss");
	
	public JLabel lbgrenzfrequenz = new JLabel("Grenzfrequenz:");
	public JLabel lbAnzahlwerte = new JLabel("Anzahl Werte:");
	
	public JTextField tfGrenzfrequenz = new JTextField(30);
	public JTextField tfAnzahlwerte = new JTextField(30);
	
	public Box.Filler bfiller;


	public FilterPanel() {
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(400, 200));
		add(keinfilter, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(10, 10, 10, 10), 0, 0));
		add(smoothingfilter, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(10, 10, 10, 10), 0, 0));
		add(Tiefpassfilter, new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(10, 10, 10, 10), 0, 0));
		
		add(lbgrenzfrequenz, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 10, 10, 10), 0, 0));
		add(tfGrenzfrequenz, new GridBagConstraints(0, 3, 3, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));

		add(cbRechteck, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(10, 10, 10, 10), 0, 0));
		add(cbGauss, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(10, 10, 10, 10), 0, 0));

		add(lbAnzahlwerte, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		add(tfAnzahlwerte, new GridBagConstraints(0, 3, 3, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		
		add(Box.createVerticalGlue(), new GridBagConstraints(0, 4, 3, 0, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.VERTICAL, new Insets(10, 10, 10, 10), 0, 0));


		keinfilter.addActionListener(this);
		smoothingfilter.addActionListener(this);
		Tiefpassfilter.addActionListener(this);
		lbgrenzfrequenz.setVisible(false);
		tfGrenzfrequenz.setVisible(false);
		lbAnzahlwerte.setVisible(false);
		tfAnzahlwerte.setVisible(false);
		rbGauss.addActionListener(this);
		rbRechteck.addActionListener(this);
		rbGauss.setVisible(false);
		rbRechteck.setVisible(false);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == smoothingfilter) {

			keinfilter.setSelected(false);
			Tiefpassfilter.setSelected(false);
			cbRechteck.setVisible(true);
			cbGauss.setVisible(true);
			tfAnzahlwerte.setVisible(true);
			lbAnzahlwerte.setVisible(true);
			lbgrenzfrequenz.setVisible(false);
			tfGrenzfrequenz.setVisible(false);
			Box.createHorizontalGlue().setVisible(false);
			

		}
		if (e.getSource() == keinfilter) {

			keinfilter.setSelected(true);
			Tiefpassfilter.setSelected(false);
			cbRechteck.setVisible(false);
			cbGauss.setVisible(false);
			tfAnzahlwerte.setVisible(false);
			lbAnzahlwerte.setVisible(false);
			lbgrenzfrequenz.setVisible(false);
			tfGrenzfrequenz.setVisible(false);
			smoothingfilter.setSelected(false);

		}
		if (e.getSource() == Tiefpassfilter) {

			keinfilter.setSelected(false);
			smoothingfilter.setSelected(false);
			Tiefpassfilter.setSelected(true);
			tfGrenzfrequenz.setVisible(true);

			lbgrenzfrequenz.setVisible(true);
			cbGauss.setVisible(false);
			cbRechteck.setVisible(false);
			tfAnzahlwerte.setVisible(false);
			lbAnzahlwerte.setVisible(false);

		}
	}
}



//package pro2e.teamX.userinterface;
//
//import java.awt.Dimension;
//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
//import java.awt.Insets;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.Box;
//import javax.swing.JCheckBox;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JTextField;
//
//public class FilterPanel extends JPanel implements ActionListener {
//	public JCheckBox keinfilter = new JCheckBox("Kein Filter");
//	public JCheckBox smoothingfilter = new JCheckBox("Smoothing");
//	public JCheckBox Tiefpassfilter = new JCheckBox("Tiefpassfilter");
//	public JLabel lbgrenzfrequenz = new JLabel("Grenzfrequenz:");
//	public JTextField tfGrenzfrequenz = new JTextField(30);
//	public JLabel lbAnzahlwerte = new JLabel("Anzahl Werte:");
//	public JTextField tfAnzahlwerte = new JTextField(30);
//	public JCheckBox cbRechteck = new JCheckBox("Rechteck");
//	public JCheckBox cbGauss = new JCheckBox("Gauss");
//	public Box.Filler bfiller;
//	public Controller controller;
//
//
//	public FilterPanel(Controller controller) {
//		this.controller=controller;
//		this.setLayout(new GridBagLayout());
//		this.setPreferredSize(new Dimension(400, 200));
//		add(keinfilter, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
//				new Insets(10, 10, 10, 10), 0, 0));
//		add(smoothingfilter, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
//				new Insets(10, 10, 10, 10), 0, 0));
//		add(Tiefpassfilter, new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
//				new Insets(10, 10, 10, 10), 0, 0));
//		
//		add(lbgrenzfrequenz, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
//				new Insets(10, 10, 10, 10), 0, 0));
//		add(tfGrenzfrequenz, new GridBagConstraints(0, 3, 3, 1, 1.0, 1.0, GridBagConstraints.CENTER,
//				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
//
//		add(cbRechteck, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
//				new Insets(10, 10, 10, 10), 0, 0));
//		add(cbGauss, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
//				new Insets(10, 10, 10, 10), 0, 0));
//
//		add(lbAnzahlwerte, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
//				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
//		add(tfAnzahlwerte, new GridBagConstraints(0, 3, 3, 1, 1.0, 1.0, GridBagConstraints.CENTER,
//				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
//		
//		add(Box.createVerticalGlue(), new GridBagConstraints(0, 4, 3, 0, 1.0, 1.0, GridBagConstraints.CENTER,
//				GridBagConstraints.VERTICAL, new Insets(10, 10, 10, 10), 0, 0));
//
//
//		keinfilter.addActionListener(this);
//		smoothingfilter.addActionListener(this);
//		Tiefpassfilter.addActionListener(this);
//		lbgrenzfrequenz.setVisible(false);
//		tfGrenzfrequenz.setVisible(false);
//		lbAnzahlwerte.setVisible(false);
//		tfAnzahlwerte.setVisible(false);
//		cbGauss.addActionListener(this);
//		cbRechteck.addActionListener(this);
//		cbGauss.setVisible(false);
//		cbRechteck.setVisible(false);
//		
//	}
//
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		// TODO Auto-generated method stub
//		if (e.getSource() == smoothingfilter) {
//
//			keinfilter.setSelected(false);
//			Tiefpassfilter.setSelected(false);
//			cbRechteck.setVisible(true);
//			cbGauss.setVisible(true);
//			tfAnzahlwerte.setVisible(true);
//			lbAnzahlwerte.setVisible(true);
//			lbgrenzfrequenz.setVisible(false);
//			tfGrenzfrequenz.setVisible(false);
//			Box.createHorizontalGlue().setVisible(false);
//			
//
//		}
//		if (e.getSource() == keinfilter) {
//
//			keinfilter.setSelected(true);
//			Tiefpassfilter.setSelected(false);
//			cbRechteck.setVisible(false);
//			cbGauss.setVisible(false);
//			tfAnzahlwerte.setVisible(false);
//			lbAnzahlwerte.setVisible(false);
//			lbgrenzfrequenz.setVisible(false);
//			tfGrenzfrequenz.setVisible(false);
//			smoothingfilter.setSelected(false);
//
//		}
//		if (e.getSource() == Tiefpassfilter) {
//
//			keinfilter.setSelected(false);
//			smoothingfilter.setSelected(false);
//			Tiefpassfilter.setSelected(true);
//			tfGrenzfrequenz.setVisible(true);
//
//			lbgrenzfrequenz.setVisible(true);
//			cbGauss.setVisible(false);
//			cbRechteck.setVisible(false);
//			tfAnzahlwerte.setVisible(false);
//			lbAnzahlwerte.setVisible(false);
//
//		}
//	}
//}
