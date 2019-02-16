package ca.warp7.frc2019

import ca.warp7.actionkt.action
import ca.warp7.frc.ControllerState.HeldDown
import ca.warp7.frc.ControllerState.Pressed
import ca.warp7.frc.RobotControlLoop
import ca.warp7.frc.set
import ca.warp7.frc.withDriver
import ca.warp7.frc.withOperator
import ca.warp7.frc2019.constants.ControlConstants
import ca.warp7.frc2019.subsystems.*

object MainLoop : RobotControlLoop {

    override fun setup() {
        println("Robot State: Teleop")
        Drive.set(DriveState.kNeutralOutput)
    }

    override fun periodic() {
        withDriver {
            Drive.set(DriveState.kCurvature) {
                xSpeed = leftYAxis
                zRotation = rightXAxis
                isQuickTurn = leftBumper == HeldDown
            }
            when {
                leftTriggerAxis > ControlConstants.kAxisDeadband -> {
                    Superstructure.set(SuperstructureState.kPassThrough) {
                        speed = leftTriggerAxis * forward
                        outtaking = rightBumper == HeldDown
                    }
                    Intake.set {
                        speed = leftTriggerAxis
                        extended = true
                    }
                }
                rightTriggerAxis > ControlConstants.kAxisDeadband -> {
                    Superstructure.set(SuperstructureState.kPassThrough) {
                        speed = rightTriggerAxis * reverse
                        outtaking = false
                    }
                    Intake.set {
                        speed = -rightTriggerAxis
                        extended = true
                    }
                }
                else -> Intake.set {
                    speed = 0.0
                    extended = false
                }
            }
            if (aButton == Pressed) Climber.set { climbing = !climbing }
        }
        withOperator {
            when {
                leftTriggerAxis > ControlConstants.kAxisDeadband ->
                    Superstructure.set(SuperstructureState.kPassThrough) {
                        speed = leftTriggerAxis * forward
                        outtaking = aButton == HeldDown
                    }
                rightTriggerAxis > ControlConstants.kAxisDeadband ->
                    Superstructure.set(SuperstructureState.kPassThrough) {
                        speed = rightTriggerAxis * reverse
                        outtaking = false
                    }
            }
            when (Pressed) {
                aButton -> Hatch.set(action {
                    onStart { Hatch.pushing = true }
                    finishWhen { elapsed > 0.5 }
                    onStop { Hatch.pushing = false }
                })
                else -> Unit
            }
            if (xButton == HeldDown) Lift.set(LiftState.kOpenLoop) { speed = leftYAxis }
            if (startButton == Pressed) Superstructure.set(SuperstructureState.kDefending)
        }
    }
}