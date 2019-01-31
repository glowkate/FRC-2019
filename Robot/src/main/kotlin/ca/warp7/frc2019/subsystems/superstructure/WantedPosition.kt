package ca.warp7.frc2019.subsystems.superstructure

import kotlin.math.max
import kotlin.math.min


class WantedPosition {

    private var setpointLevel = 0
    var setpointType = LiftSetpointType.Cargo

    fun increaseLiftSetpoint() {
        setpointLevel = min(setpointLevel + 1, 3)
    }

    fun decreaseLiftSetpoint() {
        setpointLevel = max(setpointLevel - 1, 0)
    }

    fun toWantedLiftHeight(): Double { // TODO
        return setpointLevel * 5.0 + when (setpointType) {
            LiftSetpointType.Cargo -> 3.0
            LiftSetpointType.HatchPanel -> 2.0
        }
    }
}