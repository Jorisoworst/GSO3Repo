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
public class TimeSpan2Test {

    private ITimeSpan timeSpan1, timeSpan2;
    private ITime time1, time2, time3, time4, time5, time6, time7, time8, time9,
            time10, time11, time12, time13, time14, time15, time16, time17, time18,
            time19, time20, time21, time22, time23;

    @Before
    public void setUp() {
        this.time1 = new Time(2001, 10, 4, 21, 5);
        this.time2 = new Time(2001, 10, 4, 21, 15);
        this.time3 = new Time(2013, 7, 1, 15, 23);
        this.time4 = new Time(2011, 10, 4, 21, 5);
        this.time5 = new Time(2002, 10, 4, 21, 5);
        this.time6 = new Time(2014, 7, 1, 15, 23);
        this.time7 = new Time(2002, 12, 6, 11, 12);
        this.time8 = new Time(2009, 10, 6, 20, 13);
        this.time9 = new Time(2000, 12, 6, 11, 12);
        this.time10 = new Time(2014, 10, 6, 20, 13);
        this.time11 = new Time(2014, 12, 6, 11, 12);
        this.time12 = new Time(2015, 10, 6, 20, 13);
        this.time13 = new Time(2001, 10, 6, 20, 13);
        this.time14 = new Time(2004, 7, 1, 15, 23);
        this.time15 = new Time(2003, 12, 6, 11, 12);
        this.time16 = new Time(2005, 10, 6, 20, 13);
        this.time17 = new Time(2002, 10, 3, 5, 1);
        this.time18 = new Time(2003, 10, 6, 20, 13);
        this.time19 = new Time(2005, 7, 1, 15, 23);
        this.time20 = new Time(2004, 10, 6, 20, 13);
        this.time21 = new Time(2006, 10, 6, 20, 13);
        this.time22 = new Time(2000, 12, 6, 1, 12);
        this.time23 = new Time(2010, 10, 6, 20, 13);
    }

    /**
     * Test of Lenght method, of class TimeSpan.
     */
    @Test
    public void lenght_test() {
        this.timeSpan1 = new TimeSpan2(this.time1, this.time2.difference(this.time1));
        
        assertEquals("Lengte komt niet overeen", 10, this.timeSpan1.length());
    }

    /**
     * Test of Timespan.getBeginTime(), of class TimeSpan Test of getYear,
     * getMonth, getDay, getHour, getMinutes method
     */
    @Test
    public void getBeginTime_test() {
        this.timeSpan1 = new TimeSpan2(this.time1, this.time3.difference(this.time1));

        assertEquals("getYear Test failed", this.timeSpan1.getBeginTime().getYear(), 
                this.time1.getYear());
        assertEquals("getMonth Test failed", this.timeSpan1.getBeginTime().getMonth(), 
                this.time1.getMonth());
        assertEquals("getDay Test failed", this.timeSpan1.getBeginTime().getDay(), 
                this.time1.getDay());
        assertEquals("getHours Test failed", this.timeSpan1.getBeginTime().getHours(), 
                this.time1.getHours());
        assertEquals("getMinutes Test failed", this.timeSpan1.getBeginTime().getMinutes(), 
                this.time1.getMinutes());
    }

    /**
     * Test of Timespan.getEndTime(), of class TimeSpan Test of getYear,
     * getMonth, getDay, getHour, getMinutes method
     */
    @Test
    public void getEndTime_test() {
        this.timeSpan1 = new TimeSpan2(this.time1, this.time3.difference(this.time1));

        assertEquals("getYear Test failed", this.timeSpan1.getEndTime().getYear(), 
                this.time3.getYear());
        assertEquals("getMonth Test failed", this.timeSpan1.getEndTime().getMonth(), 
                this.time3.getMonth());
        assertEquals("getDay Test failed", this.timeSpan1.getEndTime().getDay(), 
                this.time3.getDay());
        assertEquals("getHours Test failed", this.timeSpan1.getEndTime().getHours(), 
                this.time3.getHours());
        assertEquals("getMinutes Test failed", this.timeSpan1.getEndTime().getMinutes(), 
                this.time3.getMinutes());
    }

    /**
     * Test of constructor_test method, of class TimeSpan.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructor_Test() {
        this.timeSpan1 = new TimeSpan2(this.time4, this.time1.difference(this.time4));
    }

    /**
     * Test of setBeginTime_Test method, of class TimeSpan.
     */
    @Test(expected = IllegalArgumentException.class)
    public void setBeginTime_Test() {
        this.timeSpan1 = new TimeSpan2(this.time1, this.time3.difference(this.time1));

        // geeft exception
        this.timeSpan1.setEndTime(this.time5);
        this.timeSpan1.setBeginTime(this.time4);
        // set begin time moet worden getest als begin time aangepast wordt dat de duration ook aangepast wordt (eindtijd)
    }

