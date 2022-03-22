// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.FunnyMonkeyMachine;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.robot.Constants.*;

/** An example command that uses an example subsystem. */
public class RetractArms extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final FunnyMonkeyMachine funnyMonkeyMachine;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public RetractArms(FunnyMonkeyMachine funnyMonkeyMachine) {
      this.funnyMonkeyMachine = funnyMonkeyMachine;
      // Use addRequirements() here to declare subsystem dependencies.
      addRequirements(funnyMonkeyMachine);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        funnyMonkeyMachine.rotateArmMotors(-retractSpeed);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {}

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        funnyMonkeyMachine.stopMotors();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return false;
    }
}