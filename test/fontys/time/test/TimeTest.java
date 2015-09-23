/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time.test;

import fontys.time.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Joris
 */
public class TimeTest {

    private ITime time;
    private int year;
    private int month;
    private int day;
    private int hours;
    private int minutes;

    @Before
    public void setUp() {
        this.year = 2015;
        this.month = 9;
        this.day = 23;
        this.hours = 9;
        this.minutes = 12;
        this.time = new Time(this.year, this.month, this.day, this.hours, this.minutes);
    }
    
    @Test
    (expected=IllegalArgumentException.class)
    public void constructorTest()
    {
        /**
         * creation of a time-object with year y, month m, day d, hours h and
         * minutes m; if the combination of y-m-d refers to a non-existing date 
         * the value of this Time-object will be not guaranteed 
         * @param y 
         * @param m 1≤m≤12
         * @param d 1≤d≤31
         * @param h 0≤h≤23
         * @param min 0≤m≤59
         */
        ITime time;
        
        time = new Time(1991, 13, 32, 24, 60);       
        time = new Time(1991, 0, 0, -1, -1);
    }

    @Test
    public void getYearTest() {
        assertEquals("het jaar wordt niet goed bijgehouden", this.year, this.time.getYear());
    }

    @Test
    public void getMonthTest() {
        assertEquals("de maand wordt niet goed bijgehouden", this.month, this.time.getMonth());
    }

    @Test
    public void getDayTest() {
        assertEquals("de dag wordt niet goed bijgehouden", this.day, this.time.getDay());
    }

    @Test
    public void getHoursTest() {
        assertEquals("de uren wordt niet goed bijgehouden", this.hours, this.time.getHours());
    }

    @Test
    public void getMinutesTest() {
        assertEquals("de minuten wordt niet goed bijgehouden", this.minutes, this.time.getMinutes());
    }

    @Test
    public void getDayInWeekTest() {
        assertEquals("de dag in de week wordt niet goed opgehaald", this.year, this.time.getDayInWeek());
    }

    @Test
    public void plusTest() {
        this.time.plus(40);
        assertEquals("het toevoegen van minuten is niet goed geïmplementeerd", this.minutes + 40, this.time.getMinutes());
    }

    @Test
    public void differenceTest() {
        ITime time = new Time(this.year, this.month, this.day, this.hours, this.minutes);
    }
}