    /**
     * Test of setBeginTime_Test2 method, of class TimeSpan.
     */
    @Test
    public void setBeginTime_Test2() {
        this.timeSpan1 = new TimeSpan2(this.time1, this.time3.difference(this.time1));
        this.timeSpan1.setBeginTime(this.time5);

        assertEquals("getYear Test failed", this.timeSpan1.getBeginTime().getYear(), 
                this.time5.getYear());
        assertEquals("getMonth Test failed", this.timeSpan1.getBeginTime().getMonth(), 
                this.time5.getMonth());
        assertEquals("getDay Test failed", this.timeSpan1.getBeginTime().getDay(), 
                this.time5.getDay());
        assertEquals("getHours Test failed", this.timeSpan1.getBeginTime().getHours(), 
                this.time5.getHours());
        assertEquals("getMinutes Test failed", this.timeSpan1.getBeginTime().getMinutes(), 
                this.time5.getMinutes());
    }

    /**
     * Test of setEndTime_Test method, of class TimeSpan.
     */
    @Test(expected = IllegalArgumentException.class)
    public void setEndTime_Test() {
        this.timeSpan1 = new TimeSpan2(this.time1, this.time3.difference(this.time1));

        this.timeSpan1.setBeginTime(this.time4);
        this.timeSpan1.setEndTime(this.time1);
    }

    /**
     * Test of setEndTime_Test2 method, of class TimeSpan.
     */
    @Test
    public void setEndTime_Test2() {
        this.timeSpan1 = new TimeSpan2(this.time1, this.time3.difference(this.time1));
        this.timeSpan1.setEndTime(this.time6);

        assertEquals("getYear Test failed", this.timeSpan1.getEndTime().getYear(), 
                this.time6.getYear());
        assertEquals("getMonth Test failed", this.timeSpan1.getEndTime().getMonth(), 
                this.time6.getMonth());
        assertEquals("getDay Test failed", this.timeSpan1.getEndTime().getDay(), 
                this.time6.getDay());
        assertEquals("getHours Test failed", this.timeSpan1.getEndTime().getHours(), 
                this.time6.getHours());
        assertEquals("getMinutes Test failed", this.timeSpan1.getEndTime().getMinutes(), 
                this.time6.getMinutes());
    }

    /**
     * Test of moveTime_Test method, of class TimeSpan.
     */
    @Test
    public void moveTime_Test() {
        this.timeSpan1 = new TimeSpan2(this.time1, this.time3.difference(this.time1));
        this.timeSpan1.move(10);

        assertEquals("de verzette tijd komt niet overeen met de getBeginTime", 
                this.timeSpan1.getBeginTime().getMinutes(), 15);
    }

    /**
     * Test of changeLengthWith_Test method, of class TimeSpan.
     */
    @Test
    public void changeLengthWith_Test() {
        this.timeSpan1 = new TimeSpan2(this.time1, this.time3.difference(this.time1));

        // dit zou geen exeption moeten geven
        this.timeSpan1.changeLengthWith(10);
        assertEquals("de verzette tijd komt niet overeen met de getBeginTime",
                33, this.timeSpan1.getEndTime().getMinutes());
    }

    /**
     * Test of changeLengthWith_Test2 method, of class TimeSpan.
     */
    @Test(expected = IllegalArgumentException.class)
    public void changeLengthWith_Test2() {
        this.timeSpan1 = new TimeSpan2(this.time1, this.time3.difference(this.time1));

        // geeft exception
        this.timeSpan1.changeLengthWith(-10);
    }

    /**
     * Test of isPartOf_Test method, of class TimeSpan.
     */
    @Test
    public void isPartOf_Test() {
        this.timeSpan1 = new TimeSpan2(this.time1, this.time3.difference(this.time1));
        this.timeSpan2 = new TimeSpan2(this.time7, this.time8.difference(this.time7));
        assertTrue(timeSpan1.isPartOf(this.timeSpan2));

        this.timeSpan2 = new TimeSpan2(this.time9, this.time10.difference(this.time9));
        assertFalse(this.timeSpan1.isPartOf(this.timeSpan2));

        this.timeSpan2 = new TimeSpan2(this.time7, this.time10.difference(this.time7));
        assertFalse(this.timeSpan1.isPartOf(this.timeSpan2));
    }

