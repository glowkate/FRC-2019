package ca.warp7.frc2019.subsystems

import ca.warp7.actionkt.runOnce
import ca.warp7.frc2019.subsystems.superstructure.ManualFrontIntake

object FrontIntakeState {
    val kIdle = runOnce { }

    val ManualControl = ManualFrontIntake
}