
package pro2e.teamX.matlabfunctions;

import java.util.Arrays;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.optim.InitialGuess;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.NelderMeadSimplex;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.SimplexOptimizer;
import org.apache.commons.math3.util.FastMath;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

public abstract class MatlabFunktionen {
	public NelderMeadSimplexSinusCosExample nelderMeadSimplexSinusCosExample;
	public double[][] c0 = new double[10][10];
	static double[] w;
	static double[] q;
	static double betrag;
	static double[] c;
	static double[] t;
	public static final int RECHTECK = 0, GAUSS = 1;
	private static int fensterform = RECHTECK;

	public static void setFenster(int fenstertyp) {
		if (fenstertyp == RECHTECK) {
			fensterform = RECHTECK;
		}
		if (fenstertyp == GAUSS) {
			fensterform = GAUSS;
		}
	}

	public static double[] smoothing(double[] zeitvektor, int fensterbreite) {
		double[] fenster = new double[fensterbreite];
		double[] gefaltet = new double[fensterbreite + zeitvektor.length];
		double[] Resultat = new double[zeitvektor.length];

		if (fensterform == RECHTECK) {
			for (int i = 0; i < fensterbreite; i++) {
				fenster[i] = 1.0 / fensterbreite;
			}
		} else if (fensterform == GAUSS) {
			for (int i = -fensterbreite; i < fensterbreite; i++) {
				fenster[i] = Math.exp(-(i * i));
			}
		}

		gefaltet = Matlab.conv(zeitvektor, fenster);
		for (int i = 0; i < zeitvektor.length; i++) {
			Resultat[i] = gefaltet[i + fensterbreite / 2];
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

    public static double[] butterIniC(double k, int n, int N){
        double []c= new double[N];
        double[] a = new double[n];
        double []re=new double[n];
        double[] im = new double[n];
        double[] lin=Matlab.linspace(0,n-1,n);
        if(n % 2 == 0){
              
              for (int i=0;i<n;i++){
                   a[i]=Math.PI/2 + Math.PI/2/n + Math.PI/n*lin[i];
                   re[i]=Math.cos(a[i]);
              }
              for (int i=0;i<n;i++){
                   a[i]=Math.PI/2 + Math.PI/2/n + Math.PI/n*lin[i];
                   im[i]=Math.sin(a[i]);;
              }

              for (int i = 0; i < a.length; i++) {
                   a[i]=re[i];
              }
              for(int i=0;i<n/2;i=i+1){
                   re[i*2]=a[i];
                   re[i*2+1]=a[i];
              }
              for (int i = 0; i < a.length; i++) {
                   a[i]=im[i];
              }
              for(int i=0;i<n/2;i++){
                   im[i*2]=a[i];
                   im[i*2+1]=-a[i];
              }
              
              for (int i = 0; i < re.length; i++) {
                   System.out.println("re "+re[i]); 
              }
              for (int i = 0; i < im.length; i++) {
                   System.out.println("im "+im[i]); 
              }
              
              c=reimtowq(re,im,k,N);
        }else{
   
              for (int i=0;i<n-1;i++){
                   a[i]=Math.PI/2 + Math.PI/2/n + Math.PI/n*lin[i];
                   re[i]=Math.cos(a[i]);
              }
              for (int i=0;i<n-1;i++){
                   a[i]=Math.PI/2 + Math.PI/2/n + Math.PI/n*lin[i];
                   im[i]=Math.sin(a[i]);;
              }

              for (int i = 0; i < a.length; i++) {
                   a[i]=re[i];
              }
              for(int i=0;i<n/2;i=i+1){
                   re[i*2]=a[i];
                   re[i*2+1]=a[i];
              }
              for (int i = 0; i < a.length; i++) {
                   a[i]=im[i];
              }
              for(int i=0;i<n/2;i++){
                   im[i*2]=a[i];
                   im[i*2+1]=-a[i];
              }
              
              for (int i = 0; i < re.length; i++) {
                   System.out.println("re "+re[i]); 
              }
              for (int i = 0; i < im.length; i++) {
                   System.out.println("im "+im[i]); 
              }
              re[re.length-1]=-1;
              
              c=reimtowq(re,im,k,N);
              for (int i = 0; i < c.length; i++) {
                   System.out.println("c: "+c[i]);
              }
        }
        return c;
   }


//	public static double[] stepResponse(double[] c, double[] t, int n) {
//		int den = 1;
//		double[] k = new double[c.length];
//		k[0] = c[0];
//		System.arraycopy(c, 1, c, 0, c.length);
//		if (c.length % 2 == 1) {
//			int odd = 1;
//
//		}
//		return t;
//
//	}
	
	
	public static double[] stepResponse(double[] c, double[] t, int n) {
		Object[] genFragobject=genFraq(c, n);
		double[] num=(double[]) genFragobject[0];
		double[] den=(double[]) genFragobject[1];
		Object [] steped=SVTools.step(num, den, t);
		double [] h=(double[]) steped[0];
		return h;
	}
	
	public static Object[] genFraq(double [] c, int n){
		double[] num = {c[0]};
		double[] den = new double [n+1];
		den[0] = 1;
		boolean even = true;
		if(n%2 == 1){
			den = Matlab.conv(new double[]{1,Math.abs(c[n])},den);
			n = n-1;
			even = false;
		}
		for (int i = 1; i < n; i=i+2) {
			num[0] = num[0] * Math.pow(c[i], 2);
			den = Matlab.conv(new double[]{1, c[i]/c[i+1], Math.pow(c[i], 2)}, den);
		}
		if (even==true)
			den = Matlab.colon(den, 0, n);
		else
			den = Matlab.colon(den, 0, n+1);
		
		return new Object []{num,den};
	}
	
	public static Object[] optStep(double[] normt, double[] normy, int ns, int ne) {

		//		double[] val = new double[10];
		//		double[] iter = Matlab.zeros(10);
		//		double[] ef = Matlab.zeros(10);
		//		double bestordnung;

		Object[] g = null;

		for (int r = ns; r <= ne; r++) {
			double[] coeff = new double[r + 1];
			double[] c = butterIniC(1, r, 11);
			//double[] c={1.5,2.5,2,1};
			double[] steps = new double[r + 1];

			for (int i = 0; i < steps.length; i++) {
				steps[i] = 0.2;
			}
			g = fminSearch(normt, normy, c, steps, r);
		}

		return g;

	}

	public static Object[] fminSearch(double[] normt, double[] normy, double[] startwerte, double[] steps, int r) {

		SimplexOptimizer optimizer = new SimplexOptimizer(1e-10, 1e-30);//
		Target target = new Target(normy, normt, r);

		PointValuePair optimum = optimizer.optimize(new MaxEval(5000), new ObjectiveFunction(target), GoalType.MINIMIZE,
				new InitialGuess(startwerte), new NelderMeadSimplex(steps));

		double iter = optimizer.getIterations();
		double fehler = optimum.getValue();
		double[] coeff = optimum.getPoint();
        
        
        
		return new Object[] { coeff, fehler, iter };
	}

	public static class Target implements MultivariateFunction {// Fehlerfunktion 
		double[] normt;
		double[] normy;
		int r;

		public Target() {}

		public Target(double[] normy, double[] normt, int r) {
			this.normy = normy;
			this.normt = normt;
			this.r = r;
		}

		public double value(double[] c) {
			//			final double x = variables[0];
			//			final double y = variables[1];

			//	double [] coeff=error(c, normt, normy, r);
			double s = error1(c, normt, normy, r);

			return s;
		}
	}

//	public static Object[] genFrag(double[] c, int n) {
//		double[] den = new double[n + 1];
//		double k = c[0];
//		double odd;
//		double s = 1;
//		double h = 1;
//		double[] ck = new double[c.length - 1];
//		for (int i = 0; i < ck.length; i++) {
//			ck[i] = c[i + 1];
//			h = c[i + 1];
//		}
//		if (n % 2 == 1) {
//			odd = 1;
//			n = n - 1;
//			s = h;
//			double[] ckk = new double[ck.length - 1];
//			for (int i = 0; i < ckk.length; i++) {
//				ckk[i] = ck[i];
//			}
//		} else {
//			odd = 0;
//		}
//		double[] trimc = new double[n];
//		for (int i = 0; i < trimc.length; i++) {
//			trimc[i] = ck[i];
//		}
//		double[] wp = new double[trimc.length];
//		double[] qp = new double[trimc.length];
//		int g = 0;
//		while (g < trimc.length) {
//			if (g % 2 == 0) {
//				wp[g] = trimc[g];
//				g++;
//			} else {
//				qp[g] = trimc[g];
//				g++;
//			}
//			//			wp[g]=trimc[g];
//			//			g++;
//			//			qp[g]=trimc[g];
//			//			g++;
//		}
//		//		 wp=Matlab.colonColon(trimc, 0, 2, trimc.length);
//		//		 qp=Matlab.colonColon(trimc, 1, 2, trimc.length);
//		//	Bin mir nicht sicher denn Array
//		double[] denn = new double[11];
//		for (int i = 0; i < wp.length; i++) {
//
//			denn = Matlab.conv(new double[] { 1, wp[i] / qp[i], Math.pow(wp[i], 2) }, new double[] { 1 });
//		}
//		double[] num = new double[wp.length];
//		for (int i = 0; i < wp.length; i++) {
//			num[i] = Math.pow(wp[i], 2) * k;
//		}
//
//		if (odd == 1) {
//			for (int i = 0; i < num.length; i++) {
//				num[i] = num[i] * Math.abs(s);
//			}
//
//			double[] hh = { 1, s };
//			den = Matlab.conv(denn, hh);
//		}
//
//		return new Object[] { num, den };
//
//	}

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
				im[i] = Math.sqrt(Math.pow(w[i], 2) - Math.pow(re[i], 2));
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
				im[i] = Math.sqrt(Math.pow(w[i], 2) - Math.pow(re[i], 2));
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

			re[re.length - 1] = -c[n];
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
		double[] c;
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
	public static Object[] findpeaks(double [] x0){
		int [] p = new int [x0.length];
		double[] x = new double [x0.length];
		int k = 0;
		for (int i = 1; i < x.length-2; i++) {
			if(x0[i+1]==x0[i]){
				int m = 0;
				for (int j = i; x0[j+1]==x0[j]&&j+2<x0.length; j++) {
					if(x0[j+2]<x0[j+1] && x0[i-1]<x0[i]){
						x[k] = x0[i];
						p[k] = i;
						k++;
					}
					m = j+1;
				}
				i = m;
			}
			if(x0[i-1]<x0[i] && x0[i+1]<x0[i]){
				x[k] = x0[i];
				p[k] = i;
				k++;
			}
		}
		x = Matlab.colon(x, 0, k-1);
		p = Matlab.colon(p, 0, k-1);
		return new Object []{x,p};
	}
	
	public static Object[] normT(double [] x0, double [] t0){
		double T = 1;
		double[] t = new double [t0.length];
		int [] pi = new int [x0.length];
		x0 = smoothing(x0, 100);
		Object[] res = findpeaks(x0);
		pi = (int[]) res[1];
		if(pi.length>=2){
			T = Math.abs(t0[pi[0]]-t0[pi[1]]);
			for (int i = 0; i < t0.length; i++) {
				t[i] = t0[i]/T;
			}
		}
		else{
			T = 0;							//wenn keine 2 Maxima
			t = Matlab.zeros(t0.length);
		}
		return new Object []{T,t};
	}

	
	public static void main(String[] args) {
		
		double[] t = Matlab.linspace(0.0, 15.0, 100);
		
		Filter filter = FilterFactory.createButter(6, 1.0);
		System.out.println(filter);
		double[] step_soll = (double[])SVTools.step(filter.B, filter.A, t)[0];
		Filter filter1=FilterFactory.createCheby1(3, 1, 1);
		System.out.println(filter1);
		double [] step_antwort=(double[]) SVTools.step(filter1.B, filter1.A, t)[0];
		int ns=3;
		int ne=3;
		for (int i = 0; i < step_antwort.length; i++) {
			System.out.println(""+step_antwort[i]);
		}
		Object[] ress=optStep(t, step_antwort, ns, ne);
    	double[] coeff=(double[]) ress[0];
    			
    	double fehler=(double) ress[1];
    	double iter=(double) ress[2];
    	Complex[] nullstellen=Matlab.roots(coeff);
    	for (int i = 0; i < coeff.length; i++) {
			System.out.println("Koeffizienten    :"+coeff[i]);
		}
     	System.out.println("Fehlersumme"+fehler);
   	System.out.println("Iterationen"+iter);
    	for (int i = 0; i < nullstellen.length; i++) {
			System.out.println("Real  :"+nullstellen[i].getReal()+"         Imag  :"+nullstellen[i].getImaginary());
		}
//double [] butter=butterIniC(1, 3, 10);
    	double[] reim=wqtoReIm(coeff, 3);
    	for (int i = 0; i < reim.length; i++) {
			System.out.println("RealImaginar von Coeff"+reim[i]);
		}
Object[] testgen=genFraq(coeff, 3);
double[] num=(double[]) testgen[0];
double[] den=(double[]) testgen[1];
System.out.println("Num"+num[0]);
for (int i = 0; i < den.length; i++) {
	System.out.println("den"+den[i]);
}
//for (int i = 0; i < butter.length; i++) {
//	System.out.println(""+butter[i]);
//}
		
		
	}
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

}
