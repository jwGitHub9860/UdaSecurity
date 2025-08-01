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
    public SecurityServiceTest(String testName )
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
}
