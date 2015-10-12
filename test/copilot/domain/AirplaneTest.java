/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.domain;

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
        //CL = 2 * PI * angle of attack
        //L = 0.5 * p * v^2 * s * CL
        //Drag (force) = CD × 0.5* p * V^2 × S
        //cd of chesna = 0.027
        
        double pitch = -20;
        double speed = 20;
        for(int i = -20; i<50;i++)
        {
            pitch = i;
            double cl = 2 * Math.PI * (pitch / 100);
            double lift = 0.5 * 0.002308 * Math.pow(speed, 2) * 100 * cl;
            System.out.println("TestLift, pitch:" + pitch + " speed:" + speed + " CL = " + cl + " lift = " + lift);
        }
 
    }
}
