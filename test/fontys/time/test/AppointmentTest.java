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
public class AppointmentTest {
    
    private Appointment ap;
    private String TestName = "TestName";
    private String subject = "TestSubject";
    private ITimeSpan ts;
    private Contact ct;
    private ArrayList<Contact> Testinvitees;
    
   /**
     * UnitTest of class Contract
     */    
    public AppointmentTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.Testinvitees = new ArrayList<>();
    }

    @After
    public void tearDown() {
    }
    
    /**
     * getTimeSpan_Test method, of class Contact.
     */
    @Test
    public void getTimeSpan_Test() {
        this.ap = new Appointment(this.subject, this.ts);
        
        assertEquals("De TimeSpan moeten gelijk aan elkaar zijn", this.ts, this.ap.getTimeSpan());
    } 
    
    /**
     * addContact_Test method, of class Contact.
     */
    @Test
    public void addContact_Test() {
        this.ts = new TimeSpan(new Time(2001, 10, 4, 21, 5), new Time(2013, 7, 1, 15, 23));
        this.ap = new Appointment(this.subject, this.ts);
        this.ct = new Contact(this.TestName);
        
        assertTrue(this.ap.addContact(this.ct));
        
        this.ap.addContact((this.ct));
        assertFalse(this.ap.addContact(this.ct));
    }
    
    /**
     * removeAppointment_Test method, of class Contact.
     */
    @Test
    public void removeContact() {
        this.ct = new Contact(this.TestName);
        this.ts = new TimeSpan(new Time(2001, 10, 4, 21, 5), new Time(2013, 7, 1, 15, 23));
        this.ap = new Appointment(this.subject, this.ts);
        
        this.ap.addContact((this.ct));
        assertTrue(this.ap.getContact(ct));
        
        this.ap.removeContact(this.ct);
        assertFalse(this.ap.getContact(ct));
    }
    
    /**
     * getInvitees_Test method, of class Contact. //werkt nog niet helemaal!!!
     */
    @Test
    public void getInvitees_Test() {
        this.ts = new TimeSpan(new Time(2001, 10, 4, 21, 5), new Time(2013, 7, 1, 15, 23));
        this.ap = new Appointment(this.subject, this.ts);
        this.ct = new Contact(this.TestName);
        this.ap.addContact(this.ct);
        this.Testinvitees.add(this.ct);
               
        assertEquals("Deze lijsten zijn niet gelijk aan elkaar", 
                this.Testinvitees.size(), this.ap.getInvitees().size());
        assertEquals("Deze lijsten zijn niet gelijk aan elkaar", 
                this.Testinvitees.get(0), this.ap.getInvitees().get(0));
    }
}
