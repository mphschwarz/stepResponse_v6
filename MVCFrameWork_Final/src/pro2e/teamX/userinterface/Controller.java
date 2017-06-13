package pro2e.teamX.userinterface;

import pro2e.teamX.MVCFramework;
import pro2e.teamX.model.Model;
import pro2e.teamX.model.PREFERENZEN;

public class Controller {
	public Model model;
	TopView topView;
	public int startOrd = 1;
	public int endOrd = 10;
	public int filterTyp;
	public int fensterbreite;
/**
 * Setzt die Attribute Model und mvcFramework.
 * @param model
 * @param mvcFramework
 */
	public Controller(Model model, MVCFramework mvcFramework) {
		this.model = model;

	}
/**
 * Setzt das Attribute TopView.
 * @param TopView
 */
	public void setView(TopView TopView) {
		this.topView = TopView;
	}
/**
 * setParameter setzt die Start und End Ordnung. Falls die Startordnung gr�sser ist als die Endordnung werden die beiden Eintr�ge getauscht.
 * Ruft die entsprechende Methode des Models auf.
 */
	public void setParameter() {

		if (this.startOrd >= this.endOrd) {
			int temp;
			temp = startOrd;
			this.startOrd = endOrd;
			this.endOrd = temp;
		}
		try {
			model.tend = (int) (model.t0.length - 1.0 / 25.0 * model.t0.length);
		
		} catch (Exception e) {
			model.tstart = (int) (1.0 / 20.0 * model.t0.length);
			PREFERENZEN.tStart=0;
			
			
		}
		model.setParameter(startOrd, endOrd, 0, 0);
		

	}

/**
 * Setzt die Startordnung.
 * @param ns
 */
	public void setOrdnungVon(int ns) {
		this.startOrd = ns + 1;
		

	}
/**
 * Setzt die Endordnung.
 * @param ne
 */
	public void setOrdnungBis(int ne) {
		this.endOrd = ne + 1;
		
	}
public void filtern(int parseInt) {
	model.filtern(parseInt);
	
}
public void unfiltern(){
	model.unfiltern();
}


}