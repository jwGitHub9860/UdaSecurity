package com.udacity.catpoint;

import com.udacity.catpoint.data.AlarmStatus;
import com.udacity.catpoint.data.ArmingStatus;
import com.udacity.catpoint.data.PretendDatabaseSecurityRepositoryImpl;
import com.udacity.catpoint.data.Sensor;
import com.udacity.catpoint.service.SecurityService;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

// Attaches "Mockito" Extension to JUnit Test Runner
@ExtendWith(MockitoExtension.class)

/**
 * Unit test for simple App.
 */
public class SecurityServiceTest extends TestCase
{
    private AlarmStatus alarmStatus;
    private ArmingStatus armingStatus;

    // Allows Methods from "PretendDatabaseSecurityRepositoryImpl.java" to be Accessed by ALL Methods WITHIN "SecurityServiceTest" class
    final PretendDatabaseSecurityRepositoryImpl pretendDatabaseSecurityRepository = new PretendDatabaseSecurityRepositoryImpl();

    // Allows Methods from "SecurityService.java" to be Accessed by ALL Methods WITHIN "SecurityServiceTest" class
    final SecurityService securityService = new SecurityService(null, null); // CAN USE "null" as Constructor Input to Call Constructor

    // Allows Methods from "Sensor.java" to be Accessed by ALL Methods WITHIN "SecurityServiceTest" class
    final Sensor sensor = new Sensor(null, null); // CAN USE "null" as Constructor Input to Call Constructor

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SecurityServiceTest(String testName)
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SecurityServiceTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    // Tests if Alarm is Armed & a Sensor Becomes Activated, Puts System Into Pending Alarm Status if BOTH Conditions are Met
    public void IsAlarmArmedAndSensorActivated() { // "@Test" is NOT NEEDED Because "SecurityServiceTest" Already Extends To "TestCase"
        // Checks if Alarm is Armed
        if (!(securityService.getArmingStatus() == ArmingStatus.DISARMED)) {
            // Checks if a Sensor Becomes Activated
            if (sensor.getActive()) {
                // Puts System into Pending Alarm Status
                pretendDatabaseSecurityRepository.setAlarmStatus(AlarmStatus.PENDING_ALARM);
            }
        }
    }
}
