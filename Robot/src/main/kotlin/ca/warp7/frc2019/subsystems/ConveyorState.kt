package ca.warp7.frc2019.subsystems

import ca.warp7.actionkt.runOnce
import ca.warp7.frc.OpenLoopState

object ConveyorState {
    val kIdle = runOnce { }

    val kOpenLoop = OpenLoopState { Conveyor.speed = it }
}