package org.hkmi2.aagbl.tests;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.hkmi2.aagbl.AsciiArtGridBagLayout;
import org.hkmi2.aagbl.LayoutParseException;

/**
 * Test app for component weight calculation.
 * TextArea B and button C follow the component's resizing.
 */
public class GridBagTest2
implements ActionListener
{
  AsciiArtGridBagLayout aagbl;
  JLabel cA = new JLabel("A");
  JTextArea cB = new JTextArea("B is a big component with lots of text");
  JButton cC = new JButton("C");
  JTextField cD = new JTextField("D");
  
  /**
   * Constructor
   */
  public GridBagTest2() {
  }

  /**
   * Init the app
   * @throws LayoutParseException if parsing fails
   */
  public void init() throws LayoutParseException {
    String spec = 
        "+-----+-----------------+\n"+
        "|A    | ^               |\n"+
        "|     |<        B      >|\n"+
        "|     |                 |\n"+
        "+-----+-----------------+\n"+
        "|     |                 |\n"+
        "|     |                 |\n"+
        "|     |                 |\n"+
        "|< C >|                 |\n"+
        "|     |                 |\n"+
        "|     |                 |\n"+
        "|     |                D|\n"+
        "+-----+-----------------+\n"
    ;
    aagbl = new AsciiArtGridBagLayout(spec);
    aagbl.setConstraints("A", cA);
    aagbl.setConstraints("B", cB);
    aagbl.setConstraints("C", cC);
    aagbl.setConstraints("D", cD);
    cC.addActionListener(this);
  }
  
  /**
   * Run this app
   */
  public void run() {
    JFrame jfrm = new JFrame("testGridBag2()");
    jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jfrm.getContentPane().setLayout(aagbl);
    aagbl.addAllComponentsTo(jfrm.getContentPane());
    jfrm.pack();
    jfrm.setVisible(true);    
  }
  
  /**
   * Main entry point
   * @param args arguments (ignored)
   * @throws Exception if something goes wrong
   */
  public static void main(String[] args) throws Exception {
    GridBagTest2 app = new GridBagTest2();
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
