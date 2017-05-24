package pro2e.teamX.userinterface;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class UebertragungsfunktionsPanel extends JPanel {
	
	public JTextArea jtUTF=new JTextArea();
	
public UebertragungsfunktionsPanel(){
	this.setLayout(new GridLayout(1, 1));
	this.setPreferredSize(new Dimension(350, 225));
	jtUTF.setEditable(false);
	jtUTF.setFont(getFont().deriveFont(32.0f));
	jtUTF.setText("\n"+"\n"+"\n"+"\n"+"                                        1" + "\n" + "             H(s)=  ------------------------------" + "\n" + "                               3s^3+5s^2+s+3");
	add(jtUTF, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.CENTER,
			new Insets(10, 10, 10, 10), 0, 0));
	
	
	
}
}
