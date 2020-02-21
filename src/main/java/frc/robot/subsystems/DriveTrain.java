/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;

public class DriveTrain extends SubsystemBase implements Loggable {
  @Log
  private final SpeedController leftmotor = Robot.isReal() 
      ? 
      // real controller
      new SpeedControllerGroup(new PWMVictorSPX(0), new PWMVictorSPX(1)) : 
      // simulated controller 
      new SpeedControllerGroup(new PWMVictorSPX(0), new PWMVictorSPX(1));
  @Log
  private final SpeedController rightmotor = Robot.isReal() 
      ? 
      // real controller
      new SpeedControllerGroup(new PWMVictorSPX(2), new PWMVictorSPX(3)) : 
      // simulated controller 
      new SpeedControllerGroup(new PWMVictorSPX(2), new PWMVictorSPX(3));
  @Log
  private final DifferentialDrive drive = new DifferentialDrive(leftmotor, rightmotor);
  @Log
  private final Encoder leftencoder = new Encoder(1,2);
  @Log
  private final Encoder rightencoder = new Encoder(3,4);

  /**
   * Creates a new Drive Subsystem.
   */
  public DriveTrain() {

    if (Robot.isReal()) {
      // real encoders
      leftencoder.setDistancePerPulse(0.042);
      rightencoder.setDistancePerPulse(0.042);
    } else {
      leftencoder.setDistancePerPulse((4.0 / 12.0 * Math.PI) / 360.0);
      rightencoder.setDistancePerPulse((4.0 / 2.0 * Math.PI) / 360.0);
    }
  }

  public void drive(double left, double right) {
    drive.tankDrive(left, right);
  }

  public void reset() {
    leftencoder.reset();
    rightencoder.reset();
  }
  
  public double getDistance() {
    return (rightencoder.getDistance() + leftencoder.getDistance());
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
