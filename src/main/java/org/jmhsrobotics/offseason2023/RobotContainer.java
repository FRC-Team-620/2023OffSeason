// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.jmhsrobotics.offseason2023;

import org.jmhsrobotics.offseason2023.controlBoard.CompControl;
import org.jmhsrobotics.offseason2023.controlBoard.ControlBoard;
import org.jmhsrobotics.offseason2023.subsystems.drive.DriveSubsystem;
import org.jmhsrobotics.offseason2023.subsystems.drive.commands.DriveCommand;
import org.jmhsrobotics.offseason2023.utils.Tunable;
import org.jmhsrobotics.warcore.nt.NT4Util;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

@Tunable
public class RobotContainer {

  private ControlBoard control = new CompControl();
  public DriveSubsystem driveSubsystem = new DriveSubsystem();

  public RobotContainer() {

    this.driveSubsystem.setDefaultCommand(new DriveCommand(this.driveSubsystem, this.control));
    
    configureBindings();
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }

  public DriveSubsystem getDriveSubsystem(){
    return this.driveSubsystem;
  }
}
