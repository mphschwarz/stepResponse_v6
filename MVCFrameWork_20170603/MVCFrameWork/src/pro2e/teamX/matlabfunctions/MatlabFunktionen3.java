
package pro2e.teamX.matlabfunctions;

import java.lang.reflect.Array;
import java.util.Arrays;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.exception.TooManyEvaluationsException;
import org.apache.commons.math3.optim.InitialGuess;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.NelderMeadSimplex;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.SimplexOptimizer;
import org.apache.commons.math3.util.FastMath;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import pro2e.teamX.model.PREFERENZEN;
import pro2e.teamX.userinterface.FilterPanel;
import pro2e.teamX.userinterface.StatusBar;

public class MatlabFunktionen3 {

	public double[][] c0 = new double[10][10];
	static double[] w;
	static double[] q;
	static double betrag;
	static double[] c;
	static double[] t;
	static double[] d = Matlab.ones(10);
	public static final int RECHTECK = 0, GAUSS = 1;
	private static int fensterform = RECHTECK;

	public static final int KEINFILTER = 0, SMOOTHING = 1, TIEFPASS = 2;
	private static int filtertyp = KEINFILTER;

	/**
	 * -Setzt den gewählten Fenstertyp (Filter)
	 * @param fenstertyp
	 */

	public static void setFenster(int fenstertyp) {
		if (fenstertyp == RECHTECK) {
			fensterform = RECHTECK;
		}
		if (fenstertyp == GAUSS) {
			fensterform = GAUSS;
		}
	}

	/**
	 * -Setzt mittels Zahl 0 "Kein Filter" und mit der Zahl 1 den "Smoothing Filter". 
	 * @param typ
	 */
	public static void setFiltertyp(int typ) {
		if (typ == KEINFILTER) {
			filtertyp = KEINFILTER;
		}
		if (typ == SMOOTHING) {
			filtertyp = SMOOTHING;
		}

	}

	/**
	 * -signalFiltern überprüft welcher Filtertyp aktiv ist und leitet den mitgelieferten ZeitVektor an der Untermethode smoothing weiter.
	 * @param zeitvektor
	 */
	public static void signalFiltern(double[] zeitvektor,int fensterbreite) {

		//int fensterbreite = Integer.parseInt(FilterPanel.tfAnzahlwerte.getText());// wert aus Textfeld
		if (filtertyp == KEINFILTER) {
		}
		if (filtertyp == SMOOTHING) {
			smoothing(zeitvektor, fensterbreite);
		}
	}

	/**
	 * -ShiftT ermöglicht die automatische Erkennung des Anfangs der Schrittantwort. Es wird dazu den Daten-vektor mit der Schrittantwort 
	 *  eines Butterworthfilters verglichen. Die Schrittantwort des Butterworth-filters wird dazu gestreckt, und soweit nach rechts verschoben, 
	 *  dass die 50%-Punkte und Peak-Punkte zeitlich übereinstimmen. Die Anzahl Samples um die verschoben wurden wird dann zurückgegeben.
	 * @param t
	 * @param y
	 * @param c
	 * @param n
	 * @return
	 */

	public static Object[] shiftT(double[] t, double[] y, double[] c, int n) {
		double[] tx = Matlab.linspace(0, t.length - 1, t.length);
		Object[] numden = genFraq(c, n);
		double[] num = (double[]) numden[0];
		double[] den = (double[]) numden[1];
		Object[] x = SVTools.step(num, den, tx);
		double[] h = (double[]) x[0];

		int ix5 = 0;
		int iy5 = 0;

		while ((h[ix5] < 0.5 && h[ix5 + 1] >= 0.5) == false) {
			ix5 = ix5 + 1;
		}
		Object[] gm = findpeaks(h);
		try {
			int[] ixmm = (int[]) gm[1];
			int ixmmm = ixmm[0];
			for (int i = 0; i < tx.length; i++) {
				tx[i] = tx[i] / (tx[ixmmm] - tx[ix5]);
			}

			double tx5 = tx[ix5];

			while (y[iy5] < 0.5 && y[iy5 + 1] >= 0.5 == false) {
				iy5 = iy5 + 1;
			}

			Object[] fp2 = findpeaks(Matlab.colon(y, iy5, y.length - 1));
			int[] iymmg = (int[]) fp2[1];
			int iymm = iymmg[0];
			if (iymm == 0) {
				iymm = y.length;
			} else {
				iymm = iymm + iy5;
			}
			double[] ty = new double[y.length];
			for (int i = 0; i < ty.length; i++) {
				ty[i] = t[i] / (t[iymm] - t[iy5]);
			}
			double ty5 = ty[ix5];
			double dt = ty5 - tx5;
			double di = -(dt / (t[0] - t[1]));

			return new Object[] { (int) (-di * PREFERENZEN.shiftT), dt };

		} catch (Exception e) {
			int di = 0;
			int dt = 0;
			return new Object[] { di, dt };
		}

	}

