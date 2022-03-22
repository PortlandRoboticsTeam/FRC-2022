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
    private double distance;
    private boolean hasShot = false;
    private boolean outOfRange = true;
    
    public AutoMoveShootTwo(SwerveShaninigans swerve, BallGun gun){
        this.swerve = swerve;
        this.gun = gun;
        addRequirements(swerve, gun);
    }
    //You get 15sec max

    @Override
    public void initialize(){
        gun.spinLowerConveyor(1);
        System.out.println("Initialized");
        timer.reset();
    }

    @Override
    public void execute(){
        distance = gun.getDistance();

        if(outOfRange){
            if(distance<minShootDistance){
                swerve.drive(new ChassisSpeeds(-.8,0,0));
                System.out.println("too short" + distance);
            }
            else{
                System.out.print("ahhhhhhhhhh");
                outOfRange = false;
                swerve.drive(new ChassisSpeeds(0,0,0));
            }
        }
        else{
            timer.start();
            gun.spinBallLauncher(launchSpeed);
            if(timer.get()>5){
                hasShot = true;
            }
            else if(timer.get()>2){
                gun.spingHigherConveyor(1);
            }
        }
    }

    @Override
    public boolean isFinished(){
        if(hasShot) return true;
        else return false;
    }

    @Override
    public void end(boolean interrupted){
        timer.stop();
        timer.reset();
        swerve.drive(new ChassisSpeeds(0, 0, 0));
        gun.stopMotors();
        System.out.println("El fin");
    }
}
