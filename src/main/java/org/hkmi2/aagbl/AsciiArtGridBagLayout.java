package org.hkmi2.aagbl;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

/**
 * A variant of {@link GridBagLayout} that can be initialized using ascii art.
 * This is the main class to use. Under the hood, it uses a {@link GridBagLayoutBuilder}
 * object.
 * To use :
 * <ul>
 * <li> Make a String that represents the Ascii art of your layout (see the doc for this)
 * <li> Tweak some values (Weightx for example) for some rectangles if needed
 * <li> For each of you components, call {@link #setConstraints(String, Component)} with
 *   the name given in your Ascii art and your component
 * <li> Use this layout, for example in a {@link JPanel} (look in the tests dir for more examples)
 * </ul>
 * 
 * Example code (taken from the HelloWorld class (a subclass of JFrame) in the tests) :
 * <pre>
 *   //create our layout
 *   aagbl = new AsciiArtGridBagLayout(aa);
 *   //associate the constraint rectangles with our components
 *   aagbl.setConstraints("B", B);
 *   aagbl.setConstraints("L", L);
 *   aagbl.setConstraints("S1", S1);
 *   aagbl.setConstraints("S2", S2);
 *   //Alternative method to set constraints :
 *   //aagbl.setConstraints(AsciiArtGridBagLayout.makeMap(new Object[] {"B",B,"L",L,"S1",S1,"S2",S2}));
 *   //now set this as our layout
 *   setLayout(aagbl);
 *   //and add all the components
 *   aagbl.addAllComponentsTo(getContentPane());
 * </pre>
 * @author Harry
 *
 */
public class AsciiArtGridBagLayout
extends GridBagLayout
{
  /** The builder */
  GridBagLayoutBuilder gblb;
  /** All the components by name */
  HashMap<String, Component> componentsByName = new HashMap<>();
  /** All the constraints by name */
  HashMap<String, GridBagConstraints> constraintsByName = new HashMap<String, GridBagConstraints>();
  /** All the CRects */
  List<CRect> crects;
  
  /**
   * For serialization
   */
  private static final long serialVersionUID = 1L;
  
  /** The asciiArt drawing */
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
   * Set the constraints for all the components of the map.
   * You can set the constraints for all your components at once using this method.
   * @param cm The component by name map, each name must be the name used in the layout drawing.
   * @see #makeMap(Object[]) for an easy way to build a map
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
   * Get all the component names
   * @return A list of all the component names
   */
  public List<String> getAllComponentNames() { return new ArrayList<>(componentsByName.keySet()); }
  
  /**
   * Return a String that contains a comma-separated list of all component names
   * @return The list, e.g. "a,b,x"
   */
  public String getAllComponentsAsCsNameList() {
      StringBuilder sb = new StringBuilder();
      List<String> allNames = getAllComponentNames();
      for (String name:allNames) {
          if (sb.length() > 0) sb.append(',');
          sb.append(name);
      }
      return sb.toString();
  }
  
  /**
   * Get all the constraint names
   * @return A list of all the constraint names
   */
  public List<String> getAllConstraintNames() { return new ArrayList<>(constraintsByName.keySet()); }
  
  /**
   * Add all our components to the given {@link Container} object.
   * For each component, {@link Container#add(Component, Object)} is called, with the corresponding constraints object.
   * Adds all components the the container, from the list of components.
   * Useful if you already have a list of component names.
   * @param cr The container that will get all our objects
   */
  public void addAllComponentsTo(Container cr) {
    for (String k : componentsByName.keySet()) {
      cr.add(componentsByName.get(k), constraintsByName.get(k));
    }
  }
  
  /**
   * Set the x weight for all the components in the list (comma-separated list, no whitespace) 
   * Sets the x-weight for all the components in the list, and re-applies all the constraints for the
   * components in the list
   * @param rectNameList A comma-separated list of names, example "a,x,y"
   * @param val The new weight
   */
  public void setWeightx(String rectNameList, double val) {
    gblb.setWeightx(rectNameList, val); 
    resetConstraints(rectNameList);
  }
  
  /**
   * Sets the y-weight for all the components in the list (comma-separated list, no whitespace), 
   * and re-applies all the constraints for the
   * components in the list
   * @param rectNameList the list, example "a,x,y"
   * @param val the y weight to set to
   */
  public void setWeighty(String rectNameList, double val) {
    gblb.setWeighty(rectNameList, val); 
    resetConstraints(rectNameList);
  }
  
  /**
   * Reset the constraints to what we have in this layout, to all the elements that are in the list  (comma-separated list, no whitespace)
   * Internal method to reset the components to their new constraints
   * @param rectNameList the list, example "a,x,y"
   */
  private void resetConstraints(String rectNameList) {
    String[] names = GridBagLayoutBuilder.split(rectNameList);
    for (String name : names) {
      Component comp = getComponent(name);
      if (comp != null) setConstraints(name, comp);
    }
  }
  
  /**
   * Set the x ipadding for all the components in the list (comma-separated list, no whitespace)
   * and re-applies all the constraints for the
   * components in the list 
   * @param rectNameList the list, example "a,x,y"
   * @param val the x ipadding to set to
   */
  public void setIpadx(String rectNameList, int val) { 
    gblb.setIpadx(rectNameList, val);
    resetConstraints(rectNameList);
  }
  
  /**
   * Set the y ipadding for all the components in the list (comma-separated list, no whitespace)
   * and re-applies all the constraints for the
   * components in the list 
   * @param rectNameList the list, example "a,x,y"
   * @param val the y ipadding to set to
   */
  public void setIpady(String rectNameList, int val) { 
    gblb.setIpady(rectNameList, val);
    resetConstraints(rectNameList);
  }
  
  /**
   * Return a list of all the declared CRects
   * @return A list of all the CRects
   */
  public List<CRect> getCRects() { return crects; }
  
  /**
   * Return a list of all non-empty CRect names.
   * This is useful if you already have a map, that contains components that uses the same names as the CRects (but also
   * other names).
   * A typical usage is in Groovy, where undeclared variables go into the "binding" :
   * <pre>
   * gbl.allCRectNames.each { gbl.setConstraints(it, binding[it]) }
   * </pre>
   * @return A list of names (never null, but can be empty)
   */
  public List<String> getAllCRectNames() {    
    ArrayList<String> allNames = new ArrayList<>();
    if (crects == null) return allNames;
    for (CRect cr:crects) {
      if (cr.name != null && cr.name.length() > 0) allNames.add(cr.name);
    }
    return allNames;
  }

  /**
   * Get the builder that is used.
   * @return The builder instance
   */
  public GridBagLayoutBuilder getGridBagLayoutBuilder() { return gblb; }
  
  /**
   * Gets the Insets to use for future calls to {@link #setConstraints(String, Component)}. Calls getInsetsToUse() on the internal 
   * {@link GridBagLayoutBuilder}. If the {@link GridBagLayoutBuilder} is null, returns null,
   * else returns the current {@link Insets} to use. 
   * @return The Insets to use, if null default value in CRect(s) will be kept
   */
  public Insets getInsetsToUse() {
      if (gblb == null) return null;
      return gblb.getInsetsToUse();
  }
  
  public void setInsetsToUse(Insets newInsets) {
      if (gblb != null) gblb.setInsetsToUse(newInsets);
  }
}