	/**
	 * NormT normiert den Zeitvektor, sodass die Zeit zwischen dem 50%-Punkt und dem Peak-Punkt eins ist. 
	 * Dies ist für eine sinnvolle Verteilung der Polstellen nötig und ermöglicht eine automatische Erkennung des Anfangs der Schrittantwort. 
	 * Er setzt zusätzlich die 2 Preferenzen parsaDataTempEnd und parsaDataix5.
	 * diese zwei Preferenzzahlen werden benötigt um zurück zu normieren auf die richtige Zeitskalierung.
	 * @param y
	 * @param t
	 * @return
	 */
	public static Object[] normTneu(double[] y, double[] t) {
//		int iy5 = 1;
		int iy5 = 0;	// Array fängt bei null an nicht wie bei Matlab bei 1. 20170604, KS

		while (y[iy5] < 0.5 && y[(iy5 + 1)] > 0.5 == false) {
			iy5 = iy5 + 1;
		}
		Object[] fp2 = findpeaks(Matlab.colon(y, (int) iy5, y.length - 1));

		double[] ty = new double[y.length];
		try {
			int[] iymmg = (int[]) fp2[1];
			int iymm = iymmg[0];
			if (iymm == 0) {
				iymm = y.length - 1;
			} else {
				iymm = (int) (iymm + iy5);
			}
			PREFERENZEN.parseDataTempEnd = t[iymm];
			PREFERENZEN.parseDataTempix5 = t[iy5];
			for (int i = 0; i < ty.length; i++) {
				ty[i] = t[i] / (t[iymm] - t[(int) iy5]);
			}
			double T = ty[iymm] - t[iy5];
			return new Object[] { ty, T };

		} catch (Exception e) {
			double T = 0.0;
			return new Object[] { t, T };
		}
	}

	/**
	 * -Die Smoothing Filter glättet das mit gesendete Zeitvektor mit der eingegebenen Fensterbreite.
	 * @param zeitvektor
	 * @param fensterbreite
	 * @return
	 */

	public static double[] smoothing(double[] zeitvektor, int fensterbreite) {

		int fbkorr = 6;
		double[] fenster = new double[fensterbreite * fbkorr + 1];
		double[] gefaltet = new double[fensterbreite * fbkorr + 1 + zeitvektor.length];
		double[] Resultat = new double[zeitvektor.length];

		if (fensterform == RECHTECK) {

			for (int i = fensterbreite * fbkorr / 2 - fensterbreite / 2; i <= (fensterbreite * fbkorr / 2
					+ fensterbreite / 2); i++) {
				fenster[i] = 1.0 / (fensterbreite);
			}
		} else if (fensterform == GAUSS) { // Gaussfunktion muss noch geschrieben werden. :) 
			for (int i = 0; i <= fensterbreite * fbkorr; i++) {

				fenster[i] = 1 / ((double) fensterbreite * Math.sqrt(2.0 * Math.PI)) * Math.exp(
						-0.5 * Math.pow((i - (double) fensterbreite * (double) fbkorr / 2.0) / (double) fensterbreite, 2.0)); //Gaussfunktion erzeugen
			}
		}
		gefaltet = Matlab.conv(zeitvektor, fenster);
		for (int i = 0; i < zeitvektor.length; i++) {
			Resultat[i] = gefaltet[i + (fensterbreite * fbkorr) / 2];
		}
		return Resultat;
	}

