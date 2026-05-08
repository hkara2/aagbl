package org.hkmi2.aagbl.tests;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.StringReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.hkmi2.aagbl.AsciiArtGridBagLayout;
import org.hkmi2.aagbl.GridBagLayoutBuilder;
import org.hkmi2.aagbl.LayoutParseException;
import org.hkmi2.aagbl.LayoutParser;

/**
 * Further manual tests of AsciiArtGridBagLayout.
 * Here we test how empty rects are tolerated
 */
public class GridBagTest3
implements ActionListener
{
  AsciiArtGridBagLayout aagbl;
  JLabel cA = new JLabel("A");
  JTextArea cB = new JTextArea("B is a big component with lots of text");
  JButton cC = new JButton("C");
  JTextField cD = new JTextField("D");
  JPanel mainPanel = new JPanel();
  
  /**
   * Constructor
   */
  public GridBagTest3() {
  }

  /**
   * Initialization
   * @throws LayoutParseException If layout spec is invalid
   */
  public void init() throws LayoutParseException {
    String spec = 
        "+-----+-----------------+\n"+
        "|A    | ^               |\n"+
        "|     |<        B      >|\n"+
        "|     |                 |\n"+
        "+-----+--------+--------+\n"+
        "|     |        |    ^   |\n"+
        "|     |        |        |\n"+
        "|     +--------+        |\n"+
        "|< C >|        |        |\n"+
        "|     |        |        |\n"+
        "|     |        +--------+\n"+
        "|     |        |       D|\n"+
        "+-----+--------+--------+\n"
    ;
    aagbl = new AsciiArtGridBagLayout(spec);
    Object[] allComponents = {"A", cA, "B", cB, "C", cC, "D", cD};
    aagbl.setConstraints(AsciiArtGridBagLayout.makeMap(allComponents));
    cC.addActionListener(this);
    mainPanel.setLayout(aagbl);
    aagbl.addAllComponentsTo(mainPanel);
  }
  
  /**
   * Run the test
   */
  public void run() {
    JFrame jfrm = new JFrame("testGridBag3()");
    jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jfrm.setContentPane(mainPanel);
    jfrm.pack();
    jfrm.setVisible(true);    
  }
  
  /**
   * Main method
   * @param args _
   * @throws Exception _
   */
  public static void main(String[] args) throws Exception {
    GridBagTest3 app = new GridBagTest3();
    app.init();
    app.run();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == cC) {
      System.out.println("Button C pressed.");
    }
  }
  
}
