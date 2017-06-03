package JUnit;

import static org.junit.Assert.*;
import org.junit.Test;
import pro2e.teamX.matlabfunctions.*;

public class Tests {
	
	MatlabFunktionen3 tester = new MatlabFunktionen3();
	
	double abw = 0.001;	// Abweichung
	
	@Test
	public void butterIniCTest() {
		double[][] but = new double[10][];
		
		but[0] = new double[]{1, -1};
		but[1] = new double[]{1, 1, 0.70711};
		but[2] = new double[]{1, 1, 1, -1};
		but[3] = new double[]{1, 1, 1.3066, 1, 0.5412};
		but[4] = new double[]{1, 1, 1.618, 1, 0.61803, -1};
		but[5] = new double[]{1, 1, 1.9319, 1, 0.70711, 1, 0.51764};
		but[6] = new double[]{1, 1, 2.247, 1, 0.80194, 1, 0.55496, -1};
		but[7] = new double[]{1, 1, 2.5629, 1, 0.89998, 1, 0.60134, 1, 0.5098};
		but[8] = new double[]{1, 1, 2.8794, 1, 1, 1, 0.6527, 1, 0.53209, -1};
		but[9] = new double[]{1, 1, 3.1962, 1, 1.1013, 1, 0.70711, 1, 0.56116, 1, 0.50623};
		
		for (int i = 0; i < but.length; i++) {
			assertArrayEquals("ButterCheck " + (i+1) + ".Ordnung", but[i], tester.butterIniC(1, i+1, 10), abw);
		}		
	}
	
	
	@Test
	public void wqtoReImTest() {
		double[][] wqtoReIm = new double[10][];
		
		wqtoReIm[0] = new double[]
				{-1,	// re
				0,		// im
				1};		// k
		
		wqtoReIm[1] = new double[]
				{-0.7071, -0.7071,
				0.7071, -0.7071,
				1};
		
		wqtoReIm[2] = new double[]
				{-0.5000, -0.5000, -1.0000,
				0.8660, -0.8660, 0,
				1};
		
		wqtoReIm[3] = new double[]
				{-0.3827, -0.3827, -0.9239, -0.9239,
				0.9239, -0.9239, 0.3827, -0.3827,
				1};
		
		wqtoReIm[4] = new double[]
				{-0.3090, -0.3090, -0.8090, -0.8090, -1.0000,
				0.9511, -0.9511, 0.5878, -0.5878, 0,
				1};
		
		wqtoReIm[5] = new double[]
				{-0.2588, -0.2588, -0.7071, -0.7071, -0.9659, -0.9659,
				0.9659, -0.9659, 0.7071, -0.7071, 0.2588, -0.2588,
				1};
		
		wqtoReIm[6] = new double[]
				{-0.2225, -0.2225, -0.6235, -0.6235, -0.9010, -0.9010, -1.0000,
				0.9749, -0.9749, 0.7818, -0.7818, 0.4339, -0.4339, 0,
				1};
		
		wqtoReIm[7] = new double[]
				{-0.1951, -0.1951, -0.5556, -0.5556, -0.8315, -0.8315, -0.9808, -0.9808,
				0.9808, -0.9808, 0.8315, -0.8315, 0.5556, -0.5556, 0.1951, -0.1951,
				1};
		
		wqtoReIm[8] = new double[]
				{-0.1736, -0.1736, -0.5000, -0.5000, -0.7660, -0.7660, -0.9397, -0.9397, -1.0000,
				0.9848, -0.9848, 0.8660, -0.8660, 0.6428, -0.6428, 0.3420, -0.3420, 0,
				1};
		
		wqtoReIm[9] = new double[]
				{-0.1564, -0.1564, -0.4540, -0.4540, -0.7071, -0.7071, -0.8910, -0.8910, -0.9877, -0.9877,
				0.9877, -0.9877, 0.8910, -0.8910, 0.7071, -0.7071, 0.4540, -0.4540, 0.1564, -0.1564,
				1};
		
		for (int i = 0; i < wqtoReIm.length; i++) {
			assertArrayEquals("wqtoReImCheck " + (i+1), wqtoReIm[i], tester.wqtoReIm(MatlabFunktionen3.butterIniC(1, i+1, 10), i+1), abw);
		}
		
	}
	
	
	@Test
	public void genFraqTest(){
		
		Object[][] genFraqRef = new Object[10][];
		Object[][] genFraq = new Object[10][];
		double[][] num = new double[10][];
		double[][] den = new double[10][];
		
		num[0] = new double[]{1};
		den[0] = new double[]{1, 1};
		
		num[1] = new double[]{1};
		den[1] = new double[]{1, 1.4142, 1};
		
		num[2] = new double[]{1};
		den[2] = new double[]{1, 2, 2, 1};
		
		num[3] = new double[]{1};
		den[3] = new double[]{1, 2.6131, 3.4142, 2.6131, 1};
		
		num[4] = new double[]{1};
		den[4] = new double[]{1, 3.2361, 5.2361, 5.2361, 3.2361, 1};
		
		num[5] = new double[]{1};
		den[5] = new double[]{1, 3.8637, 7.4641, 9.1416, 7.4641, 3.8637, 1};
		
		num[6] = new double[]{1};
		den[6] = new double[]{1, 4.4940, 10.0978, 14.5918, 14.5918, 10.0978, 4.4940, 1};
		
		num[7] = new double[]{1};
		den[7] = new double[]{1, 5.1258, 13.1371, 21.8462, 25.6884, 21.8462, 13.1371, 5.1258, 1};
		
		num[8] = new double[]{1};
		den[8] = new double[]{1, 5.7588, 16.5817, 31.1634, 41.9864, 41.9864, 31.1634, 16.5817, 5.7588, 1};
		
		num[9] = new double[]{1};
		den[9] = new double[]{1, 6.3925, 20.432, 42.802, 64.882, 74.233, 64.882, 42.802, 20.432, 6.3925, 1};
		
		for (int i = 0; i < genFraqRef.length; i++) {
			genFraqRef[i] = new Object[] {num[i], den[i]};
		}
		
		for (int i = 0; i < genFraq.length; i++) {
			genFraq[i] = tester.genFraq(MatlabFunktionen3.butterIniC(1, i+1, 10), i+1);
			assertArrayEquals("genFraqCheck "+ (i+1) +". num", (double[])genFraqRef[i][0], (double[])genFraq[i][0], abw);
			assertArrayEquals("genFraqCheck "+ (i+1) +". den", (double[])genFraqRef[i][1], (double[])genFraq[i][1], abw);
		}
	}

}
