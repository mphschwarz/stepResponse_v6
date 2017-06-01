package pro2e.teamX.userinterface;

import java.awt.GridLayout;
import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import pro2e.teamX.model.Model;

public class UebertragungsfunktionsPanel extends JPanel {
	JLabel jtUTF = new JLabel();
	
public UebertragungsfunktionsPanel(){

	this.setLayout(new GridLayout());
	this.setBorder(MyBorderFactory.createMyBorder(" �bertragungsfunktion "));
//	this.setPreferredSize(new Dimension(350, 225));
	System.out.println("KonstruktorUTF");
	add(jtUTF);
	}


public void setUTF(String z�hler, String nenner) {
	System.out.println("setUTF");
	String utf = "H(s) = \\frac {"+z�hler+"} {"+nenner+"}";
	TeXFormula formula = new TeXFormula(utf);
	TeXIcon ti = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 25);		// Schriftgr�sse
	jtUTF.setIcon(ti);
	}


public void update(Observable obs, Object obj) {
	System.out.println("update Uebertragungsfunktion");
	Model model = (Model) obs;
	
		if(model.gerechnet==false) {
	//		String utf = " ";
		}
		if(model.gerechnet==true){
			setUTF(model.z�hler, model.nenner);
	
			System.out.println("ifBerechnetUTF");
		}
		
	}

}