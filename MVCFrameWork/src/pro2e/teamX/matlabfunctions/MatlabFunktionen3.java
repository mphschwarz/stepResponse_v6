
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
	public NelderMeadSimplexSinusCosExample nelderMeadSimplexSinusCosExample;
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
	

	public static void setFenster(int fenstertyp) {
		if (fenstertyp == RECHTECK) {
			fensterform = RECHTECK;
		}
		if (fenstertyp == GAUSS) {
			fensterform = GAUSS;
		}
	}
	public static void setFiltertyp(int typ) {
		if (typ == KEINFILTER) {
			filtertyp = KEINFILTER;
		}
		if (typ == SMOOTHING) {
			filtertyp = SMOOTHING;
		}
		if (typ == TIEFPASS) {
			filtertyp = TIEFPASS;
		}
	}

	public static void signalFiltern(double[] zeitvektor){
		
		int fensterbreite = Integer.parseInt(FilterPanel.tfAnzahlwerte.getText());// wert aus Textfeld
		if(filtertyp == KEINFILTER){
		}
		if(filtertyp == SMOOTHING){
		smoothing(zeitvektor,fensterbreite);
		}	
	}
	
	public static Object[] shiftT(double[] t, double[] y, double[] c, int n) {
		double[] tx = Matlab.linspace(0, t.length - 1, t.length);
		//	Object[] numden = genFraq(butterIniC(1, n, 10), n);
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

			return new Object[] { (int) (-di * 2.0), dt };

		} catch (Exception e) {
			int di = 0;
			int dt = 0;
			return new Object[] { di, dt };
		}
		//-------------funktioniert für sachen mit peaks 12.05.17
		//		Object[] gm = findpeaks(h);
		//		int[] ixmm = (int[]) gm[1];
		//		int ixmmm = ixmm[0];
		//		for (int i = 0; i < tx.length; i++) {
		//			tx[i] = tx[i] / (tx[ixmmm] - tx[(int) ix5]);
		//		}
		//
		//		double tx5 = tx[(int) ix5];
		//
		//		while (y[(int) iy5] < 0.5 && y[(int) (iy5 + 1)] > 0.5 == false) {
		//			iy5 = iy5 + 1;
		//		}
		//
		//		Object[] fp2 = findpeaks(Matlab.colon(y, (int) iy5, y.length - 1));
		//		int[] iymmg = (int[]) fp2[1];
		//		int iymm = iymmg[0];
		//		if (iymm == 0) {
		//			iymm = y.length;
		//		} else {
		//			iymm = (int) (iymm + iy5);
		//		}
		//		double[] ty = new double[y.length];
		//		for (int i = 0; i < ty.length; i++) {
		//			ty[i] = t[i] / (t[iymm] - t[(int) iy5]);
		//		}
		//		double ty5 = ty[(int) ix5];
		//		double dt = ty5 - tx5;
		//		double di = -(dt / (t[0] - t[1]));
		//
		//		return new Object[] { (int) (-di * 2), dt };

	}

	public static Object[] normTneu(double[] y, double[] t) {
		int iy5 = 1;
		//		while((y[iy5]<0.5 && y[iy5+1]>0.5)==false){
		//			iy5=iy5+1;
		//		}
		//		Object[] xym_iym=findpeaks(Matlab.colon(y, iy5, y.length-1));
		//		int xym= (int) xym_iym[0];
		//		double []iym=   (double[]) xym_iym[1];
		//		int  iymm=(int) iym[0];
		//		if(iymm==0){
		//			iymm=y.length;
		//		}else{
		//			iymm=(int) (iym[0]+iy5);
		//		}
		//		for (int i = 0; i < t.length; i++) {
		//			t[i]=t[i]/(t[iymm]-t[iy5]);
		//		}
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
			for (int i = 0; i < ty.length; i++) {
				ty[i] = t[i] / (t[iymm] - t[(int) iy5]);
			}
			double T = ty[iymm] - t[iy5];
			return new Object[] { ty, T };

		} catch (Exception e) {
			double T = 0.0;
			return new Object[] { y, T };
		}
	}

	//zum lösen des Problems	

	//		Object[] fp2 = findpeaks(Matlab.colon(y, (int) iy5, y.length - 1));
	//		int[] iymmg = (int[]) fp2[1];
	//		int iymm = iymmg[0];
	//		if (iymm == 0) {
	//			iymm = y.length - 1;
	//		} else {
	//			iymm = (int) (iymm + iy5);
	//		}
	//		double[] ty = new double[y.length];
	//		for (int i = 0; i < ty.length; i++) {
	//			ty[i] = t[i] / (t[iymm] - t[(int) iy5]);
	//		}
	//		double T = ty[iymm] - t[iy5];
	//
	//		return new Object[] { ty, T };
	//
	//	}

	//	public static double[] smoothing(double[] zeitvektor, int fensterbreite) {
	//		double[] fenster = new double[fensterbreite];
	//		double[] gefaltet = new double[fensterbreite + zeitvektor.length];
	//		double[] Resultat = new double[zeitvektor.length];
	//
	//		if (fensterform == RECHTECK) {
	//			for (int i = 0; i < fensterbreite; i++) {
	//				fenster[i] = 1.0 / fensterbreite;
	//			}
	//		} else if (fensterform == GAUSS) {
	//			for (int i = -fensterbreite; i < fensterbreite; i++) {
	//				fenster[i] = Math.exp(-(i * i));
	//			}
	//		}
	//
	//		gefaltet = Matlab.conv(zeitvektor, fenster);
	//		for (int i = 0; i < zeitvektor.length; i++) {
	//			Resultat[i] = gefaltet[i + fensterbreite / 2];
	//		}
	//		return Resultat;
	//	}
	//	public static void setFenster(int fenstertyp){
	//		if(fenstertyp == RECHTECK){
	//			fensterform = RECHTECK;
	//		}
	//		if(fenstertyp == GAUSS){
	//			fensterform = GAUSS;
	//		}
	//	}

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

	public static double mean(double[] v) {
		double m = 0;
		for (int i = 0; i < v.length; i++) {
			m = m + v[i];
		}
		m = m / v.length;
		return m;
	}

	public static final double[] error(double[] c, double[] t, double[] x, int n) {
		double[] r = new double[x.length];
		double[] y = stepResponse(c, t, n);
		for (int i = 0; i < x.length; i++) {

			r[i] += Math.pow((y[i] - x[i]), 2);
		}
		return r;

	}

	public static final double error1(double[] c, double[] t, double[] x, int n) {
		double r = 0;
		double[] y = stepResponse(c, t, n);
		for (int i = 0; i < x.length; i++) {

			r = r + Math.pow((y[i] - x[i]), 2);
		}
		return r;

	}

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

			for (int i = 0; i < re.length; i++) {
				//System.out.println("re " + re[i]);
			}
			for (int i = 0; i < im.length; i++) {
				//System.out.println("im " + im[i]);
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

			for (int i = 0; i < re.length; i++) {
				//System.out.println("re " + re[i]);
			}
			for (int i = 0; i < im.length; i++) {
				//	System.out.println("im " + im[i]);
			}
			re[re.length - 1] = -1;

			c = reimtowq(re, im, k, N);
			for (int i = 0; i < c.length; i++) {
				//	System.out.println("c: " + c[i]);
			}
		}
		return c;
	}

	public static double[] stepResponse(double[] c, double[] t, int n) {
		Object[] genFragobject = genFraq(c, n);
		double[] num = (double[]) genFragobject[0];
		double[] den = (double[]) genFragobject[1];
		Object[] steped = SVTools.step(num, den, t);
		double[] h = (double[]) steped[0];
		return h;
	}

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

	public static Object[] optStep(double[] normt, double[] normy, int n) {
		//Versuch mit den Nullstellen von Prof Gut
		//		Filter filterIni = FilterFactory.createButter(n, 1.0);

		double[] iter = Matlab.zeros(10);
		Object[] g = null;

		double[] coeff = new double[n + 1];
		double[] c = butterIniC(1, n, 11);

		//		Object[] buttergenFrag = genFraq(c, n);
		//		double[] numgenFrag = (double[]) buttergenFrag[0];
		//		double[] dengenFrag = (double[]) buttergenFrag[1];
		//		Object[] resi = Matlab.residue(numgenFrag, dengenFrag);
		//		Complex[] startw = (Complex[]) resi[1];
		//
		//		double[] startwerte = Matlab.c2d(startw);
		//		for (int i = 0; i < startwerte.length; i++) {
		//			System.out.println(startwerte[i]);
		//		}

		//
		//double[] c={0.5,- 0.3,-0.3,-0.8, -0.8,-1};

		//

		//double[] c={1.5,2.5,2,1};
		//double [] c={1,3,5,5,3,-1};
		double[] steps = new double[n + 1];

		for (int i = 0; i < steps.length; i++) {
			steps[i] = PREFERENZEN.fminStep; //o.oo8 funkzioniert
		}
		g = fminSearch(normt, normy, c, steps, n);

		return g;

	}

	public static Object[] fminSearch(double[] normt, double[] normy, double[] startwerte, double[] steps, int r) {
		SimplexOptimizer optimizer = new SimplexOptimizer(PREFERENZEN.fminRelativ, PREFERENZEN.fminAbsolut);//1e-6, 1e-12 //19.05 1e-13, 1e-16
		Target target = new Target(normy, normt, r);
		PointValuePair optimum = null;
		double[] coeffs = null;
		double iter = 0.0;
		double fehler = Double.POSITIVE_INFINITY;

		try {

			for (int i = 0; (i < 20) && !PREFERENZEN.abbrechen; i++) {

				optimum = optimizer.optimize(new MaxEval((int)PREFERENZEN.fminIter/5), new ObjectiveFunction(target), GoalType.MINIMIZE, //500 Iterationen laut Gut
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
	//	public static Object[] fminSearch(double[] normt, double[] normy, double[] startwerte, double[] steps, int r) {
	//		SimplexOptimizer optimizer = new SimplexOptimizer(1e-13, 1e-16);//1e-6, 1e-12
	//		Target target = new Target(normy, normt, r);
	//		//--------neu für keipeak funktionen
	//		//double[] coeff = null;
	//		boolean maxEval = false;
	//		//---------------
	//		PointValuePair optimum = null;
	//		try {
	//			optimum = optimizer.optimize(new MaxEval(30000), new ObjectiveFunction(target), GoalType.MINIMIZE,
	//					new InitialGuess(startwerte), new NelderMeadSimplex(steps));
	//
	//		} catch (TooManyEvaluationsException e) {
	//		//	coeff = optimum.getPoint();
	//			//		target.poles = optimum.getPoint();
	//			target.s = optimum.getValue();
	//			maxEval = true;
	//		}
	//		//		catch (NullPointerException e) {
	//		//			System.out.println(target.poles);
	//		//		}
	//		if (maxEval == false) {
	//		//	coeff = optimum.getPoint();
	//		}
	//		double iter = optimizer.getIterations();
	//		double fehler = optimum.getValue();
	//		double[] coeff = optimum.getPoint();
	//		//Object [] resultsShiftT=shiftT(normt, normy, coeff, r);
	//		return new Object[] { coeff, fehler, iter };
	//	}
	//
	//	public static class Target implements MultivariateFunction {// Fehlerfunktion 
	//		double[] normt;
	//		double[] normy;
	//		int r;
	//		double[] poles;
	//		double s;
	//
	//		public Target() {}
	//
	//		public Target(double[] normy, double[] normt, int r) {
	//			this.normy = normy;
	//			this.normt = normt;
	//			this.r = r;
	//		}
	//
	//		public double value(double[] c) {
	//			//			final double x = variables[0];
	//			//			final double y = variables[1];
	//
	//			//	double [] coeff=error(c, normt, normy, r);
	//
	//			double s = error1(c, normt, normy, r);
	//
	//			System.out.println("Fehlersumme" + s);
	//			return s;
	//		}
	//	}

	//	public static double[] wqtoReIm(double[] c, int n) {
	//		double k = c[0];
	//		double[] re;
	//		double[] im;
	//
	//		if (n % 2 == 0) {
	//			double[] w = new double[n / 2];
	//			double[] q = new double[n / 2];
	//			re = new double[n];
	//			im = new double[n];
	//
	//			for (int i = 0; i < n / 2; i++) {
	//				if (i == 0)
	//					w[i] = c[1];
	//				else
	//					w[i] = c[i * 2 + 1];
	//				//System.out.println("w= "+w[i] );
	//			}
	//			for (int i = 0; i < n / 2; i++) {
	//				if (i == 0)
	//					q[i] = c[2];
	//				else
	//					q[i] = c[i * 2 + 2];
	//				//System.out.println("q= "+q[i] );
	//			}
	//			for (int i = 0; i < n / 2; i++) {
	//				re[i] = -w[i] / (2 * q[i]);
	//				//System.out.println("re= "+re[i] );
	//			}
	//			for (int i = 0; i < n / 2; i++) {
	//				im[i] = Math.sqrt(Math.pow(w[i], 2) - Math.pow(re[i], 2));
	//				//System.out.println("im= "+im[i] );
	//			}
	//			double[] re2 = new double[re.length];
	//			for (int i = 0; i < re2.length; i++) {
	//				re2[i] = re[i];
	//			}
	//
	//			for (int i = 0; i < n / 2; i = i + 1) {
	//				re[i * 2] = re2[i];
	//				re[i * 2 + 1] = re2[i];
	//			}
	//			for (int i = 0; i < re2.length; i++) {
	//				re2[i] = im[i];
	//			}
	//			for (int i = 0; i < n / 2; i++) {
	//				im[i * 2] = re2[i];
	//				im[i * 2 + 1] = -re2[i];
	//				//System.out.println("2.im= "+im[i] );
	//			}
	//		} else {
	//			double[] w = new double[n / 2];
	//			double[] q = new double[n / 2];
	//			re = new double[n];
	//			im = new double[n];
	//
	//			for (int i = 0; i < n / 2; i++) {
	//				if (i == 0)
	//					w[i] = c[1];
	//				else
	//					w[i] = c[i * 2 + 1];
	//				//System.out.println("w= "+w[i] );
	//			}
	//			for (int i = 0; i < n / 2; i++) {
	//				if (i == 0)
	//					q[i] = c[2];
	//				else
	//					q[i] = c[i * 2 + 2];
	//				//System.out.println("q= "+q[i] );
	//			}
	//			for (int i = 0; i < n / 2; i++) {
	//				re[i] = -w[i] / (2 * q[i]);
	//				//System.out.println("re= "+re[i] );
	//			}
	//			for (int i = 0; i < n / 2; i++) {
	//				im[i] = Math.sqrt(Math.pow(w[i], 2) - Math.pow(re[i], 2));
	//				//System.out.println("im= "+im[i] );
	//			}
	//			double[] re2 = new double[re.length];
	//			for (int i = 0; i < re2.length; i++) {
	//				re2[i] = re[i];
	//			}
	//			for (int i = 0; i < n / 2; i = i + 1) {
	//				re[i * 2] = re2[i];
	//				re[i * 2 + 1] = re2[i];
	//			}
	//
	//			for (int i = 0; i < re2.length; i++) {
	//				re2[i] = im[i];
	//			}
	//			for (int i = 0; i < n / 2; i++) {
	//				im[i * 2] = re2[i];
	//				im[i * 2 + 1] = -re2[i];
	//			}
	//
	//			re[re.length - 1] = -c[n];
	//			im[im.length - 1] = 0;
	//
	//		}
	//		double[] rueckgabe = new double[re.length + im.length + 1];
	//		for (int i = 0; i < re.length; i++) {
	//			rueckgabe[i] = re[i];
	//		}
	//		for (int i = re.length; i < re.length + im.length; i++) {
	//			rueckgabe[i] = im[i - re.length];
	//		}
	//		rueckgabe[rueckgabe.length - 1] = k;
	//		return rueckgabe;
	//	}
	//	public static Object[] wqtoReIm(double[] c, int n) {
	//		double k = c[0];
	//		double[] re = new double[n];
	//		double[] im = new double[n];
	//		double[] w = new double[n/2];
	//		double[] q = new double[n/2];
	//		double[] temp = new double[re.length];
	//
	//		if (n % 2 == 0) {
	//			w = Matlab.colonColon(c, 1, 2, n);
	//			q = Matlab.colonColon(c, 2, 2, n);
	//			for (int i = 0; i < w.length; i++) {
	//				re[i] = -w[i] / (2 * q[i]);
	//			}
	//			for (int i = 0; i < w.length; i++) {
	//				im[i] = w[i] / (2 * q[i]) * Math.sqrt(4 * Math.pow(q[i], 2) + 1);
	//			}
	//			temp = Matlab.colon(re, 0, re.length-1);
	//			for (int i = 0; i < n/2; i++) {
	//				re[i * 2] = temp[i];
	//				re[i * 2 + 1] = temp[i];
	//			}
	//			temp = Matlab.colon(im, 0, re.length-1);
	//			for (int i = 0; i < n/2; i++) {
	//				im[i * 2] = temp[i];
	//				im[i * 2 + 1] = -temp[i];
	//			}
	//		} 
	//		else {
	//			w = Matlab.colonColon(c, 1, 2, n-1);
	//			q = Matlab.colonColon(c, 2, 2, n-1);
	//			for (int i = 0; i < w.length; i++) {
	//				re[i] = -w[i] / (2 * q[i]);
	//			}
	//			for (int i = 0; i < w.length; i++) {
	//				im[i] = w[i] / (2 * q[i]) * Math.sqrt(4 * Math.pow(q[i], 2) + 1);
	//			}
	//			temp = Matlab.colon(re, 0, re.length-1);
	//			for (int i = 0; i < n/2; i++) {
	//				re[i * 2] = temp[i];
	//				re[i * 2 + 1] = temp[i];
	//			}
	//			temp = Matlab.colon(im, 0, re.length-1);
	//			for (int i = 0; i < n/2; i++) {
	//				im[i * 2] = temp[i];
	//				im[i * 2 + 1] = -temp[i];
	//			}
	//			re[re.length - 1] = -Math.abs(c[n]); 
	//			im[im.length - 1] = 0;
	//		}
	//		return new Object []{re,im,k};
	//	}
	//
	//	public static double[] reimtowq(double[] re, double[] im, double k, int n) {
	//		
	//		double[] real = new double[re.length / 2];
	//		double[] imag = new double[re.length / 2];
	//		double[] w = new double[re.length / 2];
	//		double[] q = new double[re.length / 2];
	//		int temp =0;
	//		if(re.length+1>n)
	//			temp = re.length;
	//		else
	//			temp = n;
	//		double[] c = new double[temp]; 
	//		if (re.length % 2 == 0) {
	//			real = Matlab.colonColon(re, 0, 2, re.length-1);
	//			imag = Matlab.colonColon(im, 0, 2, im.length-1);
	//			for (int i = 0; i < real.length; i++) {
	//				w[i] = Math.sqrt(Math.pow(real[i], 2) + Math.pow(imag[i], 2));
	//				q[i] = w[i] / (2 * Math.abs(real[i]));
	//			}
	//			for (int i = 0; i < re.length/2; i++) {
	//				c[i*2] = w[i];
	//				c[i*2+1] = q[i];
	//			}
	//		} 
	//		else {
	//			real = Matlab.colonColon(re, 0, 2, re.length-2);
	//			imag = Matlab.colonColon(im, 0, 2, im.length-2);
	//			for (int i = 0; i < real.length; i++) {
	//				w[i] = Math.sqrt(Math.pow(real[i], 2) + Math.pow(imag[i], 2));
	//				q[i] = w[i] / (2 * Math.abs(real[i]));
	//			}
	//			for (int i = 0; i < re.length/2; i++) {
	//				c[i*2] = w[i];
	//				c[i*2+1] = q[i];
	//			}
	//			c[re.length-1] = re[re.length-1];
	//		}
	//		c = Matlab.concat(k, c);
	//		
	//		return c;
	//	}

	//-------------------alte funkzionen die mit fminsearch funktionieren
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
				//System.out.println("w= "+w[i] );
			}
			for (int i = 0; i < n / 2; i++) {
				if (i == 0)
					q[i] = c[2];
				else
					q[i] = c[i * 2 + 2];
				//System.out.println("q= "+q[i] );
			}
			for (int i = 0; i < n / 2; i++) {
				re[i] = -w[i] / (2 * q[i]);
				//System.out.println("re= "+re[i] );
			}
			for (int i = 0; i < n / 2; i++) {
				im[i] = w[i] / (2 * q[i]) * Math.sqrt(4 * Math.pow(q[i], 2) - 1);
				//System.out.println("im= "+im[i] );
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
				//System.out.println("2.im= "+im[i] );
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
				//System.out.println("w= "+w[i] );
			}
			for (int i = 0; i < n / 2; i++) {
				if (i == 0)
					q[i] = c[2];
				else
					q[i] = c[i * 2 + 2];
				//System.out.println("q= "+q[i] );
			}
			for (int i = 0; i < n / 2; i++) {
				re[i] = -w[i] / (2 * q[i]);
				//System.out.println("re= "+re[i] );
			}
			for (int i = 0; i < n / 2; i++) {
				im[i] = w[i] / (2 * q[i]) * Math.sqrt(4 * Math.pow(q[i], 2) - 1);
				//System.out.println("im= "+im[i] );
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

	public static double[] reimtowq(double[] re, double[] im, double k, int N) {
		double[] c = new double[11]; //new double[11] hinzugefügt
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
				q[i] = w[i] / (2 * Math.abs(real[i]));
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
				q[i] = w[i] / (2 * Math.abs(real[i]));
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

	//-------------------funktioniert für schwingende Kreise 12.05.2017 11.00
	public static Object[] findpeaks(double[] x0) {
		int[] p = new int[x0.length];
		double[] x = new double[x0.length];
		int k = 0;
		for (int i = 1; i < x.length - 2; i++) {
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

	public static Object[] normT(double[] x0, double[] t0) {
		double T = 1;
		double[] t = new double[t0.length];
		int[] pi = new int[x0.length];
		x0 = smoothing(x0, 100);
		Object[] res = findpeaks(x0);
		pi = (int[]) res[1];
		if (pi.length >= 2) {
			T = Math.abs(t0[pi[0]] - t0[pi[1]]);
			for (int i = 0; i < t0.length; i++) {
				t[i] = t0[i] / T;
			}
		} else {
			T = 0; //wenn keine 2 Maxima
			t = Matlab.zeros(t0.length);
		}
		return new Object[] { T, t };
	}

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
			y0[i] = (y0[i] - temp[i]) / (yend - ytemp);
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
		double[] t = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		double[] c = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		double[] tx = Matlab.linspace(0, t.length - 1, t.length);
		Object[] numden = genFraq(c, 1);
		double[] num = (double[]) numden[0];
		double[] den = (double[]) numden[1];
		Object[] x = SVTools.step(num, den, tx);
		double[] h = (double[]) x[0];
		for (int i = 0; i < h.length; i++) {
			System.out.println("Butter" + h[i]);
		}
		//		Filter darfilter = FilterFactory.createButter(8, 1);
		//		Complex[] rA = Matlab.roots(darfilter.A);
		//		for (int i = 0; i < rA.length; i++) {
		//			System.out.println("Real " + rA[i].getReal() + "    Imag  " + rA[i].getImaginary());
		//
		//		}
		//		int besteordnung = 0;
		//		double[] coeffizienten = null;
		//		double[] t = Matlab.linspace(0.0, 15.0, 100);
		//
		//		//		Filter filter = FilterFactory.createButter(3, 1.0);
		//		//		System.out.println(filter);
		//		//		double[] step_soll = (double[]) SVTools.step(filter.B, filter.A, t)[0];
		//		Filter filter1 = FilterFactory.createCheby1(4, 1, 1);
		//		System.out.println(filter1);
		//		double[] step_antwort = (double[]) SVTools.step(filter1.B, filter1.A, t)[0];

		//		int ns = 5;
		//		int ne = 5;
		//		for (int i = 0; i < step_antwort.length; i++) {
		//			System.out.println("" + step_antwort[i]);
		//		}
		//
		//		double[] iterationen = new double[10];
		//		double[] fehlersumme = new double[10];
		//		for (int g = 0; g < iterationen.length; g++) {
		//			iterationen[g] = 0;
		//			fehlersumme[g] = 100000000;
		//		}
		//		for (int r = ns; r <= ne; r++) {
		//			Object[] testoptstep = optStep(t, step_antwort, r);
		//			iterationen[r] = (double) testoptstep[2];
		//			fehlersumme[r] = (double) testoptstep[1];
		//			coeffizienten = (double[]) testoptstep[0];
		//		}
		//		for (int i = 0; i < coeffizienten.length; i++) {
		//			System.out.println("Koeffizienten nach fminsearch" + coeffizienten[i]);
		//		}
		//		for (int h = 0; h < iterationen.length; h++) {
		//			System.out.println("Iter for schlaufe" + iterationen[h]);
		//		}
		//		for (int j = 0; j < fehlersumme.length; j++) {
		//			System.out.println("Fehlersumme von Arraray" + fehlersumme[j]);
		//		}
		//
		//		for (int w = 0; w < fehlersumme.length - 1; w++) {
		//			if (fehlersumme[w] > fehlersumme[w + 1]) {
		//				double bestordnung = fehlersumme[w + 1];
		//				besteordnung = w + 1;
		//			}
		//		}
		//		Object[] besteord = optStep(t, step_antwort, besteordnung);
		//		double[] Coeffizients = (double[]) besteord[0];
		//		Object[] coeffgenFrag = genFraq(Coeffizients, besteordnung);
		//		double[] Numerator = (double[]) coeffgenFrag[0];
		//		double[] Dennn = (double[]) coeffgenFrag[1];
		//
		//		Matlab.print("A", Dennn);
		//
		//		for (int e = 0; e < Numerator.length; e++) {
		//			System.out.println("Koeffizienten Beste Ordnung" + Numerator[e]);
		//		}
		//		for (int z = 0; z < Dennn.length; z++) {
		//			System.out.println("Denn Beste Ordnung" + Dennn[z]);
		//		}
		//		System.out.println("Beste Ordnung: " + besteordnung);

		//bis hier fminsearch	

		//		Object[] ress = optStep(t, step_antwort, 9);
		//		double[] coeff = (double[]) ress[0];
		//
		//		double fehler = (double) ress[1];
		//		double iter = (double) ress[2];
		//
		//		for (int i = 0; i < coeff.length; i++) {
		//			System.out.println("Koeffizienten    :" + coeff[i]);
		//		}
		//		System.out.println("Fehlersumme" + fehler);
		//		System.out.println("Iterationen" + iter);
		//
		//		//				double [] butter=butterIniC(1, 4, 11);
		//		//				for (int i = 0; i < butter.length; i++) {
		//		//					System.out.println("Butter"+butter[i]);
		//		//				}
		//		Object[] buttergenFrag = genFraq(coeff, 9);
		//		double[] num = (double[]) buttergenFrag[0];
		//		double[] den = (double[]) buttergenFrag[1];
		//		for (int i = 0; i < num.length; i++) {
		//			System.out.println(num[i]);
		//		}
		//		for (int i = 0; i < den.length; i++) {
		//			System.out.println("den 6er butterworth" + den[i]);
		//		}
		//		Complex[] nullstellen = Matlab.roots(den);
		//		for (int i = 0; i < nullstellen.length; i++) {
		//			System.out.println("Real  :" + nullstellen[i].getReal() + "         Imag  :" + nullstellen[i].getImaginary());
		//		}
		//		double [] reim=wqtoReIm(butter, 8);
		//		for (int i = 0; i < reim.length; i++) {
		//			System.out.println("wqtoreim  "+reim[i]);
		//		}
		//		Complex [] roots=Matlab.roots(den);
		//		for (int i = 0; i < roots.length; i++) {
		//			System.out.println(roots[i]);
		//		}
		//		for (int i = 0; i < butter.length; i++) {
		//			System.out.println(butter[i]);
		//		}	

		//		double[] reim = wqtoReIm(coeff, 4);
		//		for (int i = 0; i < reim.length; i++) {
		//			System.out.println("RealImaginar von Coeff" + reim[i]);
		//		}
		//		Object[] testgen = genFraq(coeff, 4);
		//		double[] num = (double[]) testgen[0];
		//		double[] den = (double[]) testgen[1];
		//		System.out.println("num " + num[0]);
		//		for (int i = 0; i < den.length; i++) {
		//			System.out.println("den" + den[i]);
		//		}
		//		Filter filter1 = FilterFactory.createButter(3, 1);
		//		System.out.println(filter1);
		//		double[] tfürswift = Matlab.linspace(0, 15, 2500);
		//		double[] tfür_step_antwort=Matlab.linspace(0, 15, 2000);
		//			double[] step_antwort = (double[]) SVTools.step(filter1.B, filter1.A, tfür_step_antwort)[0];	
		//		
		//		double [] nuller=Matlab.zeros(500);
		//		double [] gg=Matlab.concat(nuller, step_antwort);
		//		for (int i = 0; i < gg.length; i++) {
		//			System.out.println(""+gg[i]);
		//		}

		//		Object [] nachT=normTneu(gg, tfürswift);
		//		double [] tnachnormT=(double[]) nachT[0];
		//		Object[] testschift = shiftT(tnachnormT, gg, butterIniC(1, 3, 10), 3);
		//
		//		double dt = (double) testschift[1];
		//		int di = (int) testschift[0];
		//		System.out.println("dt:   " + dt);
		//		System.out.println("di     " + di);
		//		for (int i1 = 0; i1 < gg.length; i1++) {
		//			System.out.println("Modischritt"+gg[i1]);
		//		}
		//		//for (int i = 0; i < butter.length; i++) {
		//	System.out.println(""+butter[i]);
		//}

	}
}
//}
//	public static void main(String[] args) {
//				double [] normt={1,2,3,4,5,6,7,8,9,10,11,12};
//				double [] normy={0,1.2,1.3,1.4,1.5,1.6,1.5,1.6,1.7,1.5,1.6,1.7};
//				int ns=4;
//				int ne=4;
//				Object[] ress=optStep(normt, normy, ns, ne);
//		    	double[] coeff=(double[]) ress[0];
//		    			
//		    	double fehler=(double) ress[1];
//		    	double iter=(double) ress[2];
//		    	Complex[] nullstellen=Matlab.roots(coeff);
//		    	for (int i = 0; i < coeff.length; i++) {
//					System.out.println("Koeffizienten    :"+coeff[i]);
//				}
//		     	System.out.println("Fehlersumme"+fehler);
//		   	System.out.println("Iterationen"+iter);
//		    	for (int i = 0; i < nullstellen.length; i++) {
//					System.out.println("Real  :"+nullstellen[i].getReal()+"         Imag  :"+nullstellen[i].getImaginary());
//				}
//
//		//    	double[] dubidu = Matlab.linspace(Math.PI / 2, Math.PI, 2);
//		double[] buttterinic = new double[11];
//		//		double[] c={1,2,3,4,5,6,7,8,9,10,11};
//		int n = 3;
//		//		Filter filter;
//		//		filter=FilterFactory.createButter(5, 1);
//		//		System.out.println(""+filter.rA);
//		//		Object[] res=genFrag(c, n);
//		//		double[] num=(double[]) res[0];
//		//		double[] den=(double[]) res[1];
////		buttterinic = butterIniC(1, 9, 11);
//		////		for (int i = 0; i < dubidu.length; i++) {
//		////			System.out.println("" + dubidu[i]);
//		////		}
////		for (int i = 0; i < buttterinic.length; i++) {
////			System.out.println("Butter" + buttterinic[i]);
////		}
//		//		        for (int i = 0; i < num.length; i++) {
//		//					System.out.println("num: "+num[i]);
//		//			}for (int i = 0; i < den.length; i++) {
//		//				System.out.println("den"+den[i]);
//		//			}for (int i = 0; i < filter.rA.length; i++) {
//		//				System.out.println(""+filter.rA[i]);
//		//			}
//
//	}

//}
