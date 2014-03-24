package jsf.controllers;

import jsf.util.EuroCurrencyConverter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class EuroCurrencyConverterTest {

    public EuroCurrencyConverterTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetAsString() {
        EuroCurrencyConverter ecc =new EuroCurrencyConverter();
        Long numTest=null;
        assertEquals(ecc.getAsString(null, null, numTest), "0,00 €");

        numTest=new Long(1);
        assertEquals(ecc.getAsString(null, null, numTest),"0,001 €");

        numTest=new Long(10);
        assertEquals(ecc.getAsString(null, null, numTest),"0,01 €");

        numTest=new Long(12);
        assertEquals(ecc.getAsString(null, null, numTest),"0,012 €");

        numTest=new Long(120);
        assertEquals(ecc.getAsString(null, null, numTest),"0,12 €");

        numTest=new Long(123);
        assertEquals(ecc.getAsString(null, null, numTest),"0,123 €");

        numTest=new Long(1230);
        assertEquals(ecc.getAsString(null, null, numTest),"1,23 €");

        numTest=new Long(1234);
        assertEquals(ecc.getAsString(null, null, numTest),"1,234 €");

        numTest=new Long(12340);
        assertEquals(ecc.getAsString(null, null, numTest),"12,34 €");

        numTest=new Long(Long.MAX_VALUE-1);
        assertEquals(ecc.getAsString(null, null, numTest),"9.223.372.036.854.775,806 €");

        numTest=new Long(Long.MAX_VALUE);
        assertEquals(ecc.getAsString(null, null, numTest),"9.223.372.036.854.775,807 €");

        numTest=new Long(-1);
        assertEquals(ecc.getAsString(null, null, numTest),"-0,001 €");

        numTest=new Long(-12);
        assertEquals(ecc.getAsString(null, null, numTest),"-0,012 €");

        numTest=new Long(-123);
        assertEquals(ecc.getAsString(null, null, numTest),"-0,123 €");

        numTest=new Long(-1234);
        assertEquals(ecc.getAsString(null, null, numTest),"-1,234 €");

        numTest=new Long(-10);
        assertEquals(ecc.getAsString(null, null, numTest),"-0,01 €");

        numTest=new Long(-120);
        assertEquals(ecc.getAsString(null, null, numTest),"-0,12 €");

        numTest=new Long(-1230);
        assertEquals(ecc.getAsString(null, null, numTest),"-1,23 €");

        numTest=new Long(-12340);
        assertEquals(ecc.getAsString(null, null, numTest),"-12,34 €");

        numTest=new Long(Long.MIN_VALUE+1);
        assertEquals(ecc.getAsString(null, null, numTest),"-9.223.372.036.854.775,807 €");

        numTest=new Long(Long.MIN_VALUE);
        assertEquals(ecc.getAsString(null, null, numTest),"-9.223.372.036.854.775,808 €");
    }


    @Test
    public void testGetAsObject() {
        EuroCurrencyConverter ecc =new EuroCurrencyConverter();

        String numTest=null;
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(0));

        numTest="0";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(0));

        numTest="1";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(1000));

        numTest=".5";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(500));

        numTest=".05";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(50));

        numTest=".50";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(500));

        numTest="0.50";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(500));

        numTest="1.50";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(1500));

        numTest="1234.50";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(1234500));

        numTest=",5 €";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(500));

        numTest=",05 €";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(50));

        numTest=",50 €";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(500));

        numTest="0,50 €";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(500));

        numTest="1,50 €";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(1500));

        numTest="1234,50 €";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(1234500));

        numTest="-0";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(0));

        numTest="-1";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(-1000));

        numTest="-.5";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(-500));

        numTest="-.05";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(-50));

        numTest="-.50";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(-500));

        numTest="-0.50";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(-500));

        numTest="-1.50";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(-1500));

        numTest="-1234.50";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(-1234500));

        numTest="-,5 €";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(-500));

        numTest="-,05 €";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(-50));

        numTest="-,50 €";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(-500));

        numTest="-0,50 €";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(-500));

        numTest="-1,50 €";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(-1500));

        numTest="-1234,50 €";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(-1234500));


        numTest=".511";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(511));

        numTest=".051";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(51));

        numTest=".501";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(501));

        numTest="0.501";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(501));

        numTest=",511";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(511));

        numTest=",051";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(51));

        numTest=",501";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(501));

        numTest="0,501";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(501));


        numTest="-.511";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(-511));

        numTest="-.051";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(-51));

        numTest="-.501";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(-501));

        numTest="-0.501";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(-501));

        numTest="-,511";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(-511));

        numTest="-,051";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(-51));

        numTest="-,501";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(-501));

        numTest="-0,501";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(-501));


        numTest="1.1234";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(1123));

        numTest="1.1235";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(1124));

        numTest="1.1236";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(1124));


        numTest="123.456.789,20 €";
        assertEquals(ecc.getAsObject(null, null, numTest), new Long(123456789200l));
        
    }



}