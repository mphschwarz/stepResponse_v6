package pro2e.teamX.userinterface;

import java.util.StringTokenizer;

import com.sun.glass.ui.View;

import pro2e.teamX.MVCFramework;
import pro2e.teamX.model.DataImport;
import pro2e.teamX.model.Model;

public class Controller {
	private Model model;
	private MVCFramework mvcFramework;
 TopView topView;
 

	public Controller(Model model, MVCFramework mvcFramework) {

		this.model = model;
		this.mvcFramework = mvcFramework;
		
	}
	public void setView(TopView TopView) {
		this.topView = TopView;

	}

	public void setParameter() {
		// 4
		System.out.println("Test Controller");
//		int ordnungvon = Integer.parseInt(topView.eingabePanel.tfOrdnung.getText());
//		int ordnungbis = Integer.parseInt(topView.eingabePanel.tfOrdnungbis.getText());
	//	double [] startwerte = stringToCoeff(topView.eingabePanel.tfStartwerte.getText());
//		model.setParameter(startwerte, 1, 2);
	}


	private double[] stringToCoeff(String s) {
		StringTokenizer tokenizer = new StringTokenizer(s, ", ");
		double[] res = new double[tokenizer.countTokens()];
		for (int i = 0; i < res.length; i++) {
			res[i] = Double.parseDouble(tokenizer.nextToken());
		}
		return res;
	}

}
