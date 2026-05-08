package org.hkmi2.aagbl;

import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 *  Component rectangle, that corresponds to a rectangle that was drawn on the supplied
 *   Ascii art drawing. Used internally by the {@link GridBagLayoutBuilder} and the
 *  {@link LayoutParser} when computing each {@link GridBagConstraints}.
 *  Some fine-tuning can be achieved by
 *  changing the rect before the constraints get generated.
 *  This is usually done by methods in the {@link AsciiArtGridBagLayout} class, not
 *  directly on the CRect class.
 */
public class CRect {
  /** Default constructor with no arguments */
  public CRect() {}
  /** name of CRect (can be null if no name given) */
  public String name;
  /** x and y coordinates */
  public int x, y; //coordinates x and y
  /** width ant height */
  public int w, h;
  /** flags */
  public int flags;
  /** line that contains the name */
  public int lineOfName;
  /** horizontal alignment : 1 left 2 centered 3 right */
  public int halign;
  /** vertical alignment : 1 top  2 centered 3 bottom*/
  public int valign;
  /** weight of x : if &gt;= 0 will be used to force weightx */
  public double weightx = -1;
  /** weight of y : if &gt;= 0 will be used to force weighty */
  public double weighty = -1; 
  /** ipad of x (0 by default) */
  public int ipadx = 0;
  /** ipad of y (0 by default) */
  public int ipady = 0;
  /** the insets, default : Insets(5, 5, 5, 5) */
  public Insets insets = new Insets(5, 5, 5, 5);
  /** convert this CRect to String */
  public String toString() { return "['"+name+"',"+x+","+y+","+w+","+h+"]"; }
}