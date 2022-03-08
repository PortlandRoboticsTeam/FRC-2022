// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import static frc.robot.Constants.*;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FunnyMonkeyMachine extends SubsystemBase {
  private VictorSPX rightArmMotor = new VictorSPX(rightArmMotorPortNum);
  private VictorSPX leftArmMotor = new VictorSPX(leftArmMotorPortNum);
  private Compressor compressor = new Compressor(0, PneumaticsModuleType.CTREPCM);
  private DoubleSolenoid doubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);
  
  /** Creates a new ExampleSubsystem. */
  public FunnyMonkeyMachine() {
    compressor.enableDigital();
    doubleSolenoid.set(Value.kReverse);
  }

  public void solenoidFoward(){
    doubleSolenoid.set(Value.kForward);
  }

  public void solenoidReverse(){
    doubleSolenoid.set(Value.kReverse);
  }

  public void rotateArmMotors(double speed){
    rightArmMotor.set(VictorSPXControlMode.PercentOutput, speed);
    leftArmMotor.set(VictorSPXControlMode.PercentOutput, -speed);
  }

  public void stopMotors(){
    rightArmMotor.set(VictorSPXControlMode.PercentOutput, 0);
    leftArmMotor.set(VictorSPXControlMode.PercentOutput, 0);
  }

  public boolean getCompressorState(){
    return compressor.enabled();
  }
  
  public Value getSolenoidState(){
    return doubleSolenoid.get();
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
