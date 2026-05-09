/**
 * "Hello world" of AsciiArtGridBagLayout, in groovy script style 
 * Execution example (adapt to your env.) :
 * C:\hkgh\aagbl\src\test\groovy>set CLASSPATH=C:\hkgh\aagbl\build\libs\aagbl-1.1.jar
 * C:\hkgh\aagbl\src\test\groovy>c:\app\groovy\groovy-4.0.8\bin\groovy org/hkmi2/aagbl/tests/Hello.groovy
 * (here Groovy SDK version 4.0.8 was unzipped to c:\app\groovy )
 */
package org.hkmi2.aagbl.tests

import javax.swing.*
import java.awt.*
import org.hkmi2.aagbl.AsciiArtGridBagLayout
import groovy.swing.SwingBuilder

aa = '''
+------------------------------------------------+
|  ^                                             |
|<                       a                      >|
|                                                |
+-----------------+-----------+------------------+
|                 |           |                  |
|                 |     b     |                  |
|                 |           |                  |
+-----------------+-----------+------------------+
'''

gbl = new AsciiArtGridBagLayout(aa)
swb = new SwingBuilder()

frm = swb.frame(
            title: "Hello world", 
            defaultCloseOperation: JFrame.EXIT_ON_CLOSE
          ) 
{
	a = textField(
            text: 'Hello to all the layouters', 
            horizontalAlignment: JTextField.CENTER
        )
	b = button(text: "OK", actionPerformed: {e-> frm.setVisible(false); frm.dispose()} )
}

//Thanks to Groovy's binding, we can use all the variables programmatically
gbl.allCRectNames.each {
    if (binding.hasVariable(it)) gbl.setConstraints(it, binding.getVariable(it))
}

frm.contentPane.setLayout(gbl)
gbl.addAllComponentsTo(frm.contentPane)
frm.pack()
frm.visible = true

