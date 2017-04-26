
package pro2e.teamX.matlabfunctions;

import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.NelderMeadSimplex;

public abstract class MatlabFunktionen {
	public NelderMeadSimplexSinusCosExample nelderMeadSimplexSinusCosExample;
	public static final double[] error(Object[] c, double[] t, double[] x, int n) {
		double[] r = new double[x.length];
		double[] y = stepResponse(c, t, n);
		for (int i = 0; i < x.length; i++) {

			r[i] += Math.pow((y[i] - x[i]), 2);
		}
		return r;

	}

	public static double[] stepResponse(Object[] c, double[] t, int n) {
		int den = 1;
		double[] k = (double[]) c[0];
		if (c.length % 2 == 1) {
			int odd = 1;

		}
		return t;

	}
	public static Object[] optStep(double[] t,double[] x,int ns, int ne,Object[] c){
		
		double [] val=new double [10];
		double [] iter=new double [10];
		double [] ef=new double [10];
		for (int r = 0; r < ns-ne; r++) {
			
		}
		
		
		return null;
		
	}

	public static void main(String[] args) {

	}

}
