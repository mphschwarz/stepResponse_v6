package JUnit;

import static org.junit.Assert.*;
import org.junit.Test;
import pro2e.teamX.matlabfunctions.*;

public class Tests {
	
	MatlabFunktionen3 tester = new MatlabFunktionen3();
	
	double abw = 0.001;
	
	double[] but1 = new double[]{1, -1};
	double[] but2 = new double[]{1, 1, 0.70711};
	double[] but3 = new double[]{1, 1, 1, -1};
	
	@Test
	public void butterIniCTest() {
		assertArrayEquals("ButterCheck 1.Ordnung", but1, tester.butterIniC(1, 1, 10), abw);
		assertArrayEquals("ButterCheck 2.Ordnung", but2, tester.butterIniC(1, 2, 10), abw);
		assertArrayEquals("ButterCheck 3.Ordnung", but3, tester.butterIniC(1, 3, 10), abw);
	
		
	}

}
