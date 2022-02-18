// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.XboxController;
import static frc.robot.Constants.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final SwerveShaninigans m_drivetrainSubsystem = new SwerveShaninigans();
  
  private final AutoDrive m_autoCommand = new AutoDrive(m_drivetrainSubsystem);
  
  private final Joystick m_controller = new Joystick(m_controllerPortNum);
  private final ZeroGyro zeroGyro = new ZeroGyro(m_drivetrainSubsystem);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Set up the default command for the drivetrain.
    // The controls are for field-oriented driving:
    // Left stick Y axis -> forward and backwards movement
    // Left stick X axis -> left and right movement
    // Right stick X axis -> rotation
    m_drivetrainSubsystem.setDefaultCommand(new DefaultDriveCommand(
            m_drivetrainSubsystem,
            () -> -(smoothLogisticInput(m_controller.getX(), true) * m_controller.getThrottle() * SwerveShaninigans.MAX_VELOCITY_METERS_PER_SECOND),
            () -> (smoothLogisticInput(m_controller.getY(), true) * m_controller.getThrottle() * SwerveShaninigans.MAX_VELOCITY_METERS_PER_SECOND),
            () -> -(smoothLogisticInput(m_controller.getTwist(), false) * m_controller.getThrottle() * SwerveShaninigans.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND)
    ));
    
    
    // Configure the button bindings
    configureButtonBindings();

  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    JoystickButton m_3 = new JoystickButton(m_controller, 3);

    m_3.whenPressed(zeroGyro);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }

  public double smoothLogisticInput(double input, Boolean drive) {
    if(drive){
      if (input > 0.1) {
          return 1/(1 + Math.exp(-10*Math.abs(input)+5));
      } else if (input < -0.1) {
          return -(1/(1 + Math.exp(-10*Math.abs(input)+5)));
      } 
      return 0;
    }
    else{
      if (input > 0.4) {
        return 1/(1 + Math.exp(-15*Math.abs(input)+10));
    } else if (input < -0.4) {
        return -(1/(1 + Math.exp(-15*Math.abs(input)+10)));
    } 
    return 0;
    }
  }
}
