// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxLimitSwitch.Type;

import static frc.robot.Constants.*;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FunnyMonkeyMachine extends SubsystemBase {
  private CANSparkMax rightArmMotor = new CANSparkMax(rightArmMotorPortNum, MotorType.kBrushless);
  private CANSparkMax leftArmMotor = new CANSparkMax(leftArmMotorPortNum, MotorType.kBrushless);
  private Compressor compressor = new Compressor(pcmPortNum, PneumaticsModuleType.CTREPCM);
  private DoubleSolenoid doubleSolenoid = new DoubleSolenoid(pcmPortNum, PneumaticsModuleType.CTREPCM, 0, 1);
  
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

  public void solenoidOff(){
    doubleSolenoid.set(Value.kOff);
  }

  public void rotateArmMotors(double speed){
    rightArmMotor.set(speed);
    leftArmMotor.set(-speed);
  }

  public void stopMotors(){
    rightArmMotor.set(0);
    leftArmMotor.set(0);
  }

  public boolean getCompressorState(){
    return compressor.enabled();
  }
  
  public Value getSolenoidState(){
    return doubleSolenoid.get();
  }
  
  /**
   * 
   * @param motor 1 = left motor, 2 = right motor
   * @param direction 1 = forward limit switch, 2 = reverse limit switch
   * @return 
   */
  public SparkMaxLimitSwitch getLimitSwitch(int motor, int direction){
    if(motor == 1){
      if(direction == 1) return leftArmMotor.getForwardLimitSwitch(Type.kNormallyOpen);
      else if(direction == 2) return leftArmMotor.getReverseLimitSwitch(Type.kNormallyOpen);
    }
    else if(motor == 2){
      if(direction == 1) return rightArmMotor.getForwardLimitSwitch(Type.kNormallyOpen);
      else if(direction == 2) return rightArmMotor.getReverseLimitSwitch(Type.kNormallyOpen);
    }
    return null;
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