package pro2e.teamX.matlabfunctions;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.optim.InitialGuess;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.NelderMeadSimplex;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.SimplexOptimizer;

public class NelderMeadQOmegaExample {

	public static Object[] xToCoeff(double[] x) {

		// 		Matlab-Code:
		//		B = x(1);
		//		A = [1, (x(3)/x(2)), x(3).^2];
		//		for k=4:2:length(x)-1,
		//		    A = conv(A, [1, (x(k+1)/x(k)), x(k+1).^2]);
		//		end;

		double[] B = { x[0] };
		double[] A = { 1.0, (x[2] / x[1]), x[2] * x[2] };

		for (int k = 3; k < x.length; k += 2) {
			A = Matlab.conv(A, new double[] { 1.0, (x[k + 1] / x[k]), x[k + 1] * x[k + 1] });
		}

		return new Object[] { B, A };
	}

	public static double[] coeffToX(double[] B, double[] A) {

		// 		Matlab-Code:
		//		rA = roots(A);
		//		x = B(end);
		//		for k = 1:2:length(rA)
		//		    x = [x, abs(rA(k))/(2*abs(real(rA(k)))), abs(rA(k))];
		//		end;

		Complex[] rA = Matlab.roots(A);
		double[] x = { B[B.length - 1] };

		for (int k = 0; k < rA.length; k += 2) {
			x = Matlab.concat(x, new double[] { (rA[k].abs()) / (2 * Math.abs((rA[k].getReal()))), (rA[k].abs()) });
		}

		return x;
	}

	public static double[] stepEstimate(double[] t, double[] x) {

		// 		Matlab-Code:
		//		[B, A] = XToCoeff(x);
		//		step_e = step(tf(B, A), t);

		Object[] c = xToCoeff(x);
		double[] B = (double[]) c[0];
		double[] A = (double[]) c[1];

//		return (double[]) SVTools.schrittIFFT(B, A, t)[0];
		return (double[]) SVTools.step(B, A, t)[0];
	}

	private static class Target implements MultivariateFunction {
		double[] step_soll;
		double[] t;
		public double[] error = {};

		public Target(double[] step_soll, double[] t) {
			this.step_soll = step_soll;
			this.t = t;
		}

		public double value(double[] x) {

			// 		Matlab-Code:
			//		step_ist = StepEstimate(t, x);
			//		e = sum((step_soll-step_ist).^2);
			//		error = [error e];

			double[] step_ist = stepEstimate(t, x);
			double error = 0.0;
			for (int i = 0; i < step_ist.length; i++) {
				error += (step_soll[i] - step_ist[i]) * (step_soll[i] - step_ist[i]);
			}

			this.error = Matlab.concat(this.error, error / t.length);

			return error;
		}
	}

	public static void main(String[] args) {
		Filter filterIni = FilterFactory.createButter(6, 1.0);

		Filter filterSoll = FilterFactory.createCheby1(4, 1, 1.0);
		double[] t = Matlab.linspace(0.0, 25.0, 64);

		double[] step_soll = (double[]) SVTools.step(filterSoll.B, filterSoll.A, t)[0];

		SimplexOptimizer optimizer = new SimplexOptimizer(1e-12, 1e-16);
		Target target = new Target(step_soll, t);

		double[] x_ini = coeffToX(filterIni.B, filterIni.A);
		double[] steps = new double[x_ini.length];
		for (int i = 0; i < x_ini.length; i++) {
			steps[i] = 0.005;
		}

		PointValuePair optimum = optimizer.optimize(new MaxEval(10000), new ObjectiveFunction(target), GoalType.MINIMIZE,
				new InitialGuess(x_ini), new NelderMeadSimplex(steps));

		Object[] x_ist = xToCoeff(optimum.getPoint());
		double[] B_ist = (double[]) x_ist[0];
		double[] A_ist = (double[]) x_ist[1];
		double[] step_ist = (double[]) SVTools.step(B_ist, A_ist, t)[0];

		Matlab.print("B_ist", B_ist);
		Matlab.print("A_ist", A_ist);

		Matlab.print("B_soll", filterSoll.B);
		Matlab.print("A_soll", filterSoll.A);

		Matlab.print("t", t);
		Matlab.print("step_soll", step_soll);
		Matlab.print("step_ist", step_ist);

		Matlab.print("error", target.error);
	}

}
