package pro2e.teamX.userinterface;

import java.awt.GridLayout;
import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import pro2e.teamX.model.Model;
import pro2e.teamX.model.PREFERENZEN;

public class UebertragungsfunktionsPanel extends JPanel {
	JLabel jtUTF = new JLabel();
	/**
	 * Baut das Panel wo anschliessend die Übertragungsfunktion angezeigt wird.
	 */
public UebertragungsfunktionsPanel(){

	this.setLayout(new GridLayout());
	this.setBorder(MyBorderFactory.createMyBorder(" Übertragungsfunktion "));

	System.out.println("KonstruktorUTF");
	add(jtUTF);
	}
/**
 * Setzt die Übertragungsfunktion zusammen durch die zwei mitgelieferten Strings.
 * @param zähler
 * @param nenner
 */

public void setUTF(String zähler, String nenner) {
	
	String utf = "H(s) = \\frac {"+zähler+"} {"+nenner+"}";
	TeXFormula formula = new TeXFormula(utf);
	TeXIcon ti = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 14);		// Schriftgrösse
	jtUTF.setIcon(ti);
	}


public void update(Observable obs, Object obj) {
	
	Model model = (Model) obs;
		if(model.gerechnet==false) {	
		}
		if(model.gerechnet==true){
			setUTF(model.zähler, model.nenner);
		}
		
	}

}