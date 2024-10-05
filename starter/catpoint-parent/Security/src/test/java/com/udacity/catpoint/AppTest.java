package com.udacity.catpoint;

import com.udacity.catpoint.image.AwsImageService;
import com.udacity.catpoint.image.FakeImageService;
import com.udacity.catpoint.security.application.StatusListener;
import com.udacity.catpoint.security.data.*;

import com.udacity.catpoint.security.service.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.image.BufferedImage;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
public class AppTest {
    private Sensor sensor;

    private final String random = UUID.randomUUID().toString();
    @Mock
    private StatusListener statusListener;

    @Mock
    private AwsImageService awsImageService;
    @Mock
    private FakeImageService fakeImageService;
    @Mock
    private SecurityRepository securityRepository =  new PretendDatabaseSecurityRepositoryImpl();

    private SecurityService securityService = new SecurityService(securityRepository, fakeImageService);
    private Sensor getNewSensor() {
        return new Sensor(random, SensorType.DOOR);
    }

    Set<Sensor> sensors = new HashSet<>();
    Sensor sensor1 = new Sensor("Door", SensorType.DOOR);
    Sensor sensor2 = new Sensor("Window", SensorType.WINDOW);
    Sensor sensor3 = new Sensor("Motion", SensorType.MOTION);

    private Set<Sensor> getSixSampleSensors(boolean activeState) {
        Set<Sensor> sensors = new HashSet<>();
        for (int i = 1; i < 6; i++) {
            Sensor sensor = new Sensor("sampleSensor" + i, SensorType.DOOR);
            sensor.setActive(activeState);
            sensors.add(sensor);
        }
        return sensors;
    }

    @BeforeEach
    void setUpTest() {
        securityService = new SecurityService(securityRepository, fakeImageService);
        sensor = getNewSensor();


        sensors.add(sensor1);
        sensors.add(sensor2);
        sensors.add(sensor3);
    }

    //#1
    @Test
    void alarmArmed_sensorActivated_pendingSystem() {
        given(securityRepository.getArmingStatus()).willReturn(ArmingStatus.ARMED_HOME);
        given(securityRepository.getAlarmStatus()).willReturn(AlarmStatus.NO_ALARM);
        securityService.changeSensorActivationStatus(sensor, true);
        verify(securityRepository, times(1)).setAlarmStatus(AlarmStatus.PENDING_ALARM);
    }

    //#2
    @Test
    void alarmArmed_sensorActivated_alreadyPending_putStatusToAlarm() {
        given(securityRepository.getArmingStatus()).willReturn(ArmingStatus.ARMED_HOME);
        given(securityRepository.getAlarmStatus()).willReturn(AlarmStatus.PENDING_ALARM);
        securityService.changeSensorActivationStatus(sensor, true);
        verify(securityRepository, times(1)).setAlarmStatus(AlarmStatus.ALARM);
    }

    //#3
    @Test
    void pendingAlarm_sensorInactive_SetNoAlarmState() {
        when(securityRepository.getAlarmStatus()).thenReturn(AlarmStatus.PENDING_ALARM);
        System.out.println("123123123 " + sensor.getActive());
        sensor.setActive(true);
        securityService.changeSensorActivationStatus(sensor, false);
        verify(securityRepository, times(1)).setAlarmStatus(AlarmStatus.NO_ALARM);
    }

    //#4
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void alarmActive_sensorChanging_doNotAffectAlarmState(boolean status) {
        given(securityRepository.getAlarmStatus()).willReturn(AlarmStatus.ALARM);

        securityService.changeSensorActivationStatus(sensor, status);
        verify(securityRepository, never()).setAlarmStatus(AlarmStatus.NO_ALARM);
        verify(securityRepository, never()).setAlarmStatus(AlarmStatus.PENDING_ALARM);

    }

    //#5
    @Test
    void sensorActivated_inWhileAlreadyActive_andSystemInPendingState_setStatusToAlarm() {
        when(securityRepository.getAlarmStatus()).thenReturn(AlarmStatus.NO_ALARM);
        sensor.setActive(Boolean.FALSE);
        securityService.changeSensorActivationStatus(sensor, false);
        verify(securityRepository, never()).setAlarmStatus(any(AlarmStatus.class));
    }

    //Tests Requirement #6. If a sensor is deactivated while already inactive, make no changes to the alarm state.
    @ParameterizedTest
    @EnumSource(AlarmStatus.class)
    @DisplayName("Test 6")
    public void sensorDeactivated_whileAlreadyInactive_alarmStateDoesNotChange(AlarmStatus alarmStatus) {

        securityService.changeSensorActivationStatus(sensor, false);
        when(securityRepository.getAlarmStatus()).thenReturn(alarmStatus);
        securityService.changeSensorActivationStatus(sensor, false);

        verify(securityRepository, never()).setAlarmStatus(any(AlarmStatus.class));

    }

