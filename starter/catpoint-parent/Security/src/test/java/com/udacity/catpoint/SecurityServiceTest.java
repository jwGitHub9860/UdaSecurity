package com.udacity.catpoint;

import com.udacity.catpoint.data.*;
import com.udacity.catpoint.service.FakeImageService;
import com.udacity.catpoint.service.SecurityService;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
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

    /**
     * Tests if Alarm is Armed & a Sensor Becomes Activated
     * Puts System Into Pending Alarm Status, if BOTH Conditions are Met
     */
    public void testIsAlarmArmedAndSensorActivated() { // "@Test" is NOT NEEDED Because "SecurityServiceTest" Already Extends To "TestCase"
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
     * Test Case that Tests if Alarm is Armed & A Sensor Becomes Activated & System is already Pending Alarm
     * Sets Alarm Status to Alarm, if ALL Conditions are Met
     */
    public void testIsAlarmArmedAndSensorActivatedAndSystemPendingAlarm() {
        // Checks if Alarm is Armed
        if (!(securityService.getArmingStatus() == ArmingStatus.DISARMED)) {
            // Checks if a Sensor Becomes Activated
            if (sensor.getActive()) {
                // Checks if System is already Pending Alarm
                if (securityService.getAlarmStatus() == AlarmStatus.PENDING_ALARM) {
                    // Sets Alarm Status to Alarm
                    pretendDatabaseSecurityRepository.setAlarmStatus(AlarmStatus.ALARM);
                }
            }
        }
    }

    /**
     * Test Case that Tests if Pending Alarm & All Sensors are Inactive
     * Sets Alarm Status to No Alarm State if ALL Conditions are Met
     */
    public void testIsPendingAlarmAndAllSensorsInactive() {
        // Checks if Alarm Status is Pending
        if (securityService.getAlarmStatus() == AlarmStatus.PENDING_ALARM) {
            // Checks if Pending Alarm is Inactive OR Disarmed
            if (securityService.getArmingStatus() == ArmingStatus.DISARMED) {
                // Sets "AlarmStatus" to No Alarm State
                securityService.setAlarmStatus(AlarmStatus.NO_ALARM);
            }
        }
    }

    /**
     * Test Case that Tests if Alarm is Active, Sensor State will Change but NOT Alarm State (Alarm STAYS Active)
     */
    public void testIsAlarmStillActiveWhileSensorStateChanges() {
        // Checks if Alarm Status is Active
        if (securityService.getAlarmStatus() == AlarmStatus.ALARM) {
            // Iterates Through "SensorType" Enum
            for (SensorType sensorType : SensorType.values()) {
                // Sets Sensor Type to "sensorType"
                sensor.setSensorType(sensorType);
                // Checks if Alarm Status is STILL Active
                if (!(securityService.getAlarmStatus() == AlarmStatus.ALARM)) {
                    throw new IllegalArgumentException("Alarm Status is NOT Active");
                }
            }
        }
    }

    /**
     * Test Case that Tests if A Sensor is Activated while Already Active & System is in Pending State
     * Change Alarm Status to Alarm State (5. TEST CASE)
     */
    public void testIsSensorActiveWhileAlreadyActiveAndSystemInPendingState() {}

    /**
     * Test Case that Tests if System is Disarmed
     * Set Alarm Status to No Alarm if Condition is Met
     */
    public void testIsSystemDisarmed() {
        // Calls "setArmingStatus" Method to Test if Alarm Status will be Set to No Alarm IF System is Disarmed
        securityService.setArmingStatus(ArmingStatus.DISARMED);
    }

    /**
     * Test Case that Tests if System is Armed
     * Reset ALL Sensors to Inactive if Condition is Met
     */
    public void testIsSystemArmed() {
        // Checks if System is Armed
        if (!(securityService.getArmingStatus() == ArmingStatus.DISARMED)) {
            // Iterates Through Set of Sensors
            for (Set<Sensor> sensorSet : securityService.getSensors()) {
        }
    }
}
