package ca.warp7.frc2019.subsystems

import ca.warp7.actionkt.runOnce
import ca.warp7.frc2019.subsystems.superstructure.IndexingCargo
import ca.warp7.frc2019.subsystems.superstructure.MovingLift

@Suppress("unused")
object SuperstructureState {

    val kStartingConfiguration = runOnce {
        Outtake.set(OuttakeState.kIdle)
        Lift.set(LiftState.kIdle)
        Intake.set(IntakeState.kUp)
        Conveyor.set(ConveyorState.kIdle)
    }

    val kDefending = runOnce {
        Intake.set(IntakeState.kUp)
        Outtake.set(OuttakeState.kIdle)
        Superstructure.set(kMovingLift) {} //TODO have the lift go down
    }

    val kIndexingCargo = IndexingCargo

    val kMovingLift = MovingLift

    val kHoldingPosition = runOnce { }
}