	/**
	 * mean Rechnet den Mittelwert des doubleArrays v und gibt anschliessend ein double zurück.
	 */
	public static double mean(double[] v) {
		double m = 0;
		for (int i = 0; i < v.length; i++) {
			m = m + v[i];
		}
		m = m / v.length;
		return m;
	}

	/**
	 * Die error Methode wird benötigt um den Fehler zwischen den Funktionen zu ermitteln. Dies wird mit den Koeffizienten c, den Normierten Zeitvektor t,
	 *  - die gegebene Funktion x und die Ordnung. error übergibt diese Werte an der Methode stepResponse wo später ausführlicher erklärt wird.
	 *    Anschliessend berechnet Error die Summe der Fehlerquadrate von Ist- und Soll-Vektoren.
	 * @param c
	 * @param t
	 * @param x
	 * @param n
	 * @return
	 */
	public static final double error1(double[] c, double[] t, double[] x, int n) {
		double r = 0;
		double[] y = stepResponse(c, t, n);
		for (int i = 0; i < x.length; i++) {

			r = r + Math.pow((y[i] - x[i]), 2);
		}
		return r;

	}

	/**Die Methode butterIniC generiert sinnvolle Startwerte für die Übertragungsfunktion, wobei von einem Butterworthfilter ausgegangen wird.
	ButterIniC generiert einen Vektor in Omega-Q-Form, der den Polen eines Butterworthfilters entspricht.
	
	 * 
	 * @param k
	 * @param n
	 * @param N
	 * @return
	 */
	public static double[] butterIniC(double k, int n, int N) {
		double[] c = new double[N];
		double[] a = new double[n];
		double[] re = new double[n];
		double[] im = new double[n];
		double[] lin = Matlab.linspace(0, n - 1, n);
		if (n % 2 == 0) {

			for (int i = 0; i < n; i++) {
				a[i] = Math.PI / 2 + Math.PI / 2 / n + Math.PI / n * lin[i];
				re[i] = Math.cos(a[i]);
			}
			for (int i = 0; i < n; i++) {
				a[i] = Math.PI / 2 + Math.PI / 2 / n + Math.PI / n * lin[i];
				im[i] = Math.sin(a[i]);
				;
			}

			for (int i = 0; i < a.length; i++) {
				a[i] = re[i];
			}
			for (int i = 0; i < n / 2; i = i + 1) {
				re[i * 2] = a[i];
				re[i * 2 + 1] = a[i];
			}
			for (int i = 0; i < a.length; i++) {
				a[i] = im[i];
			}
			for (int i = 0; i < n / 2; i++) {
				im[i * 2] = a[i];
				im[i * 2 + 1] = -a[i];
			}


			c = reimtowq(re, im, k, N);
		} else {

			for (int i = 0; i < n - 1; i++) {
				a[i] = Math.PI / 2 + Math.PI / 2 / n + Math.PI / n * lin[i];
				re[i] = Math.cos(a[i]);
			}
			for (int i = 0; i < n - 1; i++) {
				a[i] = Math.PI / 2 + Math.PI / 2 / n + Math.PI / n * lin[i];
				im[i] = Math.sin(a[i]);
				;
			}

			for (int i = 0; i < a.length; i++) {
				a[i] = re[i];
			}
			for (int i = 0; i < n / 2; i = i + 1) {
				re[i * 2] = a[i];
				re[i * 2 + 1] = a[i];
			}
			for (int i = 0; i < a.length; i++) {
				a[i] = im[i];
			}
			for (int i = 0; i < n / 2; i++) {
				im[i * 2] = a[i];
				im[i * 2 + 1] = -a[i];
			}

			
			re[re.length - 1] = -1;

			c = reimtowq(re, im, k, N);
		
		}
		return c;
	}

