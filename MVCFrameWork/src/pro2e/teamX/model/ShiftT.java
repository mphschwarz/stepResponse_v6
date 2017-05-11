//package pro2e.teamX.model;
//
//import pro2e.teamX.matlabfunctions.Matlab;
//import pro2e.teamX.matlabfunctions.MatlabFunktionen;
//import pro2e.teamX.matlabfunctions.SVTools;
//
//public class ShiftT {
//
//	public double[] shiftT(double[] t, double[] y, double[] c, int n, int N) {		
//		
//		int ix5 = 1;
//		int ixm = 1;
//		int ix0 = 1;
//		
//		int iy5 = 1;
//		int iym = 1;
//		int iy0 = 1;
//		
//		int dt, di; 	// Datentyp ???
//		
//		double[] tx;
//		double[] shiftT;
//		double[] genFrag;
//				
//		tx = Matlab.linspace(0, t.length-1, t.length);
//		
//		double genFraq[] = MatlabFunktionen.genFraq(MatlabFunktionen.butterIniC(1, n, N), n);
//		genFraq[num,den] = genFraq(butterIniC(1,n,N),n);
//		
//		
//		double[] x = SVTools.step(B, A, tx);
//		x = SVtool.step(num,den,tx)
//
//		while ((x[ix5] < 0.5 && y[ix5++] > 0.5) == false) {
//			ix5++;
//		}
//		
//		
//		Math.max
//		[~, ixm] = max(x);	% finds peak of butter
//		tx = tx/(tx(ixm)-tx(ix5));	% normalizes tx
//
//		tx5 = tx(ix5);
//		
//		while ((y[iy5] < 0.5 && y[iy5++] > 0.5) == false) {
//			iy5++;
//		}
//		
//		[~, iym] = findpeaks(y(iy5:end));
//		iym = iym(1) + iy5;
//		ty = t/(t(iym) - t(iy5));
//		
//		
//		ty5 = ty * iy5;
//		dt = ty5 - tx5;
//		di = (-1)*(int)(dt/(t[1] - t[2]));
//		
//		shiftT[0] = dt;
//		shiftT[1] = dt;
//		
//		return shiftT;
//	}
//
//}