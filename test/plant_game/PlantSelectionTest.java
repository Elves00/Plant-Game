/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class PlantSelectionTest {

    public PlantSelectionTest() {
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
     * Test of unlock method, of class PlantSelection.
     */
    @Test
    public void testUnlock() {
        System.out.println("unlock");
        Plant toUnlock = new Saffron();
        PlantSelection instance = new PlantSelection();
        instance.unlock(toUnlock);

        try {
            Assert.assertEquals("Plant unlocked", instance.getPlant(3).toFile(), new Saffron().toFile());
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(PlantSelectionTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean reached = false;

        try {
            instance.unlock(toUnlock);
        } catch (IllegalArgumentException ie) {
            reached = true;
        }
        Assert.assertTrue("Test 1", reached);

        reached = false;

        try {
            instance.unlock(null);
        } catch (IllegalArgumentException ie) {
            reached = true;
        }
        Assert.assertFalse("Test 2", reached);

    }

    /**
     * Test of purchasePlant method, of class PlantSelection.
     */
    @Test
    public void testPurchasePlant() {
        System.out.println("purchasePlant");
        Player player = new Player("", 11, 100, 1, 0);
        int x = 1;
        PlantSelection instance = new PlantSelection();
        Boolean expResult = true;
        //correct input
        Boolean result = instance.purchasePlant(player, x);
        assertEquals("Test 1", expResult, result);

        //To little money
        expResult = false;
        result = instance.purchasePlant(player, x);
        assertEquals("Test 2", expResult, result);

        player.setMoney(200);

        //Index to large
        result = instance.purchasePlant(player, 4);
        assertEquals("Test 3", expResult, result);

        //Index to small
        result = instance.purchasePlant(player, -3);
        assertEquals("Test 4", expResult, result);

    }

    /**
     * Test of getPlant method, of class PlantSelection.
     */
    @Test
    public void testGetPlant() throws Exception {
        System.out.println("getPlant");
        int x = 0;
        PlantSelection instance = new PlantSelection();
        Plant expResult = new Broccoli();
        Plant result = instance.getPlant(x);
        assertEquals("Test 1",expResult.toFile(), result.toFile());
    
    }

    /**
     * Test of getPlantName method, of class PlantSelection.
     */
    @Test
    public void testGetPlantName() throws Exception {
        System.out.println("getPlantName");
        int x = 0;
        PlantSelection instance = new PlantSelection();
        String expResult = "";
        String result = instance.getPlantName(x);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of size method, of class PlantSelection.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        PlantSelection instance = new PlantSelection();
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toFile method, of class PlantSelection.
     */
    @Test
    public void testToFile() {
        System.out.println("toFile");
        PlantSelection instance = new PlantSelection();
        String expResult = "";
        String result = instance.toFile();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlants method, of class PlantSelection.
     */
    @Test
    public void testGetPlants() {
        System.out.println("getPlants");
        PlantSelection instance = new PlantSelection();
        HashMap<Integer, Plant> expResult = null;
        HashMap<Integer, Plant> result = instance.getPlants();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPlants method, of class PlantSelection.
     */
    @Test
    public void testSetPlants() {
        System.out.println("setPlants");
        HashMap<Integer, Plant> plants = null;
        PlantSelection instance = new PlantSelection();
        instance.setPlants(plants);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class PlantSelection.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        PlantSelection instance = new PlantSelection();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
