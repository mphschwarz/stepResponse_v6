package pro2e.teamX.userinterface;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Observable;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import pro2e.teamX.model.Model;

public class PolListe extends JPanel {
	public JTextArea jtpols = new JTextArea(5, 20);
	private double[] xWert = new double[10];
	private double[] yWert = new double[10];

	public void main(double[] reBestOrd, double[] imBestOrd) {
		for (int i = 0; i < reBestOrd.length; i++) {
			reBestOrd[i] = Math.round(100.0 * reBestOrd[i]) / 100.0;
			xWert[i] = reBestOrd[i];
			System.out.println(" Real " + reBestOrd[i]);
		}
		for (int i = 0; i < imBestOrd.length; i++) {
			imBestOrd[i] = Math.round(100.0 * imBestOrd[i]) / 100.0;
			yWert[i] = imBestOrd[i];
			System.out.println(" Imag " + imBestOrd[i]);
		}
		Test();
	}

	public PolListe() {
		this.setLayout(new GridLayout(1, 1));
		this.setPreferredSize(new Dimension(250, 300));
		Test();
	}

	public void Test() {
		jtpols.setBackground(getBackground());
		jtpols.setEditable(false);
		jtpols.setFont(getFont().deriveFont(14.0f));
		jtpols.setSize(400, 400);

		jtpols.setText(" " + xWert[0] + " + " + yWert[0] + " i\n" + " " + xWert[1] + " + " + yWert[1] + " i\n" + " " + xWert[2]
				+ " + " + yWert[2] + " i\n" + " " + xWert[3] + " + " + yWert[3] + " i\n" + " " + xWert[4] + " + " + yWert[4]
				+ " i\n" + " " + xWert[5] + " + " + yWert[5] + " i\n" + " " + xWert[6] + " + " + yWert[6] + " i\n" + " "
				+ xWert[7] + " + " + yWert[7] + " i\n" + " " + xWert[8] + " + " + yWert[8] + " i\n" + " " + xWert[9] + " + "
				+ yWert[9] + " i\n");
		add(jtpols, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 10, 10, 10), 0, 0));

	}

	public void update(Observable obs, Object obj) {
		Model model = (Model) obs;
		System.out.println("Test Pol Liste");
		//                              if(model.gerechnet==false)
		//                                            main(model.reBestOrd, model.imBestOrd);
		if (model.gerechnet == true) {
			main(model.reBestOrd, model.imBestOrd);
		for (int i = 0; i < model.reBestOrd.length; i++) {
			System.out.println(model.reBestOrd[i]);
		}}
	}
}
