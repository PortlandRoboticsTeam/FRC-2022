// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import static frc.robot.Constants.*;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class BallGun extends SubsystemBase {
    VictorSPX lowerConveyorMotor = new VictorSPX(lowerConveyorMotorPortNum);
    VictorSPX higherConveyorMotor = new VictorSPX(higherConveyorMotorPortNum);
    VictorSPX ballLaunchingMotor = new VictorSPX(ballLaunchingMotorPortNum);
    AnalogInput distanceSensor = new AnalogInput(ultrasonicPortNum);

    /** Creates a new ExampleSubsystem. */   
    public BallGun(){}

    public double getDistance(){
        double distance = distanceSensor.getVoltage() * 10.0 / 1024.0;
        return distance;
    }
    public void spinLowerConveyor(double speed){
        lowerConveyorMotor.set(VictorSPXControlMode.PercentOutput, speed);
    }

    public void spingHigherConveyor(double speed){
        higherConveyorMotor.set(VictorSPXControlMode.PercentOutput, speed);
    }

    public void spinBallLauncher(double speed){
        ballLaunchingMotor.set(VictorSPXControlMode.PercentOutput, speed);
    }

    public void stopMotors(){
        ballLaunchingMotor.set(VictorSPXControlMode.PercentOutput,0);
        lowerConveyorMotor.set(VictorSPXControlMode.PercentOutput,0);
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
