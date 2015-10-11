package fontys.time.test;

/**
 * Imports
 */
import fontys.domain.*;
import fontys.time.*;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Roel
 */
public class ContactTest {
    
    private Contact ct;
    private String TestName = "TestName";
    private String subject = "TestSubject";
    private ITimeSpan ts;
    private Appointment ap;
    private ArrayList<Appointment> Testagenda;
    
    /**
     * UnitTest of class Contract
     */    
    public ContactTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.Testagenda = new ArrayList<>();
    }

    @After
    public void tearDown() {
    }
    
    /**
     * GetName_Test method, of class Contact.
     */
    @Test
    public void GetName_Test() {
        this.ct = new Contact(this.TestName);
        
        assertEquals("De namen moeten gelijk aan elkaar zijn",this.TestName ,this.ct.getName());
    }
    
    /**
     * addAppointment_Test method, of class Contact.
     */
    @Test
    public void addAppointment_Test() {
        this.ct = new Contact(this.TestName);
        this.ts = new TimeSpan(new Time(2001, 10, 4, 21, 5), new Time(2013, 7, 1, 15, 23));
        this.ap = new Appointment(this.subject, this.ts);
        
        assertTrue(this.ct.addAppointment(this.ap)); //cometaar er bij
        
        this.ct.addAppointment((this.ap));
        assertFalse(this.ct.addAppointment(this.ap));
    }
    
    /**
     * removeAppointment_Test method, of class Contact.
     */
    @Test
    public void removeAppointment_Test() {
        this.ct = new Contact(this.TestName);
        this.ts = new TimeSpan(new Time(2001, 10, 4, 21, 5), new Time(2013, 7, 1, 15, 23));
        this.ap = new Appointment(this.subject, this.ts);
        
        this.ct.addAppointment((this.ap));
        assertTrue(this.ct.getAppointment(ap)); //cometaar er bij
        
        this.ct.removeAppointment(this.ap);
        assertFalse(this.ct.getAppointment(ap));
    }
    
    /**
     * appointments_Test method, of class Contact. //werkt nog niet helemaal!!!
     */
    @Test
    public void appointments_Test() {
        this.ct = new Contact(this.TestName);
        this.ts = new TimeSpan(new Time(2001, 10, 4, 21, 5), new Time(2013, 7, 1, 15, 23));
        this.ap = new Appointment(this.subject, this.ts);
        this.ct.addAppointment(this.ap);
        this.Testagenda.add(this.ap);
               
        assertEquals("Deze lijsten zijn niet gelijk aan elkaar", 
                this.Testagenda.size(), this.ct.appointments().size());
        assertEquals("Deze lijsten zijn niet gelijk aan elkaar", 
                this.Testagenda.get(0), this.ct.appointments().get(0));
    }
}
