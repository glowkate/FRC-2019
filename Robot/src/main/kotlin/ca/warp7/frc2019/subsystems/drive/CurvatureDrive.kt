package ca.warp7.frc2019.subsystems.drive

import ca.warp7.actionkt.Action
import ca.warp7.frc.linearRamp
import ca.warp7.frc2019.constants.DriveConstants
import ca.warp7.frc2019.subsystems.Drive
import ca.warp7.frc2019.subsystems.DriveState
import edu.wpi.first.wpilibj.drive.DifferentialDrive

object CurvatureDrive : Action {
    var xSpeed = 0.0
    var zRotation = 0.0
    var isQuickTurn = false


    var left = 0.0
    var right = 0.0

    private val differentialDrive = DifferentialDrive(
            linearRamp(DriveConstants.kRampSecondsFromNeutralToFull) { left = it },
            linearRamp(DriveConstants.kRampSecondsFromNeutralToFull) { right = it }
    )

    init {
        differentialDrive.setDeadband(DriveConstants.kDifferentialDeadband)
        differentialDrive.isSafetyEnabled = false
    }

    override fun start() {
        Drive.outputMode = Drive.OutputMode.Percent
        xSpeed = 0.0
        zRotation = 0.0
        isQuickTurn = false
    }

    override fun update() {
        differentialDrive.curvatureDrive(xSpeed, zRotation, isQuickTurn)
        Drive.leftDemand = left
        Drive.rightDemand = right
    }

    override val shouldFinish get() = false

    override fun stop() {
        Drive.set(DriveState.kNeutralOutput)
    }
}