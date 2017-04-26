package pro2e.teamX.model;

import java.util.Observable;

import pro2e.teamX.matlabfunctions.Matlab;

public class Model extends Observable {
	private DataImport dataImport;
	private double[] x0;
	private int ordnungvon,ordnungbis;
	public Skalierung skalierung;
	public Messung messung = new Messung();

	public Model() {


	}

	public void setParameter(double[] x0,int ordnungvon,int ordnungbis) {
		this.x0 = x0;
		this.ordnungvon=ordnungvon;
		this.ordnungbis=ordnungbis;
		System.out.println("Model.setParameter");
		notifyObservers();
	}

	public void notifyObservers() {
		setChanged();
		super.notifyObservers();
	}



}
