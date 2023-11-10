// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.jmhsrobotics.offseason2023;

import java.util.List;

import org.jmhsrobotics.offseason2023.utils.AutoTunable;
import org.jmhsrobotics.offseason2023.utils.Tunable;

import com.pathplanner.lib.commands.FollowPathHolonomic;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.ReplanningConfig;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;
  @Tunable
  private long example = 2;
  @Tunable
  private Double doubleClass = 0.5;

  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
    AutoTunable.enableTuning(this);
    // CommandScheduler.getInstance().cancelAll();
    // PathPlannerServer.startServer(5811);
    
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
    // var pp = new PPSwerveControllerCommand();
    
    List<Translation2d> bezierPoints = PathPlannerPath.bezierFromPoses(
      new Pose2d(1.0, 1.0, Rotation2d.fromDegrees(0)),
      new Pose2d(3.0, 1.0, Rotation2d.fromDegrees(0)),
      new Pose2d(5.0, 3.0, Rotation2d.fromDegrees(0))
  );
  
  
  // Create the path using the bezier points created above
  PathPlannerPath path = new PathPlannerPath(
      bezierPoints,
      new PathConstraints(3.0, 3.0, 2 * Math.PI, 4 * Math.PI), // The constraints for this path. If using a differential drivetrain, the angular constraints have no effect.
      new GoalEndState(0.0, Rotation2d.fromDegrees(0)) // Goal end state. You can set a holonomic rotation here. If using a differential drivetrain, the rotation will have no effect.
  );

    var dr = m_robotContainer.driveSubsystem;
    var pp = new FollowPathHolonomic(
      path,
      dr::getPose, 
      dr::getChassisSpeeds, 
      dr::drive, 
      new HolonomicPathFollowerConfig(
        new PIDConstants(5, 0.0, 0.0), 
        new PIDConstants(5, 0.0, 0.0), 
        4.5, 
        0.4, 
        new ReplanningConfig()),
      dr);

      pp.schedule();
    // 
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {

    System.out.println("Running Annon: ");
    // Jank.jank(m_robotContainer.driveSubsystem);

    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}
}
