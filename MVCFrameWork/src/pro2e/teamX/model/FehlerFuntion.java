package pro2e.teamX.model;

import pro2e.teamX.matlabfunctions.Matlab;
import pro2e.teamX.matlabfunctions.SVTools;

public class FehlerFuntion extends Model {
	private double[] t;
	private double[] y1;
	private double [] startwerte;
	private int ordnung;
	private SchrittFuntion schrittFuntion;
	private double e2;
	private double[] h;
	private double[] t1;
	
	public void Fehler(int ordnung, double[] startwerte,double [] t,double []y1){
		this.t=t;
		this.y1=y1;
		this.startwerte=startwerte;
		this.ordnung=ordnung;
		Object[] y2=schrittFuntion.Schritt(ordnung, startwerte, t);
		 h = (double[]) y2[0];
		 t1 = (double[]) y2[1];
		for(int i=0;i<y1.length;i++){
		e2=e2+Math.pow(y1[i]-h[i], 2);	
		
		}
	}

}
