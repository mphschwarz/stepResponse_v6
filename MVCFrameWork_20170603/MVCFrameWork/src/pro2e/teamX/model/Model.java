package pro2e.teamX.model;

import java.util.Observable;

import org.apache.commons.math3.complex.Complex;

import jdk.nashorn.internal.codegen.OptimisticTypesPersistence;
import pro2e.teamX.matlabfunctions.Filter;
import pro2e.teamX.matlabfunctions.FilterFactory;
import pro2e.teamX.matlabfunctions.Matlab;

import pro2e.teamX.matlabfunctions.MatlabFunktionen3;
import pro2e.teamX.matlabfunctions.SVTools;
import pro2e.teamX.userinterface.SchrittantwortPlot;
import pro2e.teamX.userinterface.StatusBar;
import pro2e.teamX.userinterface.TopView;

public class Model extends Observable {

	public double[] x0;
	public double[] t0;
	public double[] y0;
	public double[] t1 = null;
	public double[] x = null;
	public double[] xparse = null;
	public double[] yIst = null;
	public int tstart = 200;
	public int tend = 2000;//2000 21.05.2017
	public double[] ty = null;
	public int dOrd;
	public double[][] coeffDen = new double[10][11];
	public boolean gerechnet = false;
	public double[] reButterIniC = null;
	public double[] imButterIniC = null;
	public double[] reBestOrd = null;
	public double[] imBestOrd = null;
	public boolean poleBestOrd = false;
	public double[] reButter = null;
	public double[] imButter = null;

	public String z�hler;
	public String nenner;

	public double[] xhammer;
	public double[] thammer;
	public double[] yhammer;

	private int startOrd, endOrd;

	public int filter;
	public int fensterbreite;
	public double zd = 0.004;
	public int sf = 2;
	//private Filter filter;

	public Model() {

	}

	/**
	 * roundNumber rundet die Zahlen auf 2 Stellen nach dem Komma.
	 * @param x
	 * @param dp
	 * @return
	 */
	public static String roundNumber(double x, int dp) {

		double log10 = Math.log10(x);
		int count = (int) Math.floor(log10);
		x = x / Math.pow(10.0, count);
		x = Math.round(x * Math.pow(10.0, dp)) / Math.pow(10.0, dp);
		if (count == 0)
			return "" + x;
		else
			return "" + x + "E" + count;
	}

	/**
	 * -setParameter setzt die entsprechenden Parameter in die Attribute. Ruft die Methode berechneParseData auf.
	 * Ruft zus�tzlich notifyObservers auf.
	 * @param ordnungvon
	 * @param ordnungbis
	 * @param filter
	 * @param fensterbreite
	 */
	public void setParameter(int ordnungvon, int ordnungbis, int filter, int fensterbreite) {

		this.startOrd = ordnungvon;
		this.endOrd = ordnungbis;
		this.filter = filter;
		this.fensterbreite = fensterbreite;
		System.out.println("L�nge daten " + t0.length);

		berechneParseData();
		//		setPNSpoles(ordnungvon);
		System.out.println("Model.setParameter");
		notifyObservers();
	}

	/**
	 * Erzeugt mittels der Methode linspace einen Array der L�nge 50,rechnet den Cosinus und Sinus Wert an der Stelle i des Arrays.
	 * Setzt die entsprechenden werde im Realanteil des Butterworthfilter ein resp. im Imagin�ranteil des Butterworthfilters.
	 */
	public void setButterworth() {
		reButter = new double[50];
		imButter = new double[50];
		double[] linspace = Matlab.linspace(Math.PI / 2, -Math.PI / 2, 50);

		System.out.println("Test set Butterworth");
		for (int i = 0; i < linspace.length; i++) {
			reButter[i] = -Math.cos(linspace[i]);
			imButter[i] = Math.sin(linspace[i]);
		}
	}
/**
 * Ruft die Methode parseData von Matlabfunktionen auf und setzt die normierten Werte in die Attribute.
 * Anschliessend wird BerechneNormT() aufgerufen.
 */
	public void berechneParseData() {
		Object[] parseDataObj = MatlabFunktionen3.parseData(this.y0, this.t0, zd, sf, PREFERENZEN.tStart, tend);
		this.t1 = (double[]) parseDataObj[0];
		this.x = (double[]) parseDataObj[1];
		this.x0 = (double[]) parseDataObj[2];
		this.tstart = (int) parseDataObj[3];
		this.tend = (int) parseDataObj[4];
		//	for (int i = 0; i < x.length; i++) {
		//		System.out.println("xnachparse  "+x[i]+" vor   "+y0[i]);
		//	}
		berechneNormT();

	}

