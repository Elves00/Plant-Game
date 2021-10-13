/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author breco
 */
public class PlantFieldTest {

    public PlantFieldTest() {
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
     * Test of getWaterState method, of class PlantField.
     */
    @Test
    public void testGetWaterState() {
        System.out.println("getWaterState");
        PlantField instance = new PlantField();
        String[][] expResult = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                expResult[i][j] = "0/10";
            }
        }
        String[][] result = instance.getWaterState();
        assertArrayEquals(expResult, result);

        instance.water(2, 2);
        expResult[2][2] = "1/10";
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getValueState method, of class PlantField.
     */
    @Test
    public void testGetValueState() {
        System.out.println("getValueState");
        PlantField instance = new PlantField();
        String[][] expResult = null;
        String[][] result = instance.getValueState();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGrowthState method, of class PlantField.
     */
    @Test
    public void testGetGrowthState() {
        System.out.println("getGrowthState");
        PlantField instance = new PlantField();
        String[][] expResult = null;
        String[][] result = instance.getGrowthState();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDayState method, of class PlantField.
     */
    @Test
    public void testGetDayState() {
        System.out.println("getDayState");
        PlantField instance = new PlantField();
        String[][] expResult = null;
        String[][] result = instance.getDayState();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAllPlants method, of class PlantField.
     */
    @Test
    public void testSetAllPlants_ArrayList() {
        System.out.println("setAllPlants");
        ArrayList<Plant> plants = null;
        PlantField instance = new PlantField();
        instance.setAllPlants(plants);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAllPlants method, of class PlantField.
     */
    @Test
    public void testSetAllPlants_StringArrArr() {
        System.out.println("setAllPlants");
        String[][] plants = null;
        PlantField instance = new PlantField();
        instance.setAllPlants(plants);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAllPlantStatus method, of class PlantField.
     */
    @Test
    public void testSetAllPlantStatus_ArrayList() {
        System.out.println("setAllPlantStatus");
        ArrayList<String> details = null;
        PlantField instance = new PlantField();
        instance.setAllPlantStatus(details);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAllPlantStatus method, of class PlantField.
     */
    @Test
    public void testSetAllPlantStatus_StringArrArr() {
        System.out.println("setAllPlantStatus");
        String[][] details = null;
        PlantField instance = new PlantField();
        instance.setAllPlantStatus(details);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of newPlant method, of class PlantField.
     */
    @Test
    public void testNewPlant() {
        System.out.println("newPlant");
        Plant plant = null;
        int x = 0;
        int y = 0;
        PlantField instance = new PlantField();
        instance.newPlant(plant, x, y);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlant method, of class PlantField.
     */
    @Test
    public void testGetPlant() {
        System.out.println("getPlant");
        int x = 0;
        int y = 0;
        PlantField instance = new PlantField();
        Plant expResult = null;
        Plant result = instance.getPlant(x, y);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of nextDay method, of class PlantField.
     */
    @Test
    public void testNextDay() {
        System.out.println("nextDay");
        PlantField instance = new PlantField();
        instance.nextDay();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkPolination method, of class PlantField.
     */
    @Test
    public void testCheckPolination() {
        System.out.println("checkPolination");
        PlantField instance = new PlantField();
        instance.checkPolination();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of polinateNeighbours method, of class PlantField.
     */
    @Test
    public void testPolinateNeighbours() {
        System.out.println("polinateNeighbours");
        int[] neighbours = null;
        PlantField instance = new PlantField();
        instance.polinateNeighbours(neighbours);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNeighbours method, of class PlantField.
     */
    @Test
    public void testGetNeighbours() {
        System.out.println("getNeighbours");
        int x = 0;
        int y = 0;
        PlantField instance = new PlantField();
        int[] expResult = null;
        int[] result = instance.getNeighbours(x, y);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of water method, of class PlantField.
     */
    @Test
    public void testWater() {
        System.out.println("water");
        int x = 0;
        int y = 0;
        PlantField instance = new PlantField();
        instance.water(x, y);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of pickPlant method, of class PlantField.
     */
    @Test
    public void testPickPlant() {
        System.out.println("pickPlant");
        int x = 0;
        int y = 0;
        PlantField instance = new PlantField();
        int expResult = 0;
        int result = instance.pickPlant(x, y);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toFile method, of class PlantField.
     */
    @Test
    public void testToFile() {
        System.out.println("toFile");
        PlantField instance = new PlantField();
        String[] expResult = null;
        String[] result = instance.toFile();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlantArray method, of class PlantField.
     */
    @Test
    public void testGetPlantArray() {
        System.out.println("getPlantArray");
        PlantField instance = new PlantField();
        Plant[][] expResult = null;
        Plant[][] result = instance.getPlantArray();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPlantArray method, of class PlantField.
     */
    @Test
    public void testSetPlantArray() {
        System.out.println("setPlantArray");
        Plant[][] plantArray = null;
        PlantField instance = new PlantField();
        instance.setPlantArray(plantArray);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class PlantField.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        PlantField instance = new PlantField();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
