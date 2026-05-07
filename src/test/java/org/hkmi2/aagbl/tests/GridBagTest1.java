package org.hkmi2.aagbl.tests;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.hkmi2.aagbl.AsciiArtGridBagLayout;
import org.hkmi2.aagbl.LayoutParseException;

/**
 * Test GridBag computation
 * Test of simple specs layout.
 * Components are anchored at various places.
 * @author hkaradimas
 */
public class GridBagTest1
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
  public GridBagTest1() {
  }

  /**
   * Init the class with the spec
   * @throws LayoutParseException If parsing failed
   */
  public void init() throws LayoutParseException {
    String spec = 
        "+-----+-----------------+\n"+
        "|A    |                B|\n"+
        "|     |                 |\n"+
        "+-----+-----------------+\n"+
        "|     |                 |\n"+
        "|     |                 |\n"+
        "|     |                 |\n"+
        "|  C  |                 |\n"+
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
    mainPanel.setLayout(aagbl);
    aagbl.addAllComponentsTo(mainPanel);
  }
  
  /**
   * Run the test
   */
  public void run() {
    JFrame jfrm = new JFrame("testGridBag1()");
    jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jfrm.setContentPane(mainPanel);
    jfrm.pack();
    jfrm.setVisible(true);    
  }
  
  /**
   * Main launch
   * @param args unused
   * @throws Exception If an error occurs
   */
  public static void main(String[] args) throws Exception {
    GridBagTest1 app = new GridBagTest1();
    app.init();
    app.run();
  }

  /**
   * The action implemented for action listener
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == cC) {
      System.out.println("Button C pressed.");
    }
  }
  
}
