/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.airplane;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ruud
 */
public class AirplaneTest {
    
    public AirplaneTest() {
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

   @Test
    public void TestLift()
    {
        double pitch = -1;
        double speed = 0;
        for(int i = 0; i<100;i++)
        {
            speed = i;
            double cl = 2 * Math.PI * pitch;
            double lift = 0.5 * 0.002308 * Math.pow(speed, 2) * 100 * cl;
            System.out.println("TestLift, pitch:" + pitch + " speed:" + speed + " CL = " + cl + " lift = " + lift);
        }
 
    }
}