	/**
	 * Ruft die Methode normT von Matlabfunktionen auf und setzt den Normierten ZeitVektor in ty.
	 * Ruft die notifyObservers auf und leitet die Berechnung weiter an berechnungOptStep().
	 */
	public void berechneNormT() {
		Object[] normTobj = MatlabFunktionen3.normTneu(x, t1);
		this.ty = (double[]) normTobj[0];
		double T = (double) normTobj[1];
		StatusBar.showStatus("Signal gefilltert und ZeitVektor Normiert");

		//	System.out.println(T);
		//	for (int i = 0; i < ty.length; i++) {
		//		System.out.println("ty nach NormT    "+ty[i]+"    orginalvektor    "+t0[i]);	
		//}

		notifyObservers();
		berechnungOptStep();
	}
/**
 * berechnungOptStep erzeugt einen neuen Thread. Bereitet diverse Arrays vor und ruft mittels for Schleife die Methode OptStep() von Matlabfunktionen auf.
 * berechungOptStep ist das Herzst�ck der Software. Hier werden die Koeffizienten erzeugt und Weitergeleitet f�r die Optimierung. Anschliessend wird mittels kleinster Fehler die
 * beste Ordnung ermittelt und 
 * 
 * 
 */
	private void berechnungOptStep() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				double[] iter = Matlab.zeros(endOrd + 1);
				double[] fehler = new double[endOrd + 1];
				int[] td = new int[endOrd + 1];
				int[] d = new int[endOrd + 1];
				int pos = 0;
				double f�rMinimum = Double.POSITIVE_INFINITY;
				for (int i = 0; i < fehler.length; i++) {
					fehler[i] = Double.POSITIVE_INFINITY;
				}

				double[][] coeff = new double[endOrd + 1][endOrd + 2];

				for (int i = startOrd; i <= endOrd; i++) {

					Object[] optStepObj = MatlabFunktionen3.optStep(ty, x, i);

					if (PREFERENZEN.abbrechen) {
						return;
					}

					iter[i] = (double) optStepObj[2];
					fehler[i] = (double) optStepObj[1];
					double[] coefford = (double[]) optStepObj[0];
					System.out.println("Iter" + iter[i]);

					//-------------ShiftT
					Object[] shiftOpt = MatlabFunktionen3.shiftT(ty, x, MatlabFunktionen3.butterIniC(1, i, 10), i);
					for (int z = 0; z < shiftOpt.length; z++) {
						td[z] = (int) shiftOpt[0];
						System.out.println(td[z] + z);
					}
					for (int k = 0; k < shiftOpt.length; k++) {
						if (td[k] < 0) {
							d[k] = 1;
							System.out.println("ShiftTist Unter 1" + d[k]);
						} else {
							d[k] = td[k];
						}
						System.out.println("ShiftT" + d[k]);
					}
					//
					for (int j = 0; j < coefford.length; j++) {
						coeff[i][j] = coefford[j];
					}

				}
				for (int i = 0; i < fehler.length; i++) {
					if (fehler[i] < f�rMinimum) {
						pos = i + 1;
						f�rMinimum = fehler[i];
					}
				}
				dOrd = d[pos - 1];
				