    /**
     * Test of unionWith_Test method, of class TimeSpan.
     */
    @Test
    public void unionWith_Test() {
        this.timeSpan1 = new TimeSpan2(this.time5, this.time3.difference(this.time5));
        this.timeSpan2 = new TimeSpan2(this.time11, this.time12.difference(this.time11));
        assertNull("er moet Null uit komen", this.timeSpan1.unionWith(this.timeSpan2));

        this.timeSpan2 = new TimeSpan2(this.time9, this.time13.difference(this.time9));
        assertNull("er moet Null uit komen", this.timeSpan1.unionWith(this.timeSpan2));
    }
    
    /**
     * Test of unionWith_Test method, of class TimeSpan.
     */
    @Test
    public void unionWith_Test_BeginTimeTest() {
        this.timeSpan1 = new TimeSpan2(this.time5, this.time14.difference(this.time5));
        this.timeSpan2 = new TimeSpan2(this.time15, this.time16.difference(this.time15));
        
        // als de begintijd van timeSpan1 groter is dan de begintijd van timeSpan2
        assertEquals("het jaar moet gelijk zijn", 
                this.timeSpan1.unionWith(this.timeSpan2).getBeginTime().getYear(),
                this.timeSpan1.getBeginTime().getYear());
        assertEquals("De maand moet gelijk zijn", 
                this.timeSpan1.unionWith(this.timeSpan2).getBeginTime().getMonth(),
                this.timeSpan1.getBeginTime().getMonth());
        assertEquals("De dag moet gelijk zijn", 
                this.timeSpan1.unionWith(this.timeSpan2).getBeginTime().getDay(),
                this.timeSpan1.getBeginTime().getDay());
        assertEquals("het jaar moet gelijk zijn", 
                this.timeSpan1.unionWith(this.timeSpan2).getBeginTime().getHours(),
                this.timeSpan1.getBeginTime().getHours());
        assertEquals("De minuten moet gelijk zijn", 
                this.timeSpan1.unionWith(this.timeSpan2).getBeginTime().getMinutes(),
                this.timeSpan1.getBeginTime().getMinutes());
        
        // als de begintijd van timeSpan2 groter is dan de begintijd van timeSpan1
        this.timeSpan2 = new TimeSpan2(this.time17, this.time18.difference(this.time17));
        assertEquals("het jaar moet gelijk zijn", 
                this.timeSpan1.unionWith(this.timeSpan2).getBeginTime().getYear(),
                this.timeSpan2.getBeginTime().getYear());
        assertEquals("De maand moet gelijk zijn", 
                this.timeSpan1.unionWith(this.timeSpan2).getBeginTime().getMonth(),
                this.timeSpan2.getBeginTime().getMonth());
        assertEquals("De dag moet gelijk zijn", 
                this.timeSpan1.unionWith(this.timeSpan2).getBeginTime().getDay(),
                this.timeSpan2.getBeginTime().getDay());
        assertEquals("het jaar moet gelijk zijn", 
                this.timeSpan1.unionWith(this.timeSpan2).getBeginTime().getHours(),
                this.timeSpan2.getBeginTime().getHours());
        assertEquals("De minuten moet gelijk zijn", 
                this.timeSpan1.unionWith(this.timeSpan2).getBeginTime().getMinutes(),
                this.timeSpan2.getBeginTime().getMinutes());
    }
    
