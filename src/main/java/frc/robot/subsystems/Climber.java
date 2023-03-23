package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.wrappers.LimitSwitch;

// Controls the climber
public class Climber {

    // Declare motors
    private CANSparkMax leftMotor;
    private CANSparkMax rightMotor;
    
    // Declare limit switches
    private LimitSwitch leftLimitSwitch;
    private LimitSwitch rightLimitSwitch;

    // Constructor
    public Climber (int motorID1, int motorID2, int leftLimitSwitchID, int rightLimitSwitchID) {
        // Initialize motors
        leftMotor = new CANSparkMax(motorID1, MotorType.kBrushless);
        rightMotor = new CANSparkMax(motorID2, MotorType.kBrushless);

        leftLimitSwitch = new LimitSwitch(leftLimitSwitchID);
        rightLimitSwitch = new LimitSwitch(rightLimitSwitchID);
    }

    // Accessor methods
    public double getLeft() { return leftMotor.get(); }
    public double getRight() { return rightMotor.get(); }
    public CANSparkMax getleftMotor() { return leftMotor; }
    public CANSparkMax getrightMotor() { return rightMotor; }
    public double getLeftClimberPosition() { return leftMotor.getEncoder().getPosition(); }
    public double getRightClimberPosition() { return rightMotor.getEncoder().getPosition(); }

    // Set left arm's power
    public void setLeft(double pwr) { 
        leftMotor.set(
            leftLimitSwitch.isPressed() ? Math.abs(pwr) : pwr );
    }

    // Set right arm's power
    public void setRight(double pwr) { 
        rightMotor.set(
            rightLimitSwitch.isPressed() ? Math.abs(pwr) : pwr );
    }

    // Set the power of the motors together
    public void set(double pwr) {
        leftMotor.set(
            leftLimitSwitch.isPressed() ? Math.abs(pwr) : pwr );

        rightMotor.set(
            rightLimitSwitch.isPressed() ? Math.abs(pwr) : pwr );
    }

    // Turns off left arm
    public void leftOff() {
        leftMotor.set(0);
    }

    // Turns off right arm
    public void rightOff() {
        rightMotor.set(0);
    }

    // Stop the climber
    public void off() {
        leftMotor.set(0);
        rightMotor.set(0);
    }
}
