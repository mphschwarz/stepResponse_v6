import java.awt.Color;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class Main {
	
	public static void main(String[] args) {
		
		// Datensatz erzeugen
		double[] a = {2 ,4 ,6 ,8 ,10 ,6 ,7};
        double[] b = {-1,2,-3,4,-5,6,-7};
        double[] t = {1, 2, 3, 4, 5, 6, 7, };
        XYSeriesCollection dataset = Dataset.dataset(a, b, t);
		
        
		//Achsenbezeichnung
		NumberAxis x = new NumberAxis("x");
		NumberAxis y = new NumberAxis("y");
		
		
		ApplicationFrame stepResponse = new ApplicationFrame("Step Response");
        XYSplineRenderer spline = new XYSplineRenderer();
        spline.setPrecision(1);
        spline.getBaseLinesVisible();
   
        
        
        XYPlot plot = new XYPlot(dataset, x, y, spline);
		
        JFreeChart chart = new JFreeChart(plot);

        ChartPanel chartPanel = new ChartPanel(chart);
        stepResponse.setContentPane(chartPanel);
        stepResponse.pack();
        stepResponse.setVisible(true);
	}

}