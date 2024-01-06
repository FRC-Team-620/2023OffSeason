package org.subsystems.vision.Commands;

import org.subsystems.vision.visionSubsystem;

import edu.wpi.first.wpilibj2.command.Command;

public class testVisionCommand extends Command{
    private visionSubsystem vision;
    
    public testVisionCommand(visionSubsystem vision){
        this.vision = vision;
    }
}
