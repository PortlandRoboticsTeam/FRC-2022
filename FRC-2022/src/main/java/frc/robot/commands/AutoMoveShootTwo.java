package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
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
        shootTwo = new ShootTwoBalls(this.gun, 0.4, lowerConveyorSpeed, higherConveyorSpeed);
    }

    @Override
    public void initialize(){
        System.out.println("Initialized");
        timer.reset();
    }

    @Override
    public void execute(){
        distance = gun.getDistance();
        if(timer.get()>6){
            System.out.println("timer greater than 6");
            swerve.drive(new ChassisSpeeds(-1, 0, 0));
        }
        else if(distance<minShootDistance){
            System.out.println(distance + " " + minShootDistance);
            swerve.drive(new ChassisSpeeds(-1, 0, 0));
        }
        else if(distance>maxShootDistance){
            System.out.println(distance + " " + maxShootDistance);
            swerve.drive(new ChassisSpeeds(1, 0, 0));
        }
        else if(distance>=minShootDistance && distance<=maxShootDistance && !hasShot){
            System.out.println("is shooting");
            shootTwo.schedule();
            timer.reset();
            timer.start();
            hasShot = true;
        }
    }

    @Override
    public boolean isFinished(){
        if(hasShot && timer.get()>14) return true;
        else return false;
    }

    @Override
    public void end(boolean interrupted){
        timer.stop();
        timer.reset();
        swerve.drive(new ChassisSpeeds(0, 0, 0));
        System.out.println("El fin");
    }
}
