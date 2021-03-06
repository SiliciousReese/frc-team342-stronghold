
package org.usfirst.frc.team342.robot;

import org.usfirst.frc.team342.robot.commands.drive.AutonomousDrive;
import org.usfirst.frc.team342.robot.commands.drive.DriveUnderLowBar;
import org.usfirst.frc.team342.robot.commands.drive.DriveWithJoystick;
import org.usfirst.frc.team342.robot.commands.shootersystem.arm.ArmDashOutput;
import org.usfirst.frc.team342.robot.subsystems.BoulderController;
import org.usfirst.frc.team342.robot.subsystems.CameraVisionRedux;
import org.usfirst.frc.team342.robot.subsystems.DriveSystem;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
// TODO Determine a standard formatting style
public class Robot extends IterativeRobot {
	private static final String INITIALIZED_MESSAGE = "Burnie is alive!!! No lolligagging or else Zayne will get angry.\n";

	private static final String AUTONOMOUSE_DRIVE_STRAIGHT_MESSAGE = "Drive straight autonomous mode activated.\n";
	private static final String AUTONOMOUS_LOW_BAR_MESSAGE = "Autoing under low bar.\n";
	private static final String AUTONOMOUS_UNUSED_MESSAGE = "No autonomous mode used.\n";

	private static final String TELEOP_MESSAGE = "Ich werde in Ihrem Gesicht zu explodieren. Teleop Initialized.\n";

	private static final String AUTONOMOUS_ENABLED = "Autonomous Enabled?";

	private static final String AUTONOMOUS_SPEED = "Autonomous Speed?";
	private static final double AUTONOMOUS_DEFAULT_SPEED_VAL = -0.8;

	private static final String AUTONMOUS_DRIVE_UNDER_LOWBAR = "Autonomous Low-Bar Enabled?";
	private static final String AUTONOMOUS_DRIVE_OVER_CHEVAL = "Autonomous Cheval de frise?";

	private DriveWithJoystick joydriveCommand;
	private ArmDashOutput armDashOutCommand;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		// Initialize subsystems.
		DriveSystem.getInstance();
		CameraVisionRedux.getInstance();
		BoulderController.getInstance();

		OI.initInstance();
		FRCNetworkCommunicationsLibrary.HALSetErrorData(INITIALIZED_MESSAGE);
		SmartDashboard.putBoolean(AUTONOMOUS_ENABLED, true);
		SmartDashboard.putBoolean(AUTONMOUS_DRIVE_UNDER_LOWBAR, false);
		SmartDashboard.putBoolean(AUTONOMOUS_DRIVE_OVER_CHEVAL, false);
		SmartDashboard.putNumber(AUTONOMOUS_SPEED, AUTONOMOUS_DEFAULT_SPEED_VAL);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	public void disabledInit() {
	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();

		double val = SmartDashboard.getNumber(AUTONOMOUS_SPEED);

		if (val > 1.0 || val < -1.0) {
			val = AUTONOMOUS_DEFAULT_SPEED_VAL;
			SmartDashboard.putNumber(AUTONOMOUSE_DRIVE_STRAIGHT_MESSAGE, AUTONOMOUS_DEFAULT_SPEED_VAL);
		}
	}

	public void autonomousInit() {
		double speed = SmartDashboard.getNumber(AUTONOMOUS_SPEED);

		if (SmartDashboard.getBoolean(AUTONOMOUS_ENABLED)) {
			if (SmartDashboard.getBoolean(AUTONMOUS_DRIVE_UNDER_LOWBAR)) {
				new DriveUnderLowBar(speed).start();
				FRCNetworkCommunicationsLibrary.HALSetErrorData(AUTONOMOUS_LOW_BAR_MESSAGE);
			} else {
				new AutonomousDrive(speed).start();
				FRCNetworkCommunicationsLibrary.HALSetErrorData(AUTONOMOUSE_DRIVE_STRAIGHT_MESSAGE);
			}
		} else {
			// DO NOTHING
			FRCNetworkCommunicationsLibrary.HALSetErrorData(AUTONOMOUS_UNUSED_MESSAGE);
		}

	}

	/** This function is called periodically during autonomous */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		FRCNetworkCommunicationsLibrary.HALSetErrorData(TELEOP_MESSAGE);
		joydriveCommand = new DriveWithJoystick();
		armDashOutCommand = new ArmDashOutput();
	}

	/** This function is called periodically during operator control */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		joydriveCommand.start();
		armDashOutCommand.start();
	}

	/** This function is called periodically during test mode */
	public void testPeriodic() {
	}
}
