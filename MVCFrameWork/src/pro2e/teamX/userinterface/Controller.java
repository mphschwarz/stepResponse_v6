package pro2e.teamX.userinterface;

import java.util.StringTokenizer;

import pro2e.teamX.MVCFramework;
import pro2e.teamX.model.Model;

public class Controller {
	private Model model;
	private MVCFramework mvcFramework;
	TopView topView;
	public int startOrd=1;
	public int endOrd=10;

	public Controller(Model model, MVCFramework mvcFramework) {
		this.model = model;
		this.mvcFramework = mvcFramework;

	}

	public void setView(TopView TopView) {
		this.topView = TopView;
	}

	public void setParameter() {
//		int ordnungVon=1;
//		int ordnungBis=10;
		model.setParameter(startOrd, endOrd, 0, 0);

		System.out.println("Test Controller");
	//	model.testFilter();
		
		
	}

	private double[] stringToCoeff(String s) {
		StringTokenizer tokenizer = new StringTokenizer(s, ", ");
		double[] res = new double[tokenizer.countTokens()];
		for (int i = 0; i < res.length; i++) {
			res[i] = Double.parseDouble(tokenizer.nextToken());
		}
		return res;
	}

	public void setOrdnungVon(int ns) {
		this.startOrd=ns+1;
		// TODO Auto-generated method stub
		
	}

	public void setOrdnungBis(int ne) {
		this.endOrd=ne+1;
		System.out.println("Startordung"+startOrd+"EndOrdnung "+this.endOrd);
	}



	

}
