package pro2e.teamX.userinterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import pro2e.teamX.model.Model;

public class SchrittantwortPlot extends JPanel implements Observer {
	private JFreeChart chart = ChartFactory.createXYLineChart("", "Zeit", "Antwort", null, PlotOrientation.VERTICAL, false,
			true, false);
	private TextField fileName;

	/**
	 * SchrittantwortPlot erzeugt einen neuen Chart und Legt die Achsen fest. SchrittantwortPlot wird verwendet um die Schrittantwort Grafisch auszugeben. 
	 * Da wird ersichtlich ob die Funktion Approximiert oder nicht.		
	 */
	public SchrittantwortPlot() {
		this.setLayout(new GridLayout(1, 1));
		setBorder(MyBorderFactory.createMyBorder(" Schrittantwort "));

		// Farben und Settings
		chart.setBackgroundPaint(getBackground());
		XYPlot xyplot = chart.getXYPlot();
		xyplot.setBackgroundPaint(getBackground());
		xyplot.setRangeGridlinePaint(Color.black);
		xyplot.setDomainGridlinePaint(Color.black);
		xyplot.getRenderer().setSeriesPaint(0, Color.black);

		ValueAxis axis = xyplot.getDomainAxis();
		axis.setRange(0, 1e4);
		axis.setAutoRange(true);
		axis.setLabelPaint(Color.black);
		axis.setTickLabelPaint(Color.black);
		axis = xyplot.getRangeAxis();
		axis.setRange(-Math.PI, Math.PI);
		axis.setAutoRange(true);
		axis.setLabelPaint(Color.black);
		axis.setTickLabelPaint(Color.black);

		ChartPanel panel = new ChartPanel(chart);

		add(panel);
		/**
		 * Setzt die erhaltenen Daten (step_soll,step_ist, zeit) in die jeweilige Serie und fügt die anschliessend am Chart hinzu.
		 */

	}

	public void setData(double[] signal1, double[] signal2, double[] t) {
		XYSeries series1 = new XYSeries("Gegebene Funktion");
		for (int i = 1; i < signal1.length; i++)
			series1.add(t[i], signal1[i]);

		XYSeries series2 = new XYSeries("Modellierte Funktion");
		for (int i = 1; i < signal2.length; i++)
			series2.add(t[i], signal2[i]);

		XYPlot xyplot = chart.getXYPlot();
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		xyplot.setDataset(dataset);
		System.out.println("Daten werden gesetzt");
	}

	/**
	 * Holt sich die entsprechenden Daten des Models und ruft die setData() Methode.
	 */
	public void update(Observable obs, Object obj) {
		System.out.println("update.schrittantwort");

		Model model = (Model) obs;
		if (model.gerechnet == false) {
			setData(model.x0, model.y0, model.t0);
		}
		if (model.gerechnet == true) {
			setData(model.xhammer, model.yhammer, model.thammer);

			repaint();
		}

		repaint();
	}

}