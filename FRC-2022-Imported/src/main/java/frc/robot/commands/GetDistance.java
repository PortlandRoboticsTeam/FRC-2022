// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.BallGun;

import java.util.function.DoubleSupplier;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class GetDistance extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final BallGun ballGun;
    double distance;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public GetDistance(BallGun ballGun) {
      this.ballGun = ballGun;
      // Use addRequirements() here to declare subsystem dependencies.
      addRequirements(ballGun);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize(){
      this.distance = ballGun.getDistance();
    }

    @Override
    public void initSendable(SendableBuilder builder){
      builder.setSmartDashboardType("Distance Display");
      builder.addBooleanProperty("In Firing Distance", this::getIfInRange, null);
      builder.addDoubleProperty("Distance needed to move", this::getDistanceDifference, null);
      builder.addDoubleProperty("Read Distance", getReadDistance(), null);
    }

    private DoubleSupplier getReadDistance(){
      return ()-> ballGun.getDistance();
    }

    private boolean getIfInRange() {
      distance = ballGun.getDistance();
      return distance>=24 && distance<=36;
    }

    private double getDistanceDifference(){
      distance = ballGun.getDistance();
      if(distance<24) return distance-24;
      else if(distance>36) return distance-36;
      else if(distance<=36 && distance>=24) return 0;
      else return 0;
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
