package org.hkmi2.aagbl.tests;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.hkmi2.aagbl.AsciiArtGridBagLayout;
import org.hkmi2.aagbl.LayoutParseException;

/**
 * The "Hello world !" of Ascii art GridBagLayout
 * @author hkaradimas
 *
 */
@SuppressWarnings("serial")
public class HelloWorld 
  extends JFrame
{

  String aa =
      "+--------------------------------------------+\n"+
      "|                                            |\n"+
      "|                     L                      |\n"+
      "|                                            |\n"+
      "+--------------+--------------+--------------+\n"+
      "|              |              |              |\n"+
      "|              |<     B      >|              |\n"+
      "|              |              |              |\n"+
      "+--------------+--------------+--------------+\n";
  
  AsciiArtGridBagLayout aagbl;
  JLabel L = new JLabel("Hello, world !");
  JButton B = new JButton("OK");
  
  /**
   * Default constructor
   * @throws LayoutParseException If something goes wrong when parsing the Ascii art drawing
   */
  public HelloWorld() 
      throws LayoutParseException 
  {
    super("Hello, world !");
    //say we want to exit on close
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    //create our layout
    aagbl = new AsciiArtGridBagLayout(aa);
    //associate the constraint rectangles with our components
    aagbl.setConstraints("B", B);
    aagbl.setConstraints("L", L);
    //Alternative method to set constraints :
    //aagbl.setConstraints(AsciiArtGridBagLayout.makeMap(new Object[] {"B",B,"L",L}));
    //now set this as our layout
    setLayout(aagbl);
    //and add all the components
    aagbl.addAllComponentsTo(getContentPane());
    //add a handler that will close the frame when OK button is pressed
    B.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) { dispose(); }
    });
    //put a larger and bold font to see our message better
    Font labelFont = L.getFont();
    labelFont = labelFont.deriveFont(60f);
    labelFont = labelFont.deriveFont(Font.BOLD);
    L.setFont(labelFont);
  }
  
  /**
   * Main application entry point
   * @param args Ignored
   * @throws Exception If something goes wrong
   */
  public static void main(String[] args) 
      throws Exception 
  {
    HelloWorld frm = new HelloWorld();
    frm.setSize(600, 200);
    frm.setVisible(true);
  }

}
