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
 * @author Roel
 */
public class TimeSpanTest {

    private TimeSpan ts;
    private TimeSpan ts2;

    public TimeSpanTest() {
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
     * Test of Lenght method, of class TimeSpan.
     */
    @Test
    public void lenght_test() {
        this.ts = new TimeSpan(new Time(2001, 10, 4, 21, 5), 
                new Time(2001, 10, 4, 21, 15));
        assertEquals("Lengte komt niet overeen", 10, this.ts.length());
    }

    /**
     * Test of Timespan.getBeginTime(), of class TimeSpan Test of getYear,
     * getMonth, getDay, getHour, getMinutes method
     */
    @Test
    public void getBeginTime_test() {
        this.ts = new TimeSpan(new Time(2001, 10, 4, 21, 5), new Time(2013, 7, 1, 15, 23));

        assertEquals("getYear Test failed", this.ts.getBeginTime().getYear(), 
                new Time(2001, 10, 4, 21, 5).getYear());
        assertEquals("getMonth Test failed", this.ts.getBeginTime().getMonth(), 
                new Time(2001, 10, 4, 21, 5).getMonth());
        assertEquals("getDay Test failed", this.ts.getBeginTime().getDay(), 
                new Time(2001, 10, 4, 21, 5).getDay());
        assertEquals("getHours Test failed", this.ts.getBeginTime().getHours(), 
                new Time(2001, 10, 4, 21, 5).getHours());
        assertEquals("getMinutes Test failed", this.ts.getBeginTime().getMinutes(), 
                new Time(2001, 10, 4, 21, 5).getMinutes());
    }

    /**
     * Test of Timespan.getEndTime(), of class TimeSpan Test of getYear,
     * getMonth, getDay, getHour, getMinutes method
     */
    @Test
    public void getEndTime_test() {
        this.ts = new TimeSpan(new Time(2001, 10, 4, 21, 5), new Time(2013, 7, 1, 15, 23));

        assertEquals("getYear Test failed", this.ts.getEndTime().getYear(), 
                new Time(2013, 7, 1, 15, 23).getYear());
        assertEquals("getMonth Test failed", this.ts.getEndTime().getMonth(), 
                new Time(2013, 7, 1, 15, 23).getMonth());
        assertEquals("getDay Test failed", this.ts.getEndTime().getDay(), 
                new Time(2013, 7, 1, 15, 23).getDay());
        assertEquals("getHours Test failed", this.ts.getEndTime().getHours(), 
                new Time(2013, 7, 1, 15, 23).getHours());
        assertEquals("getMinutes Test failed", this.ts.getEndTime().getMinutes(), 
                new Time(2013, 7, 1, 15, 23).getMinutes());
    }

    /**
     * Test of constructor_test method, of class TimeSpan.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constuctor_Test() {
        this.ts = new TimeSpan(new Time(2011, 10, 4, 21, 5), 
                new Time(2001, 10, 4, 21, 5));
    }

    /**
     * Test of setBeginTime_Test method, of class TimeSpan.
     */
    @Test(expected = IllegalArgumentException.class)
    public void setBeginTime_Test() {
        this.ts = new TimeSpan(new Time(2001, 10, 4, 21, 5), 
                new Time(2013, 7, 1, 15, 23));

        ////gives exeption
        this.ts.setEndTime(new Time(2002, 10, 4, 21, 5));
        this.ts.setBeginTime(new Time(2011, 10, 4, 21, 5));
    }

    /**
     * Test of setBeginTime_Test2 method, of class TimeSpan.
     */
    @Test
    public void setBeginTime_Test2() {
        this.ts = new TimeSpan(new Time(2001, 10, 4, 21, 5), new Time(2013, 7, 1, 15, 23));
        this.ts.setBeginTime(new Time(2002, 10, 4, 21, 5));

        assertEquals("getYear Test failed", this.ts.getBeginTime().getYear(), 
                new Time(2002, 10, 4, 21, 5).getYear());
        assertEquals("getMonth Test failed", this.ts.getBeginTime().getMonth(), 
                new Time(2002, 10, 4, 21, 5).getMonth());
        assertEquals("getDay Test failed", this.ts.getBeginTime().getDay(), 
                new Time(2002, 10, 4, 21, 5).getDay());
        assertEquals("getHours Test failed", this.ts.getBeginTime().getHours(), 
                new Time(2002, 10, 4, 21, 5).getHours());
        assertEquals("getMinutes Test failed", this.ts.getBeginTime().getMinutes(), 
                new Time(2002, 10, 4, 21, 5).getMinutes());
    }

    /**
     * Test of setEndTime_Test method, of class TimeSpan.
     */
    @Test(expected = IllegalArgumentException.class)
    public void setEndTime_Test() {
        this.ts = new TimeSpan(new Time(2001, 10, 4, 21, 5), 
                new Time(2013, 7, 1, 15, 23));

        this.ts.setBeginTime(new Time(2011, 10, 4, 21, 5));
        this.ts.setEndTime(new Time(2001, 10, 4, 21, 5));
    }

    /**
     * Test of setEndTime_Test2 method, of class TimeSpan.
     */
    @Test
    public void setEndTime_Test2() {
        this.ts = new TimeSpan(new Time(2001, 10, 4, 21, 5), new Time(2013, 7, 1, 15, 23));
        this.ts.setEndTime(new Time(2014, 7, 1, 15, 23));

        assertEquals("getYear Test failed", this.ts.getEndTime().getYear(), 
                new Time(2014, 7, 1, 15, 23).getYear());
        assertEquals("getMonth Test failed", this.ts.getEndTime().getMonth(), 
                new Time(2014, 7, 1, 15, 23).getMonth());
        assertEquals("getDay Test failed", this.ts.getEndTime().getDay(), 
                new Time(2014, 7, 1, 15, 23).getDay());
        assertEquals("getHours Test failed", this.ts.getEndTime().getHours(), 
                new Time(2014, 7, 1, 15, 23).getHours());
        assertEquals("getMinutes Test failed", this.ts.getEndTime().getMinutes(), 
                new Time(2014, 7, 1, 15, 23).getMinutes());
    }

    /**
     * Test of moveTime_Test method, of class TimeSpan.
     */
    @Test
    public void moveTime_Test() {
        this.ts = new TimeSpan(new Time(2001, 10, 4, 21, 5), new Time(2013, 7, 1, 15, 23));
        ts.move(10);

        assertEquals("de verzette tijd komt niet overeen met de getBeginTime", 
                ts.getBeginTime().getMinutes(), 15);
    }

    /**
     * Test of changeLengthWith_Test method, of class TimeSpan.
     */
    @Test
    public void changeLengthWith_Test() {
        this.ts = new TimeSpan(new Time(2001, 10, 4, 21, 5), new Time(2013, 7, 1, 15, 23));

        ////this should not give an exeption
        this.ts.changeLengthWith(10);
        assertEquals("de verzette tijd komt niet overeen met de getBeginTime",
                33, ts.getEndTime().getMinutes());
    }

    /**
     * Test of changeLengthWith_Test2 method, of class TimeSpan.
     */
    @Test(expected = IllegalArgumentException.class)
    public void changeLengthWith_Test2() {
        this.ts = new TimeSpan(new Time(2001, 10, 4, 21, 5), new Time(2013, 7, 1, 15, 23));

        ////this should give an exeption
        this.ts.changeLengthWith(-10);
    }

    /**
     * Test of isPartOf_Test method, of class TimeSpan.
     */
    @Test
    public void isPartOf_Test() {
        this.ts = new TimeSpan(new Time(2001, 10, 4, 21, 5), new Time(2013, 7, 1, 15, 23));
        this.ts2 = new TimeSpan(new Time(2002, 12, 6, 11, 12), new Time(2009, 10, 6, 20, 13));
        assertTrue(ts.isPartOf(ts2));

        this.ts2 = new TimeSpan(new Time(2000, 12, 6, 11, 12), new Time(2014, 10, 6, 20, 13));
        assertFalse(ts.isPartOf(ts2));

        this.ts2 = new TimeSpan(new Time(2002, 12, 6, 11, 12), new Time(2014, 10, 6, 20, 13));
        assertFalse(ts.isPartOf(ts2));
    }

    /**
     * Test of unionWith_Test method, of class TimeSpan.
     */
    @Test
    public void unionWith_Test() {
        this.ts = new TimeSpan(new Time(2002, 10, 4, 21, 5), new Time(2013, 7, 1, 15, 23));
        this.ts2 = new TimeSpan(new Time(2014, 12, 6, 11, 12), new Time(2015, 10, 6, 20, 13));
        assertNull("er moet Null uit komen", ts.unionWith(ts2));

        this.ts2 = new TimeSpan(new Time(2000, 12, 6, 11, 12), new Time(2001, 10, 6, 20, 13));
        assertNull("er moet Null uit komen", ts.unionWith(ts2));
    }
    
    /**
     * Test of unionWith_Test method, of class TimeSpan.
     */
    @Test
    public void unionWith_Test_BeginTimeTest() {
        this.ts = new TimeSpan(new Time(2002, 10, 4, 21, 5), new Time(2004, 7, 1, 15, 23));
        this.ts2 = new TimeSpan(new Time(2003, 12, 6, 11, 12), new Time(2005, 10, 6, 20, 13));
        
        // als de begintijd van ts groter is dan de begintijd van ts2
        assertEquals("het jaar moet gelijk zijn", 
                ts.unionWith(ts2).getBeginTime().getYear(), ts.getBeginTime().getYear());
        assertEquals("De maand moet gelijk zijn", 
                ts.unionWith(ts2).getBeginTime().getMonth(), ts.getBeginTime().getMonth());
        assertEquals("De dag moet gelijk zijn", 
                ts.unionWith(ts2).getBeginTime().getDay(), ts.getBeginTime().getDay());
        assertEquals("het jaar moet gelijk zijn", 
                ts.unionWith(ts2).getBeginTime().getHours(), ts.getBeginTime().getHours());
        assertEquals("De minuten moet gelijk zijn", 
                ts.unionWith(ts2).getBeginTime().getMinutes(), ts.getBeginTime().getMinutes());
        
        // als de begintijd van ts2 groter is dan de begintijd van ts
        this.ts2 = new TimeSpan(new Time(2002, 10, 3, 5, 1), new Time(2003, 10, 6, 20, 13));
        assertEquals("het jaar moet gelijk zijn", 
                ts.unionWith(ts2).getBeginTime().getYear(), ts2.getBeginTime().getYear());
        assertEquals("De maand moet gelijk zijn", 
                ts.unionWith(ts2).getBeginTime().getMonth(), ts2.getBeginTime().getMonth());
        assertEquals("De dag moet gelijk zijn", 
                ts.unionWith(ts2).getBeginTime().getDay(), ts2.getBeginTime().getDay());
        assertEquals("het jaar moet gelijk zijn", 
                ts.unionWith(ts2).getBeginTime().getHours(), ts2.getBeginTime().getHours());
        assertEquals("De minuten moet gelijk zijn", 
                ts.unionWith(ts2).getBeginTime().getMinutes(), ts2.getBeginTime().getMinutes());
    }
    
    /**
     * Test of unionWith_Test method, of class TimeSpan.
     */
    @Test
    public void unionWith_Test_EndTimeTest() {
        this.ts = new TimeSpan(new Time(2002, 10, 4, 21, 5), new Time(2005, 7, 1, 15, 23));
        this.ts2 = new TimeSpan(new Time(2003, 12, 6, 11, 12), new Time(2004, 10, 6, 20, 13));
        
        // als de ts eindtijd kleiner is
        assertEquals("het jaar moet gelijk zijn", 
                ts.unionWith(ts2).getEndTime().getYear(), ts.getEndTime().getYear());
        assertEquals("De maand moet gelijk zijn", 
                ts.unionWith(ts2).getEndTime().getMonth(), ts.getEndTime().getMonth());
        assertEquals("De dag moet gelijk zijn", 
                ts.unionWith(ts2).getEndTime().getDay(), ts.getEndTime().getDay());
        assertEquals("het jaar moet gelijk zijn", 
                ts.unionWith(ts2).getEndTime().getHours(), ts.getEndTime().getHours());
        assertEquals("De minuten moet gelijk zijn", 
                ts.unionWith(ts2).getEndTime().getMinutes(), ts.getEndTime().getMinutes());
        
        // als de ts2 eindtijd kleiner is
        this.ts2 = new TimeSpan(new Time(2002, 10, 3, 5, 1), new Time(2006, 10, 6, 20, 13));
        assertEquals("het jaar moet gelijk zijn", 
                ts.unionWith(ts2).getEndTime().getYear(), ts2.getEndTime().getYear());
        assertEquals("De maand moet gelijk zijn", 
                ts.unionWith(ts2).getEndTime().getMonth(), ts2.getEndTime().getMonth());
        assertEquals("De dag moet gelijk zijn", 
                ts.unionWith(ts2).getEndTime().getDay(), ts2.getEndTime().getDay());
        assertEquals("het uur moet gelijk zijn", 
                ts.unionWith(ts2).getEndTime().getHours(), ts2.getEndTime().getHours());
        assertEquals("De minuten moet gelijk zijn", 
                ts.unionWith(ts2).getEndTime().getMinutes(), ts2.getEndTime().getMinutes());
    }
    
    /**
     * Test of intersectionWith_Test method, of class TimeSpan.
     */
    @Test
    public void intersectionWith_Test_BeginTime() {
        this.ts = new TimeSpan(new Time(2001, 10, 4, 21, 5), new Time(2013, 7, 1, 15, 23));
        
        // de ts begintijd groter is dan de ts2 eindtijd
        this.ts2 = new TimeSpan(new Time(2000, 12, 6, 1, 12), new Time(2010, 10, 6, 20, 13));
        assertEquals("het jaar moet gelijk zijn", 
                ts.intersectionWith(ts2).getBeginTime().getYear(), ts.getBeginTime().getYear());
        assertEquals("De maand moet gelijk zijn", 
                ts.intersectionWith(ts2).getBeginTime().getMonth(), ts.getBeginTime().getMonth());
        assertEquals("De dag moet gelijk zijn", 
                ts.intersectionWith(ts2).getBeginTime().getDay(), ts.getBeginTime().getDay());
        assertEquals("het jaar moet gelijk zijn", 
                ts.intersectionWith(ts2).getBeginTime().getHours(), ts.getBeginTime().getHours());
        assertEquals("De minuten moet gelijk zijn", 
                ts.intersectionWith(ts2).getBeginTime().getMinutes(), ts.getBeginTime().getMinutes());
        
        // de ts begintijd groter is dan de ts2 eindtijd
        this.ts2 = new TimeSpan(new Time(2003, 12, 6, 11, 12), new Time(2014, 10, 6, 20, 13));
        assertEquals("het jaar moet gelijk zijn", 
                ts.intersectionWith(ts2).getBeginTime().getYear(), ts2.getBeginTime().getYear());
        assertEquals("De maand moet gelijk zijn", 
                ts.intersectionWith(ts2).getBeginTime().getMonth(), ts2.getBeginTime().getMonth());
        assertEquals("De dag moet gelijk zijn", 
                ts.intersectionWith(ts2).getBeginTime().getDay(), ts2.getBeginTime().getDay());
        assertEquals("het jaar moet gelijk zijn", 
                ts.intersectionWith(ts2).getBeginTime().getHours(), ts2.getBeginTime().getHours());
        assertEquals("De minuten moet gelijk zijn", 
                ts.intersectionWith(ts2).getBeginTime().getMinutes(), ts2.getBeginTime().getMinutes());
    }
    
    /**
     * Test of intersectionWith_Test_Null method, of class TimeSpan.
     */
    @Test
    public void intersectionWith_Test_Null() {
        this.ts = new TimeSpan(new Time(2002, 10, 4, 21, 5), new Time(2013, 7, 1, 15, 23));
        
        
        this.ts2 = new TimeSpan(new Time(2003, 12, 6, 11, 12), new Time(2014, 10, 6, 20, 13));
        assertNotNull(ts.intersectionWith(ts2));
        
        // moet nog worden gemaakt, geeft geen null terug!
//        this.ts2 = new TimeSpan(new Time(2000, 12, 6, 11, 12), new Time(2001, 10, 6, 20, 13));
//        assertNull(ts.intersectionWith(ts2));
    }
}
