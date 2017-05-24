package pro2e.teamX.userinterface;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.util.Observable;
import java.util.Observer;
import java.util.StringTokenizer;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import pro2e.teamX.MVCFramework;
import pro2e.teamX.model.Model;

public class PolListe extends JPanel {
     public JTextArea jtpols = new JTextArea(5, 20);
     private double[] xWert = new double[10];
     private double[] yWert = new double[10];

     public void main(double[] reBestOrd, double[] imBestOrd) {
           for (int i = 0; i < reBestOrd.length; i++) {
                reBestOrd[i] = Math.round(1000.0 * reBestOrd[i]) / 1000.0;
                xWert[i] = reBestOrd[i];
                System.out.println(" Real " + reBestOrd[i]);
           }
           for (int i = 0; i < imBestOrd.length; i++) {
                imBestOrd[i] = Math.round(1000.0 * imBestOrd[i]) / 1000.0;
                yWert[i] = imBestOrd[i];
                System.out.println(" Imag " + imBestOrd[i]);
           }
           StringBuilder sb = new StringBuilder();
        for(int i = 0; i < xWert.length; i++) {
            if (xWert[i]!=0 || yWert[i]!=0)
            {
                 
            if (yWert[i]<0)
              sb.append(" ").append(xWert[i]).append(" ").append(yWert[i]).append(" j\n");
            else
                 sb.append(" ").append(xWert[i]).append(" +").append(yWert[i]).append(" j\n");
        }
        }
           Test(sb.toString());
     }

     public PolListe() {
           this.setLayout(new GridLayout(1, 1));
           this.setPreferredSize(new Dimension(250, 300)); 
     }
     
     public void Test(String string) {
           jtpols.setBackground(getBackground());
           jtpols.setEditable(false);
           jtpols.setFont(getFont().deriveFont(14.0f));
           jtpols.setSize(400, 400);
           jtpols.setText(string);
           add(jtpols, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                     new Insets(10, 10, 10, 10), 0, 0));
     }

     public void update(Observable obs, Object obj) {
           Model model = (Model) obs;
           System.out.println("Test Pol Liste");
           //                              if(model.gerechnet==false)
           //                                            main(model.reBestOrd, model.imBestOrd);
           if (model.gerechnet == true) {
                main(model.reBestOrd, model.imBestOrd);
           for (int i = 0; i < model.reBestOrd.length; i++) {
                System.out.println(model.reBestOrd[i]);
           }}
     }
}

