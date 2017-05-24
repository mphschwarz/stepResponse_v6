package pro2e.teamX.userinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

import pro2e.teamX.model.Model;

public class PNSEbene extends JPanel implements Observer { 

JFreeChart chart = ChartFactory.createXYLineChart(
        "", // chart title
        "Real-Anteil", // x axis label
        "", // y axis label
        null, // data
        PlotOrientation.VERTICAL,
        true, // include legend
        true, // tooltips
        false // urls
        );


public PNSEbene()
  {
                 super(new BorderLayout());
      setPreferredSize(new Dimension(420, 450));
      ChartPanel chartPanel = new ChartPanel(chart);
               chartPanel.setPreferredSize(new Dimension(200, 200));
               chart.setBackgroundPaint(getBackground());

               XYPlot plot =  chart.getXYPlot();
               plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
               plot.setBackgroundPaint(getBackground());
               plot.setRangeGridlinePaint(Color.black);
               plot.setDomainGridlinePaint(Color.black);

               XYLineAndShapeRenderer renderer= (XYLineAndShapeRenderer) plot.getRenderer();
               renderer.setShapesVisible(true);
               renderer.setShapesFilled(true);
               renderer.setLinesVisible(false);

               XYDotRenderer dot = new XYDotRenderer();
               dot.setDotHeight(5);
               dot.setDotWidth(5);

               renderer.setBaseSeriesVisibleInLegend(false);

               ValueAxis yAxis = plot.getRangeAxis(0);//x-Achse
               yAxis.setAutoRange(true);
               yAxis.setTickLabelsVisible(false);
               yAxis.setAxisLineVisible(false);
               yAxis.setTickLabelsVisible(false);
               yAxis.setTickMarksVisible(false);

                plot.setRenderer(1, renderer);

               NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
               rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

              // System.out.println("set Data");

               ChartPanel panel = new ChartPanel(chart);
               add(panel);
   }

     public void setData(double[] reBestOrd,double[] imBestOrd,double[] reButter,double[] imButter){
                
     XYSeries signal1 = new XYSeries("Pole");
     for(int i=0;i<reBestOrd.length;i++)
        signal1.add(reBestOrd[i], imBestOrd[i]);
        
     XYSeries signal2 = new XYSeries("Butterworth");
     for(int i=0;i<reButter.length;i++)
        signal2.add(reButter[i], imButter[i]);
     
     XYPlot xyplot = chart.getXYPlot();
     XYSeriesCollection dataset = new XYSeriesCollection();
     dataset.addSeries(signal1);
     dataset.addSeries(signal2);
     xyplot.setDataset(dataset);
     
     NumberAxis axis2 = new NumberAxis("Imaginär-Anteil");
               axis2.setAutoRangeIncludesZero(false);
               xyplot.mapDatasetToRangeAxis(1, 1);
               xyplot.setRangeAxis(1, axis2);
               xyplot.setRangeAxisLocation(1, AxisLocation.BOTTOM_OR_RIGHT);
               xyplot.setDataset(1, dataset);

     }


     @Override
	public void update(Observable obs, Object obj) {
     Model model = (Model) obs;
     System.out.println("Test PNSEBENE");
     model.setButterworth();

//                              if(model.gerechnet==false){
//                              setData(model.reBestOrd, model.imBestOrd,model.reButter,model.imButter);}
                              if(model.gerechnet==true){
                                            setData(model.reBestOrd, model.imBestOrd,model.reButter,model.imButter);
                              }
     repaint();
     }
}
