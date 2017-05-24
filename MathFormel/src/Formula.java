import java.awt.HeadlessException;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

public class Formula extends JFrame {

	public Formula() throws HeadlessException {
		super();
		
		setTitle("Formula Demo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 580, 530);
		
	}
	
	public static void main(String[] args) {
		
		String z�hler = "1";
		String nenner = "3s^3 + 5s^2 + s + 3";
		Formula app = new Formula();
		
		String math = "H(s) = \\frac {"+z�hler+"} {"+nenner+"}";
		
		TeXFormula formula = new TeXFormula(math);
		TeXIcon ti = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 40);
		BufferedImage b = new BufferedImage(ti.getIconWidth(), ti.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		
		ti.paintIcon(new JLabel(), b.getGraphics(), 0, 0);
		
		JPanel mainPanel = new JPanel();
		JLabel fLabel = new JLabel();
		fLabel.setIcon(ti);
		mainPanel.add(fLabel);
		app.add(mainPanel);
		app.setVisible(true);
		app.pack();
	}
}

//https://www.youtube.com/watch?v=uTVe8JrgIg0