package ca.warp7.frckt

import edu.wpi.first.wpilibj.XboxController

@Suppress("unused")
class RobotController internal constructor(internal val data: ControllerData,
                                           internal val controller: XboxController) {

    val aButton get() = data.aButton
    val bButton get() = data.bButton
    val xButton get() = data.xButton
    val yButton get() = data.yButton
    val leftBumper get() = data.leftBumper
    val rightBumper get() = data.rightBumper
    val leftStickButton get() = data.leftStickButton
    val rightStickButton get() = data.rightStickButton
    val startButton get() = data.startButton
    val backButton get() = data.backButton
    val leftTriggerAxis get() = data.leftTriggerAxis
    val rightTriggerAxis get() = data.rightTriggerAxis
    val leftXAxis get() = data.leftXAxis
    val leftYAxis get() = data.leftYAxis
    val rightXAxis get() = data.rightXAxis
    val rightYAxis get() = data.rightYAxis
    var enabled = false
}

