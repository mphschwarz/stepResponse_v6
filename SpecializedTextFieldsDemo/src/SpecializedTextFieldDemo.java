import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class SpecializedTextFieldDemo extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JDoubleTextField tfKS;
	private JEngineerField tfTU;
	private JEngineeringTextField tfTG;

	public SpecializedTextFieldDemo() {
		setLayout(new GridBagLayout());
		setBorder(MyBorderFactory.createMyBorder(" Schrittantwort vermessen "));

		add(new JLabel("Ks"), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));

		tfKS = new JDoubleTextField("", 20, true);
		tfKS.setToolTipText("JDoubleTextField");
		add(tfKS, new GridBagConstraints(1, 0, 3, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));

		add(new JLabel("s"), new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));

		add(new JLabel("Ku"), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));

		tfTU = new JEngineerField(3, 20, "E3");
		tfTU.addPopupMenu();
		tfTU.setToolTipText("JEngineerField");
		tfTU.addActionListener(this);
		add(tfTU, new GridBagConstraints(1, 1, 3, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));

		add(new JLabel("s"), new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));

		add(new JLabel("Tg"), new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));

		tfTG = new JEngineeringTextField(20);
		tfTG.setToolTipText("JEngineeringTextField");
		tfTG.addActionListener(this);
		add(tfTG, new GridBagConstraints(1, 2, 3, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));

		add(new JLabel("s"), new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));

		
		add(new JLabel("Die Klassen werden AS IS und ohne Garantie überlassen!"), new GridBagConstraints(0, 3, 4, 1, 0.0, 0.0,
				GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL,
				new Insets(10, 10, 10, 10), 0, 0));

		/*
		btClear = new JButton("Löschen");
		add(btClear, new GridBagConstraints(2, 3, 2, 1, 0.0, 0.0,
				GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL,
				new Insets(10, 10, 10, 10), 0, 0));
				*/

		// Didaktisch wertvoll:

		JLabel lbh = new JLabel(" ");
		lbh.setOpaque(true);
		lbh.setBackground(Color.blue);
		add(lbh, new GridBagConstraints(5, 1, 1, 1, 1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));

		JLabel lbv = new JLabel(" ");
		lbv.setOpaque(true);
		lbv.setBackground(Color.pink);
		add(lbv, new GridBagConstraints(4, 3, 6, 1, 0.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
				new Insets(1, 1, 1, 1), 0, 0));

		// Praktisch wertvoll:
		/*
		 * add(Box.createHorizontalGlue(), new GridBagConstraints(5, 1, 1, 3,
		 * 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
		 * new Insets(0, 0, 0, 0), 0, 0));
		 * 
		 * add(Box.createVerticalGlue(), new GridBagConstraints(4, 3, 6, 1, 0.0,
		 * 1.0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new
		 * Insets(0, 0, 0, 0), 0, 0));
		 */

	}

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				try {
					UIManager
							.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				JFrame frame = new JFrame();
				frame.setUndecorated(true);
				frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setTitle("TopView");
				frame.getContentPane().add(new SpecializedTextFieldDemo());
				frame.pack();
				frame.setMinimumSize(frame.getPreferredSize());
				frame.setVisible(true);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	System.out.println("Hallo");	
	}
}
