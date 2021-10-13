/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

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
public class ScoreTest {

    public ScoreTest() {
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
     * Test of getName method, of class Score.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Score instance = new Score("a", 1);
        String expResult = "a";
        String result = instance.getName();
        assertEquals(expResult, result);

    }

    /**
     * Test of setName method, of class Score.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "gunther";
        Score instance = new Score("", 20);
        instance.setName(name);
        assertEquals(name, instance.getName());

    }

    /**
     * Test of getScore method, of class Score.
     */
    @Test
    public void testGetScore() {
        System.out.println("getScore");
        Score instance = new Score("a", 100);
        int expResult = 100;
        int result = instance.getScore();
        assertEquals(expResult, result);

    }

    /**
     * Test of setScore method, of class Score.
     */
    @Test
    public void testSetScore() {
        System.out.println("setScore");
        int score = 0;
        Score instance = new Score("a", 0);
        instance.setScore(score);

        assertEquals(score, instance.getScore());
        score = -400;
        instance = new Score("a", 0);
        instance.setScore(score);

        assertEquals(score, instance.getScore());
        score = 4000;
        instance = new Score("a", 0);
        instance.setScore(score);

        assertEquals(score, instance.getScore());

    }

    /**
     * Test of compareTo method, of class Score.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        Score o = new Score("Rose", 10);
        Score instance = new Score("Rose", 10);;
        int expResult = 0;
        int result = instance.compareTo(o);
        assertEquals(expResult, result);

        expResult = -1;
        instance = new Score("Rose", 20);
        result = instance.compareTo(o);
        assertEquals(expResult, result);

        expResult = 1;
        instance = new Score("Rose", 0);
        result = instance.compareTo(o);
        assertEquals(expResult, result);

    }

    /**
     * Test of toString method, of class Score.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Score instance = null;
        String expResult = "";
        boolean error = false;
        try {
            instance.toString();
        } catch (NullPointerException nu) {
            error = true;
        }
        assertTrue(error);

        instance = new Score("Rose", 20);
        expResult = "Rose 20";
        String result = instance.toString();
        assertEquals(expResult, result);

    }

}
