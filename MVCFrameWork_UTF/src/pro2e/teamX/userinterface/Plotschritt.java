package pro2e.teamX.userinterface;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.general.Dataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Plotschritt extends JPanel implements Observer{
	private JFreeChart chart;
	private Dataset dataset;
	public Plotschritt(){
		this.setLayout(new FlowLayout());
		this.setPreferredSize(new Dimension(800, 400));
		
		NumberAxis x = new NumberAxis("x");
		NumberAxis y = new NumberAxis("y");
		
	//	ApplicationFrame stepResponse = new ApplicationFrame("Step Response");
		XYSplineRenderer spline = new XYSplineRenderer();
        spline.setPrecision(1);
		
		XYPlot xyplot = new XYPlot();
		chart=new JFreeChart(xyplot);
		
		
		ChartPanel chartPanel = new ChartPanel(chart);
		
//        stepResponse.setContentPane(chartPanel);
//        stepResponse.pack();
//        stepResponse.setVisible(true);
	}
	public static XYSeriesCollection dataset(double[] x,double[] y, double[] t){
        // Bezeichung Punkte1 erscheint in der Legende
        XYSeries signal1 = new XYSeries("Signal1");
        for(int i=0;i<x.length;i++)
        signal1.add(t[i], x[i]);
        
        // Bezeichung Punkte2 erscheint in der Legende
        XYSeries signal2 = new XYSeries("Signal2");
        for(int i=0;i<y.length;i++)
        signal2.add(t[i], y[i]);

        // Zusammenfassen der beiden Serien
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(signal1);
        dataset.addSeries(signal2);
        return dataset;
    }
	@Override
	public void update(Observable obs, Object obj) {
		// TODO Auto-generated method stub
		
	}
}
