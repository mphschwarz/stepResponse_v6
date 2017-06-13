package pro2e.teamX.userinterface;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import pro2e.teamX.model.Model;

public class UebertragungsfunktionsPanel extends JPanel {
	JLabel jtUTF = new JLabel();
	/**
	 * Baut das Panel wo anschliessend die �bertragungsfunktion angezeigt wird.
	 */
public UebertragungsfunktionsPanel(){

	this.setLayout(new GridBagLayout());
	this.setBorder(MyBorderFactory.createMyBorder(" �bertragungsfunktion "));
    add(jtUTF, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
	
	
	}
/**
 * Setzt die �bertragungsfunktion zusammen durch die zwei mitgelieferten Strings.
 * @param z�hler
 * @param nenner
 */

public void setUTF(String z�hler, String nenner) {
	
	String utf = "H(s) = \\frac {"+z�hler+"} {"+nenner+"}";
	TeXFormula formula = new TeXFormula(utf);
	TeXIcon ti = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 24);		// Schriftgr�sse
	jtUTF.setIcon(ti);
	}


public void update(Observable obs, Object obj) {
	
	Model model = (Model) obs;
		if(model.gerechnet==false) {	
		}
		if(model.gerechnet==true){
			setUTF(model.z�hler, model.nenner);
		}
		
	}

}