// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import static frc.robot.Constants.*;

import edu.wpi.first.wpilibj.AnalogInput;

public class BallGun extends SubsystemBase {
    CANSparkMax lowerConveyorMotor = new CANSparkMax(lowerConveyorMotorPortNum, MotorType.kBrushless);
    VictorSPX higherConveyorMotor = new VictorSPX(higherConveyorMotorPortNum);
    CANSparkMax ballLaunchingMotor = new CANSparkMax(ballLaunchingMotorPortNum, MotorType.kBrushless);
    AnalogInput distanceSensor = new AnalogInput(ultrasonicPortNum);

    /** Creates a new ExampleSubsystem. */   
    public BallGun(){
        lowerConveyorMotor.setSecondaryCurrentLimit(100*.8);
        ballLaunchingMotor.setSecondaryCurrentLimit(100*.8);
    }

    public double getDistance(){
        double distance = 39.0 * distanceSensor.getVoltage()/1.024;
        return distance;
    }
    public void spinLowerConveyor(double speed){
        lowerConveyorMotor.set(-speed);
    }

    public void spingHigherConveyor(double speed){
        higherConveyorMotor.set(VictorSPXControlMode.PercentOutput, speed);
    }

    public void spinBallLauncher(double speed){
        ballLaunchingMotor.set(speed);
    }

    public void stopMotors(){
        ballLaunchingMotor.set(0);
        lowerConveyorMotor.set(0);
        higherConveyorMotor.set(VictorSPXControlMode.PercentOutput,0);
    }

    @Override
    public void periodic() {
    // This method will be called once per scheduler run
    }

    @Override
    public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    }
}
