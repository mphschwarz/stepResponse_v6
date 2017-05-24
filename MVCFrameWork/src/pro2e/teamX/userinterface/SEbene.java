package pro2e.teamX.userinterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;

public class SEbene extends JPanel {
private JFreeChart jChart=ChartFactory.createXYAreaChart("", "Real", "Imag", null, PlotOrientation.VERTICAL, false, false, false);
 
public SEbene() {
	// TODO Auto-generated constructor stub

	this.setLayout(new GridLayout());
	setBorder(MyBorderFactory.createMyBorder(" s-Ebene "));
	this.setPreferredSize(new Dimension(400	,450));

	// Farben und Settings
	jChart.setBackgroundPaint(getBackground());
	XYPlot xyplot = jChart.getXYPlot();
	xyplot.setBackgroundPaint(getBackground());
	xyplot.setRangeGridlinePaint(Color.black);
	xyplot.setDomainGridlinePaint(Color.black);
	xyplot.getRenderer().setSeriesPaint(0, Color.black);


	ValueAxis axis = xyplot.getDomainAxis();
	axis.setRange(-2, 1);
	axis.setAutoRange(true);
	axis.setLabelPaint(Color.black);
	axis.setTickLabelPaint(Color.black);
	axis = xyplot.getRangeAxis();
	axis.setRange(-Math.PI, Math.PI);
	axis.setAutoRange(false);
	axis.setLabelPaint(Color.black);
	axis.setTickLabelPaint(Color.black);

	ChartPanel panel = new ChartPanel(jChart);
	add(panel);
	
}
}