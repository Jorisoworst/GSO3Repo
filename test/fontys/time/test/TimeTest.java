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

    private String[] failMessages
            = {
                "month must be within 1..12",
                "day must be within 1..31",
                "hours must be within 0..23",
                "minutes must be within 0..59",
                "de dag in de week wordt niet goed opgehaald",
                "het berekenen van het verschil tussen twee tijden is niet goed geïmplementeerd"
            };

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
    public void constructorTest() {
        /**
         * creation of a time-object with year y, month m, day d, hours h and
         * minutes m; if the combination of y-m-d refers to a non-existing date
         * the value of this Time-object will be not guaranteed
         *
         * @param y
         * @param m 1≤m≤12
         * @param d 1≤d≤31
         * @param h 0≤h≤23
         * @param min 0≤m≤59
         */
        ITime time;

        // Maanden testcases
        try {
            time = new Time(this.year, 0, this.day, this.hours, this.minutes);
            fail(this.failMessages[0]);
        } catch (IllegalArgumentException iae) {
        }

        try {
            time = new Time(this.year, 13, this.day, this.hours, this.minutes);
            fail(this.failMessages[0]);
        } catch (IllegalArgumentException iae) {
        }

        // Dagen testcases
        try {
            time = new Time(this.year, this.month, 0, this.hours, this.minutes);
            fail(this.failMessages[1]);
        } catch (IllegalArgumentException iae) {
        }

        try {
            time = new Time(this.year, this.month, 32, this.hours, this.minutes);
            fail(this.failMessages[1]);
        } catch (IllegalArgumentException iae) {
        }

        // Uren testcases
        try {
            time = new Time(this.year, this.month, this.day, -1, this.minutes);
            fail(this.failMessages[2]);
        } catch (IllegalArgumentException iae) {
        }

        try {
            time = new Time(this.year, this.month, this.day, 24, this.minutes);
            fail(this.failMessages[2]);
        } catch (IllegalArgumentException iae) {
        }

        // Minuten testcases
        try {
            time = new Time(this.year, this.month, this.day, this.hours, -1);
            fail(this.failMessages[3]);
        } catch (IllegalArgumentException iae) {
        }

        try {
            time = new Time(this.year, this.month, this.day, this.hours, 60);
            fail(this.failMessages[3]);
        } catch (IllegalArgumentException iae) {
        }
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
        assertEquals(this.failMessages[4], this.day, this.time.getDay());
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
        for (int i = 6; i < 13; i++) {
            ITime time = new Time(this.year, this.month, i, this.hours, this.minutes);
            assertEquals(this.failMessages[4], DayInWeek.values()[i - 6], time.getDayInWeek());
            assertEquals(this.failMessages[4], DayInWeek.valueOf(time.getDayInWeek().toString()), time.getDayInWeek());
        }
    }

    @Test
    public void plusTest() {
        this.time = this.time.plus(40);
        assertEquals("het toevoegen van minuten is niet goed geïmplementeerd", this.minutes + 40, this.time.getMinutes());
    }

    @Test
    public void differenceTest() {
        ITime time = new Time(this.year, this.month, this.day, this.hours, this.minutes);
        assertEquals(this.failMessages[5], 0, time.difference(this.time));
    }

    @Test
    public void compareToTest() {
        ITime time = new Time(this.year, this.month, this.day, this.hours, this.minutes);
        assertEquals(this.failMessages[5], 0, time.compareTo(this.time));
    }
}
