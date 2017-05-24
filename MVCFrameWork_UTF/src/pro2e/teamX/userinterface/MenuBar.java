package pro2e.teamX.userinterface;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import pro2e.teamX.model.Model;

public class MenuBar extends JMenuBar implements Observer, ActionListener {
	public Model model;
	private TopView view;
	JMenu menu, submenu;
	JMenuItem menuItemOnTop, submenuItem, showPlot;
	JFrame frame;
	Controller controller;
	private JMenuItem openItem, saveItem, exportItem, quitItem;
	private JFileChooser jfchooser = new JFileChooser(new File(".//"));
	//	private SchrittantwortPlot schrittantwortPlot = new SchrittantwortPlot();
	private double[][] zeilen = null;
	public double[] y1;
	public double[] t1;
	public double[] x1;
	private boolean flag = true;

	public MenuBar(Model model, Controller controller, TopView view, JFrame frame) {
		this.model = model;
		this.frame = frame;
		this.controller = controller;
		this.view = view;
		menu = new JMenu("Datei");
		menu.setMnemonic(KeyEvent.VK_D);

		menu.addSeparator();
		//		submenu = new JMenu("A submenu");
		//		submenu.setMnemonic(KeyEvent.VK_S);
		//		submenuItem = new JMenuItem("SubmenuItem");
		//		submenu.add(submenuItem);
		//		menu.add(submenu);

		openItem = new JMenuItem("Oeffnen", KeyEvent.VK_O);
		menu.add(openItem);
		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));
		openItem.addActionListener(this);

		menuItemOnTop = new JMenuItem("Immer im Vordergrund", KeyEvent.VK_T);
		menuItemOnTop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.ALT_MASK));
		menuItemOnTop.setActionCommand("OnTop");
		menuItemOnTop.addActionListener(this);
		menu.add(menuItemOnTop);

		JMenuItem menuItemResizable = new JMenuItem("Resizable", KeyEvent.VK_R);
		menuItemResizable.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
		menuItemResizable.setActionCommand("Resizable");
		menuItemResizable.addActionListener(this);
		menu.add(menuItemResizable);

		JMenuItem menuItemNotResizable = new JMenuItem("Not Resizable", KeyEvent.VK_N);
		menuItemNotResizable.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
		menuItemNotResizable.setActionCommand("NotResizable");
		menuItemNotResizable.addActionListener(this);
		menu.add(menuItemNotResizable);
		add(menu);

		showPlot = new JMenuItem("Plot anzeigen", KeyEvent.VK_H);
		showPlot.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.ALT_MASK));
		showPlot.setActionCommand("plotShow");
		showPlot.addActionListener(this);
		menu.add(showPlot);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == openItem) {
			int returnVal = jfchooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = jfchooser.getSelectedFile();
				System.out.println(file.getAbsolutePath());

				zeilen = csvread(file.getAbsolutePath());
				this.t1 = new double[zeilen.length];
				this.x1 = new double[zeilen.length];
				this.y1 = new double[zeilen.length];
				for (int i = 0; i < zeilen.length; i++) {

					this.t1[i] = zeilen[i][0];
					this.x1[i] = zeilen[i][1];
					this.y1[i] = zeilen[i][2];
					System.out.println("Zeit:" + t1[i] + " SPrung:   " + x1[i] + "Antwort   " + y1[i]);

				}
				model.gerechnet=false;
				model.setEingabeData(t1, x1, y1);
				//schrittantwortPlot.setData(t1, x1);

			}

			if (e.getActionCommand().equals("Resizable")) {
				frame.setResizable(true);
				Dimension dim = frame.getSize();
				dim.width -= 100;
				frame.setSize(dim);
			}
			if (e.getActionCommand().equals("NotResizable")) {
				frame.setResizable(false);
				Dimension dim = frame.getSize();
				dim.width += 100;
				frame.setSize(dim);
			}
			if (e.getActionCommand().equals("OnTop")) {
				StatusBar.showStatus(this, e, e.getActionCommand());
				if (((JFrame) this.getTopLevelAncestor()).isAlwaysOnTop()) {
					((JFrame) this.getTopLevelAncestor()).setAlwaysOnTop(false);
					menuItemOnTop.setText("Allwas on Top");
				} else {
					((JFrame) this.getTopLevelAncestor()).setAlwaysOnTop(true);
					menuItemOnTop.setText("Not allwas on Top");
				}

			}
		}
		if (e.getActionCommand().equals("plotShow")) {
			System.out.println("Teste2");
			if (flag) {
				flag = false;
				view.panel2.setVisible(false);
				view.panel3.setVisible(false);
				view.panel4.setVisible(false);
				view.panel5.setVisible(false);
				view.panel6.setVisible(false);
				view.panel7.setVisible(false);
			} else {
				flag = true;
				view.panel2.setVisible(true);
				view.panel3.setVisible(true);
				view.panel4.setVisible(true);
				view.panel5.setVisible(true);
				view.panel6.setVisible(true);
				view.panel7.setVisible(true);
			}
		}
	}

	private String[] leseDatei1(String absolutePath) {
		String[] str = null;
		try {
			// Anzahl Zeilen zählen:
			BufferedReader eingabeDatei = new BufferedReader(new FileReader(absolutePath));
			int cnt = 0;
			while (eingabeDatei.readLine() != null) {
				cnt++;
			}
			eingabeDatei.close();
			// Gezählte Anzahle Zeilen lesen:
			eingabeDatei = new BufferedReader(new FileReader(absolutePath));
			str = new String[cnt];
			for (int i = 0; i < str.length; i++) {
				str[i] = eingabeDatei.readLine();

			}
			eingabeDatei.close();

		} catch (IOException exc) {
			System.err.println("Dateifehler: " + exc.toString());

		}
		return str;
	}

	public static double[][] csvread(String dateiName) {
		double[][] data = null;
		int nLines = 0;
		int nColumns = 0;

		try {
			// Anzahl Zeilen und Anzahl Kolonnen festlegen:
			BufferedReader eingabeDatei = new BufferedReader(new FileReader(dateiName));
			String[] s = eingabeDatei.readLine().split("[, ]+");
			nColumns = s.length;
			while (eingabeDatei.readLine() != null) {
				nLines++;
			}
			eingabeDatei.close();

			// Gezählte Anzahl Zeilen und Kolonnen lesen:
			eingabeDatei = new BufferedReader(new FileReader(dateiName));
			data = new double[nLines][nColumns];
			for (int i = 0; i < data.length; i++) {
				s = eingabeDatei.readLine().split("[, ]+");
				for (int k = 0; k < s.length; k++) {
					data[i][k] = Double.parseDouble(s[k]);
				}
			}
			eingabeDatei.close();
		} catch (IOException exc) {
			System.err.println("Dateifehler: " + exc.toString());
		}

		return data;
	}

	@Override
	public void update(Observable obs, Object obj) {
		System.out.println("Test.Menubar");// TODO Auto-generated method stub

	}
}
