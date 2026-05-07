package org.hkmi2.aagbl;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JPanel;

/**
 * This object builds a series of GridBagLayouts from a list of rectangles (that were defined in
 * a Ascii art drawing, and parsed using a LayoutParser)
 * Utility class that handles a list of components and rectangles, and computes
 * the {@link GridBagConstraints} for each component.
 * This class is used by {@link AsciiArtGridBagLayout}, there are few reasons to use this class
 * directly.
 * @author hkaradimas
 *
 */
public class GridBagLayoutBuilder
{
  /** set to true to see some debugging messages on System.out . Default is false. */
  public static boolean debugBuilder = false;
  
  List<CRect> rects;
  GridBagLayout gbl = new GridBagLayout();
  JPanel pnl = new JPanel();
  ArrayList<Component> components = new ArrayList<Component>();
  int maxx;
  int maxy;
  
  /**
   * Default constructor
   * @param rects A list of CRect (The list of component rectangles)
   */
  public GridBagLayoutBuilder(List<CRect> rects) {
    this.rects = rects;
    computeMaxxy();
  }

  private void computeMaxxy() {
    for (CRect r : rects) {
      int mx = r.x+r.w-1;
      if (mx > maxx) maxx = mx;
      int my = r.y+r.h-1;
      if (my > maxy) maxy = my;
    }
    //if (maxx > 0) maxx--;
    //if (maxy > 0) maxy--;
  }
  
  /**
   * Get the GridBagLayout
   * @return The contained GridBagLayout
   */
  public GridBagLayout getGridBagLayout() { return gbl; }
  
  /**
   * get the JPanel
   * @return The contained JPanel
   */
  public JPanel getJPanel() { return pnl; }
    
  /**
   * Find a rectangle with the given name
   * @param name The name of the rectangle to look for
   * @return The rectangle or null if not found.
   */
  public CRect findCRect(String name) {
    for (CRect r : rects) {
      if (name.equals(r.name)) return r;
    }
    return null;
  }
  
  /**
   * Add the component with the given name, and no contraints
   * @param name The name of the component to add (use the name of the variable that contains this component if you don't want hard to find bugs)
   * @param comp The component to add
   */
  public void add(String name, Component comp) {
    add(name, comp, null);
  }
  
  /**
   * Add the component with the given name and the given constraints
   * @param name The name of the component to add (use the name of the variable that contains this component if you don't want hard to find bugs)
   * @param comp The component to add
   * @param extraConstraints The constraints to use
   */
  public void add(String name, Component comp, GridBagConstraints extraConstraints) {
    GridBagConstraints cons = makeGridBagConstraints(name);
    if (extraConstraints != null) {
      cons.anchor = extraConstraints.anchor;
      cons.fill = extraConstraints.fill;
      cons.insets = extraConstraints.insets;
      cons.ipadx = extraConstraints.ipadx;
      cons.ipady = extraConstraints.ipady;
    }
    //comp.setPreferredSize(new Dimension(0,0));
    pnl.add(comp, cons);
    components.add(comp);
  }
  
