/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.domain.Test;

import copilot.domain.Airplane;
import copilot.domain.AirplanePart;
import copilot.domain.Elevator;
import copilot.domain.Fuel;
import copilot.domain.Gun;
import copilot.domain.Propellor;
import java.util.ArrayList;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author IndyGames
 */
public class AirplaneTest {
    private static final double DELTA = 1e-15;
    Airplane airplane;
    Elevator elevator;
    Propellor propellor;
    Gun gun;
    Fuel fuel;
    Image image;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        try {
            this.image = ImageIO.read(this.getClass().getResource("Plane.png"));
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
        
        airplane = new Airplane(image);
        elevator = new Elevator(image, airplane, null);
        propellor = new Propellor(image, airplane, null);
        gun = new Gun(image, airplane, null, 0, 0, 0);
        fuel = new Fuel(image, airplane, null);

        ArrayList<AirplanePart> parts = new ArrayList<>();
        parts.add(elevator);
        parts.add(propellor);
        parts.add(gun);
        parts.add(fuel);

        airplane.setAirplaneParts(parts);
    }

    @After
    public void tearDown() {
        
    }

    /**
     * Test the pitch from the Airplane class.
     */
    @Test
    public void testPitch() {
        //CL = 2 * PI * angle of attack
        //L = 0.5 * p * v^2 * s * CL
        //Drag (force) = CD × 0.5* p * V^2 × S
        //cd of chesna = 0.027
        double pitch = -20;
        double speed = 20;
        // Test pitch adjustments
        for (int i = -50; i < 50; i++) {
            pitch = i;
            double currentPitch = airplane.getPitch();
            elevator.setElevatorPitch(pitch);
            double cl = 2 * Math.PI * (pitch / 100);
            double lift = 0.5 * 0.002308 * Math.pow(speed, 2) * 100 * cl;
            System.out.println("TestLift, pitch:" + pitch + " speed:" + speed + " CL = " + cl + " lift = " + lift);

            double expectedAirplanePitch = currentPitch + (Elevator.PITCH_INCREASMENT * pitch);
            if (expectedAirplanePitch > 90) {
                expectedAirplanePitch = 90;
            } else if (expectedAirplanePitch < -90) {
                expectedAirplanePitch = -90;
            }

            System.out.println("TestLift, pitch:" + i + " current: " + currentPitch + " calcPitchAP: " + expectedAirplanePitch);
            assertEquals(expectedAirplanePitch, airplane.getPitch(), DELTA);
        }
    }

    /**
     * Test the altitude from the Airplane class.
     */
    @Test
    public void testAlitude() {
        //CL = 2 * PI * angle of attack
        //L = 0.5 * p * v^2 * s * CL
        //Drag (force) = CD × 0.5* p * V^2 × S
        //cd of chesna = 0.027
        double pitch = 5;
        int speed = 0;
        // Test pitch adjustments
        for (int i = 0; i < 100; i++) {
            speed = i;
            int currentAltitude = airplane.getAltitude();
            airplane.setPitch(pitch);
            airplane.setSpeed(speed);

            double cl = 2 * Math.PI * (pitch / 100);
            double lift = 0.5 * 0.002308 * Math.pow(speed, 2) * 100 * cl;
            int liftInt = (int) Math.round(lift);
            int verticalSpeed = -40 + liftInt * 2;
            int expectedAltitude = currentAltitude + verticalSpeed;

            airplane.updateAirplane();
            int actualAltitude = airplane.getAltitude();
            assertEquals("expected altitude is not equal to actual altitude.", expectedAltitude, actualAltitude);
            System.out.println("TestLift, pitch:" + pitch + " speed:" + speed + " CL = " + cl + " lift = " + lift + " vs:" + verticalSpeed);
        }
    }

    /**
     * Test propellor class.
     */
    @Test
    public void testPropellor() {
        int rpm = 0;
        airplane.setPitch(5);
        for (int i = -1; i < 2601; i = i + 20) {
            rpm = i;

            //calculate expected speed
            double airplanePitch = this.airplane.getPitch();
            double knotsSpeed = rpm * airplanePitch * 0.00822894;
            if (knotsSpeed < 0) 
                knotsSpeed = 0;

            int currentSpeed = airplane.getSpeed();
            double increasementSpeed = knotsSpeed - currentSpeed;
            int expectedAPspeed = currentSpeed + ((int) increasementSpeed);

            if (expectedAPspeed < 0) 
                expectedAPspeed = 0;

            propellor.setRpm(rpm);
            int actualSpeed = airplane.getSpeed();
            System.out.println("rpmTest, rpm: " + rpm + " currSpeed:" + currentSpeed + " newSpeed: " + expectedAPspeed);
            assertEquals("speed expected is not equal to actual speed", expectedAPspeed, actualSpeed);
        }
    }
}
