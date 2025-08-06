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

import javax.imageio.ImageIO; // defines "ImageIO"
import javax.swing.*; // defines "JFileChooser"
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

// Attaches "Mockito" Extension to JUnit Test Runner
@ExtendWith(MockitoExtension.class)

/**
 * Unit test for simple App.
 */
public class SecurityServiceTest extends TestCase
{
    // Declares Testing Variable Used to Construct "SecurityServiceTest"
    @Mock
    private String name;

    // Declares Testing Variable Used to Construct "SecurityServiceTest"
    @Mock
    private SensorType sensorType; // "SensorType" - Mock Class

    // Reads Image or Image File
    @Mock
    private BufferedImage image = null;

    // Allows Methods from "PretendDatabaseSecurityRepositoryImpl.java" to be Accessed by ALL Methods WITHIN "SecurityServiceTest" class
    final PretendDatabaseSecurityRepositoryImpl pretendDatabaseSecurityRepository = new PretendDatabaseSecurityRepositoryImpl();

    // Allows Methods from "Sensor.java" to be Accessed by ALL Methods WITHIN "SecurityServiceTest" class
    final Sensor sensor = new Sensor(name, sensorType);

    // Allows Methods from "FakeImageService.java" to be Accessed by ALL Methods WITHIN "SecurityServiceTest" class
    final FakeImageService fakeImageService = new FakeImageService();

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
        // Iterates Through Set of Sensors
        for (Sensor singleSensor : pretendDatabaseSecurityRepository.getSensors()) {
            // Checks if Alarm is Armed for Each Sensor
            if (!(pretendDatabaseSecurityRepository.getArmingStatus() == ArmingStatus.DISARMED)) { // MUST BE INSIDE "for loop"
                // Checks if a Sensor Becomes Activated
                if (singleSensor.getActive()) {
                    // Calls "setAlarmStatus(AlarmStatus alarmStatus)" Method to Put System into Pending Alarm Status
                    pretendDatabaseSecurityRepository.setAlarmStatus(AlarmStatus.PENDING_ALARM); // MUST USE "setAlarmStatus(AlarmStatus alarmStatus)" Method From "PretendDatabaseSecurityRepositoryImpl.java" TO BE ABLE TO RUN TEST CASE
                }
            }
        }
    }

    /**
     * Test Case that Tests if Alarm is Armed & A Sensor Becomes Activated & System is already Pending Alarm
     * Sets Alarm Status to Alarm, if ALL Conditions are Met
     */
    public void testIsAlarmArmedAndSensorActivatedAndSystemPendingAlarm() {
        // Checks if Alarm is Armed
        if (!(pretendDatabaseSecurityRepository.getArmingStatus() == ArmingStatus.DISARMED)) {
            // Checks if a Sensor Becomes Activated
            if (sensor.getActive()) {
                // Checks if System is already Pending Alarm
                if (pretendDatabaseSecurityRepository.getAlarmStatus() == AlarmStatus.PENDING_ALARM) {
                    // Calls "setAlarmStatus(AlarmStatus alarmStatus)" Method to Set Alarm Status to Alarm
                    pretendDatabaseSecurityRepository.setAlarmStatus(AlarmStatus.ALARM); // MUST USE "setAlarmStatus(AlarmStatus alarmStatus)" Method From "PretendDatabaseSecurityRepositoryImpl.java" TO BE ABLE TO RUN TEST CASE
                }
            }
        }
    }

    /**
     * Test Case that Tests if Pending Alarm & All Sensors are Inactive
     * Sets Alarm Status to No Alarm State, if ALL Conditions are Met
     */
    public void testIsPendingAlarmAndAllSensorsInactive() {
        // Checks if Alarm Status is Pending
        if (pretendDatabaseSecurityRepository.getAlarmStatus() == AlarmStatus.PENDING_ALARM) {
            // Checks if Pending Alarm is Inactive OR Disarmed
            if (pretendDatabaseSecurityRepository.getArmingStatus() == ArmingStatus.DISARMED) {
                // Calls "setAlarmStatus(AlarmStatus alarmStatus)" Method to Set "AlarmStatus" to No Alarm State
                pretendDatabaseSecurityRepository.setAlarmStatus(AlarmStatus.NO_ALARM); // MUST USE "setAlarmStatus(AlarmStatus alarmStatus)" Method From "PretendDatabaseSecurityRepositoryImpl.java" TO BE ABLE TO RUN TEST CASE
            }
        }
    }

    /**
     * Test Case that Tests if Alarm is Active, Sensor State will Change but NOT Alarm State (Alarm STAYS Active)
     */
    public void testIsAlarmStillActiveWhileSensorStateChanges() {
        // Checks if Alarm Status is Active
        if (pretendDatabaseSecurityRepository.getAlarmStatus() == AlarmStatus.ALARM) {
            // Iterates Through "SensorType" Enum
            for (SensorType sensorType : SensorType.values()) {
                // Sets Sensor Type to "sensorType"
                sensor.setSensorType(sensorType);
                // Checks if Alarm Status is STILL Active
                if (!(pretendDatabaseSecurityRepository.getAlarmStatus() == AlarmStatus.ALARM)) {
                    throw new IllegalArgumentException("Alarm Status is NOT Active");
                }
            }
        }
    }

    /**
     * Test Case that Tests if A Sensor is Activated while Already Active & System is in Pending State
     * Change Alarm Status to Alarm State
     */
    public void testIsSensorActiveWhileAlreadyActiveAndSystemInPendingState() {
        // Creates "Set" to Hold Sensors that are Already Active
        Set<Sensor> activeSensors = new HashSet<>();

        // Iterates Through Set of Sensors
        for (Sensor singleSensor : pretendDatabaseSecurityRepository.getSensors()) {
            // Checks if Sensor is Activated
            if (sensor.getActive()) {
                // Adds "singleSensor" to "activeSensors"
                activeSensors.add(singleSensor);
            }
        }

        // Iterates Through "activeSensors"
        for (Sensor singleSensor : activeSensors) {
            // Checks if Sensor is Activated WHILE Already Active
            if (sensor.getActive()) {
                // Checks if System is in Pending State
                if (pretendDatabaseSecurityRepository.getAlarmStatus() == AlarmStatus.PENDING_ALARM) {
                    // Calls "setAlarmStatus(AlarmStatus alarmStatus)" Method to Change Alarm Status to Alarm State
                    pretendDatabaseSecurityRepository.setAlarmStatus(AlarmStatus.ALARM);
                }
            }
        }
    }

    /**
     * Test Case that Tests if A Sensor is Deactivated WHILE Already Inactive
     * Make NO CHANGES to Alarm State, if BOTH Conditions are Met
     */
    public void testIsSensorActiveWhileAlreadyInactive() {
        // Calls "getAlarmStatus()" Method to Obtain Initial Alarm State
        AlarmStatus initialAlarmState = pretendDatabaseSecurityRepository.getAlarmStatus(); // MUST USE "getAlarmStatus()" Method From "PretendDatabaseSecurityRepositoryImpl.java" TO BE ABLE TO RUN TEST CASE

        // Calls "setAlarmStatus(AlarmStatus alarmStatus)" Method to Set Initial Alarm State
        pretendDatabaseSecurityRepository.setAlarmStatus(initialAlarmState); // MUST USE "setAlarmStatus(AlarmStatus alarmStatus)" Method From "PretendDatabaseSecurityRepositoryImpl.java" TO BE ABLE TO RUN TEST CASE

        // Creates "Set" to Hold Sensors that are Already Inactive
        Set<Sensor> inactiveSensors = new HashSet<>();

        // Iterates Through Set of Sensors
        for (Sensor singleSensor : pretendDatabaseSecurityRepository.getSensors()) {
            // Checks if Sensor is Already Inactive
            if (!sensor.getActive()) {
                // Adds "sensor" to "inactiveSensors"
                inactiveSensors.add(sensor);
            }
        }

        // Iterates Through "inactiveSensors"
        for (Sensor singleSensor : inactiveSensors) {
            // Checks if Sensor is Deactivated WHILE Already Inactive
            if (!sensor.getActive()) {
                // Calls "setAlarmStatus(AlarmStatus alarmStatus)" Method to Ensure NO CHANGES are Made to Alarm State
                pretendDatabaseSecurityRepository.setAlarmStatus(initialAlarmState); // MUST USE "setAlarmStatus(AlarmStatus alarmStatus)" Method From "PretendDatabaseSecurityRepositoryImpl.java" TO BE ABLE TO RUN TEST CASE
            }
        }
    }

    /**
     * Test Case that Tests if Image Contains Cat WHILE System is Armed-Home
     * Put System into Alarm Status, if BOTH Conditions are Met
     */
    public void testDoesImageContainCatWhileSystemArmedHome() {
        // Calls "imageContainsCat(BufferedImage image, float confidenceThreshhold)" Method to Test if Image Contains Cat
        if (fakeImageService.imageContainsCat(image, 25.0f)) {
            // Checks if System is Armed-Home
            if (pretendDatabaseSecurityRepository.getArmingStatus() == ArmingStatus.ARMED_HOME) {
                // Calls "setAlarmStatus(AlarmStatus alarmStatus)" Method to Put System into Alarm Status
                pretendDatabaseSecurityRepository.setAlarmStatus(AlarmStatus.ALARM);
            }
        }
    }

    /**
     * Test Case that Tests if Image does NOT Contain Cat
     * Change Status to No Alarm AS LONG AS Sensors are NOT Active
     */
    public void testDoesImageNotContainCatAndSensorsNotActive() {
        // Determines if ALL Sensors are NOT Active
        boolean allSensorsNotActive = false;

        // Tests if Image does NOT Contain Cat
        if (fakeImageService.imageContainsCat(image, 35.0f)) {
            // Iterates Through Set of Sensors
            for (Sensor singleSensor : pretendDatabaseSecurityRepository.getSensors()) {
                // Checks if "singleSensor" is Active
                if (sensor.getActive()) {
                    allSensorsNotActive = false;

                    // Breaks out of "for loop" because ALL Sensors Must Be NOT Active
                    break;
                }
                else {
                    allSensorsNotActive = true;
                }
            }

            // Checks if ALL Sensors are NOT Active
            if (allSensorsNotActive) {
                // Calls "setAlarmStatus(AlarmStatus alarmStatus)" Method to Change Status to No Alarm
                pretendDatabaseSecurityRepository.setAlarmStatus(AlarmStatus.NO_ALARM);
            }
        }
    }

    /**
     * Test Case that Tests if System is Disarmed
     * Set Alarm Status to No Alarm, if Condition is Met
     */
    public void testIsSystemDisarmed() {
        // Calls "setArmingStatus(ArmingStatus armingStatus)" Method to Test if Alarm Status will be Set to No Alarm IF System is Disarmed
        pretendDatabaseSecurityRepository.setArmingStatus(ArmingStatus.DISARMED); // MUST USE "setArmingStatus(ArmingStatus armingStatus)" Method From "PretendDatabaseSecurityRepositoryImpl.java" TO BE ABLE TO RUN TEST CASE
    }

    /**
     * Test Case that Tests if System is Armed
     * Reset ALL Sensors to Inactive, if Condition is Met
     */
    public void testIsSystemArmed() {
        // Checks if System is Armed
        if (!(pretendDatabaseSecurityRepository.getArmingStatus() == ArmingStatus.DISARMED)) {
            // Iterates Through Set of Sensors
            for (Sensor singleSensor : pretendDatabaseSecurityRepository.getSensors()) {
                // Calls "setActive(Boolean active)" Method to Reset ALL Sensors to Inactive if Condition is Met
                sensor.setActive(false);
            }
        }
    }

    /**
     * Test Case that Tests if System is Armed-Home WHILE Camera Shows Cat
     * Set Alarm Status to Alarm, if Conditions are Met
     */
    public void testIsSystemArmedHomeWhileCameraShowsCat() {
        // Calls "imageContainsCat(BufferedImage image, float confidenceThreshhold)" Method to Test if Image Contains Cat
        if (fakeImageService.imageContainsCat(image, 15.0f)) {
            // Tests if System is Armed-Home
            if (pretendDatabaseSecurityRepository.getArmingStatus() == ArmingStatus.ARMED_HOME) {
                // Calls "setAlarmStatus(AlarmStatus alarmStatus)" Method to Set Alarm Status to Alarm
                pretendDatabaseSecurityRepository.setAlarmStatus(AlarmStatus.ALARM);
            }
        }
    }
}