  /**
   * Calculate the GridBagConstraints for the CRect that has the given name
   * Create a {@link GridBagConstraints} object for the rectangle of the given name.
   * @param rectName The name of the CRect
   * @return the calculated GridBagConstraints
   */
  public GridBagConstraints makeGridBagConstraints(String rectName) {
    CRect r = findCRect(rectName);
    GridBagConstraints cons = new GridBagConstraints();
    if (r != null) {
      cons.gridx = r.x-1;
      cons.gridy = r.y-1;
      cons.gridwidth = r.w;
      cons.gridheight = r.h;
      cons.weightx = r.weightx >= 0 ? r.weightx : (double) r.w / maxx;
      cons.weighty = r.weighty >= 0 ? r.weighty : (double) r.h / maxy;
      if (debugBuilder) System.out.println("Name : '"+r.name+"', weightx : "+cons.weightx+", weighty : "+cons.weighty+", rect :"+r);
      cons.fill = GridBagConstraints.NONE;
      //cons.fill = GridBagConstraints.BOTH;
      if (debugBuilder) System.out.println("halign : "+r.halign+", valign : "+r.valign);
      switch (r.halign) {
      case 1:
        switch (r.valign) {
        case 1: cons.anchor = GridBagConstraints.NORTHWEST; break;
        case 2: cons.anchor = GridBagConstraints.WEST; break;
        case 3: cons.anchor = GridBagConstraints.SOUTHWEST; break;
        default:
        }
        break;
      case 2:
        switch (r.valign) {
        case 1: cons.anchor = GridBagConstraints.NORTH; break;
        case 2: cons.anchor = GridBagConstraints.CENTER; break;
        case 3: cons.anchor = GridBagConstraints.SOUTH; break;
        default:
        }
        break;
      case 3:
        switch (r.valign) {
        case 1: cons.anchor = GridBagConstraints.NORTHEAST; break;
        case 2: cons.anchor = GridBagConstraints.EAST; break;
        case 3: cons.anchor = GridBagConstraints.SOUTHEAST; break;
        default:
        }
        break;
      default:
        cons.anchor = GridBagConstraints.CENTER;
      }//switch
      if (debugBuilder) System.out.println("Flags : " + r.flags);
      switch (r.flags & (LayoutParser.FILL_HORIZ | LayoutParser.FILL_VERT)) {
      case LayoutParser.FILL_HORIZ: cons.fill = GridBagConstraints.HORIZONTAL; break;
      case LayoutParser.FILL_VERT: cons.fill = GridBagConstraints.VERTICAL; break;  
      case (LayoutParser.FILL_VERT | LayoutParser.FILL_HORIZ): 
        cons.fill = GridBagConstraints.BOTH; 
        if (debugBuilder) System.out.println("Setting fill to BOTH"); 
        break;  
      }//switch
      cons.ipadx = r.ipadx;
      cons.ipady = r.ipady;
      cons.insets = r.insets;
      if (debugBuilder) System.out.println("cons.fill="+cons.fill);
    }
    else {
      if (debugBuilder) System.out.println("Nothing found for name '"+rectName+"'");
    }
    return cons;
  }
  
  /**
   * Split the given comma-separated list into an array of Strings
   * @param str A string that is a comma-separated list of names, e.g. "a,b,x"
   * @return An arrays of String that corresponds to the list, e.g. {"a", "b", "x"}
   */
  public static String[] split(String str) {
    StringTokenizer stok = new StringTokenizer(str, ",");
    String[] r = new String[stok.countTokens()];
    int i = 0;
    while (stok.hasMoreTokens()) { r[i++] = stok.nextToken(); }
    return r;
  }
    
  /**
   * Sets the x weight for a list of rectangles (comma-separated list of names)
   * @param rectNameList A string that contains a comma-separated list of names
   * @param val The value for the x weight
   */
  public void setWeightx(String rectNameList, double val) {
    String[] names = split(rectNameList);
    for (String name : names) {
      CRect rect = findCRect(name);
      if (rect != null) {
        rect.weightx = val;
      }
    }
  }
  
  /**
   * Sets the y weight for a list of rectangles (comma-separated list of names)
   * @param rectNameList A string that contains a comma-separated list of names
   * @param val The value for the y weight
   */
  public void setWeighty(String rectNameList, double val) {
    String[] names = split(rectNameList);
    for (String name : names) {
      CRect rect = findCRect(name);
      if (rect != null) {
        rect.weighty = val;
      }
    }
  }
  
  /**
   * Sets the x ipadding for a list of rectangles (comma-separated list of names)
   * @param rectNameList A string that contains a comma-separated list of names
   * @param val The value for the x ipadding
   */
  public void setIpadx(String rectNameList, int val) {
    String[] names = split(rectNameList);
    for (String name : names) {
      CRect rect = findCRect(name);
      if (rect != null) rect.ipadx = val;
    }
  }
  
  /**
   * Sets the y ipadding for a list of rectangles (comma-separated list of names)
   * @param rectNameList A string that contains a comma-separated list of names
   * @param val The value for the y ipadding
   */
  public void setIpady(String rectNameList, int val) {
    String[] names = split(rectNameList);
    for (String name : names) {
      CRect rect = findCRect(name);
      if (rect != null) rect.ipady = val;
    }
  }
  
  /**
   * Sets the insets for a list of rectangles (comma-separated list of names)
   * @param rectNameList A string that contains a comma-separated list of names
   * @param insets The insets to set
   */
  public void setInsets(String rectNameList, Insets insets) {
    String[] names = split(rectNameList);
    for (String name : names) {
      CRect rect = findCRect(name);
      if (rect != null) rect.insets = insets;
    }
  }  
  
}
