package com.udacity.catpoint;

import com.udacity.catpoint.data.*;
import com.udacity.catpoint.service.FakeImageService;
import com.udacity.catpoint.service.SecurityService;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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

    // Declares Testing Variable Used to Construct "SecurityServiceTest"
    @Mock
    private SecurityRepository securityRepository; // "SecurityRepository" - Mock Class

    // Declares Testing Variable Used to Construct "SecurityServiceTest"
    @Mock
    private FakeImageService imageService; // "FakeImageService" - Mock Class

    // Declares Testing Variable Used to Construct "SecurityServiceTest"
    @Mock
    private String name;

    // Declares Testing Variable Used to Construct "SecurityServiceTest"
    @Mock
    private SensorType sensorType; // "SensorType" - Mock Class

    // Allows Methods from "PretendDatabaseSecurityRepositoryImpl.java" to be Accessed by ALL Methods WITHIN "SecurityServiceTest" class
    final PretendDatabaseSecurityRepositoryImpl pretendDatabaseSecurityRepository = new PretendDatabaseSecurityRepositoryImpl();

    // Allows Methods from "SecurityService.java" to be Accessed by ALL Methods WITHIN "SecurityServiceTest" class
    final SecurityService securityService = new SecurityService(securityRepository, imageService);

    // Allows Methods from "Sensor.java" to be Accessed by ALL Methods WITHIN "SecurityServiceTest" class
    final Sensor sensor = new Sensor(name, sensorType);

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
    public void isAlarmArmedAndSensorActivated() { // "@Test" is NOT NEEDED Because "SecurityServiceTest" Already Extends To "TestCase"
        // Checks if Alarm is Armed
        if (!(securityService.getArmingStatus() == ArmingStatus.DISARMED)) {
            // Checks if a Sensor Becomes Activated
            if (sensor.getActive()) {
                // Puts System into Pending Alarm Status
                pretendDatabaseSecurityRepository.setAlarmStatus(AlarmStatus.PENDING_ALARM);
            }
        }
    }

    /**
     * Test Case that Tests if Pending Alarm & All Sensors are Inactive
     *
     *
     * @return - Returns Alarm Status in No Alarm State if ALL Conditions are Met
     */
    public AlarmStatus isPendingAlarmAndAllSensorsInactive() {
        // Checks if Alarm Status is Pending
        if (securityService.getAlarmStatus() == AlarmStatus.PENDING_ALARM) {
            // Checks if Pending Alarm is Inactive OR Disarmed
            if (securityService.getArmingStatus() == ArmingStatus.DISARMED) {
                // Returns "AlarmStatus" in No Alarm State
                return AlarmStatus.NO_ALARM;
            }
        }
        // Returns "null" if Alarm is NOT Pending & All Sensors are NOT Inactive
        return null;
    }
}
