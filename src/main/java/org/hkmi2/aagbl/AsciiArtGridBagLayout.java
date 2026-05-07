package org.hkmi2.aagbl;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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
   * Constructor with a drawing
   * @param asciiArt The Ascii art drawing to use
   * @throws LayoutParseException If something goes wrong during layout
   */
  public AsciiArtGridBagLayout(String asciiArt) 
  throws LayoutParseException
  {
    setAsciiArt(asciiArt);
  }

  /**
   * Default no argument constructor
   */
  public AsciiArtGridBagLayout() {
  }
  
  /**
   * Get the drawing
   * @return The string that contains the Ascii art
   */
  public String getAsciiArt() {
    return asciiArt;
  }

  /**
   * Set the drawing
   * @param asciiArt The Ascii art drawing to use
   * @throws LayoutParseException If something goes wrong while parsing asciiArt
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
    for (CRect r : crects) {
      //S ystem.out.println("Rect"+r);
    }
  }

  /**
   * Establish the GridBagConstraints for the given named component and set them for the component
   * @param componentName The name that is used in the layout to adress the component
   * @param comp The component that will get its constraints applied to
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
   * @param cm The component by name map
   */
  public void setConstraints(Map<String, Component> cm) {
    for (String name : cm.keySet()) {
      setConstraints(name, cm.get(name));
    }
  }
  
  /**
   * Get the MaxX
   * @return the MaxX
   */
  public int getMaxX() { return gblb.maxx; }
  
  /**
   * Get the MaxY
   * @return the MaxY
   */
  public int getMaxY() { return gblb.maxy; }
  
  /**
   * Get the component for the name
   * @param name The name
   * @return the component for the name
   */
  public Component getComponent(String name) { return componentsByName.get(name); }

  /**
   * get the constraints for the name
   * @param name The name
   * @return The constraints
   */
  public GridBagConstraints getConstraints(String name) { return constraintsByName.get(name); }
  
  /**
   * Get all the components
   * @return A list of all the components
   */
  public List<Component> getAllComponents() { return new ArrayList<>(componentsByName.values()); }
  
  /**
   * Get all the component names
   * @return A list of all the component names
   */
  public List<String> getAllComponentNames() { return new ArrayList<>(componentsByName.keySet()); }
  
  /**
   * Get all the constraint names
   * @return A list of all the constraint names
   */
  public List<String> getAllConstraintNames() { return new ArrayList<>(constraintsByName.keySet()); }
  
  /**
   * Add all components the the container, from the list of components.
   * Useful if you already have a list of component names.
   * @param cr the container to add to
   */
  public void addAllComponentsTo(Container cr) {
    for (String k : componentsByName.keySet()) {
      cr.add(componentsByName.get(k), constraintsByName.get(k));
    }
  }
  
  /**
   * Set the x weight for all the components in the list (comma-separated list, no whitespace) 
   * @param rectNameList the list, example "a,x,y"
   * @param val the x weight to set to
   */
  public void setWeightx(String rectNameList, double val) {
    gblb.setWeightx(rectNameList, val); 
    resetConstraints(rectNameList);
  }
  
  /**
   * Set the y weight for all the components in the list (comma-separated list, no whitespace) 
   * @param rectNameList the list, example "a,x,y"
   * @param val the y weight to set to
   */
  public void setWeighty(String rectNameList, double val) {
    gblb.setWeighty(rectNameList, val); 
    resetConstraints(rectNameList);
  }
  
  /**
   * Reset the constraints to what we have in this layout, to all the elements that are in the list  (comma-separated list, no whitespace)
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
   * @param rectNameList the list, example "a,x,y"
   * @param val the x ipadding to set to
   */
  public void setIpadx(String rectNameList, int val) { 
    gblb.setIpadx(rectNameList, val);
    resetConstraints(rectNameList);
  }
  
  /**
   * Set the y ipadding for all the components in the list (comma-separated list, no whitespace) 
   * @param rectNameList the list, example "a,x,y"
   * @param val the y ipadding to set to
   */
  public void setIpady(String rectNameList, int val) { 
    gblb.setIpady(rectNameList, val);
    resetConstraints(rectNameList);
  }
  
  /**
   * Return a list of all the CRects
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
  
}
