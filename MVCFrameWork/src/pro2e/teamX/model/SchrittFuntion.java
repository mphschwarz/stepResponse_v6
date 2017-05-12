package pro2e.teamX.model;

import pro2e.teamX.matlabfunctions.Matlab;
import pro2e.teamX.matlabfunctions.SVTools;

public class SchrittFuntion extends Model {
	private double[] z1;
	private double[] n1;
	private double[] t;
	private Object[] y2;



	public Object[] Schritt(int ordnung, double[] startwerte, double[] t) {
		this.t = t;
		if (ordnung % 2 == 0) {
			this.z1[0] = startwerte[0] * Math.pow(startwerte[1], 2);

			this.n1[0] = 1;
			this.n1[1] = startwerte[1] / startwerte[2];
			this.n1[2] = Math.pow(startwerte[1], 2);
			int k = 2;
			while (k < ordnung - 2) {
				double[] n2 = { 1, startwerte[k + 2] / startwerte[k + 3], Math.pow(startwerte[k + 2], 2) };
				n1 = Matlab.conv(n1, n2);
				this.z1[k - 2] = this.z1[k - 2] * Math.pow(startwerte[k + 2], 2);
				k = k + 2;
			}
		} else {
			double z3 = startwerte[0] * Math.pow(startwerte[1], 2);
			double[] n3 = { 1, startwerte[1] / startwerte[2], Math.pow(startwerte[1], 2) };
			int k = 2;
			while (k < ordnung - 2) {
				double[] n4 = { 1, startwerte[k + 2] / startwerte[k + 3], Math.pow(startwerte[k + 2], 2) };
				n3 = Matlab.conv(n3, n4);
				z3 = z3 * Math.pow(startwerte[k + 2], 2);
				k = k + 2;
			}
			double[] h1 = { 1, -startwerte[ordnung + 1] };
			n3 = Matlab.conv(h1, n3);
			z3 = z3 * Math.abs(startwerte[ordnung + 1]);

			y2 = SVTools.step(z1, n1, t);

		}
		return y2;

	}
	public void main(String[] args) {
		int ordnung = 3;
		double[] startwerte = { 1, 2, 4, 5 };
		double[] t = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

		Object g = Schritt(ordnung, startwerte, t);
	}
}
