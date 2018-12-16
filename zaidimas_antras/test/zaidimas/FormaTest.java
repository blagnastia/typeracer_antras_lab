/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zaidimas;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class FormaTest {
    
    public FormaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of setEntryStringLength method, of class Forma.
     */
    @Test
    public void testSetEntryStringLength() {
        System.out.println("setEntryStringLength");
        int entryStringLength = 0;
        Forma instance = new Forma();
        instance.setEntryStringLength(entryStringLength);
        assertEquals(instance.getEntryStringLength(), entryStringLength);
        entryStringLength = 10;
        instance.setEntryStringLength(entryStringLength);
        assertEquals(instance.getEntryStringLength(), entryStringLength);
        entryStringLength = -5;
        instance.setEntryStringLength(entryStringLength);
        assertEquals(instance.getEntryStringLength(), entryStringLength);
    }
    
    /**
     * Test of isFinnished method, of class Forma.
     */
    @Test
    public void testIsFinnished() {
        System.out.println("isFinnished");
        Forma instance = new Forma();
        instance.setEntryStringLength(10);
        instance.setTextLength(10);
        boolean expResult = true;
        boolean result = instance.isFinnished();
        assertEquals(expResult, result);
        
        instance.setEntryStringLength(100);
        instance.setTextLength(100);
        expResult = true;
        result = instance.isFinnished();
        assertEquals(expResult, result);
        
        instance.setEntryStringLength(10);
        instance.setTextLength(100);
        expResult = false;
        result = instance.isFinnished();
        assertEquals(expResult, result);
        
        instance.setEntryStringLength(100);
        instance.setTextLength(10);
        expResult = false;
        result = instance.isFinnished();
        assertEquals(expResult, result);
        
        instance.setEntryStringLength(-20);
        instance.setTextLength(0);
        expResult = false;
        result = instance.isFinnished();
        assertEquals(expResult, result);
        
        instance.setEntryStringLength(0);
        instance.setTextLength(0);
        expResult = true;
        result = instance.isFinnished();
        assertEquals(expResult, result);
        
        instance.setEntryStringLength(-20);
        instance.setTextLength(-19);
        expResult = false;
        result = instance.isFinnished();
        assertEquals(expResult, result);
    }
    
}
