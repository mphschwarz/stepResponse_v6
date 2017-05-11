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
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.DomainOrder;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

import pro2e.teamX.matlabfunctions.Matlab;
//import pro2e.teamX.matlabfunctions.plotpol;
import pro2e.teamX.matlabfunctions.MatlabFunktionen;
import pro2e.teamX.model.Model;

import org.jfree.data.statistics.*;

public class PNSEbene extends JPanel {
               
               int pmin;
               int pmax;
               boolean b;
               double[] c;
               
               public PNSEbene()
               {
                              super(new BorderLayout());
                              setPreferredSize(new Dimension(420, 450));
                              main(pmin, pmax, b, c);
      //                      setData(beispiel());
               }
               
               
               public void main(int pmin, int pmax, boolean b, double [] c){
                              this.pmin=pmin;
                              this.pmax=pmax;
                              this.b=b;
                              this.c=c;
                              XYSeriesCollection dataset = berechne(pmin,pmax,b,c);
                              setData(dataset);             
               }
               
//               public XYSeriesCollection beispiel()
//               {
//                              double[] a = {2 ,4 ,6 ,8 ,10 ,6 ,7};
//        double[] b = {-1,2,-3,4,-5,6,-7};
//        double[] p = new double[7];
//        for( int i=0; i<p.length;i++)
//               p[i]=0;
//        XYSeriesCollection dataset = dataset(a, b, p, p);
//        return dataset;
//               }
               
               public XYSeriesCollection berechne(int pmin, int pmax, boolean b, double [] c) 
               {
                              double [] xwerte = new double [200];
                              double [] ywerte = new double [200];
                              double [] werte = new double [21];
                              for (int n = pmin; n < pmax; n++) 
                              {
                                            
                                            werte=MatlabFunktionen.wqtoReIm(c,n);
                                            for (int i = 0; i < (werte.length-1)/2; i++) {
                                                           xwerte[i]=werte[i];
                                            }
                                            int j=0;
                                            for (int i = (werte.length-1)/2; i < werte.length-1; i++) {
                                                           ywerte[j]=werte[i];
                                                           j++;
                                            }                     
                              }
                                                           
                              if(b==true)
                              {
                                            double [] x= new double [200];
                                            double [] y= new double [200];
                                            double [] linspace = Matlab.linspace(Math.PI/2, -Math.PI/2, 200);

                                            for (int i = 0; i < linspace.length; i++) {
                                                           x[i]=-Math.cos(linspace[i]);
                                                           y[i] =Math.sin(linspace[i]);
                                            }
                                            XYSeriesCollection dataset = dataset(xwerte, ywerte, x, y);
                                            return dataset;
                              }
                              else{
                                            double [] p= new double [200];
                                            for(int i=0;i<p.length;i++)
                                                           p[i]=0;
                                            XYSeriesCollection dataset = dataset( xwerte,  ywerte,p,p);
                                            return dataset;
                              }
               }
               
               
               public void setData(XYSeriesCollection dataset ) {
               JFreeChart chart = ChartFactory.createXYLineChart(
                                            "", // chart title
                                            "Real-Anteil", // x axis label
                                            "", // y axis label
                                            dataset, // data
                                            PlotOrientation.VERTICAL,
                                            true, // include legend
                                            true, // tooltips
                                            false // urls
                                            );
               final ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new Dimension(200, 200));
                              chart.setBackgroundPaint(getBackground());

                              XYPlot plot = (XYPlot) chart.getPlot();
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
                              
        NumberAxis axis2 = new NumberAxis("Imaginär-Anteil");
        axis2.setAutoRangeIncludesZero(false);
        plot.mapDatasetToRangeAxis(1, 1);
        plot.setRangeAxis(1, axis2);
        plot.setRangeAxisLocation(1, AxisLocation.BOTTOM_OR_RIGHT);
        plot.setDataset(1, dataset);
        plot.setRenderer(1, renderer);
        
                              NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
                              rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        ChartPanel panel = new ChartPanel(chart);
                              add(panel);
               
               }


               public static XYSeriesCollection dataset(double[] x,double[] y,double[] xx,double[] yy){
        XYSeries signal1 = new XYSeries("Pole");
        for(int i=0;i<x.length;i++)
        signal1.add(x[i], y[i]);
        
        XYSeries signal2 = new XYSeries("Butterworth");
        for(int i=0;i<xx.length;i++)
        signal2.add(xx[i], yy[i]);

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(signal1);
        dataset.addSeries(signal2);
        return dataset;
    }


               public void update(Observable obs, Object obj) {
                                         Model model = (Model) obs;
                                         System.out.println("Test PNSEBENE");
                                       double[] a = {2 ,4 ,6 ,8 ,10 ,6 ,7};
                                       double[] b = {-1,2,-3,4,-5,6,-7};
                                       double[] p = new double[7];
                                       for( int i=0; i<p.length;i++){
                                              p[i]=0;}
 //                                      main(a, b, false, p);
                              repaint();
               }
}

