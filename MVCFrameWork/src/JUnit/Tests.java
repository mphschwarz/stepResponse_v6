package JUnit;

import static org.junit.Assert.*;
import org.junit.Test;
import pro2e.teamX.matlabfunctions.*;

public class Tests {
	
	MatlabFunktionen3 tester = new MatlabFunktionen3();
	
	double[] but1 = new double[]{1, -1};
	double[] but2 = new double[]{1, 1, 0.70711};
	double[] but3 = new double[]{1, 1, 1, -1};
	
	@Test
	public void butterIniCTest() {
		assertArrayEquals("ButterCheck 1.Ordnung", but1, tester.butterIniC(1, 1, 10), 0);
//		assertArrayEquals("ButterCheck 2.Ordnung", but2, tester.butterIniC(1, 2, 10), 0);
		assertArrayEquals("ButterCheck 3.Ordnung", but3, tester.butterIniC(1, 3, 10), 0);
		
	}

	
	public static String roundNumber(double x, int dp) {

		double log10 = Math.log10(x);
		int count = (int) Math.floor(log10);
		x = x / Math.pow(10.0, count);
		x = Math.round(x * Math.pow(10.0, dp)) / Math.pow(10.0, dp);
		if (count == 0)
			return ""+x;
		else
			return ""+x + "E" + count;
	}

}
