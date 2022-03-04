// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import static frc.robot.Constants.*;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FunnyMonkeyMachine extends SubsystemBase {
  VictorSPX rightArmMotor = new VictorSPX(rightArmMotorPortNum);
  VictorSPX leftArmMotor = new VictorSPX(leftArmMotorPortNum);
  
  /** Creates a new ExampleSubsystem. */
  public FunnyMonkeyMachine() {}

  public void rotateArmMotors(double speed){
    rightArmMotor.set(VictorSPXControlMode.PercentOutput, speed);
    leftArmMotor.set(VictorSPXControlMode.PercentOutput, -speed);
  }

  public void stopMotors(){
    rightArmMotor.set(VictorSPXControlMode.PercentOutput, 0);
    leftArmMotor.set(VictorSPXControlMode.PercentOutput, 0);
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