	/**
	 * stepResponse übergibt die Koeffizienten c und die Ordnung n an der Methode genFrag wo aus den Omega -Q Vektor und der Ordnung den Zähler und Nenner des 
	 * Polynoms zurück gibt. Anschliessend wird mittels der Methode Step von SVTOOLS die Schrittantwort berechnet und zurückgegeben.
	 * @param c
	 * @param t
	 * @param n
	 * @return
	 */
	public static double[] stepResponse(double[] c, double[] t, int n) {
		Object[] genFragobject = genFraq(c, n);
		double[] num = (double[]) genFragobject[0];
		double[] den = (double[]) genFragobject[1];
		Object[] steped = SVTools.step(num, den, t);
		double[] h = (double[]) steped[0];
		return h;
	}

	/**
	 * genFrag wird benötigt um von der Omega -Q Form in Zähler und Nenner umzurechnen. 
	 * genFrag übergibt anschliessend als Object Verpackt den Zähler und Nenner.
	 */
	public static Object[] genFraq(double[] c, int n) {

		double[] num = { c[0] };
		double[] den = new double[n + 1];
		den[0] = 1;
		boolean even = true;
		if (n % 2 == 1) {

			den = Matlab.conv(new double[] { 1, Math.abs(c[n]) }, den);
			n = n - 1;
			even = false;
		}
		for (int i = 1; i < n; i = i + 2) {
			num[0] = num[0] * Math.pow(c[i], 2);
			den = Matlab.conv(new double[] { 1, c[i] / c[i + 1], Math.pow(c[i], 2) }, den);
		}
		if (even == true)
			den = Matlab.colon(den, 0, n);
		else
			den = Matlab.colon(den, 0, n + 1);

		return new Object[] { num, den };
	}

	/**
	 * optStep erzeugt mittels Ordnung n einen butterIniC und bereitet den Schrittlängen Array für den NelderMeadVerfahren vor,
	 * die mittels den eingegebenen Daten im Menu Einstellungenfminsearch gespeichert wurden. 
	 * Anschliessend wird die unter methode fminSearch aufgerufen.
	 * @param normt
	 * @param normy
	 * @param n
	 * @return
	 */
	public static Object[] optStep(double[] normt, double[] normy, int n) {

		Object[] g = null;
		double[] c = butterIniC(1, n, 11);
		double[] steps = new double[n + 1];

		for (int i = 0; i < steps.length; i++) {
			steps[i] = PREFERENZEN.fminStep;
		}
		g = fminSearch(normt, normy, c, steps, n);

		return g;

	}
/**
 * fminSearch erzeugt einen neuen SimplexOptimizer mit den Referenzen, die Im Menu(EinstellungenfminSearch) hinterleid sind. Zusätzlich wird ein Terget erzeugt 
 * und die normierten Daten normy,normt und die Ordnung mitgegeben. Anschliessend wird im Try Catch Block eine Unterfunktion vom Optimizer aufgerufen mit der Fehlerfunktion, den Startwerten,
 * die Schrittlänge und den Iterationenen Maximum. iM Catch werden die optimierten Koeffizienten der Fehlerfunktion gesichert. Dies aus dem Grund,dass nach dem 
 * überschreiten des Iterationsmaximums eine Exeption geworfen wird. so gehen die Optimierten Koeffizienten nicht verloren und die nächste Ordnung kann berechnend werden.
 * fminSearch gibt die optimierten Koeffizeineten in Omega Q Form zurück, den Fehler und die Iterationen.
 * @param normt
 * @param normy
 * @param startwerte
 * @param steps
 * @param r
 * @return
 */
	public static Object[] fminSearch(double[] normt, double[] normy, double[] startwerte, double[] steps, int r) {
		SimplexOptimizer optimizer = new SimplexOptimizer(PREFERENZEN.fminRelativ, PREFERENZEN.fminAbsolut);//1e-6, 1e-12 //19.05 1e-13, 1e-16
		Target target = new Target(normy, normt, r);
		PointValuePair optimum = null;
		double[] coeffs = null;
		double iter = 0.0;
		double fehler = Double.POSITIVE_INFINITY;

		try {

			for (int i = 0; (i < 20) && !PREFERENZEN.abbrechen; i++) {

				optimum = optimizer.optimize(new MaxEval((int) PREFERENZEN.fminIter / 5), new ObjectiveFunction(target),
						GoalType.MINIMIZE, //500 Iterationen laut Prof. Dr. Gut
						new InitialGuess(startwerte), new NelderMeadSimplex(steps));

				coeffs = optimum.getPoint();
				startwerte = coeffs;
				iter = optimizer.getIterations();
				fehler = optimum.getValue();
			}

		} catch (TooManyEvaluationsException e) {
			coeffs = target.coeff;
			fehler = target.s;
			iter = target.iter;
			StatusBar.showStatus("Iterationen Maximum erreicht");
		}

		return new Object[] { coeffs, fehler, iter };
	}
/**
 * Target ist die Fehlerfunktion des SimplexOptimizers. Sie wird benötigt um die Koeffizienten zu optimieren.
 * Die wichtigste Komponente im Target ist das Value, die wird jedes mal vom Optimizer aufgerufen um die Koeffizienten zu Optimieren.
 * 
 * @author Zogg
 *
 */
	public static class Target implements MultivariateFunction {// Fehlerfunktion 
		double[] normt;
		double[] normy;
		int r;
		double[] coeff;
		double s;
		int iter = 0;

