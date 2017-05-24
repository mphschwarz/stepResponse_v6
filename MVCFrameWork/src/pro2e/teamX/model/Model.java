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
	public int tend = 2000;//2000 21.05.2017
	public double[] ty = null;
	public int dOrd;
	public double[][] coeffDen = new double[10][11];
	public boolean gerechnet = false;
	public double[] reButterIniC = null;
	public double[] imButterIniC = null;
	public double[] reBestOrd = null;
	public double[] imBestOrd = null;
	public boolean poleBestOrd = false;
	public double[] reButter = null;
	public double[] imButter = null;

	public double[] xhammer;
	public double[] thammer;
	public double[] yhammer;

	private int startOrd, endOrd;
	public Skalierung skalierung;
	public Messung messung = new Messung();
	public int filter;
	public int fensterbreite;
	public double zd = 0.004;
	public int sf = 25;
	//private Filter filter;

	public Model() {

	}

	public void setParameter(int ordnungvon, int ordnungbis, int filter, int fensterbreite) {

		this.startOrd = ordnungvon;
		this.endOrd = ordnungbis;
		this.filter = filter;
		this.fensterbreite = fensterbreite;
		System.out.println("L�nge daten " + t0.length);

		berechenParseData();
		//		setPNSpoles(ordnungvon);
		System.out.println("Model.setParameter");
		notifyObservers();
	}

	public void setButterworth() {
		reButter = new double[50];
		imButter = new double[50];
		double[] linspace = Matlab.linspace(Math.PI / 2, -Math.PI / 2, 50);

		System.out.println("Test set Butterworth");
		for (int i = 0; i < linspace.length; i++) {
			reButter[i] = -Math.cos(linspace[i]);
			imButter[i] = Math.sin(linspace[i]);
		}
	}

	private void setPNSpoles(int ordnungvon) {
		Filter darfilter = FilterFactory.createButter(ordnungvon, 1);
		Complex[] rA = Matlab.roots(darfilter.A);
		for (int i = 0; i < rA.length; i++) {
			System.out.println(rA[i].getReal());
			//			reButterIniC[i] = rA[i].getReal();
			//			imButterIniC[i] = rA[i].getImaginary();

		}
		for (int i = 0; i < rA.length; i++) {
			System.out.println(rA[i].getImaginary());
		}

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
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				double[] iter = Matlab.zeros(endOrd + 1);
				double[] fehler = new double[endOrd + 1];
				int[] td = new int[endOrd + 1];
				int[] d = new int[endOrd + 1];
				int pos = 0;
				double f�rMinimum = Double.POSITIVE_INFINITY;
				for (int i = 0; i < fehler.length; i++) {
					fehler[i] = Double.POSITIVE_INFINITY;
				}

				double[][] coeff = new double[endOrd + 1][endOrd + 2];

				for (int i = startOrd; i <= endOrd; i++) {

					Object[] optStepObj = MatlabFunktionen3.optStep(ty, x, i);
					
					if(PREFERENZEN.abbrechen){
						return;
					}

					iter[i] = (double) optStepObj[2];
					fehler[i] = (double) optStepObj[1];
					double[] coefford = (double[]) optStepObj[0];
					System.out.println("Iter" + iter[i]);

					//-------------ShiftT
					Object[] shiftOpt = MatlabFunktionen3.shiftT(ty, x, MatlabFunktionen3.butterIniC(1, i, 10), i);
					for (int z = 0; z < shiftOpt.length; z++) {
						td[z] = (int) shiftOpt[0];
						System.out.println(td[z] + z);
					}
					for (int k = 0; k < shiftOpt.length; k++) {
						if (td[k] < 0) {
							d[k] = 1;
							System.out.println("ShiftTist Unter 1" + d[k]);
						} else {
							d[k] = td[k];
						}
						System.out.println("ShiftT" + d[k]);
					}
					//
					for (int j = 0; j < coefford.length; j++) {
						coeff[i][j] = coefford[j];
					}

				}
				for (int i = 0; i < fehler.length; i++) {
					if (fehler[i] < f�rMinimum) {
						pos = i + 1;
						f�rMinimum = fehler[i];
					}
				}
				dOrd = d[pos - 1];
				System.out.println("positionOrdung:   " + pos);
				System.out.println("dOrdnung abschneiden" + dOrd);
				double[] coeffBesteOrdung = new double[pos + 1];
				for (int j = 0; j < coeffBesteOrdung.length; j++) {
					coeffBesteOrdung[j] = coeff[pos - 1][j];
				}

				Object[] genfrag = MatlabFunktionen3.genFraq(coeffBesteOrdung, pos - 1);
				double[] num = (double[]) genfrag[0];
				double[] den = (double[]) genfrag[1];
				Object[] step_ist = SVTools.step(num, den, ty);
				yIst = (double[]) step_ist[0];

				//-----------------------------

				//-----------------------------
				//Nullstellen berechen der Besten Ordnung
				Complex[] nullstellenBestOrd = Matlab.roots(den);
				//				int zaehler=0;
				double[] real = new double[nullstellenBestOrd.length];
				double[] imag = new double[nullstellenBestOrd.length];
				for (int i = 0; i < imag.length; i++) {
					real[i] = nullstellenBestOrd[i].getReal();
					imag[i] = nullstellenBestOrd[i].getImaginary();
				}
				setPolesBestOrd(real, imag);

				//---------

				//				for (int i = 0; i < nullstellenBestOrd.length; i++) {
				//					zaehler=i;
				//			//		imBestOrd[i]=nullstellenBestOrd[i].getImaginary();
				//				}
				//				reBestOrd=new double[zaehler];
				//				for (int j = 0; j < nullstellenBestOrd.length; j++) {
				//					reBestOrd[j]=nullstellenBestOrd[j].getReal();
				//				}
				//		------------------------
				// Zeitoptimierung f�r Plot
				timeSettings();
				//-----------------

				gerechnet = true;

				notifyObservers();
				// Code funktioniert f�r eine Ordnung

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
		});
		thread.start();
	}

	private void setPolesBestOrd(double[] real, double[] imag) {
		reBestOrd = new double[real.length];
		imBestOrd = new double[imag.length];
		for (int i = 0; i < imag.length; i++) {
			reBestOrd[i] = real[i];
			imBestOrd[i] = imag[i];
		}
		poleBestOrd = true;
	}

	private void timeSettings() {
		thammer = Matlab.colon(this.ty, dOrd, this.ty.length - 1);
		yhammer = Matlab.colon(this.yIst, dOrd, this.yIst.length - 1);
		xhammer = Matlab.colon(this.x, dOrd, this.x.length - 1);

	}

	public void setEingabeData(double[] t1, double[] x1, double[] y1) {
		StatusBar.showStatus("Daten erfolgreich Importiert");
		StatusBar.showStatus("Ordnung ausw�hlen und berechen");
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
