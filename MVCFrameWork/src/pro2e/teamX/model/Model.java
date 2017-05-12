package pro2e.teamX.model;

import java.util.Observable;

import org.apache.commons.math3.complex.Complex;

import jdk.nashorn.internal.codegen.OptimisticTypesPersistence;
import pro2e.teamX.matlabfunctions.Filter;
import pro2e.teamX.matlabfunctions.FilterFactory;
import pro2e.teamX.matlabfunctions.Matlab;
import pro2e.teamX.matlabfunctions.MatlabFunktionen;
import pro2e.teamX.matlabfunctions.MatlabFunktionen3;
import pro2e.teamX.matlabfunctions.SVTools;
import pro2e.teamX.userinterface.SchrittantwortPlot;
import pro2e.teamX.userinterface.StatusBar;
import pro2e.teamX.userinterface.TopView;

public class Model extends Observable {
	private DataImport dataImport;
	private SchrittantwortPlot schrittantwortPlot;
	public double[] x0;
	public double[] t0;
	public double[] y0;
	public double[] t1 = null;
	public double[] x = null;
	public double[] xparse = null;
	public double[] yIst = null;
	public int tstart = 100;
	public int tend = 2000;
	public double[] ty = null;
	public double[][] coeffDen = new double[10][11];
	public boolean gerechnet = false;

	private int startOrd, endOrd;
	public Skalierung skalierung;
	public Messung messung = new Messung();
	public int filter;
	public int fensterbreite;
	public double zd = 0.004;
	public int sf = 25;
	//	private Filter filter;

	public Model() {

	}

	public void setParameter(int ordnungvon, int ordnungbis, int filter, int fensterbreite) {

		this.startOrd = ordnungvon;
		this.endOrd = ordnungbis;
		this.filter = filter;
		this.fensterbreite = fensterbreite;

		berechenParseData();

		System.out.println("Model.setParameter");
		//notifyObservers();
	}

	public void berechenParseData() {
		Object[] parseDataObj = MatlabFunktionen3.parseData(this.y0, this.t0, zd, sf, tstart, tend);
		this.t1 = (double[]) parseDataObj[0];
		this.x = (double[]) parseDataObj[1];
		this.x0 = (double[]) parseDataObj[2];
		this.tstart = (int) parseDataObj[3];
		this.tend = (int) parseDataObj[4];
		//	for (int i = 0; i < x.length; i++) {
		//		System.out.println("xnachparse  "+x[i]+" vor   "+y0[i]);
		//	}
		berechneNormT();

	}

	public void berechneNormT() {
		Object[] normTobj = MatlabFunktionen3.normTneu(x, t1);
		this.ty = (double[]) normTobj[0];
		double T = (double) normTobj[1];
		StatusBar.showStatus("Signal gefilltert und ZeitVektor Normiert");

		//	System.out.println(T);
		//	for (int i = 0; i < ty.length; i++) {
		//		System.out.println("ty nach NormT    "+ty[i]+"    orginalvektor    "+t0[i]);	
		//}
		berechnungOptStep();
	}

	private void berechnungOptStep() {
		double[] iter = Matlab.zeros(10);
		double[] fehler = new double[10];
		int pos = 0;
		double fürMinimum = Double.POSITIVE_INFINITY;
		for (int i = 0; i < fehler.length; i++) {
			fehler[i] = Double.POSITIVE_INFINITY;
		}

		double[][] coeff = new double[10][11];
		
		for (int i = startOrd; i <= endOrd; i++) {
			Object[] optStepObj = MatlabFunktionen3.optStep(ty, x, i);
			iter[i] = (double) optStepObj[2];
			fehler[i] = (double) optStepObj[1];
			double[] coefford = (double[]) optStepObj[0];
			for (int j = 0; j < coefford.length; j++) {
				coeff[i][j] = coefford[j];
			}

		}
		for (int i = 0; i < fehler.length; i++) {
			if (fehler[i] < fürMinimum) {
				pos = i + 1;
				fürMinimum = fehler[i];
			}
		}
		
		System.out.println("" + pos);
		double [] coeffBesteOrdung= new double[pos+1];
			for (int j = 0; j < coeffBesteOrdung.length; j++) {
				coeffBesteOrdung[j]=coeff[pos-1][j];
			}

		
		Object[] genfrag = MatlabFunktionen3.genFraq(coeffBesteOrdung, pos-1);
		double[] num = (double[]) genfrag[0];
		double[] den = (double[]) genfrag[1];
		Object[] step_ist = SVTools.step(num, den, ty);
		this.yIst = (double[]) step_ist[0];
		
		gerechnet = true;
				notifyObservers();
		// Code funktioniert für eine Ordnung

		//		Object[] optStepObj = MatlabFunktionen3.optStep(ty, x, startOrd);
		//		double[] coeff = (double[]) optStepObj[0];
		//		double fehler = (double) optStepObj[1];
		//		double iter = (double) optStepObj[2];
		//		Object[] genfrag = MatlabFunktionen3.genFraq(coeff, startOrd);
		//		double[] num = (double[]) genfrag[0];
		//		double[] den = (double[]) genfrag[1];
		//		Object[] step_ist = SVTools.step(num, den, ty);
		//		this.yIst = (double[]) step_ist[0];
		//		for (int i = 0; i < yIst.length; i++) {
		//			System.out.println("Antwort von fMinSearch  " + yIst[i] + "   orginal" + x[i]);
		//		}
		//	
		//		for (int i = 0; i < step_ist.length; i++) {
		//			System.out.println(fehler);
		//		}
		//		gerechnet = true;
		//		notifyObservers();
		//			}
	}

	public void setEingabeData(double[] t1, double[] x1, double[] y1) {
		this.t0 = new double[t1.length];
		this.x0 = new double[x1.length];
		this.y0 = new double[y1.length];

		this.t0 = t1;
		this.x0 = x1;
		this.y0 = y1;
		System.out.println("model.setEingabeData");

		notifyObservers();

	}

	@Override
	public void notifyObservers() {
		System.out.println("model.notifyObservers");
		setChanged();
		super.notifyObservers();
	}

}
