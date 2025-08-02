package com.udacity.catpoint;

import com.udacity.catpoint.data.AlarmStatus;
import com.udacity.catpoint.data.ArmingStatus;
import com.udacity.catpoint.data.Sensor;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class SecurityServiceTest extends TestCase
{
    private AlarmStatus alarmStatus;
    private ArmingStatus armingStatus;

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
    public void IsAlarmArmedAndSensorActivated() {} // "@Test" is NOT NEEDED Because "SecurityServiceTest" Already Extends To "TestCase"
}
