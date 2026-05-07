package org.hkmi2.aagbl.tests;

import java.io.BufferedReader;
import java.io.StringReader;

import org.hkmi2.aagbl.CRect;
import org.hkmi2.aagbl.LayoutParseException;
import org.hkmi2.aagbl.LayoutParser;
import org.junit.jupiter.api.Test;

/**
 * More tests for the layouts
 * @author hkaradimas
 *
 */
public class LayoutTests
{

  /**
   * Test that names can be left aligned, right aligned, or centered.
   * @throws LayoutParseException _
   */
  @Test
  void testParseSpec1() throws LayoutParseException {

    String spec1 = 
        "+-----+-----+\n"+
        "|A    |    B|\n"+
        "|     |     |\n"+
        "+-----+-----+\n"+
        "|     |     |\n"+
        "|  C  |  D  |\n"+
        "|     |     |\n"+
        "+-----+-----+\n"
    ;
    LayoutParser parser = new LayoutParser();
    parser.parseSpec(parser.readSpec(new BufferedReader( new StringReader(spec1) )));
    for (CRect r : parser.getCRects()) {
      System.out.println(r);
    }
  }

  /**
   * Test that rectangles can be of different sizes
   * @throws LayoutParseException _
   */
  @Test
  void testParseSpec2() throws LayoutParseException {

    String spec = 
        "+-----+-----+\n"+
        "|A    |    B|\n"+
        "|     |     |\n"+
        "+-----+-----+\n"+
        "|     |     |\n"+
        "|     |  D  |\n"+
        "|  C  +-----+\n"+
        "|     |     |\n"+
        "|     |  E  |\n"+
        "+-----+-----+\n"
    ;
    LayoutParser parser = new LayoutParser();
    parser.parseSpec(parser.readSpec(new BufferedReader( new StringReader(spec) )));
    for (CRect r : parser.getCRects()) {
      System.out.println(r);
    }
  }

  /**
   * Test that a rectangle name can be on first line of rectangle
   * @throws LayoutParseException _
   */
  @Test
  void testParseSpec3() throws LayoutParseException {

    String spec = 
        "+-------------------+\n"+
        "|          n        |\n"+
        "|                   |\n"+
        "|                   |\n"+
        "+-------------------+\n"+
        "|          |        |\n"+
        "|     i2   |   i3   |\n"+
        "|          |        |\n"+
        "+-------------------+\n";
    ;
    LayoutParser parser = new LayoutParser();
    parser.parseSpec(parser.readSpec(new BufferedReader( new StringReader(spec) )));
    for (CRect r : parser.getCRects()) {
      System.out.println(r);
    }
  }

  /**
   * Test that flags work
   * @throws LayoutParseException _
   */
  @Test
  void testParseSpec4() throws LayoutParseException {

    String spec = 
        "+-------------------+\n"+
        "|          n        |\n"+
        "|                   |\n"+
        "|                   |\n"+
        "+-------------------+\n"+
        "|          | ^      |\n"+
        "|<    i2  >|<  i3  >|\n"+
        "|          |        |\n"+
        "+-------------------+\n";
    ;
    LayoutParser parser = new LayoutParser();
    parser.parseSpec(parser.readSpec(new BufferedReader( new StringReader(spec) )));
    for (CRect r : parser.getCRects()) {
      System.out.println(r);
    }
  }



}
