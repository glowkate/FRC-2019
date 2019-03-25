package ca.warp7.frc2019

import ca.warp7.actionkt.queue
import ca.warp7.actionkt.runOnce
import ca.warp7.actionkt.wait
import ca.warp7.frc2019.subsystems.Outtake
import ca.warp7.frc2019.subsystems.drive.DriveForDistance
import ca.warp7.frc2019.subsystems.drive.QuickTurn

@Suppress("unused")
object Autonomous {

    val mode get() = quickTurn

    private val nothingMode = { runOnce { } }

    private val quickTurn
        get() = queue {
            +QuickTurn(1.0)
            +QuickTurn(-180.0)
        }

    private val driveBackgroundSubtractor
        get() = queue {
            +QuickTurn(180.0)
            +wait(0.5)
            +DriveForDistance(8.0)
        }

    private val driveBackingStoreException
        get() = queue {
            +DriveForDistance(8.0)
            +wait(0.5)
            +QuickTurn(180.0)
            +wait(0.5)
            +DriveForDistance(8.0)
            +wait(0.5)
            +QuickTurn(180.0)
        }

    private val straightHatch
        get() = queue {
            +runOnce { Outtake.grabbing = true }
            +DriveForDistance(138.0 / 12)
            +wait(0.5)
            +runOnce { Outtake.grabbing = false }
            +wait(0.2)
            +runOnce { Outtake.pushing = true }
            +wait(0.5)
            +runOnce { Outtake.pushing = false }
        }

    private val leftSideCargoShipHatch
        get() = queue {
            +runOnce { Outtake.grabbing = true }
            +DriveForDistance(12.0)
            +QuickTurn(90.0)
            +DriveForDistance(2.0)
            +runOnce { Outtake.grabbing = false }
            +wait(0.1)
            +runOnce { Outtake.pushing = true }
            +wait(0.3)
            +runOnce { Outtake.pushing = false }
        }

    private val leftRocketHatch
        get() = queue {
            //+DriveForDistance(4.0)
            //+wait(0.5)
            +QuickTurn(-90.0)
            //+wait(0.5)
            //+DriveForDistance(10.0)
        }
}