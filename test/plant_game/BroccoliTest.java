/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author breco
 */
public class BroccoliTest {

    public BroccoliTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of nextDay method, of class Broccoli.
     */
    @Test
    public void testNextDay() {
        System.out.println("nextDay");
        Broccoli instance = new Broccoli();

        instance.nextDay();

        System.out.println("Testing new broccoli going to next day");
        Assert.assertEquals("Time Planted", 1, instance.getTimePlanted());
        Assert.assertEquals("Value", 0, instance.getValue());
        Assert.assertFalse("pollinated", instance.isPollinated());

        instance.setGrowth(4);
        instance.setGrowCounter(4);
        instance.setPollinated(true);
        instance.nextDay();

        System.out.println("Testing grown broccoli going to next day");
        Assert.assertEquals("Time Planted", 2, instance.getTimePlanted());
        Assert.assertEquals("Value", 4, instance.getValue());
        Assert.assertFalse("pollinated", instance.isPollinated());
        Assert.assertEquals("Water count", 0, instance.getWaterCount());

        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of grow method, of class Broccoli.
     */
    @Test
    public void testGrow() {
        System.out.println("grow");
        Broccoli instance = new Broccoli();
        boolean expResult = true;
        boolean result = instance.grow();
        assertEquals(expResult, result);

        Assert.assertEquals("Water count", 0, instance.getWaterCount());
        Assert.assertEquals("Time planted", 0, instance.getTimePlanted());
        Assert.assertEquals("Growth", 1, instance.getGrowth());
    }

    /**
     * Test of pickOverride method, of class Broccoli.
     */
    @Test
    public void testPickOverride() {
        System.out.println("pickOverride");
        Broccoli instance = new Broccoli();

        try {
            Plant result = instance.pickOverride();
        } catch (UnsupportedOperationException e) {
            Assert.assertTrue(true);
        }

    }

    /**
     * Test of hasOverride method, of class Broccoli.
     */
    @Test
    public void testHasOverride() {
        System.out.println("hasOverride");
        Broccoli instance = new Broccoli();
        boolean expResult = false;
        boolean result = instance.hasOverride();
        assertEquals(expResult, result);

    }

    /**
     * Test of toString method, of class Broccoli.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Broccoli instance = new Broccoli();
        String expResult = "broccoli";
        String result = instance.toString();
        assertEquals(expResult, result);

    }

}
