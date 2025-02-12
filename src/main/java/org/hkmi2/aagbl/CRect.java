package org.hkmi2.aagbl;

import java.awt.Insets;

/** component rectangle  */
public class CRect {
    /** The name of the rectangle */
  public String name;
    /** The x coordinate of the rectangle, it starts at 1 it's the column of the text */
  public int x; //coordinates x 
  /** The y coordinates of the rectangle, it starts at 1 because it's the line the text */
  public int y; //coordinates y 
  /** The width of the rectangle */  
  public int w;
  /** The height of the rectangle */  
  public int h;
  /** The flags for this rectangle */
  public int flags;
  /** The number of the line in which the name of the rectangle is */
  public int lineOfName;
  /** Horizontal alignment : 1 left 2 centered 3 right */
  public int halign;
  /** Vertical alignment : 1 top  2 centered 3 bottom */
  public int valign; 
  /** x-weight, defaults to -1 */
  public double weightx = -1; //if >= 0 will be used to force weightx
  /** y-weight defaults to -1 */
  public double weighty = -1; //if >= 0 will be used to force weighty
  /** x-ipad, defaults to 0 */
  public int ipadx = 0;
  /** y-ipad, defaults to 0 */
  public int ipady = 0;
  /** the insets, defaults to new Insets(0, 0, 0, 0) */
  public Insets insets = new Insets(0, 0, 0, 0);
  /** make a string with main infos : name,x,y,width,height */
  public String toString() { return "['"+name+"',"+x+","+y+","+w+","+h+"]"; }
}