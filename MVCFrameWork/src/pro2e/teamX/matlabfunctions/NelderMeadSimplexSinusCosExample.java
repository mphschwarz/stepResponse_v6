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

public class NelderMeadSimplexSinusCosExample {

	public static void main(String[] args) {
		
		
		SimplexOptimizer optimizer = new SimplexOptimizer(1e-10, 1e-30);//toleranzen dann bricht er ab
		Target target = new Target();//

		PointValuePair optimum = optimizer.optimize(new MaxEval(1000), new ObjectiveFunction(target), GoalType.MINIMIZE,
				new InitialGuess(new double[] { 3.5, 1.6 }), new NelderMeadSimplex(new double[] { 0.02, 0.02 }));
	//MaxEval
		//new NelderMeadSimplex(new double[] { 0.02, 0.02 }) Stepabstand
		//new InitialGuess(new double[] { 3.5, 1.6 }) Startwerte
		
		System.out.println(optimizer.getIterations());
		System.out.println(Arrays.toString(optimum.getPoint()) + " : " + optimum.getSecond());
	}

	public static class Target implements MultivariateFunction {// Fehlerfunktion 
		double[] stepsoll;
		double[] t;
		public Target(){}
		
		public Target(double[] step_soll,double[] t){
			this.stepsoll=step_soll;
			this.t=t;
		}
		
		public double value(double[] variables) {
			final double x = variables[0];
			final double y = variables[1];
			return FastMath.cos(x) * FastMath.sin(y);
		}
	}

}
