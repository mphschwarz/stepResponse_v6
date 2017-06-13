package pro2e.teamX.matlabfunctions;

import org.apache.commons.math3.complex.Complex;
/**
 * SVTOOLS wurde von Prof. Dr. Gut zur Verfügung gestellt.
 * @author Zogg
 *
 */
public class SVTools {
	/**
	 * Berechnet den Frequenzgang aufgrund von Zähler- und Nennerpolynom b resp.
	 * a sowie der Frequenzachse f.
	 * 
	 * @param b
	 *            Zählerpolynom
	 * @param a
	 *            Nennerpolynom
	 * @param f
	 *            Frequenzachse
	 * @return Komplexwertiger Frequenzgang.
	 */
	public static final Complex[] freqs(double[] b, double[] a, double[] f) {
		Complex[] res = new Complex[f.length];

		for (int k = 0; k < res.length; k++) {
			Complex jw = new Complex(0, 2.0 * Math.PI * f[k]);

			Complex zaehler = Matlab.polyval(b, jw);
			Complex nenner = Matlab.polyval(a, jw);

			res[k] = zaehler.divide(nenner);
		}
		return res;
	}

	public static final Object[] schrittIFFT(double[] B, double[] A, double fs, int N) {
		double[] h = null;

		double T = 1 / fs;

		double[] f = Matlab.linspace(1e-12, fs / 2.0, N / 2);

		Complex[] H = freqs(B, A, f);

		Complex[] X = Matlab.concat(H, new Complex(0.0), Matlab.conj(Matlab.colonColon(H, N / 2 - 1, -1, 1)));

		h = Matlab.c2d(Matlab.ifft(X));

		for (int i = 1; i < h.length; i++) {
			h[i] += h[i - 1];
		}

		double[] t = Matlab.linspace(0.0, (N - 1) * T, N);

		return new Object[] { h, t };
	}

	public static final Object[] schrittIFFT(double[] B, double[] A, double[] t) {
		double[] h = null;
		int up = 4;
		int N = up * t.length;
		double T = t[1] - t[0];
		double fs = 1 / T;

		double[] f = Matlab.linspace(1e-16, fs / 2.0, N / 2);

		Complex[] H = freqs(B, A, f);

		Matlab.print("H", H);

		Complex[] X = Matlab.concat(H, new Complex(0.0), Matlab.conj(Matlab.colonColon(H, N / 2 - 1, -1, 1)));

		h = Matlab.c2d(Matlab.ifft(X));

		double[] res = new double[t.length];
		for (int i = 1; i < res.length; i++) {
			h[i] += h[i - 1];
		}

		for (int i = 0; i < res.length; i++) {
			res[i] = h[i];
		}

		return new Object[] { res, t };
	}

	public static final Object[] step(double[] B, double[] A, double[] t) {

		// Koeff. der höchste Potenz des Nenners auf 1.0 normieren:
		B = B.clone();
		B = Matlab.multiply(B, 1.0 / A[0]);
		A = A.clone();
		A = Matlab.multiply(A, 1.0 / A[0]);

		// 1e-12 an A anhängen und Residuen rechnen
		Object[] obj = Matlab.residue(B, Matlab.concat(A, 1e-6));
		Complex[] R = (Complex[]) obj[0];
		Complex[] P = (Complex[]) obj[1];
		double K = ((Double) obj[2]).doubleValue();

		double[] h = Matlab.zeros(t.length);

		if (K != 0.0) {
			h[0] = K;
		}

		// Schrittantwort rechnen
		for (int i = 0; i < t.length; i++) {
			for (int k = 0; k < R.length; k++) {
				h[i] = h[i] + P[k].multiply(t[i]).exp().multiply(R[k]).getReal(); // .devide(fs);
			}
		}

		return new Object[] { h, t };
	}

	public static void main(String[] args) {

		Filter butter = FilterFactory.createCheby1(10, 3.0, 1.0);
		double[] A = butter.A;
		double[] B = butter.B;

		long start1 = System.currentTimeMillis();
		Object[] results = step(B, A, Matlab.linspace(0.0, 100.0, 2048));
		long time1 = System.currentTimeMillis() - start1;
		double[] h1 = (double[]) results[0];
		double[] t = (double[]) results[1];

		long start2 = System.currentTimeMillis();
		results = schrittIFFT(B, A, Matlab.linspace(0.0, 100.0, 2048));
		long time2 = System.currentTimeMillis() - start2;

		double[] h2 = (double[]) results[0];
		t = (double[]) results[1];

		Matlab.print("h1", h1);
		Matlab.print("h2", h2);
		Matlab.print("t", t);

				System.out.println("time1 "+time1+" time2 "+time2);
				
				Matlab.print("rA", Matlab.roots(A));

		//		1.00004813830448
		//		1.0000426520320962
		//		1.0000373442394175
		//		1.0000322899729377
	}

}