				System.out.println("positionOrdung:   " + pos);
				System.out.println("dOrdnung abschneiden" + dOrd);
				double[] coeffBesteOrdung = new double[pos + 1];
				for (int j = 0; j < coeffBesteOrdung.length; j++) {
					coeffBesteOrdung[j] = coeff[pos - 1][j];
				}

				Object[] genfrag = MatlabFunktionen3.genFraq(coeffBesteOrdung, pos - 1);
				double[] num = (double[]) genfrag[0];
				double[] den = (double[]) genfrag[1];
				Object[] step_ist = SVTools.step(num, den, ty);
				yIst = (double[]) step_ist[0];

				//-----------------------------
				// �bertragungsfunktion

				StringBuilder sbnum = new StringBuilder();
				for (int i = 0; i < num.length; i++) {

					sbnum.append(roundNumber(num[i], 2)).append("");
				}

				System.out.println(sbnum.toString());
				z�hler = sbnum.toString();

				StringBuilder sbden = new StringBuilder();
				for (int i = 0, k = den.length; i < den.length; i++) {
					if (i < den.length - 1) {
						sbden.append(roundNumber(den[i], 2)).append("s^" + k-- + " + ");
					} else {
						sbden.append(roundNumber(den[i], 2)).append("s");
					}
				}

				System.out.println(sbden.toString());
				nenner = sbden.toString();

				//-----------------------------
				//Nullstellen berechen der besten Ordnung
				Complex[] nullstellenBestOrd = Matlab.roots(den);
				double[] real = new double[nullstellenBestOrd.length];
				double[] imag = new double[nullstellenBestOrd.length];
				for (int i = 0; i < imag.length; i++) {
					real[i] = nullstellenBestOrd[i].getReal();
					imag[i] = nullstellenBestOrd[i].getImaginary();
				}
				setPolesBestOrd(real, imag);

				timeSettings();
				//-----------------

				gerechnet = true;

				notifyObservers();
				
			}
		});
		thread.start();
	}
/**
 * Setzt den Real und Imagin�ranteil in den entsprechenden Attribut von der besten Ordnung.
 * @param real
 * @param imag
 */
	private void setPolesBestOrd(double[] real, double[] imag) {
		reBestOrd = new double[real.length];
		imBestOrd = new double[imag.length];
		for (int i = 0; i < imag.length; i++) {
			reBestOrd[i] = real[i];
			imBestOrd[i] = imag[i];
		}
		poleBestOrd = true;
	}
/**
 * timeSettings schneidet die nicht ben�tigten Anfangswerte ab.
 */
	private void timeSettings() {
		thammer = Matlab.colon(this.ty, dOrd, this.ty.length - 1);
		yhammer = Matlab.colon(this.yIst, dOrd, this.yIst.length - 1);
		xhammer = Matlab.colon(this.x, dOrd, this.x.length - 1);
		Matlab.print("Zeit", thammer);
		Matlab.print("stepIst", yIst);
		Matlab.print("Step_soll", x);

	}
/**
 * Setzt die Attribute nachdem sie in der Menubar mittels filereader eingelesen wurden. Ruft notifyObservers() auf.
 * @param t1
 * @param x1
 * @param y1
 */
	public void setEingabeData(double[] t1, double[] x1, double[] y1) {
		StatusBar.showStatus("Daten erfolgreich Importiert");
		StatusBar.showStatus("Ordnung ausw�hlen und berechen");
		this.t0 = new double[t1.length];
		this.x0 = new double[x1.length];
		this.y0 = new double[y1.length];

		this.t0 = t1;
		this.x0 = x1;
		this.y0 = y1;
		System.out.println("model.setEingabeData");

		notifyObservers();

	}
	public void filtern(int fensterbreite) {
		y0=MatlabFunktionen3.smoothing(y0, fensterbreite);
		notifyObservers();
	}
/**
 * Benachrichtet alle Beobachter.
 */
	@Override
	public void notifyObservers() {
		System.out.println("model.notifyObservers");
		setChanged();
		super.notifyObservers();
	}



}
