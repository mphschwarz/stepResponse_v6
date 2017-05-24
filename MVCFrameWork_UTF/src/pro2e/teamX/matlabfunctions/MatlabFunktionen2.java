
package pro2e.teamX.matlabfunctions;

import java.util.Arrays;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.optim.InitialGuess;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.NelderMeadSimplex;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.SimplexOptimizer;
import org.apache.commons.math3.util.FastMath;

public abstract class MatlabFunktionen2 {
	public NelderMeadSimplexSinusCosExample nelderMeadSimplexSinusCosExample;
	public double[][] c0 = new double[10][10];
	static double[] w;
	static double[] q;
	static double betrag;
	    static double []c;
	    

	public static final double[] error(double[] c, double[] t, double[] x, int n) {
		double[] r = new double[x.length];
		double[] y = stepResponse(c, t, n);
		for (int i = 0; i < x.length; i++) {

			r[i] += Math.pow((y[i] - x[i]), 2);
		}
		return r;

	}

	public static double[] butterIniC(double k, int n, int N) {
		if (n % 2 == 0) {
			n = n / 2;

			double[] l = Matlab.linspace(Math.PI / 2, Math.PI, n + 1);
			double[] re = new double[n+1 ];

			for (int i = 0; i < n; i++) {
				re[i] = Math.cos(l[i]);
			}
			double[] im = new double[n+1];
			for (int i = 0; i < n; i++) {
				im[i] = Math.sin(l[i]);
			}
			w = new double[n+1];
			for (int i = 0; i < n; i++) {

				w[i] = Math.sqrt(Math.pow(re[i], 2) + Math.pow(im[i], 2));
			}
			double[] abs = new double[n+1];
			for (int i = 0; i < n; i++) {

				abs[i] = Math.abs(re[i]);
			}
			for (int i = 0; i < n; i++) {
				q = new double[n+1];
				q[i] = w[i] / (2 * abs[i]);
			}
			 c = new double[11];
			c = Matlab.concat(w, q);
			
			
			//            for (int i=0;i<=w.length*2;i++){
			//          
			//               c[i]=w[i];        
			//               c[i+1]=q[i];  
			//                      
			//                 }    
		}

		//		else{
		//            n=n-1/2;
		//            double []re=Matlab.linspace(Math.PI/2,Math.PI,n+1);
		//            for (int i=0;i<n;i++){
		//                 re[i]=Math.cos(re[i]);
		//            }
		//            double []im=Matlab.linspace(Math.PI/2,Math.PI,n+1);
		//            for (int i=0;i<n;i++){
		//                 im[i]=Math.sin(im[i]);
		//            }w=new double [N];
		//            for (int i=0;i<n;i++){
		//            	
		//                 w[i]=Math.sqrt(Math.pow(re[i],2)+Math.pow(im[i],2));
		//            }
		//            for (int i=0;i<n;i++){
		//                 betrag = betrag + re[i];
		//            }
		//            for (int i=0;i<n;i++){
		//                 q=new double[n];
		//                 q[i]=w[i]/(2*betrag);
		//            }
		//            c=new double[N];
		//            c[0]=k;
		//            for (int i=1;i<=n*2;i++){
		//                 if(i%2==0){
		//                      c[i]=q[(i/2)-1];
		//                 }else{
		//                      c[i]=w[(i/2)]; 
		//                 }    
		//            }
		//    }
		return c;
	}

	public static double[] stepResponse(double[] c, double[] t, int n) {
		int den = 1;
		double[] k = new double[c.length];
		k[0] = c[0];
		System.arraycopy(c, 1, c, 0, c.length);
		if (c.length % 2 == 1) {
			int odd = 1;

		}
		return t;

	}

	public static Object[] optStep(double[] t, double[] x, int ns, int ne, double[] c) {
		double[] c0 = new double[10];
		double[] val = new double[10];
		double[] iter = new double[10];
		double[] ef = new double[10];
		SimplexOptimizer optimizer = new SimplexOptimizer(1e-10, 1e-30);//
		Target target = new Target();
		PointValuePair optimum = optimizer.optimize(new MaxEval(1000), new ObjectiveFunction(target), GoalType.MINIMIZE,
				new InitialGuess(new double[] { 7, 3.5 }), new NelderMeadSimplex(new double[] { 0.02, 0.02 }));

		System.out.println(Arrays.toString(optimum.getPoint()) + " : " + optimum.getSecond());

		for (int r = 1; r < ns - ne; r++) {
			optimum.getValue();
		}

		return null;

	}
	public static Object[] genFrag(double [] c, int n){
		double[] den = new double [n+1];
		den[0] = 1;
		double [] ck = new double[c.length-1];
		double[] ckk = new double[ck.length-1];
		double [] trimc = new double[n+1];
		double[] wp = new double [Math.floorDiv(n, 2)];
		double[] qp = new double [Math.floorDiv(n, 2)];
		double num = 1;
		double k = c[0];
		double odd = 1;
		double s = 1;

		ck = Matlab.colon(c, 1, c.length-1);
		if(n%2 == 1){
			odd = 1;
			n = n-1;
			s = ck[ck.length-1];
			ckk = Matlab.colon(ck, 0, ck.length-2);
		}
		else{
			 odd = 0;
			 ckk = Matlab.colon(c, 0, c.length-1);
		}
		trimc = Matlab.colon(ckk, 0, n-1);
		wp = Matlab.colonColon(trimc, 0, 2, trimc.length-1);
		qp = Matlab.colonColon(trimc, 1, 2, trimc.length-1);
		for (int i = 0; i < wp.length; i++) {
			 den = Matlab.conv(new double[]{1, wp[i]/qp[i], Math.pow(wp[i], 2)}, den);
			 num *= Math.pow(wp[i], 2);
		}
		num = num*k;
		if(odd == 1){
			num = num*Math.abs(s);
			den = Matlab.conv(den, new double[]{1,s});
		}
		return new Object []{num,den};
	}
	
	

	public static void main(String[] args) {
		double[] dubidu = Matlab.linspace(Math.PI / 2, Math.PI, 2);
		double[] buttterinic = new double [11];
		double[] c={1,2,3,4,5,6,7,8,9,10,11};
		int n=6;
		Filter filter;
		filter=FilterFactory.createButter(5, 1e3);
		System.out.println(""+filter.rA);
		Object[] res=genFrag(c, n);
		double num=(double) res[0];
		double[] den=(double[]) res[1];
		buttterinic = butterIniC(1, 8, 11);
//		for (int i = 0; i < dubidu.length; i++) {
//			System.out.println("" + dubidu[i]);
//		}

		//for (int i = 0; i < num.length; i++) {
			System.out.println("num: "+num);
		//}
		for (int i = 0; i < den.length; i++) {
			System.out.println("den: "+den[i]);
		}

	}

	private static class Target implements MultivariateFunction {// Fehlerfunktion 
		@Override
		public double value(double[] variables) {
			final double x = variables[0];
			final double y = variables[1];
			return FastMath.cos(x) * FastMath.sin(y);
		}
	}
}
