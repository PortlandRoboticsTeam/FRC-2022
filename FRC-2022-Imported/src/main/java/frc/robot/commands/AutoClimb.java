package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.FunnyMonkeyMachine;

public class AutoClimb extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    FunnyMonkeyMachine armMachine;
    int i;

    ExtendArms extendArms;
    RetractArms retractArms;
    

    Boolean forwardLimit;
    Boolean reverseLimit;

    public AutoClimb(FunnyMonkeyMachine armMachine){
        this.armMachine = armMachine;
        addRequirements(armMachine);
    }

    @Override
    public void initialize(){
        i = 0;
    }

    @Override
    public void execute(){
        forwardLimit = armMachine.getLimitSwitch(1, 1).isPressed() && armMachine.getLimitSwitch(2, 1).isPressed();
        reverseLimit = armMachine.getLimitSwitch(1, 2).isPressed() && armMachine.getLimitSwitch(2, 2).isPressed();
        
    }


}
