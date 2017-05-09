import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Dataset {
	
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
	
}
