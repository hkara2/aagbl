package org.hkmi2.aagbl.tests;

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
 * Test app for more component weight calculation
 */
public class WeightTest
implements ActionListener
{
  AsciiArtGridBagLayout aagbl;
  //GridBagLayoutBuilder aagbl;
  JLabel cA = new JLabel("A main title");
  JLabel cB = new JLabel("B label");
  JTextArea cC = new JTextArea("C is a big text area component with lots of text");
  JButton cD = new JButton("D btn");
  
  JButton cE = new JButton("E btn");
  JTextField cF = new JTextField("F text field");
  
  JPanel mainPanel = new JPanel();
  
  /**
   * Constructor
   */
  public WeightTest() {
  }

  /**
   * Init the app
   * @throws LayoutParseException if parsing fails
   */
  public void init() throws LayoutParseException {
    String spec = 
        "+-----+-----------------+\n"+
        "|<         A           >|\n"+
        "+-----+---------+-------+\n"+
        "|B    | ^       |       |\n"+
        "|     |<   C   >|       |\n"+
        "|     |         |      D|\n"+
        "+-----+---------+-------+\n"+
        "|     |                 |\n"+
        "|     |                 |\n"+
        "|     |                 |\n"+
        "|< E >|                 |\n"+
        "|     |                 |\n"+
        "|     |                 |\n"+
        "|     |                F|\n"+
        "+-----+-----------------+\n"
    ;
    aagbl = new AsciiArtGridBagLayout(spec);
    
    LayoutParser parser = new LayoutParser();
    parser.parseSpec(parser.readSpec(new BufferedReader( new StringReader(spec) )));
    GridBagLayoutBuilder.debugBuilder = true;
    //aagbl = new GridBagLayoutBuilder(parser.getCRects());
    aagbl.setConstraints("A", cA);
    aagbl.setConstraints("B", cB);
    aagbl.setConstraints("C", cC);
    aagbl.setConstraints("D", cD);
    aagbl.setConstraints("E", cE);
    aagbl.setConstraints("F", cF);
    cE.addActionListener(this);
    //setting B and D weight to 0 so C can expand fully
    aagbl.setWeightx("B,D,E", 0);
  }
  
  /**
   * Run this app
   */
  public void run() {
    JFrame jfrm = new JFrame("testGridBag3()");
    jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainPanel.setLayout(aagbl);
    aagbl.addAllComponentsTo(mainPanel);
    jfrm.setContentPane(mainPanel);
    jfrm.pack();
    jfrm.setVisible(true);    
  }
  
  /**
   * Main entry point
   * @param args arguments (ignored)
   * @throws Exception if something goes wrong
   */
  public static void main(String[] args) throws Exception {
    WeightTest app = new WeightTest();
    app.init();
    app.run();
  }

  /**
   * Called when a GUI action is performed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == cE) {
      System.out.println("Button E pressed.");
    }
  }
  
}