    /**
     * Test of unionWith_Test method, of class TimeSpan.
     */
    @Test
    public void unionWith_Test_EndTimeTest() {
        this.timeSpan1 = new TimeSpan2(this.time5, this.time19.difference(this.time5));
        this.timeSpan2 = new TimeSpan2(this.time15, this.time20.difference(this.time15));
        
        // als de timeSpan1 eindtijd kleiner is
        assertEquals("het jaar moet gelijk zijn", 
                this.timeSpan1.unionWith(this.timeSpan2).getEndTime().getYear(),
                this.timeSpan1.getEndTime().getYear());
        assertEquals("De maand moet gelijk zijn", 
                this.timeSpan1.unionWith(this.timeSpan2).getEndTime().getMonth(),
                this.timeSpan1.getEndTime().getMonth());
        assertEquals("De dag moet gelijk zijn", 
                this.timeSpan1.unionWith(this.timeSpan2).getEndTime().getDay(),
                this.timeSpan1.getEndTime().getDay());
        assertEquals("het jaar moet gelijk zijn", 
                this.timeSpan1.unionWith(this.timeSpan2).getEndTime().getHours(),
                this.timeSpan1.getEndTime().getHours());
        assertEquals("De minuten moet gelijk zijn", 
                this.timeSpan1.unionWith(this.timeSpan2).getEndTime().getMinutes(),
                this.timeSpan1.getEndTime().getMinutes());
        
        // als de timeSpan2 eindtijd kleiner is
        this.timeSpan2 = new TimeSpan2(this.time17, this.time21.difference(this.time17));
        assertEquals("het jaar moet gelijk zijn", 
                this.timeSpan1.unionWith(this.timeSpan2).getEndTime().getYear(),
                this.timeSpan2.getEndTime().getYear());
        assertEquals("De maand moet gelijk zijn", 
                this.timeSpan1.unionWith(this.timeSpan2).getEndTime().getMonth(),
                this.timeSpan2.getEndTime().getMonth());
        assertEquals("De dag moet gelijk zijn", 
                this.timeSpan1.unionWith(this.timeSpan2).getEndTime().getDay(),
                this.timeSpan2.getEndTime().getDay());
        assertEquals("het uur moet gelijk zijn", 
                this.timeSpan1.unionWith(this.timeSpan2).getEndTime().getHours(),
                this.timeSpan2.getEndTime().getHours());
        assertEquals("De minuten moet gelijk zijn", 
                this.timeSpan1.unionWith(this.timeSpan2).getEndTime().getMinutes(),
                this.timeSpan2.getEndTime().getMinutes());
    }
    
    /**
     * Test of intersectionWith_Test method, of class TimeSpan.
     */
    @Test
    public void intersectionWith_Test_BeginTime() {
        this.timeSpan1 = new TimeSpan2(this.time1, this.time3.difference(this.time1));
        
        // de timeSpan1 begintijd groter is dan de timeSpan2 eindtijd
        this.timeSpan2 = new TimeSpan2(this.time22, this.time23.difference(this.time22));
        assertEquals("het jaar moet gelijk zijn", 
                this.timeSpan1.intersectionWith(this.timeSpan2).getBeginTime().getYear(),
                this.timeSpan1.getBeginTime().getYear());
        assertEquals("De maand moet gelijk zijn", 
                this.timeSpan1.intersectionWith(this.timeSpan2).getBeginTime().getMonth(),
                this.timeSpan1.getBeginTime().getMonth());
        assertEquals("De dag moet gelijk zijn", 
                this.timeSpan1.intersectionWith(this.timeSpan2).getBeginTime().getDay(),
                this.timeSpan1.getBeginTime().getDay());
        assertEquals("het jaar moet gelijk zijn", 
                this.timeSpan1.intersectionWith(this.timeSpan2).getBeginTime().getHours(),
                this.timeSpan1.getBeginTime().getHours());
        assertEquals("De minuten moet gelijk zijn", 
                this.timeSpan1.intersectionWith(this.timeSpan2).getBeginTime().getMinutes(),
                this.timeSpan1.getBeginTime().getMinutes());
        
        // de timeSpan1 begintijd groter is dan de timeSpan2 eindtijd
        this.timeSpan2 = new TimeSpan2(this.time15, this.time10.difference(this.time15));
        assertEquals("het jaar moet gelijk zijn", 
                this.timeSpan1.intersectionWith(this.timeSpan2).getBeginTime().getYear(),
                this.timeSpan2.getBeginTime().getYear());
        assertEquals("De maand moet gelijk zijn", 
                this.timeSpan1.intersectionWith(this.timeSpan2).getBeginTime().getMonth(),
                this.timeSpan2.getBeginTime().getMonth());
        assertEquals("De dag moet gelijk zijn", 
                this.timeSpan1.intersectionWith(this.timeSpan2).getBeginTime().getDay(),
                this.timeSpan2.getBeginTime().getDay());
        assertEquals("het jaar moet gelijk zijn", 
                this.timeSpan1.intersectionWith(this.timeSpan2).getBeginTime().getHours(),
                this.timeSpan2.getBeginTime().getHours());
        assertEquals("De minuten moet gelijk zijn", 
                this.timeSpan1.intersectionWith(this.timeSpan2).getBeginTime().getMinutes(),
                this.timeSpan2.getBeginTime().getMinutes());
    }
    
    /**
     * Test of intersectionWith_Test_Null method, of class TimeSpan.
     */
    @Test
    public void intersectionWith_Test_Null() {
        this.timeSpan1 = new TimeSpan2(this.time5, this.time3.difference(this.time5));        
        this.timeSpan2 = new TimeSpan2(this.time15, this.time10.difference(this.time15));
        assertNotNull(this.timeSpan1.intersectionWith(this.timeSpan2));
    }
}
