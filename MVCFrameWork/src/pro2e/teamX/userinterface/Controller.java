package pro2e.teamX.userinterface;

import java.util.StringTokenizer;

import pro2e.teamX.MVCFramework;
import pro2e.teamX.model.Model;
import pro2e.teamX.model.PREFERENZEN;

public class Controller {
	private Model model;
	private MVCFramework mvcFramework;
	TopView topView;
	public int startOrd = 1;
	public int endOrd = 10;
	public int filterTyp;
	public int fensterbreite;

	public Controller(Model model, MVCFramework mvcFramework) {
		this.model = model;
		this.mvcFramework = mvcFramework;

	}

	public void setView(TopView TopView) {
		this.topView = TopView;
	}

	public void setParameter() {
		//		int ordnungVon=1;
		//		int ordnungBis=10;
		//try {
		if (this.startOrd >= this.endOrd) {
			int temp;
			temp = startOrd;
			this.startOrd = endOrd;
			this.endOrd = temp;
		}
		try {
			model.tend = (int) (model.t0.length - 1.0 / 20.0 * model.t0.length);
			model.tstart = 100;
		} catch (Exception e) {
			model.tstart = (int) (1.0 / 20.0 * model.t0.length);
		}
		model.setParameter(startOrd, endOrd, 0, 0);
		//	} catch (NullPointerException e) {

		//	StatusBar.showStatus("Daten zuerst einlesen");
		//	}

		System.out.println("Test Controller");
		//	model.testFilter();

	}

//	public void setFminSearchEinstellungen(double relativfehler, double absolutFehler, double schrittLänge, int iterationen) {
//		PREFERENZEN.fminRelativ = relativfehler;
//		PREFERENZEN.fminAbsolut = absolutFehler;
//		PREFERENZEN.fminIter = iterationen;
//		PREFERENZEN.fminStep = schrittLänge;
//	}

	private double[] stringToCoeff(String s) {
		StringTokenizer tokenizer = new StringTokenizer(s, ", ");
		double[] res = new double[tokenizer.countTokens()];
		for (int i = 0; i < res.length; i++) {
			res[i] = Double.parseDouble(tokenizer.nextToken());
		}
		return res;
	}

	public void setOrdnungVon(int ns) {
		this.startOrd = ns + 1;
		//System.out.println("StartOrdnung"+startOrd);

	}

	public void setOrdnungBis(int ne) {
		this.endOrd = ne + 1;
		//System.out.println("EndOrdnung "+this.endOrd);
	}

}
