package pro2e.teamX.model;

public class Skalierung extends Model {
	public int xDelay;
	public double [] xoff;
	public double [] toff;
	public double [] yoff;
	
	 double[] testzeit = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };
	 double[] testx = { 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
	 double[] testy = { 0.0, 0.1, 0.2, 1.2, 3, 4, 5, 6, 7, 9, 11, 12, 12, 32 };

	Object test33 = new Object();
	
	
	public double[] setOffset(double[] y1,double offset){
		double[] yohne=new double [y1.length];
		for (int i = 0; i < y1.length; i++) {
			yohne[i]=y1[i]-offset;
		}
		return yohne;
	}
	//Mittels for schleife die Sprungstelle finden. Neuer Array erzeugen vom Zeitpunkt xDelay für alle 3 Arrays. neuzeichnen lassen.
	public Object[] finddelay(double[] x1,double []y1,double[] t1){
		for (int i = 0; i < x1.length; i++) {
			
			if(x1[i]>0.8){
				xDelay=(int) x1[i];
			}
			
	System.arraycopy(x1, xDelay, xoff, 0, x1.length);
	System.arraycopy(y1, xDelay, yoff, 0, y1.length);
	System.arraycopy(t1, xDelay, toff, 0, t1.length);
	
		}
		Object[] object={xoff,yoff,toff};
		
		return object;
		
		
		
	}
	public static void main(String[] args) {

	}

	

}
