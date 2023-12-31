// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.drivetrain.DriveSubsystem;
import frc.robot.drivetrain.commands.DriveCommand;

public class RobotContainer {

  private CommandXboxController control = new CommandXboxController(0);
  private DriveSubsystem m_swerve = new DriveSubsystem();

  public RobotContainer() {
    //TODO: replace with the commands later!!!

    //create a new brake command
    // control.a().onTrue(new RunCommand(
    //   () -> m_swerve.setX(),
    //   m_swerve));

    // // reset zero heading
    // control.b().onTrue(new RunCommand(()->m_swerve.zeroHeading(), m_swerve));

    this.m_swerve.setDefaultCommand(new DriveCommand(this.m_swerve, this.control));
    configureBindings();
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }

  public DriveSubsystem getDriveSubsystem(){
    return this.m_swerve;
  }
}
