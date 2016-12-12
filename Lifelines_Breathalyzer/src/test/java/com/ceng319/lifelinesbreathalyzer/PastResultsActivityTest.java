/*
    Designated Drivers
 */

package com.ceng319.lifelinesbreathalyzer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PastResultsActivityTest {
    private PastResultsActivity teststring;

    @Before
    public void setUp() throws Exception{
        teststring = new PastResultsActivity();
    }

    @Test
    public void convertArrayToString() throws Exception {
        String[] myStringArray = {"this","is","a","test"};
        String string="this\\r?\\nis\\r?\\na\\r?\\ntest";
        assertEquals(string, teststring.convertArrayToString(myStringArray));
    }

    @Test
    public void convertStringToArray() throws Exception {
        String string="this\nis\na\ntest";
        String[] stringArray = teststring.convertStringToArray(string);
        assertEquals("this", stringArray[0]);
    }

}