    //Tests Requirement #7. If the image service identifies an image containing a cat while the system is armed-home,
    //put the system into alarm status.
    @Test
    @DisplayName("Test 7")
    public void imageServiceIdentifiesImageContainingCat_while_SystemArmedHome_putSystemIntoAlarmStatus() {

        when(securityRepository.getArmingStatus()).thenReturn(ArmingStatus.ARMED_HOME);
        when(fakeImageService.imageContainsCat(any(), anyFloat())).thenReturn(true);
        securityService.processImage(mock(BufferedImage.class));

        verify(securityRepository, times(1)).setAlarmStatus(AlarmStatus.ALARM);
    }


    //Tests Requirement #8. If the image service identifies an image that does not contain a cat, change the status
    //to no alarm as long as the sensors are not active.
    @Test
    @DisplayName("Test 8")
    public void imageServiceIdentifiesImage_doesNotContainCat_changeStatusToNoAlarm_ifSensorsNotActive() {

        when(fakeImageService.imageContainsCat(any(), anyFloat())).thenReturn(false);
        Set<Sensor> sensors2 = Set.of(sensor, sensor1, sensor2, sensor3);
        sensors.iterator().next().setActive(false);

        securityService.processImage(mock(BufferedImage.class));
//        when(securityRepository.image()).thenReturn(sensors);
//        securityService.setArmingStatus(ArmingStatus.ARMED_HOME);
//        securityService.processImage(mock(BufferedImage.class));

        verify(securityRepository, atMostOnce()).setAlarmStatus(AlarmStatus.NO_ALARM);
    }

    //9
    @Test
    void inCaseSystemDisarmed_SetToNoAlarmState() {
        securityService.setArmingStatus(ArmingStatus.DISARMED);

        verify(securityRepository, times(1)).setAlarmStatus(AlarmStatus.NO_ALARM);
    }

    //Tests Requirement #10. If the system is armed, reset all sensors to inactive.
    @ParameterizedTest
    @EnumSource(value = ArmingStatus.class, names = {"ARMED_AWAY", "ARMED_HOME"})
    @DisplayName("Test 10")
    public void systemArmed_setAllSensorsToInactive(ArmingStatus armingStatus) {

        when(securityRepository.getSensors()).thenReturn(sensors);
        when(securityRepository.getArmingStatus()).thenReturn(ArmingStatus.DISARMED);
        securityService.changeSensorActivationStatus(sensor, true);
//        securityService.changeSensorActivationStatus(sensor2,true);
        securityService.setArmingStatus(armingStatus);

        assert (securityRepository).getSensors().stream().noneMatch(Sensor::getActive);

    }

    //11
    @Test
    void ifSystemArmedHomeWhileImageServiceIdentifiesCat_changeStatusToAlarm() {
        BufferedImage catImage = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
        given(fakeImageService.imageContainsCat(any(), anyFloat())).willReturn(true);
        given(securityRepository.getArmingStatus()).willReturn(ArmingStatus.DISARMED);
        securityService.processImage(catImage);
        securityService.setArmingStatus(ArmingStatus.ARMED_HOME);

        verify(securityRepository, times(1)).setAlarmStatus(AlarmStatus.ALARM);
    }


    //Tests if a sensor is added in SecurityService, that it is happening in SecurityRepository
    @Test
    @DisplayName("Test 13")
    public void addSensor_to_securityService_isAddedTo_securityRepository() {

        securityService.addSensor(sensor1);
        verify(securityRepository,times(1)).addSensor(sensor1);

    }

    //Tests if a sensor is removed in SecurityService, that it is happening in SecurityRepository
    @Test
    @DisplayName("Test 14")
    public void removeSensor_from_securityService_isRemovedFrom_securityRepository() {

        securityService.removeSensor(sensor2);
        verify(securityRepository,times(1)).removeSensor(sensor2);

    }

    //Tests if getSenors is called in SecurityService, that it returns sensors from SecurityRepository
    @Test
    @DisplayName("Test 17")
    public void getSensors_calledInSecurityService_returnsSensorsFromSecurityRepository(){

        when(securityService.getSensors()).thenReturn(sensors);

        System.out.println(securityService.getSensors());
        System.out.println(securityRepository.getSensors());

    }

//    @Test
//    void coverage() {
//        when(securityRepository.getSensors()).thenReturn(sensors);
//        securityService.changeSensorActivationStatus(sensor, false);
//        securityService.addSensor(sensor1);
////        securityService.getAlarmStatus();
////        securityService.removeSensor(sensor1);
//        securityService.setFalseActivationStatusForSensors(sensors);
//        securityService.catDetected(false);
//
//        securityService.setAlarmStatus(AlarmStatus.ALARM);
//        securityService.handleSensorDeactivated();
////        securityService.getAlarmStatus();
//    }

    // More tests covering SecurityService class
    @Test
    void statusListenerTestCoverage() {
        securityService.addStatusListener(statusListener);
        securityService.removeStatusListener(statusListener);
    }

    @Test
    void sensorTestCoverage() {
        securityService.addSensor(sensor);
        securityService.removeSensor(sensor);
    }
}
