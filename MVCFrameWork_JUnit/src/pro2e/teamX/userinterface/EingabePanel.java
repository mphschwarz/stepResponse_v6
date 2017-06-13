package pro2e.teamX.userinterface;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EingabePanel extends JPanel implements ActionListener  {
      public JTextField tfOrdnung = new JTextField(30);
      public JTextField tfOrdnungbis = new JTextField(30);
      public JTextField tfStartwerte = new JTextField(30);
      public JTextField tfZeitNormierung = new JTextField(30);

      public Controller controller;
      public int ns=0;
      public int ne=9;

      public JButton btberechen = new JButton("Berechnen");
      public JButton btAbbruch = new JButton("Abrechen");

      public JComboBox jcordnungVon;
      public JComboBox jcordnungBis;
      public String[] jcordString = {"1. Ordnung", "2. Ordnung", "3. Ordnung", "4. Ordnung", "5. Ordnung", "6. Ordnung", "7. Ordnung", "8. Ordnung", "9. Ordnung", "10. Ordnung"};
/**
 * EingabePanel
 * 
 *  Das Panel wird gebaut und die einzelnen Komponenten darauf platziert.
 * Alle Komponenten werden mit den ActionListener verbunden.
 * @param controller
 */
      public EingabePanel(Controller controller) {
    	 this.controller=controller;
            this.setLayout(new GridBagLayout());
            this.setBorder(MyBorderFactory.createMyBorder(" Eingabe "));

            // JComboBox
            jcordnungVon = new JComboBox(jcordString);
            jcordnungVon.setSelectedIndex(0);
            jcordnungVon.setActionCommand("ordListVon");
           jcordnungVon.addActionListener(this);
//
            jcordnungBis = new JComboBox(jcordString);
            jcordnungBis.setSelectedIndex(9);
      //      jcordnungBis.setActionCommand("ordListBis");
            jcordnungBis.addActionListener(this);
            
            btAbbruch.addActionListener(this);

              // Adding stuff to display
            add(btberechen, new GridBagConstraints(0, 4, 1, 1, 1.0, 1.0, GridBagConstraints.LAST_LINE_END, GridBagConstraints.BOTH,
                        new Insets(10, 10, 10, 10), 0, 0));
            add(btAbbruch, new GridBagConstraints(1, 4, 1, 1, 1.0, 1.0, GridBagConstraints.LAST_LINE_END, GridBagConstraints.BOTH,
                        new Insets(10, 10, 10, 10), 0, 0));
            
            add(new JLabel("Ordnung von: "), new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
                            GridBagConstraints.ABOVE_BASELINE, new Insets(10, 10, 10, 10), 0, 0));
            add(jcordnungVon, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
                            GridBagConstraints.ABOVE_BASELINE, new Insets(10, 10, 10, 10), 0, 0));
            
            add(new JLabel("Ordnung bis: "), new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
                            GridBagConstraints.ABOVE_BASELINE, new Insets(10, 10, 10, 10), 0, 0));
            add(jcordnungBis, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
                            GridBagConstraints.ABOVE_BASELINE, new Insets(10, 10, 10, 10), 0, 0));
            
            add(Box.createVerticalGlue(), new GridBagConstraints(0, 4, 2, 2, 1.0, 1.0, GridBagConstraints.CENTER,
                        GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));

      }

  /**
   * actionPerformed wird aufgerufen sobald eine änderung an den CheckBoxes erfolgt. Ruft setOrdnungVon()
   * resp. setOrbnungBis() des Controllers mit den Übergabewerten auf.
   */
              public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("ordListVon")) {
                             this.ns = jcordnungVon.getSelectedIndex();
                             controller.setOrdnungVon(ns);    
                }
                
                if (e.getSource()==jcordnungBis) {
                             this.ne = jcordnungBis.getSelectedIndex();
                           controller.setOrdnungBis(ne);
                       //    System.out.println(""+ne);
                }
                
                

              }

}
