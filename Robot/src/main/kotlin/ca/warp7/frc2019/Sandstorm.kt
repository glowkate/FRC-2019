package ca.warp7.frc2019

import ca.warp7.actionkt.Action
import ca.warp7.frc.Controls
import ca.warp7.frc.RobotControlLoop
import ca.warp7.frc.runAutonomous
import ca.warp7.frc.setControlLoop

object Sandstorm : RobotControlLoop {

    private lateinit var autonomousAction: Action

    override fun setup() {
        println("Robot State: Sandstorm")
        autonomousAction = runAutonomous(Autonomous.mode, timeout = 15.0)
    }

    override fun periodic() {
        if (autonomousAction.shouldFinish() || Controls.driver.leftXAxis > 0.8 || Controls.operator.leftXAxis > 0.8) {
            setControlLoop(ControlLoop)
        }
    }
}