// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
  private final BallGun ballGun = new BallGun();
  private final FunnyMonkeyMachine funnyMonkeyMachine = new FunnyMonkeyMachine();

  private final AutoMoveShootTwo autoMoveShootTwo = new AutoMoveShootTwo(m_drivetrainSubsystem, ballGun);
  
  private final ZeroGyro zeroGyro = new ZeroGyro(m_drivetrainSubsystem);
  
  private final ShootBall shootBall = new ShootBall(ballGun, launchSpeed);
  private final ShootBallStop shootBallStop = new ShootBallStop(ballGun);
  private final SpinHigherConveyor spinHigherConveyor = new SpinHigherConveyor(ballGun, higherConveyorSpeed);
  private final SpinLowerConveyor spinLowerConveyor = new SpinLowerConveyor(ballGun, lowerConveyorSpeed);
  private final StopHigherConveyor stopHigherConveyor = new StopHigherConveyor(ballGun);
  private final StopLowerConveyor stopLowerConveyor = new StopLowerConveyor(ballGun);
  private final ShootTwoBalls shootTwoBalls = new ShootTwoBalls(ballGun, launchSpeed, higherConveyorSpeed, lowerConveyorSpeed);
  private final SendDistance sendDistance = new SendDistance(ballGun);

  private final ExtendArms extendArms = new ExtendArms(funnyMonkeyMachine);
  private final RetractArms retractArms = new RetractArms(funnyMonkeyMachine);
  private final CloseArms closeArms = new CloseArms(funnyMonkeyMachine);
  private final OpenArms openArms = new OpenArms(funnyMonkeyMachine);
  private final SendPneumatics sendPneumatics = new SendPneumatics(funnyMonkeyMachine);
  
  private final Joystick m_controller = new Joystick(m_controllerPortNum);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    CameraServer.startAutomaticCapture("Intake", 0);
    CameraServer.startAutomaticCapture("Launcher", 1);

    //Uncomment if using neos and want to throw their position to shuffleboard
    // Shuffleboard.getTab("Arms").addNumber("rigth arm pos", ()->funnyMonkeyMachine.getRightArmPosition());
    // Shuffleboard.getTab("Arms").addNumber("left arm pos", ()->funnyMonkeyMachine.getLeftArmPosition());


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
    SmartDashboard.putData(sendDistance);
    SmartDashboard.putData(sendPneumatics);
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
    JoystickButton m_5 = new JoystickButton(m_controller, 5);
    JoystickButton m_7 = new JoystickButton(m_controller, 7);
    JoystickButton m_8 = new JoystickButton(m_controller, 8);
    JoystickButton m_9 = new JoystickButton(m_controller, 9);
    JoystickButton m_10 = new JoystickButton(m_controller, 10);
    JoystickButton m_11 = new JoystickButton(m_controller, 11);

    m_5.whenPressed(shootTwoBalls);
    m_3.whenPressed(zeroGyro);
    m_4.whenPressed(spinLowerConveyor);
    m_6.whenPressed(spinHigherConveyor);
    m_1.whenPressed(shootBall);
    m_4.whenReleased(stopLowerConveyor);
    m_6.whenReleased(stopHigherConveyor);
    m_1.whenReleased(shootBallStop);
    m_7.whileHeld(extendArms);
    m_8.whileHeld(retractArms);
    m_9.whenPressed(openArms);
    m_10.whenPressed(closeArms);
    //Don't run this command without neos or encoders, shit will break.
    //m_11.whenPressed(new SequentialCommandGroup(new OpenArms(funnyMonkeyMachine), new ExtendArms(funnyMonkeyMachine), new CloseArms(funnyMonkeyMachine), new RetractArms(funnyMonkeyMachine)));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return autoMoveShootTwo;
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