		public Target() {}

		public Target(double[] normy, double[] normt, int r) {
			this.normy = normy;
			this.normt = normt;
			this.r = r;
		}

		public double value(double[] c) {

			final double[] coeffs = c;
			this.iter++;
			double s = error1(c, normt, normy, r);

			this.coeff = coeffs;
			this.s = s;
			System.out.println("Fehlersumme" + s);
			return s;
		}
	}
	
/**
 * normTzurück wird benötigt, um den normierten ZeitVektor zurück zu normieren auf die Ursprüngliche Grösse.
 * @param t
 * @return
 */
	public static double[] NormTZurück(double[] t) {
		double[] tNormBack = new double[t.length];
		for (int i = 0; i < t.length; i++) {
			tNormBack[i] = t[i] * (PREFERENZEN.parseDataTempEnd - PREFERENZEN.parseDataTempix5);
		}

		return tNormBack;

	}
/**
 * wqtoReIm ist eine Hilfsmethode die den c Array von der Form Omega-Q in Real und Imaginär Anteil umwandelt.
 * @param c
 * @param n
 * @return
 */
	public static double[] wqtoReIm(double[] c, int n) {
		double k = c[0];
		double[] re;
		double[] im;

		if (n % 2 == 0) {
			double[] w = new double[n / 2];
			double[] q = new double[n / 2];
			re = new double[n];
			im = new double[n];

			for (int i = 0; i < n / 2; i++) {
				if (i == 0)
					w[i] = c[1];
				else
					w[i] = c[i * 2 + 1];
				
			}
			for (int i = 0; i < n / 2; i++) {
				if (i == 0)
					q[i] = c[2];
				else
					q[i] = c[i * 2 + 2];
				
			}
			for (int i = 0; i < n / 2; i++) {
				re[i] = -w[i] / (2 * q[i]);
			
			}
			for (int i = 0; i < n / 2; i++) {
				im[i] = w[i] / (2 * q[i]) * Math.sqrt(4 * Math.pow(q[i], 2) - 1);
				
			}
			double[] re2 = new double[re.length];
			for (int i = 0; i < re2.length; i++) {
				re2[i] = re[i];
			}

			for (int i = 0; i < n / 2; i = i + 1) {
				re[i * 2] = re2[i];
				re[i * 2 + 1] = re2[i];
			}
			for (int i = 0; i < re2.length; i++) {
				re2[i] = im[i];
			}
			for (int i = 0; i < n / 2; i++) {
				im[i * 2] = re2[i];
				im[i * 2 + 1] = -re2[i];
				
			}
		} else {
			double[] w = new double[n / 2];
			double[] q = new double[n / 2];
			re = new double[n];
			im = new double[n];

			for (int i = 0; i < n / 2; i++) {
				if (i == 0)
					w[i] = c[1];
				else
					w[i] = c[i * 2 + 1];
			
			}
			for (int i = 0; i < n / 2; i++) {
				if (i == 0)
					q[i] = c[2];
				else
					q[i] = c[i * 2 + 2];
			
			}
			for (int i = 0; i < n / 2; i++) {
				re[i] = -w[i] / (2 * q[i]);
			
			}
			for (int i = 0; i < n / 2; i++) {
				im[i] = w[i] / (2 * q[i]) * Math.sqrt(4 * Math.pow(q[i], 2) - 1);
				
			}
			double[] re2 = new double[re.length];
			for (int i = 0; i < re2.length; i++) {
				re2[i] = re[i];
			}
			for (int i = 0; i < n / 2; i = i + 1) {
				re[i * 2] = re2[i];
				re[i * 2 + 1] = re2[i];
			}

			for (int i = 0; i < re2.length; i++) {
				re2[i] = im[i];
			}
			for (int i = 0; i < n / 2; i++) {
				im[i * 2] = re2[i];
				im[i * 2 + 1] = -re2[i];
			}

			re[re.length - 1] = c[n]; // War ein Minus 11.05.17 17.48
			im[im.length - 1] = 0;

		}
		double[] rueckgabe = new double[re.length + im.length + 1];
		for (int i = 0; i < re.length; i++) {
			rueckgabe[i] = re[i];
		}
		for (int i = re.length; i < re.length + im.length; i++) {
			rueckgabe[i] = im[i - re.length];
		}
		rueckgabe[rueckgabe.length - 1] = k;
		return rueckgabe;
	}
/**
 * reimtowq wandelt die von Real und Imaginäranteil in Omega -Q Form um.
 * @param re
 * @param im
 * @param k
 * @param N
 * @return
 */
	public static double[] reimtowq(double[] re, double[] im, double k, int N) {
		double[] c = new double[11]; 
		if (im.length == 1) {
			int temp = 1;
			int e = -1;
			double[] cd = { temp, e };
			return cd;
		} else {
		}
		if (re.length % 2 == 0) {

			double[] real = new double[re.length / 2];
			double[] imag = new double[re.length / 2];
			double[] w = new double[re.length / 2];
			double[] q = new double[re.length / 2];
			c = new double[re.length + 1];
			for (int i = 0; i < re.length / 2; i++) {
				real[i] = re[i * 2];
			}
			imag[0] = im[1];
			for (int i = 1; i < im.length / 2; i++) {
				imag[i] = im[i * 2 + 1];
			}
			for (int i = 0; i < real.length; i++) {
				w[i] = Math.sqrt(Math.pow(real[i], 2) + Math.pow(imag[i], 2));
			}
			for (int i = 0; i < w.length; i++) {
				q[i] = -w[i] / (2 * real[i]);	// Minus fehlte & Math.abs entfernt gemäss MatLab / 01.06.2017, KS
//				q[i] = w[i] / (2 * Math.abs(real[i]));
			}
			c[0] = k;
			for (int i = 1; i <= w.length * 2; i++) {
				if (i % 2 == 0) {
					c[i] = q[(i / 2) - 1];
				} else {
					c[i] = w[(i / 2)];
				}
			}
		} else {

			double[] real = new double[re.length / 2];
			double[] imag = new double[re.length / 2];
			double[] w = new double[re.length / 2];
			double[] q = new double[re.length / 2];
			c = new double[re.length / 2 * 2 + 2];
			for (int i = 0; i < re.length / 2; i++) {
				real[i] = re[i * 2];
			}

			imag[0] = im[1];

			for (int i = 1; i < im.length / 2; i++) {
				imag[i] = im[i * 2 + 1];
			}
			for (int i = 0; i < real.length; i++) {
				w[i] = Math.sqrt(Math.pow(real[i], 2) + Math.pow(imag[i], 2));
			}
			for (int i = 0; i < w.length; i++) {
				q[i] = -w[i] / (2 * real[i]);	// Minus fehlte & Math.abs entfernt gemäss MatLab / 01.06.2017, KS
//				q[i] = w[i] / (2 * Math.abs(real[i]));
			}
			c[0] = k;
			for (int i = 1; i <= w.length * 2; i++) {
				if (i % 2 == 0) {
					c[i] = q[(i / 2) - 1];
				} else {
					c[i] = w[(i / 2)];
				}

			}
			c[c.length - 1] = re[re.length - 1];
		}
		return c;
	}

/**
 * findpeaks sucht im Daten Array die Maximum stellen und gibt den jeweiligen Wert und Position im Array zurück. Wird von shiftT und NormT benötigt um den ZeitVektor zu
 * normieren oder den Maximum des Schritts zu bestimmen.
 * @param x0
 * @return
 */
	public static Object[] findpeaks(double[] x0) {
		int[] p = new int[x0.length];
		double[] x = new double[x0.length];
		int k = 0;
		for (int i = 1; i < x.length - 1; i++) {		// KS, 04.06.2017
//		for (int i = 1; i < x.length - 2; i++) {	
			if (x0[i + 1] == x0[i]) {
				int m = 0;
				for (int j = i; x0[j + 1] == x0[j] && j + 2 < x0.length; j++) {
					if (x0[j + 2] < x0[j + 1] && x0[i - 1] < x0[i]) {
						x[k] = x0[i];
						p[k] = i;
						k++;
					}
					m = j + 1;
				}
				i = m;
			}
			if (x0[i - 1] < x0[i] && x0[i + 1] < x0[i]) {
				x[k] = x0[i];
				p[k] = i;
				k++;
			}
		}
		x = Matlab.colon(x, 0, k - 1);
		p = Matlab.colon(p, 0, k - 1);
		return new Object[] { x, p };
	}

/**
 * Liest Daten- und Zeitvektor ein und schneidet unnötige Daten weg.
ParseData liest die Soll-Vektoren für Daten und Zeit ein. Es kann dabei vom Nutzer bestimmte Anfangs- und Endpunkte berücksichtigt werden.

 */
	public static Object[] parseData(double[] y, double[] t, double ep, int f, int tstart, int tend) {
		double ystart = 0;
		double yend = 0;
		double ytemp = 0;
		double[] x0 = y;
		double[] t0 = null;
		double[] y0 = null;
		double[] temp = null;

		y = smoothing(y, f);
		if (y.length >= 40) {
			ystart = mean(Matlab.colon(y, 0, Math.floorDiv(y.length, 20) - 1));
			yend = mean(Matlab.colon(y, y.length - Math.floorDiv(y.length, 20), y.length - 1));
		} else {
			ystart = mean(Matlab.colon(y, 0, 1));
			yend = mean(Matlab.colon(y, y.length - 2, y.length - 1));
		}
		if (tstart <= 0) {
			tstart = 1;
			while (Math.abs(y[tstart - 1] - ystart) < ep) {
				tstart = tstart + 1;
			}
		}
		ystart = mean(Matlab.colon(y, 0, tstart - 1));
		if (tend <= 0) {
			tend = y.length;
			while (Math.abs(y[tend - 1] - yend) < ep) {
				tend = tend - 1;
			}
		}
		yend = mean(Matlab.colon(y, tend - 1, y.length - 1));
		y0 = Matlab.colon(y, tstart - 1, tend - 1);
		ytemp = y0[0];
		temp = Matlab.multiply(Matlab.ones(y0.length), ytemp);
		for (int i = 0; i < y0.length; i++) {
			y0[i] = (y0[i] - temp[i]);/// (yend - ytemp)
		}
		t0 = Matlab.colon(t, tstart - 1, tend - 1);
		temp = Matlab.multiply(Matlab.ones(y0.length), t[tstart - 1]);
		for (int i = 0; i < y0.length; i++) {
			t0[i] = t0[i] - temp[i];
		}
		x0 = Matlab.colon(x0, tstart - 1, tend - 1);
		return new Object[] { t0, y0, x0, tstart, tend };
	}

	public static void main(String[] args) {
		

	}
}
