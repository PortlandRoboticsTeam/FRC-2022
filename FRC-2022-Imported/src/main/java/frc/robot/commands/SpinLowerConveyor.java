// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.BallGun;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class SpinLowerConveyor extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final BallGun ballGun;
    private final double speed;

    public SpinLowerConveyor(BallGun  ballGun, double speed) {
        this.ballGun = ballGun;
        this.speed = speed;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(ballGun);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        ballGun.spinLowerConveyor(speed);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

     // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;
    }
}
