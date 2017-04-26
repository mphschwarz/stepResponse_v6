package pro2e.teamX.userinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Observable;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.DomainOrder;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.statistics.*;

public class SchrittantwortPlot extends JPanel {
	private JFreeChart chart = ChartFactory.createXYLineChart("", "Zeit", "Antwort", null, PlotOrientation.VERTICAL,
			false, false, false);;

	public SchrittantwortPlot() {
		this.setLayout(new FlowLayout());
		this.setPreferredSize(new Dimension(800, 400));
	//	this.setBorder(MyBorderFactory.createMyBorder(" Schrittantwort "));

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
		
		//
		

		
	}
	

	void setData(double[] x, double[] y) {
		XYSeries series = new XYSeries("Schrittantwort");
		for (int i = 1; i < x.length; i++) {
			series.add(x[i], y[i]);
		}
		XYPlot xyplot = chart.getXYPlot();
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);
		xyplot.setDataset(dataset);
	}


	public void update(Observable obs, Object obj) {
//		Model model = (Model) obs;

		repaint();
	}

}