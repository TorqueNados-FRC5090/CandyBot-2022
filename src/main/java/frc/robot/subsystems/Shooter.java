package frc.robot.subsystems;

// Imports
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// This object is used to control the shooter
public class Shooter {
    // Declare all shooter variables 
    private CANSparkMax leaderMotor; // CW
    private CANSparkMax followerMotor; // CCW
    private boolean shooterIsOn;
    private boolean locked;
    private double currentPower;
  
  // Constructor method initiallizes variables used
  public Shooter( int LeaderID, int FollowerID) {
    // Initiallize Leader motor
    leaderMotor = new CANSparkMax(LeaderID, MotorType.kBrushless);
    leaderMotor.restoreFactoryDefaults();

    // Initialize Follower motor
    // Follower motor is inverted so that it will
    // always spin opposite to the Leader motor
    followerMotor = new CANSparkMax(FollowerID, MotorType.kBrushless);
    followerMotor.restoreFactoryDefaults();
    followerMotor.follow(leaderMotor, true);

    // Shooter starts in the 'off' state
    shooterIsOn = false;
    // Shooter is unlocked by default
    locked = false;
  }

  // Accessor Methods (getters)
  public CANSparkMax getLeaderMotor() { return leaderMotor; }
  public CANSparkMax getFollowerMotor() { return followerMotor; }
  public double getLeaderMotorRPM() { return leaderMotor.getEncoder().getVelocity(); }
  public double getFollowerMotorRPM() { return followerMotor.getEncoder().getVelocity(); }
  public boolean isOn() { return shooterIsOn; }
  public boolean isLocked() { return locked; }
  public double getCurrentPower() { return currentPower; }

  // Makes sure that current power is accurate
  public void updateCurrentPower() { currentPower = leaderMotor.get(); }

  // Tracks the power of the motor and
  // locks the power at the highest value detected
  public void setLock(boolean locked) { this.locked = locked; }

  // Sets shooter to specified power
  // Power is locked to [-1, 1]
  public void set( double pwr ) {
    // If the lock is on and the desired power
    // is not greater than the current power,
    // the function immediately ends
    if(locked && pwr <= leaderMotor.get())
      return; 

      leaderMotor.set(pwr);

    // Update shooter state
    if( pwr == 0 ) { shooterIsOn = false; }
    else { shooterIsOn = true; }
  }

  public void increasePowerBy(double pwr) {
    currentPower = currentPower + pwr;
    set(currentPower);
  }

  public void decreasePowerBy(double pwr) {
    currentPower = currentPower - pwr;
    set(currentPower);
  }

  // Sets shooter to full power
  public void fullPower() {
    leaderMotor.set(1);
    shooterIsOn = true;
  }

  // Turns shooter off
  public void off(){
    leaderMotor.set(0);
    shooterIsOn = false;
  }

  // If shooter is off, this will turn it on
  // If shooter is on, this will turn it off
  public void toggle() {
    if( shooterIsOn ) { off(); }
    else { fullPower(); }
  }

  // If shooter is off, this will set it to
  // the specified power
  // If shooter is on, this will turn it off
  public void toggle( double pwr ) {
    if( shooterIsOn ) { off(); }
    else { set(pwr); }
  }
}
