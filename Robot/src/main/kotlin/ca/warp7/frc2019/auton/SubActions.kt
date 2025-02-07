package ca.warp7.frc2019.auton

import ca.warp7.actionkt.queue
import ca.warp7.actionkt.runOnce
import ca.warp7.actionkt.wait
import ca.warp7.actionkt.withTimeout
import ca.warp7.frc2019.actions.DriveForDistance
import ca.warp7.frc2019.actions.LiftSetpoint
import ca.warp7.frc2019.constants.FieldConstants
import ca.warp7.frc2019.constants.LiftConstants
import ca.warp7.frc2019.io.BaseIO
import ca.warp7.frc2019.io.ioInstance

object SubActions {
    private val io: BaseIO = ioInstance()

    internal val intakeHatch
        get() = queue {
            +runOnce { io.grabbing = true }
            +wait(0.3)
            +LiftSetpoint(FieldConstants.kCargo1Height).withTimeout(0.4)
            +wait(0.1)
            +DriveForDistance(10.0 / 12, isBackwards = true)
            +LiftSetpoint(LiftConstants.kHomeHeightInches).withTimeout(0.4)
        }

    internal val outtakeHatch
        get() = queue {
            +runOnce { io.grabbing = false }
            +runOnce { io.pushing = true }
            +wait(0.5)
            +runOnce { io.pushing = false }
        }
}