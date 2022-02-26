// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.BallGun;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class ShootTwoBalls extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final BallGun ballGun;
    private Timer timer;
    private double launchSpeed;
    private double bottomSpeed;
    private double topSpeed;
    boolean firstHasntRun;
    boolean secondHasntRun;
    boolean thirdHasntRun;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public ShootTwoBalls(BallGun ballGun, double launchSpeed, double bottomSpeed, double topSpeed) {
      this.ballGun = ballGun;
      this.topSpeed = topSpeed;
      this.bottomSpeed = bottomSpeed;
      this.launchSpeed = launchSpeed;
      timer = new Timer();
      // Use addRequirements() here to declare subsystem dependencies.
      addRequirements(ballGun);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        firstHasntRun = true;
        secondHasntRun = true;
        thirdHasntRun = true;
        timer.start();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        ballGun.spinBallLauncher(launchSpeed);
        if(timer.get()>2.0 && firstHasntRun){
            ballGun.spingHigherConveyor(topSpeed);
            firstHasntRun = false;
        }
        if(timer.get()>4.0 && secondHasntRun){
            ballGun.spinLowerConveyor(bottomSpeed);
            secondHasntRun = false;
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        System.out.println(timer.get());
        timer.stop();
        timer.reset();
        ballGun.stopMotors();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      if(timer.get()>5.0 && !secondHasntRun){
        return true;
      }
      else return false;
    }
}
