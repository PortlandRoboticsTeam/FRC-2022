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
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

import static frc.robot.Constants.*;

import java.util.Map;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final SwerveShaninigans m_drivetrainSubsystem = new SwerveShaninigans();
  private final BallGun ballGun = new BallGun();
  
  private final AutoDrive m_autoCommand = new AutoDrive(m_drivetrainSubsystem);
  private final ZeroGyro zeroGyro = new ZeroGyro(m_drivetrainSubsystem);
  private final ShootBall shootBall = new ShootBall(ballGun, launchSpeed);
  private final ShootBallStop shootBallStop = new ShootBallStop(ballGun);
  private final SpinHigherConveyor spinHigherConveyor = new SpinHigherConveyor(ballGun, higherConveyorSpeed);
  private final SpinLowerConveyor spinLowerConveyor = new SpinLowerConveyor(ballGun, lowerConveyorSpeed);
  private final StopHigherConveyor stopHigherConveyor = new StopHigherConveyor(ballGun);
  private final StopLowerConveyor stopLowerConveyor = new StopLowerConveyor(ballGun);
  private final GetDistance getDistance = new GetDistance(ballGun);
  private final ShootTwoBalls shootTwoBalls = new ShootTwoBalls(ballGun, launchSpeed, higherConveyorSpeed, lowerConveyorSpeed);
  
  private final Joystick m_controller = new Joystick(m_controllerPortNum);
  private double distance;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Set up the default command for the drivetrain.
    // The controls are for field-oriented driving:
    // Left stick Y axis -> forward and backwards movement
    // Left stick X axis -> left and right movement
    // Right stick X axis -> rotation
    m_drivetrainSubsystem.setDefaultCommand(new DefaultDriveCommand(
            m_drivetrainSubsystem,
            () -> -(smoothLogisticInput(m_controller.getX(), true) * m_controller.getThrottle() * SwerveShaninigans.MAX_VELOCITY_METERS_PER_SECOND * speedReductionConst),
            () -> (smoothLogisticInput(m_controller.getY(), true) * m_controller.getThrottle() * SwerveShaninigans.MAX_VELOCITY_METERS_PER_SECOND * speedReductionConst),
            () -> -(smoothLogisticInput(m_controller.getTwist(), false) * m_controller.getThrottle() * SwerveShaninigans.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND * speedReductionConst)
    ));
    
    // Configure the button bindings
    configureButtonBindings();
    distance = ballGun.getDistance();
    Shuffleboard.getTab("Display").addBoolean("In Firing Distance", () -> (distance>24 && distance<36));
    if(distance<24) Shuffleboard.getTab("Display").addNumber("Distance Needed to Move", ()-> distance-24).withWidget(BuiltInWidgets.kNumberBar).withProperties(Map.of("min", -24, "max", 24));
    else if(distance>36) Shuffleboard.getTab("Display").addNumber("Distance Needed to Move", ()-> distance-36).withWidget(BuiltInWidgets.kNumberBar).withProperties(Map.of("min", -24, "max", 24));
    else Shuffleboard.getTab("Display").addString("Distance Needed to Move", ()-> "No Object in Range!").withWidget(BuiltInWidgets.kTextView);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    JoystickButton m_3 = new JoystickButton(m_controller, 3);
    JoystickButton m_4 = new JoystickButton(m_controller, 4);
    JoystickButton m_6 = new JoystickButton(m_controller, 6);
    JoystickButton m_1 = new JoystickButton(m_controller, 1);
    JoystickButton m_11 = new JoystickButton(m_controller, 11);
    JoystickButton m_5 = new JoystickButton(m_controller, 5);

    m_5.whenPressed(shootTwoBalls);
    m_3.whenPressed(zeroGyro);
    m_4.whenPressed(spinLowerConveyor);
    m_6.whenPressed(spinHigherConveyor);
    m_1.whenPressed(shootBall);
    m_4.whenReleased(stopLowerConveyor);
    m_6.whenReleased(stopHigherConveyor);
    m_1.whenReleased(shootBallStop);
    m_11.whenPressed(getDistance);
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
