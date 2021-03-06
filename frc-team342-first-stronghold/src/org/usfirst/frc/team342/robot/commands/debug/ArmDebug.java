package org.usfirst.frc.team342.robot.commands.debug;

import org.usfirst.frc.team342.robot.subsystems.BoulderController;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary;

public class ArmDebug extends Command {
	private BoulderController boulderCont;

	private double prevTime;

	@Override
	protected void initialize() {
		boulderCont = BoulderController.getInstance();

		prevTime = timeSinceInitialized();
	}

	@Override
	protected void execute() {
		// Only update at most 5 times a second
		if (timeSinceInitialized() > prevTime + 0.1) {
			String arm = "Arm Current " + boulderCont.getArmCurrent() + "\n";
			String armEncoder = "Arm encoder " + boulderCont.getArmEncoder() + "\n";
			String armPositionError = "Arm Position error " + boulderCont.getPositionError() + "\n";
			String current = "Shooter Current " + boulderCont.getShooterCurrent() + "\n";
			String enc = "Encoder " + boulderCont.getShootEncVel() + "\n";

			FRCNetworkCommunicationsLibrary.HALSetErrorData(arm);
			System.out.print(arm);

			FRCNetworkCommunicationsLibrary.HALSetErrorData(armEncoder);
			System.out.print(armEncoder);

			FRCNetworkCommunicationsLibrary.HALSetErrorData(armPositionError);
			System.out.print(armPositionError);

			FRCNetworkCommunicationsLibrary.HALSetErrorData(current);
			System.out.print(current);

			FRCNetworkCommunicationsLibrary.HALSetErrorData(enc);
			System.out.print(enc);

			prevTime = timeSinceInitialized();
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}

}
