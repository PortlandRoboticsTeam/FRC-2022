package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.BallGun;
import frc.robot.subsystems.SwerveShaninigans;
import static frc.robot.Constants.*;

public class AutoMoveShootTwo extends CommandBase{
    private final SwerveShaninigans swerve;
    private final BallGun gun;
    private final Timer timer = new Timer();
    private final ShootTwoBalls shootTwo;
    private double distance;
    private boolean hasShot = false;
    
    public AutoMoveShootTwo(SwerveShaninigans swerve, BallGun gun){
        this.swerve = swerve;
        this.gun = gun;
        addRequirements(swerve, gun);
        shootTwo = new ShootTwoBalls(this.gun, launchSpeed, lowerConveyorSpeed, higherConveyorSpeed);
    }

    @Override
    public void initialize(){
        timer.reset();
    }

    @Override
    public void execute(){
        distance = gun.getDistance();
        if(timer.get()>6){
            swerve.setDefaultCommand(new DefaultDriveCommand(swerve, ()-> 0, ()-> -0.75, ()-> 0));
        }
        else if(distance<minShootDistance){
            swerve.setDefaultCommand(new DefaultDriveCommand(swerve, ()-> 0, ()-> -0.75, ()-> 0));
        }
        else if(distance>maxShootDistance){
            swerve.setDefaultCommand(new DefaultDriveCommand(swerve, ()-> 0, ()-> 0.5, ()-> 0));
        }
        else if(distance>=minShootDistance && distance<=maxShootDistance && !hasShot){
            shootTwo.schedule();
            timer.reset();
            timer.start();
            hasShot = true;
        }
    }

    @Override
    public boolean isFinished(){
        if(hasShot && timer.get()>7) return true;
        else return false;
    }

    @Override
    public void end(boolean interrupted){
        timer.stop();
        timer.reset();
        swerve.setDefaultCommand(new DefaultDriveCommand(swerve, ()-> 0, ()-> 0, ()-> 0));
    }
}
