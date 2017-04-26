package pro2e.teamX.model;
import org.apache.*;
import java.math.*;

public class Messung {
	
	
	public Messung(){
		
	}
	public Object normT(double[] x0,double [] t0){
		double max=0;
		for (int i = 0; i < x0.length; i++) {
			if(x0[i]>max){
				max=x0[i];
			}
		}
		
		
		
		
		
		return max;
		
	}

	public static void main(String[] args) {
		new Messung();
		System.out.println("Test");


	}

}
