package ca.warp7.frc2019.subsystems

import ca.warp7.frc.control.Subsystem
import ca.warp7.frc.control.lazySolenoid
import ca.warp7.frc.control.victorSPX
import ca.warp7.frc2019.constants.HatchConstants
import ca.warp7.frc2019.constants.OuttakeConstants
import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.can.VictorSPX
import edu.wpi.first.wpilibj.Solenoid

object Outtake : Subsystem() {

    private val leftMaster: VictorSPX = victorSPX(OuttakeConstants.kLeftMaster)
    private val rightMaster: VictorSPX = victorSPX(OuttakeConstants.kRightMaster)
    private val pusher: Solenoid = lazySolenoid(HatchConstants.kPusherSolenoid)
    private val grabber: Solenoid = lazySolenoid(HatchConstants.kGrabberSolenoid)

    var speed = 0.0
    var pushing = false
    var grabbing = false

    override fun onDisabled() {
        leftMaster.neutralOutput()
        rightMaster.neutralOutput()
        pusher.set(false)
        grabber.set(false)
    }

    override fun onOutput() {
        println("pushing: $pushing")
        println("grabbing: ${!grabbing}")
        leftMaster.set(ControlMode.PercentOutput, speed)
        // Note unlike the conveyor, the right master is reversed
        rightMaster.set(ControlMode.PercentOutput, -speed)
        pusher.set(pushing)
        grabber.set(!grabbing)
    }
}