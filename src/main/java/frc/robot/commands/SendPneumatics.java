// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.FunnyMonkeyMachine;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class SendPneumatics extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final FunnyMonkeyMachine armSystem;
    double distance;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public SendPneumatics(FunnyMonkeyMachine armSystem) {
      this.armSystem = armSystem;
      // Use addRequirements() here to declare subsystem dependencies.
      addRequirements(armSystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize(){}

    @Override
    public void initSendable(SendableBuilder builder){
      builder.setSmartDashboardType("Pneumatic Display");
      builder.addStringProperty("Current output of the solenoid", this::getSolenoidData, null);
      builder.addBooleanProperty("Whether the compressor is running...", this::getCompressorData, null);
    }

    private boolean getCompressorData(){
      return armSystem.getCompressorState();
    }

    private String getSolenoidData() {
      if(armSystem.getSolenoidState()==Value.kForward){
          return "Forward";
      }
      else if(armSystem.getSolenoidState()==Value.kReverse){
        return "Reverse";
    }
    else if(armSystem.getSolenoidState()==Value.kOff){
        return "Off";
    }
    else return "Broke";
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {}

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return true;
    }
}
