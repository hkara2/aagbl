package org.hkmi2.aagbl;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A variant of {@link GridBagLayout} that can be initialized using ascii art.
 * @author Harry
 *
 */
public class AsciiArtGridBagLayout
extends GridBagLayout
{
  GridBagLayoutBuilder gblb;
  HashMap<String, Component> componentsByName = new HashMap<>();
  HashMap<String, GridBagConstraints> constraintsByName = new HashMap<String, GridBagConstraints>();
  List<CRect> crects;
  
  /**
   * For serialization
   */
  private static final long serialVersionUID = 1L;
  
  String asciiArt;

  /**
   * Using an array of objects, make a HashMap.
   * This allows for a more compact initialization.
   * Ex :
   * <pre>
   * Object[] objs = ["B0", B0, "L1", L1];
   * HashMap&lt;String, Component&gt; hm = AsciiArtGridBagLayout.makeMap(objs);
   * </pre>
   * @param objs An array of String, Component, String, Component ...
   * @return The HashMap of Components by name
   */
  public static HashMap<String, Component> makeMap(Object[] objs) {
    HashMap<String, Component> hm = new HashMap<>();
    for (int i  = 0; i  < objs.length; i += 2) {
      hm.put((String)objs[i], (Component)objs[i+1]);
    }
    return hm;
  }
  
  /**
   * Main constructor, with the layout specification as an ASCII art drawing.
   * @param asciiArt The layout (see docs for the format)
   * @throws LayoutParseException If layout drawing is incorrect
   */
  public AsciiArtGridBagLayout(String asciiArt) 
  throws LayoutParseException
  {
    setAsciiArt(asciiArt);
  }

  /**
   * Simple no-args constructor
   */
  public AsciiArtGridBagLayout() {
  }
  
  /**
   * Return the current ASCII art drawing
   * @return the current ASCII art drawing
   */
  public String getAsciiArt() {
    return asciiArt;
  }

  /**
   * Set the ASCII art drawing, and compute the layout
   * @param asciiArt the ASCII art drawing
   * @throws LayoutParseException if layout drawing is incorrect
   */
  public void setAsciiArt(String asciiArt) 
  throws LayoutParseException
  {
    this.asciiArt = asciiArt;
    LayoutParser parser = new LayoutParser();
    List<char[]> lines = parser.readSpec(new BufferedReader(new StringReader(asciiArt)));
    parser.parseSpec(lines);
    gblb = new GridBagLayoutBuilder(parser.getCRects());
    crects = parser.getCRects();
    //S ystem.out.println("Rects :");
    for (@SuppressWarnings("unused") CRect r : crects) {
      //S ystem.out.println("Rect"+r);
    }
  }

  /**
   * Set the constraints for the given component. Used also to "declare" the components
   * that will be affected by the layout constraints.
   * @param componentName The name the component has in the layout
   * @param comp The actual component that will get drawn 
   */
  public void setConstraints(String componentName, Component comp) {
    GridBagConstraints cons = gblb.makeGridBagConstraints(componentName);
    super.setConstraints(comp, cons);
    //comp.setPreferredSize(new Dimension(0,0));
    constraintsByName.put(componentName, cons);
    componentsByName.put(componentName, comp);
  }
  
  /**
   * Set the constraints for all the components of the map
   * @param cm The component by name map, each name must be the name used in the layout drawing.
   */
  public void setConstraints(Map<String, Component> cm) {
    for (String name : cm.keySet()) {
      setConstraints(name, cm.get(name));
    }
  }
  
  /**
   * Get Max X which is the maximum char number used in the drawing, without the final column.
   * Ex :
   * <pre>
   * +-+-+
   * |A|B|
   * +-+-+
   * | Z |
   * +---+
   * </pre>
   * Here MaxX will be 4.
   * @return The maximum X
   */
  public int getMaxX() { return gblb.maxx; }
  
  /**
   * Get Max Y which is the maximum line number used in the drawing, without the final line.
   * @return The maximum Y
   */
  public int getMaxY() { return gblb.maxy; }
  
  /**
   * Get the component that has the given name
   * @param name The name that was used when {@link #setConstraints(String, Component)} was called.
   * @return The component or null if there is no component with that name
   */
  public Component getComponent(String name) { return componentsByName.get(name); }

  /**
   * Get the {@link GridBagConstraints} for the given name
   * @param name The name that was used when {@link #setConstraints(String, Component)} was called.
   * @return The {@link GridBagConstraints} object or null if there is no rectangle with that name
   */
  public GridBagConstraints getConstraints(String name) { return constraintsByName.get(name); }
  
  /**
   * Get all the components that were declared.
   * @return A list with all the components
   */
  public List<Component> getAllComponents() { return new ArrayList<>(componentsByName.values()); }
  
  /**
   * Add all our components to the given {@link Container} object.
   * For each component, {@link Container#add(Component, Object)} is called, with the corresponding constraints object.
   * @param cr The container that will get all our objects
   */
  public void addAllComponentsTo(Container cr) {
    for (String k : componentsByName.keySet()) {
      cr.add(componentsByName.get(k), constraintsByName.get(k));
    }
  }
  
  /**
   * Sets the x-weight for all the components in the list, and re-applies all the constraints for the
   * components in the list
   * @param rectNameList A comma-separated list of names
   * @param val The new weight
   */
  public void setWeightx(String rectNameList, double val) {
    gblb.setWeightx(rectNameList, val); 
    resetConstraints(rectNameList);
  }
  
  /**
   * Sets the y-weight for all the components in the list, and re-applies all the constraints for the
   * components in the list
   * @param rectNameList A comma-separated list of names
   * @param val The new weight
   */
  public void setWeighty(String rectNameList, double val) {
    gblb.setWeighty(rectNameList, val); 
    resetConstraints(rectNameList);
  }
  
  /**
   * Internal method to reset the components to their new constraints
   * @param rectNameList A comma-separated list of names
   */
  private void resetConstraints(String rectNameList) {
    String[] names = GridBagLayoutBuilder.split(rectNameList);
    for (String name : names) {
      Component comp = getComponent(name);
      if (comp != null) setConstraints(name, comp);
    }
  }
  
  
  /**
   * Sets the x-ipad for all the components in the list, and re-applies all the constraints for the
   * components in the list
   * @param rectNameList A comma-separated list of names
   * @param val The new ipad
   */
  public void setIpadx(String rectNameList, int val) { 
    gblb.setIpadx(rectNameList, val);
    resetConstraints(rectNameList);
  }
  
  /**
   * Sets the y-ipad for all the components in the list, and re-applies all the constraints for the
   * components in the list
   * @param rectNameList A comma-separated list of names
   * @param val The new ipad
   */
  public void setIpady(String rectNameList, int val) { 
    gblb.setIpady(rectNameList, val);
    resetConstraints(rectNameList);
  }
  
  /**
   * Get a list of all the declared rectangles
   * @return The list of the rectangles
   */
  public List<CRect> getCRects() { return crects; }
  
}
